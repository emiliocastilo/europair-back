package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractConditionDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.ContractCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContractConditionMapper {

    IContractConditionMapper INSTANCE = Mappers.getMapper(IContractConditionMapper.class);

    @Mapping(target = "contract", ignore = true)
    ContractConditionDto toDto(final ContractCondition entity);

    @Mapping(target = "contract", ignore = true)
    ContractCondition toEntity(final ContractConditionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "contract", ignore = true)
    void updateFromDto(final ContractConditionDto dto, @MappingTarget ContractCondition entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractId", ignore = true)
    @Mapping(target = "contract", ignore = true)
    void copyFromEntity(final ContractCondition entityToCopy, @MappingTarget ContractCondition entity);

}
