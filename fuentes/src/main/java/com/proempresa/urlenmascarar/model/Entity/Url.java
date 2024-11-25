package com.proempresa.urlenmascarar.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "SWB_CAMPANIA_URL", schema = "CACTUS_SAFI")
public class Url {

    @Id
    @Column(name = "COD_URL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "DES_URL")
    private String longUrl;

    @Column(name = "DES_HASH")
    private String hash;

    @Column(name = "IND_EXITO")
    private int exito;

    @Column(name = "FEC_CREACION")
    private Date createdDate;

    @Column(name = "FEC_EXPIRACION")
    private Date expiresDate;
}
  