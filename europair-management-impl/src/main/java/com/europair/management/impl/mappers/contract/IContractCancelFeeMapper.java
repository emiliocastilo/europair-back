package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractCancelFeeDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.ContractCancelFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContractCancelFeeMapper {

    IContractCancelFeeMapper INSTANCE = Mappers.getMapper(IContractCancelFeeMapper.class);

    ContractCancelFeeDto toDto(final ContractCancelFee entity);

    ContractCancelFee toEntity(final ContractCancelFeeDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(final ContractCancelFeeDto dto, @MappingTarget ContractCancelFee entity);

}
