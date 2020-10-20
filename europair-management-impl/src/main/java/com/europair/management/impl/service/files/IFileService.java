package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.rest.model.common.CoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFileService {

  Page<FileDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria);

  FileDTO findById(Long id);

  FileDTO saveFile(FileDTO fileDTO);

  Boolean updateFile(Long id, FileDTO fileDTO);

  void deleteFile(Long id);

  void updateStates(List<Long> fileIds, FileStatesEnum state);
}
