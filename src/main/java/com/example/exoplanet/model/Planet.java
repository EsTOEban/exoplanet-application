package com.example.exoplanet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * {
 * "pl_hostname": "HD 142022 A",
 * "pl_name": "HD 142022 A b",
 * "pl_discmethod": "Radial Velocity",
 * "pl_controvflag": 0,
 * "rowupdate": "2018-09-06",
 * "pl_facility": "La Silla Observatory",
 * "pl_disc": 2005,
 * "pl_locale": "Ground",
 * "pl_disc_refname": "Eggenberger et al. 2006",
 * "pl_telescope": "1.2 m Leonhard Euler Telescope",
 * "pl_instrument": "CORALIE Spectrograph",
 * "pl_status": 3
 * }
 */
@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pl_hostname;
    private String pl_name;
    private String pl_discmethod;
    private String pl_controvflag;
    private String rowupdate;
    private String pl_facility;
    private String pl_disc;
    private String pl_locale;
    private String pl_disc_refname;
    private String pl_telescope;
    private String pl_instrument;
    private String pl_status;
}
