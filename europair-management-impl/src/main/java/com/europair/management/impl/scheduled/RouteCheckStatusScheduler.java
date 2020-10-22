package com.europair.management.impl.scheduled;

import com.europair.management.api.enums.RouteStates;
import com.europair.management.rest.model.flights.entity.Flight;
import com.europair.management.rest.model.routes.entity.Route;
import com.europair.management.rest.model.routes.repository.RouteRepository;
import com.europair.management.rest.model.users.entity.User;
import com.europair.management.rest.model.users.repository.IUserRepository;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
public class RouteCheckStatusScheduler {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Cron executed every 15 minutes
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "RouteCheckStatusScheduler_checkRouteStatus", lockAtLeastForString = "PT2M", lockAtMostForString = "PT14M")
    public void checkRouteStatus() {

        // first step is to populate the securedSession with the System user
        User user = this.userRepository.findByUsername("system")
                .orElseThrow(
                    () -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Something when wrong retrieveing the System user. Check if exist on database"))
                );

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>()));

        // Take a look at the state of a route, check the date of the flight associated with It
        // and if is before than the current date then transit the state to expired.
        List<Route> routeList = this.routeRepository.searchNotLostRoutesAndNotWon(RouteStates.SALES);

        List<Flight> flightList = routeList.stream().map(Route::getFlights).flatMap(Collection::stream).collect(Collectors.toList());

        for ( Flight flight : flightList ){
            if(flight.getDepartureTime().isBefore(LocalDateTime.now())){
                Route route = this.routeRepository.findById(flight.getRouteId())
                        .orElseThrow(
                                () -> new ResponseStatusException(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        String.format("Something when wrong with the flight : %s can not retrieve the route information", flight.getId()))
                        );
                route.setRouteState(RouteStates.LOST_EXPIRED);

                this.routeRepository.save(route);
            }
        }
    }

}
