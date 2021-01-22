package com.europair.management.impl.service.files;

import com.europair.management.api.dto.common.AuditChangesDto;
import com.europair.management.api.dto.common.BaseAuditDto;
import com.europair.management.api.dto.files.FileDTO;
import com.europair.management.api.enums.FileStatesEnum;
import com.europair.management.api.enums.RevTypeEnum;
import com.europair.management.api.util.ErrorCodesEnum;
import com.europair.management.impl.common.service.IStateChangeService;
import com.europair.management.impl.mappers.files.IFileMapper;
import com.europair.management.impl.util.Utils;
import com.europair.management.rest.model.audit.entity.AuditRevision;
import com.europair.management.rest.model.common.CoreCriteria;
import com.europair.management.rest.model.common.OperatorEnum;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.FileStatus;
import com.europair.management.rest.model.files.repository.FileRepository;
import com.europair.management.rest.model.files.repository.FileStatusRepository;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class FileServiceImpl implements IFileService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private IStateChangeService stateChangeService;

  @Autowired
  private FileStatusRepository fileStatusRepository;

  @Override
  public Page<FileDTO> findAllPaginatedByFilter(Pageable pageable, CoreCriteria criteria) {
    return fileRepository.findFilesByCriteria(criteria, pageable)
      .map(IFileMapper.INSTANCE::toDto);
  }

  @Override
  public FileDTO findById(Long id) {
    return IFileMapper.INSTANCE.toDto(fileRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(id))));
  }

  @Override
  public FileDTO saveFile(FileDTO fileDTO) {

    if (fileDTO.getId() != null) {
      throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NEW_WITH_ID, String.valueOf(fileDTO.getId()));
    }
    File file = IFileMapper.INSTANCE.toEntity(fileDTO);

    // Generate file Code
    file.setCode(generateFileCode());

    // Check status
    if (file.getStatusId() == null) {
      FileStatus status = fileStatusRepository.findFirstByCode(FileStatesEnum.SALES.toString())
              .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_STATUS_CODE_NOT_FOUND, FileStatesEnum.SALES.toString()));
      file.setStatusId(status.getId());
      file.setStatus(status);
    }

    file = fileRepository.save(file);

    return IFileMapper.INSTANCE.toDto(file);
  }

  @Override
  public Boolean updateFile(Long id, FileDTO fileDTO) {

    File file = fileRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(id)));

    IFileMapper.INSTANCE.updateFromDto(fileDTO, file);
    file = fileRepository.save(file);

    return Boolean.TRUE;
  }

  @Override
  public void deleteFile(Long id) {

    File file = fileRepository.findById(id)
      .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(id)));

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
  public void updateStates(List<Long> fileIds, FileStatesEnum state) {
    stateChangeService.changeState(fileIds, state);
  }

  @Override
  public List<String> getValidFileStatesToChange(Long id) {
    File file = fileRepository.findById(id)
            .orElseThrow(() -> Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(id)));
    return Stream.of(FileStatesEnum.values())
            .filter(state -> stateChangeService.canChangeState(file, state))
            .map(FileStatesEnum::name)
            .collect(Collectors.toList());
  }

  @Override
  public List<BaseAuditDto> getAuditChanges(Long id) {
    if (!fileRepository.existsById(id)) {
      throw Utils.ErrorHandlingUtils.getException(ErrorCodesEnum.FILE_NOT_FOUND, String.valueOf(id));
    }
    // Get entity revisions sorted by rev asc
    List<AuditRevision<File>> auditRevisions = fileRepository.getAuditRevisions(id).stream()
            .sorted(Comparator.comparing(fileAuditRevision -> fileAuditRevision.getRev().longValue()))
            .collect(Collectors.toList());
    List<DiffResult<File>> diffResults = new ArrayList<>();

    // search diffs of revisions
    for (int i = 0; i < auditRevisions.size(); i++) {
      File previous = auditRevisions.get(i == 0 ? i : i - 1).getEntity();
      File current = auditRevisions.get(i).getEntity();

      diffResults.add(previous.diff(current));
    }

    return diffResults.stream()
            .map(diffResult -> {
              BaseAuditDto baseAuditDto = new BaseAuditDto();
              baseAuditDto.setUser(diffResult.getRight().getModifiedBy());
              baseAuditDto.setRevType(diffResult.getRight().getModifiedAt().equals(diffResult.getRight().getCreatedAt()) ?
                      RevTypeEnum.ADD : RevTypeEnum.UPDATE);
              baseAuditDto.setDatetime(diffResult.getRight().getModifiedAt());
              baseAuditDto.setChanges(diffResult.getDiffs().stream().map(diff -> {
                AuditChangesDto auditChangesDto = new AuditChangesDto();
                auditChangesDto.setPropertyName(diff.getFieldName());
                auditChangesDto.setOldValue(String.valueOf(diff.getLeft()));
                auditChangesDto.setNewValue(String.valueOf(diff.getRight()));

                return auditChangesDto;
              }).collect(Collectors.toList()));

              return baseAuditDto;
            })
            .sorted(Comparator.comparing(BaseAuditDto::getDatetime).reversed())
            .collect(Collectors.toList());
  }
}
