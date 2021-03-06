package com.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.budget.UiEntity.SpendingHistoryItem;
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
    class SpendingHistoryMapper implements RowMapper<SpendingHistoryItem> {

        @Override
        public SpendingHistoryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            SpendingHistoryItem spendingHistoryItem = new SpendingHistoryItem();
            spendingHistoryItem.setTotalSpending((int)rs.getDouble("total"));
            spendingHistoryItem.setMonthName(rs.getDate(("monthYear")));
            return spendingHistoryItem;
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

    @Override
    public boolean expenseExists(Expense expenseToValidate) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? "
                   + " AND id = ?";
        try {
            Expense expense = jdbcTemp.queryForObject(sql,
                                 new Object[]{expenseToValidate.getUser_id(),
                                              expenseToValidate.getId()},
                                 new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteExpenseById(int id) {
        String sql = "DELETE FROM expense "
                   + " WHERE id = ?";
        try {
            jdbcTemp.update(sql, new Object[]{id});
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public List<Expense> getExpensesForCategoryAfterDateForUser(Category category, Timestamp dateFrom, User user) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? "
                   + " AND date > ?";

        List<Object> args = new ArrayList<>();
        args.add(user.getId());
        args.add(dateFrom);

        if (category != null) {
            sql += " AND category_id = ?";
            args.add(category.getId());
        }

        List<Expense> expenses = null;
        try {
            expenses = jdbcTemp.query(sql, args.toArray(), new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        if (expenses == null) {
            return new ArrayList<Expense>();
        }
       return expenses;
    }

    @Override
    public List<Expense> getAllExpensesForUser(User currentUser) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? ";
        List<Expense> expenses = null;
        try {
            expenses = jdbcTemp.query(sql,
                         new Object[]{currentUser.getId()},
                         new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            return new ArrayList<Expense>();
        }
        return expenses;
        
    }

    @Override
    public List<SpendingHistoryItem> getExpenseMonthSummary(User currentUser) {
        String sql = "SELECT SUM(amount) AS total, "
                   + " DATE_FORMAT(date, '%Y-%m-01') AS monthYear "
                   + " FROM expense "
                   + " WHERE user_id = ? "
                   + " GROUP BY DATE_FORMAT(date, '%Y-%m-01')"
                   + " ORDER BY monthYear DESC";

        List<SpendingHistoryItem> spendingHistory = null;
        try {
            spendingHistory = jdbcTemp.query(sql,
                              new Object[]{currentUser.getId()},
                              new SpendingHistoryMapper());
            
        } catch (DataAccessException ex) {
            return new ArrayList<SpendingHistoryItem>();
        }
        return spendingHistory;
    }

    @Override
    public boolean userHasExpenseForDate(User currentUser, int month, int year) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? "
                   + " AND MONTH(date) = ?"
                   + " AND YEAR(date) = ?";
        
        try {
            jdbcTemp.query(sql, new Object[]{currentUser.getId(), month, year},
                           new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            return false;
        }

        return true;
    }

    @Override
    public List<Expense> getExpensesFromMonthYear(User currentUser, int month, int year) {
        String sql = "SELECT * FROM expense "
                   + " WHERE user_id = ? "
                   + " AND MONTH(date) = ?"
                   + " AND YEAR(date) = ?";
        
        List<Expense> expenses = null;
        try {
            expenses = jdbcTemp.query(sql, new Object[]{currentUser.getId(), month, year},
                                     new ExpenseRowMapper());
            
        } catch (DataAccessException ex) {
            return new ArrayList<Expense>();
        }

        return expenses;
    }
    
}
