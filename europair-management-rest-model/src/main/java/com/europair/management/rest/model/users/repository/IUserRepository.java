package com.europair.management.rest.model.users.repository;

import com.europair.management.rest.model.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  Optional<User> findByUsername(final String username);

  List<User> findByName(final String name);

  Optional<User> findByEmail(final String email);

}
