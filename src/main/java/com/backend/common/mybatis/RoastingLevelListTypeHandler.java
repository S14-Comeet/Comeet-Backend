package com.backend.common.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.backend.domain.bean.enums.RoastingLevel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * MySQL JSON Array 컬럼을 List<RoastingLevel>로 매핑하는 TypeHandler
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class RoastingLevelListTypeHandler extends BaseTypeHandler<List<RoastingLevel>> {

	private static final Gson gson = new Gson();

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<RoastingLevel> parameter, JdbcType jdbcType)
		throws SQLException {
		List<String> stringList = parameter.stream()
			.map(RoastingLevel::name)
			.toList();
		ps.setString(i, gson.toJson(stringList));
	}

	@Override
	public List<RoastingLevel> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return parseJson(rs.getString(columnName));
	}

	@Override
	public List<RoastingLevel> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return parseJson(rs.getString(columnIndex));
	}

	@Override
	public List<RoastingLevel> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return parseJson(cs.getString(columnIndex));
	}

	private List<RoastingLevel> parseJson(String json) {
		if (json == null || json.isBlank()) {
			return new ArrayList<>();
		}
		List<String> stringList = gson.fromJson(json, new TypeToken<List<String>>() {}.getType());
		return stringList.stream()
			.map(RoastingLevel::valueOf)
			.toList();
	}
}
