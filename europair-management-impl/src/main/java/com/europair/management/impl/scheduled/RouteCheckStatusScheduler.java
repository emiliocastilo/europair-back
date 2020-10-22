package com.europair.management.impl.scheduled;

import com.europair.management.api.enums.RouteStates;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteCheckStatusScheduler {

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Cron executed every 15 minutes
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "RouteCheckStatusScheduler_checkRouteStatus", lockAtLeastForString = "PT5M", lockAtMostForString = "PT14M")
    public void checkRouteStatus() {

        // Take a look at the state of a route, check the date of the flight associated with It
        // and if is before than the current date then transit the state to expired.
        List<Route> routeList = this.routeRepository.searchNotLostRoutesAndNotWon(RouteStates.SALES);

        // List<Flight> flightList = routeList.stream().map(Route::getFlights).collect(Collectors.toList());

        // iterate over the list and check all the dates from flights
        System.out.println("waka wakaa : " + (null != routeList ? routeList.size() : "0"));

    }

}
