package com.europair.management.rest.model.regions.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.regions.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RegionRepositoryImpl extends BaseRepositoryImpl<Region> implements IRegionRepositoryCustom {

    @Override
    public Page<Region> findRegionByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Region.class, criteria, pageable);
    }
}
