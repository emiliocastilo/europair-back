package com.europair.management.impl.service.expedient;


import com.europair.management.api.dto.expedient.ContactDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.expedient.IContactMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.expedient.entity.Contact;
import com.europair.management.rest.model.expedient.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactDto findById(Long id) {
        return IContactMapper.INSTANCE.toDto(contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id)));
    }

    @Override
    public Page<ContactDto> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return contactRepository.findContactsByCriteria(criteria, pageable)
                .map(IContactMapper.INSTANCE::toDto);
    }

    @Override
    public ContactDto saveContact(final ContactDto contactDto) {

        if (contactDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New contact expected. Identifier %s got", contactDto.getId()));
        }
        Contact contact = IContactMapper.INSTANCE.toEntity(contactDto);
        contact = contactRepository.save(contact);

        return IContactMapper.INSTANCE.toDto(contact);
    }

    @Override
    public ContactDto updateContact(Long id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));

        IContactMapper.INSTANCE.updateFromDto(contactDto, contact);
        contact = contactRepository.save(contact);

        return IContactMapper.INSTANCE.toDto(contact);
    }

    @Override
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }
}
