package com.oracle.tas.rest.api.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceInstanceDetails")
public class ServiceInstanceDetails {

  private TasServiceType svcInstType;
  private ServiceSizeCategoryType svcInstSize;
  private String svcInstName;
  private String svcInstEntitlementId;
  private String svcInstCreditType;
  private Map<String, String> svcInstFields;
  private String svcInstJsonPayload;

  private List<AttributeValue> customAttributes;


  public void setSvcInstTypeStr(TasServiceType svcInstType) {
    this.svcInstType = svcInstType;
  }

  public void setSvcInstType(String svcInstType) {
    if (svcInstType != null) {
      this.svcInstType = TasServiceType.valueOf(svcInstType);
    }
  }

  public TasServiceType getSvcInstType() {
    return svcInstType;
  }

  public void setSvcInstSize(ServiceSizeCategoryType svcInstSize) {
    this.svcInstSize = svcInstSize;
  }

  public void setSvcInstSizeStr(String svcInstSize) {
    if (svcInstSize != null) {
      this.svcInstSize = ServiceSizeCategoryType.valueOf(svcInstSize);
    }
  }

  public ServiceSizeCategoryType getSvcInstSize() {
    return svcInstSize;
  }

  public void setSvcInstName(String svcInstName) {
    this.svcInstName = svcInstName;
  }

  public String getSvcInstName() {
    return svcInstName;
  }

  public void setSvcInstEntitlementId(String svcInstEntitlementId) {
    this.svcInstEntitlementId = svcInstEntitlementId;
  }

  public String getSvcInstEntitlementId() {
    return svcInstEntitlementId;
  }

  public void setSvcInstCreditType(String svcInstCreditType) {
    this.svcInstCreditType = svcInstCreditType;
  }

  public String getSvcInstCreditType() {
    return svcInstCreditType;
  }

  public void setSvcInstFields(Map<String, String> svcInstFields) {
    this.svcInstFields = svcInstFields;
  }

  public Map<String, String> getSvcInstFields() {
    return svcInstFields;
  }

  public void setSvcInstJsonPayload(String svcCreateJsonPayload) {
    this.svcInstJsonPayload = svcCreateJsonPayload;
  }

  public String getSvcInstJsonPayload() {
    return svcInstJsonPayload;
  }

  public void setCustomAttributes(List<AttributeValue> customAttributes) {
    this.customAttributes = customAttributes;
  }

  public List<AttributeValue> getCustomAttributes() {
    return customAttributes;
  }

  public AttributeValue[] toCustomAttributesArray() {
    AttributeValue[] ret = null;
    if (customAttributes != null) {
      ret =
          customAttributes.toArray(new AttributeValue[customAttributes.size()]);
    }
    return ret;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ServiceInstanceDetails)) {
      return false;
    }
    final ServiceInstanceDetails other = (ServiceInstanceDetails)object;
    if (!(svcInstType == null ? other.svcInstType == null :
          svcInstType.equals(other.svcInstType))) {
      return false;
    }
    if (!(svcInstSize == null ? other.svcInstSize == null :
          svcInstSize.equals(other.svcInstSize))) {
      return false;
    }
    if (!(svcInstName == null ? other.svcInstName == null :
          svcInstName.equals(other.svcInstName))) {
      return false;
    }
    if (!(svcInstEntitlementId == null ? other.svcInstEntitlementId == null :
          svcInstEntitlementId.equals(other.svcInstEntitlementId))) {
      return false;
    }
    if (!(svcInstCreditType == null ? other.svcInstCreditType == null :
          svcInstCreditType.equals(other.svcInstCreditType))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int PRIME = 37;
    int result = 1;
    result =
        PRIME * result + ((svcInstType == null) ? 0 : svcInstType.hashCode());
    result =
        PRIME * result + ((svcInstSize == null) ? 0 : svcInstSize.hashCode());
    result =
        PRIME * result + ((svcInstName == null) ? 0 : svcInstName.hashCode());
    result =
        PRIME * result + ((svcInstEntitlementId == null) ? 0 : svcInstEntitlementId.hashCode());
    result =
        PRIME * result + ((svcInstCreditType == null) ? 0 : svcInstCreditType.hashCode());
    return result;
  }


}
