package com.oracle.saas.qa.tas.rest.client;


import com.oracle.cim.rest.api.model.Instance;
import com.oracle.cim.rest.api.model.Link;
import com.oracle.cim.rest.api.model.Request;
import com.oracle.saas.qa.cim.rest.client.CommonInstanceMgrRestClient;
import com.oracle.saas.qa.cim.rest.client.CommonInstanceMgrRestFactory;
import com.oracle.saas.qa.lib.RedisParamsDataProvider;
import com.oracle.saas.qa.lib.TestConstants;
import com.oracle.saas.qa.lib.keys.CIM;
import com.oracle.saas.qa.lib.keys.SSO;
import com.oracle.saas.qa.lib.keys.TAS;
import com.oracle.saas.qa.lib.mail.OpcActivationEmail;
import com.oracle.saas.qa.lib.mail.OpcInstanceReadyEmail;
import com.oracle.saas.qa.lib.mail.OpcJavaMailUtil;
import com.oracle.saas.qa.lib.mail.OpcProvisioningCompleteEmail;
import com.oracle.tas.rest.api.model.Account;
import com.oracle.tas.rest.api.model.AttributeValue;
import com.oracle.tas.rest.api.model.EntitlementRunType;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.OrderItem;
import com.oracle.tas.rest.api.model.ServiceInstanceDetails;
import com.oracle.tas.rest.api.model.ServiceInstanceEndPoint;
import com.oracle.tas.rest.api.model.ServiceSizeCategoryType;
import com.oracle.tas.rest.api.model.Subscription;
import com.oracle.tas.rest.api.model.TasServiceType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;


/**
 * Manages Provisioning Job Run.
 *
 * @author Haris Shah
 */
public final class TasProvisioningRunManager {

  private static Logger logger =
    Logger.getLogger(TasProvisioningRunManager.class.getName());
  private File workingDir;
  private File tasProvisioningPropsFile;
  private File tasProvisioningSummaryPropsFile;

  private Properties tasProvisioningProps;
  private Properties tasProvisioningSummaryProps;

  private TasCommonRestUtil util;

  private static String VALS_SEP = ",";

  public enum TasProvisioningProperties {

    LAB_NAME("lab.env"),
    DATA_CENTER("DC"),
    ENTITLEMENT_RUN_TYPE("entitlement.run.type"),
    SERVICES_INSTANCES_DETAILS_JSON("svcs.instances.details.json"),
    CUSTOM_ATTRIBUTES_JSON("custom.attributes.json"),
    ALL_TEST_METHODS("all.test.methods"),
    COMPLETED_TEST_METHODS("completed.test.methods"),
    SKIP_TEST_METHODS("skip.test.methods"),
    ORDER_ID("order.id"),
    CREDIT_TYPES("credit.types"),
    ADMIN_USERID("admin.userid"),
    ADMIN_PASSWORD("admin.password"),
    ACCOUNT_NAME("account.name"),
    IDENTITY_DOMAIN_NAME("identity.domain.name"),
    SEED_ORDER_JSON("seed.order.json"),
    PROVISIONED_ORDER_JSON("provisioned.order.json"),
    ACTIVATION_EMAIL_JSON("activation.email.json"),
    PROVISIONING_EMAIL_JSON("provisioning.email.json"),
    INSTANCE_READY_EMAIL_JSON("instance.ready.email.json"),
    ACCOUNT_JSON("account.json"),
    SUBSCRIPTIONS_JSON("subscriptions.json"),
    CIM_SERVICE_REQUEST_JSON("cim.svc.req.json"),
    CIM_SERVICE_INSTANCE_JSON("cim.svc.inst.json"),
    MCS_USER_SEQ("mcs.user.seq"),
    SUMMARY_PROPS_FILE_NAME("summary.props.file.name"),
    AUTO_COMPLETION_ENABLED("auto.completion.enabled");

    private String val;

    TasProvisioningProperties(String val) {
      this.val = val;
    }

    String v() {
      return val;
    }

    static String getProperty(Properties tasOrderProps, File tasOrderPropsFile,
                              TasProvisioningProperties orderProp,
                              boolean failIfNull) {
      StringBuilder err = new StringBuilder();
      String theProp = null;
      if (orderProp == null) {
        err.append("orderProp Parameter cannot be NULL");
      } else {
        theProp = tasOrderProps.getProperty(orderProp.v());
        if (theProp != null) {
          if (theProp.trim().length() == 0) {
            theProp = null;
          } else {
            theProp = theProp.trim();
          }
        }
        if ((theProp == null && failIfNull)) {
          err.append("Property : " + orderProp.name() +
                     " Not found in Properties File : " +
                     tasOrderPropsFile.getAbsolutePath());
        }
      }
      if (err.length() > 0) {
        throw new IllegalStateException(err.toString());
      }
      return theProp;
    }
  }


  public TasProvisioningRunManager() {
    checkBuildProvisioningBaseDir();
    tasProvisioningProps = new SortedProperties();
    tasProvisioningSummaryProps = new SortedProperties();
    util = TasRestFactory.getTasRestUtil();
  }

  public void setLabName(String labName) {
    if (labName == null) {
      throw new IllegalArgumentException("labName Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.LAB_NAME, true, labName);
  }

  public String getLabName() {
    return getProvisioningPropertyVal(TasProvisioningProperties.LAB_NAME);
  }

  public void setDataCenter(String dataCenter) {
    if (dataCenter == null) {
      throw new IllegalArgumentException("dataCenter Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.DATA_CENTER, true,
                            dataCenter);
  }

  public String getDataCenter() {
    return getProvisioningPropertyVal(TasProvisioningProperties.DATA_CENTER);
  }

  public void setEntitlementRunType(EntitlementRunType entitlementRunType) {
    if (entitlementRunType == null) {
      throw new IllegalArgumentException("entitlementRunType Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ENTITLEMENT_RUN_TYPE,
                            true, entitlementRunType.name());
  }

  public EntitlementRunType getEntitlementRunType() {
    return EntitlementRunType.valueOf(getProvisioningPropertyVal(TasProvisioningProperties.ENTITLEMENT_RUN_TYPE));
  }

  public void setServicesInstancesDetails(Set<ServiceInstanceDetails> servicesInstancesDetails) throws IOException,
                                                                                                       JsonGenerationException,
                                                                                                       JsonMappingException {
    if (servicesInstancesDetails == null ||
        servicesInstancesDetails.size() == 0) {
      throw new IllegalArgumentException("servicesInstancesDetails Parameter cannot be NULL or Empty");
    }
    setProvisioningProperty(TasProvisioningProperties.SERVICES_INSTANCES_DETAILS_JSON,
                            false,
                            util.toJSONString(servicesInstancesDetails.toArray(new ServiceInstanceDetails[servicesInstancesDetails.size()])));

    int seq = 0;
    for (ServiceInstanceDetails svcInstance : servicesInstancesDetails) {
      if (svcInstance.getSvcInstName() == null) {
        continue;
      }
      setProperty(tasProvisioningSummaryProps, "svc.inst.name." + seq,
                  svcInstance.getSvcInstName());
      setProperty(tasProvisioningSummaryProps, "svc.inst.size." + seq,
                  (svcInstance.getSvcInstSize() != null ?
                   svcInstance.getSvcInstSize().name() : null));
      setProperty(tasProvisioningSummaryProps, "svc.inst.type." + seq,
                  (svcInstance.getSvcInstType() != null ?
                   svcInstance.getSvcInstType().name() : null));
      setProperty(tasProvisioningSummaryProps, "svc.inst.credit.type." + seq,
                  svcInstance.getSvcInstCreditType());
      setProperty(tasProvisioningSummaryProps,
                  "svc.inst.entitlement.id." + seq,
                  svcInstance.getSvcInstEntitlementId());
      if (svcInstance.getSvcInstFields() != null &&
          svcInstance.getSvcInstFields().size() > 0) {
        for (String svcInstField : svcInstance.getSvcInstFields().keySet()) {
          setProperty(tasProvisioningSummaryProps,
                      "svc.inst." + svcInstField + "." + seq,
                      svcInstance.getSvcInstFields().get(svcInstField));
        }
      }
      seq++;
    }

  }

  public void addServiceInstanceDetails(ServiceInstanceDetails serviceInstanceDetails) throws IOException,
                                                                                              JsonGenerationException,
                                                                                              JsonMappingException {
    if (serviceInstanceDetails == null) {
      throw new IllegalArgumentException("serviceInstanceDetails Parameter cannot be NULL");
    }
    Set<ServiceInstanceDetails> servicesInstancesDetails =
      getServicesInstancesDetails();
    servicesInstancesDetails.add(serviceInstanceDetails);
    setServicesInstancesDetails(servicesInstancesDetails);
  }

  public ServiceInstanceDetails getLatestServiceInstanceDetails() throws IOException,
                                                                         JsonParseException,
                                                                         JsonMappingException {
    ServiceInstanceDetails serviceInstanceDetails = null;
    Set<ServiceInstanceDetails> servicesInstancesDetails =
      getServicesInstancesDetails();
    if (servicesInstancesDetails != null &&
        servicesInstancesDetails.size() > 0) {
      serviceInstanceDetails =
          servicesInstancesDetails.toArray(new ServiceInstanceDetails[servicesInstancesDetails.size()])[servicesInstancesDetails.size() -
          1];
    }
    return serviceInstanceDetails;
  }

  public void replaceLatestServiceInstanceDetails(ServiceInstanceDetails serviceInstanceDetails) throws IOException,
                                                                                                        JsonParseException,
                                                                                                        JsonMappingException {
    Set<ServiceInstanceDetails> servicesInstancesDetails =
      getServicesInstancesDetails();
    if (servicesInstancesDetails != null &&
        servicesInstancesDetails.size() > 0) {
      Iterator<ServiceInstanceDetails> it =
        servicesInstancesDetails.iterator();
      while (it.hasNext()) {
        it.next();
      }
      it.remove();
      servicesInstancesDetails.add(serviceInstanceDetails);
      setServicesInstancesDetails(servicesInstancesDetails);
    }
  }

  public Set<ServiceInstanceDetails> getServicesInstancesDetails() throws IOException,
                                                                          JsonParseException,
                                                                          JsonMappingException {
    ServiceInstanceDetails[] serviceInstanceDetailsArr =
      getObject(ServiceInstanceDetails[].class,
                TasProvisioningProperties.SERVICES_INSTANCES_DETAILS_JSON);
    return new LinkedHashSet<ServiceInstanceDetails>(Arrays.asList(serviceInstanceDetailsArr !=
                                                                   null ?
                                                                   serviceInstanceDetailsArr :
                                                                   new ServiceInstanceDetails[0]));
  }


  public void setCustomAttributes(List<AttributeValue> customAttributes) throws IOException,
                                                                                JsonGenerationException,
                                                                                JsonMappingException {
    if (customAttributes == null || customAttributes.size() == 0) {
      throw new IllegalArgumentException("customAttributes Parameter cannot be NULL or Empty");
    }
    setProvisioningProperty(TasProvisioningProperties.CUSTOM_ATTRIBUTES_JSON,
                            false,
                            util.toJSONString(customAttributes.toArray(new AttributeValue[customAttributes.size()])));

    int seq = 0;
    for (AttributeValue customAttribute : customAttributes) {
      setProperty(tasProvisioningSummaryProps, "custom.attr.name" + seq,
                  customAttribute.getName());
      setProperty(tasProvisioningSummaryProps, "custom.attr.value" + seq,
                  customAttribute.getValue());
      seq++;
    }

  }

  public void addCustomAttribute(AttributeValue customAttribute) throws IOException,
                                                                        JsonGenerationException,
                                                                        JsonMappingException {
    if (customAttribute == null) {
      throw new IllegalArgumentException("customAttribute Parameter cannot be NULL");
    }
    List<AttributeValue> customAttributes = getCustomAttributes();
    customAttributes.add(customAttribute);
    setCustomAttributes(customAttributes);
  }

  public List<AttributeValue> getCustomAttributes() throws IOException,
                                                           JsonParseException,
                                                           JsonMappingException {
    AttributeValue[] customAttributesArr =
      getObject(AttributeValue[].class, TasProvisioningProperties.CUSTOM_ATTRIBUTES_JSON);
    return new ArrayList<AttributeValue>(Arrays.asList(customAttributesArr !=
                                                       null ?
                                                       customAttributesArr :
                                                       new AttributeValue[0]));
  }

  public boolean isTestDisabled(String testMethodName) {
    if (testMethodName == null) {
      throw new IllegalArgumentException("testMethodName Parameter cannot be NULL");
    }
    return !getAllTestMethods().contains(testMethodName) ||
      getCompletedTestMethods().contains(testMethodName) ||
      getSkippedTestMethods().contains(testMethodName);
  }

  public void addTestMethods(String... testMethodNames) {
    addTestMethods(testMethodNames,
                   TasProvisioningProperties.ALL_TEST_METHODS);
  }

  public Set<String> getAllTestMethods() {
    return getProvisioningPropertyVals(TasProvisioningProperties.ALL_TEST_METHODS);
  }

  public void addCompletedTestMethods(String... testMethodNames) {
    addTestMethods(testMethodNames,
                   TasProvisioningProperties.COMPLETED_TEST_METHODS);
  }

  public Set<String> getCompletedTestMethods() {
    return getProvisioningPropertyVals(TasProvisioningProperties.COMPLETED_TEST_METHODS);
  }

  public void addSkippedTestMethods(String... testMethodNames) {
    addTestMethods(testMethodNames,
                   TasProvisioningProperties.SKIP_TEST_METHODS);
  }

  public Set<String> getSkippedTestMethods() {
    return getProvisioningPropertyVals(TasProvisioningProperties.SKIP_TEST_METHODS);
  }

  public Set<String> getRemaingTestMethods() {
    Set<String> remainingTestMethods = getAllTestMethods();
    remainingTestMethods.removeAll(getCompletedTestMethods());
    logger.fine("Returning Remaining Test Methods : " + remainingTestMethods);

    return remainingTestMethods;

  }

  public void setProvisioningOrderId(String orderId) {
    if (orderId == null) {
      throw new IllegalArgumentException("orderId Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ORDER_ID, true, orderId);
  }

  public String getProvisioningOrderId() {
    return getProvisioningPropertyVal(TasProvisioningProperties.ORDER_ID);
  }

  public void setCreditTypes(Set<String> creditTypes) {
    if (creditTypes == null || creditTypes.size() == 0) {
      throw new IllegalArgumentException("creditTypes Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.CREDIT_TYPES, true,
                            creditTypes.toArray(new String[creditTypes.size()]));
  }

  public Set<String> getCreditTypes() {
    return getProvisioningPropertyVals(TasProvisioningProperties.CREDIT_TYPES);
  }

  public void setAdminUserId(String adminUserId) {
    if (adminUserId == null) {
      throw new IllegalArgumentException("adminUserId Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ADMIN_USERID, true,
                            adminUserId);
  }

  public String getAdminUserId() {
    return getProvisioningPropertyVal(TasProvisioningProperties.ADMIN_USERID);
  }

  public void setAdminPassword(String adminPassword) {
    if (adminPassword == null) {
      throw new IllegalArgumentException("adminPassword Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ADMIN_PASSWORD, true,
                            adminPassword);
  }

  public String getAdminPassword() {
    return getProvisioningPropertyVal(TasProvisioningProperties.ADMIN_PASSWORD);
  }

  public void setAccountName(String accountName) {
    if (accountName == null) {
      throw new IllegalArgumentException("accountName Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ACCOUNT_NAME, true,
                            accountName);
  }

  public String getAccountName() {
    return getProvisioningPropertyVal(TasProvisioningProperties.ACCOUNT_NAME);
  }

  public void setIdenityDomainName(String identityDomain) {
    if (identityDomain == null) {
      throw new IllegalArgumentException("identityDomain Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.IDENTITY_DOMAIN_NAME,
                            true, identityDomain);
  }

  public String getIdenityDomainName() {
    return getProvisioningPropertyVal(TasProvisioningProperties.IDENTITY_DOMAIN_NAME);
  }

  public void setSeedOrder(Order seedOrder) throws IOException,
                                                   JsonGenerationException,
                                                   JsonMappingException {

    if (seedOrder == null) {
      throw new IllegalArgumentException("seedOrder Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.SEED_ORDER_JSON, false,
                            util.toJSONString(seedOrder));
  }

  public Order getSeedOrder() throws IOException, JsonParseException,
                                     JsonMappingException {
    return getObject(Order.class, TasProvisioningProperties.SEED_ORDER_JSON);
  }

  public void setProvisionedOrder(Order provisionedOrder) throws IOException,
                                                                 JsonGenerationException,
                                                                 JsonMappingException {
    if (provisionedOrder == null) {
      throw new IllegalArgumentException("provisionedOrder Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.PROVISIONED_ORDER_JSON,
                            false, util.toJSONString(provisionedOrder));
  }

  public void setProvisionedOrderAndRelatedProperties(Order provisionedOrder,
                                                      TasCentralRestClient tasCentralClient) throws IOException,
                                                                                                    JsonGenerationException,
                                                                                                    JsonMappingException {
    setProvisionedOrder(provisionedOrder);
    setProvisioningOrderId(provisionedOrder.getId());
    if (tasCentralClient == null) {
      throw new IllegalArgumentException("tasCentralClient Parameter cannot be NULL");
    }
    Account account =
      tasCentralClient.getUserAdministeredAccount(provisionedOrder.getAccount().getId());
    setAccount(account);

    ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
    for (OrderItem orderItem : provisionedOrder.getOrderItems()) {
      Subscription subscr = null;
      if (orderItem.getSubscription().getId() != null) {
        subscr =
            tasCentralClient.getSubscription(orderItem.getSubscription().getId());
      } else {
        subscr =
            tasCentralClient.getSubscription(orderItem.getSubscriptionId());
      }
      subscriptions.add(subscr);
      subscr.getIdentityDomain();
    }
    setSubscriptions(subscriptions);
  }

  public Order getProvisionedOrder() throws IOException, JsonParseException,
                                            JsonMappingException {
    return getObject(Order.class,
                     TasProvisioningProperties.PROVISIONED_ORDER_JSON);
  }

  public void setOpcActivationEmail(OpcActivationEmail opcActivationEmail) throws IOException,
                                                                                  JsonParseException,
                                                                                  JsonMappingException {
    if (opcActivationEmail == null) {
      throw new IllegalArgumentException("opcActivationEmail Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ACTIVATION_EMAIL_JSON,
                            false, util.toJSONString(opcActivationEmail));
  }

  public OpcActivationEmail getOpcActivationEmail() throws IOException,
                                                           JsonParseException,
                                                           JsonMappingException {
    return getObject(OpcActivationEmail.class,
                     TasProvisioningProperties.ACTIVATION_EMAIL_JSON);
  }

  public void setOpcProvisioningCompleteEmail(OpcProvisioningCompleteEmail opcProvisioningCompleteEmail) throws IOException,
                                                                                                                JsonParseException,
                                                                                                                JsonMappingException {
    if (opcProvisioningCompleteEmail == null) {
      throw new IllegalArgumentException("opcProvisioningCompleteEmail Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.PROVISIONING_EMAIL_JSON,
                            false,
                            util.toJSONString(opcProvisioningCompleteEmail));
    setAdminUserId(opcProvisioningCompleteEmail.getUserName());
    setIdenityDomainName(opcProvisioningCompleteEmail.getIdentityDomain());

    setProperty(tasProvisioningSummaryProps, "my.acct.url",
                (opcProvisioningCompleteEmail.getMyAccountURL() != null ?
                 opcProvisioningCompleteEmail.getMyAccountURL().toString() :
                 null));
    setProperty(tasProvisioningSummaryProps, "my.svcs.url",
                (opcProvisioningCompleteEmail.getMyServicesURL() != null ?
                 opcProvisioningCompleteEmail.getMyServicesURL().toString() :
                 null));

  }


  public OpcProvisioningCompleteEmail getOpcProvisioningCompleteEmail() throws IOException,
                                                                               JsonParseException,
                                                                               JsonMappingException {
    return getObject(OpcProvisioningCompleteEmail.class,
                     TasProvisioningProperties.PROVISIONING_EMAIL_JSON);
  }

  public void setOpcInstanceReadyEmail(OpcInstanceReadyEmail opcInstanceReadyEmail) throws IOException,
                                                                                           JsonParseException,
                                                                                           JsonMappingException {
    if (opcInstanceReadyEmail == null) {
      throw new IllegalArgumentException("opcInstanceReadyEmail Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.INSTANCE_READY_EMAIL_JSON,
                            false, util.toJSONString(opcInstanceReadyEmail));
  }

  public OpcInstanceReadyEmail getOpcInstanceReadyEmail() throws IOException,
                                                                 JsonParseException,
                                                                 JsonMappingException {
    return getObject(OpcInstanceReadyEmail.class,
                     TasProvisioningProperties.INSTANCE_READY_EMAIL_JSON);
  }

  public void setAccount(Account account) throws IOException,
                                                 JsonGenerationException,
                                                 JsonMappingException {
    if (account == null) {
      throw new IllegalArgumentException("account Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.ACCOUNT_JSON, false,
                            util.toJSONString(account));

    setProperty(tasProvisioningSummaryProps, "acct.id", account.getId());
    setProperty(tasProvisioningSummaryProps, "acct.name", account.getName());

    setAccountName(account.getName());

  }

  public Account getAccount() throws IOException, JsonParseException,
                                     JsonMappingException {
    return getObject(Account.class, TasProvisioningProperties.ACCOUNT_JSON);
  }

  public void setSubscriptions(List<Subscription> subscriptions) throws IOException,
                                                                        JsonGenerationException,
                                                                        JsonMappingException {
    if (subscriptions == null || subscriptions.size() == 0) {
      throw new IllegalArgumentException("subscriptions Parameter cannot be NULL or Empty");
    }
    setProvisioningProperty(TasProvisioningProperties.SUBSCRIPTIONS_JSON,
                            false,
                            util.toJSONString(subscriptions.toArray(new Subscription[subscriptions.size()])));

    int seq = 0;
    for (Subscription sub : subscriptions) {
      setProperty(tasProvisioningSummaryProps, "subscr.id." + seq,
                  sub.getId());
      setProperty(tasProvisioningSummaryProps, "subscr.name." + seq,
                  sub.getName());
      setProperty(tasProvisioningSummaryProps, "subscr.category." + seq,
                  (sub.getCategory() != null ? sub.getCategory().name() :
                   null));
      setProperty(tasProvisioningSummaryProps, "subscr.sdi.reqid." + seq,
                  sub.getSdiRequestId());
      setProperty(tasProvisioningSummaryProps, "subscr.status." + seq,
                  (sub.getStatus() != null ? sub.getStatus().name() : null));
      setProperty(tasProvisioningSummaryProps, "subscr.version.number." + seq,
                  sub.getVersionNumber());
      setProperty(tasProvisioningSummaryProps, "subscr.svc.id." + seq,
                  sub.getService() != null ? sub.getService().getId() : null);
      List<ServiceInstanceEndPoint> svcInstEndPoints =
        sub.getServiceInstanceEndPoints();
      if (svcInstEndPoints != null && svcInstEndPoints.size() > 0) {
        for (ServiceInstanceEndPoint svcInstEndPoint : svcInstEndPoints) {
          setProperty(tasProvisioningSummaryProps,
                      "subscr.svc.endpoint.type." + seq,
                      svcInstEndPoint.getType());
          setProperty(tasProvisioningSummaryProps,
                      "subscr.svc.endpoint.uri." + seq,
                      svcInstEndPoint.getUrl());
        }

      }
      seq++;
    }
  }

  public void addSubscription(Subscription subscription) throws IOException,
                                                                JsonGenerationException,
                                                                JsonMappingException {
    if (subscription == null) {
      throw new IllegalArgumentException("subscriptions Parameter cannot be NULL");
    }
    List<Subscription> subscriptions = getSubscriptions();
    subscriptions.add(subscription);
    setSubscriptions(subscriptions);
  }

  public List<Subscription> getSubscriptions() throws IOException,
                                                      JsonParseException,
                                                      JsonMappingException {
    Subscription[] subsArr =
      getObject(Subscription[].class, TasProvisioningProperties.SUBSCRIPTIONS_JSON);
    return Arrays.asList(subsArr != null ? subsArr : new Subscription[0]);
  }

  public void setCimServiceRequest(Request cimRequest) throws IOException,
                                                              JsonParseException,
                                                              JsonMappingException {
    if (cimRequest == null) {
      throw new IllegalArgumentException("cimRequest Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.CIM_SERVICE_REQUEST_JSON,
                            false, util.toJSONString(cimRequest));

    setProperty(tasProvisioningSummaryProps, "cim.req.id", cimRequest.getId());
    setProperty(tasProvisioningSummaryProps, "cim.req.status",
                cimRequest.getStatus());
  }

  public Request getCimServiceRequest() throws IOException, JsonParseException,
                                               JsonMappingException {
    return getObject(Request.class,
                     TasProvisioningProperties.CIM_SERVICE_REQUEST_JSON);
  }

  public void setCimServiceInstance(Instance cimInstance) throws IOException,
                                                                 JsonParseException,
                                                                 JsonMappingException {
    if (cimInstance == null) {
      throw new IllegalArgumentException("cimInstance Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.CIM_SERVICE_INSTANCE_JSON,
                            false, util.toJSONString(cimInstance));

    setProperty(tasProvisioningSummaryProps, "cim.inst.id",
                cimInstance.getId());
    setProperty(tasProvisioningSummaryProps, "cim.inst.name",
                cimInstance.getName());
    setProperty(tasProvisioningSummaryProps, "cim.inst.status",
                cimInstance.getStatus());
    setProperty(tasProvisioningSummaryProps, "cim.inst.entitlement.id",
                cimInstance.getEntitlementId());
    setProperty(tasProvisioningSummaryProps, "cim.inst.entitlement.type",
                cimInstance.getEntitlementType());
    setProperty(tasProvisioningSummaryProps, "cim.inst.svc.type",
                cimInstance.getServiceType());
    setProperty(tasProvisioningSummaryProps, "cim.inst.size",
                cimInstance.getSize());
    setProperty(tasProvisioningSummaryProps, "cim.inst.svc.entitlement.id",
                cimInstance.getServiceEntitlementId());
    List<Link> links = cimInstance.getLinks();
    if (links != null) {
      int seq = 0;
      for (Link link : links) {
        setProperty(tasProvisioningSummaryProps, "cim.inst.link.name." + seq,
                    link.getName());
        setProperty(tasProvisioningSummaryProps, "cim.inst.link.uri." + seq,
                    (link.getUri() != null ? link.getUri().toString() : null));
        ++seq;
      }
    }

  }

  public Instance getCimServiceInstance() throws IOException,
                                                 JsonParseException,
                                                 JsonMappingException {
    return getObject(Instance.class,
                     TasProvisioningProperties.CIM_SERVICE_INSTANCE_JSON);
  }

  public void setMcsUserSeq(Integer seqNum) {

    if (seqNum == null) {
      throw new IllegalArgumentException("seqNum Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.MCS_USER_SEQ, false,
                            seqNum.toString());
  }

  public Integer getMcsUserSeq() {
    Integer mcsSeq = null;
    String seqNumStr =
      getProvisioningPropertyVal(TasProvisioningProperties.MCS_USER_SEQ);
    if (seqNumStr != null) {
      mcsSeq = Integer.valueOf(seqNumStr);
    }
    return mcsSeq;
  }

  public void setAutoCompletionEnabled(Boolean autoCompletionEnabled) {

    if (autoCompletionEnabled == null) {
      throw new IllegalArgumentException("autoCompletionEnabled Parameter cannot be NULL");
    }
    setProvisioningProperty(TasProvisioningProperties.AUTO_COMPLETION_ENABLED,
                            true, autoCompletionEnabled.toString());
  }

  public Boolean getAutoCompletionEnabled() {
    Boolean autoCompletionEnabled = null;
    String autoCompletionEnabledStr =
      getProvisioningPropertyVal(TasProvisioningProperties.AUTO_COMPLETION_ENABLED);
    if (autoCompletionEnabledStr != null) {
      autoCompletionEnabled = Boolean.valueOf(autoCompletionEnabledStr);
    }
    return autoCompletionEnabled;
  }

  public void setProvisioningProperty(TasProvisioningProperties provisioningProp,
                                      boolean addToSummaryFile,
                                      String... vals) {
    StringBuilder err = new StringBuilder();
    if (provisioningProp == null) {
      err.append("provisioningProp Parameter cannot be NULL\n");
    }
    if (vals == null || vals.length == 0) {
      err.append("vals Parameter cannot be NULL\n");
    }
    if (err.length() > 0) {
      throw new IllegalArgumentException(err.toString());
    }
    StringBuilder sb = new StringBuilder();
    for (String val : vals) {
      if (sb.length() != 0) {
        sb.append(VALS_SEP + val.trim());
      } else {
        sb.append(val.trim());
      }
    }
    setProperty(tasProvisioningProps, provisioningProp.v(), sb.toString());
    if (addToSummaryFile) {
      setProperty(tasProvisioningSummaryProps, provisioningProp.v(),
                  sb.toString());
    }

    logger.fine("Set Property : " + provisioningProp.name() + " Val/s : " +
                sb.toString());
  }


  public String getProvisioningPropertyVal(TasProvisioningProperties orderProp) {
    String retVal = null;
    Set<String> retVals = getProvisioningPropertyVals(orderProp);
    if (retVals != null && retVals.size() > 0) {
      retVal = retVals.iterator().next();
    }
    return retVal;
  }

  public Set<String> getProvisioningPropertyVals(TasProvisioningProperties orderProp) {
    String[] retVals = new String[0];
    String vals =
      TasProvisioningProperties.getProperty(tasProvisioningProps, tasProvisioningPropsFile,
                                            orderProp, false);
    if (vals != null && vals.trim().length() > 0) {
      retVals = vals.trim().split(VALS_SEP);
    }
    logger.fine("Returning Property : " + orderProp.name() + " Val/s : " +
                Arrays.toString(retVals));
    return new LinkedHashSet<String>(Arrays.asList(retVals));
  }

  public void saveProvisioningProperties() throws FileNotFoundException,
                                                  IOException {
    if (tasProvisioningProps == null || tasProvisioningProps.size() == 0) {
      throw new IllegalStateException("No Tas Properties to Save Properties...");
    }
    String orderId =
      tasProvisioningProps.getProperty(TasProvisioningProperties.ORDER_ID.v());
    if (orderId != null) {
      File summaryFile =
        saveProvisioningFile(orderId + "-Summary" + ProvisioningVals.ORDER_TESTS_PROPS_FILE_SUFFIX.v(),
                             true, tasProvisioningSummaryProps);
      setProperty(tasProvisioningProps,
                  TasProvisioningProperties.SUMMARY_PROPS_FILE_NAME.v(),
                  summaryFile.getName());
      saveProvisioningFile(orderId +
                           ProvisioningVals.ORDER_TESTS_PROPS_FILE_SUFFIX.v(),
                           true, tasProvisioningProps);
    }
    File summaryFile =
      saveProvisioningFile(ProvisioningVals.ORDER_LATEST_SUMMARY_PROPS_FILE_NAME.v(),
                           true, tasProvisioningSummaryProps);
    setProperty(tasProvisioningProps,
                TasProvisioningProperties.SUMMARY_PROPS_FILE_NAME.v(),
                summaryFile.getName());
    saveProvisioningFile(ProvisioningVals.ORDER_LATEST_TESTS_PROPS_FILE_NAME.v(),
                         true, tasProvisioningProps);
    saveProvisioningFile(ProvisioningVals.ORDER_LATEST_SUMMARY_TXT_FILE_NAME.v(),
                         false, getSummary());
  }

  public Properties getProvisioningProperties() {
    return this.tasProvisioningProps;
  }

  public File getProvisioningPropertiesFile() {
    return tasProvisioningPropsFile;

  }

  public Properties getProvisioningSummaryProperties() {
    return this.tasProvisioningSummaryProps;
  }

  public File getProvisioningSummaryPropertiesFile() {
    return tasProvisioningSummaryPropsFile;

  }


  public void loadLatestProvisioningPropertiesFile() throws IOException {
    loadProvisioningPropertiesFile(new File(workingDir.getAbsolutePath() +
                                            File.separator +
                                            ProvisioningVals.ORDER_LATEST_TESTS_PROPS_FILE_NAME.v()));
  }

  public void loadProvisioningPropertiesFile(String orderId) throws IOException {
    if (orderId == null) {
      throw new IllegalArgumentException("orderId Parameter cannot be NULL\n");
    }
    loadProvisioningPropertiesFile(new File(workingDir.getAbsolutePath() +
                                            File.separator + orderId +
                                            ProvisioningVals.ORDER_TESTS_PROPS_FILE_SUFFIX.v()));
  }

  public void loadProvisioningPropertiesFile(File orderPropsFile) throws IOException {
    if (orderPropsFile == null) {
      throw new IllegalArgumentException("orderPropsFile Parameter cannot be NULL\n");
    }
    StringBuilder sb = new StringBuilder();
    if (!orderPropsFile.exists()) {
      sb.append(orderPropsFile.getAbsolutePath() + " Does not Exist\n");
    } else if (!orderPropsFile.isFile()) {
      sb.append(orderPropsFile.getAbsolutePath() +
                " is Not a Regular Exist\n");
    } else if (!orderPropsFile.canRead()) {
      sb.append(orderPropsFile.getAbsolutePath() + " is Not Readable\n");
    } else if (!orderPropsFile.canWrite()) {
      sb.append(orderPropsFile.getAbsolutePath() + " is Not Writable\n");
    }
    if (sb.length() > 0) {
      throw new IllegalStateException(sb.toString());
    }
    tasProvisioningPropsFile = orderPropsFile;
    tasProvisioningProps = new SortedProperties();
    FileInputStream fis = null;
    FileInputStream fisSummary = null;
    try {
      fis = new FileInputStream(tasProvisioningPropsFile);
      tasProvisioningProps.load(fis);
      logger.info("Successfully Loaded Properties File  : " +
                  tasProvisioningPropsFile.getAbsolutePath());
      logger.fine("Properties   : \n" +
          tasProvisioningProps);
      String summaryPropsFileName =
        tasProvisioningProps.getProperty(TasProvisioningProperties.SUMMARY_PROPS_FILE_NAME.v());
      if (summaryPropsFileName != null) {
        tasProvisioningSummaryPropsFile =
            new File(tasProvisioningPropsFile.getParentFile(),
                     summaryPropsFileName);
        fisSummary = new FileInputStream(tasProvisioningSummaryPropsFile);
        tasProvisioningSummaryProps = new SortedProperties();
        tasProvisioningSummaryProps.load(fisSummary);
        logger.info("Successfully Loaded Summary Properties File  : " +
                    tasProvisioningSummaryPropsFile.getAbsolutePath());
        logger.fine("Properties   : \n" +
            tasProvisioningSummaryProps);
      } else {
        logger.warning("Summary File Name Property : " +
                       TasProvisioningProperties.SUMMARY_PROPS_FILE_NAME.v() +
                       "..Not Found in File : " +
                       tasProvisioningPropsFile.getAbsolutePath());
      }
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (Exception ex) {
          //ignore
        }
      }
    }
  }

  public String getSummary() throws IOException, JsonParseException,
                                    JsonMappingException {
    StringBuilder summary = new StringBuilder();
    summary.append("\n\n");
    summary.append("      -----------------------------" + "\n");
    summary.append("      ******** RUN SUMMARY ********" + "\n");
    summary.append("      -----------------------------" + "\n");
    summary.append("      " + Calendar.getInstance().getTime() + "\n");
    summary.append("      -----------------------------" + "\n\n");

    addSummaryLine(summary, "Lab", getLabName(), true);
    addSummaryLine(summary, "DataCenter", getDataCenter(), true);
    addSummaryLine(summary, "AutoCompletionEnabled",
                   getAutoCompletionEnabled(), true);
    summary.append("\n");

    EntitlementRunType entType = getEntitlementRunType();
    addSummaryLine(summary, "EntitlementRunType", entType, true);
    Set<String> creditTypes = getCreditTypes();
    if (creditTypes != null && creditTypes.size() > 0) {
      addSummaryLine(summary, "CreditTypes", creditTypes, false);
    }
    summary.append("\n");

    addSummaryLine(summary, "OrderId", getProvisioningOrderId(), true);
    addSummaryLine(summary, "AdminUserId", getAdminUserId(), true);
    addSummaryLine(summary, "AdminPassword", getAdminPassword(), true);
    addSummaryLine(summary, "AccountName", getAccountName(), true);
    addSummaryLine(summary, "IdentityDomainName", getIdenityDomainName(),
                   true);
    summary.append("\n");

    OpcProvisioningCompleteEmail provEmail = getOpcProvisioningCompleteEmail();
    if (provEmail != null) {
      addSummaryLine(summary, "MyAccountURL", provEmail.getMyAccountURL(),
                     false);
      addSummaryLine(summary, "MyServicesURL", provEmail.getMyServicesURL(),
                     false);
      summary.append("\n");
    }

    Account account = getAccount();
    if (account != null) {
      addSummaryLine(summary, "AccountId", account.getId(), true);
      addSummaryLine(summary, "AccountName", account.getName(), true);
      summary.append("\n");
    }

    List<Subscription> subscriptions = getSubscriptions();
    if (subscriptions != null) {
      int seq = 0;
      for (Subscription sub : subscriptions) {
        addSummaryLine(summary, "SubscriptionId[" + seq + "]", sub.getId(),
                       true);
        addSummaryLine(summary, "SubscriptionName[" + seq + "]", sub.getName(),
                       true);
        addSummaryLine(summary, "SubscriptionCategory[" + seq + "]",
                       sub.getCategory(), true);
        addSummaryLine(summary, "SubscriptionSdiRequestId[" + seq + "]",
                       sub.getSdiRequestId(), false);
        addSummaryLine(summary, "SubscriptionStatus[" + seq + "]",
                       sub.getStatus(), false);
        addSummaryLine(summary, "SubscriptionVersionNumber[" + seq + "]",
                       sub.getVersionNumber(), false);
        addSummaryLine(summary, "SubscriptionServiceId[" + seq + "]",
                       sub.getService() != null ? sub.getService().getId() :
                       null, false);
        List<ServiceInstanceEndPoint> svcInstEndPoints =
          sub.getServiceInstanceEndPoints();
        if (svcInstEndPoints != null && svcInstEndPoints.size() > 0) {
          for (ServiceInstanceEndPoint svcInstEndPoint : svcInstEndPoints) {
            addSummaryLine(summary,
                           "SubscriptionServiceEndPointType[" + seq + "]",
                           svcInstEndPoint.getType(), false);
            addSummaryLine(summary,
                           "SubscriptionServiceEndPointUri[" + seq + "]",
                           svcInstEndPoint.getUrl(), false);
          }

        }
        summary.append("\n");
        seq++;
      }
    }

    Set<ServiceInstanceDetails> svcsInstances = getServicesInstancesDetails();
    if (svcsInstances != null) {
      int seq = 0;
      for (ServiceInstanceDetails svcInstance : svcsInstances) {
        if (svcInstance.getSvcInstName() == null) {
          continue;
        }
        addSummaryLine(summary, "ServiceInstanceName[" + seq + "]",
                       svcInstance.getSvcInstName(), false);
        addSummaryLine(summary, "ServiceInstanceSize[" + seq + "]",
                       svcInstance.getSvcInstSize(), false);
        addSummaryLine(summary, "ServiceInstanceType[" + seq + "]",
                       svcInstance.getSvcInstType(), false);
        addSummaryLine(summary, "ServiceInstanceCreditType[" + seq + "]",
                       svcInstance.getSvcInstCreditType(), false);
        addSummaryLine(summary, "ServiceInstanceEntitlementId[" + seq + "]",
                       svcInstance.getSvcInstEntitlementId(), false);

        if (svcInstance.getSvcInstFields() != null &&
            svcInstance.getSvcInstFields().size() > 0) {
          for (String svcInstField : svcInstance.getSvcInstFields().keySet()) {
            addSummaryLine(summary, svcInstField + "[" + seq + "]",
                           svcInstance.getSvcInstFields().get(svcInstField),
                           false);
          }
        }
        summary.append("\n");
        seq++;
      }
    }

    List<AttributeValue> customAttributes = getCustomAttributes();
    if (customAttributes != null && customAttributes.size() > 0) {
      int seq = 0;
      for (AttributeValue customAttribute : customAttributes) {
        addSummaryLine(summary, "CustomAttributeName[" + seq + "]",
                       customAttribute.getName(), false);
        addSummaryLine(summary, "CustomAttributeValue[" + seq + "]",
                       customAttribute.getValue(), false);
        summary.append("\n");
        seq++;
      }
    }

    Request cimRequest = getCimServiceRequest();
    if (cimRequest != null) {
      addSummaryLine(summary, "CimRequestId", cimRequest.getId(), true);
      addSummaryLine(summary, "CimRequestStatus", cimRequest.getStatus(),
                     true);
      summary.append("\n");
    }

    Instance cimInstance = getCimServiceInstance();
    if (cimInstance != null) {
      addSummaryLine(summary, "CimInstanceId", cimInstance.getId(), true);
      addSummaryLine(summary, "CimInstanceName", cimInstance.getName(), true);
      addSummaryLine(summary, "CimInstanceStatus", cimInstance.getStatus(),
                     true);
      summary.append("\n");
      addSummaryLine(summary, "CimInstanceEntitlementId",
                     cimInstance.getEntitlementId(), false);
      addSummaryLine(summary, "CimInstanceEntitlementType",
                     cimInstance.getEntitlementType(), false);
      summary.append("\n");
      addSummaryLine(summary, "CimInstanceServiceType",
                     cimInstance.getServiceType(), false);
      addSummaryLine(summary, "CimInstanceSize", cimInstance.getSize(), false);
      addSummaryLine(summary, "CimInstanceServiceEntitlementId",
                     cimInstance.getServiceEntitlementId(), false);
      summary.append("\n");
      List<Link> links = cimInstance.getLinks();
      if (links != null) {
        int seq = 0;
        for (Link link : links) {
          addSummaryLine(summary, "CimInstanceLinkName[" + seq + "]",
                         link.getName(), true);
          addSummaryLine(summary, "CimInstanceLinkUri[" + seq + "]",
                         link.getUri(), true);
          ++seq;
        }
      }
      summary.append("\n");
    }

    return summary.toString();

  }

  private static void setProperty(Properties props, String key, String value) {
    if (value != null) {
      props.setProperty(key, value);
    } else {
      logger.warning("Skipping Property : " + key + " With NULL Value...");
    }
  }
  private static final int TOTAL_TITLE_LENGTH = 40;
  private static final String TITLE_SEP = "-";

  private static void addSummaryLine(StringBuilder sb, String lineTitle,
                                     Object lineValue,
                                     boolean addEvenIfValueNull) {
    if (addEvenIfValueNull || lineValue != null) {
      StringBuilder summaryLine = new StringBuilder();
      summaryLine.append(lineTitle + " ");
      while (summaryLine.length() < TOTAL_TITLE_LENGTH) {
        summaryLine.append(TITLE_SEP);
      }
      summaryLine.append("> " + lineValue + "\n");
      sb.append(summaryLine.toString());
    }
  }

  private void addTestMethods(String[] testMethodNames,
                              TasProvisioningProperties testMethodsProp) {
    if (testMethodNames == null || testMethodNames.length == 0) {
      throw new IllegalArgumentException("testMethodNames Parameter cannot be NULL");
    }
    Set<String> currMethods = getProvisioningPropertyVals(testMethodsProp);
    currMethods.addAll(Arrays.asList(testMethodNames));
    setProvisioningProperty(testMethodsProp, false,
                            currMethods.toArray(new String[currMethods.size()]));
    logger.fine("Added TestMethodNames : " + Arrays.asList(testMethodNames) +
                " To Property : " + testMethodsProp.name() +
                " Method Values : " + currMethods);
  }

  private File saveProvisioningFile(String fileName, boolean isPropertiesFile,
                                    Object data) throws FileNotFoundException,
                                                        IOException {
    File file =
      new File(workingDir.getAbsolutePath() + File.separator + fileName);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      if (isPropertiesFile) {
        Properties provisioningProperties = (Properties)data;
        provisioningProperties.store(fos, null);
      } else {
        fos.write(data.toString().getBytes());
      }
      fos.flush();
      logger.fine("Saved ProvisioningFile  : " + file.getAbsolutePath() +
                  "\n" +
          data.toString());
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (Exception e) {
          //ignore
        }
      }
      fos.close();
    }
    return file;
  }

  private <T> T getObject(Class<T> clazz,
                          TasProvisioningProperties orderProperty) throws IOException,
                                                                          JsonParseException,
                                                                          JsonMappingException {
    String val =
      TasProvisioningProperties.getProperty(tasProvisioningProps, tasProvisioningPropsFile,
                                            orderProperty, false);
    T t = null;
    if (val != null) {
      t = util.fromJSONString(val, clazz);
    }
    return t;
  }

  private enum HudsonEnv {
    //The current build number, such as "153"
    BUILD_NUMBER,
    //The current build id, such as "2005-08-22_23-59-59" (YYYY-MM-DD_hh-mm-ss)
    BUILD_ID,
    //Name of the project of this build, such as "foo"
    JOB_NAME,
    //String of "hudson-${JOB_NAME}-${BUILD_NUMBER}". Convenient to put into a resource file, a jar file, etc for easier identification.
    BUILD_TAG,
    //The unique number that identifies the current executor (among executors of the same machine) that's carrying out this build. This is the number you see in the "build executor status", except that the number starts from 0, not 1.
    EXECUTOR_NUMBER,
    //Name of the slave if the build is on a slave, or "" if run on master
    NODE_NAME,
    //Whitespace-separated list of labels that the node is assigned.
    NODE_LABELS,
    //If your job is configured to use a specific JDK, this variable is set to the JAVA_HOME of the specified JDK. When this variable is set, PATH is also updated to have $JAVA_HOME/bin.
    JAVA_HOME,
    //The absolute path of the workspace.
    WORKSPACE,
    //Full URL of Hudson, like http://server:port/hudson/
    HUDSON_URL,
    //Full URL of this build, like http://server:port/hudson/job/foo/15/
    BUILD_URL,
    //Full URL of this job, like http://server:port/hudson/job/foo/
    JOB_URL,
    //For Subversion-based projects, this variable contains the revision number of the module.
    SVN_REVISION,
    //For CVS-based projects, this variable contains the branch of the module. If CVS is configured to check out the trunk, this environment variable will not be set.
    CVS_BRANCH,
    //Currently logged in user
    HUDSON_USER;

    static String getEnv(HudsonEnv env) {
      String val = trim(System.getenv(env.name()));
      if (val == null) {
        val = trim(System.getProperty(env.name()));
      }
      logger.fine("Returning Hudson Env[" + env.name() + "] Val[" + val + "]");
      return val;
    }

    private static String trim(String val) {
      if (val == null || val.trim().length() == 0) {
        val = null;
      } else {
        val = val.trim();
      }
      return val;
    }

  }

  private enum ProvisioningVals {

    ORDER_BASE_DIR_NAME("TasProvisioningOrders"),
    ORDER_TESTS_PROPS_FILE_SUFFIX(".properties"),
    ORDER_LATEST_SUMMARY_PROPS_FILE_NAME("OrderSummary.properties"),
    ORDER_LATEST_SUMMARY_TXT_FILE_NAME("OrderSummary.txt"),
    ORDER_LATEST_TESTS_PROPS_FILE_NAME("Order.properties");

    private String val;

    ProvisioningVals(String val) {
      this.val = val;
    }

    String v() {
      return val;
    }
  }

  private void checkBuildProvisioningBaseDir() {
    String baseDir = HudsonEnv.getEnv(HudsonEnv.WORKSPACE);
    if (baseDir == null) {
      baseDir = System.getProperty("user.home");
    }
    workingDir =
        new File(baseDir + File.separator + ProvisioningVals.ORDER_BASE_DIR_NAME.v());
    workingDir.mkdirs();
    logger.info("Working Dir Set To : " + workingDir.getAbsolutePath());
  }

  public static void main(String[] args) throws MessagingException,
                                                IOException,
                                                GeneralSecurityException {

    String orderId1 = "9999_4522337"; //cloud_credit bucket
    String orderId2 = "9999_4531678"; //subscription based;
    String cimRequestId = "1070";

    TasProvisioningRunManager tasProvisioningRunManager =
      new TasProvisioningRunManager();
    TasProvisioningRunManager tasProvisioningRunManager1 =
      new TasProvisioningRunManager();
    TasProvisioningRunManager tasProvisioningRunManager2 =
      new TasProvisioningRunManager();
    TasProvisioningRunManager tasProvisioningRunManager3 =
      new TasProvisioningRunManager();

    //writeOrderPropsFile(tasProvisioningRunManager, orderId1, cimRequestId);
    loadProvisioningPropsFile(tasProvisioningRunManager, orderId1);
    //loadProvisioningPropsFile(tasProvisioningRunManager1, null);
    //loadProvisioningPropsFile(tasProvisioningRunManager2, orderId1);
    //loadProvisioningPropsFile(tasProvisioningRunManager3, orderId2);

    System.out.println(tasProvisioningRunManager.getSummary());
    //System.out.println(tasProvisioningRunManager1.getSummary());
    //System.out.println(tasProvisioningRunManager2.getSummary());
    //System.out.println(tasProvisioningRunManager3.getSummary());


  }

  private static void loadProvisioningPropsFile(TasProvisioningRunManager tasProvisioningRunManager,
                                                String orderId) throws IOException {

    tasProvisioningRunManager.loadLatestProvisioningPropertiesFile();
    tasProvisioningRunManager.loadProvisioningPropertiesFile(tasProvisioningRunManager.getProvisioningPropertiesFile());
    if (orderId != null) {
      tasProvisioningRunManager.loadProvisioningPropertiesFile(orderId);
    }

    String labEnv = tasProvisioningRunManager.getLabName();
    String dataCenter = tasProvisioningRunManager.getDataCenter();
    Boolean autoCompletionEnabled =
      tasProvisioningRunManager.getAutoCompletionEnabled();


    EntitlementRunType eRunType =
      tasProvisioningRunManager.getEntitlementRunType();
    Set<String> creditTypes = tasProvisioningRunManager.getCreditTypes();
    Set<ServiceInstanceDetails> svcs =
      tasProvisioningRunManager.getServicesInstancesDetails();
    ServiceInstanceDetails svcLatest =
      tasProvisioningRunManager.getLatestServiceInstanceDetails();
    List<AttributeValue> customAttributes =
      tasProvisioningRunManager.getCustomAttributes();
    Set<String> allTestMethods = tasProvisioningRunManager.getAllTestMethods();
    Set<String> completedTestMethods =
      tasProvisioningRunManager.getCompletedTestMethods();
    Set<String> skippedTestMethods =
      tasProvisioningRunManager.getSkippedTestMethods();
    Set<String> remainingTestMethods =
      tasProvisioningRunManager.getRemaingTestMethods();
    orderId = tasProvisioningRunManager.getProvisioningOrderId();
    String idmName = tasProvisioningRunManager.getIdenityDomainName();
    String adminUser = tasProvisioningRunManager.getAdminUserId();
    String adminPassword = tasProvisioningRunManager.getAdminPassword();
    Order seedOrder = tasProvisioningRunManager.getSeedOrder();
    Order provisionedOrder = tasProvisioningRunManager.getProvisionedOrder();
    OpcActivationEmail actEmail =
      tasProvisioningRunManager.getOpcActivationEmail();
    String actEmailTxt = actEmail.getOpcEmail();
    OpcProvisioningCompleteEmail provEmail =
      tasProvisioningRunManager.getOpcProvisioningCompleteEmail();
    if (provEmail != null) {
      String provEmailTxt = provEmail.getOpcEmail();
    }
    Account account = tasProvisioningRunManager.getAccount();
    List<Subscription> subscriptions =
      tasProvisioningRunManager.getSubscriptions();
    Subscription subscription = subscriptions.get(0);
    Request cimRequest = tasProvisioningRunManager.getCimServiceRequest();
    Instance cimInstance = tasProvisioningRunManager.getCimServiceInstance();
    Integer mcsSeq = tasProvisioningRunManager.getMcsUserSeq();
  }

  private static void writeOrderPropsFile(TasProvisioningRunManager tasProvisioningRunManager,
                                          String orderId,
                                          String cimRequestId) throws MessagingException,
                                                                      IOException,
                                                                      GeneralSecurityException {
    String labEnv = "c9qa132";
    String dataCenter = "dc1";
    String paramsKeyPrefix = "central.";

    System.setProperty(TestConstants.REDIS_HOST,
                       "slcn03vmf0254.us.oracle.com");
    RedisParamsDataProvider dp = new RedisParamsDataProvider();
    Map<String, String> params = dp.getLabParams(labEnv);
    String oracleUserId =
      params.get(paramsKeyPrefix + TAS.REST_ORACLE_APPID.toString());
    String ssoUser = params.get(SSO.USERNAME.toString());
    String ssoPassword = params.get(SSO.PASSWORD.toString());
    OpcJavaMailUtil jmu = new OpcJavaMailUtil(ssoUser, ssoPassword);
    String adminPassword = "Welcome1$";
    String[] creditTypes = { "JAVAMB", "DBMB", "IAASMB" };
    Boolean autoCompletionEnabled = true;

    tasProvisioningRunManager.setLabName(labEnv);
    tasProvisioningRunManager.setDataCenter(dataCenter);

    tasProvisioningRunManager.setAutoCompletionEnabled(autoCompletionEnabled);

    tasProvisioningRunManager.setEntitlementRunType(EntitlementRunType.CLOUD_CREDIT);
    tasProvisioningRunManager.setCreditTypes(new LinkedHashSet<String>(Arrays.asList(creditTypes)));

    TasCentralRestClient tasCentral =
      TasRestFactory.getTasCentralRestClientHandle(params.get(paramsKeyPrefix +
                                                              TAS.REST_API_VERSION.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_BASE_URI.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_ORACLE_USER.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_ORACLE_PASSWORD.toString()),
                                                   oracleUserId, null);

    Order order = tasCentral.getOrder(orderId);
    tasProvisioningRunManager.setSeedOrder(order);
    tasProvisioningRunManager.setProvisionedOrderAndRelatedProperties(order,
                                                                      tasCentral);
    tasProvisioningRunManager.addTestMethods("seedProvisioningOrder",
                                             "checkActivationEmail",
                                             "activateProvisioningOrder",
                                             "checkProvisioningEmail",
                                             "changeTempPassword");
    tasProvisioningRunManager.addCompletedTestMethods("seedProvisioningOrder",
                                                      "checkActivationEmail",
                                                      "activateProvisioningOrder");
    tasProvisioningRunManager.addSkippedTestMethods("createServiceInstance",
                                                    "createMessagingSvcUsersAndRoleGrants");


    ServiceInstanceDetails serviceInstanceDetails1 =
      new ServiceInstanceDetails();
    serviceInstanceDetails1.setSvcInstEntitlementId("1");
    serviceInstanceDetails1.setSvcInstName("svc1");
    serviceInstanceDetails1.setSvcInstCreditType("JAVAMB");
    serviceInstanceDetails1.setSvcInstSize(ServiceSizeCategoryType.CUSTOM);
    serviceInstanceDetails1.setSvcInstTypeStr(TasServiceType.Messaging);
    tasProvisioningRunManager.addServiceInstanceDetails(serviceInstanceDetails1);

    ServiceInstanceDetails serviceInstanceDetails2 =
      new ServiceInstanceDetails();
    serviceInstanceDetails2.setSvcInstEntitlementId("2");
    serviceInstanceDetails2.setSvcInstName("svc2");
    serviceInstanceDetails1.setSvcInstCreditType("JAVAMB");
    serviceInstanceDetails2.setSvcInstSize(ServiceSizeCategoryType.ENTERPRISE);
    serviceInstanceDetails2.setSvcInstTypeStr(TasServiceType.Documents);
    tasProvisioningRunManager.addServiceInstanceDetails(serviceInstanceDetails2);

    ServiceInstanceDetails serviceInstanceDetails3 =
      new ServiceInstanceDetails();
    serviceInstanceDetails3.setSvcInstEntitlementId("3");
    serviceInstanceDetails3.setSvcInstName("svc3");
    serviceInstanceDetails1.setSvcInstCreditType("JAVAMB");
    serviceInstanceDetails3.setSvcInstSize(ServiceSizeCategoryType.STANDARD);
    serviceInstanceDetails3.setSvcInstTypeStr(TasServiceType.Integration);
    tasProvisioningRunManager.replaceLatestServiceInstanceDetails(serviceInstanceDetails3);

    AttributeValue customAttribute1 = new AttributeValue();
    customAttribute1.setName("LOCALE");
    customAttribute1.setValue("en_us");
    tasProvisioningRunManager.addCustomAttribute(customAttribute1);
    AttributeValue customAttribute2 = new AttributeValue();
    customAttribute2.setName("IS_BRANDING_REQUIRED");
    customAttribute2.setValue("TRUE");
    tasProvisioningRunManager.addCustomAttribute(customAttribute2);

    Integer days = null;
    OpcActivationEmail actEmail =
      jmu.getActivationEmail(OpcJavaMailUtil.QueryType.ORDER_ID, orderId, days,
                             0);

    tasProvisioningRunManager.setOpcActivationEmail(actEmail);

    OpcProvisioningCompleteEmail provEmail =
      jmu.getProvisioningCompleteEmail(OpcJavaMailUtil.QueryType.ORDER_ID,
                                       orderId, days, 0);
    tasProvisioningRunManager.setOpcProvisioningCompleteEmail(provEmail);

    tasProvisioningRunManager.setAdminPassword(adminPassword);

    CommonInstanceMgrRestClient cimClient =
      CommonInstanceMgrRestFactory.getCommonInstanceMgrRestClient(params.get(paramsKeyPrefix +
                                                                             CIM.REST_API_VERSION.toString()),
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_BASE_URI.toString()),
                                                                  provEmail.getIdentityDomain(),
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_ORACLE_USER.toString()),
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_ORACLE_PASSWORD.toString()),
                                                                  CommonInstanceMgrRestFactory.ReturnPreference.WAIT,
                                                                  provEmail.getUserName());

    Request cimRequest = cimClient.getRequest(cimRequestId);
    tasProvisioningRunManager.setCimServiceRequest(cimRequest);
    Instance cimInstance = cimClient.getInstance(cimRequest.getInstanceId());
    tasProvisioningRunManager.setCimServiceInstance(cimInstance);

    tasProvisioningRunManager.setMcsUserSeq(1);

    tasProvisioningRunManager.saveProvisioningProperties();

  }

}
