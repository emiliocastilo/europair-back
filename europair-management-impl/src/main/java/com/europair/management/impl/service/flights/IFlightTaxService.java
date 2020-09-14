package com.europair.management.impl.service.flights;

import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.flights.entity.FlightTax;
import com.europair.management.rest.model.routes.entity.Route;

import java.util.List;

public interface IFlightTaxService {

    List<FlightTax> saveFlightTaxes(Contribution contribution, Route route);

}
