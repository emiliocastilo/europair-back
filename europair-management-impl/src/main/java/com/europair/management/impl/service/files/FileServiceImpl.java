package com.europair.management.impl.service.files;

import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.enums.FileStates;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.files.IFileMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements IFileService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private IStateChangeService stateChangeService;

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

    // Generate file Code
    file.setCode(generateFileCode());

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

  private String generateFileCode() {
    final String CODE_FILTER = "code";
    final String CODE_FILLER = "000";
    final int CODE_INIT_VALUE = 200;

    StringBuilder sb = new StringBuilder();

    // Code to filter
    if (Month.NOVEMBER.equals(LocalDate.now().getMonth()) || Month.DECEMBER.equals(LocalDate.now().getMonth())) {
      // Next fiscal year
      sb.append(String.valueOf(LocalDate.now().plusYears(1).getYear()).substring(2));
      sb.append(String.valueOf(LocalDate.now().plusYears(2).getYear()).substring(2));
    } else {
      // Current fiscal year
      sb.append(String.valueOf(LocalDate.now().getYear()).substring(2));
      sb.append(String.valueOf(LocalDate.now().plusYears(1).getYear()).substring(2));
    }
    sb.append(CODE_FILLER);

    // Find last code if exists
    final String codeFilterValue = sb.toString();
    CoreCriteria criteria = new CoreCriteria();
    criteria.setRestrictions(new ArrayList<>());
    Utils.addCriteriaIfNotExists(criteria, CODE_FILTER, OperatorEnum.CONTAINS, codeFilterValue);
    final Integer lastCodeOfCurrentYear = fileRepository.findFilesByCriteria(criteria).stream()
            .map(file -> Integer.valueOf(file.getCode().replace(codeFilterValue, "")))
            .max(Integer::compareTo)
            .orElse(null);

    // Code for new File
    sb.append(lastCodeOfCurrentYear == null ? CODE_INIT_VALUE : lastCodeOfCurrentYear + 1);

    return sb.toString();
  }

  @Override
  public void updateStates(List<Long> fileIds, FileStates state) {
    stateChangeService.changeState(fileIds, state);
  }
}
