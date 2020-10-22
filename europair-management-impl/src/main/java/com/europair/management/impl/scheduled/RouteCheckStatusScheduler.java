package com.europair.management.impl.scheduled;

import com.europair.management.api.enums.RouteStatesEnum;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
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
        populateSecurityContextForAuditorWithSystemUser();
        // Take a look at the state of a route, check the date of the flight associated with It
        // and if is before than the current date then transit the state to expired.
        List<Route> routeList = this.routeRepository.searchNotLostRoutesAndNotWon(
                new HashSet<>(Arrays.asList(RouteStatesEnum.LOST_CANX_REQUEST, RouteStatesEnum.LOST_DECLINED, RouteStatesEnum.LOST_EXPIRED, RouteStatesEnum.LOST_LOST_TO_X, RouteStatesEnum.WON)));
        List<Flight> flightList = routeList.stream().map(Route::getFlights).flatMap(Collection::stream).collect(Collectors.toList());

        transitRotationToLostExpiredIfFlightIsAreGone(flightList);
        // now we must take a look over all the Routes and see if all the rotations are in LOST_EXPIRED
        // then the ROUTE must change to LOST_EXPIRED
        checkAndTransitRouteStateToLostExpired(routeList);
    }

    /**
     * Transits a route if his flight are gone
     * @param flightList
     */
    private void transitRotationToLostExpiredIfFlightIsAreGone(List<Flight> flightList) {
        for ( Flight flight : flightList){
            if(flight.getDepartureTime().isBefore(LocalDateTime.now())){
                Route route = this.routeRepository.findById(flight.getRouteId())
                        .orElseThrow(
                                () -> new ResponseStatusException(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        String.format("Something when wrong with the flight : %s can not retrieve the route information", flight.getId()))
                        );
                route.setRouteState(RouteStatesEnum.LOST_EXPIRED);
                this.routeRepository.saveAndFlush(route);
            }
        }
    }

    /**
     * This method transits a route to RouteStatesEnum.LOST_EXPIRED if all the rotations are in that state
     * @param routeList
     */
    private void checkAndTransitRouteStateToLostExpired(List<Route> routeList) {
        for (Route route : routeList){
            if (null != route.getRotations() && !route.getRotations().isEmpty()){
                boolean transitToLostExpired = true;
                for (Route rotation : route.getRotations()){
                    if ( !rotation.getRouteState().equals(RouteStatesEnum.LOST_EXPIRED)){
                        transitToLostExpired = false;
                    }
                }
                if (transitToLostExpired){
                    route.setRouteState(RouteStatesEnum.LOST_EXPIRED);
                    this.routeRepository.saveAndFlush(route);
                }
            }
        }
    }

    /**
     * This method populates the SecurityContextHolder to keep the auditory based on spring working
     */
    private void populateSecurityContextForAuditorWithSystemUser() {
        User user = this.userRepository.findByUsername("system")
                .orElseThrow(
                    () -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Something when wrong retrieveing the System user. Check if exist on database"))
                );

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>()));
    }

}
