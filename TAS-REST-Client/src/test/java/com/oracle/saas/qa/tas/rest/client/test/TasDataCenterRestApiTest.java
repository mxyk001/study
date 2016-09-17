package com.oracle.saas.qa.tas.rest.client.test;


import com.oracle.saas.qa.lib.TestConstants;
import com.oracle.saas.qa.tas.rest.client.TasDataCenterRestClient;
import com.oracle.tas.rest.api.model.DataCenterVersion;
import com.oracle.tas.rest.api.model.ServiceRegistration;
import com.oracle.tas.rest.api.model.ServiceRegistrations;
import com.oracle.tas.rest.api.model.Subscriptions;

import java.io.IOException;

import java.util.logging.Logger;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test(groups = { "TasDataCenterRestApiTests" },
      suiteName = "TasDataCenterRestApiTests")
public class TasDataCenterRestApiTest extends TasRestApiTestBase {

  private static final Logger logger =
    Logger.getLogger(TasDataCenterRestApiTest.class.getName());

  private static final String TAS_DATA_CENTER_REST_API_GRP =
    "TasDataCenterRestApiGrp";


  @BeforeClass(groups =
               { TAS_COMMON_REST_API_GRP, TAS_DATA_CENTER_REST_API_GRP })
  public void beforeClass() {
    init();
  }


  @Test(groups = { TAS_DATA_CENTER_REST_API_GRP })
  public void dataCenterVersionQuery() throws IOException {

    LOG_ENTRY(getLogger(), "dataCenterVersionQuery...");

    TasDataCenterRestClient tasDataCenterApi =
      getRestApiHandle(TasDataCenterRestClient.class);

    DataCenterVersion dataCenterVersion =
      tasDataCenterApi.getDataCenterVersion();
    if (dataCenterVersion != null) {
      writeToFile("DataCenterVersion.xml", dataCenterVersion);
    }

    LOG_EXIT(getLogger(), "dataCenterVersionQuery...");
  }

  @Test(groups = { TAS_DATA_CENTER_REST_API_GRP })
  public void serviceRegistrationsQuery() throws IOException {

    LOG_ENTRY(getLogger(), "serviceRegistrationsQuery...");

    TasDataCenterRestClient tasDataCenterApi =
      getRestApiHandle(TasDataCenterRestClient.class);

    ServiceRegistrations svRegs =
      tasDataCenterApi.getAllServiceRegistrations();

    if (svRegs != null) {
      writeToFile("ServiceRegistrations.xml", svRegs);
    }
    if (svRegs.getItems() != null && svRegs.getItems().size() > 0) {
      String svcregistrationid = svRegs.getItems().get(0).getId();
      ServiceRegistration svReg =
        tasDataCenterApi.getServiceRegistration(svcregistrationid);
      if (svReg != null) {
        writeToFile("ServiceRegistration-" + svcregistrationid + ".xml",
                    svReg);
      }
    }

    LOG_EXIT(getLogger(), "serviceRegistrationsQuery...");
  }

  @Test(groups = { TAS_DATA_CENTER_REST_API_GRP })
  public void subscriptionsQuery() throws IOException {

    LOG_ENTRY(getLogger(), "subscriptionsQuery...");

    TasDataCenterRestClient tasDataCenterApi =
      getRestApiHandle(TasDataCenterRestClient.class);

    String identitydomainname = "ushurricanecotrial05533";
    Subscriptions subs =
      tasDataCenterApi.getAllSubscriptions(null, null, null, null, null,
                                           identitydomainname, null, null,
                                           null, null, null, null, null, null,
                                           null, null, null, null, null, null,
                                           null, null);


    if (subs != null) {
      writeToFile("SubscriptionsDataCenter.xml", subs);
    }

    LOG_EXIT(getLogger(), "subscriptionsQuery...");
  }


  Logger getLogger() {
    return logger;
  }
  //_______________________________________________
  // For Testing this Class

  public static void main(String[] args) {
    String idmName = "ushurricanecotrial05533";
    System.setProperty(TestConstants.LAB_ENV_PROPERTY, "c9qa132");
    System.setProperty(TestConstants.DATA_CENTER, "dc1");
    System.setProperty(TestConstants.REDIS_HOST,
                       "slcn03vmf0254.us.oracle.com");
    System.setProperty(TestConstants.REDIS_KEY, "none");
    System.setProperty(IDM_NM_SYS_PROP, idmName);


    TestNG testng = new TestNG();
    testng.setTestClasses(new Class[] { TasDataCenterRestApiTest.class });
    testng.setVerbose(-1);
    testng.setGroups(TAS_DATA_CENTER_REST_API_GRP);
    //testng.setGroups(TAS_COMMON_REST_API_GRP);
    //testng.setGroups(TAS_COMMON_REST_API_GRP + "," +
    //                 TAS_DATA_CENTER_REST_API_GRP);
    TestListenerAdapter tla = new TestListenerAdapter();
    testng.addListener(tla);
    testng.run();
    System.out.println(tla);
    System.out.println("Exiting Main....");
    System.exit(0);
  }

}
