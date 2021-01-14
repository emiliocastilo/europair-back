package com.europair.management.rest.model.users.repository;

import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IUserRepositoryCustom {

    Page<User> findUsersByCriteria(CoreCriteria criteria, Pageable pageable);

}
