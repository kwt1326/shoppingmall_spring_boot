package com.kwtproject.shoppingmall.repository.user;

import com.kwtproject.shoppingmall.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTempleteUserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired /* 생성자가 하나일 경우 Autowired  생략 가능하다. */
    public JdbcTempleteUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserEntity save(UserEntity user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        List<UserEntity> result = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        List<UserEntity> result = jdbcTemplate.query("select * from user where name = ?", userRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public Optional<UserEntity> findByUserName(String username) {
        List<UserEntity> result = jdbcTemplate.query("select * from user where username = ?", userRowMapper(), username);
        return result.stream().findAny();
    }

    @Override
    public List<UserEntity> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    private RowMapper<UserEntity> userRowMapper() {
        return (rs, rowNum) -> {
            UserEntity user = UserEntity.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .build();
            return user;
        };
//        return new RowMapper<User>() { /* 람다로 축 가능 */
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setName(rs.getString("name"));
//                return user;
//            }
//        };
    }
}
