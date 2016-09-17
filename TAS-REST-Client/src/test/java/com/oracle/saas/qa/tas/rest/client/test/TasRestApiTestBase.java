package com.oracle.saas.qa.tas.rest.client.test;


import com.oracle.tas.rest.api.model.Account;
import com.oracle.tas.rest.api.model.Accounts;
import com.oracle.tas.rest.api.model.Admin;
import com.oracle.tas.rest.api.model.Admins;
import com.oracle.tas.rest.api.model.Countries;
import com.oracle.tas.rest.api.model.Country;
import com.oracle.tas.rest.api.model.CustomAttributeValueSet;
import com.oracle.tas.rest.api.model.CustomAttributeValueSets;
import com.oracle.tas.rest.api.model.DataCenter;
import com.oracle.tas.rest.api.model.DataCenters;
import com.oracle.tas.rest.api.model.DataRegion;
import com.oracle.tas.rest.api.model.DataRegions;
import com.oracle.tas.rest.api.model.IdentityDomain;
import com.oracle.tas.rest.api.model.IdentityDomains;
import com.oracle.tas.rest.api.model.Notification;
import com.oracle.tas.rest.api.model.Notifications;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.Orders;
import com.oracle.tas.rest.api.model.Service;
import com.oracle.tas.rest.api.model.ServiceInstanceStates;
import com.oracle.tas.rest.api.model.ServicePlan;
import com.oracle.tas.rest.api.model.ServicePlans;
import com.oracle.tas.rest.api.model.Services;
import com.oracle.tas.rest.api.model.Subdivision;
import com.oracle.tas.rest.api.model.Subdivisions;
import com.oracle.tas.rest.api.model.Subscription;
import com.oracle.tas.rest.api.model.Subscriptions;
import com.oracle.tas.rest.api.model.TimeZone;
import com.oracle.tas.rest.api.model.TimeZones;
import com.oracle.tas.rest.api.model.UpTimes;
import com.oracle.tas.rest.api.model.Usages;

import java.io.IOException;

import org.testng.annotations.Test;


public abstract class TasRestApiTestBase extends TasRestApiTestCommonBase {

  protected static final String TAS_COMMON_REST_API_GRP =
    "TasCommonRestApiGrp";


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void administeredAccountsQuery() throws IOException {
    LOG_ENTRY(getLogger(), "administeredAccountsQuery...");

    Accounts accts = tasRestCommonApi.getAllUserAdministeredAccounts();
    writeToFile("AdmininsteredAccounts.xml", accts);
    if (accts.getItems() != null && accts.getItems().size() > 0) {

      String acctid = accts.getItems().get(0).getId();
      Account acct = tasRestCommonApi.getUserAdministeredAccount(acctid);
      writeToFile("AdmininsteredAccount.xml", acct);
    }

    LOG_EXIT(getLogger(), "administeredAccountsQuery...");
  }

  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void adminsQuery() throws IOException {
    LOG_ENTRY(getLogger(), "adminsQuery...");

    Admins admins = tasRestCommonApi.getAllAdmins();
    writeToFile("Admins.xml", admins);
    if (admins.getItems() != null && admins.getItems().size() > 0) {
      String adminId = admins.getItems().get(0).getId();
      Admin admin = tasRestCommonApi.getAdmin(adminId);
      writeToFile("Admin.xml", admin);
    }

    LOG_EXIT(getLogger(), "adminsQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void supportedCountriesAndSubDivisionsQuery() throws IOException {
    LOG_ENTRY(getLogger(), "supportedCountriesAndSubDivisionsQuery...");

    Countries countries = tasRestCommonApi.getAllSupportedCountries();
    writeToFile("Supported-Countries.xml", countries);
    String countryId = countries.getItems().get(0).getId();
    Country country = tasRestCommonApi.getSupportedCountry(countryId);
    writeToFile("Supported-Country.xml", country);


    countryId = "US";
    Subdivisions subdivs =
      tasRestCommonApi.getAllSubDivisionsOfCountry(countryId);

    String subdivisionId = subdivs.getItems().get(0).getId();
    Subdivision subdiv =
      tasRestCommonApi.getSubDivisionOfCountry(countryId, subdivisionId);

    LOG_EXIT(getLogger(), "supportedCountriesAndSubDivisionsQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void customAttrsValueSetsQuery() {
    LOG_ENTRY(getLogger(), "customAttrsValueSetsQuery...");

    CustomAttributeValueSets custAttrVSets =
      tasRestCommonApi.getAllCustomAttributeValueSets();

    String serviceId = "500043057"; //JAVA
    custAttrVSets = tasRestCommonApi.getCustomAttributeValueSets(serviceId);

    if (custAttrVSets.getItems() != null &&
        custAttrVSets.getItems().size() > 0) {
      String custAttrValueSetId = custAttrVSets.getItems().get(0).getId();
      CustomAttributeValueSet custAttrVSet =
        tasRestCommonApi.getCustomAttributeValueSet(custAttrValueSetId);
    }

    LOG_EXIT(getLogger(), "customAttrsValueSetsQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void supportedDataCentersQuery() throws IOException {
    LOG_ENTRY(getLogger(), "supportedDataCentersQuery...");

    DataCenters dataCenters = tasRestCommonApi.getAllSupportedDataCenters();
    writeToFile("Supported-DataCenters.xml", dataCenters);

    if (dataCenters.getItems() != null && dataCenters.getItems().size() > 0) {
      DataCenter dc = dataCenters.getItems().get(0);
      String prodSvcType = null;
      String trialSvcType = null;
      boolean regional = false;
      if (dc.getProductionServices() != null &&
          dc.getProductionServices().size() > 0) {
        prodSvcType = dc.getProductionServices().get(0).getType();
      }
      if (dc.getTrialServices() != null && dc.getTrialServices().size() > 0) {
        trialSvcType = dc.getTrialServices().get(0).getType();
      }
      dataCenters =
          tasRestCommonApi.getAllSupportedDataCenters(prodSvcType, trialSvcType,
                                                      regional);

      String dataCenterId = dc.getId();
      DataCenter dataCenter =
        tasRestCommonApi.getSupportedDataCenter(dataCenterId);
    }

    LOG_EXIT(getLogger(), "supportedDataCentersQuery...");
  }

  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void supportedDataCenterRegionsQuery() throws IOException {
    LOG_ENTRY(getLogger(), "supportedDataCenterRegionsQuery...");

    DataRegions dataCenterRegions =
      tasRestCommonApi.getAllSupportedDataCenterRegions();

    writeToFile("Supported-DataCenter-Regions.xml", dataCenterRegions);

    if (dataCenterRegions.getItems() != null &&
        dataCenterRegions.getItems().size() > 0) {
      DataRegion dcr = dataCenterRegions.getItems().get(0);
      String prodSvcType = null;
      String trialSvcType = null;
      if (dcr.getProductionServices() != null &&
          dcr.getProductionServices().size() > 0) {
        prodSvcType = dcr.getProductionServices().get(0).getType();
      }
      if (dcr.getTrialServices() != null &&
          dcr.getTrialServices().size() > 0) {
        trialSvcType = dcr.getTrialServices().get(0).getType();
      }
      dataCenterRegions =
          tasRestCommonApi.getAllSupportedDataCenterRegions(prodSvcType,
                                                            trialSvcType);

      String dataCenterRegionId = dcr.getId();
      DataRegion dataCenterRegion =
        tasRestCommonApi.getSupportedDataCenterRegion(dataCenterRegionId);
    }

    LOG_EXIT(getLogger(), "supportedDataCenterRegionsQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void identityDomainsQuery() throws IOException {
    LOG_ENTRY(getLogger(), "identityDomainsQuery...");

    IdentityDomains identityDomains = tasRestCommonApi.getAllIdentityDomains();
    writeToFile("All-IdentityDomains.xml", identityDomains);

    if (identityDomains.getItems() != null &&
        identityDomains.getItems().size() > 0) {
      IdentityDomain idm = identityDomains.getItems().get(0);

      String accountId = idm.getAccount().getId();
      String idmName = idm.getName();
      identityDomains =
          tasRestCommonApi.getAllIdentityDomains(accountId, dataCenterId, null,
                                                 idmName, null, null, null,
                                                 null, null, null);


      String identitydomainid = idm.getId();
      IdentityDomain identityDomain =
        tasRestCommonApi.getIdentityDomain(identitydomainid);
    }

    LOG_EXIT(getLogger(), "identityDomainsQuery...");
  }

  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void notificationsQuery() {
    LOG_ENTRY(getLogger(), "notificationsQuery...");

    Notifications notifications = tasRestCommonApi.getAllNotifications();

    if (notifications.getItems() != null &&
        notifications.getItems().size() > 0) {
      String notfId = notifications.getItems().get(0).getId();
      Notification notification = tasRestCommonApi.getNotification(notfId);
    }

    LOG_EXIT(getLogger(), "notificationsQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void ordersQuery() throws IOException {
    LOG_ENTRY(getLogger(), "ordersQuery...");

    Orders orders = tasRestCommonApi.getAllOrders();
    writeToFile("All-Orders.xml", orders);
    if (orders.getItems() != null && orders.getItems().size() > 0) {
      String orderId = orders.getItems().get(0).getId();
      Order order = tasRestCommonApi.getOrder(orderId);
    }

    LOG_EXIT(getLogger(), "ordersQuery...");
  }

  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void supportedServicesAndPlansQuery() throws IOException {
    LOG_ENTRY(getLogger(), "supportedServicesAndPlansQuery...");

    Services services = tasRestCommonApi.getAllSupportedServices();
    writeToFile("SupportedServicesAndPlans.xml", services);
    if (services.getItems() != null && services.getItems().size() > 0) {
      String svcId = services.getItems().get(0).getId();
      Service service = tasRestCommonApi.getSupportedService(svcId);

      ServicePlans servicePlans = tasRestCommonApi.getAllServicePlans(svcId);

      if (servicePlans.getItems() != null &&
          servicePlans.getItems().size() > 0) {
        String svcPlanId = servicePlans.getItems().get(0).getId();
        ServicePlan servicePlan =
          tasRestCommonApi.getServicePlan(svcId, svcPlanId);
      }
    }

    LOG_EXIT(getLogger(), "supportedServicesAndPlansQuery...");
  }


  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void subscriptionsAndStatesAndUpTimesAndUsagesQuery() throws IOException {
    LOG_ENTRY(getLogger(),
              "subscriptionsAndStatesAndUpTimesAndUsagesQuery...");

    Subscriptions subscriptions = tasRestCommonApi.getAllSubscriptions();
    writeToFile("Subscriptions.xml", subscriptions);
    if (subscriptions.getItems() != null &&
        subscriptions.getItems().size() > 0) {
      String subscId = subscriptions.getItems().get(0).getId();
      Subscription subscription = tasRestCommonApi.getSubscription(subscId);
      writeToFile("Subscription.xml", subscription);
      ServiceInstanceStates serviceInstanceStates =
        tasRestCommonApi.getAllServiceInstanceStates(subscId);
      writeToFile("ServiceInstanceStates.xml", serviceInstanceStates);
      UpTimes upTimes = tasRestCommonApi.getAllUpTimes(subscId);
      writeToFile("UpTimes.xml", upTimes);
      Usages usages = tasRestCommonApi.getAllUsages(subscId);
      writeToFile("Usages.xml", usages);
    }

    LOG_EXIT(getLogger(), "subscriptionsAndStatesAndUpTimesAndUsagesQuery...");
  }

  @Test(groups = { TAS_COMMON_REST_API_GRP })
  public void timezonesQuery() throws IOException {
    LOG_ENTRY(getLogger(), "timezonesQuery...");

    TimeZones timezones = tasRestCommonApi.getAllTimeZones();
    writeToFile("TimeZones.xml", timezones);
    if (timezones.getItems() != null && timezones.getItems().size() > 0) {
      String base64urlid = timezones.getItems().get(0).getId(); //TODO check ID
      TimeZone timezone = tasRestCommonApi.getTimeZone(base64urlid);
      writeToFile("Timezone.xml", timezone);
    }

    LOG_EXIT(getLogger(), "timezonesQuery...");
  }

}
