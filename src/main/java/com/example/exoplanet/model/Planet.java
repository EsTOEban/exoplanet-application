package com.example.exoplanet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 */
@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("pl_hostname")
    private String hostName;

    @JsonProperty("pl_name")
    private String name;

    @JsonProperty("pl_discmethod")
    private String discMethod;

    @JsonProperty("pl_controvflag")
    private String controvFlag;

    @JsonProperty("rowupdate")
    private String rowUpdate;

    @JsonProperty("pl_facility")
    private String facility;

    @JsonProperty("pl_disc")
    private String disc;

    @JsonProperty("pl_locale")
    private String locale;

    @JsonProperty("pl_disc_refname")
    private String discRefName;

    @JsonProperty("pl_telescope")
    private String telescope;

    @JsonProperty("pl_instrument")
    private String instrument;

    @JsonProperty("pl_status")
    private String status;
}
