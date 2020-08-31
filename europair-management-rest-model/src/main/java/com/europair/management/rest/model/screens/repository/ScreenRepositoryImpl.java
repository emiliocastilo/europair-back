package com.europair.management.rest.model.screens.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.screens.entity.Screen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ScreenRepositoryImpl extends BaseRepositoryImpl<Screen> implements IScreenRepositoryCustom {

    @Override
    public Page<Screen> findScreensByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Screen.class, criteria, pageable);
    }

}
