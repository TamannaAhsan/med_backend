package org.tamu.medbackend.utils;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

import static org.tamu.medbackend.utils.JsonUtils.toSimpleJsonObject;

@Component
@RequiredArgsConstructor
public class SQLUtility {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<JSONObject> buildJsonResult(String sql, MapSqlParameterSource param) throws RuntimeException{
        try {
            List<JSONObject> dataList = this.namedParameterJdbcTemplate.query(sql, param, (ResultSet rs, int rowNum) -> {
                try {
                    return toSimpleJsonObject(rs.getString("obj"));
                }catch (RuntimeException e ){

                }
                return null;
            });
            return dataList;

        } catch (Exception e) {
            throw new RuntimeException("errors.happens");
        }
    }

}
