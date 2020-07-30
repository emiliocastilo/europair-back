package com.europair.management.rest.model.airport.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "terminals")
@Data
public class Terminal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Airport airport;

    @Column(unique = true)
    private String code;

    private String name;

    private String comments;

}
