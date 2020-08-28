package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.files.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, IFileRepositoryCustom {
}
