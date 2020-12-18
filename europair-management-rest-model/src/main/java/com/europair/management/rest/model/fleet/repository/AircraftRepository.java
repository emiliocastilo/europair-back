package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long>, IAircraftRepositoryCustom {


    @Query("select distinct aircraft from Aircraft aircraft" +
            " inner join aircraft.bases base" +
            " inner join base.airport airport" +
            " inner join airport.country country" +
            " inner join aircraft.aircraftType aircraftType" +
            " inner join aircraftType.category category" +
            " inner join aircraftType.subcategory subcategory" +
            " inner join aircraft.operator operator" +
            " where" +
            " (aircraft.removedAt is null)" +
            " and (COALESCE(:baseIds) is null or airport.id in (:baseIds))" +
            " and (COALESCE(:countryIds) is null or country.id in (:countryIds))" +
            " and (:seats is null or :seats <= (aircraft.seatingF + aircraft.seatingC + aircraft.seatingY))" +
            " and (:seatingF is null or :seatingF <= aircraft.seatingF)" +
            " and (:seatingC is null or :seatingC <= aircraft.seatingC)" +
            " and (:seatingY is null or :seatingY <= aircraft.seatingY)" +
            " and (:seatingFC is null or :seatingFC <= (aircraft.seatingF + aircraft.seatingC))" +
            " and (:beds is null or :beds <= aircraft.nighttimeConfiguration or (aircraft.nighttimeConfiguration is null and :beds <= (aircraft.seatingF + aircraft.seatingC + aircraft.seatingY)))" +
            " and (:categoryId is null or category.id = :categoryId)" +
            " and (:subcategoryId is null or (:exactSubcategory = true and subcategory.id = :subcategoryId) or (subcategory.parentCategory.id = :categoryId and subcategory.order >= :minSubcategory))" +
            " and (:ambulance is null or aircraft.ambulance = :ambulance)" +
            " and (COALESCE(:aircraftTypeIds) is null or aircraftType.id in (:aircraftTypeIds))" +
            " and (COALESCE(:operatorIds) is null or operator.id in (:operatorIds))" +
            " and (:regionId is null or (country.id in (:regionCountryIds) or airport.id in (:regionAirportIds)))" +
            " "
    )
    List<Aircraft> searchAircraft(
            @Param(value = "baseIds") Set<Long> baseIds,
            @Param(value = "countryIds") Set<Long> countryIds,
            @Param(value = "seats") Integer seats,
            @Param(value = "seatingF") Integer seatingF,
            @Param(value = "seatingC") Integer seatingC,
            @Param(value = "seatingY") Integer seatingY,
            @Param(value = "seatingFC") Integer seatingFC,
            @Param(value = "beds") Integer beds,
            @Param(value = "categoryId") Long categoryId,
            @Param(value = "subcategoryId") Long subcategoryId,
            @Param(value = "exactSubcategory") Boolean exactSubcategory,
            @Param(value = "minSubcategory") Integer minSubcategory,
            @Param(value = "ambulance") Boolean ambulance,
            @Param(value = "aircraftTypeIds") List<Long> aircraftTypeIds,
            @Param(value = "operatorIds") List<Long> operatorIds,
            @Param(value = "regionId") Long regionId,
            @Param(value = "regionAirportIds") Set<Long> regionAirportIds,
            @Param(value = "regionCountryIds") Set<Long> regionCountryIds
    );

    Set<Aircraft> findByIdIn(@NotEmpty Set<Long> ids);
}
