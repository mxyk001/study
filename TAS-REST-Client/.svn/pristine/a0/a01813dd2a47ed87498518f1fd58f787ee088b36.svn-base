package com.oracle.tas.rest.api.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Size of the Service Requested in the Order
 */
@XmlType(name = "serviceSizeCategoryType")
@XmlEnum
public enum ServiceSizeCategoryType {
  MINI,
  BASIC,
  STANDARD,
  ENTERPRISE,
  TRIAL,
  CUSTOM;

  public String value() {
    return name();
  }

  public static ServiceSizeCategoryType fromValue(String v) {
    return valueOf(v);
  }


  public static ServiceSizeCategoryType findValue(String val,
                                                  boolean failIfNull) {
    ServiceSizeCategoryType retSvcCatType = null;
    for (ServiceSizeCategoryType svcT : ServiceSizeCategoryType.values()) {
      if (svcT.name().equalsIgnoreCase(val)) {
        retSvcCatType = svcT;
        break;
      }
    }
    if (failIfNull && retSvcCatType == null) {
      throw new RuntimeException("Service Category Type Not Recognized : " +
                                 val + " Recognized are : " +
                                 Arrays.toString(ServiceSizeCategoryType.values()));
    }
    return retSvcCatType;
  }

  @Override
  public String toString() {
    return name();
  }
}
