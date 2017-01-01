package com.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.budget.entity.Category;
import com.budget.entity.Expense;
import com.budget.entity.User;

@Repository
@Qualifier("MySql")
public class ExpenseDaoImpl implements ExpenseDao {

    @Autowired
    private JdbcTemplate jdbcTemp;

    class ExpenseRowMapper implements RowMapper<Expense> {
        
        @Override
        public Expense mapRow(ResultSet resultSet, int i) throws SQLException {
            Expense expense = new Expense();
            expense.setId(resultSet.getInt("id"));
            expense.setUser_id(resultSet.getInt("user_id"));
            expense.setAmount(resultSet.getDouble("amount"));
            expense.setCategory(mapCategoryById((resultSet.getInt("category_id"))));
            expense.setNote(resultSet.getString("note"));
            expense.setDate(resultSet.getDate(("date")));
            return expense;
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
    public void insertExpense(Expense expense) {
        String sql = "INSERT INTO expense (user_id, category_id, amount, note) "
                   + " values (?, ?, ?, ?)";
        try {
            jdbcTemp.update(sql, new Object[]{expense.getUser_id(),
                                              expense.getCategory().getId(),
                                              expense.getAmount(),
                                              expense.getNote()});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public List<Expense> getExpensesForUserAfterDate(User currentUser, Timestamp firstDayOfMonth) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? "
                   + " AND date > ?";
        List<Expense> expenses = null;
        try {
            expenses = jdbcTemp.query(sql,
                         new Object[]{currentUser.getId(), firstDayOfMonth},
                         new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            return new ArrayList<Expense>();
        }
        return expenses;
    }
    
}
