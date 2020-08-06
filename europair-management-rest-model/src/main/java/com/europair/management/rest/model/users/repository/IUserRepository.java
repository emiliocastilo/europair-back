package com.europair.management.rest.model.users.repository;

import com.europair.management.rest.model.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(final String username);

  List<User> findByName(final String name);

}
