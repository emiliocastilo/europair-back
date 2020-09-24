package com.europair.management.rest.model.fleet.repository;

import com.europair.management.rest.model.fleet.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            " and ((:baseIds) is null or airport.id in (:baseIds))" +
            " and ((:countryIds) is null or country.id in (:countryIds))" +
            " and (:seats is null or :seats <= (aircraft.seatingF + aircraft.seatingC + aircraft.seatingY))" +
            " and (:beds is null or :beds <= aircraft.nighttimeConfiguration or (aircraft.nighttimeConfiguration is null and :beds <= (aircraft.seatingF + aircraft.seatingC + aircraft.seatingY)))" +
            " and (:categoryId is null or category.id = :categoryId)" +
            " and (:subcategoryId is null or (:exactSubcategory = true and subcategory.id = :subcategoryId) or (subcategory.parentCategory.id = :categoryId and subcategory.order >= :minSubcategory))" +
            " and (:ambulance is null or aircraft.ambulance = :ambulance)" +
            " and ((:aircraftTypeIds) is null or aircraftType.id in (:aircraftTypeIds))" +
            " and ((:operatorIds) is null or operator.id in (:operatorIds))"
    )
    List<Aircraft> searchAircraft(
            @Param(value = "baseIds") List<Long> baseIds,
            @Param(value = "countryIds") List<Long> countryIds,
            @Param(value = "seats") Integer seats,
            @Param(value = "beds") Integer beds,
            @Param(value = "categoryId") Long categoryId,
            @Param(value = "subcategoryId") Long subcategoryId,
            @Param(value = "exactSubcategory") Boolean exactSubcategory,
            @Param(value = "minSubcategory") Integer minSubcategory,
            @Param(value = "ambulance") Boolean ambulance,
            @Param(value = "aircraftTypeIds") List<Long> aircraftTypeIds,
            @Param(value = "operatorIds") List<Long> operatorIds
    );

    @Query("select distinct aircraft from Aircraft aircraft" +
            " inner join aircraft.bases base" +
            " inner join base.airport airport" +
            " inner join airport.country country" +
            " where" +
            " (aircraft.removedAt is null)" +
            " and ((:baseIds) is null or airport.id in (:baseIds))" +
            " and ((:countryIds) is null or country.id in (:countryIds))"
    )
    List<Aircraft> test(
            @Param(value = "baseIds") List<Long> baseIds,
            @Param(value = "countryIds") List<Long> countryIds
//            ,@Param(value = "regionId") Long regionId
    );


}
