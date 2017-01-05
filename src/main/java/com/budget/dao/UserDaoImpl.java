package com.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.budget.entity.User;

@Repository
@Qualifier("MySql")
public class UserDaoImpl implements UserDao {
    
    @Autowired
    private JdbcTemplate jdbcTemp;
    
    private static class UserRowMapper implements RowMapper<User> {
        
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }

    }
    
    
    @Override
    public boolean userExists(User userToVerify) {
        String sql = "SELECT * FROM user "
                   + "WHERE username = ? "
                   + "AND password = ? ";
        try {
            User user = jdbcTemp.queryForObject(sql,
                        new Object[]{userToVerify.getUsername(),
                                     userToVerify.getPassword()},
                        new UserRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }
        return true;
    }
    
    @Override
    public User findByUsername(String username) {

        String sql = "SELECT * FROM user "
                   + "WHERE username = ? ";
        User user = null;
        try {
            user = jdbcTemp.queryForObject(sql,
                   new Object[]{username},
                   new UserRowMapper());
            
        } catch (DataAccessException ex) {
            //user not found
            return null;

        }
        
        return user;
        
    }

    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO user (username, password) "
                   + " values (?, ?)";
        try {
            jdbcTemp.update(sql, new Object[]{user.getUsername(),
                                              user.getPassword()});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertDefaultCategoriesForUser(User user) {
        String sql = "INSERT INTO category (user_id, name) "
                   + " values (?, ?), (?, ?), (?, ?), (?, ?), (?, ?) ";
        try {
            jdbcTemp.update(sql, new Object[]{
                    user.getId(),
                    "Rent",
                    user.getId(),
                    "Groceries",
                    user.getId(),
                    "Gas",
                    user.getId(),
                    "Clothes",
                    user.getId(),
                    "Recreation",
            });
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
    }

}
