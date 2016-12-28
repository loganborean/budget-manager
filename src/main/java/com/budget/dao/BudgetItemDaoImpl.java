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
    
    private static class BudgetItemRowMapper implements RowMapper<BudgetItem> {
        
        @Override
        public BudgetItem mapRow(ResultSet resultSet, int i) throws SQLException {
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setId(resultSet.getInt("id"));
            budgetItem.setUser_id(resultSet.getInt("user_id"));
            budgetItem.setCategory_id(resultSet.getInt("category_id"));
            budgetItem.setAmount(resultSet.getDouble("amount"));
            budgetItem.setTime(resultSet.getTime("time"));
            return budgetItem;
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
                                              item.getCategory_id(),
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



}
