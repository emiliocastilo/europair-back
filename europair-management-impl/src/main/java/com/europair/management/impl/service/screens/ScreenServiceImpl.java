package com.europair.management.impl.service.screens;

import com.europair.management.api.dto.screens.ScreenDTO;
import com.europair.management.impl.mappers.screens.ScreenMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.screens.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class ScreenServiceImpl implements IScreenService {

    private final ScreenRepository screenRepository;

    @Override
    public Page<ScreenDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
        return screenRepository.findScreensByCriteria(criteria, pageable).map(ScreenMapper.INSTANCE::toDto);
    }

    @Override
    public ScreenDTO findById(Long id) {
        return ScreenMapper.INSTANCE.toDto(screenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screen not found on id: " + id)));
    }
}
