package com.budget.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.budget.UiEntity.GraphResponse;
import com.budget.service.BudgetItemService;
import com.budget.service.CategoryService;
import com.budget.service.TrendsService;
import com.budget.validators.AjaxDataRequestValidator;

@Controller
public class TrendsController {

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private TrendsService trendsService;

    @Autowired
    private AjaxDataRequestValidator validator;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"/trends"})
    public String trends(Model model) {
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "trends/index";
    }

    @GetMapping(value = {"/trends/data"}, produces = "application/json")
    public @ResponseBody ResponseEntity<GraphResponse> 
    getData(@RequestParam("category") String categoryName,
            @RequestParam("date") int dateFrom) {

        if (!validator.validDataRequest(categoryName, dateFrom)) {
            return new ResponseEntity<GraphResponse>(HttpStatus.BAD_REQUEST);
        }
        
        GraphResponse response = trendsService.getGraphData(categoryName, dateFrom);
        
        return ResponseEntity.ok(response);
    }
    
}
