package com.europair.management.rest.model.files.entity;

import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntityHardAudited;
import com.europair.management.rest.model.audit.entity.SoftRemovableBaseEntityHardAudited;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Additional data in a separate table to not pass the row length limit of the database
 */
@Entity
@Table(name = "files_additional_data")
@Data
public class FileAdditionalData extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private File file;

    @Column(name = "flight_motive")
    private String flightMotive;

    @Column
    private String connections;

    @Column
    private String limitations;

    @Column(name = "fixed_variable_fuel")
    private String fixedVariableFuel;

    @Column
    private String luggage;

    @Column(name = "special_luggage")
    private String specialLuggage;

    @Column(name = "on_board_service")
    private String onBoardService;

    @Column(name = "special_requests")
    private String specialRequests;

    @Column(name = "other_charges")
    private String otherCharges;

    @Column(name = "operational_info")
    private String operationalInfo;

}
