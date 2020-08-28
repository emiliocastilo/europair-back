package com.europair.management.rest.model.files.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.files.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ContactRepositoryImpl extends BaseRepositoryImpl<Contact> implements IContactRepositoryCustom {

    @Override
    public Page<Contact> findContactsByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Contact.class, criteria, pageable);
    }

}
