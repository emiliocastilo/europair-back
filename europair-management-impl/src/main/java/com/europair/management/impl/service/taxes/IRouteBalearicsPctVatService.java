package com.europair.management.impl.service.taxes;

import com.europair.management.api.dto.taxes.RouteBalearicsPctVatDTO;

public interface IRouteBalearicsPctVatService {

  RouteBalearicsPctVatDTO findByOriginAndDestinationWithInverseSearch(Long originAirportId, Long destinationAirportId);

}
