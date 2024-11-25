package com.proempresa.urlenmascarar.model.repository.impl;

import com.proempresa.urlenmascarar.config.RestProperties;
import com.proempresa.urlenmascarar.model.Entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Map;

@Repository
public class UrlRepositoryImpl {

    private RestProperties restProperties;
    @Autowired
    public void RestService(RestProperties restProperties) {
        this.restProperties = restProperties;
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    public int actualizarRegistro(Url url) {

        String longId = url.getLongUrl();
        String hash = url.getHash();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(restProperties.getSchemaName())
                .withProcedureName("SP_SWB_CAMPANIA_URL_ACTUALIZAR")
                .declareParameters( new SqlParameter[] {
                        new SqlParameter("vi_des_url", Types.VARCHAR),
                        new SqlParameter("vi_des_hash", Types.VARCHAR),
                        new SqlOutParameter("vo_resultado", Types.INTEGER)
                });

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("vi_des_url", longId);
        mapSqlParameterSource.addValue("vi_des_hash", hash);

        Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);
        return (int) result.get("vo_resultado");
    }
}
