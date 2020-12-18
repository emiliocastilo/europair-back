package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractConfigurationDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.ContractConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContractConfigurationMapper {

    IContractConfigurationMapper INSTANCE = Mappers.getMapper(IContractConfigurationMapper.class);

    @Mapping(target = "contract", ignore = true)
    ContractConfigurationDto toDto(final ContractConfiguration entity);

    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "paymentConditions", ignore = true)
    ContractConfiguration toEntity(final ContractConfigurationDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "paymentConditions", ignore = true)
    void updateFromDto(final ContractConfigurationDto dto, @MappingTarget ContractConfiguration entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "paymentConditions", ignore = true)
    ContractConfiguration copyEntity(final ContractConfiguration entity);
}
