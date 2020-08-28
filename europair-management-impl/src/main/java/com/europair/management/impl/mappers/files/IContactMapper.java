package com.europair.management.impl.mappers.files;

import com.europair.management.api.dto.files.ContactDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.files.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IContactMapper {

    IContactMapper INSTANCE = Mappers.getMapper(IContactMapper.class);

    ContactDto toDto(final Contact entity);

    Contact toEntity(final ContactDto contactDto);

    void updateFromDto(final ContactDto contactDto, @MappingTarget Contact contact);

}
