package com.europair.management.impl.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.impl.mappers.taxes.IRouteBalearicsPctVatMapper;
import com.europair.management.rest.model.taxes.entity.RouteBalearicsPctVat;
import com.europair.management.rest.model.taxes.repository.IRouteBalearicsPctVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RouteBalearicsPctVatServiceImpl implements IRouteBalearicsPctVatService {

  @Autowired
  private IRouteBalearicsPctVatRepository routeBalearicsPctVatRepository;

  @Override
  public RouteBalearicsPctVatDTO findByOriginAndDestinationWithInverseSearch(Long originAirportId, Long destinationAirportId) {

    Optional<RouteBalearicsPctVat> routeBalearicsPctVat = routeBalearicsPctVatRepository.findByOriginAirportIdAndDestinationAirportId(originAirportId, destinationAirportId);

    if (routeBalearicsPctVat.isPresent()) {
      return IRouteBalearicsPctVatMapper.INSTANCE.toDto(routeBalearicsPctVat.get());
    } else {
      routeBalearicsPctVat = routeBalearicsPctVatRepository.findByOriginAirportIdAndDestinationAirportId(destinationAirportId, originAirportId);
      if (routeBalearicsPctVat.isPresent()) {
        return IRouteBalearicsPctVatMapper.INSTANCE.toDto(routeBalearicsPctVat.get());
      }
    }

    return null;
  }

}
