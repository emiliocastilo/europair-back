package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractPaymentConditionDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.ContractPaymentCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContractPaymentConditionMapper {

    IContractPaymentConditionMapper INSTANCE = Mappers.getMapper(IContractPaymentConditionMapper.class);

    ContractPaymentConditionDto toDto(final ContractPaymentCondition entity);

    ContractPaymentCondition toEntity(final ContractPaymentConditionDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(final ContractPaymentConditionDto dto, @MappingTarget ContractPaymentCondition entity);

}
