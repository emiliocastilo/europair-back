package com.europair.management.rest.model.files.repository;

import com.europair.management.rest.model.files.entity.FileAdditionalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAdditionalDataRepository extends JpaRepository<FileAdditionalData, Long>, IFileAdditionalDataRepositoryCustom {

    List<FileAdditionalData> findByFileId(Long fileId);

}
