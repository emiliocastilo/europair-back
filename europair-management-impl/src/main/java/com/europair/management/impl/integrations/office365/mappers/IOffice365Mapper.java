package com.europair.management.impl.integrations.office365.mappers;

import com.europair.management.api.integrations.office365.dto.ContributionDataDto;
import com.europair.management.api.integrations.office365.dto.ContributionLineDataDto;
import com.europair.management.api.integrations.office365.dto.FileSharingExtendedInfoDto;
import com.europair.management.api.integrations.office365.dto.FileSharingInfoDTO;
import com.europair.management.api.integrations.office365.dto.FlightServiceDataDto;
import com.europair.management.impl.mappers.audit.AuditModificationBaseMapperConfig;
import com.europair.management.rest.model.contributions.entity.Contribution;
import com.europair.management.rest.model.contributions.entity.LineContributionRoute;
import com.europair.management.rest.model.files.entity.File;
import com.europair.management.rest.model.files.entity.FileAdditionalData;
import com.europair.management.rest.model.fleet.entity.AircraftBase;
import com.europair.management.rest.model.flights.entity.FlightService;
import com.europair.management.rest.model.routes.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(config = AuditModificationBaseMapperConfig.class,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface IOffice365Mapper {

    IOffice365Mapper INSTANCE = Mappers.getMapper(IOffice365Mapper.class);


    @Mapping(target = "code", source = "route.file.code")
    @Mapping(target = "description", source = "route.file.description")
    FileSharingInfoDTO mapFile(Route route);

    FileSharingExtendedInfoDto mapFile(File file);

    @Mapping(target = "code", source = "file.code")
    @Mapping(target = "description", source = "file.description")
    FileSharingExtendedInfoDto mapFile(FileAdditionalData fileAdditionalData);

    @Named("mapFlightService")
    default FlightServiceDataDto mapFlightService(final FlightService flightService) {
        FlightServiceDataDto dto = new FlightServiceDataDto();
        dto.setCode(flightService.getService().getCode());
        dto.setComments(flightService.getComments());
        dto.setDescription(flightService.getDescription());
        dto.setProvider(flightService.getProvider().getCode() + " | " + flightService.getProvider().getName());
        dto.setPurchaseAmount(flightService.getPurchasePrice());
        dto.setQuantity(flightService.getQuantity());
        dto.setSaleAmount(flightService.getSalePrice());
        dto.setStatus(flightService.getStatus());

        return dto;
    }

    @Named("mapContribution")
    default ContributionDataDto mapContribution(final Contribution contribution) {
        ContributionDataDto dto = new ContributionDataDto();

        AircraftBase mainBase = contribution.getAircraft().getBases().stream()
                .filter(AircraftBase::getMainBase).findFirst().orElse(null);

        dto.setAircraftType(contribution.getAircraft().getAircraftType().getCode() + " | " +
                contribution.getAircraft().getAircraftType().getDescription());
        dto.setAircraftBase(mainBase == null ? null :
                (mainBase.getAirport().getIataCode() + " | " + mainBase.getAirport().getName()));

        dto.setCurrencyOnPurchase(contribution.getCurrency());
        dto.setExchangeTypeOnPurchase(contribution.getExchangeBuyType());

        dto.setPurchasePrice(contribution.getPurchasePrice());
        dto.setPurchaseCommissionPercentage(contribution.getPurchaseCommissionPercent() == null ? null :
                contribution.getPurchaseCommissionPercent().doubleValue());
        dto.setPurchaseCommissionAmount(
                (dto.getPurchasePrice() == null || dto.getPurchaseCommissionPercentage() == null) ? null :
                        dto.getPurchasePrice().multiply(BigDecimal.valueOf(dto.getPurchaseCommissionPercentage() / 100)));
        dto.setPurchaseNetPrice((dto.getPurchasePrice() == null || dto.getPurchaseCommissionAmount() == null) ? null :
                dto.getPurchasePrice().add(dto.getPurchaseCommissionAmount()));

        dto.setIncludedVAT(Boolean.TRUE.equals(contribution.getIncludedVAT()));

        dto.setCurrencyOnSale(contribution.getCurrencyOnSale());
        dto.setExchangeTypeOnSale(contribution.getExchangeBuyType()); // ToDo: pendiente saber como hacemos las conversiones de divisas

        // ToDo: pendiente saber como hacemos las conversiones de divisas
        dto.setPurchasePriceOnSaleCurrency(contribution.getCurrency().equals(contribution.getCurrencyOnSale()) ?
                contribution.getPurchasePrice() : null);

        dto.setSalePrice(contribution.getSalesPrice());
        dto.setSaleCommissionPercentage(contribution.getSalesCommissionPercent() == null ? null :
                contribution.getSalesCommissionPercent().doubleValue());
        dto.setSaleCommissionAmount(
                (dto.getSalePrice() == null || dto.getSaleCommissionPercentage() == null) ? null :
                        dto.getSalePrice().multiply(BigDecimal.valueOf(dto.getSaleCommissionPercentage() / 100)));
        dto.setSaleNetPrice((dto.getSalePrice() == null || dto.getSaleCommissionAmount() == null) ? null :
                dto.getSalePrice().add(dto.getSaleCommissionAmount()));

        dto.setMarginPercentage((dto.getSalePrice() == null || dto.getPurchasePrice() == null) ? null :
                dto.getSalePrice().multiply(BigDecimal.valueOf(100d))
                        .divide(dto.getPurchasePrice(), RoundingMode.HALF_EVEN).doubleValue() - 100);
        dto.setMarginAmount((dto.getSalePrice() == null || dto.getPurchasePrice() == null) ? null :
                dto.getSalePrice().subtract(dto.getPurchasePrice()));

        dto.setSeller(contribution.getFile().getSalePerson().getUsername());

        dto.setSeatingF(contribution.getSeatingF());
        dto.setSeatingC(contribution.getSeatingC());
        dto.setSeatingY(contribution.getSeatingY());

        return dto;
    }

    @Mapping(target = "routeLabel", source = "line.route.label")
    @Mapping(target = "routeStartDate", source = "line.route.startDate")
    @Mapping(target = "routeEndDate", source = "line.route.endDate")
    ContributionLineDataDto mapContributionLine(LineContributionRoute line);

}
