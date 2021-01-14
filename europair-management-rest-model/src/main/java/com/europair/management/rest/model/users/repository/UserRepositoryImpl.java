package com.europair.management.rest.model.users.repository;


import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.repository.BaseRepositoryImpl;
import com.europair.management.rest.model.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements IUserRepositoryCustom {

    @Override
    public Page<User> findUsersByCriteria(CoreCriteria criteria, Pageable pageable) {
        return findPageByCriteria(User.class, criteria, pageable);
    }

}
