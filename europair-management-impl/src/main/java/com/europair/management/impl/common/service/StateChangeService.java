package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.FileStates;
import com.europair.management.api.enums.RouteStates;

public interface StateChangeService {

    void changeState(final Long fileId, final FileStates state);

    void changeState(final Long routeId, final RouteStates state);

    void changeState(final Long contributionId, final ContributionStates state);

}
