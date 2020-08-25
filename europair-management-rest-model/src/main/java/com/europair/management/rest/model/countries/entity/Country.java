package com.europair.management.rest.model.countries.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.regions.entity.Region;
import com.europair.management.rest.model.regionscountries.entity.RegionCountry;
import com.europair.management.rest.model.rolestasks.entity.RolesTasks;
import com.europair.management.rest.model.users.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "countries")
@Data
public class Country extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "countries")
    private List<Region> regions;

}
