package com.oracle.saas.qa.tas.rest.client.test;


import com.oracle.saas.qa.cim.rest.client.CommonInstanceMgrRestClient;
import com.oracle.saas.qa.cim.rest.client.CommonInstanceMgrRestFactory;
import com.oracle.saas.qa.cloudidentity.rest.client.CloudIdentityRestClient;
import com.oracle.saas.qa.cloudidentity.rest.client.CloudIdentityRestFactory;
import com.oracle.saas.qa.lib.RedisParamsDataProvider;
import com.oracle.saas.qa.lib.TestConstants;
import com.oracle.saas.qa.lib.keys.CID;
import com.oracle.saas.qa.lib.keys.CIM;
import com.oracle.saas.qa.lib.keys.PSM;
import com.oracle.saas.qa.lib.keys.SSO;
import com.oracle.saas.qa.lib.keys.TAS;
import com.oracle.saas.qa.psm.rest.client.PsmRestResourceFactory;
import com.oracle.saas.qa.psm.rest.resources.PsmBaseResource;
import com.oracle.saas.qa.tas.rest.client.TasCommonRestClient;
import com.oracle.saas.qa.tas.rest.client.TasRestFactory;
import com.oracle.tas.rest.api.model.TasServiceType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.testng.annotations.BeforeClass;


public abstract class TasRestApiTestCommonBase {

  protected static String IDM_NM_SYS_PROP = "tas.rest.dc.idm.name";

  abstract Logger getLogger();

  protected String ssoUser;
  protected String ssoPassword;
  protected String oracleUserId;
  protected String dataCenterId;
  protected String dataCenterRegionId;
  protected String csiNumber;
  protected String accountId;
  protected String labEnv;
  protected String dc;
  protected String idenitydomainname;


  protected TasCommonRestClient tasRestCommonApi;

  protected Map<String, String> params;

  protected String paramsKeyPrefix;

  @BeforeClass
  public abstract void beforeClass();

  protected void init() {
    boolean isCentral =
      this instanceof TasCentralRestApiTest || this instanceof
      TasProvisioningTest || this instanceof TasProvisioningMockTest;
    LOG_ENTRY(getLogger(),
              "Initializing...IsCentral -> ( " + isCentral + " )");

    if (labEnv == null) {
      labEnv = System.getProperty(TestConstants.LAB_ENV_PROPERTY);
    }

    if (idenitydomainname == null) {
      idenitydomainname = System.getProperty(IDM_NM_SYS_PROP);
    }


    if (labEnv == null) {
      String msg =
        "'" + TestConstants.LAB_ENV_PROPERTY + "' Property not defined";
      LOG(getLogger(), Level.SEVERE, msg);
      throw new IllegalStateException(msg);
    }
    if (isCentral) {
      paramsKeyPrefix = "central.";
    } else {
      if (dc == null) {
        dc = System.getProperty(TestConstants.DATA_CENTER);
      }
      if (dc == null) {
        String msg =
          "'" + TestConstants.DATA_CENTER + "' Property not defined";
        LOG(getLogger(), Level.SEVERE, msg);
        throw new IllegalStateException(msg);
      }
      paramsKeyPrefix = dc + ".";
    }

    RedisParamsDataProvider dp = new RedisParamsDataProvider();
    params = dp.getLabParams(labEnv);
    ssoUser = params.get(SSO.USERNAME.toString());
    ssoPassword = params.get(SSO.PASSWORD.toString());
    oracleUserId =
        params.get(paramsKeyPrefix + TAS.REST_ORACLE_APPID.toString());

    dataCenterId = params.get(paramsKeyPrefix + TAS.REST_DC_ID.toString());
    dataCenterRegionId =
        params.get(paramsKeyPrefix + TAS.REST_DC_REGION_ID.toString());

    csiNumber = params.get(paramsKeyPrefix + TAS.REST_CSI_NUMBER.toString());
    accountId = params.get(paramsKeyPrefix + TAS.REST_ACCT_NUMBER.toString());
    if (isCentral) {
      tasRestCommonApi =
          TasRestFactory.getTasCentralRestClientHandle(params.get(paramsKeyPrefix +
                                                                  TAS.REST_API_VERSION.toString()),
                                                       params.get(paramsKeyPrefix +
                                                                  TAS.REST_BASE_URI.toString()),
                                                       params.get(paramsKeyPrefix +
                                                                  TAS.REST_ORACLE_USER.toString()),
                                                       params.get(paramsKeyPrefix +
                                                                  TAS.REST_ORACLE_PASSWORD.toString()),
                                                       oracleUserId,
                                                       idenitydomainname);
      //TasCentralRestClient tc = (TasCentralRestClient)tasRestCommonApi;

      //IdentityDomains idms = tc.getAllIdentityDomains();
      //idm1103154642167t16
      //IdentityDomain idm = tc.getIdentityDomain("502576252");

      //Order order = tc.getOrder("9999_4530470");
      //Account acct = tc.getUserAdministeredAccount(order.getAccount().getId());
      //Subscription sc =
      //  tc.getSubscription(order.getOrderItems().get(0).getSubscription().getId());
      //Service svc = tc.getSupportedService("500221823");


      //IdentityDomain idm = tc.getIdentityDomain("502557698");
      ///tas-central/.common/tas/api/v1/services/500221823
      //Service svc = tc.getSupportedService("500221823");
      ///tas-central/.common/tas/api/v1/services/500221823/plans/15442
      //ServicePlan svcPlan = tc.getServicePlan("500221823", "15442");
    } else if (this instanceof TasDataCenterRestApiTest) {
      tasRestCommonApi =
          TasRestFactory.getTasDataCenterRestClientHandle(params.get(paramsKeyPrefix +
                                                                     TAS.REST_API_VERSION.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     TAS.REST_BASE_URI.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     TAS.REST_ORACLE_USER.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     TAS.REST_ORACLE_PASSWORD.toString()),
                                                          oracleUserId,
                                                          idenitydomainname);
    } else {
      throw new IllegalStateException("Test Class Not Supported -> " +
                                      this.getClass().getName());
    }

    LOG_EXIT(getLogger(),
             "Finished Initializing...IsCentral -> ( " + isCentral + " )");
  }


  protected <T extends TasCommonRestClient> T getRestApiHandle(Class<T> type) {
    return (T)this.tasRestCommonApi;
  }

  protected CloudIdentityRestClient getCloudIdentityRestClient(String identityDomainId,
                                                               String domainAdminId,
                                                               String serviceInstanceName) {

    CloudIdentityRestClient cloudIdentityRestClient =
      CloudIdentityRestFactory.getCloudIdentityRestClient(params.get(paramsKeyPrefix +
                                                                     CID.REST_API_VERSION.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     CID.REST_BASE_URI.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     CID.REST_ORACLE_USER.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     CID.REST_ORACLE_PASSWORD.toString()),
                                                          identityDomainId,
                                                          serviceInstanceName,
                                                          domainAdminId);
    return cloudIdentityRestClient;
  }


  protected CloudIdentityRestClient getCloudIdentityRestClient(String identityDomainId,
                                                               String domainAdminId) {

    return getCloudIdentityRestClient(identityDomainId, domainAdminId,
                                      params.get(paramsKeyPrefix +
                                                 CID.REST_ORACLE_USER.toString()),
                                      params.get(paramsKeyPrefix +
                                                 CID.REST_ORACLE_PASSWORD.toString()));
  }

  protected CloudIdentityRestClient getCloudIdentityRestClient(String identityDomainId,
                                                               String domainAdminId,
                                                               String authUserId,
                                                               String authPassword,
                                                               String serviceInstanceName) {


    CloudIdentityRestClient cloudIdentityRestClient =
      CloudIdentityRestFactory.getCloudIdentityRestClient(params.get(paramsKeyPrefix +
                                                                     CID.REST_API_VERSION.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     CID.REST_BASE_URI.toString()),
                                                          authUserId,
                                                          authPassword,
                                                          identityDomainId,
                                                          serviceInstanceName,
                                                          domainAdminId);
    return cloudIdentityRestClient;

  }

  protected CloudIdentityRestClient getCloudIdentityRestClient(String identityDomainId,
                                                               String domainAdminId,
                                                               String authUserId,
                                                               String authPassword) {


    CloudIdentityRestClient cloudIdentityRestClient =
      CloudIdentityRestFactory.getCloudIdentityRestClient(params.get(paramsKeyPrefix +
                                                                     CID.REST_API_VERSION.toString()),
                                                          params.get(paramsKeyPrefix +
                                                                     CID.REST_BASE_URI.toString()),
                                                          authUserId,
                                                          authPassword,
                                                          identityDomainId,
                                                          domainAdminId);
    return cloudIdentityRestClient;

  }

  protected CommonInstanceMgrRestClient getCommonInstanceMgrRestClient(String tenantName,
                                                                       String domainAdminId) {

    CommonInstanceMgrRestClient client =
      CommonInstanceMgrRestFactory.getCommonInstanceMgrRestClient(params.get(paramsKeyPrefix +
                                                                             CIM.REST_API_VERSION.toString()),
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_BASE_URI.toString()),
                                                                  tenantName,
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_ORACLE_USER.toString()),
                                                                  params.get(paramsKeyPrefix +
                                                                             CIM.REST_ORACLE_PASSWORD.toString()),
                                                                  CommonInstanceMgrRestFactory.ReturnPreference.WAIT,
                                                                  domainAdminId);
    return client;
  }


  protected <T extends PsmBaseResource> T getPsmRestResource(Class<T> psmResource,
                                                             TasServiceType serviceType) {

    T client =
      PsmRestResourceFactory.getPsmRestResource(psmResource, getPsmBaseURL(serviceType),
                                                params.get(paramsKeyPrefix +
                                                           PSM.REST_USER.toString()),
                                                params.get(paramsKeyPrefix +
                                                           PSM.REST_PASSWORD.toString()));
    return client;
  }

  private static final String URL_SEP = ",";
  private Map<String, String> psmSvcToBaseUriMapping;

  private synchronized String getPsmBaseURL(TasServiceType serviceType) {
    if (psmSvcToBaseUriMapping == null) {
      psmSvcToBaseUriMapping = new HashMap<String, String>();
      String svcName = null;
      for (String val :
           params.get(paramsKeyPrefix + PSM.REST_BASE_URLS).split(URL_SEP)) {
        if (svcName == null) {
          svcName = val;
        } else {
          psmSvcToBaseUriMapping.put(svcName.toUpperCase(), val);
          svcName = null;
        }
      }
      getLogger().info("psmSvcToBaseUriMapping : " + psmSvcToBaseUriMapping);
    }
    String baseUrl =
      psmSvcToBaseUriMapping.get(serviceType.name().toUpperCase());
    if (baseUrl == null) {
      throw new UnsupportedOperationException("PSM ServiceType : " +
                                              serviceType +
                                              " currently Not Supported");
    }
    return baseUrl;
  }

  protected static void LOG_ENTRY(Logger logger, String msg) {
    LOG(logger, Level.INFO, "Entering..." + msg);
  }

  protected static void LOG_EXIT(Logger logger, String msg) {
    LOG(logger, Level.INFO, "Exiting..." + msg);
  }

  protected static void LOG(Logger logger, Level level, String msg) {
    LOG(logger, level, msg, null);
  }

  protected static void LOG(Logger logger, Level level, String msg,
                            Throwable error) {
    logger.log(level, "[" + Thread.currentThread().getId() + "]..." + msg,
               error);
  }

  protected void writeToFile(String fileName,
                             Object modelObject) throws IOException {
    File f = new File(getTempFolder(), fileName);
    LOG(getLogger(), Level.INFO, "Writing  To -> " + f.getAbsolutePath());
    tasRestCommonApi.getUtilityHandle().toXMLStream(modelObject,
                                                    new FileOutputStream(f));
  }

  protected enum FileType {
    XML(".xml"),
    JSON(".json"),
    TXT(".txt");
    private String ext;

    FileType(String ext) {
      this.ext = ext;
    }

    boolean isXML() {
      return this == XML;
    }

    boolean isJSON() {
      return this == JSON;
    }

    String addExt(String fileName) {
      return fileName + ext;
    }
  }

  protected void writeToFile(String fileName, String fileData,
                             FileType fileType) throws IOException,
                                                       JAXBException {
    File f = new File(getTempFolder(), fileType.addExt(fileName));
    LOG(getLogger(), Level.INFO, "Writing  To -> " + f.getAbsolutePath());
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(f);
      fos.write(fileData.getBytes("utf-8"));
    } finally {
      try {
        fos.close();
      } catch (Exception ex) {
        //ignore
      }
    }
  }

  private synchronized File getTempFolder() {
    File file =
      new File(System.getProperty("java.io.tmpdir"), File.separator + "TasRest-" +
               idenitydomainname);
    file.mkdirs();
    getLogger().info("Created Temp Folder..." + file.getAbsolutePath());
    return file;
  }

  protected void writeToFile(String fileName, Object modelObject,
                             FileType fileType) throws IOException,
                                                       JAXBException {
    File f = new File(getTempFolder(), fileType.addExt(fileName));
    LOG(getLogger(), Level.INFO, "Writing  To -> " + f.getAbsolutePath());
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(f);
      fos.write(fileType.isJSON() ?
                tasRestCommonApi.getUtilityHandle().toJSONString(modelObject).getBytes("utf-8") :
                tasRestCommonApi.getUtilityHandle().toXMLString(modelObject).getBytes("utf-8"));
    } finally {
      try {
        fos.close();
      } catch (Exception ex) {
        //ignore
      }
    }
  }

  protected <T> T readFromFile(String fileName, FileType fileType,
                               Class<T> clazz) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] bytes = new byte[1024];
    int bytesRead = -1;
    File f = new File(getTempFolder(), fileType.addExt(fileName));
    LOG(getLogger(), Level.INFO, "Reading From -> " + f.getAbsolutePath());
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(f);
      while ((bytesRead = fis.read(bytes)) != -1) {
        baos.write(bytes, 0, bytesRead);
      }
      String objStr = baos.toString("utf-8");
      T modelObj =
        fileType.isJSON() ? tasRestCommonApi.getUtilityHandle().fromJSONString(objStr,
                                                                               clazz) :
        tasRestCommonApi.getUtilityHandle().fromXMLString(objStr, clazz);
      return modelObj;
    } finally {
      try {
        fis.close();
      } catch (Exception ex) {
        //ignore
      }
    }
  }

}
