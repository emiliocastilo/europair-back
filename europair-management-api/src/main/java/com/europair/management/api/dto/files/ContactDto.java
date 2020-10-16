package com.europair.management.api.dto.files;


import com.europair.management.api.dto.audit.AuditModificationBaseDTO;
import com.europair.management.api.dto.cities.CityDTO;
import com.europair.management.api.dto.common.TextField;
import com.europair.management.api.dto.countries.CountryDTO;
import com.europair.management.api.enums.ContactCategoryEnum;
import com.europair.management.api.enums.ContactTypeEnum;
import com.europair.management.api.enums.OperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto extends AuditModificationBaseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    @Size(min = 1, max = TextField.TEXT_20)
    @NotEmpty
    private String code;

    @JsonProperty("name")
    @Size(min = 1, max = TextField.TEXT_255)
    @NotEmpty
    private String name;

    //    @NotEmpty
    @Size(min = 1, max = TextField.TEXT_30)
    private String crmCode;

    @Size(max = TextField.TEXT_20)
    private ContactTypeEnum contactType;

    @Size(max = TextField.TEXT_30)
    private String companyCode;

    private String companyName;

    @Size(max = TextField.TEXT_20)
    private ContactCategoryEnum contactCategory;

    private String alias;

    private String address;

    private Long cityId;
    private CityDTO city;

    @Size(max = TextField.TEXT_20)
    private String postalCode;

    @Size(max = TextField.TEXT_120)
    private String province;

    private Long countryId;
    private CountryDTO country;

    @Size(max = TextField.TEXT_30)
    private OperationTypeEnum commonOperationType;

    private String email;

    @Size(max = TextField.TEXT_40)
    private String phoneNumber;

    @Size(max = TextField.TEXT_30)
    private String mobilePhoneNumber;

    @Size(max = TextField.TEXT_30)
    private String faxNumber;

    private String observation;

}
