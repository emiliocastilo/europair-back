package com.europair.management.impl.common.service;

import com.europair.management.api.enums.ContributionStatesEnum;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RouteStatesEnum;
import com.europair.management.rest.model.files.entity.File;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IStateChangeService {

    void changeState(@NotEmpty final List<Long> fileIds, final FileStatesEnum state);

    void changeState(@NotEmpty final List<Long> routeIds, final RouteStatesEnum state);

    void changeState(@NotEmpty final List<Long> contributionIds, final ContributionStatesEnum state);

    boolean canChangeState(@NotNull final File file, final FileStatesEnum stateTo);
}
