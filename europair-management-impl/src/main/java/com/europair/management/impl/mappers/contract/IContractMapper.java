package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = {IContractLineMapper.class}
)
public interface IContractMapper {

    IContractMapper INSTANCE = Mappers.getMapper(IContractMapper.class);

    ContractDto toDto(final Contract entity);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "contractState", ignore = true)
    Contract toEntity(final ContractDto dto);

    @Mapping(target = "contractState", ignore = true)
    void updateFromDto(final ContractDto dto, @MappingTarget Contract entity);

}
