package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStates;
import com.europair.management.api.enums.FileStates;
import com.europair.management.api.enums.RouteStates;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface IStateChangeService {

    void changeState(@NotEmpty final List<Long> fileIds, final FileStates state);

    void changeState(@NotEmpty final List<Long> routeIds, final RouteStates state);

    void changeState(@NotEmpty final List<Long> contributionIds, final ContributionStates state);

}
