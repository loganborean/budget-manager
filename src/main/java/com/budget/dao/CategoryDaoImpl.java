package com.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.budget.entity.Category;

@Repository
@Qualifier("MySql")
public class CategoryDaoImpl implements CategoryDao {
    
    @Autowired
    private JdbcTemplate jdbcTemp;
    
    private static class CategoryRowMapper implements RowMapper<Category> {
        
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category user = new Category();
            user.setId(resultSet.getInt("id"));
            user.setUser_id(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            return user;
        }
    }

    @Override
    public boolean categoryExists(Category categoryToFind) {
        String sql = "SELECT * FROM category "
                   + " WHERE user_id = ? "
                   + " AND name = ? ";
        try {
            Category cat =  jdbcTemp.queryForObject(sql,
                            new Object[]{categoryToFind.getName(),
                                         categoryToFind.getUser_id()},
                            new CategoryRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }
        return true;

        
    }

}
