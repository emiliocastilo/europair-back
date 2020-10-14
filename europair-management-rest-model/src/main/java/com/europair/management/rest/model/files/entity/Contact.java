package com.europair.management.rest.model.files.entity;

import com.europair.management.api.enums.ContactCategoryEnum;
import com.europair.management.api.enums.ContactTypeEnum;
import com.europair.management.api.enums.OperationTypeEnum;
import com.europair.management.rest.model.audit.entity.AuditModificationBaseEntity;
import com.europair.management.rest.model.cities.entity.City;
import com.europair.management.rest.model.common.TextField;
import com.europair.management.rest.model.countries.entity.Country;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "contacts")
@Data
public class Contact extends AuditModificationBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "contact_code", nullable = false, unique = true, length = TextField.TEXT_20)
    private String code;

//    @NotNull
//    @Column(name = "crm_code", nullable = false, unique = true, length = TextField.TEXT_30)
    @Column(name = "crm_code", length = TextField.TEXT_30)
    private String crmCode;

    @Column(name = "contact_type", length = TextField.TEXT_20)
    @Enumerated(EnumType.STRING)
    private ContactTypeEnum contactType;

    @Column(name = "company_code", length = TextField.TEXT_30)
    private String companyCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_category", length = TextField.TEXT_20)
    @Enumerated(EnumType.STRING)
    private ContactCategoryEnum contactCategory;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alias")
    private String alias;

    @Column(name = "address")
    private String address;

    @Column(name = "city_id")
    private Long cityId;

    @ManyToOne
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;

    @Column(name = "postal_code", length = TextField.TEXT_20)
    private String postalCode;

    @Column(name = "province", length = TextField.TEXT_120)
    private String province;

    @Column(name = "country_id")
    private Long countryId;

    @ManyToOne
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private Country country;

    @Column(name = "common_operation_type", length = TextField.TEXT_30)
    @Enumerated(EnumType.STRING)
    private OperationTypeEnum commonOperationType;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = TextField.TEXT_40)
    private String phoneNumber;

    @Column(name = "mobile_phone_number", length = TextField.TEXT_30)
    private String mobilePhoneNumber;

    @Column(name = "fax_number", length = TextField.TEXT_30)
    private String faxNumber;

    @Column(name = "observation")
    private String observation;

}
