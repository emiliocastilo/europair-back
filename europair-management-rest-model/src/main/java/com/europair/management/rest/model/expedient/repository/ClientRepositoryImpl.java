package com.europair.management.rest.model.expedient.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.expedient.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ClientRepositoryImpl extends BaseRepositoryImpl<Client> implements IClientRepositoryCustom {

    @Override
    public Page<Client> findClientsByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(Client.class, criteria, pageable);
    }

}
