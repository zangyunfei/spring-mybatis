package com.zyf.springmybatis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SpringJdbcBase {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public String insert(final String sql, final Object[] obj) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				for (int i = 1; i <= obj.length; i++) {
					if (obj[i - 1] instanceof String) {
						ps.setString(i, (String) obj[i - 1]);
					} else if (obj[i - 1] instanceof Integer) {
						ps.setInt(i, (Integer) obj[i - 1]);
					}

				}

				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().toString();
	}
}
