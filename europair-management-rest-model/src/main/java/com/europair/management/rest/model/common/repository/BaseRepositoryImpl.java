package com.europair.management.rest.model.common.repository;

import com.europair.management.rest.model.audit.entity.AuditRevision;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.Restriction;
import com.europair.management.rest.model.common.exception.EuropairGeneralException;
import com.europair.management.rest.model.common.exception.InvalidArgumentException;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.criteria.internal.path.AbstractJoinImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.AccountException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.io.Serializable;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class BaseRepositoryImpl<T> {

    private static final String LIST_SEPARATOR = "\\|\\|"; // Separator by ||
	private static final Logger LOG = LoggerFactory.getLogger(BaseRepositoryImpl.class);

	@PersistenceContext
    private EntityManager entityManager;

	// Query projection list
	protected Map<String,String> projectionsList = null;

	/**
	 * Metodo que retorna una session de Hibernate para realizar consultas:
	 *    - Nativas
	 *    - JQL
	 *    - Hibernate Criteria
	 *
	 * @return - Session de Hibernate
	 */
	protected Session getSession(){

		return entityManager.unwrap(Session.class);
	}

	/**
	 * Method to return a criteria builder
	 */
	protected CriteriaBuilder getCriteriaBuilder(){
	    return entityManager.getCriteriaBuilder();
	}

    /**
     * method to return the AuditReader repository
     * @return audit reader repository
     */
    protected AuditReader getAuditBuilder() {
        return AuditReaderFactory.get(entityManager);
    }

	/** Method to execute a criteria Query
     *
     * @return Query to be executed
     *
     */
    protected Query createQuery(CriteriaQuery<?> criteriaQuery){
        return entityManager.createQuery(criteriaQuery);

    }

    /** Method to execute a criteria Count Query
     *
     * @return Query count to be executed
     *
     */
    protected Query createCountQuery(CriteriaQuery<Long> countCriteriaQuery){
        return entityManager.createQuery(countCriteriaQuery);

    }


    /** Method to execute a criteria Query with Graph Strategy
    *
    * @return Query to be executed
    *
    */
    protected Query createQuery(CriteriaQuery<T> criteriaQuery, String graphName){

        Query query = entityManager.createQuery(criteriaQuery);
        EntityGraph<?> graph = entityManager.getEntityGraph(graphName);
        query.setHint(QueryHints.HINT_LOADGRAPH, graph);
        return query;
    }


	/**
	 * Method to lazy load a Entity reference from Hibernate session for updates
	 *
	 * @param clazz Entity to load
	 * @param primaryKey primary Key object
	 */
	@SuppressWarnings("unchecked")
	@Transactional(value=TxType.REQUIRED)
	public Object getEntityReference(@SuppressWarnings("rawtypes") Class clazz, Serializable primaryKey){
		return getSession().load(clazz, primaryKey);
	}

	/**
	 * Method to lazy load a Entity references from Hibernate session for updates
	 *
	 * @param clazz Entity to load
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getEntityReferences(@SuppressWarnings("rawtypes") Class clazz, List<Serializable> primaryKeys){
		return getSession().byMultipleIds(clazz).multiLoad(primaryKeys);
	}

	/**
	 * Method to detach Entity from Hibernate session and allowing entity modifications without db changes
	 *
	 * @param entity Entity to detach
	 */
	void detachEntity(T entity){

		getSession().evict(entity);
	}

	/**
	 * Builds Entities -> DTO projection fields
	 *
	 * @return projection map
	 */
	protected Map<String, String> getEntityProjections(){
		throw new NotImplementedException("Method not implemented");
		// Extended classes must override it if it's required
	}

	/**
	 * @throws AccountException
	 * Return entity revisions of an entity
	 * @param id
	 * @return
	 */
	public List<AuditRevision<T>> listRevisions(String id) {

	    List<AuditRevision<T>> revisions = new ArrayList<>();
        AuditReader auditReader = getAuditBuilder();
        Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepositoryImpl.class);

        if(auditReader.isEntityClassAudited(clazz)){

            List<Number> revisionNumbers = auditReader.getRevisions(clazz, id);

            for (Number revision : revisionNumbers) {
            	AuditRevision<T> auditRevision = new AuditRevision<>();
            	auditRevision.setRev(revision);
                auditRevision.setEntity(auditReader.find(clazz, id, revision));
                revisions.add(auditRevision);
            }
        }
        else{
            throw new InvalidArgumentException(String.format("Entity %s is not being audited", clazz.getName()));
        }
        return revisions;
    }

	/**
     * @throws AccountException
     * Return current entity revision number
     * @param id - entity Id
     * @return revision number
     */
    public Number getCurrentRevision(String id) {

        AuditReader auditReader = getAuditBuilder();
        Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepositoryImpl.class);

        if(auditReader.isEntityClassAudited(clazz)){

            List<Number> revisionNumbers = auditReader.getRevisions(clazz, id);
            if(revisionNumbers != null){
                return revisionNumbers.get(0);
            }
            else{
                return 1;
            }
        }
        else{
            throw new InvalidArgumentException(String.format("Entity %s is not being audited", clazz.getName()));
        }
    }



	   /**
     * @throws AccountException
     * Return entity revisions of an entity
     * @param id
     * @return
     */
    public T getRevision(String id, Number revisionNumber) throws AccountException {

        T revisionEntity;
        AuditReader auditReader = getAuditBuilder();
        Class<T> clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepositoryImpl.class);

        if(auditReader.isEntityClassAudited(clazz)){
            revisionEntity = auditReader.find(clazz, id, revisionNumber);
        }
        else{
            throw new AccountException(String.format("Entity %s is not being audited", clazz.getName()));
        }
        return revisionEntity;
    }

    public int getCurrentRevision() {
    	return ((DefaultRevisionEntity) getAuditBuilder().getCurrentRevision(DefaultRevisionEntity.class, true)).getId();
    }

    // PROTECTED METHODS

    /**
     * Create a criteria tp the given entity ordered by pageable sort properties
     *
     * @param criteria  CoreCriteria
     * @param rootClass Root class
     * @param pageable  Pageable with sort information
     * @return Criteria Query object
     */
    protected CriteriaQuery<?> buildCriteria(CoreCriteria criteria, Class rootClass, Pageable pageable) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Object> crit = builder.createQuery(rootClass);
//        CriteriaQuery<Object> crit = builder.createQuery(rootClass).distinct(true); ToDo: falta cambiar config en bdd porque fallan los order by
        Root<?> root = crit.from(rootClass);
        crit.select(root);

        List<Pair<Predicate, Predicate.BooleanOperator>> restrictions = new ArrayList<>();

        if (!CollectionUtils.isEmpty(criteria.getRestrictions())) {
            for (Restriction restriction : criteria.getRestrictions()) {
                restrictions.add(Pair.of(
                        createRestriction(builder, root, restriction),
                        restriction.getQueryOperator() == null ? Predicate.BooleanOperator.AND : restriction.getQueryOperator()));
            }
        }

        if (!restrictions.isEmpty()) {
            Predicate finalPredicate = null;
            for (Pair<Predicate, Predicate.BooleanOperator> r : restrictions) {
                if (Predicate.BooleanOperator.OR.equals(r.getSecond())) {
                    finalPredicate = finalPredicate == null ? builder.or(r.getFirst()) : builder.or(finalPredicate, r.getFirst());
                } else {
                    finalPredicate = finalPredicate == null ? builder.and(r.getFirst()) : builder.and(finalPredicate, r.getFirst());
                }
            }
            crit.where(finalPredicate);
        }

        if (pageable != null) {
            crit.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
        }

        return crit;
    }

    protected CriteriaQuery<Long> buildCountCriteria(CoreCriteria criteria, Class rootClass) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> critCount = builder.createQuery(Long.class);
//        CriteriaQuery<Long> critCount = builder.createQuery(Long.class).distinct(true); ToDo: falta cambiar config en bdd porque fallan los order by

        Root<?> root = critCount.from(rootClass);
        List<Pair<Predicate, Predicate.BooleanOperator>> restrictions = new ArrayList<>();

        if (!CollectionUtils.isEmpty(criteria.getRestrictions())) {
            for (Restriction restriction : criteria.getRestrictions()) {
                restrictions.add(Pair.of(
                        createRestriction(builder, root, restriction),
                        restriction.getQueryOperator() == null ? Predicate.BooleanOperator.AND : restriction.getQueryOperator()));
            }
        }

        if (!restrictions.isEmpty()) {
            Predicate finalPredicate = null;
            for (Pair<Predicate, Predicate.BooleanOperator> r : restrictions) {
                if (Predicate.BooleanOperator.OR.equals(r.getSecond())) {
                    finalPredicate = finalPredicate == null ? builder.or(r.getFirst()) : builder.or(finalPredicate, r.getFirst());
                } else {
                    finalPredicate = finalPredicate == null ? builder.and(r.getFirst()) : builder.and(finalPredicate, r.getFirst());
                }
            }
            critCount.where(finalPredicate);
        }

        Expression<Long> count = builder.count(root);
        critCount.select(count);
        return critCount;
    }

    protected Object getValue(Class<?> expectedDataType, String stringRepresentation) throws ParseException {

        if(String.class.isAssignableFrom(expectedDataType)){
            return stringRepresentation;
        }
        else if(Boolean.class.isAssignableFrom(expectedDataType) ||
                boolean.class.isAssignableFrom(expectedDataType)) {
            return Boolean.valueOf(stringRepresentation);
        }
        else if(Long.class.isAssignableFrom(expectedDataType)){
            return NumberUtils.toLong(stringRepresentation);
        }
        else if(Double.class.isAssignableFrom(expectedDataType)){
            return NumberUtils.toDouble(stringRepresentation);
        }
        else if(Date.class.isAssignableFrom(expectedDataType)){
            ZonedDateTime zdt = ZonedDateTime.parse(stringRepresentation, DateTimeFormatter.ISO_DATE_TIME);
            return Date.from( zdt.toInstant() );
        }
        else if(Enum.class.isAssignableFrom(expectedDataType)){
            Class<Enum> enumType = (Class<Enum>) expectedDataType;
            return Enum.valueOf(enumType, stringRepresentation);
        }
        return stringRepresentation;
    }

    protected Object[] getValueFromList(Class<?> expectedDataType, String stringRepresentation) throws ParseException {
    	List<Object> listOfValues = new ArrayList<>();
    	String[] stringRepresentationSplit = stringRepresentation.split("\\|\\|");
    	for(String value: stringRepresentationSplit) {
    		Object convertedValue = getValue(expectedDataType, value);
    		listOfValues.add(convertedValue);
    	}
    	return listOfValues.toArray(new Object[listOfValues.size()]);
    }

    protected Predicate createRestriction(CriteriaBuilder builder, Root<?> root, Restriction restriction) {

        LOG.trace("Creating restriction by {} : {}", restriction.getColumn(), restriction.getValue());

        String joinTable = null;
        String columnName = null;
        @SuppressWarnings("rawtypes")
        Path column;

        Pattern p = Pattern.compile("(?:(.*?)\\.)?(.*)"); // Matches table alias if exists in group 1. Column name at group 2
        String columnPath = restriction.getColumn();

        column = evaluateJoinPath(root, p, columnPath);

        Class<?> expectedJavaType = column.getJavaType();

        Predicate predicate;
        try{
            switch (restriction.getOperator()) {
                case EQUALS:
                    predicate = builder.equal(column, getValue(expectedJavaType, restriction.getValue()));
                    break;
                case GREATER:
                    predicate = builder.greaterThan(column, (Comparable) getValue(expectedJavaType, restriction.getValue()));
                    break;
                case LOWER:
                    predicate = builder.lessThan(column, (Comparable) getValue(expectedJavaType, restriction.getValue()));
                    break;
                case GREATER_OR_EQ:
                    predicate = builder.greaterThanOrEqualTo(column, (Comparable) getValue(expectedJavaType, restriction.getValue()));
                    break;
                case LOWER_OR_EQ:
                    predicate = builder.lessThanOrEqualTo(column, (Comparable) getValue(expectedJavaType, restriction.getValue()));
                    break;
                case NOT_EQUALS:
                    predicate = builder.or(builder.notEqual(column, getValue(expectedJavaType, restriction.getValue())), builder.isNull(column));
                    break;
                case IS_NULL:
                    predicate = builder.isNull(column);
                    break;
                case IS_NOT_NULL:
                    predicate = builder.isNotNull(column);
                    break;
                case CONTAINS:
                    predicate = builder.like(column, "%" + restriction.getValue() + "%");
                    break;
                case NOT_CONTAINS:
                    predicate = builder.notLike(column, "%" + restriction.getValue() + "%");
                    break;
                case IN:
                    predicate = column.in(getValueFromList(expectedJavaType, restriction.getValue()));
                    break;
                default:
                    throw new InvalidArgumentException(String.format("Invalid operator %s", restriction.getOperator()));
            }
        }
        catch(ParseException | ClassCastException e){
            throw new EuropairGeneralException(
                    String.format("Invalid class type type: %s - value: %s", expectedJavaType, restriction.getValue()),
                    e);
        }
        return predicate;
    }

	private Path<?> evaluateJoinPath(Path<?> root, Pattern p, String columnPath) {

		if (LOG.isTraceEnabled()) {
			LOG.trace(String.format("evaluateJoinPath:(root, p, columnPath=%s)".replaceAll(", ", "=%s, "), root, p,
					columnPath));
		}

		String joinTable;
		String columnName;
		@SuppressWarnings("rawtypes")
		Path column;
		Matcher m = p.matcher(columnPath);

        if(m.matches()){
        	LOG.trace("Valor a matchear en evaluateJoinPath: " + columnPath);
            joinTable = m.group(1);
            columnName = m.group(2);
            if (!StringUtils.isEmpty(joinTable)) {
                Join<?, ?> join;
                try {
                    join = ((Root<?>) root).join(joinTable);
                } catch (ClassCastException e) {
                    /*
                    Casting to AbstractJoinImpl because root class can be of types: SingularAttributeJoin or ListAttributeJoin
                    and both extend from AbstractJoinImpl
                    */
                    LOG.trace("Class cast exception to Root<?> from class: " + root.getClass() + ", trying to cast to AbstractJoinImpl<?,?>");
                    join = ((AbstractJoinImpl<?, ?>) root).join(joinTable);
                }
                column = evaluateJoinPath(join, p, columnName);
            }
            else{
                column = root.get(columnPath);
            }
        }
        else{
            column = root.get(columnPath); // if does not match uses full field name as fallback
        }
		return column;
	}

    /**
     * Create a criteria tp the given entity
     *
     * @param criteria  CoreCriteria
     * @param rootClass Root class
     * @return Criteria Query object
     */
    protected CriteriaQuery<?> buildCriteria(CoreCriteria criteria, Class rootClass) {
        return buildCriteria(criteria, rootClass, null);
    }

    /**
     * Returns a page with filtered elements by criteria and sorted by pageable properties
     *
     * @param rootClass Root class to filter
     * @param criteria  CoreCriteria to use as filter
     * @param pageable  Pageable data
     * @return Page of filtered elements
     */
    protected Page<T> findPageByCriteria(Class<T> rootClass, CoreCriteria criteria, Pageable pageable) {
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> crit = (CriteriaQuery<T>) buildCriteria(criteria, rootClass, pageable);
        Query query = createQuery(crit);

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<T> result = query.getResultList();

        return new PageImpl<>(result, pageable, countByCriteria(rootClass, criteria));
    }

    /**
     * Counts the elements matching the criteria
     *
     * @param rootClass Root class to count
     * @param criteria  CoreCriteria to use as filter
     * @return Number of elements matching the criteria
     */
    protected Long countByCriteria(Class<T> rootClass, CoreCriteria criteria) {
        CriteriaQuery<Long> crit = buildCountCriteria(criteria, rootClass);
        Query query = createCountQuery(crit);

        return (Long) query.getSingleResult();
    }

}
