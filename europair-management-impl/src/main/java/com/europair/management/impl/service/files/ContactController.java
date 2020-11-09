package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.ContactDto;
import com.europair.management.api.service.files.IContactController;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private IContactService contactService;

    public ResponseEntity<ContactDto> getContactById(@NotNull final Long id) {
        LOGGER.debug("[ContactController] - Starting method [getContactById] with input: id={}", id);
        final ContactDto contactDto = contactService.findById(id);
        LOGGER.debug("[ContactController] - Ending method [getContactById] with return: {}", contactDto);
        return ResponseEntity.ok(contactDto);
    }

    public ResponseEntity<Page<ContactDto>> getContactByFilter(Pageable pageable, Map<String, String> reqParam) {
        LOGGER.debug("[ContactController] - Starting method [getContactByFilter] with input: pageable={}, reqParam={}", pageable, reqParam);
        CoreCriteria criteria = Utils.mapFilterRequestParams(reqParam);
        final Page<ContactDto> contactDtoPage = contactService.findAllPaginatedByFilter(pageable, criteria);
        LOGGER.debug("[ContactController] - Ending method [getContactByFilter] with return: {}", contactDtoPage);
        return ResponseEntity.ok(contactDtoPage);
    }

    public ResponseEntity<ContactDto> saveContact(@NotNull final ContactDto contactDto) {
        LOGGER.debug("[ContactController] - Starting method [saveContact] with input: contactDto={}", contactDto);

        final ContactDto contactDtoSaved = contactService.saveContact(contactDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contactDtoSaved.getId())
                .toUri();

        LOGGER.debug("[ContactController] - Ending method [saveContact] with return: {}", contactDtoSaved);
        return ResponseEntity.created(location).body(contactDtoSaved);

    }

    public ResponseEntity<ContactDto> updateContact(@NotNull final Long id, @NotNull final ContactDto contactDto) {
        LOGGER.debug("[ContactController] - Starting method [updateContact] with input: id={}, contactDto={}", id, contactDto);

        final ContactDto contactDtoSaved = contactService.updateContact(id, contactDto);
        LOGGER.debug("[ContactController] - Ending method [updateContact] with return: {}", contactDtoSaved);
        return ResponseEntity.ok().body(contactDtoSaved);
    }

    public ResponseEntity<?> deleteContact(@NotNull final Long id) {
        LOGGER.debug("[ContactController] - Starting method [deleteContact] with input: id={}", id);
        contactService.deleteContact(id);
        LOGGER.debug("[ContactController] - Ending method [deleteContact] with no return.");
        return ResponseEntity.noContent().build();
    }

}
