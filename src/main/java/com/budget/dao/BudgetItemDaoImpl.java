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

import com.budget.entity.BudgetItem;
import com.budget.entity.Category;
import com.budget.entity.User;

@Repository
@Qualifier("MySql")
public class BudgetItemDaoImpl implements BudgetItemDao {

    @Autowired
    private JdbcTemplate jdbcTemp;
    
    private class BudgetItemRowMapper implements RowMapper<BudgetItem> {
        
        @Override
        public BudgetItem mapRow(ResultSet resultSet, int i) throws SQLException {
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setId(resultSet.getInt("id"));
            budgetItem.setUser_id(resultSet.getInt("user_id"));
            budgetItem.setCategory(mapCategoryById((resultSet.getInt("category_id"))));
            budgetItem.setAmount(resultSet.getDouble("amount"));
            budgetItem.setTime(resultSet.getTime("time"));
            return budgetItem;
        }
        
        public Category mapCategoryById(int id) {
            String sql = "SELECT * FROM category "
                       + " WHERE id = ? ";
            Category category = null;
            try {
                category = jdbcTemp.queryForObject(sql,
                                new Object[]{id}, new CategoryDaoImpl.CategoryRowMapper());
                
            } catch (DataAccessException ex) {
                return null;
            }
            return category;
        }
    }


    @Override
    public boolean budgetForCategoryExists(Category category) {
        String sql = "SELECT * FROM budget_item "
                   + " WHERE category_id = ? ";
        try {
            jdbcTemp.queryForObject(sql,
                    new Object[]{category.getId()},
                    new BudgetItemRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void insertBudgetItem(BudgetItem item) {
        String sql = "INSERT INTO budget_item (user_id, category_id, amount) "
                   + " values (?, ?, ?)";
        try {
            jdbcTemp.update(sql, new Object[]{item.getUser_id(),
                                              item.getCategory().getId(),
                                              item.getAmount()});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public List<BudgetItem> getAllBudgetItemsForUser(User user) {
        String sql = "SELECT * FROM budget_item "
                   + " WHERE user_id = ? ";
        List<BudgetItem> budgetItems = null;
        try {
            budgetItems = jdbcTemp.query(sql,
                            new Object[]{user.getId()},
                            new BudgetItemRowMapper());
        } catch (DataAccessException ex) {
            return new ArrayList<BudgetItem>();
        }
        return budgetItems;
    }

    @Override
    public boolean budgetItemForUserExists(BudgetItem item) {
        String sql = "SELECT * FROM budget_item "
                   + " WHERE user_id = ? "
                   + " AND id = ? ";
        try {
            jdbcTemp.queryForObject(sql,
                new Object[]{item.getUser_id(), item.getId()},
                new BudgetItemRowMapper());
        } catch (DataAccessException ex) {
            return false;
        }
        return true;
    }

    @Override
    public BudgetItem getBudgetById(int budgetItemId) {
        String sql = "SELECT * FROM budget_item "
                   + " WHERE id = ? ";
        BudgetItem budgetItem = null;
        try {
             budgetItem = jdbcTemp.queryForObject(sql,
                            new Object[]{budgetItemId},
                            new BudgetItemRowMapper());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        return budgetItem;
    }

    @Override
    public void updateBudgetItemAmount(BudgetItem item) {
        String sql = "UPDATE budget_item "
                   + " SET amount = ? "
                   + " WHERE id = ?";
        try {
            jdbcTemp.update(sql, new Object[]{item.getAmount(),
                                              item.getId()});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void deleteBudgetItemById(int budgetItemId) {
        String sql = "DELETE FROM budget_item "
                   + " WHERE id = ?";
        try {
            jdbcTemp.update(sql, new Object[]{budgetItemId});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }

        
    }


}
