package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.api.dto.contract.ContractLineDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.ContractLine;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContractLineMapper {

    IContractLineMapper INSTANCE = Mappers.getMapper(IContractLineMapper.class);

    @Mapping(target = "contract.contractLines", ignore = true)
    ContractLineDto toDto(final ContractLine entity);

    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "flight", ignore = true)
    ContractLine toEntity(final ContractLineDto dto);

    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "routeId", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "flightId", ignore = true)
    @Mapping(target = "flight", ignore = true)
    void updateFromDto(final ContractDto dto, @MappingTarget ContractLine entity);

    @Mapping(target = "contractLineType", source = "lineContributionRouteType")
    ContractLine toContractLineFromContributionLine(final LineContributionRoute contributionLine);
}
