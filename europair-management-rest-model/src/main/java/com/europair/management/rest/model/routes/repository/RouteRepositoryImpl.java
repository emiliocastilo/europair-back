package com.europair.management.rest.model.routes.repository;

import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.routes.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RouteRepositoryImpl extends BaseRepositoryImpl<Route> implements IRouteRepositoryCustom {

    @Override
    public Page<Route> findRouteByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Route.class, criteria, pageable);
    }

    @Override
    public boolean canChangeState(final RouteStatesEnum stateFrom, final RouteStatesEnum stateTo) {
        return switch (stateFrom) {
            case SALES -> true;
            case OPTIONED -> !RouteStatesEnum.SALES.equals(stateTo);
            case WON -> !RouteStatesEnum.SALES.equals(stateTo) && !RouteStatesEnum.OPTIONED.equals(stateTo);
            default -> false;
        };
    }
}
