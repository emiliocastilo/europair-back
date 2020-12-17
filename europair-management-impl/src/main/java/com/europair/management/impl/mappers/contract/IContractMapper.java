package com.europair.management.impl.mappers.contract;

import com.europair.management.api.dto.contract.ContractCompleteDataDto;
import com.europair.management.api.dto.contract.ContractDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contracts.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        uses = {IContractLineMapper.class}
)
public interface IContractMapper {

    IContractMapper INSTANCE = Mappers.getMapper(IContractMapper.class);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "provider.country", ignore = true)
    @Mapping(target = "client.country", ignore = true)
    @Mapping(target = "totalAmount", source = "entity", qualifiedByName = "mapTotalAmount")
    @Mapping(target = "contractConfiguration.contract", ignore = true)
    ContractDto toDto(final Contract entity);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "provider.country", ignore = true)
    @Mapping(target = "client.country", ignore = true)
    @Mapping(target = "contractLines", ignore = true)
    @Mapping(target = "totalAmount", source = "entity", qualifiedByName = "mapTotalAmount")
    @Mapping(target = "contractConfiguration.contract", ignore = true)
    ContractDto toDtoNoLines(final Contract entity);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "provider.country", ignore = true)
    @Mapping(target = "client.country", ignore = true)
    @Mapping(target = "totalAmount", source = "entity", qualifiedByName = "mapTotalAmount")
    @Mapping(target = "contractConfiguration.contract", ignore = true)
    @Mapping(target = "contractLines", qualifiedByName = "toDtoLineOnly")
    ContractCompleteDataDto toDtoComplete(final Contract entity);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "contractState", ignore = true)
    @Mapping(target = "contractConfiguration", ignore = true)
    Contract toEntity(final ContractDto dto);

    @Mapping(target = "contractState", ignore = true)
    @Mapping(target = "contractConfiguration", ignore = true)
    void updateFromDto(final ContractDto dto, @MappingTarget Contract entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "removedAt", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "contractState", ignore = true)
    @Mapping(target = "contractDate", ignore = true)
    @Mapping(target = "signatureDate", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "contractConfiguration", ignore = true)
    @Mapping(target = "contractLines", ignore = true)
    Contract copyEntity(final Contract entity);

    @Named("mapTotalAmount")
    default BigDecimal mapTotalAmount(final Contract contract) {
        BigDecimal totalAmount = null;
        if (!CollectionUtils.isEmpty(contract.getContractLines())) {
            totalAmount = contract.getContractLines().stream()
                    .map(line -> {
                        BigDecimal price = line.getPrice() != null ? line.getPrice() : BigDecimal.ZERO;
                        BigDecimal taxAmount = line.getVatAmount() != null ? line.getVatAmount() : BigDecimal.ZERO;
                        return price.add(taxAmount);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return totalAmount;
    }

}
