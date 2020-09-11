package com.europair.management.impl.mappers.contributions;

import com.europair.management.api.dto.contribution.ContributionDTO;
import com.europair.management.api.dto.users.UserDTO;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = AuditModificationBaseMapperConfig.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContributionMapper {

    IContributionMapper INSTANCE = Mappers.getMapper(IContributionMapper.class);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "aircraft", ignore = true)
    Contribution toEntity (final ContributionDTO contributionDTO);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "aircraft", ignore = true)
    ContributionDTO toDto(final Contribution contribution);

    List<ContributionDTO> toListDtos(final List<Contribution> listEntities);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "operator", ignore = true)
    @Mapping(target = "aircraft", ignore = true)
    void updateFromDto(final ContributionDTO contributionDTO, @MappingTarget Contribution contribution);

}
