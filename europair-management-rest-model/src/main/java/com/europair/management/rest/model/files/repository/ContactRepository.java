package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.files.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, IContactRepositoryCustom {
}
