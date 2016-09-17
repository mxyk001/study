package com.oracle.tas.rest.api.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Type of OPC Service
 */
@XmlType(name = "tasServiceType")
@XmlEnum
public enum TasServiceType {
  APAAS("APAAS"),
  APEX("Database"),
  APM("APM"),
  ARCS("ARCS"),
  AppBuilder("AppBuilder"),
  BAREMETALMB("BAREMETALMB"),
  BDCS("BDCS"),
  BDDCS("BDDCS"),
  BI("BI"),
  BIGMB("BIGMB"),
  BMI("BMI"),
  BigDataAppliance("BigDataAppliance"),
  CILEGACY("CILEGACY"),
  CLOUDMB("CLOUDMB"),
  CLT("CLT"),
  COMPUTEBAREMETAL("COMPUTEBAREMETAL"),
  CRM("CRM"),
  Commerce("Commerce"),
  Compute("Compute"),
  DBAAS("DBAAS"),
  DBBCS("DBBCS"),
  DBMB("DBMB"),
  DVCS("DVCS"),
  Data("Data"),
  DataEnrichment("DataEnrichment"),
  Developer("Developer"),
  Documents("Documents"),
  ELOQUA("ELOQUA"),
  EPRCS("EPRCS"),
  ERP("ERP"),
  ESSCS("ESSCS"),
  Exadata("Exadata"),
  HCM("HCM"),
  IAASMB("IAASMB"),
  INVCI("INVCI"),
  ITAnalytics("ITAnalytics"),
  Integration("Integration"),
  IoT("IoT"),
  JAAS("JAAS"),
  JAVA("JAVA"),
  JAVAMB("JAVAMB"),
  LogAnalytics("LogAnalytics"),
  Messaging("Messaging"),
  MobileEnvironment("MobileEnvironment"),
  MobilePortal("MobilePortal"),
  OSM("OSM"),
  OTBIE("OTBIE"),
  OTMGTM("OTMGTM"),
  PRIMAVERA("PRIMAVERA"),
  Planning("Planning"),
  Process("Process"),
  RESPONSYS("RESPONSYS"),
  RNOW("RNOW"),
  SKIRELEGACY("SKIRELEGACY"),
  SOA("SOA"),
  SocialNetwork("SocialNetwork"),
  Storage("Storage"),
  TBE("TBE"),
  TEE("TEE"),
  TLE("TLE"),
  VITRUELEGACY("VITRUELEGACY");


  private String displayName;
  private static final String METERED_BASED_SUFIX = "MB";

  TasServiceType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String value() {
    return name();
  }

  public static TasServiceType fromValue(String v) {
    return valueOf(v);
  }

  public static TasServiceType findValue(String val, boolean failIfNull) {
    TasServiceType retSvcType = null;
    for (TasServiceType svcT : TasServiceType.values()) {
      if (svcT.name().equalsIgnoreCase(val) ||
          svcT.displayName.equalsIgnoreCase(val)) {
        retSvcType = svcT;
        break;
      }
    }
    if (failIfNull && retSvcType == null) {
      throw new RuntimeException("Service Type Not Recognized : " + val +
                                 " Recognized are : " +
                                 Arrays.toString(TasServiceType.values()));
    }
    return retSvcType;
  }

  @Override
  public String toString() {
    return name();
  }

  public boolean isMetered() {
    return displayName.endsWith(METERED_BASED_SUFIX);
  }

  public boolean isPsmBasedProvisioning() {
    return this == APAAS || this == JAAS || this == DBAAS || this == SOA ||
      this == DBBCS || this == BDCS;
  }
}
