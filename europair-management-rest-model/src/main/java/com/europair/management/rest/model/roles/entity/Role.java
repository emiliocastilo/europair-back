package com.europair.management.rest.model.roles.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.rolestasks.entity.RolesTasks;
import com.europair.management.rest.model.tasks.entity.Task;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role extends AuditModificationBaseEntity implements Serializable, GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, length = TextField.TEXT_255)
  private String name;

  @Column
  private String description;


  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "roles_tasks",
              joinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName = "id"))
  private List<Task> tasks;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;

  @OneToMany(mappedBy = "role")
  private Set<RolesTasks> rolesTasks;

  /**
   * If the <code>GrantedAuthority</code> can be represented as a <code>String</code>
   * and that <code>String</code> is sufficient in precision to be relied upon for an
   * access control decision by an {@link AccessDecisionManager} (or delegate), this
   * method should return such a <code>String</code>.
   * <p>
   * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
   * as a <code>String</code>, <code>null</code> should be returned. Returning
   * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
   * to specifically support the <code>GrantedAuthority</code> implementation, so
   * returning <code>null</code> should be avoided unless actually required.
   *
   * @return a representation of the granted authority (or <code>null</code> if the
   * granted authority cannot be expressed as a <code>String</code> with sufficient
   * precision).
   */
  @Override
  public String getAuthority() {
    return this.getName();
  }
}
