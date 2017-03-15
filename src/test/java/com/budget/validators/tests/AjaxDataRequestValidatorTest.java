package com.budget.validators.tests;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.budget.service.SpendingService;
import com.budget.validators.AjaxDataRequestValidator;

public class AjaxDataRequestValidatorTest {
    AjaxDataRequestValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new AjaxDataRequestValidator();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValidSpendingHistoryRequest() {
        SpendingService spendingServiceStub = mock(SpendingService.class);
        when(spendingServiceStub.userHasExpensesThisMonthAndYear(any())).thenReturn(true);
        validator.setSpendingService(spendingServiceStub);

        assertTrue(validator.validSpendingHistoryRequest("January 2017"));
        assertTrue(validator.validSpendingHistoryRequest("December 2019"));

        assertFalse(validator.validSpendingHistoryRequest("December 2019 hello"));
        assertFalse(validator.validSpendingHistoryRequest("ksdf sdlkf"));
        assertFalse(validator.validSpendingHistoryRequest("ksdf 17"));
        assertFalse(validator.validSpendingHistoryRequest("March sdlkf"));

    }

}
