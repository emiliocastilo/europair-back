package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ContactDto;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContactService {

    Page<ContactDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

    ContactDto findById(Long id);

    ContactDto saveContact(ContactDto contactDto);

    ContactDto updateContact(Long id, ContactDto contactDto);

    void deleteContact(Long id);
}
