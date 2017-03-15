package com.budget.UiEntity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SpendingHistoryItem {

    private String monthName;
    private int totalSpending;
    private String tileColor;
    
    
    public String getMonthName() {
        return monthName;
    }
    public void setMonthName(Date date) {
        Format formatter = new SimpleDateFormat("MMMM YYYY"); 
        //this.setTileColor();
        this.monthName = formatter.format(date);
    }
    public int getTotalSpending() {
        return totalSpending;
    }
    public void setTotalSpending(int totalSpending) {
        this.totalSpending = totalSpending;
    }
    
    private void setTileColor() {
        Scanner scan = new Scanner(monthName);
        String onlyMonth = scan.next();
        
        if (onlyMonth.equals("January")) {
            
        } else if (onlyMonth.equals("February")) {

        }else if (onlyMonth.equals("March")) {

        }else if (onlyMonth.equals("April")) {

        }else if (onlyMonth.equals("June")) {

        }else if (onlyMonth.equals("July")) {

        }else if (onlyMonth.equals("August")) {

        }else if (onlyMonth.equals("September")) {

        }else if (onlyMonth.equals("October")) {

        }else if (onlyMonth.equals("November")) {

        }else if (onlyMonth.equals("December")) {

        }











            
        
    }
    
}
