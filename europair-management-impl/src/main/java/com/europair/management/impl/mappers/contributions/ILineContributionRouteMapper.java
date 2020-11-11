package com.europair.management.impl.mappers.contributions;

import com.europair.management.api.dto.contribution.LineContributionRouteDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface ILineContributionRouteMapper {

    ILineContributionRouteMapper INSTANCE = Mappers.getMapper(ILineContributionRouteMapper.class);

    @Mapping(target = "contribution", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "flight", ignore = true)
    LineContributionRoute toEntity(final LineContributionRouteDTO lineContributionRouteDTO);

    @Mapping(target = "contribution", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "flight", ignore = true)
    LineContributionRouteDTO toDto(final LineContributionRoute lineContributionRoute);

    List<LineContributionRouteDTO> toListDtos(final List<LineContributionRoute> listEntities);

    @Mapping(target = "contribution", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "flight", ignore = true)
    void updateFromDto(final LineContributionRouteDTO contributionDTO, @MappingTarget LineContributionRoute contribution);

}
