package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ContactDto;
import com.europair.management.impl.mappers.files.IContactMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.Contact;
import com.europair.management.rest.model.files.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactDto findById(Long id) {
        return IContactMapper.INSTANCE.toDto(contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found with id: " + id)));
    }

    @Override
    public Page<ContactDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contactRepository.findContactsByCriteria(criteria, pageable)
                .map(IContactMapper.INSTANCE::toDto);
    }

    @Override
    public ContactDto saveContact(final ContactDto contactDto) {

        if (contactDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New contact expected. Identifier %s got", contactDto.getId()));
        }
        Contact contact = IContactMapper.INSTANCE.toEntity(contactDto);
        contact = contactRepository.save(contact);

        return IContactMapper.INSTANCE.toDto(contact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found with id: " + id));

        IContactMapper.INSTANCE.updateFromDto(contactDto, contact);
        contact = contactRepository.save(contact);

        return IContactMapper.INSTANCE.toDto(contact);
    }

    @Override
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }
}
