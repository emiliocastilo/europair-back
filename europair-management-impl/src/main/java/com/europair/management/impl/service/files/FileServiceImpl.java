package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.impl.mappers.files.IFileMapper;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements IFileService {

  @Autowired
  private FileRepository fileRepository;

  @Override
  public Page<FileDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return fileRepository.findFilesByCriteria(criteria, pageable)
      .map(IFileMapper.INSTANCE::toDto);
  }

  @Override
  public FileDTO findById(Long id) {
    return IFileMapper.INSTANCE.toDto(fileRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public FileDTO saveFile(FileDTO fileDTO) {

    if (fileDTO.getId() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New File expected. Identifier %s got", fileDTO.getId()));
    }
    File file = IFileMapper.INSTANCE.toEntity(fileDTO);
    file = fileRepository.save(file);

    return IFileMapper.INSTANCE.toDto(file);
  }

  @Transactional(readOnly = false)
  @Override
  public Boolean updateFile(Long id, FileDTO fileDTO) {

    File file = fileRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found with id: " + id));

    IFileMapper.INSTANCE.updateFromDto(fileDTO, file);
    file = fileRepository.save(file);

    return Boolean.TRUE;
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteFile(Long id) {

    File file = fileRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found on id: " + id));

    file.setRemovedAt(LocalDateTime.now());
    fileRepository.save(file);
  }
}
