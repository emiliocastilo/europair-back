package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.files.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStatusRepository extends JpaRepository<FileStatus, Long>, IFileStatusRepositoryCustom {

    Optional<FileStatus> findFirstByCode(String code);
}
