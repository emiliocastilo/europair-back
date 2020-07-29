package com.europair.management.rest.model.common;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {

    private double latitude;

    private double longitude;

}
