package com.europair.management.rest.operators.repository;

import com.europair.management.rest.model.operators.entity.Operator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OperatorSpecification implements Specification<Operator> {

  private String text;

  public OperatorSpecification(String text) {
    super();
    this.text = text;
  }

  @Override
  public Predicate toPredicate(Root<Operator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

    Predicate predicate = criteriaBuilder.disjunction();

    String filterText = text;

    if (!text.contains("%")) {
      filterText = "%" + text + "%";
    }

    return criteriaBuilder.or(
      criteriaBuilder.like(root.get("name"), filterText),
      criteriaBuilder.like(root.get("iataCode"), filterText),
      criteriaBuilder.like(root.get("icaoCode"), filterText)
    );

  }
}
