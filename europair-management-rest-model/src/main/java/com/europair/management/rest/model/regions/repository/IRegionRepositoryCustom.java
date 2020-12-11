package com.europair.management.rest.model.regions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.regions.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRegionRepositoryCustom {

    Page<Region> findRegionByCriteria(CoreCriteria criteria, Pageable pageable);
}
