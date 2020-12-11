package com.europair.management.impl.service.airport;

import com.europair.management.api.dto.operatorsairports.OperatorsAirportsDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.operatorsAirports.IOperatorsAirportsMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.repository.AirportRepository;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.operators.entity.Operator;
import com.europair.management.rest.model.operatorsairports.entity.OperatorsAirports;
import com.europair.management.rest.model.operatorsairports.repository.OperatorsAirportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AirportOperatorServiceImpl implements IAirportOperatorService {

    private final String AIRPORT_ID_FILTER = "airport.id";

    @Autowired
    private OperatorsAirportsRepository operatorsAirportsRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Page<OperatorsAirportsDTO> findAllPaginatedByFilter(final Long airportId, Pageable pageable, CoreCriteria criteria) {
        checkIfAirportExists(airportId);
        Utils.addCriteriaIfNotExists(criteria, AIRPORT_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(airportId));

        return operatorsAirportsRepository.findOperatorsAirportsByCriteria(criteria, pageable)
                .map(IOperatorsAirportsMapper.INSTANCE::toDtoFromAirport);
    }

    @Override
    public OperatorsAirportsDTO findById(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        return IOperatorsAirportsMapper.INSTANCE.toDtoFromAirport(operatorsAirportsRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_OPERATOR_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public OperatorsAirportsDTO saveOperatorsAirports(final Long airportId, OperatorsAirportsDTO operatorsAirportsDto) {
        checkIfAirportExists(airportId);
        if (operatorsAirportsDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_OPERATOR_NEW_WITH_ID, String.valueOf(operatorsAirportsDto.getId()));
        }

        OperatorsAirports operatorsAirports = IOperatorsAirportsMapper.INSTANCE.toEntityFromAirport(operatorsAirportsDto);

        // Set relationships
        Airport airport = new Airport();
        airport.setId(airportId);
        operatorsAirports.setAirport(airport);
        Operator operator = new Operator();
        operator.setId(operatorsAirportsDto.getOperator().getId());
        operatorsAirports.setOperator(operator);

        operatorsAirports = operatorsAirportsRepository.save(operatorsAirports);

        return IOperatorsAirportsMapper.INSTANCE.toDtoFromAirport(operatorsAirports);
    }

    @Override
    public OperatorsAirportsDTO updateOperatorsAirports(final Long airportId, Long id, OperatorsAirportsDTO operatorsAirportsDto) {
        checkIfAirportExists(airportId);
        OperatorsAirports operatorsAirports = operatorsAirportsRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_OPERATOR_NOT_FOUND, String.valueOf(id)));
        IOperatorsAirportsMapper.INSTANCE.updateFromDtoFromAirport(operatorsAirportsDto, operatorsAirports);

        // Set relationships
        if (!operatorsAirports.getOperator().getId().equals(operatorsAirportsDto.getOperator().getId())) {
            Operator operator = new Operator();
            operator.setId(operatorsAirportsDto.getOperator().getId());
            operatorsAirports.setOperator(operator);
        }

        operatorsAirports = operatorsAirportsRepository.save(operatorsAirports);

        return IOperatorsAirportsMapper.INSTANCE.toDtoFromAirport(operatorsAirports);
    }

    @Override
    public void deleteOperatorsAirports(final Long airportId, Long id) {
        checkIfAirportExists(airportId);
        if (!operatorsAirportsRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_OPERATOR_NOT_FOUND, String.valueOf(id));
        }
        operatorsAirportsRepository.deleteById(id);
    }

    private void checkIfAirportExists(final Long airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRPORT_NOT_FOUND, String.valueOf(airportId));
        }
    }

}
