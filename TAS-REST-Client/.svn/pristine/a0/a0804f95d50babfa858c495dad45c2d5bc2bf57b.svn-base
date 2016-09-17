package com.oracle.saas.qa.tas.rest.client;


import com.oracle.tas.rest.api.model.Account;
import com.oracle.tas.rest.api.model.AccountType;
import com.oracle.tas.rest.api.model.Admin;
import com.oracle.tas.rest.api.model.Admins;
import com.oracle.tas.rest.api.model.AttributeValue;
import com.oracle.tas.rest.api.model.CentralVersion;
import com.oracle.tas.rest.api.model.CreditAccount;
import com.oracle.tas.rest.api.model.DataCenter;
import com.oracle.tas.rest.api.model.DataRegion;
import com.oracle.tas.rest.api.model.IdentityDomain;
import com.oracle.tas.rest.api.model.OperationType;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.OrderItem;
import com.oracle.tas.rest.api.model.OrderSource;
import com.oracle.tas.rest.api.model.Role;
import com.oracle.tas.rest.api.model.RoleNames;
import com.oracle.tas.rest.api.model.Roles;
import com.oracle.tas.rest.api.model.ScopeTypes;
import com.oracle.tas.rest.api.model.Service;
import com.oracle.tas.rest.api.model.ServicePlan;
import com.oracle.tas.rest.api.model.ServiceSizeCategoryType;
import com.oracle.tas.rest.api.model.Subscription;
import com.oracle.tas.rest.api.model.SubscriptionType;
import com.oracle.tas.rest.api.model.TasServiceType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.reflect.Method;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *  Helper Class for Building a TAS Order.
 *
 *  @author Haris Shah.
 *
 *  @see  com.oracle.saas.qa.tas.rest.client.TasOrderBuilder For Description
 */
class TasOrderBuilderImpl implements TasOrderBuilder {

  private static Logger logger =
    Logger.getLogger(TasOrderBuilderImpl.class.getName());

  private static final SimpleDateFormat ACCT_NAME_FORMATTER =
    new SimpleDateFormat("EEE-MMM-d-yyyy-hh:mm:ss:SSSSSSS-a-z");

  private TasCentralRestClient tasCentralRestClient;
  private CentralVersion centralVersion;
  private Order order;
  private String adminEmailAddress;
  private String adminUserName;
  private XMLGregorianCalendar startDate;
  private XMLGregorianCalendar endDate;
  private OperationType operationType;
  private SubscriptionType subscriptionType;
  private String gsiCsiNumber;
  private String identityDomainName;
  private AccountType acountType;
  private boolean isNewOrder;
  private boolean isAutoComplete;

  private BitSet methodOrderSet;
  private Random randNumGenerator;
  private static final int RAND_NUM_UPPER_BOUND = 10000;
  private static final String COUNTRY_CODE = "US";

  private static class MethodsInvocationOrder {
    String methodName;
    int methodId;
    int[] precedingMethodsId;

    MethodsInvocationOrder(String methodName) {
      this.methodName = methodName;
    }

    MethodsInvocationOrder(String methodName, int methodId,
                           int[] precedingMethodsId) {
      this.methodName = methodName;
      this.methodId = methodId;
      this.precedingMethodsId = precedingMethodsId;
    }

    boolean verifyPrecedingMethodsCalled(boolean allMethods,
                                         BitSet methodOrderSet) {

      boolean precdMethodsCalled = allMethods;
      for (int precedingMethodId : precedingMethodsId) {
        if (precedingMethodId > 0) {
          boolean precdMethodCalled = methodOrderSet.get(precedingMethodId);
          precdMethodsCalled =
              allMethods ? precdMethodsCalled && precdMethodCalled :
              precdMethodsCalled || precdMethodCalled;
        } else {
          precdMethodsCalled = true;
          break;
        }
      }
      if (precdMethodsCalled) {
        methodOrderSet.set(methodId);
      } else {
        throw new IllegalStateException("Builder Method -> ( " + methodName +
                                        " ) Cannot Be Invoked Before -> ( " +
                                        getPrecedingMethodNames() + " )");
      }
      return precdMethodsCalled;
    }

    private List<String> getPrecedingMethodNames() {
      List<String> precedingMethodNames = new ArrayList<String>();
      for (int precedingMethodId : precedingMethodsId) {
        if (precedingMethodId > 0) {
          for (MethodsInvocationOrder mio : methodsInvocationOrderSet) {
            if (mio.methodId == precedingMethodId) {
              precedingMethodNames.add(mio.methodName);
              break;
            }
          }
        }
      }
      return precedingMethodNames;
    }

    @Override
    public String toString() {
      return "\n MethodName -> ( " + methodName + " ) MethodId -> ( " +
        methodId + " ) PrecedingMethodsId -> ( " +
        Arrays.toString(precedingMethodsId) + " ) ";
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (!(object instanceof TasOrderBuilderImpl.MethodsInvocationOrder)) {
        return false;
      }
      final TasOrderBuilderImpl.MethodsInvocationOrder other =
        (TasOrderBuilderImpl.MethodsInvocationOrder)object;
      if (!(methodName == null ? other.methodName == null :
            methodName.equals(other.methodName))) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      final int PRIME = 37;
      int result = 1;
      result =
          PRIME * result + ((methodName == null) ? 0 : methodName.hashCode());
      return result;
    }
  }

  private static ArrayList<MethodsInvocationOrder> methodsInvocationOrderSet;


  static {
    methodsInvocationOrderSet = new ArrayList<MethodsInvocationOrder>();
    Method[] methods = TasOrderBuilder.class.getMethods();
    for (Method method : methods) {
      String methodName = method.getName();
      if (method.isAnnotationPresent(TasOrderBuilderMethodsOrder.class)) {
        TasOrderBuilderMethodsOrder annnotation =
          method.getAnnotation(TasOrderBuilderMethodsOrder.class);
        methodsInvocationOrderSet.add(new MethodsInvocationOrder(methodName,
                                                                 annnotation.methodId(),
                                                                 annnotation.precedingMethodsId()));
      }
    }
    Collections.sort(methodsInvocationOrderSet,
                     new Comparator<MethodsInvocationOrder>() {

        public int compare(TasOrderBuilderImpl.MethodsInvocationOrder o1,
                           TasOrderBuilderImpl.MethodsInvocationOrder o2) {
          return o1.methodId - o2.methodId;
        }
      });

    logger.info("MethodsInvocationOrderSet : " + methodsInvocationOrderSet);
  }

  TasOrderBuilderImpl(TasCentralRestClient tasCentralRestClient) {
    this.tasCentralRestClient = tasCentralRestClient;
    this.centralVersion = tasCentralRestClient.getCentralVersion();
    methodOrderSet = new BitSet();
    randNumGenerator = new Random();
  }

  public void printMethodsOrder() {
    System.out.println(methodsInvocationOrderSet);
  }

  public TasOrderBuilder startNewOrder(String adminEmailAddress,
                                       String adminUserName, String csiNumber,
                                       boolean isMeteredService,
                                       boolean isAutoComplete) throws DatatypeConfigurationException {
    initOrder(true, adminEmailAddress, adminUserName, null, csiNumber,
              isMeteredService);
    this.isAutoComplete = isAutoComplete;
    isNewOrder = true;
    return this;

  }


  public TasOrderBuilder startUpdateOrder(String adminEmailAddress,
                                          String adminUserName, String orderId,
                                          String csiNumber,
                                          boolean isMeteredService) throws DatatypeConfigurationException {
    initOrder(true, adminEmailAddress, adminUserName, orderId, csiNumber,
              isMeteredService);
    isAutoComplete = false;
    isNewOrder = false;
    return this;

  }

  public TasOrderBuilder addOrderSource(OrderSource orderSource) {
    if (orderSource == null) {
      throw new IllegalArgumentException("orderSource Param is Required");
    }
    checkMethodInvocationOrder(false);
    order.setOrderSource(orderSource);
    return this;
  }

  public TasOrderBuilder addDataCenter(String dataCenterId,
                                       String dataCenterRegionId) {
    if (dataCenterId == null) {
      throw new IllegalArgumentException("dataCenterId Param is Required");
    }
    if (dataCenterRegionId == null) {
      throw new IllegalArgumentException("dataCenterRegionId Param is Required");
    }
    checkMethodInvocationOrder(false);
    DataCenter dataCenter = new DataCenter();
    dataCenter.setCanonicalLink(UriBuilder.fromUri(centralVersion.getDataCenters().getCanonicalLink()).path(dataCenterId).build().toString());
    DataRegion dataCenterRegion = new DataRegion();
    dataCenterRegion.setCanonicalLink(UriBuilder.fromUri(centralVersion.getDataRegions().getCanonicalLink()).path(dataCenterRegionId).build().toString());
    dataCenter.setDataRegion(dataCenterRegion);
    order.setDataCenter(dataCenter);

    return this;
  }

  public TasOrderBuilder addOperation(OperationType operationType) {
    if (operationType == null) {
      throw new IllegalArgumentException("operationType Param is Required");
    }
    checkMethodInvocationOrder(false);
    this.operationType = operationType;

    return this;
  }

  public TasOrderBuilder addSubscription(SubscriptionType subscriptionType,
                                         String identityDomainName,
                                         String csiGsiNumber) {
    if (subscriptionType == null) {
      throw new IllegalArgumentException("subscriptionType Param is Required");
    }
    checkMethodInvocationOrder(false);
    this.subscriptionType = subscriptionType;
    this.gsiCsiNumber = csiGsiNumber;
    if (identityDomainName == null) {
      identityDomainName = generateSystemName();
    }
    this.identityDomainName = identityDomainName.toLowerCase();
    setSystemNameCustomProperty();

    return this;
  }

  public TasOrderBuilder addAccount(AccountType acountType, String accountId,
                                    String accountName) {
    if (acountType == null) {
      throw new IllegalArgumentException("acountType Param is Required");
    }
    if (accountId == null) {
      throw new IllegalArgumentException("accountId Param is Required");
    }
    checkMethodInvocationOrder(false);
    this.acountType = acountType;

    if (accountName == null) {
      accountName =
          "Account-" + ACCT_NAME_FORMATTER.format(Calendar.getInstance().getTime());
    }

    Account account = new Account();
    account.setId(accountId);
    account.setName(accountName.toLowerCase());
    account.setCountryCode(COUNTRY_CODE);
    order.setAccount(account);

    return this;
  }


  private static final String GOLD_PRIORITY = "99";

  public TasOrderBuilder addService(TasServiceType serviceType,
                                    ServiceSizeCategoryType serviceSizeCategoryType,
                                    String serviceDisplayName,
                                    AttributeValue... customAttributes) {
    if (serviceType == null) {
      throw new IllegalArgumentException("serviceType Param is Required");
    }
    if (serviceSizeCategoryType == null) {
      throw new IllegalArgumentException("serviceSizeCategoryType Param is Required");
    }

    checkMethodInvocationOrder(false);
    OrderItem orderItem = new OrderItem();
    orderItem.setStartDate(startDate);
    orderItem.setEndDate(endDate);
    orderItem.setId(String.valueOf(randNumGenerator.nextInt(RAND_NUM_UPPER_BOUND)));
    orderItem.setOperationType(operationType);

    Subscription subscription = new Subscription();
    subscription.setPriority(GOLD_PRIORITY);
    if (serviceDisplayName == null) {
      serviceDisplayName = serviceType.getDisplayName() +
          //NAME_SUFFIX_FORMATTER.format(Calendar.getInstance().getTime()) +
          randNumGenerator.nextInt(RAND_NUM_UPPER_BOUND);
    }

    serviceDisplayName = serviceDisplayName.toLowerCase();
    subscription.setServiceDisplayName(serviceDisplayName);
    if (customAttributes != null && customAttributes.length > 0) {
      subscription.getCustomAttributeValues().addAll(Arrays.asList(customAttributes));
    }

    initAdminRoles(subscription);

    subscription.setSubscriptionType(subscriptionType);
    subscription.setAccountType(acountType);
    subscription.setGsiCsiNumber(gsiCsiNumber);

    Service service = new Service();
    service.setType(serviceType.value());
    if (serviceType.isMetered()) {
      service.setMaxExtensionsAllowed(0L);
      service.setArchiveEnabled(false);
      service.setSoftArchivalEnabled(false);
      service.setTrialPeriod(0L);
      service.setIsFaService(false);
      service.setRoutingTierEnabled(false);

      subscription.setNumOfPendingExtensions(0L);
      subscription.setAssociatedSubscriptionsCount(0L);
      subscription.setNumOfPendingExtensions(0L);
      subscription.setUserCount("0");
      subscription.setNumOfExtensionsDone(0L);
      subscription.setOverageAllowed(false);

    } else {
      service.setDisplayName(serviceDisplayName);
      subscription.setStartDate(startDate);
      subscription.setEndDate(endDate);
    }
    subscription.setService(service);

    IdentityDomain identityDomain = new IdentityDomain();
    identityDomain.setName(identityDomainName);
    subscription.setIdentityDomain(identityDomain);

    ServicePlan plan = new ServicePlan();
    plan.setPlan(serviceSizeCategoryType.value());
    subscription.setPlan(plan);


    orderItem.setSubscription(subscription);
    order.getOrderItems().add(orderItem);
    order.setIdentityDomain(identityDomain);

    return this;
  }

  public TasOrderBuilder associateServices(int addOrderOfServiceToAssociate,
                                           int addOrderOfServiceToAssociateWith) {

    checkMethodInvocationOrder(false);
    StringBuilder errors = new StringBuilder();
    if (addOrderOfServiceToAssociate < 1 ||
        addOrderOfServiceToAssociate > order.getOrderItems().size()) {
      errors.append("Invalid Value -> ( " + addOrderOfServiceToAssociate +
                    " ) Of Param -> ( addOrderOfServiceToAssociate )\n");
    }
    if (addOrderOfServiceToAssociateWith < 1 ||
        addOrderOfServiceToAssociateWith > order.getOrderItems().size()) {
      errors.append("Invalid Value -> ( " + addOrderOfServiceToAssociateWith +
                    " ) Of Param -> ( addOrderOfServiceToAssociateWith )\n");
    }
    if (errors.length() == 0 &&
        addOrderOfServiceToAssociate == addOrderOfServiceToAssociateWith) {
      errors.append("Cannot Associate a Service With ItsSelf... addOrderOfServiceToAssociate -> ( " +
                    addOrderOfServiceToAssociate +
                    " ) addOrderOfServiceToAssociateWith -> ( " +
                    addOrderOfServiceToAssociateWith + ")\n");
    }
    if (errors.length() > 0) {
      throw new IllegalArgumentException(errors.toString());
    }
    order.getOrderItems().get(addOrderOfServiceToAssociateWith -
                              1).getAssociatedOrderItems().add(order.getOrderItems().get(addOrderOfServiceToAssociate -
                                                                                         1));
    return this;
  }

  public Order build() {
    checkMethodInvocationOrder(false);
    return this.order;
  }

  public Order createActivationOrder(String orderId, String identityDomainName,
                                     String creditAccountName) {
    if (orderId == null) {
      throw new IllegalArgumentException("orderId Param is Required");
    }
    Order order = tasCentralRestClient.getOrder(orderId);

    for (OrderItem oi : order.getOrderItems()) {

      Admin admin = oi.getSubscription().getAdmins().getItems().get(0);

      if (identityDomainName == null) {
        identityDomainName = generateSystemName();
      }
      identityDomainName = identityDomainName.toLowerCase();
      this.identityDomainName = identityDomainName;
      setSystemNameCustomProperty();
      IdentityDomain identityDomain = new IdentityDomain();
      identityDomain.setName(identityDomainName);
      oi.getSubscription().setIdentityDomain(identityDomain);

      oi.getSubscription().setServiceAdminUserName(admin.getUserName());
      oi.getSubscription().setServiceAdminContact(admin);
      oi.getSubscription().setServiceDisplayName(oi.getSubscription().getService().getType().toLowerCase() +
                                                 randNumGenerator.nextInt(RAND_NUM_UPPER_BOUND));
      Admin identityAdmin = new Admin();
      Roles roles = new Roles();
      identityAdmin.setAssignedRoles(roles);
      oi.getSubscription().getAdmins().getItems().add(identityAdmin);

      identityAdmin.setUserName(admin.getUserName());
      identityAdmin.setFirstName(admin.getFirstName());
      identityAdmin.setLastName(admin.getLastName());
      identityAdmin.setEmail(admin.getEmail());
      identityAdmin.setId(admin.getId());
      identityAdmin.setCanonicalLink(admin.getCanonicalLink());

      Role role = new Role();
      role.setRole(RoleNames.ADMIN);
      role.setScopeType(ScopeTypes.IDENTITY_DOMAIN);
      roles.getItems().add(role);

      if (creditAccountName != null) {
        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setName(creditAccountName.toLowerCase());
        oi.setCreditAccount(creditAccount);
      }
    }

    return order;

  }

  private void initOrder(boolean newOrder, String adminEmailAddress,
                         String adminUserName, String orderId,
                         String csiNumber,
                         boolean isMetered) throws DatatypeConfigurationException {

    if (adminEmailAddress == null) {
      throw new IllegalArgumentException("adminEmailAddress Param is Required");
    }
    if (!newOrder && orderId == null) {
      throw new IllegalArgumentException("orderId Param is Required");
    }
    if (csiNumber == null) {
      throw new IllegalArgumentException("csiNumber Param is Required");
    }
    Order existingOrder = null;
    if (orderId != null) {
      existingOrder = this.tasCentralRestClient.getOrder(orderId);
    }

    order = new Order();
    if (newOrder) {
      if (isMetered) {
        addCustomProperty("PURCHASED AMOUNT", "100");
        addCustomProperty("ACTIVATOR_EMAIL_ADDRESSES", adminEmailAddress);
      } else {
        addCustomProperty("IS_COTERM", "true");
        addCustomProperty("CUSTOMER_PRIMARY_NAME", "C9QAWWW");
      }
    }
    order.setId(orderId);
    order.setCsiNumber(csiNumber);
    methodOrderSet.clear();

    this.adminEmailAddress = adminEmailAddress;
    if (adminUserName == null) {
      adminUserName = adminEmailAddress;
    }
    this.adminUserName = adminUserName;

    GregorianCalendar gcalStart = new GregorianCalendar();
    GregorianCalendar gcalEnd = new GregorianCalendar();
    gcalStart.add(GregorianCalendar.DAY_OF_MONTH, -1);
    gcalEnd.add(GregorianCalendar.YEAR, 10);
    startDate =
        DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalStart);
    endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalEnd);
    methodOrderSet.set(1);
  }

  private void addCustomProperty(String propName, String propValue) {
    AttributeValue av = new AttributeValue();
    av.setName(propName);
    av.setValue(propValue);
    order.getCustomAttributeValues().add(av);
  }

  private void initAdminRoles(Subscription subscription) {

    Admin admin = new Admin();
    admin.setEmail(adminEmailAddress);
    admin.setUserName(adminUserName);

    Roles roles = new Roles();

    Role role = new Role();
    role.setScopeType(ScopeTypes.SUBSCRIPTION);
    role.setRole(RoleNames.BUYER);
    roles.getItems().add(role);

    role = new Role();
    role.setScopeType(ScopeTypes.IDENTITY_DOMAIN);
    role.setRole(RoleNames.ADMIN);
    roles.getItems().add(role);

    role = new Role();
    role.setScopeType(ScopeTypes.SUBSCRIPTION);
    role.setRole(RoleNames.ADMIN);
    roles.getItems().add(role);

    role = new Role();
    role.setScopeType(ScopeTypes.SERVICE);
    role.setRole(RoleNames.ADMIN);
    roles.getItems().add(role);

    admin.setAssignedRoles(roles);
    Admins admins = new Admins();
    admins.setHasMore(false);

    admins.getItems().add(admin);
    subscription.setAdmins(admins);
    order.setBuyerContact(admin);
    order.setBuyerUserName(adminUserName);

  }

  private void setSystemNameCustomProperty() {
    if (isNewOrder && isAutoComplete) {
      addCustomProperty("SYSTEM_NAME_FOR_AUTO_COMP", identityDomainName);
    }
  }

  private void checkMethodInvocationOrder(boolean allMethods) {
    String methodName =
      Thread.currentThread().getStackTrace()[2].getMethodName();
    MethodsInvocationOrder mio = getMethodInvocationOrder(methodName);
    if (mio != null) {
      logger.info("checkingMethodInvocationOrder...-> ( " + mio +
                  " ) CurrentMethodOrderSet -> " + methodOrderSet);
      mio.verifyPrecedingMethodsCalled(allMethods, methodOrderSet);
    }
  }


  private MethodsInvocationOrder getMethodInvocationOrder(String methodName) {
    MethodsInvocationOrder retmoi = null;
    for (MethodsInvocationOrder mio : methodsInvocationOrderSet) {
      if (mio.methodName.equals(methodName)) {
        retmoi = mio;
        break;
      }
    }
    return retmoi;
  }


  private final static SimpleDateFormat DATE_FORMAT =
    new SimpleDateFormat("MMddHHmmssSSS");

  private String generateSystemName() {
    String systemName =
      "idm" + DATE_FORMAT.format(Calendar.getInstance().getTime()) + "t" +
      randNumGenerator.nextInt(RAND_NUM_UPPER_BOUND);
    logger.info("Generated SystemName -> ( " + systemName + " )");
    return systemName.toLowerCase();
  }

  //For Testing

  public static void main(String[] args) throws DatatypeConfigurationException,
                                                IOException {
    String versionid = "v1";
    String baseURICentral =
      "https://tascentral.c9qa132.oraclecorp.com/tas-central";
    //"http://10.240.218.241:8100//tas-central";
    //"http://slcn06vmf0216.us.oracle.com:8003/tas-central";
    String userId = "weblogic";
    String password = "cloud9_TAS";
    //String password = "welcome1";
    String adminEmailAddress = "c9qa-automation_ww@oracle.com";
    String dataCenterId = "US001";
    String dataCenterRegionId = "US001";
    String gsiNumber = "18711303";
    String accountId = "18779547";
    //String identityDomaiName = "Idm" + System.currentTimeMillis();

    TasCentralRestClient tasCentralHandle =
      TasRestFactory.getTasCentralRestClientHandle(versionid, baseURICentral,
                                                   userId, password,
                                                   adminEmailAddress, null);

    TasOrderBuilder tob = tasCentralHandle.getTasOrderBuilderHandle();
    tob.printMethodsOrder();
    tob.startNewOrder(adminEmailAddress, null, gsiNumber, false,
                      true). // Admin Email + gsiNumber
      addOrderSource(OrderSource.STORE). // Order Origin
      addDataCenter(dataCenterId,
                    dataCenterRegionId). // DataCenter ID and DataCenter Region ID
      addOperation(OperationType.ONBOARDING). // Operation Type
      addSubscription(SubscriptionType.PRODUCTION, null,
                      null). // Subscription Type + IdentityDomainName + CSINumber
      addAccount(AccountType.SINGLE, accountId,
                 null). // Account Type + Account Name
      addService(TasServiceType.JAVA, ServiceSizeCategoryType.BASIC,
                 null). // 1- Service and Catetory and Optionally the ServiceDisplayName
      addService(TasServiceType.APEX, ServiceSizeCategoryType.BASIC,
                 null). // 2- Service and Catetory and Optionally the ServiceDisplayName
      associateServices(2, 1). // Associate Service 2 with Service 1
      build();
    logger.info(tasCentralHandle.getUtilityHandle().toString(tob.build()));

    String xmlStr =
      tasCentralHandle.getUtilityHandle().toXMLString(tob.build());
    logger.info(xmlStr);
    Order order =
      tasCentralHandle.getUtilityHandle().fromXMLString(xmlStr, Order.class);
    logger.info(tasCentralHandle.getUtilityHandle().toString(order));

    File f =
      new File(new File(System.getProperty("java.io.tmpdir")), "Order.xml");
    logger.info("Writing Order To -> " + f.getAbsolutePath());
    tasCentralHandle.getUtilityHandle().toXMLStream(order,
                                                    new FileOutputStream(f));

    logger.info("Reading Order From -> " + f.getAbsolutePath());
    order =
        tasCentralHandle.getUtilityHandle().fromXMLStream(new FileInputStream(f),
                                                          Order.class);
    logger.info(tasCentralHandle.getUtilityHandle().toString(order));
    logger.info(tasCentralHandle.getUtilityHandle().toJSONString(order));


  }
}
