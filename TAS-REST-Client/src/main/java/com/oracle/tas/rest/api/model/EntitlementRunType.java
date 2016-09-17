package com.oracle.tas.rest.api.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Entitlment Run Type
 */
@XmlType(name = "entitlementRunType")
@XmlEnum
public enum EntitlementRunType {
  NO_RUN,
  SRVC_INSTANCE,
  SRVC_ENTITLEMENT,
  CLOUD_CREDIT,
  RE_RUN_LATEST,
  RE_RUN_ORDER_ID;

  public String value() {
    return name();
  }

  public boolean isNoRun() {
    return this == NO_RUN;
  }

  public boolean isSrvcInstance() {
    return this == SRVC_INSTANCE;
  }

  public boolean isSrvcEntitlement() {
    return this == SRVC_ENTITLEMENT;
  }

  public boolean isCloudCredit() {
    return this == CLOUD_CREDIT;
  }

  public boolean isRerunLatest() {
    return this == RE_RUN_LATEST;
  }

  public boolean isRerunOrderId() {
    return this == RE_RUN_ORDER_ID;
  }

  public boolean isEntitlementRun() {
    return isSrvcEntitlement() || isCloudCredit();
  }

  public boolean isNewRun() {
    return isEntitlementRun() || isSrvcInstance();
  }

  public boolean isRerun() {
    return isRerunLatest() || isRerunOrderId();
  }

  public static EntitlementRunType fromValue(String v) {
    return valueOf(v);
  }

}

