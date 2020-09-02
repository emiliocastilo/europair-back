package com.europair.management.impl.service.routes;

import com.europair.management.api.dto.routes.RouteDto;
import com.europair.management.impl.common.exception.InvalidArgumentException;
import com.europair.management.impl.common.exception.ResourceNotFoundException;
import com.europair.management.impl.mappers.routes.IRouteMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RouteServiceImpl implements IRouteService {

    private final String FILE_ID_FILTER = "file.id";

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Page<RouteDto> findAllPaginatedByFilter(final Long fileId, Pageable pageable, CoreCriteria criteria) {
        checkIfFileExists(fileId);
        Utils.addCriteriaIfNotExists(criteria, FILE_ID_FILTER, OperatorEnum.EQUALS, String.valueOf(fileId));

        return routeRepository.findRouteByCriteria(criteria, pageable)
                .map(IRouteMapper.INSTANCE::toDto);
    }

    @Override
    public RouteDto findById(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        return IRouteMapper.INSTANCE.toDto(routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id)));
    }

    @Override
    public RouteDto saveRoute(final Long fileId, RouteDto routeDto) {
        checkIfFileExists(fileId);
        if (routeDto.getId() != null) {
            throw new InvalidArgumentException(String.format("New Route expected. Identifier %s got", routeDto.getId()));
        }

        Route route = IRouteMapper.INSTANCE.toEntity(routeDto);

        // Set relationships
        File file = new File();
        file.setId(fileId);
        route.setFile(file);

        route = routeRepository.save(route);

        // ToDo: crear rotaciones?

        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public RouteDto updateRoute(final Long fileId, Long id, RouteDto routeDto) {
        checkIfFileExists(fileId);
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
        IRouteMapper.INSTANCE.updateFromDto(routeDto, route);
        route = routeRepository.save(route);

        // ToDo: modificar rotaciones o recrearlas?

        return IRouteMapper.INSTANCE.toDto(route);
    }

    @Override
    public void deleteRoute(final Long fileId, Long id) {
        checkIfFileExists(fileId);
        if (!routeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Route not found with id: " + id);
        }
        routeRepository.deleteById(id);
    }

    private void checkIfFileExists(final Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new ResourceNotFoundException("File not found with id: " + fileId);
        }
    }

}
