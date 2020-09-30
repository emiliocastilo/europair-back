package com.europair.management.impl.service.roles;

import com.europair.management.api.dto.roles.RoleDTO;
import com.europair.management.api.dto.tasks.TaskDTO;
import com.europair.management.impl.mappers.roles.RoleMapper;
import com.europair.management.rest.model.roles.entity.Role;
import com.europair.management.rest.model.roles.repository.IRoleRepository;
import com.europair.management.rest.model.rolestasks.entity.RolesTasks;
import com.europair.management.rest.model.rolestasks.entity.RolesTasksPK;
import com.europair.management.rest.model.rolestasks.repository.RolesTasksRepository;
import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

  private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

  private final IRoleRepository roleRepository;
  private final TaskRepository taskRepository;
  private final RolesTasksRepository rolesTasksRepository;

  @Override
  public Page<RoleDTO> findAllPaginated(Pageable pageable) {
    return roleRepository.findAll(pageable).map(role -> RoleMapper.INSTANCE.toDto(role));
  }

  @Override
  public RoleDTO findById(final Long id) {
    return RoleMapper.INSTANCE.toDto(roleRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found on id: " + id)));
  }

  @Transactional(readOnly = false)
  @Override
  public RoleDTO saveRole(final RoleDTO roleDTO) {

    if(roleDTO.getId() != null){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("New role expected. Identifier %s got", roleDTO.getId()));
    }
    Role role = RoleMapper.INSTANCE.toEntity(roleDTO);
    role = roleRepository.save(role);
    return RoleMapper.INSTANCE.toDto(role);
  }

  @Transactional(readOnly = false)
  @Override
  public RoleDTO updateRole(final Long id, final RoleDTO roleDTO) {
    roleDTO.setId(id);
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found on id: " + id));

    RoleMapper.INSTANCE.updateFromDto(roleDTO, role);
    role = roleRepository.save(role);


      /*
      must iterate over the dto list adding all the task that are not in the JPA list
       */
      for (TaskDTO taskDTO : roleDTO.getTasks()) {
        if (!existIdTaskDTOInJPAList(taskDTO, role.getRolesTasks())){

          // we have left hand side of the relationship role but we must have both
          Task task = taskRepository.findById(taskDTO.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found on id: " + taskDTO.getId()) );

          RolesTasksPK rolesTasksPK = new RolesTasksPK();
          rolesTasksPK.setRoleId(role.getId());
          rolesTasksPK.setTaskId(task.getId());
          RolesTasks rolesTasks = new RolesTasks();
          rolesTasks.setId(rolesTasksPK);
          rolesTasks.setRole(role);
          rolesTasks.setTask(task);

          rolesTasksRepository.save(rolesTasks);
        };
      }
      /*
      must iterate over the jpa list deleting all the task that are not in the DTO list
       */
      for ( RolesTasks rolesTasks : role.getRolesTasks()){
        if (!existIdTaskInDTOList(rolesTasks, roleDTO.getTasks())){
          rolesTasksRepository.delete(rolesTasks);
        }
      }

    role = roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found on id: " + id));

    return RoleMapper.INSTANCE.toDto(role);
  }

  private boolean existIdTaskInDTOList(RolesTasks rolesTasks, List<TaskDTO> tasks) {
    boolean res = false;
    for (TaskDTO taskDTO : tasks){
      if (rolesTasks.getId().getTaskId().equals(taskDTO.getId())){
        res = true;
        break;
      }
    }
    return res;
  }

  private boolean existIdTaskDTOInJPAList(TaskDTO taskDTO, Set<RolesTasks> rolesTasks) {
    boolean res = false;
    for (RolesTasks roleTask : rolesTasks) {
      if (taskDTO.getId().equals(roleTask.getId().getTaskId())) {
        res = true;
        break;
      }
    }
    return res;
  }


  @Transactional(readOnly = false)
  @Override
  public void deleteRole(Long id) {

    Role roleBD = roleRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found on id: " + id));
    roleRepository.deleteById(id);
  }


}
