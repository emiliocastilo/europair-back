package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.routes.entity.Route;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IStateChangeService {

    void changeState(@NotEmpty final List<Long> fileIds, final FileStatesEnum state);

    void changeState(@NotEmpty final List<Long> routeIds, final RouteStatesEnum state);

    void changeState(@NotEmpty final List<Long> contributionIds, final ContributionStatesEnum state);

    boolean canChangeState(@NotNull final File file, final FileStatesEnum stateTo);

    boolean canChangeState(@NotNull final Route route, final RouteStatesEnum stateTo);

    boolean canChangeState(@NotNull final Contribution contribution, final ContributionStatesEnum stateTo);
}
