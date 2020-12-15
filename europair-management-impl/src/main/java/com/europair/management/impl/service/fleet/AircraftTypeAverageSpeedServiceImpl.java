package com.europair.management.impl.service.fleet;

import com.europair.management.api.dto.fleet.AircraftTypeAverageSpeedDto;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.fleet.IAircraftTypeAverageSpeedMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.fleet.entity.AircraftType;
import com.europair.management.rest.model.fleet.entity.AircraftTypeAverageSpeed;
import com.europair.management.rest.model.fleet.repository.AircraftTypeAverageSpeedRepository;
import com.europair.management.rest.model.fleet.repository.AircraftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AircraftTypeAverageSpeedServiceImpl implements IAircraftTypeAverageSpeedService {

    private final String AIRCRAFT_TYPE_ID_FILTER = "aircraftType.id";

    @Autowired
    private AircraftTypeAverageSpeedRepository aircraftTypeAverageSpeedRepository;

    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;

    @Override
    public Page<AircraftTypeAverageSpeedDto> findAllPaginatedByFilter(final Long aircraftTypeId, Pageable pageable, CoreCriteria criteria) {
        checkIfAircraftTypeExists(aircraftTypeId);
        Utils.addCriteriaIfNotExists(criteria, AIRCRAFT_TYPE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(aircraftTypeId));

        return aircraftTypeAverageSpeedRepository.findAircraftTypeAverageSpeedByCriteria(criteria, pageable)
                .map(IAircraftTypeAverageSpeedMapper.INSTANCE::toDto);
    }

    @Override
    public AircraftTypeAverageSpeedDto findById(final Long aircraftTypeId, Long id) {
        checkIfAircraftTypeExists(aircraftTypeId);
        return IAircraftTypeAverageSpeedMapper.INSTANCE.toDto(aircraftTypeAverageSpeedRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_TYPE_AVG_SPEED_NOT_FOUND, String.valueOf(id))));
    }

    @Override
    public AircraftTypeAverageSpeedDto saveAircraftTypeAverageSpeed(final Long aircraftTypeId, AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        checkIfAircraftTypeExists(aircraftTypeId);
        if (aircraftTypeAverageSpeedDto.getId() != null) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_TYPE_AVG_SPEED_NEW_WITH_ID, String.valueOf(aircraftTypeAverageSpeedDto.getId()));
        }

        AircraftTypeAverageSpeed aircraftTypeAverageSpeed = IAircraftTypeAverageSpeedMapper.INSTANCE.toEntity(aircraftTypeAverageSpeedDto);

        // Set relationships
        AircraftType type = new AircraftType();
        type.setId(aircraftTypeId);
        aircraftTypeAverageSpeed.setAircraftType(type);

        aircraftTypeAverageSpeed = aircraftTypeAverageSpeedRepository.save(aircraftTypeAverageSpeed);

        return IAircraftTypeAverageSpeedMapper.INSTANCE.toDto(aircraftTypeAverageSpeed);
    }

    @Override
    public AircraftTypeAverageSpeedDto updateAircraftTypeAverageSpeed(final Long aircraftTypeId, Long id, AircraftTypeAverageSpeedDto aircraftTypeAverageSpeedDto) {
        checkIfAircraftTypeExists(aircraftTypeId);
        AircraftTypeAverageSpeed aircraftTypeAverageSpeed = aircraftTypeAverageSpeedRepository.findById(id)
                .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_TYPE_AVG_SPEED_NOT_FOUND, String.valueOf(id)));
        IAircraftTypeAverageSpeedMapper.INSTANCE.updateFromDto(aircraftTypeAverageSpeedDto, aircraftTypeAverageSpeed);
        aircraftTypeAverageSpeed = aircraftTypeAverageSpeedRepository.save(aircraftTypeAverageSpeed);

        return IAircraftTypeAverageSpeedMapper.INSTANCE.toDto(aircraftTypeAverageSpeed);
    }

    @Override
    public void deleteAircraftTypeAverageSpeed(final Long aircraftTypeId, Long id) {
        checkIfAircraftTypeExists(aircraftTypeId);
        if (!aircraftTypeAverageSpeedRepository.existsById(id)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_TYPE_AVG_SPEED_NOT_FOUND, String.valueOf(id));
        }
        aircraftTypeAverageSpeedRepository.deleteById(id);
    }

    private void checkIfAircraftTypeExists(final Long aircraftTypeId) {
        if (!aircraftTypeRepository.existsById(aircraftTypeId)) {
            throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.AIRCRAFT_TYPE_NOT_FOUND, String.valueOf(aircraftTypeId));
        }
    }

}
