package com.europair.management.rest.model.screens.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.screens.entity.Screen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IScreenRepositoryCustom {

    Page<Screen> findScreensByCriteria(CoreCriteria criteria, Pageable pageable);

}
