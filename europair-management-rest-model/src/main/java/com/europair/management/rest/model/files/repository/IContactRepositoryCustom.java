package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContactRepositoryCustom {

    Page<Contact> findContactsByCriteria(CoreCriteria criteria, Pageable pageable);

}
