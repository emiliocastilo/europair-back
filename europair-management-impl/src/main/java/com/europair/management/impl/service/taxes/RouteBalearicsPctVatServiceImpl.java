package com.europair.management.impl.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.mappers.taxes.IRouteBalearicsPctVatMapper;
import com.europair.management.impl.util.Utils;
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

    throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.TAX_ROUTE_BALEARIC_NOT_FOUND,
            String.format("[originAirportId:%s], [destinationAirportId:%s]", originAirportId, destinationAirportId));
  }

}
