CREATE TABLE CACTUS_SAFI.SWB_CAMPANIA_URL
(	
    COD_URL         NUMBER(19) GENERATED ALWAYS AS IDENTITY,    
    DES_URL         VARCHAR2(500), 
	DES_HASH        VARCHAR2(255), 
	FEC_CREACION    DATE, 
	FEC_EXPIRACION  DATE, 
	IND_EXITO           NUMBER(1,0) DEFAULT 0 
)
tablespace CACTUS_SAFI
pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
);

-- Add comments to the table 
comment on table CACTUS_SAFI.SWB_CAMPANIA_URL is 'Tabla para registrar las url acortadas y notificadas';
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.COD_URL is 'Identificador unico';
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.DES_URL is 'Descripción de la URL original';
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.DES_HASH is 'Descripción de la clave asociada a la url';    
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.FEC_CREACION is 'Fecha de creación';
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.FEC_EXPIRACION is 'Fecha de expiración';
comment on column CACTUS_SAFI.SWB_CAMPANIA_URL.IND_EXITO is 'Indice de uso.';


-- Create/Recreate primary, unique and foreign key constraints 
alter table CACTUS_SAFI.SWB_CAMPANIA_URL
add constraint SWB_CAMPANIA_URL_PK primary key (COD_URL)
using index 
tablespace CACTUS_SAFI
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
); 

-- Grant/Revoke object privileges 
grant select, insert, update, delete on CACTUS_SAFI.SWB_CAMPANIA_URL to WEBUSER;