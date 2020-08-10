package com.europair.management.rest.model.fleet.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.common.TextField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "aircraft_categories")
@Data
public class AircraftCategory extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_code", nullable = false, length = TextField.TEXT_20)
    private String code;

    @Column(name = "category_name", nullable = false, length = TextField.TEXT_120)
    private String name;

    @Column(name = "category_order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private AircraftCategory parentCategory;

    @OneToMany(mappedBy = "mainCategory")
    private List<AircraftCategory> subcategories;
}
