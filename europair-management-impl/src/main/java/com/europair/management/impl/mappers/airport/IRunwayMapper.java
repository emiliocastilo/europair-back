package com.europair.management.impl.mappers.airport;

import com.europair.management.api.dto.airport.RunwayDto;
import com.europair.management.api.dto.conversions.common.Unit;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.airport.entity.Airport;
import com.europair.management.rest.model.airport.entity.Runway;
import com.europair.management.rest.model.enums.UnitEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IRunwayMapper {

    IRunwayMapper INSTANCE = Mappers.getMapper(IRunwayMapper.class);

    //    @Mapping(target = "airport", ignore = true)
    @Mapping(target = "length.value", source = "length")
    @Mapping(target = "length.type", source = "lengthUnit", qualifiedByName = "mapUnitEntityToDto")
    @Mapping(target = "width.value", source = "width")
    @Mapping(target = "width.type", source = "widthUnit", qualifiedByName = "mapUnitEntityToDto")
    RunwayDto toDto(final Runway entity);

    @Mapping(source = "length.value", target = "length")
    @Mapping(source = "length.type", target = "lengthUnit", qualifiedByName = "mapUnitDtoToEntity")
    @Mapping(source = "width.value", target = "width")
    @Mapping(source = "width.type", target = "widthUnit", qualifiedByName = "mapUnitDtoToEntity")
    Runway toEntity(final RunwayDto dto);

    @Mapping(target = "airport", source = "dto", qualifiedByName = "mapRunwayAirportToEntity")
    @Mapping(source = "dto.length.value", target = "length")
    @Mapping(source = "dto.length.type", target = "lengthUnit", qualifiedByName = "mapUnitDtoToEntity")
    @Mapping(source = "dto.width.value", target = "width")
    @Mapping(source = "dto.width.type", target = "widthUnit", qualifiedByName = "mapUnitDtoToEntity")
    void updateFromDto(final RunwayDto dto, @MappingTarget Runway entity);

    @Named("mapRunwayAirportToEntity")
    default Airport mapAirportCountryToEntity(RunwayDto dto) {
        Airport airport = new Airport();
        airport.setId(dto.getAirport().getId());
        return airport;
    }

    @Named("mapUnitDtoToEntity")
    default Unit mapUnitDtoToEntity(UnitEnum unitEntity) {
        return Unit.valueOf(unitEntity.toString());
    }

    @Named("mapUnitEntityToDto")
    default UnitEnum mapUnitEntityToDto(Unit unitDto) {
        return UnitEnum.valueOf(unitDto.toString());
    }

}
