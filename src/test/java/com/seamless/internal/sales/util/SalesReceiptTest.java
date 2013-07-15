/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.sales.util;

import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marembo
 */
public class SalesReceiptTest {

  public SalesReceiptTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testToString() {
    Calendar date = Calendar.getInstance();
    date.set(2012, 04, 23);
    int id = 3;
    SalesReceipt sr = new SalesReceipt(date, id);
    String expected = "20120423/000003";
    String actual = sr.toString();
    assertEquals(expected, actual);
  }
}