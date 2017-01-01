package com.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.budget.entity.Category;
import com.budget.entity.User;

@Repository
@Qualifier("MySql")
public class CategoryDaoImpl implements CategoryDao {
    
    @Autowired
    private JdbcTemplate jdbcTemp;
    
    static class CategoryRowMapper implements RowMapper<Category> {
        
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setUser_id(resultSet.getInt("user_id"));
            category.setName(resultSet.getString("name"));
            return category;
        }
    }

    @Override
    public boolean categoryExists(Category categoryToFind) {
        String sql = "SELECT * FROM category "
                   + " WHERE user_id = ? "
                   + " AND name = ? ";
        try {
            jdbcTemp.queryForObject(sql,
                    new Object[]{categoryToFind.getUser_id(),
                                 categoryToFind.getName()},
                    new CategoryRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }
        return true;

        
    }

    @Override
    public void insertCategory(Category category) {
        String sql = "INSERT INTO category (user_id, name) "
                   + " values (?, ?)";
        try {
            jdbcTemp.update(sql, new Object[]{category.getUser_id(),
                                              category.getName()});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category "
                   + " WHERE id = ? ";
        Category category = null;
        try {
            category = jdbcTemp.queryForObject(sql,
                            new Object[]{id},
                            new CategoryRowMapper());
            
        } catch (DataAccessException ex) {
            return null;
        }
        return category;
    }
    @Override
    public List<Category> getAllCategoriesForUser(User user) {
        String sql = "SELECT * FROM category "
                   + " WHERE user_id = ? ";
        List<Category> categories = null;
        try {
            categories = jdbcTemp.query(sql,
                         new Object[]{user.getId()},
                         new CategoryRowMapper());
            
        } catch (DataAccessException ex) {
            return new ArrayList<Category>();
        }
        return categories;
        
    }
    
    
    @Override
    public List<Category> getAllCategoriesBudgetedForUser(User user) {
        String sql = "SELECT * FROM category "
                   + " WHERE user_id = ? "
                   + " AND EXISTS "
                           + "(SELECT * FROM budget_item "
                           + "WHERE category.id = budget_item.category_id) ";
        List<Category> categoriedBudgetedFor = null;
        try {
             categoriedBudgetedFor = jdbcTemp.query(sql,
                            new Object[]{user.getId()},
                            new CategoryRowMapper());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        return categoriedBudgetedFor;
    }

}
