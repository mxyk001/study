package com.oracle.saas.qa.tas.rest.client.test;


import com.oracle.saas.qa.lib.TestConstants;
import com.oracle.saas.qa.tas.rest.client.TasCentralRestClient;
import com.oracle.tas.rest.api.model.AccountType;
import com.oracle.tas.rest.api.model.AppCredential;
import com.oracle.tas.rest.api.model.AppCredentials;
import com.oracle.tas.rest.api.model.CentralVersion;
import com.oracle.tas.rest.api.model.ConsoleRegistration;
import com.oracle.tas.rest.api.model.ConsoleRegistrations;
import com.oracle.tas.rest.api.model.Coupon;
import com.oracle.tas.rest.api.model.Coupons;
import com.oracle.tas.rest.api.model.CreditAccount;
import com.oracle.tas.rest.api.model.CreditAccounts;
import com.oracle.tas.rest.api.model.CreditEntitlement;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstances;
import com.oracle.tas.rest.api.model.CreditEntitlements;
import com.oracle.tas.rest.api.model.Messages;
import com.oracle.tas.rest.api.model.MetricStates;
import com.oracle.tas.rest.api.model.OperationType;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.OrderItem;
import com.oracle.tas.rest.api.model.OrderSource;
import com.oracle.tas.rest.api.model.Redemption;
import com.oracle.tas.rest.api.model.Redemptions;
import com.oracle.tas.rest.api.model.ServiceSizeCategoryType;
import com.oracle.tas.rest.api.model.SubscriptionType;
import com.oracle.tas.rest.api.model.Subscriptions;
import com.oracle.tas.rest.api.model.TasServiceType;
import com.oracle.tas.rest.api.model.UpTimes;
import com.oracle.tas.rest.api.model.Usages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test(groups = { "TasCentralRestApiTests" },
      suiteName = "TasCentralRestApiTests")
public class TasCentralRestApiTest extends TasRestApiTestBase {

  private static final Logger logger =
    Logger.getLogger(TasCentralRestApiTest.class.getName());

  private static final String TAS_CENTRAL_REST_API_GRP =
    "TasCentralRestApiGrp";
  private static final String TAS_CENTRAL_REST_API_CREATE_GRP =
    "TasCentralRestApiCreateGrp";
  private static final String TAS_CENTRAL_REST_API_14_1_6_GRP =
    "TasCentralRestApi_14_16_Grp";
  private static final String TAS_CENTRAL_REST_API_14_1_6_NO_GRP =
    "TasCentralRestApi_14_16_NO_Grp";
  private static final String TAS_CENTRAL_REST_API_UPDT_ORDER_GRP =
    "TasCentralRestApiUpdtOrderGrp";


  @BeforeClass(groups =
               { TAS_COMMON_REST_API_GRP, TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_UPDT_ORDER_GRP,
                 TAS_CENTRAL_REST_API_14_1_6_GRP,
                 TAS_CENTRAL_REST_API_14_1_6_NO_GRP,
                 TAS_CENTRAL_REST_API_CREATE_GRP })
  public void beforeClass() {
    init();
  }


  @Test(groups = { TAS_CENTRAL_REST_API_GRP })
  public void centralVersionQuery() {
    LOG_ENTRY(getLogger(), "centralVersionsQuery...");

    TasCentralRestClient tasCentral =
      getRestApiHandle(TasCentralRestClient.class);
    CentralVersion centralVersion = tasCentral.getCentralVersion();

    LOG_EXIT(getLogger(), "centralVersionQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP })
  public void documentContractsQuery() {
    LOG_ENTRY(getLogger(), "documentContractsQuery...");

    /*TasCentralRestClient tasCentral =
            getRestApiHandle(TasCentralRestClient.class);
        String docId = "12345";
        String docName = "bogusDoc";
        Documents docs = tasCentral.getAllDocumentContracts(docId, docName);*/

    LOG_EXIT(getLogger(), "documentContractsQuery...");
  }


  @Test(groups = { TAS_CENTRAL_REST_API_CREATE_GRP })
  public void createUpdateDeleteOrder() throws DatatypeConfigurationException,
                                               IOException {
    LOG_ENTRY(getLogger(), "createUpdateDeleteOrder...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);


    String adminEmailAddress = this.oracleUserId;

    Order orderReq = tasCentralApi.getTasOrderBuilderHandle(). //Get Handle
      startNewOrder(adminEmailAddress, null, csiNumber, false,
                    true). // Admin Email + csiNumber
      addOrderSource(OrderSource.GSI). // Order Origin
      addDataCenter(dataCenterId,
                    dataCenterRegionId). // DataCenter ID and DataCenter Region ID
      addOperation(OperationType.ONBOARDING). // Operation Type
      addSubscription(SubscriptionType.PRODUCTION, null,
                      null). // Subscription Type + IdentityDomainName
      addAccount(AccountType.SINGLE, accountId,
                 null). // Account Type + AccountId + Account Name
      addService(TasServiceType.APEX, ServiceSizeCategoryType.BASIC,
        //addService(TasServiceType.Developer, ServiceSizeCategoryType.BASIC,
        null). // 1- Service and Catetory and Optionally the ServiceDisplayName
      //addService(TasServiceType.APEX, ServiceSizeCategoryType.BASIC,
      //           null,
      //           null). // 2- Service and Catetory and Optionally the ServiceDisplayName and Boolean to Auto Activate Service
      //associateServices(2, 1). // Associate Service 2 with Service 1
      build();
    //orderReq.setCsiNumber(null);

    writeToFile("Order-Req-" + "Before-send" + ".xml", orderReq);
    Order orderResp = tasCentralApi.createOrder(orderReq);
    writeToFile("Order-Req-" + orderResp.getId() + ".xml", orderReq);
    writeToFile("Order-Resp-" + orderResp.getId() + ".xml", orderResp);


    String xmlStr = tasCentralApi.getUtilityHandle().toXMLString(orderResp);
    logger.info(xmlStr);
    orderResp =
        tasCentralApi.getUtilityHandle().fromXMLString(xmlStr, Order.class);
    logger.info(tasCentralApi.getUtilityHandle().toString(orderResp));

    Order actOrder =
      tasCentralApi.getTasOrderBuilderHandle().createActivationOrder(orderResp.getId(),
                                                                     orderReq.getIdentityDomain().getName(),
                                                                     null);
    writeToFile("Activate-Order-Req-" + orderResp.getId() + ".xml", actOrder);
    actOrder = tasCentralApi.activateOrder(actOrder, actOrder.getId());
    writeToFile("Activate-Order-Resp-" + orderResp.getId() + ".xml", actOrder);

    orderResp = tasCentralApi.getOrder(orderResp.getId());

    //order.getOrderItems().get(0).getSubscription().getPlan().setPlan(ServiceSizeCategoryType.STANDARD.value());
    //order = tasCentralApi.UpdateOrder(order);

    boolean status = tasCentralApi.deleteOrder(orderResp.getId());

    LOG_EXIT(getLogger(), "createUpdateDeleteOrder...Status : " + status);
  }

  // @Test(groups = { TAS_CENTRAL_REST_API_UPDT_ORDER_GRP })

  public void deleteServices() throws DatatypeConfigurationException,
                                      IOException {

    LOG_ENTRY(getLogger(), "deleteServices...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);

    String orderId = "C10033";
    Order theOrder = tasCentralApi.getOrder(orderId);
    writeToFile("Order-Query-" + theOrder.getId() + ".xml", theOrder);

    String adminEmailAddress = this.oracleUserId;

    Order orderReq = tasCentralApi.getTasOrderBuilderHandle(). //Get Handle
      startNewOrder(adminEmailAddress, null, csiNumber, false,
                    false). // Admin Email
      addOrderSource(OrderSource.STORE). // Order Origin
      addDataCenter(dataCenterId,
                    dataCenterRegionId). // DataCenter ID + Data Center Region ID
      addOperation(OperationType.TERMINATION). // Operation Type
      addSubscription(SubscriptionType.PRODUCTION,
                      theOrder.getIdentityDomain().getName(),
                      null). // Subscription Type + IdentityDomainName + CSINumber
      addAccount(AccountType.SINGLE, accountId,
                 theOrder.getAccount().getName()). // Account Type + AccountId + Account Name
      build();
    for (OrderItem orderItem : theOrder.getOrderItems()) {
      orderItem.setOperationType(OperationType.TERMINATION);
    }
    orderReq.getOrderItems().addAll(theOrder.getOrderItems());
    Order orderResp = tasCentralApi.createOrder(orderReq);

    LOG_EXIT(getLogger(), "deleteServices...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_UPDT_ORDER_GRP })
  public void activateOrder() throws DatatypeConfigurationException,
                                     IOException {

    LOG_ENTRY(getLogger(), "activateOrder...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);

    //String orderId = "9999_4521524";
    String orderId = "9999_4521524";
    /*Order theOrder = tasCentralApi.getOrder(orderId);
    theOrder.getOrderItems().get(0).getSubscription().setStatus(SubscriptionStatus.ACTIVATED);
    theOrder.getBuyerContact().setFirstName("FirstName");
    theOrder.getBuyerContact().setLastName("FirstName");
    theOrder.getBuyerContact().setPhone("999-999-9999");*/
    //writeToFile("Activation-Order-" + theOrder.getId() + ".xml", theOrder);


    Order order =
      tasCentralApi.getTasOrderBuilderHandle().createActivationOrder(orderId,
                                                                     "", null);
    writeToFile("Activation-Order-" + order.getId() + ".xml", order);
    //logger.info(tasCentralApi.toString(order));
    //order = tasCentralApi.UpdateOrder(order);
    order = tasCentralApi.activateOrder(order, order.getId());

    LOG_EXIT(getLogger(), "activateOrder...");
  }

  //@Test(groups = { TAS_CENTRAL_REST_API_UPDT_ORDER_GRP })

  public void UpdateOrder() throws DatatypeConfigurationException,
                                   IOException {
    LOG_ENTRY(getLogger(), "UpdateOrder...");
    String orderId = "C10024";
    File f =
      new File(new File(System.getProperty("java.io.tmpdir")), "Order-Query-" +
               orderId + ".xml");
    logger.info("Reading Order From -> " + f.getAbsolutePath());

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);

    Order order =
      tasCentralApi.getUtilityHandle().fromXMLStream(new FileInputStream(f),
                                                     Order.class);
    logger.info(tasCentralApi.getUtilityHandle().toString(order));
    order.getOrderItems().get(0).getSubscription().getPlan().setPlan(ServiceSizeCategoryType.STANDARD.value());
    order = tasCentralApi.activateOrder(order, order.getId());

    LOG_EXIT(getLogger(), "UpdateOrder...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void credentialsQuery() throws Exception {
    //TODO not working getting 403 Forbidden
    /*
         * NFO: 5 * Client out-bound request
          5 > GET https://tascentral.c9qa132.oraclecorp.com/tas-central/ushurricanecotrial05533/tas/api/v1/appCredentials
          5 > Accept: application/json

           Jul 11, 2014 2:43:27 PM com.oracle.saas.qa.tas.rest.client.TasRestClientImpl$3 handle
           INFO: [1] Adding Header Name -> ( X-Oracle-UserId } Value -> ( c9qa-automation_ww@oracle.com )
           Jul 11, 2014 2:43:27 PM com.oracle.saas.qa.tas.rest.client.TasRestClientImpl$3 handle
           INFO: [1] Adding Header Name -> ( X-Oracle-IdentityDomain } Value -> ( ushurricanecotrial05533 )
           Jul 11, 2014 2:43:27 PM com.sun.jersey.api.client.filter.LoggingFilter log
           INFO: 5 * Client in-bound response
          5 < 403
          5 < Date: Fri, 11 Jul 2014 18:43:03 GMT
          5 < Server: Oracle-Application-Server-11g
          5 < Set-Cookie: _WL_AUTHCOOKIE_JSESSIONID=wi2BMzsqHs8-Rnkz5qzq; path=/; secure; HttpOnly
          5 < X-ORACLE-DMS-ECID: 004zUxFkSAQ1JfILIqk3yf0005NK00022J
          5 < X-Powered-By: Servlet/2.5 JSP/2.1
          5 < Keep-Alive: timeout=5, max=496
          5 < Connection: Keep-Alive
          5 < Transfer-Encoding: chunked
          5 < Content-Type: application/json
          5 < Content-Language: en
         5 <
        {"httpStatusCode":403,"httpMessage":"Forbidden","errorCode":"urn:oracle:cloud:errorcode:tas:jersey-error","errorMessage":"javax.ws.rs.WebApplicationException\njavax.ws.rs.WebApplicationException\n\tat com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory$Filter.filter(RolesAllowedResourceFilterFactory.java:130)\n\tat com.sun.jersey.server.impl.uri.rules.HttpMethodRule.accept(HttpMethodRule.java:277)\n\tat com.sun.jersey.server.impl.uri.rules.ResourceClassRule.accept(ResourceClassRule.java:108)\n\tat com.sun.jersey.server.impl.uri.rules.RightHandPathRule.accept(RightHandPathRule.java:147)\n\tat com.sun.jersey.server.impl.uri.rules.RootResourceClassesRule.accept(RootResourceClassesRule.java:84)\n\tat com.sun.jersey.server.impl.application.WebApplicationImpl._handleRequest(WebApplicationImpl.java:1469)\n\tat com.sun.jersey.server.impl.application.WebApplicationImpl._handleRequest(WebApplicationImpl.java:1400)\n\tat com.sun.jersey.server.impl.application.WebApplicationImpl.handleRequest(WebApplicationImpl.java:1349)\n\tat com.sun.jersey.server.impl.application.WebApplicationImpl.handleRequest(WebApplicationImpl.java:1340)\n\tat com.sun.jersey.spi.container.servlet.WebComponent.service(WebComponent.java:417)\n\tat com.sun.jersey.spi.container.servlet.ServletContainer.service(ServletContainer.java:537)\n\tat com.sun.jersey.spi.container.servlet.ServletContainer.service(ServletContainer.java:700)\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:821)\n\tat weblogic.servlet.internal.StubSecurityHelper$ServletServiceAction.run(StubSecurityHelper.java:227)\n\tat weblogic.servlet.internal.ServletStubImpl.execute(ServletStubImpl.java:301)\n\tat weblogic.servlet.internal.TailFilter.doFilter(TailFilter.java:27)\n\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:61)\n\tat oracle.tas.rest.common.filters.RestImpersonationFilter.doFilter(RestImpersonationFilter.java:155)\n\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:61)\n\tat oracle.tas.rest.common.filters.RestDocFilter.doFilter(RestDocFilter.java:52)\n\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:61)\n\tat oracle.security.jps.ee.http.JpsAbsFilter$1.run(JpsAbsFilter.java:119)\n\tat oracle.security.jps.util.JpsSubject.doAsPrivileged(JpsSubject.java:324)\n\tat oracle.security.jps.ee.util.JpsPlatformUtil.runJaasMode(JpsPlatformUtil.java:460)\n\tat oracle.security.jps.ee.http.JpsAbsFilter.runJaasMode(JpsAbsFilter.java:103)\n\tat oracle.security.jps.ee.http.JpsAbsFilter.doFilter(JpsAbsFilter.java:171)\n\tat oracle.security.jps.ee.http.JpsFilter.doFilter(JpsFilter.java:71)\n\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:61)\n\tat oracle.security.wls.filter.SSOSessionSynchronizationFilter.doFilter(SSOSessionSynchronizationFilter.java:419)\n\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:61)\n\tat oracle.dms.servlet.DMSServletFilter.doFilter(DMSServletFilter.java:163)\n\tat weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction.wrapRun(WebAppServletContext.java:3739)\n\tat weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction.run(WebAppServletContext.java:3705)\n\tat weblogic.security.acl.internal.AuthenticatedSubject.doAs(AuthenticatedSubject.java:321)\n\tat weblogic.servlet.internal.WebAppServletContext.execute(WebAppServletContext.java:2181)\n\tat weblogic.servlet.internal.ServletRequestImpl.run(ServletRequestImpl.java:1491)\n\tat weblogic.work.ExecuteThread.execute(ExecuteThread.java:256)\n\tat weblogic.work.ExecuteThread.run(ExecuteThread.java:221)\n"}
        */

    LOG_ENTRY(getLogger(), "credentialsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    AppCredentials appCreds = tasCentralApi.getAppCredentials();
    if (appCreds != null) {
      writeToFile("AppCredentials.xml", appCreds);
      if (appCreds.getItems() != null && appCreds.getItems().size() > 0) {
        String appCredId = appCreds.getItems().get(0).getId();
        AppCredential appCred = tasCentralApi.getAppCredential(appCredId);
        writeToFile("AppCredential-" + appCredId + ".xml", appCred);
      }
    }

    LOG_EXIT(getLogger(), "credentialsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void consoleRegistrationsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "consoleRegistrationsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    ConsoleRegistrations consRegs = tasCentralApi.getConsoleRegistrations();
    if (consRegs != null) {
      writeToFile("ConsoleRegistrations.xml", consRegs);
      if (consRegs.getItems() != null && consRegs.getItems().size() > 0) {
        String consRegId = consRegs.getItems().get(0).getId();
        ConsoleRegistration consReg =
          tasCentralApi.getConsoleRegistration(consRegId);
        writeToFile("ConsoleRegistration-" + consRegId + ".xml", consReg);
      }
    }

    LOG_EXIT(getLogger(), "consoleRegistrationsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void couponsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "couponsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    Coupons coupons = tasCentralApi.getCoupons();
    if (coupons != null) {
      writeToFile("Coupons.xml", coupons);
      if (coupons.getItems() != null && coupons.getItems().size() > 0) {
        String couponId = coupons.getItems().get(0).getId();
        Coupon coupon = tasCentralApi.getCoupon(couponId);
        writeToFile("Coupon-" + couponId + ".xml", coupon);

        Redemptions couponRedmps =
          tasCentralApi.getCouponRedemptions(couponId);
        writeToFile("CouponRedemptions-" + couponId + ".xml", couponRedmps);
        if (couponRedmps.getItems() != null &&
            couponRedmps.getItems().size() > 0) {
          String couponRedmpId = couponRedmps.getItems().get(0).getId();
          Redemption couponRedmp =
            tasCentralApi.getCouponRedemption(couponId, couponRedmpId);
          writeToFile("CouponRedemption-" + couponId + "-" + couponRedmpId +
                      ".xml", couponRedmp);
        }

      }
    }

    LOG_EXIT(getLogger(), "couponsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void creditAccountsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "creditAccountsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    CreditAccounts creditAccts = tasCentralApi.getCreditAccounts();
    if (creditAccts != null) {
      writeToFile("CreditAccounts.xml", creditAccts);
      if (creditAccts.getItems() != null &&
          creditAccts.getItems().size() > 0) {
        String creditAcctId = creditAccts.getItems().get(0).getId();
        CreditAccount creditAcct =
          tasCentralApi.getCreditAccount(creditAcctId);
        writeToFile("CreditAccount-" + creditAcctId + ".xml", creditAcct);
      }
    }

    LOG_EXIT(getLogger(), "creditAccountsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void creditEntitlementServiceInstancesQuery() throws Exception {

    LOG_ENTRY(getLogger(), "creditEntitlementServiceInstancesQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    String entitlementId = "502361172";
    CreditEntitlementServiceInstances cesInsts1 =
      tasCentralApi.getCreditEntitlementServiceInstances(entitlementId);

    /*EntitlementServices cesInsts =
      tasCentralApi.getEntitlementServices(entitlementId);
    if (cesInsts != null) {
      writeToFile("CreditEntitlementServiceInstances.xml", cesInsts);
      if (cesInsts.getItems() != null && cesInsts.getItems().size() > 0) {
        String cesInstId = cesInsts.getItems().get(0).getId();
        CreditEntitlementServiceInstance cesInst =
          tasCentralApi.getCreditEntitlementServiceInstance(cesInstId);
        writeToFile("CreditEntitlementServiceInstance-" + cesInstId + ".xml",
                    cesInst);
      }
    }*/

    LOG_EXIT(getLogger(), "creditEntitlementServiceInstancesQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void creditEntitlementsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "creditEntitlementsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    CreditEntitlements cEnts = tasCentralApi.getCreditEntitlements();
    if (cEnts != null) {
      writeToFile("CreditEntitlements.xml", cEnts);
      if (cEnts.getItems() != null && cEnts.getItems().size() > 0) {
        String cEntId = cEnts.getItems().get(0).getId();
        CreditEntitlement cEnt = tasCentralApi.getCreditEntitlement(cEntId);
        writeToFile("CreditEntitlement-" + cEntId + ".xml", cEnt);
      }
    }

    LOG_EXIT(getLogger(), "creditEntitlementsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void entitlementServicesQuery() throws Exception {
    //TODO not working
    /*
         * 9 > GET https://tascentral.c9qa132.oraclecorp.com/tas-central/ushurricanecotrial05533/tas/api/v1/entitlementServices
           9 > Accept: application/json

           Jul 11, 2014 2:43:28 PM com.oracle.saas.qa.tas.rest.client.TasRestClientImpl$3 handle
           INFO: [1] Adding Header Name -> ( X-Oracle-UserId } Value -> ( c9qa-automation_ww@oracle.com )
           Jul 11, 2014 2:43:28 PM com.oracle.saas.qa.tas.rest.client.TasRestClientImpl$3 handle
           INFO: [1] Adding Header Name -> ( X-Oracle-IdentityDomain } Value -> ( ushurricanecotrial05533 )
           Jul 11, 2014 2:43:28 PM com.sun.jersey.api.client.filter.LoggingFilter log
           INFO: 9 * Client in-bound response
           9 < 400
           9 < Date: Fri, 11 Jul 2014 18:43:05 GMT
           9 < Server: Oracle-Application-Server-11g
           9 < Set-Cookie: _WL_AUTHCOOKIE_JSESSIONID=9ydZPKfxtaAgAmkkOGbL; path=/; secure; HttpOnly
           9 < X-ORACLE-DMS-ECID: 004zUxFpmGB1JfILIqk3yf0005NK00022N
           9 < X-Powered-By: Servlet/2.5 JSP/2.1
           9 < Connection: close
           9 < Transfer-Encoding: chunked
           9 < Content-Type: application/json
           9 < Content-Language: en
           9 <
{"httpStatusCode":400,"httpMessage":"Bad Request","errorCode":"urn:oracle:cloud:errorcode:tas:spring-error","errorMessage":"org.springframework.dao.DataIntegrityViolationException: CallableStatementCallback; SQL [{call TRA_ENTITLEMENT_SERVICE_PKG.GET_ENTITLEMENT_SERVICES(?, ?, ?, ?, ?, ?, ?, ?, ?)}]; ORA-01722: invalid number\nORA-06512: at \"TAS.TRA_ENTITLEMENT_SERVICE_PKG\", line 60\nORA-06512: at line 1\n; nested exception is java.sql.SQLSyntaxErrorException: ORA-01722: invalid number\nORA-06512: at \"TAS.TRA_ENTITLEMENT_SERVICE_PKG\", line 60\nORA-06512: at line 1\n"}


         */
    LOG_ENTRY(getLogger(), "entitlementServicesQuery...");
    String parentServiceId = "88888";
    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    /*EntitlementServices envSvcs =
      tasCentralApi.getEntitlementServices(parentServiceId);
    if (envSvcs != null) {
      writeToFile("EntitlementServices.xml", envSvcs);
      if (envSvcs.getItems() != null && envSvcs.getItems().size() > 0) {
        String envSvcId = envSvcs.getItems().get(0).getId();
        EntitlementService envSvc =
          tasCentralApi.getEntitlementService(envSvcId);
        writeToFile("EntitlementService-" + envSvcId + ".xml", envSvc);
      }
    }*/

    LOG_EXIT(getLogger(), "entitlementServicesQuery...");
  }


  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void messsagesQuery() throws Exception {

    LOG_ENTRY(getLogger(), "messsagesQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    Messages messages = tasCentralApi.getMessages();
    if (messages != null) {
      writeToFile("Messages.xml", messages);
    }

    LOG_EXIT(getLogger(), "messsagesQuery...");
  }


  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void metricStatesQuery() throws Exception {

    LOG_ENTRY(getLogger(), "metricStatesQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    MetricStates metricStates = tasCentralApi.getMetricStates();
    if (metricStates != null) {
      writeToFile("MetricStates.xml", metricStates);
    }

    LOG_EXIT(getLogger(), "metricStatesQuery...");
  }


  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void upTimesMetricsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "upTimesMetricsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    UpTimes upTimesmetric = tasCentralApi.getUpTimeMetrics();
    if (upTimesmetric != null) {
      writeToFile("UpTimesMetrics.xml", upTimesmetric);
    }

    LOG_EXIT(getLogger(), "upTimesMetricsQuery...");
  }

  @Test(groups = { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP })
  public void usageMetricsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "usageMetricsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    Usages usageMetrics = tasCentralApi.getUsageMetrics("500078560", null);
    if (usageMetrics != null) {
      writeToFile("UsageMetrics.xml", usageMetrics);
    }

    LOG_EXIT(getLogger(), "usageMetricsQuery...");
  }


  @Test(groups =
        { TAS_CENTRAL_REST_API_GRP, TAS_CENTRAL_REST_API_14_1_6_GRP, TAS_CENTRAL_REST_API_14_1_6_NO_GRP })
  public void getCentralSubscriptionsQuery() throws Exception {

    LOG_ENTRY(getLogger(), "getCentralSubscriptionsQuery...");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);
    String identitydomainname = "ushurricanecotrial05533";
    Subscriptions subscriptions =
      tasCentralApi.getAllSubscriptions(null, null, null, null, null,
                                        identitydomainname, null, null, null,
                                        null, null, null, null, null, null,
                                        null, null, null, null, null, null,
                                        null, null, null);
    if (subscriptions != null) {
      writeToFile("SubscriptionsCentral.xml", subscriptions);
    }

    LOG_EXIT(getLogger(), "getCentralSubscriptionsQuery...");
  }


  Logger getLogger() {
    return logger;
  }

  //_______________________________________________
  // For Testing this Class

  public static void main(String[] args) {
    //String idmName = "meter2015101904";
    //System.setProperty(IDM_NM_SYS_PROP, idmName);
    System.setProperty(TestConstants.LAB_ENV_PROPERTY, "c9qa132");
    System.setProperty(TestConstants.DATA_CENTER, "dc1");
    //System.setProperty(TestConstants.LAB_ENV_PROPERTY, "MATS4");
    System.setProperty(TestConstants.REDIS_HOST,
                       "slcn03vmf0254.us.oracle.com");
    System.setProperty(TestConstants.REDIS_KEY, "TAS");


    TestNG testng = new TestNG();
    testng.setTestClasses(new Class[] { TasCentralRestApiTest.class });
    testng.setVerbose(-1);
    testng.setGroups(TAS_CENTRAL_REST_API_GRP);
    //testng.setGroups(TAS_CENTRAL_REST_API_UPDT_ORDER_GRP);
    //testng.setGroups(TAS_CENTRAL_REST_API_14_1_6_NO_GRP);
    //testng.setGroups(TAS_CENTRAL_REST_API_14_1_6_GRP);
    //testng.setGroups(TAS_COMMON_REST_API_GRP);
    //testng.setGroups(TAS_COMMON_REST_API_GRP + "," +
    //                 TAS_CENTRAL_REST_API_GRP);
    //testng.setGroups(TAS_CENTRAL_REST_API_CREATE_GRP);
    TestListenerAdapter tla = new TestListenerAdapter();
    testng.addListener(tla);
    testng.run();
    System.out.println(tla);
    System.out.println("Exiting Main....");
    System.exit(0);
  }

}
