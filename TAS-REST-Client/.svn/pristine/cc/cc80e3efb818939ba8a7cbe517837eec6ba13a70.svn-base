package com.oracle.saas.qa.tas.rest.client.test;


import com.oracle.cim.rest.api.model.Attribute;
import com.oracle.cim.rest.api.model.Instance;
import com.oracle.cim.rest.api.model.Payload;
import com.oracle.cim.rest.api.model.Request;
import com.oracle.cim.rest.api.model.RequestType;
import com.oracle.cloudidentity.rest.api.model.NewUser;
import com.oracle.cloudidentity.rest.api.model.Role;
import com.oracle.cloudidentity.rest.api.model.RoleGrant;
import com.oracle.cloudidentity.rest.api.model.Roles;
import com.oracle.cloudidentity.rest.api.model.User;
import com.oracle.cloudidentity.rest.api.model.Users;
import com.oracle.saas.qa.cim.rest.client.CommonInstanceMgrRestClient;
import com.oracle.saas.qa.cloudidentity.rest.client.CloudIdentityRestClient;
import com.oracle.saas.qa.dbaas.rest.resources.DbaasProvisioningEndpoint;
import com.oracle.saas.qa.dbaas.rest.resources.DbaasServiceInstanceEndpoint;
import com.oracle.saas.qa.jaas.rest.resources.JaasProvisioningEndpoint;
import com.oracle.saas.qa.jaas.rest.resources.JaasServiceInstanceEndpoint;
import com.oracle.saas.qa.lib.RedisParamsDataProvider;
import com.oracle.saas.qa.lib.TestConstants;
import com.oracle.saas.qa.lib.keys.SSO;
import com.oracle.saas.qa.lib.keys.TAS;
import com.oracle.saas.qa.lib.mail.OpcActivationEmail;
import com.oracle.saas.qa.lib.mail.OpcInstanceReadyEmail;
import com.oracle.saas.qa.lib.mail.OpcJavaMailUtil;
import com.oracle.saas.qa.lib.mail.OpcProvisioningCompleteEmail;
import com.oracle.saas.qa.psm.rest.common.PsmClientResponse;
import com.oracle.saas.qa.psm.rest.common.PsmJsonFields;
import com.oracle.saas.qa.psm.rest.common.PsmPayloadType;
import com.oracle.saas.qa.psm.rest.common.PsmServiceType;
import com.oracle.saas.qa.psm.rest.resources.PsmBaseResource;
import com.oracle.saas.qa.tas.rest.client.TasCentralRestClient;
import com.oracle.saas.qa.tas.rest.client.TasOrderBuilder;
import com.oracle.saas.qa.tas.rest.client.TasProvisioningRunManager;
import com.oracle.saas.qa.tas.rest.client.TasRestFactory;
import com.oracle.tas.rest.api.model.AccountType;
import com.oracle.tas.rest.api.model.AttributeValue;
import com.oracle.tas.rest.api.model.CreditAccount;
import com.oracle.tas.rest.api.model.CreditAccounts;
import com.oracle.tas.rest.api.model.CreditEntitlement;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstance;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstances;
import com.oracle.tas.rest.api.model.EntitlementRunType;
import com.oracle.tas.rest.api.model.EntitlementType;
import com.oracle.tas.rest.api.model.OperationType;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.OrderSource;
import com.oracle.tas.rest.api.model.ServiceInstanceDetails;
import com.oracle.tas.rest.api.model.ServiceSizeCategoryType;
import com.oracle.tas.rest.api.model.SubscriptionType;
import com.oracle.tas.rest.api.model.TasServiceType;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.security.GeneralSecurityException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;


/**
 * SBE and MBE Provisioning
 *
 * @author Haris Shah
 */
@Test(groups = { "TasProvisioningTests" }, suiteName = "TasProvisioningSuite")
public class TasProvisioningTest extends TasRestApiTestCommonBase {
  private static final Logger logger =
    Logger.getLogger(TasProvisioningTest.class.getName());


  private static final String TAS_PROVISIONING_GRP = "TasProvisioningGrp";
  private static final String TAS_DELETE_GRP = "TasDeleteGrp";


  private static final SimpleDateFormat s =
    new SimpleDateFormat("yyyyMMddHHmmss");

  private static String labEnvironment;
  private static String dataCenter;
  private static EntitlementRunType entitlementRunType;
  private static Set<String> creditTypes;
  private static List<AttributeValue> customAttributes;
  private static boolean defaultCustomAttributes;
  private static ServiceInstanceDetails svcInstDetails;
  private static String accountName;
  private static String identityDomain;
  private static String orderId;
  private static String domainAdminId;
  private static String domainAdminPassword;

  private static final String gsiNumber =
    String.valueOf(System.currentTimeMillis());
  private static final String accountId =
    String.valueOf(System.currentTimeMillis());


  private static Order seedOrderResp;
  private static OpcActivationEmail activationEmail;
  private static Order activationOrderResp;
  private static String creditAccountName;
  private static OpcProvisioningCompleteEmail provisioningEmail;
  private static OpcInstanceReadyEmail instanceReadyEmail;
  private static boolean activationComplete;
  private static boolean provisioningComplete;
  private static boolean instanceReadyComplete;
  private static Request cimRequest;
  private static Instance cimInstance;
  private static Integer mcsSeq;
  private static OpcJavaMailUtil jmUtil;
  private static boolean autoCompletionEnabled;

  private static Set<String> skipMethodNames = new HashSet<String>();

  private static TasProvisioningRunManager tasProvisioningRunManager =
    new TasProvisioningRunManager();

  static final String VALS_SEP = ",";
  static final String NAME_VAL_PAIR_SEP = ":";


  private static final String NEW_PASSWORD = "Welcom1$";


  private static McsUserAndRoles[] mcsUserAndRoles1;
  private static McsUserAndRoles[] mcsUserAndRoles2;

  static {

    McsUserAndRoles[] mcsUserAndRoles1 =
    { new McsUserAndRoles("awuser", "Welcom1$", McsRole.Messaging_Worker,
                          McsRole.Messaging_Administrator),
      new McsUserAndRoles("auser", "Welcom1$",
                          McsRole.Messaging_Administrator),
      new McsUserAndRoles("wuser", "Welcom1$", McsRole.Messaging_Worker) };
    TasProvisioningTest.mcsUserAndRoles1 = mcsUserAndRoles1;

    McsUserAndRoles[] mcsUserAndRoles2 =
    { new McsUserAndRoles("awuser2", "Welcom2$", McsRole.Messaging_Worker,
                          McsRole.Messaging_Administrator),
      new McsUserAndRoles("auser2", "Welcom2$",
                          McsRole.Messaging_Administrator),
      new McsUserAndRoles("wuser2", "Welcom2$", McsRole.Messaging_Worker) };
    TasProvisioningTest.mcsUserAndRoles2 = mcsUserAndRoles2;
  }

  /**
   * System/Environment Properties needed by the Tests.
   */
  enum TasProvisioningTestSysEnvProps {

    LAB_NAME(TestConstants.LAB_ENV_PROPERTY, null),
    DATA_CENTER(TestConstants.DATA_CENTER, null),
    ENTITELEMENT_RUN_TYPE("ent.run.type", null),
    ORDER_ID("order.id", null),
    SKIP_METHODS("skip.methods", null),
    CREDIT_TYPES("credit.types", null), //comma sep list of JAVAMB,DBMB,IAASMB
    ACCOUNT_NAME("acct.name", null),
    IDENTITY_DOMAIN_NAME("idm.name", null),
    SVC_INSTANCE_TYPE("svc.inst.type", null), //Messaging,ICS etc
    SVC_INSTANCE_SIZE("svc.inst.size", null),
    SVC_INSTANCE_NAME("svc.inst.name", null),
    SVC_ENTITLEMENT_ID("svc.entitlement.id", null),
    SVC_INSTANCE_CREDIT_TYPE("svc.inst.credit.type",
                             null), ///one of JAVAMB,DBMB,IAASMB etc
    SVC_CUSTOM_ATTRIBUTES("svc.custom.attributes",
                          null), //Pattern : name:value,name:value
    CUSTOM_ATTRIBUTES("custom.attributes",
                      null), //Pattern : name:value,name:value
    DOMAIN_ADMIN_ID("domain.admin.id", null),
    CHECK_ACTIVATION_EMAIL_BACK_DAYS("check.act.email.back.days", "0"),
    ACTIVATION_EMAIL_WAIT_MINS("act.email.wait.mins", "60"),
    CHECK_PROVISIONING_EMAIL_BACK_DAYS("check.prov.email.back.days", "0"),
    PROVISIONING_EMAIL_WAIT_MINS("prov.email.wait.mins", "120"),
    CHECK_INSTANCE_READY_EMAIL_BACK_DAYS("check.inst.ready.email.back.days",
                                         "0"),
    INSTANCE_READY_EMAIL_WAIT_MINS("inst.ready.email.wait.mins", "120"),
    MCS_USER_SEQ("mcs.user.seq", null),
    AUTO_COMPLETION_ENABLED("auto.completion.enabled", null);

    String propName;
    String defaultValue;

    TasProvisioningTestSysEnvProps(String propName, String defaultValue) {
      this.propName = propName;
      this.defaultValue = defaultValue;
    }

    String getPropName() {
      return propName;
    }


    boolean hasDefaultValue() {
      return this.defaultValue != null;
    }

    String getDefaultValue() {
      return this.defaultValue;
    }

    static ServiceInstanceDetails[] buildTasSvcTypeAndSize(EntitlementRunType entitlementRunType,
                                                           Set<String> creditTypes,
                                                           TasServiceType svcType,
                                                           ServiceSizeCategoryType svcSize,
                                                           List<AttributeValue> customAttributes) {
      ServiceInstanceDetails[] svcsDetails = null;
      if (entitlementRunType.isCloudCredit()) {
        String sizes = ServiceSizeCategoryType.CUSTOM.name();
        String[] svcSizes = null;
        if (sizes != null) {
          svcSizes = sizes.split(VALS_SEP);
        }
        svcsDetails = new ServiceInstanceDetails[creditTypes.size()];
        Iterator<String> it = creditTypes.iterator();
        for (int i = 0; i < creditTypes.size() && it.hasNext(); ++i) {
          String creditType = it.next();
          if (creditType != null && creditType.trim().length() > 0 &&
              !NONE_VAL.equalsIgnoreCase(creditType.trim())) {
            ServiceInstanceDetails svcDetails = new ServiceInstanceDetails();
            svcDetails.setSvcInstTypeStr(TasServiceType.findValue(creditType.trim(),
                                                                  true));
            svcDetails.setSvcInstSize(svcSizes != null &&
                                      svcSizes.length > i &&
                                      svcSizes[i].trim().length() > 0 &&
                                      !NONE_VAL.equalsIgnoreCase(svcSizes[i].trim()) ?
                                      ServiceSizeCategoryType.findValue(svcSizes[i].trim(),
                                                                        true) :
                                      ServiceSizeCategoryType.CUSTOM);
            svcDetails.setCustomAttributes(customAttributes);
            svcsDetails[i] = svcDetails;
          }
        }
      } else {
        if (entitlementRunType.isSrvcEntitlement()) {
          svcSize = ServiceSizeCategoryType.CUSTOM;
        }
        svcsDetails = new ServiceInstanceDetails[1];
        ServiceInstanceDetails svcDetails = new ServiceInstanceDetails();
        svcDetails.setSvcInstTypeStr(svcType);
        svcDetails.setSvcInstSize(svcSize);
        svcDetails.setCustomAttributes(customAttributes);
        svcsDetails[0] = svcDetails;
      }
      return svcsDetails;
    }

    public static String getStringPropValue(TasProvisioningTestSysEnvProps sysEnvProp,
                                            boolean failIfNull) {
      return getPropValue(sysEnvProp, failIfNull);
    }

    public static long getTimerPropValueMillis(TasProvisioningTestSysEnvProps sysEnvProp) {
      return getIntegerPropValue(sysEnvProp, true) * 1000;
    }

    public static Integer getIntegerPropValue(TasProvisioningTestSysEnvProps sysEnvProp,
                                              boolean failIfNull) {
      Integer retIntegerProp = null;
      String propVal = getPropValue(sysEnvProp, failIfNull);
      if (propVal != null) {
        retIntegerProp = Integer.valueOf(propVal);
      }
      return retIntegerProp;
    }

    public static Long getLongPropValue(TasProvisioningTestSysEnvProps sysEnvProp,
                                        boolean failIfNull) {
      Long retLongProp = null;
      String propVal = getPropValue(sysEnvProp, failIfNull);
      if (propVal != null) {
        retLongProp = Long.valueOf(propVal);
      }
      return retLongProp;
    }

    public static Boolean getBooleanPropValue(TasProvisioningTestSysEnvProps sysEnvProp,
                                              boolean failIfNull) {
      Boolean retBooleanProp = null;
      String propVal = getPropValue(sysEnvProp, failIfNull);
      if (propVal != null) {
        retBooleanProp = Boolean.valueOf(propVal);
      }
      return retBooleanProp;
    }

    private static String getPropValue(TasProvisioningTestSysEnvProps sysEnvProp,
                                       boolean failIfNull) {
      if (sysEnvProp == null) {
        throw new RuntimeException("Property Param is NULL");
      }

      String propStrVal = System.getProperty(sysEnvProp.getPropName());
      if (propStrVal == null || "".equals(propStrVal.trim())) {
        propStrVal = System.getenv(sysEnvProp.getPropName());
      }
      if (propStrVal == null || "".equals(propStrVal.trim())) {
        propStrVal = null;
      } else if (propStrVal != null &&
                 NONE_VAL.equalsIgnoreCase(propStrVal.trim())) {
        propStrVal = null;
      }
      if ((propStrVal == null || "".equals(propStrVal.trim())) &&
          sysEnvProp.hasDefaultValue()) {
        propStrVal = sysEnvProp.getDefaultValue().toString();
      }
      if (failIfNull && propStrVal == null) {
        throw new RuntimeException("SysEnvProperty NOT found and Does Not have a default Value : " +
                                   sysEnvProp.toString());
      } else if (propStrVal != null) {
        propStrVal = propStrVal.trim();
      }
      return propStrVal;
    }

    @Override
    public String toString() {
      return "SysEnvPropName -> ( " + getPropName() +
        " ) HasDefaultValue -> ( " + hasDefaultValue() +
        " ) DefaultValue -> ( " + getDefaultValue() + " ) ";
    }
  }


  @BeforeClass(groups = { TAS_PROVISIONING_GRP, TAS_DELETE_GRP })
  public void beforeClass() {
    initProvisioning();
    this.labEnv = labEnvironment;
    this.dc = dataCenter;
    init();
    this.idenitydomainname = identityDomain;
  }

  @BeforeMethod(groups = { TAS_PROVISIONING_GRP })
  public void beforeMethod() {
    saveProperties(false);
  }

  @AfterMethod(groups = { TAS_PROVISIONING_GRP })
  public void afterMethod() {
    saveProperties(false);
  }

  @AfterClass(groups = { TAS_PROVISIONING_GRP })
  public void afterClass() throws IOException, JsonParseException,
                                  JsonMappingException {
    saveProperties(true);
    logger.info("\n" +
        tasProvisioningRunManager.getSummary() + "\n");
  }

  @Test(groups = { TAS_PROVISIONING_GRP })
  public void seedProvisioningOrder() throws IOException, JAXBException,
                                             DatatypeConfigurationException {

    LOG_ENTRY(getLogger(), "seedProvisioningOrder...");

    assertNotNull(entitlementRunType, "entitlementRunType is required");
    if (entitlementRunType.isSrvcEntitlement() ||
        entitlementRunType.isSrvcInstance()) {
      assertNotNull(svcInstDetails.getSvcInstType(),
                    "svcInstType is required");
      if (entitlementRunType.isSrvcInstance()) {
        assertNotNull(svcInstDetails.getSvcInstSize(),
                      "svcInstSize is required");
      }
    } else if (entitlementRunType.isCloudCredit()) {
      assertNotNull(creditTypes, "creditTypes is required");
    }


    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);

    Order seedOrderReq =
      createSeedOrder(tasCentralApi, entitlementRunType.isCloudCredit(),
                      autoCompletionEnabled,
                      TasProvisioningTestSysEnvProps.buildTasSvcTypeAndSize(entitlementRunType,
                                                                            creditTypes,
                                                                            svcInstDetails.getSvcInstType(),
                                                                            svcInstDetails.getSvcInstSize(),
                                                                            svcInstDetails.getCustomAttributes()));

    this.accountName = seedOrderReq.getAccount().getName();
    this.idenitydomainname = seedOrderReq.getIdentityDomain().getName();

    writeToFile("seedOrderReq", seedOrderReq, FileType.XML);

    seedOrderResp = tasCentralApi.createOrder(seedOrderReq);

    assertNotNull(seedOrderResp,
                  "Unable to Create Seed Order:\n : " + tasRestCommonApi.getUtilityHandle().toString(seedOrderReq));

    orderId = seedOrderResp.getId();

    writeToFile("seedOrderReq-" + seedOrderResp.getId(), seedOrderReq,
                FileType.JSON);
    writeToFile("seedOrderReq-" + seedOrderResp.getId(), seedOrderReq,
                FileType.XML);
    writeToFile("seedOrderResp-" + seedOrderResp.getId(), seedOrderResp,
                FileType.JSON);
    writeToFile("seedOrderResp-" + seedOrderResp.getId(), seedOrderResp,
                FileType.XML);

    assertNotNull(orderId, "orderId is required");

    if (entitlementRunType.isSrvcInstance() && autoCompletionEnabled) {
      svcInstDetails.setSvcInstName(seedOrderResp.getOrderItems().get(0).getSubscription().getServiceDisplayName());
      assertNotNull(svcInstDetails.getSvcInstName(),
                    "svcInstName is required");

      identityDomain =
          seedOrderResp.getOrderItems().get(0).getSubscription().getIdentityDomain().getName();
      assertNotNull(identityDomain, "identityDomain is required");

    }

    tasProvisioningRunManager.addCompletedTestMethods("seedProvisioningOrder");

    LOG_EXIT(getLogger(), "seedProvisioningOrder...Seed Order :\n" +
        tasCentralApi.getUtilityHandle().toString(seedOrderResp));
  }


  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "seedProvisioningOrder" })
  public void checkActivationEmail() throws MessagingException, IOException,
                                            GeneralSecurityException {

    LOG_ENTRY(getLogger(), "checkActivationEmail...");

    assertNotNull(orderId, "orderId is required");

    activationEmail =
        jmUtil.getActivationEmail(OpcJavaMailUtil.QueryType.ORDER_ID, orderId,
                                  TasProvisioningTestSysEnvProps.getIntegerPropValue(TasProvisioningTestSysEnvProps.CHECK_ACTIVATION_EMAIL_BACK_DAYS,
                                                                                     true),
                                  TasProvisioningTestSysEnvProps.getLongPropValue(TasProvisioningTestSysEnvProps.ACTIVATION_EMAIL_WAIT_MINS,
                                                                                  true));

    assertNotNull(activationEmail,
                  "Could Not Get Activation Email...OrderId : " + orderId);

    tasProvisioningRunManager.addCompletedTestMethods("checkActivationEmail");

    LOG_EXIT(getLogger(), "checkActivationEmail...\n" +
        activationEmail);

  }

  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "checkActivationEmail" })
  public void activateProvisioningOrder() throws IOException, JAXBException,
                                                 DatatypeConfigurationException {

    LOG_ENTRY(getLogger(), "activateProvisioningOrder...");

    assertNotNull(entitlementRunType, "entitlementRunType is required");
    assertNotNull(orderId, "orderId is required");

    TasCentralRestClient tasCentralApi =
      getRestApiHandle(TasCentralRestClient.class);

    creditAccountName = null;
    if (entitlementRunType.isCloudCredit()) {
      creditAccountName = "meter" + s.format(Calendar.getInstance().getTime());
    } else if (entitlementRunType.isSrvcEntitlement()) {
      creditAccountName = "subsr" + s.format(Calendar.getInstance().getTime());
    }

    Order activationOrderReq =
      createActivationOrder(tasCentralApi, orderId, creditAccountName);

    writeToFile("activationOrderReq-" + activationOrderReq.getId(),
                activationOrderReq, FileType.JSON);
    writeToFile("activationOrderReq-" + activationOrderReq.getId(),
                activationOrderReq, FileType.XML);

    activationOrderResp =
        tasCentralApi.activateOrder(activationOrderReq, orderId);

    assertNotNull(activationOrderResp,
                  "Could Not Activate Order...OrderId : " + orderId);

    writeToFile("activationOrderResp-" + activationOrderResp.getId(),
                activationOrderResp, FileType.JSON);
    writeToFile("activationOrderResp-" + activationOrderResp.getId(),
                activationOrderResp, FileType.XML);

    if (entitlementRunType.isSrvcInstance()) {
      svcInstDetails.setSvcInstName(activationOrderResp.getOrderItems().get(0).getSubscription().getServiceDisplayName());
      assertNotNull(svcInstDetails.getSvcInstName(),
                    "svcInstName is required");
    }

    tasProvisioningRunManager.addCompletedTestMethods("activateProvisioningOrder");

    LOG_EXIT(getLogger(), "activateProvisioningOrder...Activation Order :\n" +
        tasCentralApi.getUtilityHandle().toString(activationOrderResp));

  }


  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "activateProvisioningOrder" })
  public void checkProvisioningEmail() throws MessagingException, IOException,
                                              GeneralSecurityException {

    LOG_ENTRY(getLogger(), "checkProvisioningEmail...");

    assertNotNull(orderId, "orderId is required");

    provisioningEmail =
        jmUtil.getProvisioningCompleteEmail(OpcJavaMailUtil.QueryType.ORDER_ID,
                                            orderId,
                                            TasProvisioningTestSysEnvProps.getIntegerPropValue(TasProvisioningTestSysEnvProps.CHECK_PROVISIONING_EMAIL_BACK_DAYS,
                                                                                               true),
                                            TasProvisioningTestSysEnvProps.getLongPropValue(TasProvisioningTestSysEnvProps.PROVISIONING_EMAIL_WAIT_MINS,
                                                                                            true));

    assertNotNull(provisioningEmail,
                  "Could Not Get Provisioning Email...OrderId : " + orderId);

    identityDomain = provisioningEmail.getIdentityDomain();
    domainAdminId = provisioningEmail.getUserName();

    assertNotNull(identityDomain, "identityDomain is required");
    assertNotNull(domainAdminId, "domainAdminId is required");

    tasProvisioningRunManager.addCompletedTestMethods("checkProvisioningEmail");

    LOG_EXIT(getLogger(), "checkProvisioningEmail...\n" +
        provisioningEmail);

  }


  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "checkProvisioningEmail" })
  public void changeTempPassword() throws MessagingException, IOException,
                                          GeneralSecurityException {

    LOG_ENTRY(getLogger(), "changeTempPassword...");

    assertNotNull(provisioningEmail, "identityDomain is required");
    assertNotNull(provisioningEmail.getIdentityDomain(),
                  "provisioningEmail.getIdentityDomain() is required");
    assertNotNull(provisioningEmail.getUserName(),
                  "provisioningEmail.getUserName() is required");
    assertNotNull(provisioningEmail.getUserName(),
                  "provisioningEmail.getUserName() is required");
    assertNotNull(provisioningEmail.getTempPassword(),
                  "provisioningEmail.getTempPassword() is required");

    sleep(90 * 1000, "changeTempPassword");

    CloudIdentityRestClient cloudIdentityRestClient =
      getCloudIdentityRestClient(provisioningEmail.getIdentityDomain(),
                                 provisioningEmail.getUserName(),
                                 provisioningEmail.getUserName(),
                                 provisioningEmail.getTempPassword());
    cloudIdentityRestClient.updatePassword(provisioningEmail.getTempPassword(),
                                           NEW_PASSWORD);

    domainAdminPassword = NEW_PASSWORD;

    tasProvisioningRunManager.addCompletedTestMethods("changeTempPassword");

    LOG_EXIT(getLogger(), "changeTempPassword...\n" +
        provisioningEmail);

  }

  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "checkProvisioningEmail" })
  public void createServiceInstance() throws IOException, JAXBException,
                                             URISyntaxException {

    LOG_ENTRY(getLogger(), "createServiceInstance...");

    assertNotNull(entitlementRunType, "entitlementRunType is required");
    assertNotNull(identityDomain, "identityDomain is required");
    assertNotNull(domainAdminId, "domainAdminId is required");
    assertNotNull(oracleUserId, "oracleUserId is required");
    assertNotNull(svcInstDetails, "svcInstDetails is required");
    assertNotNull(svcInstDetails.getSvcInstType(), "svcInstType is required");
    assertNotNull(svcInstDetails.getSvcInstSize(), "svcInstSize is required");
    if (entitlementRunType.isCloudCredit()) {
      assertNotNull(svcInstDetails.getSvcInstCreditType(),
                    "svcInstCreditType is required");
    }

    sleep(90 * 1000, "createServiceInstance");

    CommonInstanceMgrRestClient cimClient =
      getCommonInstanceMgrRestClient(identityDomain, domainAdminId);

    TasCentralRestClient tasCentralApi =
      TasRestFactory.getTasCentralRestClientHandle(params.get(paramsKeyPrefix +
                                                              TAS.REST_API_VERSION.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_BASE_URI.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_ORACLE_USER.toString()),
                                                   params.get(paramsKeyPrefix +
                                                              TAS.REST_ORACLE_PASSWORD.toString()),
                                                   oracleUserId,
                                                   identityDomain);


    svcInstDetails.setSvcInstEntitlementId(getEntitlementId(tasCentralApi,
                                                            identityDomain,
                                                            svcInstDetails.getSvcInstCreditType(),
                                                            svcInstDetails.getSvcInstType().name()));

    assertNotNull(svcInstDetails.getSvcInstEntitlementId(),
                  "Could Not Get Entitlement ID for ...Identity Domain : " +
                  identityDomain + " CreditType : " +
                  svcInstDetails.getSvcInstCreditType() + " ServiceType : " +
                  svcInstDetails.getSvcInstType().name());

    if (svcInstDetails.getSvcInstType().isPsmBasedProvisioning()) {
      createPsmService();
    } else {
      cimRequest =
          createCimRequest(svcInstDetails.getSvcInstType().name(), svcInstDetails.getSvcInstSize().name(),
                           svcInstDetails.getSvcInstName(),
                           svcInstDetails.getSvcInstEntitlementId(),
                           svcInstDetails.getCustomAttributes() == null ?
                           customAttributes :
                           svcInstDetails.getCustomAttributes());


      cimRequest = cimClient.submitRequest(cimRequest);
      writeToFile("CIM-Request-" + cimRequest.getId(), cimRequest,
                  FileType.XML);

      cimInstance = cimClient.getInstance(cimRequest.getInstanceId());
      svcInstDetails.setSvcInstName(cimInstance.getName());
      writeToFile("CIM-Instance-" + cimInstance.getId(), cimInstance,
                  FileType.XML);
      URI uri = cimInstance.getLinks().get(0).getUri();
      logger.info(uri.toString());

      assertNotNull(svcInstDetails.getSvcInstName(),
                    "svcInstName is required");

      LOG_EXIT(getLogger(),
               "createServiceInstance..." + "\nCIM-Request-" + cimRequest.getId() +
               "\n" +
          cimClient.toString(cimRequest) + "\nCIM-Instance-" +
          cimRequest.getId() + "\n" +
          cimClient.toString(cimInstance));
    }
    tasProvisioningRunManager.addCompletedTestMethods("createServiceInstance");

  }

  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "createServiceInstance" })
  public void checkInstanceReadyEmail() throws MessagingException, IOException,
                                               GeneralSecurityException {

    LOG_ENTRY(getLogger(), "checkInstanceReadyEmail...");

    assertNotNull(svcInstDetails, "svcInstDetails is required");
    assertNotNull(svcInstDetails.getSvcInstName(), "svcInstName is Required");

    instanceReadyEmail =
        jmUtil.getInstanceReadyEmail(OpcJavaMailUtil.QueryType.BODY_TEXT,
                                     svcInstDetails.getSvcInstName().toLowerCase(),
                                     TasProvisioningTestSysEnvProps.getIntegerPropValue(TasProvisioningTestSysEnvProps.CHECK_INSTANCE_READY_EMAIL_BACK_DAYS,
                                                                                        true),
                                     TasProvisioningTestSysEnvProps.getLongPropValue(TasProvisioningTestSysEnvProps.INSTANCE_READY_EMAIL_WAIT_MINS,
                                                                                     true));

    assertNotNull(instanceReadyEmail,
                  "Could Not Get Instance Ready Email...SvcInstanceName : " +
                  svcInstDetails.getSvcInstName());

    identityDomain = instanceReadyEmail.getIdentityDomain();

    assertNotNull(identityDomain, "identityDomain is required");

    tasProvisioningRunManager.addCompletedTestMethods("checkInstanceReadyEmail");

    LOG_EXIT(getLogger(), "checkInstanceReadyEmail...\n" +
        instanceReadyEmail);

  }


  @Test(groups = { TAS_PROVISIONING_GRP },
        dependsOnMethods = { "checkInstanceReadyEmail" })
  public void createMessagingSvcUsersAndRoleGrants() throws IOException {

    LOG_ENTRY(getLogger(), "createMessagingSvcUsersAndRoleGrants...");

    assertNotNull(svcInstDetails, "svcInstDetails is required");
    assertNotNull(svcInstDetails.getSvcInstName(), "svcInstName is Required");
    assertNotNull(identityDomain, "identityDomain is required");
    assertNotNull(domainAdminId, "domainAdminId is required");
    assertNotNull(mcsSeq, "mcsSeq is Required");
    assertTrue(mcsSeq > 0 && mcsSeq <= 2,
               "mcsSeq Should be 1 or 2..CurrVal : " + mcsSeq);

    sleep(90 * 1000, "createMessagingSvcUsersAndRoleGrants");

    McsUserAndRoles[] mcsUserAndRolesArray = mcsUserAndRoles1;
    if (mcsSeq != 1) {
      mcsUserAndRolesArray = mcsUserAndRoles2;
    }
    Map<McsRole, Role> rolesMap = getMcsRoles();
    for (McsUserAndRoles mcsUserAndRoles : mcsUserAndRolesArray) {

      logger.info("Processing MCSUserAndRoles : " + mcsUserAndRoles);
      CloudIdentityRestClient cloudIdentityRestClient =
        getCloudIdentityRestClient(identityDomain, domainAdminId,
                                   svcInstDetails.getSvcInstName());

      NewUser newUser = createNewUser(mcsUserAndRoles.getUserName());

      User user = getUser(newUser.getEmail());
      if (user == null) {
        user = cloudIdentityRestClient.createUser(newUser);
      } else {
        newUser = null;
      }
      for (McsRole mcsRole : mcsUserAndRoles.getRoles()) {
        Role role = rolesMap.get(mcsRole);
        if (role != null) {
          RoleGrant roleGrant = new RoleGrant();
          roleGrant.setRole(role);
          roleGrant.setUser(user);
          logger.info("Adding RoleGrant : " + role.getName() +
                      " To User -> ( " + user.getLogin() + " ) ");
          getCloudIdentityRestClient(identityDomain, domainAdminId,
                                     svcInstDetails.getSvcInstName()).createRoleGrant(roleGrant);
          logger.info("Successfully Added RoleGrant : " + role.getName() +
                      " To User -> ( " + user.getLogin() + " ) ");

        }
      }
      if (newUser != null) {
        getCloudIdentityRestClient(identityDomain, user.getLogin(),
                                   user.getLogin(), newUser.getPassword(),
                                   svcInstDetails.getSvcInstName()).updatePassword(newUser.getPassword(),
                                                                                   mcsUserAndRoles.getPassword());
      }
    }

    tasProvisioningRunManager.addCompletedTestMethods("createMessagingSvcUsersAndRoleGrants");

    LOG_EXIT(getLogger(), "createMessagingSvcUsersAndRoleGrants...");
  }

  @Test(groups = { TAS_DELETE_GRP })
  public void deleteServiceInstance() throws IOException, URISyntaxException {

    LOG_ENTRY(getLogger(), "deleteServiceInstance...");

    assertNotNull(entitlementRunType, "entitlementRunType is required");
    assertNotNull(identityDomain, "identityDomain is required");
    assertNotNull(domainAdminId, "domainAdminId is required");
    assertNotNull(svcInstDetails, "svcInstDetails is required");
    assertNotNull(svcInstDetails.getSvcInstType(),
                  "svcInstDetails.svcInstType is required");
    assertTrue(entitlementRunType.isEntitlementRun(),
               "Delete Instance Allowed for entitlementRunType CLOUD_CREDIT,SRVC_ENTITLEMENT");
    if (svcInstDetails.getSvcInstType().isPsmBasedProvisioning()) {
      assertNotNull(svcInstDetails.getSvcInstName(),
                    "svcInstDetails.svcInstName is required");
      PsmClientResponse psmClientResponse = null;
      String jobId = null;
      if (svcInstDetails.getSvcInstType() == TasServiceType.DBAAS) {
        DbaasServiceInstanceEndpoint serviceResource =
          getPsmRestResource(DbaasServiceInstanceEndpoint.class,
                             svcInstDetails.getSvcInstType());
        psmClientResponse =
            serviceResource.deleteService(svcInstDetails.getSvcInstName(),
                                          identityDomain);
        jobId = psmClientResponse.getJobId();
        psmClientResponse =
            serviceResource.waitForJobToFinish(psmClientResponse);

      } else if (svcInstDetails.getSvcInstType() == TasServiceType.JAAS) {
        JaasServiceInstanceEndpoint serviceResource =
          getPsmRestResource(JaasServiceInstanceEndpoint.class,
                             svcInstDetails.getSvcInstType());
        Map<JaasServiceInstanceEndpoint.DelRequestParams, String> delReqParams =
          new HashMap<JaasServiceInstanceEndpoint.DelRequestParams, String>();
        Map<String, String> jsonFields = toJsonFields();
        for (String key : jsonFields.keySet()) {
          delReqParams.put(JaasServiceInstanceEndpoint.DelRequestParams.valueOf(key),
                           jsonFields.get(key));
        }
        psmClientResponse =
            serviceResource.deleteService(svcInstDetails.getSvcInstName(),
                                          identityDomain, delReqParams);
        jobId = psmClientResponse.getJobId();
        psmClientResponse =
            serviceResource.waitForJobToFinish(psmClientResponse);
      } else {
        throw new UnsupportedOperationException("PSM Instance Deletion Testing Currently Not Supported for ServiceType : " +
                                                svcInstDetails.getSvcInstType());
      }
      assertTrue(psmClientResponse.getJobStatus().hasJobSucceeded(),
                 svcInstDetails.getSvcInstName() + " Job : " + jobId +
                 "...Did Not Complete Successfully...Status : " +
                 psmClientResponse.getJobStatus());
    } else {
      assertNotNull(cimInstance, "cimInstance is required");
      CommonInstanceMgrRestClient cimClient =
        getCommonInstanceMgrRestClient(identityDomain, domainAdminId);
      cimClient.deleteInstance(cimInstance.getId());
    }

    LOG_EXIT(getLogger(), "deleteServiceInstance...");

  }

  private void createPsmService() throws IOException, JsonParseException,
                                         URISyntaxException, JAXBException {

    PsmBaseResource psmResource = null;
    if (svcInstDetails.getSvcInstType() == TasServiceType.DBAAS) {
      psmResource =
          getPsmRestResource(DbaasProvisioningEndpoint.class, svcInstDetails.getSvcInstType());
    } else if (svcInstDetails.getSvcInstType() == TasServiceType.JAAS) {
      psmResource =
          getPsmRestResource(JaasProvisioningEndpoint.class, svcInstDetails.getSvcInstType());
    } else {
      throw new UnsupportedOperationException("PSM Instance Creation Testing Currently Not Supported for ServiceType : " +
                                              svcInstDetails.getSvcInstType());
    }

    PsmServiceType svcType =
      PsmServiceType.valueOf(svcInstDetails.getSvcInstType().name().toLowerCase());
    JsonNode jaasCreateJsonNode =
      psmResource.buildDefaultPayload(svcType, svcInstDetails.getSvcInstName(),
                                      PsmPayloadType.create, toJsonFields());

    svcInstDetails.setSvcInstName(PsmJsonFields.getJSONValue(PsmJsonFields.SERVICE_NAME,
                                                             jaasCreateJsonNode));
    svcInstDetails.setSvcInstFields(psmResource.buildSummaryMap(jaasCreateJsonNode));
    svcInstDetails.setSvcInstJsonPayload(psmResource.fromJsonNode(jaasCreateJsonNode));

    logger.info("Created Request..." + svcType.name() +
                " Service Payload :\n" +
        psmResource.fromJsonNode(jaasCreateJsonNode));
    PsmClientResponse psmClientResponse =
      psmResource.postService(identityDomain, jaasCreateJsonNode);
    writeToFile("Create-" + psmClientResponse.getServiceId(),
                psmClientResponse.toString(), FileType.TXT);

    //Wait For Job to Finish
    psmClientResponse = psmResource.waitForJobToFinish(psmClientResponse);
    logger.info("Created Job Completed..." + svcType.name() +
                " Service Payload :\n" +
        psmResource.fromJsonNode(psmClientResponse.getJsonNode()));
    writeToFile("JobComplete-" + psmClientResponse.getServiceId() + "-" +
                psmClientResponse.getJobId(), psmClientResponse.toString(),
                FileType.TXT);

    assertTrue(psmClientResponse.getJobStatus().hasJobSucceeded(),
               svcInstDetails.getSvcInstType().name() + " Job : " +
               psmClientResponse.getJobId() +
               "...Did Not Complete Successfully...Status : " +
               psmClientResponse.getJobStatus() + "\nPsmClientResponse : " +
               psmClientResponse);

    psmClientResponse =
        psmResource.getService(psmClientResponse.getServiceId(),
                               identityDomain);
    logger.info("GetService..." + svcType.name() + " Service Details :\n" +
        psmResource);

    svcInstDetails.getSvcInstFields().putAll(psmResource.buildSummaryMap(psmClientResponse.getJsonNode()));
    svcInstDetails.setSvcInstJsonPayload(psmResource.fromJsonNode(psmClientResponse.getJsonNode()));

  }

  private Map<String, String> toJsonFields() {
    Map<String, String> addUpdtFields = null;
    if (svcInstDetails.getCustomAttributes() != null &&
        svcInstDetails.getCustomAttributes().size() > 0) {
      addUpdtFields = new HashMap<String, String>();
      for (AttributeValue attrVal : svcInstDetails.getCustomAttributes()) {
        addUpdtFields.put(attrVal.getName(), attrVal.getValue());
      }
    }
    return addUpdtFields;
  }

  private Map<McsRole, Role> getMcsRoles() {
    Map<McsRole, Role> retRolesMap = new HashMap<McsRole, Role>();
    User adminUser = getUser(domainAdminId);
    Roles roles =
      getCloudIdentityRestClient(identityDomain, domainAdminId, svcInstDetails.getSvcInstName()).getRoles(adminUser.getId());
    for (Role role : roles.getItems()) {
      for (McsRole mcsRole : McsRole.values()) {
        logger.info("...Matching LdapRoleName -> ( " + role.getName() +
                    " ) To McsRoleName -> ( " + mcsRole.name() + " )");

        if (role.getName().contains(mcsRole.name())) {
          if (!retRolesMap.containsKey(mcsRole) ||
              role.getName().contains(svcInstDetails.getSvcInstName())) {
            retRolesMap.put(mcsRole, role);
            logger.info("...Successfully Mached LdapRoleName -> ( " +
                        role.getName() + " ) To McsRoleName -> ( " +
                        mcsRole.name() + " )");
          }
        }
      }
    }
    return retRolesMap;
  }

  private User getUser(String login) {
    User adminUser = null;
    Users users =
      getCloudIdentityRestClient(identityDomain, domainAdminId, svcInstDetails.getSvcInstName()).getUsers();
    for (User user : users.getItems()) {
      if (user.getLogin().equalsIgnoreCase(login)) {
        adminUser = user;
        break;
      }
    }
    return adminUser;
  }

  private static NewUser createNewUser(String userName) {
    NewUser newUser = new NewUser();
    newUser.setDisplayName(userName);
    newUser.setEmail(userName + "@oracle.com");
    newUser.setFirstName(userName);
    newUser.setLastName(userName);
    newUser.setLogin(newUser.getEmail());
    newUser.setPassword("YadaYada12#");

    return newUser;

  }

  private void sleep(long sleepTimeMillis, String testName) {
    logger.info("Sleeping for " + sleepTimeMillis +
                " Millis...Requested By Test : " + testName);
    try {
      Thread.currentThread().sleep(sleepTimeMillis);
    } catch (InterruptedException e) {
      //ignore.
    }
  }

  private enum McsRole {
    Messaging_Administrator,
    Messaging_Worker;

    public boolean isAdminRole() {
      return this == Messaging_Administrator;
    }

    public boolean isAWorkerRole() {
      return this == Messaging_Worker;
    }
  }

  private static class McsUserAndRoles {
    private McsRole[] roles;
    private String userName;
    private String password;

    McsUserAndRoles(String userName, String password, McsRole... roles) {
      this.userName = userName;
      this.password = password;
      this.roles = roles;
    }

    McsRole[] getRoles() {
      return roles;
    }

    String getUserName() {
      return userName;
    }

    String getPassword() {
      return password;
    }


    @Override
    public String toString() {
      return "UserName -> ( " + userName + " ) Password -> ( " + password +
        " ) Roles ->  " + Arrays.toString(roles);
    }
  }


  Logger getLogger() {
    return logger;
  }

  static boolean isTestDisabled(String methodName,
                                ITestAnnotation annotation) {

    EntitlementRunType currEntitlementRunType =
      EntitlementRunType.valueOf(TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE,
                                                                                   true));
    boolean skipMethod = false;
    if (currEntitlementRunType.isNoRun()) {
      skipMethod = true;
    } else {
      initProvisioning();
      if (methodName != null) {
        if (annotation != null) {
          if (svcInstDetails.getSvcInstType().isPsmBasedProvisioning() &&
              methodName.equalsIgnoreCase("createMessagingSvcUsersAndRoleGrants")) {
            annotation.setDependsOnMethods(new String[] { "createServiceInstance" });
            logger.info("For method " + methodName +
                        " Changing DepedentMethods : " +
                        Arrays.toString(annotation.getDependsOnMethods()));
          }
          if (entitlementRunType.isSrvcInstance() &&
              methodName.equalsIgnoreCase("createMessagingSvcUsersAndRoleGrants")) {
            annotation.setDependsOnMethods(new String[] { "changeTempPassword" });
            logger.info("For method " + methodName +
                        " Changing DepedentMethods : " +
                        Arrays.toString(annotation.getDependsOnMethods()));
          }
          if (autoCompletionEnabled &&
              methodName.equalsIgnoreCase("checkProvisioningEmail")) {
            annotation.setDependsOnMethods(new String[] { "seedProvisioningOrder" });
            logger.info("For method " + methodName +
                        " Changing DepedentMethods : " +
                        Arrays.toString(annotation.getDependsOnMethods()));
          }
        }
        if (currEntitlementRunType.isRerun()) {
          skipMethod = tasProvisioningRunManager.isTestDisabled(methodName);
        } else {
          Integer propSeq =
            TasProvisioningTestSysEnvProps.getIntegerPropValue(TasProvisioningTestSysEnvProps.MCS_USER_SEQ,
                                                               false);
          if (methodName.equalsIgnoreCase("main")) {
            logger.info("Skipping Main method " + methodName);
            skipMethod = true;
          } else if ((svcInstDetails.getSvcInstType().isPsmBasedProvisioning() ||
                      entitlementRunType.isSrvcInstance()) &&
                     methodName.equalsIgnoreCase("checkInstanceReadyEmail")) {
            logger.info("Skipping method " + methodName +
                        " as PsmBasedProvisioning/SvcInstanceProvisioning");
            skipMethod = true;
          } else if (autoCompletionEnabled &&
                     (methodName.equalsIgnoreCase("checkActivationEmail") ||
                      methodName.equalsIgnoreCase("activateProvisioningOrder"))) {
            logger.info("Skipping method " + methodName +
                        " as autoCompletionEnabled");
            skipMethod = true;

          } else if (activationComplete &&
                     (methodName.equalsIgnoreCase("seedProvisioningOrder") ||
                      methodName.equalsIgnoreCase("checkActivationEmail"))) {
            logger.info("Skipping method " + methodName +
                        " as activationComplete");
            skipMethod = true;
          } else if (provisioningComplete &&
                     (methodName.equalsIgnoreCase("checkProvisioningEmail") ||
                      methodName.equalsIgnoreCase("activateProvisioningOrder"))) {
            logger.info("Skipping method " + methodName +
                        " as provisioningComplete");
            skipMethod = true;
          } else if (instanceReadyComplete &&
                     methodName.equalsIgnoreCase("checkInstanceReadyEmail")) {
            logger.info("Skipping method " + methodName +
                        " as instanceReadyComplete");
            skipMethod = true;
          } else if ((methodName.equalsIgnoreCase("checkActivationEmail") ||
                      methodName.equalsIgnoreCase("activateProvisioningOrder") ||
                      methodName.equalsIgnoreCase("checkProvisioningEmail")) &&
                     orderId == null && skipMethod("seedProvisioningOrder")) {
            logger.info("Skipping method " + methodName +
                        " as seedProvisioningOrder disabled and orderId sys property NOT passed");
            skipMethod = true;
          } else if ((methodName.equalsIgnoreCase("changeTempPassword") ||
                      methodName.equalsIgnoreCase("createServiceInstance") ||
                      methodName.equalsIgnoreCase("createMessagingSvcUsersAndRoleGrants")) &&
                     (provisioningEmail == null &&
                      (identityDomain == null || domainAdminId == null)) &&
                     skipMethod("checkProvisioningEmail")) {
            logger.info("Skipping method " + methodName +
                        " as checkProvisioningEmail disabled and provisioningEmail is NULL and IdentityDomain and DomainAdmin NOT Set.");
            skipMethod = true;
          } else if ((!entitlementRunType.isEntitlementRun() ||
                      (entitlementRunType.isEntitlementRun() &&
                       svcInstDetails.getSvcInstType() == null)) &&
                     methodName.equalsIgnoreCase("createServiceInstance")) {
            logger.info("Skipping method " + methodName +
                        " as entitlement Type is NOT CLOUD_CREDIT or SRVC_ENTITLEMENT or SvcInstType is NULL...is : " +
                        entitlementRunType + "....SvcInstType..." +
                        svcInstDetails.getSvcInstType());
            skipMethod = true;
          } else if (methodName.equalsIgnoreCase("createMessagingSvcUsersAndRoleGrants") &&
                     (propSeq == null ||
                      svcInstDetails.getSvcInstType() == null ||
                      TasServiceType.Messaging !=
                      svcInstDetails.getSvcInstType())) {
            logger.info("Skipping method " + methodName +
                        " svcInstType is NULL or Not Messaging or PropSeq is NULL...ServiceType...svcInstType : " +
                        (svcInstDetails.getSvcInstType() != null ?
                         svcInstDetails.getSvcInstType().name() : "") +
                        " propSeq : " + propSeq);
            skipMethod = true;
          } else {
            skipMethod = skipMethod(methodName);
          }
        }
        if (skipMethod) {
          addToSkipMethods(methodName);
        } else {
          tasProvisioningRunManager.addTestMethods(methodName);
        }
      }
    }
    return skipMethod;
  }

  private static boolean skipMethod(String methodName) {
    boolean skipMethod = false;
    for (String skipMethodName : skipMethodNames) {
      if (methodName.equalsIgnoreCase(skipMethodName)) {
        logger.info("Skipping Requested method[" + methodName + "] " +
                    methodName);
        skipMethod = true;
        break;
      }
    }
    return skipMethod;
  }

  private static void addToSkipMethods(String methodName) {
    skipMethodNames.add(methodName);
  }

  private static final String NONE_VAL = "NONE";

  private static synchronized void initProvisioning() {

    if (entitlementRunType == null) {
      entitlementRunType =
          EntitlementRunType.valueOf(TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE,
                                                                                       true));
      try {
        if (entitlementRunType.isNewRun()) {
          initSysEnvProps();
          initNewRun();
        } else { //rerun.
          if (entitlementRunType.isRerunLatest()) {
            tasProvisioningRunManager.loadLatestProvisioningPropertiesFile();
          } else {
            orderId =
                TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.ORDER_ID,
                                                                  true);
            tasProvisioningRunManager.loadProvisioningPropertiesFile(orderId);
          }
          initRerun();
          initSysEnvProps();
        }
      } catch (Exception e) {
        String errMsg =
          "Error InitProvisioning for Order : " + orderId + "..Error : " +
          e.getMessage();
        logger.log(Level.SEVERE, errMsg, e);
        throw new RuntimeException(errMsg, e);
      }
    }

  }

  private static final String DEFAULT_CUSTOM_ATTRIBURES =
    "LOCALE,en_us:IS_BRANDING_REQUIRED,TRUE"; //CIM seems to require this.

  private static void initSysEnvProps() {
    if (labEnvironment == null) {
      labEnvironment =
          TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.LAB_NAME,
                                                            true);
    }
    if (dataCenter == null) {
      dataCenter =
          TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.DATA_CENTER,
                                                            true);
    }
    RedisParamsDataProvider dp = new RedisParamsDataProvider();
    Map<String, String> params = dp.getLabParams(labEnvironment);
    String ssoUser = params.get(SSO.USERNAME.toString());
    String ssoPassword = params.get(SSO.PASSWORD.toString());
    jmUtil = new OpcJavaMailUtil(ssoUser, ssoPassword);

    String orderId =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.ORDER_ID,
                                                        false);
    if (orderId != null) {
      TasProvisioningTest.orderId = orderId;
    }

    if (svcInstDetails == null) {
      svcInstDetails = new ServiceInstanceDetails();
    }
    String svcInstType =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE,
                                                        false);
    if (NONE_VAL.equalsIgnoreCase(svcInstType)) {
      svcInstType = null;
    } else {
      if (svcInstType != null) {
        svcInstDetails.setSvcInstType(svcInstType);
      }
    }
    String svcInstSize =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_INSTANCE_SIZE,
                                                        false);
    if (svcInstSize != null) {
      svcInstDetails.setSvcInstSizeStr(svcInstSize);
    }
    String svcInstCreditType =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_INSTANCE_CREDIT_TYPE,
                                                        false);
    if (svcInstCreditType != null) {
      svcInstDetails.setSvcInstCreditType(svcInstCreditType);
    }
    String svcInstName =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_INSTANCE_NAME,
                                                        false);
    if (svcInstName != null) {
      svcInstDetails.setSvcInstName(svcInstName);
    }
    String svcInstEntitlementId =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_ENTITLEMENT_ID,
                                                        false);
    if (svcInstEntitlementId != null) {
      svcInstDetails.setSvcInstEntitlementId(svcInstEntitlementId);
    }
    if (entitlementRunType.isCloudCredit()) {
      String creditTypesStr =
        TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.CREDIT_TYPES,
                                                          false);
      if (creditTypesStr != null) {
        creditTypes =
            new LinkedHashSet<String>(Arrays.asList(creditTypesStr.split(VALS_SEP)));
      }
    }

    String customAttributesStr =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.CUSTOM_ATTRIBUTES,
                                                        false);
    if (customAttributesStr == null &&
        (customAttributes == null || customAttributes.size() == 0) &&
        entitlementRunType.isEntitlementRun()) {
      customAttributesStr = DEFAULT_CUSTOM_ATTRIBURES;
      defaultCustomAttributes = true;
    }
    if (customAttributesStr != null) {
      customAttributes = createCustomAttributesList(customAttributesStr);
    }

    String customSvcAttributesStr =
      TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.SVC_CUSTOM_ATTRIBUTES,
                                                        false);
    svcInstDetails.setCustomAttributes(createCustomAttributesList(customSvcAttributesStr));

    Integer mcsSeq =
      TasProvisioningTestSysEnvProps.getIntegerPropValue(TasProvisioningTestSysEnvProps.MCS_USER_SEQ,
                                                         false);
    if (mcsSeq != null && mcsSeq > 0) {
      TasProvisioningTest.mcsSeq = mcsSeq;
    }

    Boolean autoCompletionEnabled =
      TasProvisioningTestSysEnvProps.getBooleanPropValue(TasProvisioningTestSysEnvProps.AUTO_COMPLETION_ENABLED,
                                                         false);
    if (autoCompletionEnabled != null) {
      TasProvisioningTest.autoCompletionEnabled = autoCompletionEnabled;
    }

  }

  private static List<AttributeValue> createCustomAttributesList(String customAttributesStr) {
    List<AttributeValue> customAttributes = null;
    if (customAttributesStr != null) {
      customAttributes = new ArrayList<AttributeValue>();
      String[] nmValPairsArr = customAttributesStr.split(NAME_VAL_PAIR_SEP);
      for (String nmValPair : nmValPairsArr) {
        nmValPair = nmValPair.trim();
        if (nmValPair.length() > 0) {
          String[] nmValPairArr = nmValPair.split(VALS_SEP);
          AttributeValue customAttrubute = new AttributeValue();
          customAttrubute.setName(nmValPairArr[0]);
          if (nmValPairArr.length > 1) {
            customAttrubute.setValue(nmValPairArr[1]);
          }
          customAttributes.add(customAttrubute);
        }
      }
    }
    return customAttributes;
  }

  private static void initNewRun() {
    initSkipMethods();
    if (orderId != null) {
      Integer days = null;
      try {
        if (!autoCompletionEnabled) {
          activationEmail =
              jmUtil.getActivationEmail(OpcJavaMailUtil.QueryType.ORDER_ID,
                                        orderId, days, 0);
        }
        provisioningEmail =
            jmUtil.getProvisioningCompleteEmail(OpcJavaMailUtil.QueryType.ORDER_ID,
                                                orderId, days, 0);
        activationComplete = activationEmail != null;
        provisioningComplete = provisioningEmail != null;
        if (provisioningComplete) {
          identityDomain = provisioningEmail.getIdentityDomain();
          domainAdminId = provisioningEmail.getUserName();
        }
        if (svcInstDetails != null &&
            svcInstDetails.getSvcInstName() != null) {
          instanceReadyEmail =
              jmUtil.getInstanceReadyEmail(OpcJavaMailUtil.QueryType.BODY_TEXT,
                                           svcInstDetails.getSvcInstName(),
                                           days, 0);
          instanceReadyComplete = instanceReadyEmail != null;
        }
      } catch (Exception e) {
        String errMsg =
          "Error Retrieving Email for Order : " + orderId + "..Error : " +
          e.getMessage();
        logger.log(Level.SEVERE, errMsg, e);
        throw new RuntimeException(errMsg, e);
      }
      if (activationEmail == null && provisioningEmail == null) {
        RuntimeException rex =
          new RuntimeException("Could Not Find Activation or Provisioning Email for Passed OrderID : " +
                               orderId);
        logger.severe(rex.getMessage());
        throw rex;
      }
    }
    if (accountName == null) {
      accountName =
          TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.ACCOUNT_NAME,
                                                            false);
    }
    if (identityDomain == null) {
      identityDomain =
          TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.IDENTITY_DOMAIN_NAME,
                                                            false);
    }
    if (domainAdminId == null) {
      domainAdminId =
          TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTestSysEnvProps.DOMAIN_ADMIN_ID,
                                                            false);
    }
  }

  private static void initRerun() throws IOException, JsonParseException,
                                         JsonMappingException {

    labEnvironment = tasProvisioningRunManager.getLabName();
    dataCenter = tasProvisioningRunManager.getDataCenter();
    skipMethodNames = tasProvisioningRunManager.getSkippedTestMethods();
    entitlementRunType = tasProvisioningRunManager.getEntitlementRunType();
    orderId = tasProvisioningRunManager.getProvisioningOrderId();
    creditTypes = tasProvisioningRunManager.getCreditTypes();
    customAttributes = tasProvisioningRunManager.getCustomAttributes();
    svcInstDetails =
        tasProvisioningRunManager.getLatestServiceInstanceDetails();
    accountName = tasProvisioningRunManager.getAccountName();
    identityDomain = tasProvisioningRunManager.getIdenityDomainName();
    domainAdminId = tasProvisioningRunManager.getAdminUserId();
    domainAdminPassword = tasProvisioningRunManager.getAdminPassword();
    seedOrderResp = tasProvisioningRunManager.getSeedOrder();
    activationOrderResp = tasProvisioningRunManager.getProvisionedOrder();
    activationEmail = tasProvisioningRunManager.getOpcActivationEmail();
    provisioningEmail =
        tasProvisioningRunManager.getOpcProvisioningCompleteEmail();
    instanceReadyEmail = tasProvisioningRunManager.getOpcInstanceReadyEmail();
    activationComplete = activationEmail != null;
    provisioningComplete = provisioningEmail != null;
    instanceReadyComplete = instanceReadyEmail != null;
    if (provisioningComplete) {
      if (identityDomain == null) {
        identityDomain = provisioningEmail.getIdentityDomain();
      }
      if (domainAdminId == null) {
        domainAdminId = provisioningEmail.getUserName();
      }
    }
    cimRequest = tasProvisioningRunManager.getCimServiceRequest();
    cimInstance = tasProvisioningRunManager.getCimServiceInstance();
    mcsSeq = tasProvisioningRunManager.getMcsUserSeq();

    autoCompletionEnabled =
        tasProvisioningRunManager.getAutoCompletionEnabled() != null ?
        tasProvisioningRunManager.getAutoCompletionEnabled() : false;
  }


  private static void initSkipMethods() {

    String skipMethodsStr =
      TasProvisioningTest.TasProvisioningTestSysEnvProps.getStringPropValue(TasProvisioningTest.TasProvisioningTestSysEnvProps.SKIP_METHODS,
                                                                            false);
    if (skipMethodsStr != null) {
      String[] skipMethodsArray =
        skipMethodsStr.split(TasProvisioningTest.VALS_SEP);
      for (String skipMethod : skipMethodsArray) {
        if (skipMethod != null && skipMethod.trim().length() > 0 &&
            !NONE_VAL.equalsIgnoreCase(skipMethod.trim())) {
          skipMethodNames.add(skipMethod.trim());
        }
      }
    }
    logger.info("skipMethodNames -> " + skipMethodNames);
  }

  private static void initTasProvisioningManager(TasCentralRestClient tasCentralClient,
                                                 boolean afterClass) {
    try {
      if (labEnvironment != null) {
        tasProvisioningRunManager.setLabName(labEnvironment);
      }
      if (dataCenter != null) {
        tasProvisioningRunManager.setDataCenter(dataCenter);
      }
      if (skipMethodNames != null && skipMethodNames.size() > 0) {
        tasProvisioningRunManager.addSkippedTestMethods(skipMethodNames.toArray(new String[skipMethodNames.size()]));
      }
      tasProvisioningRunManager.setEntitlementRunType(entitlementRunType);
      if (creditTypes != null && creditTypes.size() > 0) {
        tasProvisioningRunManager.setCreditTypes(creditTypes);
      }
      if (customAttributes != null && customAttributes.size() > 0) {
        tasProvisioningRunManager.setCustomAttributes(customAttributes);
      }
      if (accountName != null) {
        tasProvisioningRunManager.setAccountName(accountName);
      }
      if (identityDomain != null) {
        tasProvisioningRunManager.setIdenityDomainName(identityDomain);
      }
      if (orderId != null) {
        tasProvisioningRunManager.setProvisioningOrderId(orderId);
      }
      if (domainAdminId != null) {
        tasProvisioningRunManager.setAdminUserId(domainAdminId);
      }
      if (domainAdminPassword != null) {
        tasProvisioningRunManager.setAdminPassword(domainAdminPassword);
      }
      if (seedOrderResp != null) {
        tasProvisioningRunManager.setSeedOrder(seedOrderResp);
      }
      if (activationEmail != null) {
        tasProvisioningRunManager.setOpcActivationEmail(activationEmail);
      }
      if (activationOrderResp != null) {
        if (afterClass) {
          tasProvisioningRunManager.setProvisionedOrderAndRelatedProperties(activationOrderResp,
                                                                            tasCentralClient);
        } else {
          tasProvisioningRunManager.setProvisionedOrder(activationOrderResp);
        }
      }
      if (provisioningEmail != null) {
        tasProvisioningRunManager.setOpcProvisioningCompleteEmail(provisioningEmail);
      }
      if (instanceReadyEmail != null) {
        tasProvisioningRunManager.setOpcInstanceReadyEmail(instanceReadyEmail);
      }
      if (svcInstDetails != null) {
        if (entitlementRunType.isRerun()) {
          tasProvisioningRunManager.replaceLatestServiceInstanceDetails(svcInstDetails);
        } else {
          tasProvisioningRunManager.addServiceInstanceDetails(svcInstDetails);
        }
      }
      if (cimRequest != null) {
        tasProvisioningRunManager.setCimServiceRequest(cimRequest);
      }
      if (cimInstance != null) {
        tasProvisioningRunManager.setCimServiceInstance(cimInstance);
      }
      if (mcsSeq != null) {
        tasProvisioningRunManager.setMcsUserSeq(mcsSeq);
      }
      tasProvisioningRunManager.setAutoCompletionEnabled(autoCompletionEnabled);

    } catch (Exception e) {
      throw new RuntimeException("Error InitTasProvisioningManager...Error : " +
                                 e.getMessage() + "\nProperties : " +
                                 tasProvisioningRunManager.getProvisioningProperties().toString(),
                                 e);
    }
  }

  private void saveProperties(boolean afterClass) {
    try {
      initTasProvisioningManager(getRestApiHandle(TasCentralRestClient.class),
                                 afterClass);
      tasProvisioningRunManager.saveProvisioningProperties();
    } catch (Exception e) {
      throw new RuntimeException("Error Saving Provisioning Properties...Error : " +
                                 e.getMessage() + "\nProperties : " +
                                 tasProvisioningRunManager.getProvisioningProperties().toString(),
                                 e);
    }
  }

  private Order createSeedOrder(TasCentralRestClient tasCentralClient,
                                boolean isMetered, boolean isAutoComplete,
                                ServiceInstanceDetails... svcsInstsDetails) throws IOException,
                                                                                   JAXBException,
                                                                                   DatatypeConfigurationException {

    TasOrderBuilder tob =
      tasCentralClient.getTasOrderBuilderHandle().startNewOrder(ssoUser,
                                                                domainAdminId,
                                                                gsiNumber,
                                                                isMetered,
                                                                isAutoComplete). // Admin Email + gsiNumber
      addOrderSource(OrderSource.STORE). // Order Origin
      addDataCenter(dataCenterId,
                    dataCenterRegionId). // DataCenter ID and DataCenter Region ID
      addOperation(OperationType.ONBOARDING). // Operation Type
      addSubscription(SubscriptionType.PRODUCTION, this.identityDomain,
                      null). // Subscription Type + IdentityDomainName + CSINumber
      addAccount(AccountType.SINGLE, accountId, this.accountName);
    for (ServiceInstanceDetails svcInstDetails : svcsInstsDetails) {
      tob.addService(svcInstDetails.getSvcInstType(),
                     svcInstDetails.getSvcInstSize(),
                     svcInstDetails.getSvcInstName(),
                     svcInstDetails.toCustomAttributesArray());
    }
    Order seedOrder = tob.build();
    if (customAttributes != null && customAttributes.size() > 0) {
      if (seedOrder.getCustomAttributeValues().size() == 0 ||
          !defaultCustomAttributes) {
        seedOrder.getCustomAttributeValues().addAll(customAttributes);
      }
    }

    return seedOrder;

  }


  private Order createActivationOrder(TasCentralRestClient tasCentralClient,
                                      String orderId,
                                      String creditAccountName) throws IOException,
                                                                       JAXBException {

    Order activationOrderReq =
      tasCentralClient.getTasOrderBuilderHandle().createActivationOrder(orderId,
                                                                        this.identityDomain,
                                                                        creditAccountName);

    writeToFile("activationOrderReq-" + activationOrderReq.getId(),
                activationOrderReq, FileType.JSON);
    writeToFile("activationOrderReq-" + activationOrderReq.getId(),
                activationOrderReq, FileType.XML);


    return activationOrderReq;
  }


  private String getEntitlementId(TasCentralRestClient tasCentralClient,
                                  String identityDomain, String creditType,
                                  String serviceType) throws IOException,
                                                             JAXBException {

    CreditAccount creditAccount =
      getCreditAccount(tasCentralClient, identityDomain);
    writeToFile("creditAccount-" + creditAccount.getId(), creditAccount,
                FileType.XML);

    CreditEntitlement entitlement = null;
    String serviceEntitlementId = null;
    for (CreditEntitlement ent : creditAccount.getCreditEntitlements()) {
      if ((ent.getEntitlementType() == EntitlementType.CLOUD_CREDIT) &&
          ent.getCreditType().equals(creditType) ||
          (ent.getEntitlementType() == EntitlementType.SUBSCRIPTION) &&
          ent.getCreditType().equals(serviceType)) {
        entitlement = ent;
        entitlement =
            tasCentralClient.getCreditEntitlement(entitlement.getId());
        writeToFile("creditEntitlement-" +
                    (creditType != null ? creditType : serviceType) + "-" +
                    entitlement.getId(), entitlement, FileType.XML);
        CreditEntitlementServiceInstances creditSvcInsts =
          tasCentralClient.getCreditEntitlementServiceInstances(ent.getId());
        writeToFile("creditEntitlementSvcInsts-" +
                    (creditType != null ? creditType : serviceType) + "-" +
                    ent.getId(), creditSvcInsts, FileType.XML);
        for (CreditEntitlementServiceInstance svc :
             creditSvcInsts.getItems()) {
          if (svc.getService().getType().equalsIgnoreCase(serviceType)) {
            serviceEntitlementId =
                svc.getCanonicalLink().substring(svc.getCanonicalLink().lastIndexOf("/") +
                                                 1);
            break;
          }
        }

        break;
      }
    }
    return serviceEntitlementId;

  }


  private CreditAccount getCreditAccount(TasCentralRestClient tasCentralClient,
                                         String identityDomain) {
    CreditAccount creditAccount = null;
    CreditAccounts accts = tasCentralClient.getCreditAccounts();
    for (CreditAccount creditAcct : accts.getItems()) {
      if (creditAcct.getName().equalsIgnoreCase(identityDomain)) {
        creditAccount = creditAcct;
        break;
      }
    }
    return creditAccount;
  }


  private Request createCimRequest(String serviceType, String serviceSize,
                                   String serviceName,
                                   String serviceEntitlementId,
                                   List<AttributeValue> customAttributes) {

    Request request = new Request();
    request.setOperation(RequestType.CREATE_INSTANCE);
    Payload payload = new Payload();
    payload.setAdminEmail(ssoUser);
    String name = ssoUser.split("@")[0];
    payload.setAdminFirstName(name);
    payload.setAdminLastName(name);
    payload.setAdminUsername(ssoUser);
    payload.setInvokerAdminUsername(domainAdminId);
    payload.setInvokerAdminEmail(ssoUser);
    payload.setInvokerAdminFirstName(name);
    payload.setInvokerAdminLastName(name);
    payload.setInvokerAdminUsername(ssoUser);
    if (serviceName != null) {
      payload.setName(serviceName);
    } else {
      payload.setName(serviceType.toLowerCase() +
                      s.format(Calendar.getInstance().getTime()));
    }
    payload.setServiceEntitlementId(serviceEntitlementId);
    payload.setServiceType(serviceType);
    payload.setSize(serviceSize);

    if (customAttributes != null && customAttributes.size() > 0) {
      for (AttributeValue customAttribute : customAttributes) {
        Attribute attr = new Attribute();
        attr.setName(customAttribute.getName());
        attr.setValue(customAttribute.getValue());
        payload.getCustomAttributes().add(attr);
      }
    }

    request.setRequestPayload(payload);

    return request;
  }

  private JsonNode toJsonNode(String jsonNodeStr) throws IOException,
                                                         JsonParseException {
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory factory = mapper.getJsonFactory();
    JsonParser jp = factory.createJsonParser(jsonNodeStr);
    return mapper.readTree(jp);
  }
  //_______________________________________________
  // For Testing this Class

  public static void main(String[] args) {

    System.setProperty(TestConstants.LAB_ENV_PROPERTY, "c9qa132");
    //System.setProperty(TestConstants.LAB_ENV_PROPERTY, "c9edgga");
    System.setProperty(TestConstants.DATA_CENTER, "dc1");
    System.setProperty(TestConstants.REDIS_HOST,
                       "slcn03vmf0254.us.oracle.com");
    System.setProperty(TestConstants.REDIS_KEY, "TAS");
    System.setProperty(TasProvisioningTestSysEnvProps.AUTO_COMPLETION_ENABLED.getPropName(),
                       Boolean.TRUE.toString());
    //setupNewLegacy();
    setupNewSubscriptionEntitlement();
    //setupExistingSubscriptionEntitlement();

    //System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
    //EntitlementRunType.NO_RUN.name());
    //    EntitlementRunType.RE_RUN_LATEST.name());

    //setupNewMeteredEntitlement();
    //setupExistinCloudCreditEntitlement();

    TestNG testng = new TestNG();
    testng.setAnnotationTransformer(new TasProvisioningAnnotationTransformer());
    testng.setTestClasses(new Class[] { TasProvisioningTest.class });
    testng.setVerbose(-1);
    testng.setGroups(TAS_PROVISIONING_GRP);
    //testng.setGroups(TAS_DELETE_GRP);

    TestListenerAdapter tla = new TestListenerAdapter();
    testng.addListener(tla);
    testng.run();
    System.out.println(tla);
    System.out.println("Exiting Main....");
    System.exit(0);
  }

  private static void setupNewLegacy() {
    System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
                       EntitlementRunType.SRVC_INSTANCE.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE.getPropName(),
                       TasServiceType.Messaging.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_SIZE.getPropName(),
                       ServiceSizeCategoryType.CUSTOM.name());
    System.setProperty(TasProvisioningTestSysEnvProps.MCS_USER_SEQ.getPropName(),
                       "1");
  }

  private static void setupNewSubscriptionEntitlement() {
    System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
                       EntitlementRunType.SRVC_ENTITLEMENT.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE.getPropName(),
                       TasServiceType.Documents.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_SIZE.getPropName(),
                       ServiceSizeCategoryType.CUSTOM.name());
    /*System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_NAME.getPropName(),
                       "docBasic0718153");
    System.setProperty(TasProvisioningTestSysEnvProps.ACCOUNT_NAME.getPropName(),
                       "acctDoc0718153");
    System.setProperty(TasProvisioningTestSysEnvProps.IDENTITY_DOMAIN_NAME.getPropName(),
                       "idmDoc0718153");
    System.setProperty(TasProvisioningTestSysEnvProps.DOMAIN_ADMIN_ID.getPropName(),
                       "johndoe");*/

    //System.setProperty(TasProvisioningTestSysEnvProps.MCS_USER_SEQ.getPropName(),
    //                   "1");
  }

  private static void setupExistingSubscriptionEntitlement() {
    String orderId = "9999_4534867";
    String svcInstName = "documents469";
    String mcsUserSeq = "1";
    String skipMethods =
      "seedProvisioningOrder,checkActivationEmail,activateProvisioningOrder,checkProvisioningEmail,createServiceInstance";
    //"seedProvisioningOrder,checkActivationEmail,activateProvisioningOrder,checkProvisioningEmail,changeTempPassword,createServiceInstance";
    System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
                       EntitlementRunType.RE_RUN_ORDER_ID.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE.getPropName(),
                       TasServiceType.Documents.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_NAME.getPropName(),
                       svcInstName);
    System.setProperty(TasProvisioningTestSysEnvProps.ORDER_ID.getPropName(),
                       orderId);
    //System.setProperty(TasProvisioningTestSysEnvProps.MCS_USER_SEQ.getPropName(),
    //                   mcsUserSeq);
    System.setProperty(TasProvisioningTestSysEnvProps.SKIP_METHODS.getPropName(),
                       skipMethods);
  }

  private static void setupNewMeteredEntitlement() {

    String creditType =
      TasServiceType.JAVAMB.name() + VALS_SEP + TasServiceType.DBMB.name() +
      VALS_SEP + TasServiceType.IAASMB.name();
    System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
                       EntitlementRunType.CLOUD_CREDIT.name());
    System.setProperty(TasProvisioningTestSysEnvProps.CREDIT_TYPES.getPropName(),
                       creditType);
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE.getPropName(),
                       TasServiceType.DBAAS.name());
    //TasServiceType.JAAS.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_CREDIT_TYPE.getPropName(),
                       TasServiceType.DBMB.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_SIZE.getPropName(),
                       ServiceSizeCategoryType.CUSTOM.name());
    // System.setProperty(TasProvisioningTestSysEnvProps.SVC_CUSTOM_ATTRIBUTES.getPropName(),
    //                    "dbServiceName:dbaas20160610194004,dbaName:system,dbaPassword:Welcom1#");


    //System.setProperty(TasProvisioningTestSysEnvProps.MCS_USER_SEQ.getPropName(),
    //                   "1");
  }

  private static void setupExistinCloudCreditEntitlement() {

    String orderId = "9999_480474";
    String skipMethods =
      //"seedProvisioningOrder,checkActivationEmail,activateProvisioningOrder,checkProvisioningEmail,changeTempPassword,createServiceInstance";
      "seedProvisioningOrder,checkActivationEmail,activateProvisioningOrder,checkProvisioningEmail,changeTempPassword";
    //String skipMethods = "seedProvisioningOrder";
    //String svcInstance = "messaging20151117162728";
    //String identityDomain="idm1102060947927t3416";
    //String svcInstName = " jaas20160616145259";
    //System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_NAME.getPropName(),
    //                   svcInstName); //For Delete

    //System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),EntitlementRunType.SUBSCRIPTION.name());
    System.setProperty(TasProvisioningTestSysEnvProps.ENTITELEMENT_RUN_TYPE.getPropName(),
                       EntitlementRunType.CLOUD_CREDIT.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_TYPE.getPropName(),
                       TasServiceType.Messaging.name());
    //TasServiceType.JAAS.name());
    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_SIZE.getPropName(),
                       ServiceSizeCategoryType.CUSTOM.name());

    System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_CREDIT_TYPE.getPropName(),
                       TasServiceType.JAVAMB.name());
    //TasServiceType.JAVAMB.name());
    System.setProperty(TasProvisioningTestSysEnvProps.MCS_USER_SEQ.getPropName(),
                       "1");

    System.setProperty(TasProvisioningTestSysEnvProps.ORDER_ID.getPropName(),
                       orderId);

    System.setProperty(TasProvisioningTestSysEnvProps.SKIP_METHODS.getPropName(),
                       skipMethods);
    //For Create New Jaas Service
    //System.setProperty(TasProvisioningTestSysEnvProps.SVC_CUSTOM_ATTRIBUTES.getPropName(),
    //                   "dbServiceName,dbaas20160615091446:dbaName,sys:dbaPassword,Welcom1#");
    //For Delete Jaas Service
    //System.setProperty(TasProvisioningTestSysEnvProps.SVC_CUSTOM_ATTRIBUTES.getPropName(),
    //                   "dbaName,sys:dbaPassword,Welcom1#");
    //System.setProperty(TasProvisioningTestSysEnvProps.SVC_INSTANCE_NAME.getPropName(),
    //                   svcInstance);

    //System.setProperty(TasProvisioningTestSysEnvProps.IDENTITY_DOMAIN_NAME.getPropName(),
    //                   identityDomain);
  }

}
