/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamless.internal.util;

/**
 *
 * @author marembo
 */
public enum Gender {

  MALE,
  FEMALE;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
