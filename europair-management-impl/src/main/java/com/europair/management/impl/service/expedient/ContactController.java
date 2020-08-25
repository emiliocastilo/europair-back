package com.europair.management.impl.service.expedient;

import com.europair.management.api.dto.expedient.ContactDto;
import com.europair.management.api.service.expedient.IContactController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

@RestController
@Slf4j
public class ContactController implements IContactController {

    @Autowired
    private IContactService contactService;

    public ResponseEntity<ContactDto> getContactById(@NotNull final Long id) {
        final ContactDto contactDto = contactService.findById(id);
        return ResponseEntity.ok(contactDto);
    }

    public ResponseEntity<Page<ContactDto>> getContactByFilter(Pageable pageable, Map<String, String> reqParam) {
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContactDto> contactDtoPage = contactService.findAllPaginatedByFilter(pageable, criteria);
        return ResponseEntity.ok(contactDtoPage);
    }

    public ResponseEntity<ContactDto> saveContact(@NotNull final ContactDto contactDto) {

        final ContactDto contactDtoSaved = contactService.saveContact(contactDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contactDtoSaved.getId())
                .toUri();

        return ResponseEntity.created(location).body(contactDtoSaved);

    }

    public ResponseEntity<ContactDto> updateContact(@NotNull final Long id, @NotNull final ContactDto contactDto) {

        final ContactDto contactDtoSaved = contactService.updateContact(id, contactDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contactDtoSaved.getId())
                .toUri();

        return ResponseEntity.ok().body(contactDtoSaved);

    }

    public ResponseEntity<?> deleteContact(@NotNull final Long id) {

        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();

    }

}
