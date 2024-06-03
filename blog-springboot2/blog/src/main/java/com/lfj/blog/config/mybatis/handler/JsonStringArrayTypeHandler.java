package com.lfj.blog.config.mybatis.handler;


import com.alibaba.fastjson2.JSON;
import com.lfj.blog.service.vo.ChatVo;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: LFJ
 * @Date: 2024-06-02 18:17
 */


@MappedTypes(value = {List.class})
@MappedJdbcTypes(value = JdbcType.VARCHAR)
public class JsonStringArrayTypeHandler extends BaseTypeHandler<List<ChatVo>> {

	/**
	 * 设置非空参数
	 *
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<ChatVo> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, JSON.toJSONString(parameter));
	}

	/**
	 * 根据列名，获取可以为空的结果
	 *
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	@Override
	@SneakyThrows
	public List<ChatVo> getNullableResult(ResultSet rs, String columnName) {
		String reString = rs.getString(columnName);
		return JSON.parseArray(reString, ChatVo.class);
	}

	/**
	 * 根据列索引，获取可以为内控的接口
	 *
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	@Override
	@SneakyThrows
	public List<ChatVo> getNullableResult(ResultSet rs, int columnIndex) {
		String reString = rs.getString(columnIndex);
		return JSON.parseArray(reString, ChatVo.class);
	}

	/**
	 * @param cs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	@Override
	@SneakyThrows
	public List<ChatVo> getNullableResult(CallableStatement cs, int columnIndex) {
		String reString = cs.getString(columnIndex);
		return JSON.parseArray(reString, ChatVo.class);
	}
}
