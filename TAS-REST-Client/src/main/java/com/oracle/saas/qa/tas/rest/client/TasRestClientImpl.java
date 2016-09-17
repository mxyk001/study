package com.oracle.saas.qa.tas.rest.client;


import com.oracle.tas.rest.api.model.Account;
import com.oracle.tas.rest.api.model.Accounts;
import com.oracle.tas.rest.api.model.Admin;
import com.oracle.tas.rest.api.model.Admins;
import com.oracle.tas.rest.api.model.AppCredential;
import com.oracle.tas.rest.api.model.AppCredentials;
import com.oracle.tas.rest.api.model.CentralVersion;
import com.oracle.tas.rest.api.model.ConsoleRegistration;
import com.oracle.tas.rest.api.model.ConsoleRegistrations;
import com.oracle.tas.rest.api.model.Countries;
import com.oracle.tas.rest.api.model.Country;
import com.oracle.tas.rest.api.model.Coupon;
import com.oracle.tas.rest.api.model.CouponDistributor;
import com.oracle.tas.rest.api.model.Coupons;
import com.oracle.tas.rest.api.model.CreditAccount;
import com.oracle.tas.rest.api.model.CreditAccounts;
import com.oracle.tas.rest.api.model.CreditEntitlement;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstance;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstances;
import com.oracle.tas.rest.api.model.CreditEntitlements;
import com.oracle.tas.rest.api.model.CustomAttributeValueSet;
import com.oracle.tas.rest.api.model.CustomAttributeValueSets;
import com.oracle.tas.rest.api.model.DataCenter;
import com.oracle.tas.rest.api.model.DataCenterVersion;
import com.oracle.tas.rest.api.model.DataCenters;
import com.oracle.tas.rest.api.model.DataRegion;
import com.oracle.tas.rest.api.model.DataRegions;
import com.oracle.tas.rest.api.model.Documents;
import com.oracle.tas.rest.api.model.IdentityDomain;
import com.oracle.tas.rest.api.model.IdentityDomains;
import com.oracle.tas.rest.api.model.IdentityValidations;
import com.oracle.tas.rest.api.model.Messages;
import com.oracle.tas.rest.api.model.MetricStates;
import com.oracle.tas.rest.api.model.Notification;
import com.oracle.tas.rest.api.model.Notifications;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.Orders;
import com.oracle.tas.rest.api.model.Redemption;
import com.oracle.tas.rest.api.model.Redemptions;
import com.oracle.tas.rest.api.model.Service;
import com.oracle.tas.rest.api.model.ServiceInstanceStates;
import com.oracle.tas.rest.api.model.ServicePlan;
import com.oracle.tas.rest.api.model.ServicePlans;
import com.oracle.tas.rest.api.model.ServiceRegistration;
import com.oracle.tas.rest.api.model.ServiceRegistrations;
import com.oracle.tas.rest.api.model.Services;
import com.oracle.tas.rest.api.model.Subdivision;
import com.oracle.tas.rest.api.model.Subdivisions;
import com.oracle.tas.rest.api.model.Subscription;
import com.oracle.tas.rest.api.model.Subscriptions;
import com.oracle.tas.rest.api.model.TasCentral;
import com.oracle.tas.rest.api.model.TasDataCenter;
import com.oracle.tas.rest.api.model.TimeZone;
import com.oracle.tas.rest.api.model.TimeZones;
import com.oracle.tas.rest.api.model.TrialValidations;
import com.oracle.tas.rest.api.model.UpTimes;
import com.oracle.tas.rest.api.model.Usages;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URI;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.module.SimpleModule;


/**
 *  Client for making REST Calls To TAS.
 *  Implements the Contract Exposed By Both the TasCentralRestClient and TasDataCenterRestClient Interfaces
 *
 *  @author Haris Shah
 *
 *  @see com.oracle.saas.qa.tas.rest.client.TasCentralRestClient
 *  @see com.oracle.saas.qa.tas.rest.client.TasDataCenterRestClient
 *  @see com.oracle.saas.qa.tas.rest.client.TasOrderBuilder
 *  @see com.oracle.saas.qa.tas.rest.client.TasRestFactory
 */
final class TasRestClientImpl implements TasCommonRestUtil,
                                         TasCentralRestClient,
                                         TasDataCenterRestClient {

  private static Logger logger =
    Logger.getLogger(TasRestClientImpl.class.getName());

  private Map<String, String> commonMessageHeaders;
  TasRestAPIType apiType;
  private String versionid;
  private URI baseURI;
  private String authUserId;
  private String authPassword;
  private String oracleUserId;
  private String identitydomainname;
  private static boolean sunHttpClient;
  private Client client;
  private static boolean postWithPatchOverride;

  private static final int CONNECT_TIMEOUT_MILLIS = 1 * 60 * 1000;
  private static final int READ_TIMEOUT_MILLIS = 2 * 60 * 1000;
  private static final String ORACLE_USER_ID_HEADER = "X-Oracle-UserId";
  private static final String ORACLE_IDM_HEADER = "X-Oracle-IdentityDomain";

  protected static final String COMMON_PATH_URI = ".common";
  //_______________________________________________________
  // UpdateOrder with a PostJson Call rather than PatchJson
  private static final String POST_WITH_PATCH_OVERRIDE_HEADER =
    "X-HTTP-Method-Override";
  private static final String PATCH_METHOD = "PATCH";
  private static final String POST_METHOD = "POST";

  private static final String SUN_HTTP_CLIENT_SYS_PROP =
    "tas.rest.sun.http.client";
  private static final String PATCH_OVERRIDE_SYS_PROP =
    "tas.rest.patch.override";


  static {
    try {
      // Create a trust manager that does not validate certificate chains
      TrustManager[] trustAllCerts =
        trustAllCerts = new TrustManager[] { new javax.net.ssl.X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
            }
          } };
      // Install the all-trusting trust manager
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      // Create all-trusting host name verifier
      HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      };

      // Install the all-trusting host verifier
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

    } catch (Throwable e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
    logger.info("Looking for System Property -> ( " +
                SUN_HTTP_CLIENT_SYS_PROP + " )");
    sunHttpClient =
        Boolean.parseBoolean(System.getProperty(SUN_HTTP_CLIENT_SYS_PROP,
                                                Boolean.FALSE.toString()));
    logger.info(" SunHttpClient -> ( " + sunHttpClient + " ) ");

    logger.info("Looking for System Property -> ( " + PATCH_OVERRIDE_SYS_PROP +
                " )");
    postWithPatchOverride =
        Boolean.parseBoolean(System.getProperty(PATCH_OVERRIDE_SYS_PROP,
                                                Boolean.FALSE.toString()));
    logger.info(" PostWithPatchOverride -> ( " + postWithPatchOverride +
                " ) ");
  }

  public TasOrderBuilder getTasOrderBuilderHandle() {
    return new TasOrderBuilderImpl(this);
  }

  public CentralVersion getCentralVersion() {

    LOG_ENTRY("getCentralVersion[" + apiType + "] VersionId -> ( " +
              versionid + " ) ");

    CentralVersion centralVersion =
      TasCentral.identityDomainNameTasApiVersionId(client, baseURI,
                                                   identitydomainname,
                                                   versionid).getAsJson(CentralVersion.class);

    LOG_EXIT("getCentralVersion[" + apiType + "] VersionId -> ( " + versionid +
             " )...CentralVersion -> \n" +
        toXMLString(centralVersion));

    return centralVersion;
  }

  public DataCenterVersion getDataCenterVersion() {

    LOG_ENTRY("getDataCenterVersion[" + apiType + "] VersionId -> ( " +
              versionid + " ) ");

    DataCenterVersion dataCenterVersion =
      TasDataCenter.identityDomainNameTasApiVersionId(client, baseURI,
                                                      identitydomainname,
                                                      versionid).getAsJson(DataCenterVersion.class);

    LOG_EXIT("getDataCenterVersion[" + apiType + "] VersionId -> ( " +
             versionid + " )...DataCentralVersion -> \n" +
        toXMLString(dataCenterVersion));

    return dataCenterVersion;
  }


  public Accounts getAllUserAdministeredAccounts() {

    LOG_ENTRY("getAllUserAdministeredAccounts[" + apiType + "]");

    Accounts accounts = null;
    if (apiType.isTasCentral()) {
      accounts =
          TasCentral.identityDomainNameTasApiVersionIdAccounts(client, baseURI,
                                                               identitydomainname,
                                                               versionid).getAsJson(Accounts.class);
    } else if (apiType.isTasDataCenter()) {
      accounts =
          TasDataCenter.identityDomainNameTasApiVersionIdAccounts(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(Accounts.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllUserAdministeredAccounts[" + apiType +
             "]...Accounts -> \n" +
        toXMLString(accounts));

    return accounts;
  }

  public Account getUserAdministeredAccount(String accountid) {

    LOG_ENTRY("getUserAdministeredAccount[" + apiType + "]...AccountId -> ( " +
              accountid + " ) ");

    Account account = null;
    if (apiType.isTasCentral()) {
      account =
          TasCentral.identityDomainNameTasApiVersionIdAccountsAccountId(client,
                                                                        baseURI,
                                                                        identitydomainname,
                                                                        versionid,
                                                                        accountid).getAsJson(Account.class);
    } else if (apiType.isTasDataCenter()) {
      account =
          TasDataCenter.identityDomainNameTasApiVersionIdAccountsAccountId(client,
                                                                           baseURI,
                                                                           identitydomainname,
                                                                           versionid,
                                                                           accountid).getAsJson(Account.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getUserAdministeredAccount[" + apiType + "]...AccountId -> ( " +
             accountid + " ) Account ->\n" +
        toXMLString(account));

    return account;

  }

  public Admins getAllAdmins() {

    LOG_ENTRY("getAllAdmins[" + apiType + "]");

    Admins allAdmins = null;
    Admins admins = null;
    do {
      if (allAdmins == null) {
        if (apiType.isTasCentral()) {
          admins =
              TasCentral.identityDomainNameTasApiVersionIdAdmins(client, baseURI,
                                                                 identitydomainname,
                                                                 versionid).getAsJson(Admins.class);
        } else if (apiType.isTasDataCenter()) {
          admins =
              TasDataCenter.identityDomainNameTasApiVersionIdAdmins(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid).getAsJson(Admins.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }

      } else {
        admins =
            getAllAdmins(null, null, null, null, null, null, String.valueOf((admins.getLimit() +
                                                                             admins.getOffset())));
        admins.getItems().addAll(0, allAdmins.getItems());
      }
      allAdmins = admins;
    } while (admins.isHasMore());

    LOG_EXIT("getAllAdmins[" + apiType + "]...AllAdmins ->\n" +
        toXMLString(allAdmins));

    return allAdmins;


  }

  public Admins getAllAdmins(String accountid, String identitydomainid,
                             String subscriptionid, String username,
                             String scopetypes, String limit, String offset) {

    LOG_ENTRY("getAllAdmins[" + apiType + "]...AccountId -> ( " + accountid +
              " ) Identitydomainid -> ( " + identitydomainid +
              " ) Subscriptionid -> ( " + subscriptionid +
              " ) Username -> ( " + username + " ) Scopetypes -> ( " +
              scopetypes + " ) Limit -> ( " + limit + " ) Offset -> ( " +
              offset + " )");

    Admins allAdmins = null;
    if (apiType.isTasCentral()) {
      allAdmins =
          TasCentral.identityDomainNameTasApiVersionIdAdmins(client, baseURI,
                                                             identitydomainname,
                                                             versionid).getAsJson(accountid,
                                                                                  identitydomainid,
                                                                                  username,
                                                                                  subscriptionid,
                                                                                  scopetypes,
                                                                                  limit,
                                                                                  offset,
                                                                                  null,
                                                                                  authUserId,
                                                                                  Admins.class);
    } else if (apiType.isTasDataCenter()) {
      allAdmins =
          TasDataCenter.identityDomainNameTasApiVersionIdAdmins(client, baseURI,
                                                                identitydomainname,
                                                                versionid).getAsJson(accountid,
                                                                                     identitydomainid,
                                                                                     username,
                                                                                     scopetypes,
                                                                                     limit,
                                                                                     offset,
                                                                                     null,
                                                                                     authUserId,
                                                                                     Admins.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllAdmins[" + apiType + "]...AccountId -> ( " + accountid +
             " ) Identitydomainid -> ( " + identitydomainid +
             " ) Subscriptionid -> ( " + subscriptionid + " ) Username -> ( " +
             username + " ) Scopetypes -> ( " + scopetypes + " ) Limit -> ( " +
             limit + " ) Offset -> ( " + offset + " )...AllAdmins ->\n" +
        toXMLString(allAdmins));

    return allAdmins;
  }

  public Admin getAdmin(String adminid) {

    LOG_ENTRY("getAdmin[" + apiType + "]...Adminid -> ( " + adminid + " ) ");

    Admin admin = null;
    if (apiType.isTasCentral()) {
      admin =
          TasCentral.identityDomainNameTasApiVersionIdAdminsAdminId(client, baseURI,
                                                                    identitydomainname,
                                                                    versionid,
                                                                    adminid).getAsJson(Admin.class);
    } else if (apiType.isTasDataCenter()) {
      admin =
          TasDataCenter.identityDomainNameTasApiVersionIdAdminsAdminId(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid,
                                                                       adminid).getAsJson(Admin.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAdmin[" + apiType + "]...Adminid -> ( " + adminid +
             " )...Admin ->\n" +
        toXMLString(admin));

    return admin;
  }


  public Countries getAllSupportedCountries() {

    LOG_ENTRY("getAllSupportedCountries[" + apiType + "]");

    Countries allCountries = null;
    Countries countries = null;
    do {
      if (allCountries == null) {
        if (apiType.isTasCentral()) {
          countries =
              TasCentral.identityDomainNameTasApiVersionIdCountries(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid).getAsJson(Countries.class);
        } else if (apiType.isTasDataCenter()) {
          countries =
              TasDataCenter.identityDomainNameTasApiVersionIdCountries(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid).getAsJson(Countries.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }
      } else {
        countries =
            getAllSupportedCountries(null, null, null, null, String.valueOf((countries.getLimit() +
                                                                             countries.getOffset())));
        countries.getItems().addAll(0, allCountries.getItems());
      }
      allCountries = countries;
    } while (countries.isHasMore());

    LOG_EXIT("getAllSupportedCountries[" + apiType +
             "]...AllSupportedCountries ->\n" +
        toXMLString(allCountries));

    return allCountries;
  }

  public Countries getAllSupportedCountries(String common, String countryId,
                                            String expands, String limit,
                                            String offset) {

    LOG_ENTRY("getAllSupportedCountries[" + apiType + "] Common -> ( " +
              common + " ) CountryId -> ( " + countryId + " ) Expands -> ( " +
              expands + " ) Limit -> ( " + limit + " ) Offset -> ( " + offset +
              " )");

    Countries allCountries = null;
    if (apiType.isTasCentral()) {
      allCountries =
          TasCentral.identityDomainNameTasApiVersionIdCountries(client,
                                                                baseURI,
                                                                identitydomainname,
                                                                versionid).getAsJson(common,
                                                                                     countryId,
                                                                                     expands,
                                                                                     limit,
                                                                                     offset,
                                                                                     Countries.class);
    } else if (apiType.isTasDataCenter()) {
      allCountries =
          TasDataCenter.identityDomainNameTasApiVersionIdCountries(client,
                                                                   baseURI,
                                                                   identitydomainname,
                                                                   versionid).getAsJson(common,
                                                                                        countryId,
                                                                                        expands,
                                                                                        limit,
                                                                                        offset,
                                                                                        Countries.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedCountries[" + apiType + "] Common -> ( " +
             common + " ) CountryId ( " + countryId + " ) Expands -> ( " +
             expands + " ) Limit -> ( " + limit + " ) Offset -> ( " + offset +
             " ) AllSupportedCountries ->\n" +
        toXMLString(allCountries));

    return allCountries;
  }

  public Country getSupportedCountry(String countryid) {

    LOG_ENTRY("getSupportedCountry[" + apiType + "] CountryId -> ( " +
              countryid + " )");

    Country country = null;
    if (apiType.isTasCentral()) {
      country =
          TasCentral.identityDomainNameTasApiVersionIdCountriesCountryId(client,
                                                                         baseURI,
                                                                         identitydomainname,
                                                                         versionid,
                                                                         countryid).getAsJson(Country.class);
    } else if (apiType.isTasDataCenter()) {
      country =
          TasDataCenter.identityDomainNameTasApiVersionIdCountriesCountryId(client,
                                                                            baseURI,
                                                                            identitydomainname,
                                                                            versionid,
                                                                            countryid).getAsJson(Country.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSupportedCountry[" + apiType + "] CountryId -> ( " +
             countryid + " ) SupportedCountry ->\n" +
        toXMLString(country));

    return country;
  }

  public Subdivisions getAllSubDivisionsOfCountry(String countryid) {

    LOG_ENTRY("getAllSubDivisionsOfCountry[" + apiType + "] CountryId -> ( " +
              countryid + " )");

    Subdivisions subdivisions = null;
    if (apiType.isTasCentral()) {
      subdivisions =
          TasCentral.identityDomainNameTasApiVersionIdCountriesCountryIdSubdivisions(client,
                                                                                     baseURI,
                                                                                     identitydomainname,
                                                                                     versionid,
                                                                                     countryid).getAsJson(Subdivisions.class);
    } else if (apiType.isTasDataCenter()) {
      subdivisions =
          TasDataCenter.identityDomainNameTasApiVersionIdCountriesCountryIdSubdivisions(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        countryid).getAsJson(Subdivisions.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSubDivisionsOfCountry[" + apiType + "] CountryId -> ( " +
             countryid + " ) AllSubDivisions ->\n" +
        toXMLString(subdivisions));

    return subdivisions;
  }

  public Subdivision getSubDivisionOfCountry(String countryid,
                                             String subdivisionid) {

    LOG_ENTRY("getSubDivisionOfCountry[" + apiType + "] CountryId -> ( " +
              countryid + " ) SubdivisionId -> ( " + subdivisionid + " )");

    Subdivision subdivision = null;
    if (apiType.isTasCentral()) {
      subdivision =
          TasCentral.identityDomainNameTasApiVersionIdCountriesCountryIdSubdivisionsId(client,
                                                                                       baseURI,
                                                                                       identitydomainname,
                                                                                       versionid,
                                                                                       countryid,
                                                                                       subdivisionid).getAsJson(Subdivision.class);
    } else if (apiType.isTasDataCenter()) {
      subdivision =
          TasDataCenter.identityDomainNameTasApiVersionIdCountriesCountryIdSubdivisionsId(client,
                                                                                          baseURI,
                                                                                          identitydomainname,
                                                                                          versionid,
                                                                                          countryid,
                                                                                          subdivisionid).getAsJson(Subdivision.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSubDivisionOfCountry[" + apiType + "] CountryId -> ( " +
             countryid + " ) SubdivisionId -> ( " + subdivisionid +
             " ) SubdivisionOfCountry ->\n" +
        toXMLString(subdivision));

    return subdivision;
  }

  public CustomAttributeValueSets getAllCustomAttributeValueSets() {

    LOG_ENTRY("getAllCustomAttributeValueSets[" + apiType + "]");

    CustomAttributeValueSets customAttrValSets = null;
    if (apiType.isTasCentral()) {
      customAttrValSets =
          TasCentral.identityDomainNameTasApiVersionIdCustomAttributeValueSets(client,
                                                                               baseURI,
                                                                               identitydomainname,
                                                                               versionid).getAsJson(CustomAttributeValueSets.class);
    } else if (apiType.isTasDataCenter()) {
      customAttrValSets =
          TasDataCenter.identityDomainNameTasApiVersionIdCustomAttributeValueSets(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid).getAsJson(CustomAttributeValueSets.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllCustomAttributeValueSets[" + apiType +
             "]  AllCustomAttributeValueSets ->\n" +
        toXMLString(customAttrValSets));

    return customAttrValSets;
  }

  public CustomAttributeValueSets getCustomAttributeValueSets(String serviceid) {

    LOG_ENTRY("getCustomAttributeValueSets[" + apiType + "] ServiceId -> ( " +
              serviceid + " )");

    CustomAttributeValueSets customAttrValSets = null;
    if (apiType.isTasCentral()) {
      customAttrValSets =
          TasCentral.identityDomainNameTasApiVersionIdCustomAttributeValueSets(client,
                                                                               baseURI,
                                                                               identitydomainname,
                                                                               versionid).getAsJson(serviceid,
                                                                                                    null,
                                                                                                    CustomAttributeValueSets.class);
    } else if (apiType.isTasDataCenter()) {
      customAttrValSets =
          TasDataCenter.identityDomainNameTasApiVersionIdCustomAttributeValueSets(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid).getAsJson(serviceid,
                                                                                                       null,
                                                                                                       CustomAttributeValueSets.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getCustomAttributeValueSets[" + apiType + "] ServiceId -> ( " +
             serviceid + " ) AllCustomAttributeValueSets ->\n" +
        toXMLString(customAttrValSets));

    return customAttrValSets;
  }

  public CustomAttributeValueSet getCustomAttributeValueSet(String customattrid) {

    LOG_ENTRY("getCustomAttributeValueSet[" + apiType +
              "] Customattrid -> ( " + customattrid + " )");

    CustomAttributeValueSet customAttrValSet = null;
    if (apiType.isTasCentral()) {
      customAttrValSet =
          TasCentral.identityDomainNameTasApiVersionIdCustomAttributeValueSetsId(client,
                                                                                 baseURI,
                                                                                 identitydomainname,
                                                                                 versionid,
                                                                                 customattrid).getAsJson(CustomAttributeValueSet.class);
    } else if (apiType.isTasDataCenter()) {
      customAttrValSet =
          TasDataCenter.identityDomainNameTasApiVersionIdCustomAttributeValueSetsId(client,
                                                                                    baseURI,
                                                                                    identitydomainname,
                                                                                    versionid,
                                                                                    customattrid).getAsJson(CustomAttributeValueSet.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getCustomAttributeValueSet[" + apiType + "] Customattrid -> ( " +
             customattrid + " ) CustomAttributeValueSet ->\n" +
        toXMLString(customAttrValSet));

    return customAttrValSet;

  }

  public DataCenters getAllSupportedDataCenters() {

    LOG_ENTRY("getAllSupportedDataCenters[" + apiType + "]");

    DataCenters dataCenters = null;
    if (apiType.isTasCentral()) {
      dataCenters =
          TasCentral.identityDomainNameTasApiVersionIdDataCenters(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(DataCenters.class);
    } else if (apiType.isTasDataCenter()) {
      dataCenters =
          TasDataCenter.identityDomainNameTasApiVersionIdDataCenters(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).getAsJson(DataCenters.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedDataCenters[" + apiType +
             "] AllSupportedDataCenters ->\n" +
        toXMLString(dataCenters));

    return dataCenters;
  }

  public DataCenters getAllSupportedDataCenters(String trialservicetype,
                                                String productionservicetype,
                                                boolean regional) {

    LOG_ENTRY("getAllSupportedDataCenters[" + apiType +
              "] Trialservicetype -> ( " + trialservicetype +
              " ) Productionservicetype -> ( " + productionservicetype +
              " ) Regional -> ( " + regional + " )");

    DataCenters dataCenters = null;
    if (apiType.isTasCentral()) {
      dataCenters =
          TasCentral.identityDomainNameTasApiVersionIdDataCenters(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(trialservicetype,
                                                                                       productionservicetype,
                                                                                       Boolean.toString(regional),
                                                                                       null,
                                                                                       null,
                                                                                       null,
                                                                                       DataCenters.class);
    } else if (apiType.isTasDataCenter()) {
      dataCenters =
          TasDataCenter.identityDomainNameTasApiVersionIdDataCenters(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).getAsJson(trialservicetype,
                                                                                          productionservicetype,
                                                                                          Boolean.toString(regional),
                                                                                          null,
                                                                                          DataCenters.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedDataCenters[" + apiType +
             "] Trialservicetype -> ( " + trialservicetype +
             " ) Productionservicetype -> ( " + productionservicetype +
             " ) Regional -> ( " + regional +
             " ) AllSupportedDataCenters ->\n" +
        toXMLString(dataCenters));

    return dataCenters;

  }

  public DataCenter getSupportedDataCenter(String datacenterid) {

    LOG_ENTRY("getSupportedDataCenter[" + apiType + "] DataCenterId -> ( " +
              datacenterid + " )");

    DataCenter dataCenter = null;
    if (apiType.isTasCentral()) {
      dataCenter =
          TasCentral.identityDomainNameTasApiVersionIdDataCentersDataCenterId(client,
                                                                              baseURI,
                                                                              identitydomainname,
                                                                              versionid,
                                                                              datacenterid).getAsJson(DataCenter.class);
    } else if (apiType.isTasDataCenter()) {
      dataCenter =
          TasDataCenter.identityDomainNameTasApiVersionIdDataCentersDataCenterId(client,
                                                                                 baseURI,
                                                                                 identitydomainname,
                                                                                 versionid,
                                                                                 datacenterid).getAsJson(DataCenter.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSupportedDataCenter[" + apiType + "] DataCenterId -> ( " +
             datacenterid + " ) SupportedDataCenter ->\n" +
        toXMLString(dataCenter));

    return dataCenter;
  }

  public DataRegions getAllSupportedDataCenterRegions() {

    LOG_ENTRY("getAllSupportedDataCenterRegions[" + apiType + "]");

    DataRegions dataRegions = null;
    if (apiType.isTasCentral()) {
      dataRegions =
          TasCentral.identityDomainNameTasApiVersionIdDataRegions(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(DataRegions.class);
    } else if (apiType.isTasDataCenter()) {
      dataRegions =
          TasDataCenter.identityDomainNameTasApiVersionIdDataRegions(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).getAsJson(DataRegions.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedDataCenterRegions[" + apiType +
             "] AllSupportedDataCenterRegions ->\n" +
        toXMLString(dataRegions));

    return dataRegions;
  }

  public DataRegions getAllSupportedDataCenterRegions(String trialservicetype,
                                                      String productionservicetype) {

    LOG_ENTRY("getAllSupportedDataCenterRegions[" + apiType +
              "] Trialservicetype -> ( " + trialservicetype +
              " ) Productionservicetype -> ( " + productionservicetype + " )");

    DataRegions dataRegions = null;
    if (apiType.isTasCentral()) {
      dataRegions =
          TasCentral.identityDomainNameTasApiVersionIdDataRegions(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(trialservicetype,
                                                                                       productionservicetype,
                                                                                       null,
                                                                                       DataRegions.class);
    } else if (apiType.isTasDataCenter()) {
      dataRegions =
          TasDataCenter.identityDomainNameTasApiVersionIdDataRegions(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).getAsJson(trialservicetype,
                                                                                          productionservicetype,
                                                                                          null,
                                                                                          DataRegions.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedDataCenterRegions[" + apiType +
             "] Trialservicetype -> ( " + trialservicetype +
             " ) Productionservicetype -> ( " + productionservicetype +
             " ) AllSupportedDataCenterRegions ->\n" +
        toXMLString(dataRegions));

    return dataRegions;

  }

  public DataRegion getSupportedDataCenterRegion(String dataregionid) {

    LOG_ENTRY("getSupportedDataCenterRegion[" + apiType +
              "] DataRegionid -> ( " + dataregionid + " )");

    DataRegion dataRegion = null;
    if (apiType.isTasCentral()) {
      dataRegion =
          TasCentral.identityDomainNameTasApiVersionIdDataRegionsDataRegionId(client,
                                                                              baseURI,
                                                                              identitydomainname,
                                                                              versionid,
                                                                              dataregionid).getAsJson(DataRegion.class);
    } else if (apiType.isTasDataCenter()) {
      dataRegion =
          TasDataCenter.identityDomainNameTasApiVersionIdDataRegionsDataRegionId(client,
                                                                                 baseURI,
                                                                                 identitydomainname,
                                                                                 versionid,
                                                                                 dataregionid).getAsJson(DataRegion.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSupportedDataCenterRegion[" + apiType +
             "] DataRegionid -> ( " + dataregionid +
             " ) SupportedDataCenterRegion ->\n" +
        toXMLString(dataRegion));

    return dataRegion;
  }

  public Documents getAllDocumentContracts(String documentcontractid,
                                           String documentcontractname) { //TODO check

    LOG_ENTRY("getAllDocumentContracts[" + apiType +
              "] DocumentContractId -> ( " + documentcontractid +
              " ) DocumentContractName -> ( " + documentcontractname + " )");

    Documents documents =
      TasCentral.identityDomainNameTasApiVersionIdDocumentsDocumentIdName(client,
                                                                          baseURI,
                                                                          identitydomainname,
                                                                          versionid,
                                                                          documentcontractid,
                                                                          documentcontractname).getAsJson(Documents.class);

    LOG_EXIT("getAllDocumentContracts[" + apiType +
             "] DocumentContractName -> ( " + documentcontractname +
             " ) AllDocumentContracts ->\n" +
        toXMLString(documents));

    return documents;
  }


  public IdentityDomains getAllIdentityDomains() {

    LOG_ENTRY("getAllIdentityDomains[" + apiType + "]");

    IdentityDomains identityDomains = null;
    if (apiType.isTasCentral()) {
      identityDomains =
          TasDataCenter.identityDomainNameTasApiVersionIdIdentityDomains(client,
                                                                         baseURI,
                                                                         identitydomainname,
                                                                         versionid).getAsJson(IdentityDomains.class);
    } else if (apiType.isTasDataCenter()) {
      identityDomains =
          TasDataCenter.identityDomainNameTasApiVersionIdIdentityDomains(client,
                                                                         baseURI,
                                                                         identitydomainname,
                                                                         versionid).getAsJson(IdentityDomains.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllIdentityDomains[" + apiType + "] AllIdentityDomains ->\n" +
        toXMLString(identityDomains));

    return identityDomains;
  }


  public IdentityDomains getAllIdentityDomains(String accountid,
                                               String datacenterid,
                                               String customerZoneid,
                                               String idmName,
                                               String idmDisplayName,
                                               String statuses,
                                               String excludedstatuses,
                                               String hasImpersonateeTypes,
                                               String limit, String offset) {

    LOG_ENTRY("getAllIdentityDomains[" + apiType + "] Accountid -> ( " +
              accountid + " ) DataCenterid -> ( " + datacenterid +
              " ) CustomerZoneid -> ( " + customerZoneid + " ) IdmName -> ( " +
              idmName + " ) IdmDisplayName -> ( " + idmDisplayName +
              " ) Statuses -> ( " + statuses + " ) Excludedstatuses -> ( " +
              excludedstatuses + " ) HasImpersonateeTypes -> ( " +
              hasImpersonateeTypes + " ) Limit -> ( " + limit +
              " ) Offset -> ( " + offset + " ) ");

    IdentityDomains identityDomains = null;
    if (apiType.isTasCentral()) {
      identityDomains =
          TasCentral.identityDomainNameTasApiVersionIdIdentityDomains(client,
                                                                      baseURI,
                                                                      identitydomainname,
                                                                      versionid).getAsJson(accountid,
                                                                                           datacenterid,
                                                                                           customerZoneid,
                                                                                           idmName,
                                                                                           idmDisplayName,
                                                                                           statuses,
                                                                                           excludedstatuses,
                                                                                           hasImpersonateeTypes,
                                                                                           limit,
                                                                                           offset,
                                                                                           null,
                                                                                           null,
                                                                                           IdentityDomains.class);

    } else if (apiType.isTasDataCenter()) {
      identityDomains =
          TasDataCenter.identityDomainNameTasApiVersionIdIdentityDomains(client,
                                                                         baseURI,
                                                                         identitydomainname,
                                                                         versionid).getAsJson(accountid,
                                                                                              datacenterid,
                                                                                              idmName,
                                                                                              idmDisplayName,
                                                                                              statuses,
                                                                                              excludedstatuses,
                                                                                              limit,
                                                                                              offset,
                                                                                              null,
                                                                                              null,
                                                                                              IdentityDomains.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllIdentityDomains[" + apiType + "] Accountid -> ( " +
             accountid + " ) DataCenterid -> ( " + datacenterid +
             " ) CustomerZoneid -> ( " + customerZoneid + " ) IdmName -> ( " +
             idmName + " ) IdmDisplayName -> ( " + idmDisplayName +
             " ) Statuses -> ( " + statuses + " ) Excludedstatuses -> ( " +
             excludedstatuses + " ) HasImpersonateeTypes -> ( " +
             hasImpersonateeTypes + " ) Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) AllIdentityDomains ->\n" +
        toXMLString(identityDomains));

    return identityDomains;
  }

  public IdentityDomain getIdentityDomain(String identitydomainid) {

    LOG_ENTRY("getIdentityDomain[" + apiType + "] IdentityDomainId -> ( " +
              identitydomainid + " )");

    IdentityDomain identityDomain = null;
    if (apiType.isTasCentral()) {
      identityDomain =
          TasCentral.identityDomainNameTasApiVersionIdIdentityDomainsIdentityDomainId(client,
                                                                                      baseURI,
                                                                                      identitydomainname,
                                                                                      versionid,
                                                                                      identitydomainid).getAsJson(IdentityDomain.class);
    } else if (apiType.isTasDataCenter()) {
      identityDomain =
          TasDataCenter.identityDomainNameTasApiVersionIdIdentityDomainsIdentityDomainId(client,
                                                                                         baseURI,
                                                                                         identitydomainname,
                                                                                         versionid,
                                                                                         identitydomainid).getAsJson(IdentityDomain.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getIdentityDomain[" + apiType + "] IdentityDomainId -> ( " +
             identitydomainid + " ) IdentityDomain ->\n" +
        toXMLString(identityDomain));

    return identityDomain;

  }

  public IdentityValidations createIdentityValidations(IdentityValidations identityvalidations) { //TODO check return value

    LOG_ENTRY("createIdentityValidations[" + apiType +
              "] IdentityValidations ->\n" +
        toXMLString(identityvalidations));

    identityvalidations =
        TasCentral.identityDomainNameTasApiVersionIdIdentityValidations(client,
                                                                        baseURI,
                                                                        identitydomainname,
                                                                        versionid).postJson(identityvalidations,
                                                                                            IdentityValidations.class);

    LOG_EXIT("createIdentityValidations[" + apiType +
             "] IdentityValidations ->\n" +
        toXMLString(identityvalidations));

    return identityvalidations;

  }


  public Notifications getAllNotifications() {

    LOG_ENTRY("getAllNotifications[" + apiType + "]");

    Notifications allNotifications = null;
    Notifications notifications = null;
    do {
      if (allNotifications == null) {
        if (apiType.isTasCentral()) {
          notifications =
              TasCentral.identityDomainNameTasApiVersionIdNotifications(client,
                                                                        baseURI,
                                                                        identitydomainname,
                                                                        versionid).getAsJson(Notifications.class);
        } else if (apiType.isTasDataCenter()) {
          notifications =
              TasDataCenter.identityDomainNameTasApiVersionIdNotifications(client,
                                                                           baseURI,
                                                                           identitydomainname,
                                                                           versionid).getAsJson(Notifications.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }

      } else {
        notifications =
            getAllNotifications(null, null, null, null, null, null, null, null,
                                null, null, null, null, null, null, null,
                                String.valueOf((notifications.getLimit() +
                                                notifications.getOffset())));
        notifications.getItems().addAll(0, allNotifications.getItems());
      }
      allNotifications = notifications;
    } while (notifications.isHasMore());

    LOG_EXIT("getAllNotifications[" + apiType + "] AllNotifications ->\n" +
        toXMLString(allNotifications));

    return allNotifications;

  }

  public Notifications getAllNotifications(String accountid,
                                           String subscriptionid,
                                           String creditentitlementserviceinstanceid,
                                           String creditserviceinstanceid,
                                           String servicetype,
                                           String serviceinstancename,
                                           String servicecategory,
                                           String textmatch,
                                           String incidentnumber,
                                           String communicationtypeid,
                                           String communicationtypeclassids,
                                           String rangestart, String rangeend,
                                           String source, String limit,
                                           String offset) {

    LOG_ENTRY("getAllNotifications[" + apiType + "] Accountid -> ( " +
              accountid + " ) Subscriptionid -> ( " + subscriptionid +
              " ) Creditentitlementserviceinstanceid -> ( " +
              creditentitlementserviceinstanceid +
              " ) Creditserviceinstanceid -> ( " + creditserviceinstanceid +
              " ) Servicetype -> ( " + servicetype +
              " ) Serviceinstancename -> ( " + serviceinstancename +
              " ) Servicecategory -> ( " + servicecategory +
              " ) Textmatch -> ( " + textmatch + " ) Incidentnumber -> ( " +
              incidentnumber + " ) Communicationtypeid -> ( " +
              communicationtypeid + " ) Rangestart -> ( " + rangestart +
              " ) Rangeend -> ( " + rangeend + " ) Source -> ( " + source +
              " ) Limit -> ( " + limit + " ) Offset -> ( " + offset + " )");

    Notifications notifications = null;
    if (apiType.isTasCentral()) {
      notifications =
          TasCentral.identityDomainNameTasApiVersionIdNotifications(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid).getAsJson(accountid,
                                                                                         subscriptionid,
                                                                                         null,
                                                                                         creditentitlementserviceinstanceid,
                                                                                         creditserviceinstanceid,
                                                                                         servicetype,
                                                                                         serviceinstancename,
                                                                                         servicecategory,
                                                                                         textmatch,
                                                                                         incidentnumber,
                                                                                         communicationtypeid,
                                                                                         communicationtypeclassids,
                                                                                         rangestart,
                                                                                         rangeend,
                                                                                         source,
                                                                                         limit,
                                                                                         offset,
                                                                                         null,
                                                                                         Notifications.class);
    } else if (apiType.isTasDataCenter()) {

      notifications =
          TasDataCenter.identityDomainNameTasApiVersionIdNotifications(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid).getAsJson(accountid,
                                                                                            subscriptionid,
                                                                                            servicetype,
                                                                                            serviceinstancename,
                                                                                            servicecategory,
                                                                                            textmatch,
                                                                                            incidentnumber,
                                                                                            communicationtypeid,
                                                                                            communicationtypeclassids,
                                                                                            rangestart,
                                                                                            rangeend,
                                                                                            limit,
                                                                                            offset,
                                                                                            null,
                                                                                            Notifications.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllNotifications[" + apiType + "] Accountid -> ( " +
             accountid + " ) Subscriptionid -> ( " + subscriptionid +
             " ) Creditentitlementserviceinstanceid -> ( " +
             creditentitlementserviceinstanceid +
             " ) Creditserviceinstanceid -> ( " + creditserviceinstanceid +
             " ) Servicetype -> ( " + servicetype +
             " ) Serviceinstancename -> ( " + serviceinstancename +
             " ) Servicecategory -> ( " + servicecategory +
             " ) Textmatch -> ( " + textmatch + " ) Incidentnumber -> ( " +
             incidentnumber + " ) Communicationtypeid -> ( " +
             communicationtypeid + " ) Rangestart -> ( " + rangestart +
             " ) Rangeend -> ( " + rangeend + " ) Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) AllNotifications ->\n" +
        toXMLString(notifications));

    return notifications;

  }

  public Notification getNotification(String notificationid) {

    LOG_ENTRY("getNotification[" + apiType + "] NotificationId -> ( " +
              notificationid + " )");

    Notification notification = null;
    if (apiType.isTasCentral()) {
      notification =
          TasCentral.identityDomainNameTasApiVersionIdNotificationsNotificationId(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid,
                                                                                  notificationid).getAsJson(Notification.class);
    } else if (apiType.isTasDataCenter()) {
      notification =
          TasDataCenter.identityDomainNameTasApiVersionIdNotificationsNotificationId(client,
                                                                                     baseURI,
                                                                                     identitydomainname,
                                                                                     versionid,
                                                                                     notificationid).getAsJson(Notification.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getNotification[" + apiType + "] NotificationId -> ( " +
             notificationid + " ) Notification ->\n" +
        toXMLString(notification));

    return notification;
  }

  public Orders getAllOrders() {

    LOG_ENTRY("getAllOrders[" + apiType + "]");

    Orders allOrders = null;
    Orders orders = null;
    do {
      if (allOrders == null) {
        if (apiType.isTasCentral()) {
          orders =
              TasCentral.identityDomainNameTasApiVersionIdOrders(client, baseURI,
                                                                 identitydomainname,
                                                                 versionid).getAsJson(Orders.class);
        } else if (apiType.isTasDataCenter()) {
          orders =
              TasDataCenter.identityDomainNameTasApiVersionIdOrders(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid).getAsJson(Orders.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }

      } else {
        orders =
            getAllOrders(null, null, null, null, null, null, String.valueOf((orders.getLimit() +
                                                                             orders.getOffset())));
        orders.getItems().addAll(0, allOrders.getItems());
      }
      allOrders = orders;
    } while (orders.isHasMore());

    LOG_ENTRY("getAllOrders[" + apiType + "] AllOrders ->\n" +
        toXMLString(allOrders));

    return allOrders;


  }

  public Orders getAllOrders(String accountid, String subscriptionid,
                             String subscriptiontype, String statuses,
                             String excludedstatuses, String limit,
                             String offset) {

    LOG_ENTRY("getAllOrders[" + apiType + "] Accountid -> ( " + accountid +
              " ) Subscriptionid -> ( " + subscriptionid +
              " ) Subscriptiontype -> ( " + subscriptiontype +
              " ) Statuses -> ( " + statuses + ") Excludedstatuses -> ( " +
              excludedstatuses + " ) Limit -> ( " + limit + " ) Offset -> ( " +
              offset + " )");

    Orders orders = null;
    if (apiType.isTasCentral()) {
      orders =
          TasCentral.identityDomainNameTasApiVersionIdOrders(client, baseURI,
                                                             identitydomainname,
                                                             versionid).getAsJson(null,
                                                                                  accountid,
                                                                                  subscriptionid,
                                                                                  subscriptiontype,
                                                                                  null,
                                                                                  null,
                                                                                  null,
                                                                                  null,
                                                                                  null,
                                                                                  null,
                                                                                  statuses,
                                                                                  excludedstatuses,
                                                                                  limit,
                                                                                  offset,
                                                                                  null,
                                                                                  Orders.class);
    } else if (apiType.isTasDataCenter()) {
      orders =
          TasDataCenter.identityDomainNameTasApiVersionIdOrders(client, baseURI,
                                                                identitydomainname,
                                                                versionid).getAsJson(null,
                                                                                     accountid,
                                                                                     subscriptionid,
                                                                                     subscriptiontype,
                                                                                     statuses,
                                                                                     excludedstatuses,
                                                                                     limit,
                                                                                     offset,
                                                                                     null,
                                                                                     Orders.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllOrders[" + apiType + "] Accountid -> ( " + accountid +
             " ) Subscriptionid -> ( " + subscriptionid +
             " ) Subscriptiontype -> ( " + subscriptiontype +
             " ) Statuses -> ( " + statuses + " ) Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) AllOrders ->\n" +
        toXMLString(orders));

    return orders;
  }

  public Order createOrder(Order order) {

    LOG_ENTRY("createOrder[" + apiType + "] Order ->\n" +
        toXMLString(order));

    order =
        TasCentral.identityDomainNameTasApiVersionIdOrders(client, baseURI, identitydomainname,
                                                           versionid).postJson(order,
                                                                               Order.class);


    //order = this.getOrder(order.getId());

    LOG_EXIT("createOrder[" + apiType + "] Order ->\n" +
        toXMLString(order));

    return order;
  }

  public Order getOrder(String orderid) {

    LOG_ENTRY("getOrder[" + apiType + "] orderid -> ( " + orderid + " ) ");

    Order order = null;
    if (apiType.isTasCentral()) {
      order =
          TasCentral.identityDomainNameTasApiVersionIdOrdersOrderId(client, baseURI,
                                                                    identitydomainname,
                                                                    versionid,
                                                                    orderid).getAsJson(Order.class);
    } else if (apiType.isTasDataCenter()) {
      order =
          TasDataCenter.identityDomainNameTasApiVersionIdOrdersOrderId(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid,
                                                                       orderid).getAsJson(Order.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getOrder[" + apiType + "] orderid -> ( " + orderid +
             " ) Order ->\n" +
        toXMLString(order));

    return order;

  }

  public boolean deleteOrder(String orderid) {

    LOG_ENTRY("deleteOrder[" + apiType + "] orderid -> ( " + orderid + " ) ");

    boolean success = true;
    ClientResponse response =
      TasCentral.identityDomainNameTasApiVersionIdOrdersOrderId(client,
                                                                baseURI,
                                                                identitydomainname,
                                                                versionid,
                                                                orderid).deleteAsJson(ClientResponse.class);

    ClientResponse.Status responseStatus =
      ClientResponse.Status.fromStatusCode(response.getStatus());
    Response.Status.Family statusFamily = responseStatus.getFamily();
    String errMsg = null;
    if (statusFamily != Response.Status.Family.SUCCESSFUL) {
      success = false;
      errMsg = response.getEntity(String.class);
    }

    LOG_EXIT("deleteOrder[" + apiType + "] orderid -> ( " + orderid +
             " ) Status ->\n" +
        response.getClientResponseStatus().toString() + " Msg : " + errMsg);

    verifyResponse(response);

    return success;

  }

  public Order activateOrder(Order order, String orderid) {

    LOG_ENTRY("activateOrder[" + apiType + "] orderid -> ( " + orderid +
              " )\nOrder ->\n" +
        toXMLString(order));

    ClientResponse response =
      TasCentral.identityDomainNameTasApiVersionIdOrdersOrderId(client,
                                                                baseURI,
                                                                identitydomainname,
                                                                versionid,
                                                                orderid).patchJson(order,
                                                                                   ClientResponse.class);


    ClientResponse.Status responseStatus =
      ClientResponse.Status.fromStatusCode(response.getStatus());
    Response.Status.Family statusFamily = responseStatus.getFamily();
    String errMsg = null;
    if (statusFamily != Response.Status.Family.SUCCESSFUL) {
      errMsg = response.getEntity(String.class);
    } else {
      order = this.getOrder(orderid);
    }

    LOG_EXIT("activateOrder[" + apiType + "] orderid -> ( " + orderid +
             " ) Status ->\n" +
        response.getClientResponseStatus().toString() + " Msg : " + errMsg +
        "\nOrder ->\n" +
        toXMLString(order));

    verifyResponse(response);

    return order;

  }


  public Services getAllSupportedServices() {

    LOG_ENTRY("getAllSupportedServices[" + apiType + "]");

    Services allServices = null;
    Services services = null;
    do {
      if (allServices == null) {
        if (apiType.isTasCentral()) {
          services =
              TasCentral.identityDomainNameTasApiVersionIdServices(client,
                                                                   baseURI,
                                                                   identitydomainname,
                                                                   versionid).getAsJson(Services.class);
        } else if (apiType.isTasDataCenter()) {
          services =
              TasDataCenter.identityDomainNameTasApiVersionIdServices(client,
                                                                      baseURI,
                                                                      identitydomainname,
                                                                      versionid).getAsJson(Services.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }
      } else {
        services =
            getAllSupportedServices(null, String.valueOf((services.getLimit() +
                                                          services.getOffset())),
                                    null);
        services.getItems().addAll(0, allServices.getItems());
      }
      allServices = services;
    } while (services.isHasMore());

    LOG_EXIT("getAllSupportedServices[" + apiType +
             "] AllSupportedServices ->\n" +
        toXMLString(allServices));

    return allServices;

  }

  public Services getAllSupportedServices(String limit, String offset,
                                          String name) {

    LOG_ENTRY("getAllSupportedServices[" + apiType + "] Limit -> ( " + limit +
              " ) Offset -> ( " + offset + " ) Name -> ( " + name + " )");

    Services services = null;
    if (apiType.isTasCentral()) {
      services =
          TasCentral.identityDomainNameTasApiVersionIdServices(client, baseURI,
                                                               identitydomainname,
                                                               versionid).getAsJson(limit,
                                                                                    offset,
                                                                                    null,
                                                                                    name,
                                                                                    null,
                                                                                    Services.class);
    } else if (apiType.isTasDataCenter()) {
      services =
          TasDataCenter.identityDomainNameTasApiVersionIdServices(client,
                                                                  baseURI,
                                                                  identitydomainname,
                                                                  versionid).getAsJson(limit,
                                                                                       offset,
                                                                                       null,
                                                                                       name,
                                                                                       null,
                                                                                       Services.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllSupportedServices[" + apiType + "] Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) Name -> ( " + name +
             " ) AllSupportedServices ->\n" +
        toXMLString(services));

    return services;


  }

  public Service getSupportedService(String serviceid) {

    LOG_ENTRY("getSupportedService[" + apiType + "] serviceid -> ( " +
              serviceid + " )");

    Service service = null;
    if (apiType.isTasCentral()) {
      service =
          TasCentral.identityDomainNameTasApiVersionIdServicesId(client, baseURI,
                                                                 identitydomainname,
                                                                 versionid,
                                                                 serviceid).getAsJson(Service.class);
    } else if (apiType.isTasDataCenter()) {
      service =
          TasDataCenter.identityDomainNameTasApiVersionIdServicesId(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid,
                                                                    serviceid).getAsJson(Service.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSupportedService[" + apiType + "] serviceid -> ( " +
             serviceid + " ) SupportedService ->\n" +
        toXMLString(service));

    return service;

  }

  public ServicePlans getAllServicePlans(String serviceid) {

    LOG_ENTRY("getAllServicePlans[" + apiType + "] serviceid -> ( " +
              serviceid + " )");

    ServicePlans servicePlans = null;
    if (apiType.isTasCentral()) {
      servicePlans =
          TasCentral.identityDomainNameTasApiVersionIdServicesServiceIdPlans(client,
                                                                             baseURI,
                                                                             identitydomainname,
                                                                             versionid,
                                                                             serviceid).getAsJson(ServicePlans.class);
    } else if (apiType.isTasDataCenter()) {
      servicePlans =
          TasDataCenter.identityDomainNameTasApiVersionIdServicesServiceIdPlans(client,
                                                                                baseURI,
                                                                                identitydomainname,
                                                                                versionid,
                                                                                serviceid).getAsJson(ServicePlans.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllServicePlans[" + apiType + "] serviceid -> ( " +
             serviceid + " ) AllServicePlans ->\n" +
        toXMLString(servicePlans));

    return servicePlans;

  }

  public ServicePlan getServicePlan(String serviceid, String planid) {

    LOG_ENTRY("getServicePlan[" + apiType + "] serviceid -> ( " + serviceid +
              " ) Planid -> ( " + planid + " )");

    ServicePlan servicePlan = null;
    if (apiType.isTasCentral()) {
      servicePlan =
          TasCentral.identityDomainNameTasApiVersionIdServicesServiceIdPlansId(client,
                                                                               baseURI,
                                                                               identitydomainname,
                                                                               versionid,
                                                                               serviceid,
                                                                               planid).getAsJson(ServicePlan.class);
    } else if (apiType.isTasDataCenter()) {
      servicePlan =
          TasDataCenter.identityDomainNameTasApiVersionIdServicesServiceIdPlansId(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid,
                                                                                  serviceid,
                                                                                  planid).getAsJson(ServicePlan.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getServicePlan[" + apiType + "] serviceid -> ( " + serviceid +
             " ) Planid -> ( " + planid + " ) ServicePlan ->\n" +
        toXMLString(servicePlan));

    return servicePlan;

  }

  public Subscriptions getAllSubscriptions() {

    LOG_ENTRY("getAllSubscriptions[" + apiType + "]");

    Subscriptions allSubscriptions = null;
    Subscriptions subscriptions = null;
    do {
      if (allSubscriptions == null) {
        if (apiType.isTasCentral()) {
          subscriptions =
              TasCentral.identityDomainNameTasApiVersionIdSubscriptions(client,
                                                                        baseURI,
                                                                        identitydomainname,
                                                                        versionid).getAsJson(Subscriptions.class);
        } else if (apiType.isTasDataCenter()) {
          subscriptions =
              TasDataCenter.identityDomainNameTasApiVersionIdSubscriptions(client,
                                                                           baseURI,
                                                                           identitydomainname,
                                                                           versionid).getAsJson(Subscriptions.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }
      } else {
        if (apiType.isTasCentral()) {
          subscriptions =
              getAllSubscriptions((null), null, null, null, null, null, null,
                                  null, null, null, null, null, null, null,
                                  null, null, null, null, null, null, null,
                                  String.valueOf((subscriptions.getLimit() +
                                                  subscriptions.getOffset())));

        } else {
          getAllSubscriptions(null, null, null, null, null, null, null, null,
                              null, null, null, null, null, null, null, null,
                              null, null, null, null, null,
                              String.valueOf((subscriptions.getLimit() +
                                              subscriptions.getOffset())));
        }

        subscriptions.getItems().addAll(0, allSubscriptions.getItems());
      }
      allSubscriptions = subscriptions;
      //TODO fix
      allSubscriptions.setHasMore(false);
    } while (subscriptions.isHasMore());

    LOG_EXIT("getAllSubscriptions[" + apiType + "] AllSubscriptions ->\n" +
        toXMLString(allSubscriptions));

    return allSubscriptions;

  }

  public Subscriptions getAllSubscriptions(String ids, String accountid,
                                           String associatedsubscriptionid,
                                           String relatedsubscriptionid,
                                           String identitydomainid,
                                           String identitydomainname,
                                           String servicetype,
                                           String subscriptiontype,
                                           String type,
                                           String subscriptionstatuses,
                                           String statuses,
                                           String excludedsubscriptionstatuses,
                                           String excludedstatuses,
                                           String excludepurgedterminatedbefore,
                                           String statesrangestart,
                                           String statesrangeend,
                                           String aggregateduptimesrangestart,
                                           String aggregateduptimesrangeend,
                                           String servicecategory,
                                           String serviceinstancetype,
                                           String limit, String offset) {


    LOG_ENTRY("getAllSubscriptions[" + apiType + "] Ids -> ( " + ids +
              " ) Accountid -> ( " + accountid +
              " ) Associatedsubscriptionid -> ( " + associatedsubscriptionid +
              " ) Relatedsubscriptionid -> ( " + relatedsubscriptionid +
              " ) Identitydomainid -> ( " + identitydomainid +
              " ) Identitydomainname -> ( " + identitydomainname +
              " ) Servicetype -> ( " + servicetype +
              " ) Subscriptiontype -> ( " + subscriptiontype +
              " ) Type -> ( " + type + " ) Subscriptionstatuses -> ( " +
              subscriptionstatuses + " ) Statuses -> ( " + statuses +
              " ) Excludedsubscriptionstatuses -> ( " +
              excludedsubscriptionstatuses + " ) Excludedstatuses -> ( " +
              excludedstatuses + " ) Excludepurgedterminatedbefore -> ( " +
              excludepurgedterminatedbefore + " ) Statesrangestart -> ( " +
              statesrangestart + " ) Statesrangeend -> ( " + statesrangeend +
              " ) Aggregateduptimesrangestart -> ( " +
              aggregateduptimesrangestart +
              " ) Aggregateduptimesrangeend -> ( " +
              aggregateduptimesrangeend + " ) Servicecategory -> ( " +
              servicecategory + " ) Serviceinstancetype -> ( " +
              serviceinstancetype + " Limit -> ( " + limit +
              " ) Offset -> ( " + offset + " )");

    Subscriptions allSubscriptions =


      TasDataCenter.identityDomainNameTasApiVersionIdSubscriptions(client,
                                                                   baseURI,
                                                                   identitydomainname,
                                                                   versionid).getAsJson(ids,
                                                                                        accountid,
                                                                                        associatedsubscriptionid,
                                                                                        relatedsubscriptionid,
                                                                                        identitydomainid,
                                                                                        identitydomainname,
                                                                                        servicetype,
                                                                                        subscriptiontype,
                                                                                        type,
                                                                                        subscriptionstatuses,
                                                                                        statuses,
                                                                                        excludedsubscriptionstatuses,
                                                                                        excludedstatuses,
                                                                                        excludepurgedterminatedbefore,
                                                                                        statesrangestart,
                                                                                        statesrangeend,
                                                                                        aggregateduptimesrangestart,
                                                                                        aggregateduptimesrangeend,
                                                                                        servicecategory,
                                                                                        serviceinstancetype,
                                                                                        limit,
                                                                                        offset,
                                                                                        null,
                                                                                        null,
                                                                                        Subscriptions.class);

    LOG_EXIT("getAllSubscriptions[" + apiType + "] Ids -> ( " + ids +
             " ) Accountid -> ( " + accountid +
             " ) Associatedsubscriptionid -> ( " + associatedsubscriptionid +
             " ) Relatedsubscriptionid -> ( " + relatedsubscriptionid +
             " ) Identitydomainid -> ( " + identitydomainid +
             " ) Identitydomainname -> ( " + identitydomainname +
             " ) Servicetype -> ( " + servicetype +
             " ) Subscriptiontype -> ( " + subscriptiontype + " ) Type -> ( " +
             type + " ) Subscriptionstatuses -> ( " + subscriptionstatuses +
             " ) Statuses -> ( " + statuses +
             " ) Excludedsubscriptionstatuses -> ( " +
             excludedsubscriptionstatuses + " ) Excludedstatuses -> ( " +
             excludedstatuses + " ) Excludepurgedterminatedbefore -> ( " +
             excludepurgedterminatedbefore + " ) Statesrangestart -> ( " +
             statesrangestart + " ) Statesrangeend -> ( " + statesrangeend +
             " ) Aggregateduptimesrangestart -> ( " +
             aggregateduptimesrangestart +
             " ) Aggregateduptimesrangeend -> ( " + aggregateduptimesrangeend +
             " ) Servicecategory -> ( " + servicecategory +
             " ) Serviceinstancetype -> ( " + serviceinstancetype +
             " Limit -> ( " + limit + " ) Offset -> ( " + offset + " )" +
             " ) AllSubscriptions ->\n" +
        toXMLString(allSubscriptions));

    return allSubscriptions;
  }

  public Subscriptions getAllSubscriptions(String ids, String accountid,
                                           String associatedsubscriptionid,
                                           String relatedsubscriptionid,
                                           String identitydomainid,
                                           String identitydomainname,
                                           String servicetype,
                                           String subscriptiontype,
                                           String type,
                                           String subscriptionstatuses,
                                           String statuses,
                                           String excludedsubscriptionstatuses,
                                           String excludedstatuses,
                                           String excludepurgedterminatedbefore,
                                           String statesrangestart,
                                           String statesrangeend,
                                           String aggregateduptimesrangestart,
                                           String aggregateduptimesrangeend,
                                           String servicecategory,
                                           String subscriptioncategory,
                                           String category,
                                           String serviceinstancetype,
                                           String limit, String offset) {

    LOG_ENTRY("getAllSubscriptions[" + apiType + "] Ids -> ( " + ids +
              " ) Accountid -> ( " + accountid +
              " ) Associatedsubscriptionid -> ( " + associatedsubscriptionid +
              " ) Relatedsubscriptionid -> ( " + relatedsubscriptionid +
              " ) Identitydomainid -> ( " + identitydomainid +
              " ) Identitydomainname -> ( " + identitydomainname +
              " ) Servicetype -> ( " + servicetype +
              " ) Subscriptiontype -> ( " + subscriptiontype +
              " ) Type -> ( " + type + " ) Subscriptionstatuses -> ( " +
              subscriptionstatuses + " ) Statuses -> ( " + statuses +
              " ) Excludedsubscriptionstatuses -> ( " +
              excludedsubscriptionstatuses + " ) ExcludedStatuses -> ( " +
              excludedstatuses + " ) Excludepurgedterminatedbefore -> ( " +
              excludepurgedterminatedbefore + " ) Statesrangestart -> ( " +
              statesrangestart + " ) Statesrangeend -> ( " + statesrangeend +
              " ) aggregateduptimesrangestart -> ( " +
              aggregateduptimesrangestart +
              " ) aggregateduptimesrangeend -> ( " +
              aggregateduptimesrangeend + " ) Servicecategory -> ( " +
              servicecategory + " ) Subscriptioncategory -> ( " +
              subscriptioncategory + " ) category -> ( " + category +
              " ) serviceinstancetype -> ( " + serviceinstancetype +
              " ) Limit -> ( " + limit + " ) Offset -> ( " + offset + " )");

    Subscriptions allSubscriptions =

      TasCentral.identityDomainNameTasApiVersionIdSubscriptions(client,
                                                                baseURI,
                                                                identitydomainname,
                                                                versionid).getAsJson(ids,
                                                                                     accountid,
                                                                                     associatedsubscriptionid,
                                                                                     relatedsubscriptionid,
                                                                                     identitydomainid,
                                                                                     identitydomainname,
                                                                                     servicetype,
                                                                                     subscriptiontype,
                                                                                     type,
                                                                                     subscriptionstatuses,
                                                                                     statuses,
                                                                                     excludedsubscriptionstatuses,
                                                                                     excludedstatuses,
                                                                                     excludepurgedterminatedbefore,
                                                                                     statesrangestart,
                                                                                     statesrangeend,
                                                                                     aggregateduptimesrangestart,
                                                                                     aggregateduptimesrangeend,
                                                                                     servicecategory,
                                                                                     subscriptioncategory,
                                                                                     category,
                                                                                     serviceinstancetype,
                                                                                     limit,
                                                                                     offset,
                                                                                     null,
                                                                                     null,
                                                                                     Subscriptions.class);


    LOG_EXIT("getAllSubscriptions[" + apiType + "] Ids -> ( " + ids +
             " ) Accountid -> ( " + accountid +
             " ) Associatedsubscriptionid -> ( " + associatedsubscriptionid +
             " ) Relatedsubscriptionid -> ( " + relatedsubscriptionid +
             " ) Identitydomainid -> ( " + identitydomainid +
             " ) Identitydomainname -> ( " + identitydomainname +
             " ) Servicetype -> ( " + servicetype +
             " ) Subscriptiontype -> ( " + subscriptiontype + " ) Type -> ( " +
             type + " ) Subscriptionstatuses -> ( " + subscriptionstatuses +
             " ) Statuses -> ( " + statuses +
             " ) Excludedsubscriptionstatuses -> ( " +
             excludedsubscriptionstatuses + " ) ExcludedStatuses -> ( " +
             excludedstatuses + " ) Excludepurgedterminatedbefore -> ( " +
             excludepurgedterminatedbefore + " ) Statesrangestart -> ( " +
             statesrangestart + " ) Statesrangeend -> ( " + statesrangeend +
             " ) aggregateduptimesrangestart -> ( " +
             aggregateduptimesrangestart +
             " ) aggregateduptimesrangeend -> ( " + aggregateduptimesrangeend +
             " ) Servicecategory -> ( " + servicecategory +
             " ) Subscriptioncategory -> ( " + subscriptioncategory +
             " ) category -> ( " + category + " ) serviceinstancetype -> ( " +
             serviceinstancetype + " ) Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) AllSubscriptions ->\n" +
        toXMLString(allSubscriptions));

    return allSubscriptions;
  }

  public Subscription getSubscription(String subscriptionid) {

    LOG_ENTRY("getSubscription[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " )");

    Subscription subscription = null;
    if (apiType.isTasCentral()) {
      subscription =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionId(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid,
                                                                                  subscriptionid).getAsJson(Subscription.class);
    } else if (apiType.isTasDataCenter()) {
      subscription =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionId(client,
                                                                                     baseURI,
                                                                                     identitydomainname,
                                                                                     versionid,
                                                                                     subscriptionid).getAsJson(Subscription.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSubscription[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) Subscription ->\n" +
        toXMLString(subscription));

    return subscription;
  }

  public Subscription getSubscription(String subscriptionid,
                                      String statesrangestart,
                                      String statesrangeend,
                                      String aggregateduptimesrangestart,
                                      String aggregateduptimesrangeend) {

    LOG_ENTRY("getSubscription[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " ) Statesrangestart -> ( " + statesrangestart +
              " ) Statesrangeend -> ( " + statesrangeend +
              " ) aggregateduptimesrangestart -> ( " +
              aggregateduptimesrangestart +
              " ) aggregateduptimesrangeend -> ( " +
              aggregateduptimesrangeend + " )");

    Subscription subscription = null;
    if (apiType.isTasCentral()) {

      subscription =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionId(client,
                                                                                  baseURI,
                                                                                  identitydomainname,
                                                                                  versionid,
                                                                                  subscriptionid).getAsJson(statesrangestart,
                                                                                                            statesrangeend,
                                                                                                            aggregateduptimesrangestart,
                                                                                                            aggregateduptimesrangeend,
                                                                                                            null,
                                                                                                            Subscription.class);

    } else if (apiType.isTasDataCenter()) {

      subscription =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionId(client,
                                                                                     baseURI,
                                                                                     identitydomainname,
                                                                                     versionid,
                                                                                     subscriptionid).getAsJson(statesrangestart,
                                                                                                               statesrangeend,
                                                                                                               aggregateduptimesrangestart,
                                                                                                               aggregateduptimesrangeend,
                                                                                                               null,
                                                                                                               Subscription.class);
      ;
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getSubscription[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) Statesrangestart -> ( " + statesrangestart +
             " ) Statesrangeend -> ( " + statesrangeend +
             " ) aggregateduptimesrangestart -> ( " +
             aggregateduptimesrangestart +
             " ) aggregateduptimesrangeend -> ( " + aggregateduptimesrangeend +
             " ) Subscription ->\n" +
        toXMLString(subscription));

    return subscription;

  }

  public ServiceInstanceStates getAllServiceInstanceStates(String subscriptionid) {

    LOG_ENTRY("getAllServiceInstanceStates[" + apiType +
              "] Subscriptionid -> ( " + subscriptionid + " )");

    ServiceInstanceStates states = null;
    if (apiType.isTasCentral()) {
      states =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdStates(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        subscriptionid).getAsJson(ServiceInstanceStates.class);
    } else if (apiType.isTasDataCenter()) {
      states =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdStates(client,
                                                                                           baseURI,
                                                                                           identitydomainname,
                                                                                           versionid,
                                                                                           subscriptionid).getAsJson(ServiceInstanceStates.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllServiceInstanceStates[" + apiType +
             "] Subscriptionid -> ( " + subscriptionid +
             " ) AllServiceInstanceStates ->\n" +
        toXMLString(states));

    return states;

  }

  public ServiceInstanceStates getAllServiceInstanceStates(String subscriptionid,
                                                           String since,
                                                           String until,
                                                           String componentid,
                                                           String componenttype) {

    LOG_ENTRY("getAllServiceInstanceStates[" + apiType +
              "] Subscriptionid -> ( " + subscriptionid + " ) Since -> ( " +
              since + " ) Until -> ( " + until + " )");

    ServiceInstanceStates states = null;
    if (apiType.isTasCentral()) {
      states =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdStates(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        subscriptionid).getAsJson(since,
                                                                                                                  until,
                                                                                                                  componentid,
                                                                                                                  componenttype,
                                                                                                                  null,
                                                                                                                  ServiceInstanceStates.class);

    } else if (apiType.isTasDataCenter()) {
      states =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdStates(client,
                                                                                           baseURI,
                                                                                           identitydomainname,
                                                                                           versionid,
                                                                                           subscriptionid).getAsJson(since,
                                                                                                                     until,
                                                                                                                     componentid,
                                                                                                                     componenttype,
                                                                                                                     null,
                                                                                                                     ServiceInstanceStates.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }


    LOG_EXIT("getAllServiceInstanceStates[" + apiType +
             "] Subscriptionid -> ( " + subscriptionid + " ) Since -> ( " +
             since + " ) Until -> ( " + until +
             " ) ServiceInstanceStates ->\n" +
        toXMLString(states));

    return states;

  }

  public UpTimes getAllUpTimes(String subscriptionid) {

    LOG_ENTRY("getAllUpTimes[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " )");

    UpTimes upTimes = null;
    if (apiType.isTasCentral()) {
      upTimes =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUpTimes(client,
                                                                                         baseURI,
                                                                                         identitydomainname,
                                                                                         versionid,
                                                                                         subscriptionid).getAsJson(UpTimes.class);
    } else if (apiType.isTasDataCenter()) {
      upTimes =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUpTimes(client,
                                                                                            baseURI,
                                                                                            identitydomainname,
                                                                                            versionid,
                                                                                            subscriptionid).getAsJson(UpTimes.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllUpTimes[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) AllUpTimes ->\n" +
        toXMLString(upTimes));

    return upTimes;

  }

  public UpTimes getAllUpTimes(String subscriptionid, String componentid,
                               String componenttype, String rangestart,
                               String rangeend, String aggregatedrangestart,
                               String aggregatedrangeend,
                               String aggregationinterval) {

    LOG_ENTRY("getAllUpTimes[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " ) Componentid-> ( " + componentid +
              " ) Componenttype -> ( " + componenttype +
              " ) Rangestart -> ( " + rangestart + " ) Rangeend -> ( " +
              rangeend + " )");

    UpTimes upTimes = null;
    if (apiType.isTasCentral()) {
      upTimes =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUpTimes(client,
                                                                                         baseURI,
                                                                                         identitydomainname,
                                                                                         versionid,
                                                                                         subscriptionid).getAsJson(componentid,
                                                                                                                   componenttype,
                                                                                                                   rangestart,
                                                                                                                   rangeend,
                                                                                                                   aggregatedrangestart,
                                                                                                                   aggregatedrangeend,
                                                                                                                   aggregationinterval,
                                                                                                                   null,
                                                                                                                   UpTimes.class);

    } else if (apiType.isTasDataCenter()) {
      upTimes =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUpTimes(client,
                                                                                            baseURI,
                                                                                            identitydomainname,
                                                                                            versionid,
                                                                                            subscriptionid).getAsJson(componentid,
                                                                                                                      componenttype,
                                                                                                                      rangestart,
                                                                                                                      rangeend,
                                                                                                                      aggregatedrangestart,
                                                                                                                      aggregatedrangeend,
                                                                                                                      aggregationinterval,
                                                                                                                      null,
                                                                                                                      UpTimes.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }


    LOG_EXIT("getAllUpTimes[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) Componentid-> ( " + componentid +
             " ) Componenttype -> ( " + componenttype + " ) Rangestart -> ( " +
             rangestart + " ) Rangeend -> ( " + rangeend +
             " ) AllUpTimes ->\n" +
        toXMLString(upTimes));

    return upTimes;
  }

  public Usages getAllUsages(String subscriptionid) {

    LOG_ENTRY("getAllUsages[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " )");

    Usages usages = null;
    if (apiType.isTasCentral()) {
      usages =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUsages(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        subscriptionid).getAsJson(Usages.class);
    } else if (apiType.isTasDataCenter()) {
      usages =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUsages(client,
                                                                                           baseURI,
                                                                                           identitydomainname,
                                                                                           versionid,
                                                                                           subscriptionid).getAsJson(Usages.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllUsages[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) AllUsages ->\n" +
        toXMLString(usages));

    return usages;
  }

  public Usages getAllUsages(String subscriptionid, String rangestart,
                             String rangeend, String specificinfocontents,
                             String specificinfos) {

    LOG_ENTRY("getAllUsages[" + apiType + "] Subscriptionid -> ( " +
              subscriptionid + " ) Rangestart -> ( " + rangestart +
              " ) Rangeend -> ( " + rangeend +
              " ) Specificinfocontents -> ( " + specificinfocontents +
              " ) Specificinfos -> ( " + specificinfos + " )");

    Usages usages = null;
    if (apiType.isTasCentral()) {
      usages =
          TasCentral.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUsages(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        subscriptionid).getAsJson(rangestart,
                                                                                                                  rangeend,
                                                                                                                  specificinfocontents,
                                                                                                                  specificinfos,
                                                                                                                  null,
                                                                                                                  Usages.class);
    } else if (apiType.isTasDataCenter()) {
      usages =
          TasDataCenter.identityDomainNameTasApiVersionIdSubscriptionsSubscriptionIdUsages(client,
                                                                                           baseURI,
                                                                                           identitydomainname,
                                                                                           versionid,
                                                                                           subscriptionid).getAsJson(rangestart,
                                                                                                                     rangeend,
                                                                                                                     specificinfocontents,
                                                                                                                     specificinfos,
                                                                                                                     null,
                                                                                                                     Usages.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllUsages[" + apiType + "] Subscriptionid -> ( " +
             subscriptionid + " ) Rangestart -> ( " + rangestart +
             " ) Rangeend -> ( " + rangeend + " ) Specificinfocontents -> ( " +
             specificinfocontents + " ) Specificinfos -> ( " + specificinfos +
             " ) AllUsages ->\n" +
        toXMLString(usages));

    return usages;

  }

  public TimeZones getAllTimeZones() {

    LOG_ENTRY("getAllTimeZones[" + apiType + "]");

    TimeZones allTimeZones = null;
    TimeZones timeZones = null;
    do {
      if (allTimeZones == null) {
        if (apiType.isTasCentral()) {
          timeZones =
              TasCentral.identityDomainNameTasApiVersionIdTimeZones(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid).getAsJson(TimeZones.class);
        } else if (apiType.isTasDataCenter()) {
          timeZones =
              TasDataCenter.identityDomainNameTasApiVersionIdTimeZones(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid).getAsJson(TimeZones.class);
        } else {
          throw new UnsupportedOperationException("API Type : " + apiType +
                                                  " Does Not Support Operation");
        }
      } else {
        timeZones =
            getAllTimeZones(null, null, null, null, null, String.valueOf((timeZones.getLimit() +
                                                                          timeZones.getOffset())));
        timeZones.getItems().addAll(0, allTimeZones.getItems());
      }
      allTimeZones = timeZones;
    } while (timeZones.isHasMore());

    LOG_EXIT("getAllTimeZones[" + apiType + "] AllTimeZones ->\n" +
        toXMLString(allTimeZones));

    return allTimeZones;
  }

  public TimeZones getAllTimeZones(String common, String tzid,
                                   String countryid, String includedeprecated,
                                   String limit, String offset) {

    LOG_ENTRY("getAllTimeZones[" + apiType + "] Common -> ( " + common +
              " ) Tzid -> ( " + tzid + " ) Countryid -> ( " + countryid +
              " ) Includedeprecated -> ( " + includedeprecated +
              " ) Limit -> ( " + limit + " ) Offset -> ( " + offset + " ) ");

    TimeZones allTimeZones = null;
    if (apiType.isTasCentral()) {
      allTimeZones =
          TasCentral.identityDomainNameTasApiVersionIdTimeZones(client,
                                                                baseURI,
                                                                identitydomainname,
                                                                versionid).getAsJson(common,
                                                                                     tzid,
                                                                                     countryid,
                                                                                     includedeprecated,
                                                                                     limit,
                                                                                     offset,
                                                                                     TimeZones.class);
    } else if (apiType.isTasDataCenter()) {
      allTimeZones =
          TasDataCenter.identityDomainNameTasApiVersionIdTimeZones(client,
                                                                   baseURI,
                                                                   identitydomainname,
                                                                   versionid).getAsJson(common,
                                                                                        tzid,
                                                                                        countryid,
                                                                                        includedeprecated,
                                                                                        limit,
                                                                                        offset,
                                                                                        TimeZones.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getAllTimeZones[" + apiType + "] Common -> ( " + common +
             " ) Tzid -> ( " + tzid + " ) Countryid -> ( " + countryid +
             " ) Includedeprecated -> ( " + includedeprecated +
             " ) Limit -> ( " + limit + " ) Offset -> ( " + offset +
             " ) AllTimeZones ->\n" +
        toXMLString(allTimeZones));

    return allTimeZones;

  }

  public TimeZone getTimeZone(String base64urlid) {

    LOG_ENTRY("getTimeZone[" + apiType + "] Base64urlid -> ( " + base64urlid +
              " ) ");

    TimeZone timeZone = null;
    if (apiType.isTasCentral()) {
      timeZone =
          TasCentral.identityDomainNameTasApiVersionIdTimeZonesBase64UrlId(client,
                                                                           baseURI,
                                                                           identitydomainname,
                                                                           versionid,
                                                                           base64urlid).getAsJson(TimeZone.class);
    } else if (apiType.isTasDataCenter()) {
      timeZone =
          TasDataCenter.identityDomainNameTasApiVersionIdTimeZonesBase64UrlId(client,
                                                                              baseURI,
                                                                              identitydomainname,
                                                                              versionid,
                                                                              base64urlid).getAsJson(TimeZone.class);
    } else {
      throw new UnsupportedOperationException("API Type : " + apiType +
                                              " Does Not Support Operation");
    }

    LOG_EXIT("getTimeZone[" + apiType + "] Base64urlid -> ( " + base64urlid +
             " ) TimeZone ->\n" +
        toXMLString(timeZone));

    return timeZone;

  }

  public ServiceRegistrations getAllServiceRegistrations() {

    LOG_ENTRY("getAllServiceRegistrations[" + apiType + "]");

    ServiceRegistrations allServiceRegistrations = null;
    ServiceRegistrations serviceRegistrations = null;
    do {
      if (allServiceRegistrations == null) {
        serviceRegistrations =
            TasDataCenter.identityDomainNameTasApiVersionIdServiceRegistrations(client,
                                                                                baseURI,
                                                                                identitydomainname,
                                                                                versionid).getAsJson(ServiceRegistrations.class);
      } else {
        serviceRegistrations =
            getAllServiceRegistrations(null, null, null, null, null, null,
                                       String.valueOf((serviceRegistrations.getLimit() +
                                                       serviceRegistrations.getOffset())));
        serviceRegistrations.getItems().addAll(0,
                                               allServiceRegistrations.getItems());
      }
      allServiceRegistrations = serviceRegistrations;
    } while (serviceRegistrations.isHasMore());

    LOG_EXIT("getAllServiceRegistrations[" + apiType +
             "] AllServiceRegistrations ->\n" +
        toXMLString(allServiceRegistrations));

    return allServiceRegistrations;

  }

  public ServiceRegistrations getAllServiceRegistrations(String associatedserviceregistrationid,
                                                         String servicetype,
                                                         String serviceinstancename,
                                                         String javaadminrole,
                                                         String includeassociatedserviceregistrations,
                                                         String limit,
                                                         String offset) {


    LOG_ENTRY("getAllServiceRegistrations[" + apiType +
              "] Associatedserviceregistrationid -> ( " +
              associatedserviceregistrationid + " ) Servicetype -> ( " +
              servicetype + " ) Serviceinstancename -> ( " +
              serviceinstancename + " ) Javaadminrole -> ( " + javaadminrole +
              " ) Includeassociatedserviceregistrations -> ( " +
              includeassociatedserviceregistrations + " ) Limit -> ( " +
              limit + " ) Offset -> ( " + offset + " )");

    ServiceRegistrations allServiceRegistrations =
      TasDataCenter.identityDomainNameTasApiVersionIdServiceRegistrations(client,
                                                                          baseURI,
                                                                          identitydomainname,
                                                                          versionid).getAsJson(associatedserviceregistrationid,
                                                                                               servicetype,
                                                                                               serviceinstancename,
                                                                                               javaadminrole,
                                                                                               includeassociatedserviceregistrations,
                                                                                               limit,
                                                                                               offset,
                                                                                               ServiceRegistrations.class);
    LOG_EXIT("getAllServiceRegistrations[" + apiType + "] Servicetype -> ( " +
             servicetype + " ) Serviceinstancename -> ( " +
             serviceinstancename + " ) Limit -> ( " + limit +
             " ) Offset -> ( " + offset + " ) AllServiceRegistrations ->\n" +
        toXMLString(allServiceRegistrations));

    return allServiceRegistrations;

  }

  public ServiceRegistration getServiceRegistration(String svcregistrationid) {

    LOG_ENTRY("getServiceRegistration[" + apiType +
              "] Svcregistrationid -> ( " + svcregistrationid + " )");

    ServiceRegistration serviceRegistration =
      TasDataCenter.identityDomainNameTasApiVersionIdServiceRegistrationsId(client,
                                                                            baseURI,
                                                                            identitydomainname,
                                                                            versionid,
                                                                            svcregistrationid).getAsJson(ServiceRegistration.class);

    LOG_EXIT("getServiceRegistration[" + apiType +
             "] Svcregistrationid -> ( " + svcregistrationid +
             " ) ServiceRegistration ->\n" +
        toXMLString(serviceRegistration));

    return serviceRegistration;

  }


  //___________________________________________
  // Start 14.1.6 API Additions to TasCentral.
  //___________________________________________

  public AppCredentials getAppCredentials() {

    LOG_ENTRY("getAppCredentials[" + apiType + "]");

    AppCredentials appCredentials =
      TasCentral.identityDomainNameTasApiVersionIdAppCredentials(client,
                                                                 baseURI,
                                                                 identitydomainname,
                                                                 versionid).getAsJson(AppCredentials.class);
    LOG_EXIT("getAppCredentials[" + apiType + "] AppCredentials ->\n" +
        toXMLString(appCredentials));

    return appCredentials;
  }

  public AppCredential getAppCredential(String appcredentialid) {

    LOG_ENTRY("getAppCredential[" + apiType + "] Appcredentialid -> ( " +
              appcredentialid + " )");

    AppCredential appCredential =
      TasCentral.identityDomainNameTasApiVersionIdAppCredentialsAppCredentialId(client,
                                                                                baseURI,
                                                                                identitydomainname,
                                                                                versionid,
                                                                                appcredentialid).getAsJson(AppCredential.class);


    LOG_EXIT("getAppCredential[" + apiType + "] Appcredentialid -> ( " +
             appcredentialid + " ) AppCredential ->\n" +
        toXMLString(appCredential));

    return appCredential;
  }

  public ConsoleRegistrations getConsoleRegistrations() {

    LOG_ENTRY("getConsoleRegistrations[" + apiType + "]");

    ConsoleRegistrations consoleRegistrations =
      TasCentral.identityDomainNameTasApiVersionIdConsoleRegistrations(client,
                                                                       baseURI,
                                                                       identitydomainname,
                                                                       versionid).getAsJson(ConsoleRegistrations.class);


    LOG_EXIT("getConsoleRegistrations[" + apiType +
             "] ConsoleRegistrations ->\n" +
        toXMLString(consoleRegistrations));

    return consoleRegistrations;
  }

  public ConsoleRegistration getConsoleRegistration(String registrationId) {

    LOG_ENTRY("getConsoleRegistration[" + apiType + "] RegistrationId -> ( " +
              registrationId + " )");

    ConsoleRegistration consoleRegistration =
      TasCentral.identityDomainNameTasApiVersionIdConsoleRegistrationsId(client,
                                                                         baseURI,
                                                                         identitydomainname,
                                                                         versionid,
                                                                         registrationId).getAsJson(ConsoleRegistration.class);


    LOG_EXIT("getConsoleRegistration[" + apiType + "] RegistrationId -> ( " +
             registrationId + " ) ConsoleRegistration ->\n" +
        toXMLString(consoleRegistration));

    return consoleRegistration;
  }

  public CouponDistributor createCouponDistributor(CouponDistributor couponDistributor) {

    LOG_ENTRY("createCouponDistributor[" + apiType +
              "] CouponDistributor -> ( " + toXMLString(couponDistributor));

    couponDistributor =
        TasCentral.identityDomainNameTasApiVersionIdCouponDistributor(client,
                                                                      baseURI,
                                                                      identitydomainname,
                                                                      versionid).postJson(couponDistributor,
                                                                                          CouponDistributor.class);


    LOG_EXIT("createCouponDistributor[" + apiType +
             "] CouponDistributor -> ( " + toXMLString(couponDistributor));

    return couponDistributor;

  }

  public Coupons getCoupons() {

    LOG_ENTRY("getCoupons[" + apiType + "]");

    Coupons coupons =
      TasCentral.identityDomainNameTasApiVersionIdCoupons(client, baseURI,
                                                          identitydomainname,
                                                          versionid).getAsJson(Coupons.class);


    LOG_EXIT("getCoupons[" + apiType + "] Coupons ->\n" +
        toXMLString(coupons));

    return coupons;
  }

  public Coupon getCoupon(String couponCode) {

    LOG_ENTRY("getCoupon[" + apiType + "] CouponCode -> ( " + couponCode +
              " )");

    Coupon coupon =
      TasCentral.identityDomainNameTasApiVersionIdCouponsCouponCode(client,
                                                                    baseURI,
                                                                    identitydomainname,
                                                                    versionid,
                                                                    couponCode).getAsJson(Coupon.class);


    LOG_EXIT("getCoupon[" + apiType + "] CouponCode -> ( " + couponCode +
             " ) Coupon ->\n" +
        toXMLString(coupon));

    return coupon;
  }

  public Redemptions getCouponRedemptions(String couponCode) {

    LOG_ENTRY("getCouponRedemptions[" + apiType + "] CouponCode -> ( " +
              couponCode + " )");

    Redemptions redemptions =
      TasCentral.identityDomainNameTasApiVersionIdCouponsCouponCodeRedemptions(client,
                                                                               baseURI,
                                                                               identitydomainname,
                                                                               versionid,
                                                                               couponCode).getAsJson(Redemptions.class);


    LOG_EXIT("getCouponRedemptions[" + apiType + "] CouponCode -> ( " +
             couponCode + " ) Redemptions ->\n" +
        toXMLString(redemptions));

    return redemptions;
  }

  public Redemption getCouponRedemption(String couponCode,
                                        String redemptionId) {

    LOG_ENTRY("getCouponRedemption[" + apiType + "] CouponCode -> ( " +
              couponCode + " ) RedemptionId -> ( " + redemptionId + " )");

    Redemption redemption =
      TasCentral.identityDomainNameTasApiVersionIdCouponsCouponCodeRedemptionsId(client,
                                                                                 baseURI,
                                                                                 identitydomainname,
                                                                                 versionid,
                                                                                 couponCode,
                                                                                 redemptionId).getAsJson(Redemption.class);


    LOG_EXIT("getCouponRedemption[" + apiType + "] CouponCode -> ( " +
             couponCode + " ) RedemptionId -> ( " + redemptionId +
             " ) Redemption ->\n" +
        toXMLString(redemption));

    return redemption;
  }

  public CreditAccounts getCreditAccounts() {

    LOG_ENTRY("getCreditAccounts[" + apiType + "]");

    CreditAccounts creditAccounts =
      TasCentral.identityDomainNameTasApiVersionIdCreditAccounts(client,
                                                                 baseURI,
                                                                 identitydomainname,
                                                                 versionid).getAsJson(CreditAccounts.class);


    LOG_EXIT("getCreditAccounts[" + apiType + "] CreditAccounts ->\n" +
        toXMLString(creditAccounts));

    return creditAccounts;
  }

  public CreditAccount getCreditAccount(String creditaccountid) {

    LOG_ENTRY("getCreditAccount[" + apiType + "] Creditaccountid -> ( " +
              creditaccountid + " )");

    CreditAccount creditAccount =
      TasCentral.identityDomainNameTasApiVersionIdCreditAccountsCreditAccountId(client,
                                                                                baseURI,
                                                                                identitydomainname,
                                                                                versionid,
                                                                                creditaccountid).getAsJson(CreditAccount.class);


    LOG_EXIT("getCreditAccount[" + apiType + "] Creditaccountid -> ( " +
             creditaccountid + " ) CreditAccount ->\n" +
        toXMLString(creditAccount));

    return creditAccount;
  }

  public CreditEntitlementServiceInstances getCreditEntitlementServiceInstances(String creditEntitlementId) {

    LOG_ENTRY("getCreditEntitlementServiceInstances[" + apiType +
              "] creditEntitlementId[" + creditEntitlementId + "]");

    CreditEntitlementServiceInstances creditEntitlementServiceInstances =
      TasCentral.identityDomainNameTasApiVersionIdCreditEntitlementServiceInstances(client,
                                                                                    baseURI,
                                                                                    identitydomainname,
                                                                                    versionid).getAsJson(null,
                                                                                                         null,
                                                                                                         creditEntitlementId,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         null,
                                                                                                         CreditEntitlementServiceInstances.class);

    LOG_EXIT("getCreditEntitlementServiceInstances[" + apiType +
             "] CreditEntitlementServiceInstances ->\n" +
        toXMLString(creditEntitlementServiceInstances));

    return creditEntitlementServiceInstances;
  }

  public CreditEntitlementServiceInstance getCreditEntitlementServiceInstance(String creditentitlementserviceinstanceid) {

    LOG_ENTRY("getCreditEntitlementServiceInstance[" + apiType +
              "] Creditentitlementserviceinstanceid -> ( " +
              creditentitlementserviceinstanceid + " )");

    CreditEntitlementServiceInstance creditEntitlementServiceInstance =
      TasCentral.identityDomainNameTasApiVersionIdCreditEntitlementServiceInstancesCreditEntitlementServiceInstanceId(client,
                                                                                                                      baseURI,
                                                                                                                      identitydomainname,
                                                                                                                      versionid,
                                                                                                                      creditentitlementserviceinstanceid).getAsJson(CreditEntitlementServiceInstance.class);


    LOG_EXIT("getCreditEntitlementServiceInstance[" + apiType +
             "] Creditentitlementserviceinstanceid -> ( " +
             creditentitlementserviceinstanceid +
             " ) CreditEntitlementServiceInstance ->\n" +
        toXMLString(creditEntitlementServiceInstance));

    return creditEntitlementServiceInstance;
  }

  public CreditEntitlements getCreditEntitlements() {

    LOG_ENTRY("getCreditEntitlements[" + apiType + "]");

    CreditEntitlements creditEntitlements =
      TasCentral.identityDomainNameTasApiVersionIdCreditEntitlements(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).getAsJson(CreditEntitlements.class);


    LOG_EXIT("getCreditEntitlements[" + apiType + "] CreditEntitlements ->\n" +
        toXMLString(creditEntitlements));

    return creditEntitlements;
  }

  public CreditEntitlement getCreditEntitlement(String creditEntitlementId) {

    LOG_ENTRY("getCreditEntitlement[" + apiType +
              "] CreditEntitlementId -> ( " + creditEntitlementId + " )");

    CreditEntitlement creditEntitlement =
      TasCentral.identityDomainNameTasApiVersionIdCreditEntitlementsCreditEntitlementId(client,
                                                                                        baseURI,
                                                                                        identitydomainname,
                                                                                        versionid,
                                                                                        creditEntitlementId).getAsJson(CreditEntitlement.class);


    LOG_EXIT("getCreditEntitlement[" + apiType +
             "] CreditEntitlementId -> ( " + creditEntitlementId +
             " ) CreditEntitlement ->\n" +
        toXMLString(creditEntitlement));

    return creditEntitlement;
  }


  public Messages getMessages() {

    LOG_ENTRY("getMessages[" + apiType + "]");

    Messages messages =
      TasCentral.identityDomainNameTasApiVersionIdMessages(client, baseURI,
                                                           identitydomainname,
                                                           versionid).getAsJson(Messages.class);


    LOG_EXIT("getMessages[" + apiType + "] Messages ->\n" +
        toXMLString(messages));

    return messages;
  }

  public MetricStates getMetricStates() {

    LOG_ENTRY("getMetricStates[" + apiType + "]");

    MetricStates metricStates =
      TasCentral.identityDomainNameTasApiVersionIdStates(client, baseURI,
                                                         identitydomainname,
                                                         versionid).getAsJson(MetricStates.class);


    LOG_EXIT("getMetricStates[" + apiType + "] MetricStates ->\n" +
        toXMLString(metricStates));

    return metricStates;
  }

  public TrialValidations createTrialValidations(TrialValidations trialValidations) {

    LOG_ENTRY("createTrialValidations[" + apiType +
              "] TrialValidations -> ( " + toXMLString(trialValidations));

    trialValidations =
        TasCentral.identityDomainNameTasApiVersionIdTrialValidations(client,
                                                                     baseURI,
                                                                     identitydomainname,
                                                                     versionid).postJson(trialValidations,
                                                                                         TrialValidations.class);


    LOG_EXIT("createTrialValidations[" + apiType + "] TrialValidations ->\n" +
        toXMLString(trialValidations));

    return trialValidations;
  }

  public UpTimes getUpTimeMetrics() {

    LOG_ENTRY("getUpTimeMetrics[" + apiType + "]");

    UpTimes upTimeMetrics =
      TasCentral.identityDomainNameTasApiVersionIdUpTimes(client, baseURI,
                                                          identitydomainname,
                                                          versionid).getAsJson(UpTimes.class);


    LOG_EXIT("getUpTimeMetrics[" + apiType + "] UpTimeMetrics ->\n" +
        toXMLString(upTimeMetrics));

    return upTimeMetrics;
  }

  public Usages getUsageMetrics(String creditaccountid,
                                String creditentitlementserviceinstanceids) {

    LOG_ENTRY("getUsageMetrics[" + apiType + "] Creditaccountid -> ( " +
              creditaccountid +
              " ) Creditentitlementserviceinstanceids -> ( " +
              creditentitlementserviceinstanceids + " )");

    Usages usageMetrics =
      TasCentral.identityDomainNameTasApiVersionIdUsages(client, baseURI,
                                                         identitydomainname,
                                                         versionid).getAsJson(creditaccountid,
                                                                              creditentitlementserviceinstanceids,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              null,
                                                                              Usages.class);


    LOG_EXIT("getUsages[" + apiType + " ] Creditaccountid -> ( " +
             creditaccountid + " ) Creditentitlementserviceinstanceids -> ( " +
             creditentitlementserviceinstanceids + " ) UsageMetrics ->\n" +
        toXMLString(usageMetrics));

    return usageMetrics;
  }


  //______________________
  // Utility Methods Start
  //______________________

  private static final String ENCODING = "utf-8";

  public String toString(Object tasRestModelObject) {
    StringBuilder sb = new StringBuilder();
    ToStringUtil.toString(sb, tasRestModelObject);
    return sb.toString();
  }

  public String toXMLString(Object tasRestModelObject) {
    return ToStringUtil.toRestApiModelXML(tasRestModelObject);

  }

  public <T> T fromXMLString(String theXMLString, Class<T> clazz) {
    return ToStringUtil.toRestApiModelObject(theXMLString, clazz);
  }

  public void toXMLStream(Object tasRestModelObject,
                          java.io.OutputStream outputStream) throws IOException {
    try {
      outputStream.write(ToStringUtil.toRestApiModelXML(tasRestModelObject).getBytes(ENCODING));
    } finally {
      try {
        outputStream.close();
      } catch (Exception ex) {
        //ignore
      }
    }
  }

  public <T> T fromXMLStream(InputStream inputStream,
                             Class<T> clazz) throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] bytes = new byte[1024];
    int bytesRead = -1;
    try {
      while ((bytesRead = inputStream.read(bytes)) != -1) {
        baos.write(bytes, 0, bytesRead);
      }
    } finally {
      try {
        inputStream.close();
      } catch (Exception ex) {
        //ignore
      }
    }
    return ToStringUtil.toRestApiModelObject(baos.toString(ENCODING), clazz);
  }


  public String toJSONString(Object tasRestModelObject) throws IOException,
                                                               JsonGenerationException,
                                                               JsonMappingException {
    ObjectMapper objectMapper = getObjectMapper();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    objectMapper.writeValue(baos, tasRestModelObject);
    return baos.toString(ENCODING);
  }

  public <T> T fromJSONString(String theJSONString,
                              Class<T> modelClass) throws IOException,
                                                          JsonParseException,
                                                          JsonMappingException {
    ObjectMapper objectMapper = getObjectMapper();
    T model = objectMapper.readValue(theJSONString, modelClass);
    return model;
  }


  public void toJSONStream(Object tasRestModelObject,
                           java.io.OutputStream outputStream) throws IOException {
    try {
      outputStream.write(toJSONString(tasRestModelObject).getBytes(ENCODING));
    } finally {
      try {
        outputStream.close();
      } catch (Exception ex) {
        //ignore
      }
    }
  }

  public <T> T fromJSONStream(InputStream inputStream,
                              Class<T> clazz) throws IOException,
                                                     JsonParseException,
                                                     JsonMappingException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] bytes = new byte[1024];
    int bytesRead = -1;
    try {
      while ((bytesRead = inputStream.read(bytes)) != -1) {
        baos.write(bytes, 0, bytesRead);
      }
    } finally {
      try {
        inputStream.close();
      } catch (Exception ex) {
        //ignore
      }
    }
    return fromJSONString(baos.toString(ENCODING), clazz);
  }


  public XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return toXMLGregorianCalendar(cal);
  }

  public XMLGregorianCalendar toXMLGregorianCalendar(Calendar calendar) throws DatatypeConfigurationException {
    return buildXMLGregorianCalendar(calendar);
  }

  public Calendar toCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
    return buildCalendar(xmlGregorianCalendar);
  }

  public Date toDate(XMLGregorianCalendar xmlGregorianCalendar) {
    return buildCalendar(xmlGregorianCalendar).getTime();
  }

  //____________________
  // Utility Methods End
  //____________________

  public TasCommonRestUtil getUtilityHandle() {
    return this;
  }

  public IdentityDomains getAllIdentityDomains(String accountid,
                                               String datacenterid,
                                               String customerZone,
                                               String customerZoneid,
                                               String idmName, String statuses,
                                               String excludedstatuses,
                                               String limit, String offset) {
    return null;
  }

  enum TasRestAPIType {
    TAS_CENTRAL,
    TAS_DATACENTER;

    public boolean isTasCentral() {
      return this == TAS_CENTRAL;
    }

    public boolean isTasDataCenter() {
      return this == TAS_DATACENTER;
    }

    @Override
    public String toString() {
      return name();
    }
  }

  TasRestClientImpl() {

  }

  TasRestClientImpl(TasRestAPIType apiType, String versionid, String baseURI,
                    String authUserId, String authPassword,
                    String oracleUserId, String identitydomainname) {

    validateParams(apiType, versionid, baseURI, authUserId, authPassword,
                   oracleUserId, identitydomainname);

    logger.info("Initializing...ApiType -> ( " + apiType +
                " ) Versionid -> ( " + versionid + " ) BaseURI -> ( " +
                baseURI + " ) AuthUserId -> ( " + authUserId +
                " ) AuthPassword -> ( " + authPassword +
                " ) oracleUserId -> ( " + oracleUserId +
                " ) IdentityDomainName -> ( " + identitydomainname + " )");

    initTasRestClient();

  }

  boolean matchBaseURI(String authUserId, String authPassword, String baseURI,
                       String versionid, TasRestAPIType apiType,
                       String identitydomainname) {

    logger.info("Matching Client...authUserId -> ( " + authUserId +
                " ) authPassword -> ( " + authPassword + " ) baseUri -> ( " +
                baseURI + " This : " + this.baseURI +
                " ) VersionID -> ( Param : " + versionid + " This : " +
                this.versionid + " ) ApiType -> ( Param : " + apiType +
                " This : " + this.apiType +
                " ) IdentityDomainName -> ( Passed : " + identitydomainname +
                " This : " + this.identitydomainname + " ) ");

    boolean isMatch = true;
    if (!(apiType == null ? this.apiType == null : apiType == apiType)) {
      isMatch = false;
    } else if (!(versionid == null ? this.versionid == null :
                 versionid.equals(this.versionid))) {
      isMatch = false;
    } else if (!(baseURI == null ? this.baseURI == null :
                 URI.create(baseURI).equals(this.baseURI))) {
      isMatch = false;
    } else if (!(authUserId == null ? this.authUserId == null :
                 authUserId.equals(this.authUserId))) {
      isMatch = false;
    } else if (!(authPassword == null ? this.authPassword == null :
                 authPassword.equals(this.authPassword))) {
      isMatch = false;
    } else if (!(identitydomainname == null ? this.identitydomainname == null :
                 identitydomainname.equals(this.identitydomainname))) {
      isMatch = false;
    } else {
      isMatch = true;
    }
    logger.info(isMatch ? "Matched " :
                "Did NOT Match" + " This Client...baseURI -> ( Param : " +
                baseURI + " This : " + this.baseURI +
                " ) VersionID -> ( Param : " + versionid + " This : " +
                this.versionid + " ) ApiType -> ( Param : " + apiType +
                " This : " + this.apiType +
                " ) IdentityDomainName -> ( Passed : " + identitydomainname +
                " This : " + this.identitydomainname + " ) ");
    return isMatch;
  }

  private void validateParams(TasRestAPIType apiType, String versionid,
                              String baseURI, String authUserId,
                              String authPassword, String oracleUserId,
                              String identitydomainname) {

    logger.info("validateParams...ApiType -> ( " + apiType +
                " ) Versionid -> ( " + versionid + " ) BaseURI -> ( " +
                baseURI + " ) AuthUserId -> ( " + authUserId +
                " ) AuthPassword -> ( " + authPassword +
                " ) OracleUserId -> ( " + oracleUserId +
                " ) IdentityDomainName -> ( " + identitydomainname + " )");

    StringBuilder errors = new StringBuilder();
    if (apiType == null) {
      errors.append("apiType Param is Required\n");
    }
    if (versionid == null) {
      errors.append("versionid Param is Required\n");
    }
    if (baseURI == null) {
      errors.append("baseURI Param is Required\n");
    }
    if (authUserId == null) {
      errors.append("userId Param is Required\n");
    }
    if (authPassword == null) {
      errors.append("password Param is Required\n");
    }
    if (oracleUserId == null) {
      errors.append("oracleUserId Param is Required\n");
    }
    if (errors.length() > 0) {
      throw new IllegalArgumentException(errors.toString());
    }

    this.apiType = apiType;
    this.versionid = versionid;
    this.baseURI = URI.create(baseURI);
    this.authUserId = authUserId;
    this.authPassword = authPassword;
    this.oracleUserId = oracleUserId;
    this.identitydomainname = identitydomainname;

  }


  private static ObjectMapper getObjectMapper() {
    SimpleModule enumModule =
      new SimpleModule("SimpleModule", new Version(1, 0, 0, null));
    enumModule.setDeserializers(new CustomDeserializers());
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(enumModule);

    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
    mapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY,
                     false);
    mapper.enable(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
    //mapper.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
    mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                     true);
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                     true);
    //mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
    //                 false);
    mapper.setDateFormat(getUTCDateFormat());


    /*ObjectMapper objectMapper = new ObjectMapper();
    DeserializerProvider provider =
      new StdDeserializerProvider().withAdditionalKeyDeserializers(new CaseInsensitiveKeyDeserializers());
    objectMapper.setDeserializerProvider(provider);*/

    return mapper;
  }

  private static SimpleDateFormat getUTCDateFormat() {

    SimpleDateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

    return dateFormat;
  }

  private Logger jerseyLogger;

  private void initTasRestClient() {

    logger.info("Initializing TAS REST CLient...");

    jerseyLogger = Logger.getLogger("com.sun.jersey");
    jerseyLogger.setLevel(Level.FINEST);

    JSONConfiguration.natural().humanReadableFormatting(true).build();

    commonMessageHeaders = new HashMap<String, String>();
    commonMessageHeaders.put(ORACLE_USER_ID_HEADER, oracleUserId);
    if (this.identitydomainname != null) {
      commonMessageHeaders.put(ORACLE_IDM_HEADER, identitydomainname);
    } else {
      this.identitydomainname = COMMON_PATH_URI;
    }

    ClientConfig clientConfig = new DefaultClientConfig();
    clientConfig.getClasses().add(JAXBObjectMapperContextResolver.class);
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
                                   Boolean.TRUE);
    clientConfig.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND,
                                     true);
    if (sunHttpClient) {
      logger.info("Setting Sun Http Client...");
      client = Client.create(clientConfig);
    } else {
      logger.info("Setting Apache Http Client...");
      client =
          new ApacheHttpClient4(createClientHandler(clientConfig), clientConfig);
    }

    /**
         * Add Filter to add Any common headers to requests sent to the
         * REST Service Provider for ALL WebResources
         */
    client.addFilter(new ClientFilter() {
        public ClientResponse handle(ClientRequest request) {
          if (commonMessageHeaders != null &&
              commonMessageHeaders.size() > 0) {
            for (String headerKey : commonMessageHeaders.keySet()) {
              if (!request.getHeaders().containsKey(headerKey)) {

                logger.info("[" + Thread.currentThread().getId() +
                            "] Adding Header Name -> ( " + headerKey +
                            " } Value -> ( " +
                            commonMessageHeaders.get(headerKey) + " )");
                request.getHeaders().putSingle(headerKey,
                                               commonMessageHeaders.get(headerKey));
              }
            }
          }
          if (postWithPatchOverride &&
              request.getMethod().equalsIgnoreCase(PATCH_METHOD)) {
            logger.info("Switching Method from PATCH to POST and Adding Header -> ( " +
                        POST_WITH_PATCH_OVERRIDE_HEADER +
                        " ) Curr Value -> ( " + request.getMethod() + " ) ");
            request.setMethod(POST_METHOD);
            request.getHeaders().putSingle(POST_WITH_PATCH_OVERRIDE_HEADER,
                                           PATCH_METHOD);
          }
          ClientResponse response = getNext().handle(request);
          commonMessageHeaders.remove(POST_WITH_PATCH_OVERRIDE_HEADER);
          return response;
        }
      });

    final HTTPBasicAuthFilter authFilter =
      new HTTPBasicAuthFilter(authUserId, authPassword);
    client.addFilter(authFilter);
    client.addFilter(new LoggingFilter());
    //client.addFilter(new LoggingFilter(logger));
    client.setFollowRedirects(true);
    client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
    client.setReadTimeout(READ_TIMEOUT_MILLIS);

  }


  private static void LOG_ENTRY(String msg) {
    LOG(Level.INFO, "Entering..." + msg);
  }

  private static void LOG_EXIT(String msg) {
    LOG(Level.INFO, "Exiting..." + msg);
  }

  private static void LOG(Level level, String msg) {
    LOG(level, msg, null);
  }

  private static void LOG(Level level, String msg, Throwable error) {
    logger.log(level, "[" + Thread.currentThread().getId() + "]..." + msg,
               error);
  }


  private static class ToStringUtil {

    static String toRestApiModelXML(Object tasRestModelObject) {
      logger.info("Ready toRestApiModelXML For Class -> (" +
                  tasRestModelObject.getClass().getName() + " )");

      String restApiModelXML = null;
      if (tasRestModelObject != null) {
        try {
          TasRestApiModelWrapper<Object> wrapper =
            new TasRestApiModelWrapper<Object>();
          wrapper.setObj(tasRestModelObject);
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          Marshaller m =
            getMarshaller(wrapper.getClass(), tasRestModelObject.getClass());
          m.marshal(wrapper, bos);
          restApiModelXML = bos.toString(ENCODING);
        } catch (Throwable ex) {
          logger.log(Level.WARNING,
                     "Unable toRestApiModelXML...Error : " + ex.getMessage(),
                     ex);
        }
        logger.fine("Created restApiModelXML String :\n" +
            restApiModelXML);
      } else {
        logger.warning("tasRestModelObject Param is NULL...Cannot Marshall....");
      }
      return restApiModelXML;
    }

    static <T> T toRestApiModelObject(String restApiModelXML,
                                      Class<T> restApiModelClass) {

      logger.info("Ready to Convert XML -> (" + restApiModelXML +
                  ") For Class -> (" + restApiModelClass.getName() + " )");

      TasRestApiModelWrapper<T> respApiWrapperObject = null;
      if (restApiModelXML != null && restApiModelXML.trim().length() > 0) {
        ByteArrayInputStream bis =
          new ByteArrayInputStream(restApiModelXML.trim().getBytes());
        try {
          Unmarshaller um =
            getUnMarshaller(TasRestApiModelWrapper.class, restApiModelClass);
          respApiWrapperObject =
              (TasRestClientImpl.ToStringUtil.TasRestApiModelWrapper<T>)um.unmarshal(bis);
        } catch (Throwable ex) {
          logger.log(Level.WARNING,
                     "Unable to Unmarshall restApiModelXML : " + restApiModelXML +
                     "...Error : " + ex.getMessage(), ex);
        }
      } else {
        logger.warning("restApiModelXML Param is NULL or Empty String...Cannot Unmarshall....");
      }
      logger.info("Created " + restApiModelClass.getName() +
                  "..For restApiModelXML : " + restApiModelXML);
      return respApiWrapperObject.getObj();
    }


    private static Marshaller getMarshaller(Class... msgSvcRespClass) throws JAXBException {
      JAXBContext jaxbContext = JAXBContext.newInstance(msgSvcRespClass);
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      return marshaller;
    }

    private static Unmarshaller getUnMarshaller(Class... msgSvcRespClass) throws JAXBException {
      JAXBContext jaxbContext = JAXBContext.newInstance(msgSvcRespClass);
      Unmarshaller Unmarshaller = jaxbContext.createUnmarshaller();
      return Unmarshaller;
    }

    @XmlRootElement(namespace = "##default", name = "##default")
    private static class TasRestApiModelWrapper<T> {
      T obj;


      public void setObj(T obj) {
        this.obj = obj;
      }

      public T getObj() {
        return obj;
      }
    }

    private static final String BASE_IDENT = " ";
    private static final String PRO_SEP = " : ";
    private static final String CLASS_SEP_START = " { ";
    private static final String CLASS_SEP_END = "} ";
    private static final String COLLECTION_SEP_START = " [ ";
    private static final String COLLECTION_SEP_END = " ] ";
    private static final String NULL_PROP = "NULL";
    private static final String NL = "\n";

    private static final String TAS_REST_MODEL_PKG_NM =
      Account.class.getPackage().getName();
    private static final String[] SKIP_PROPS = { "class" };
    private static final List<String> SKIP_LIST = Arrays.asList(SKIP_PROPS);

    static void toString(StringBuilder sb, Object tasRestModelBean) {
      buildRestApiModelString(sb, tasRestModelBean, BASE_IDENT, null);
    }

    private static void buildRestApiModelString(StringBuilder sb,
                                                Object tasRestModelBean,
                                                String indent, String key) {
      if (tasRestModelBean != null &&
          tasRestModelBean.getClass().getPackage().getName().equalsIgnoreCase(TAS_REST_MODEL_PKG_NM)) {
        logger.fine("Ready toString TAS REST Bean Object  -> ( " +
                    tasRestModelBean.getClass().getName() + " )");
        sb.append(NL + indent + tasRestModelBean.getClass().getSimpleName() +
                  CLASS_SEP_START);
        try {
          PropertyDescriptor[] pds =
            Introspector.getBeanInfo(tasRestModelBean.getClass()).getPropertyDescriptors();
          String propNameIdent =
            buildIndent(indent.length() + CLASS_SEP_START.length() +
                        tasRestModelBean.getClass().getSimpleName().length());
          if (pds != null && pds.length > 0) {
            for (PropertyDescriptor pd : pds) {
              logger.fine("Processing Property[" + pd.getName() + "] Type[" +
                          pd.getPropertyType() + "]");
              Method getter = pd.getReadMethod();
              if (getter != null && !SKIP_LIST.contains(pd.getName())) {
                String propNameFormat =
                  NL + propNameIdent + pd.getName() + (key != null ?
                                                       "<" + key + ">" : "") +
                  PRO_SEP;
                try {
                  Object tmpObj = getter.invoke(tasRestModelBean, null);
                  if (tmpObj == null) {
                    sb.append(propNameFormat + NULL_PROP);
                  } else if (tmpObj instanceof Enum) {
                    sb.append(propNameFormat + ((Enum)tmpObj).name());
                  } else if (tmpObj instanceof Collection) {
                    toStringCollection(sb, propNameFormat, pd,
                                       (Collection)tmpObj, key);
                  } else if (tmpObj instanceof Map) {
                    toStringMap(sb, propNameFormat, pd, (Map)tmpObj);
                  } else if (tmpObj.getClass().getPackage().getName().equalsIgnoreCase(TAS_REST_MODEL_PKG_NM)) {
                    buildRestApiModelString(sb, tmpObj, propNameIdent, key);
                  } else {
                    sb.append(propNameFormat + tmpObj.toString());
                  }
                } catch (InvocationTargetException e) {
                  logger.log(Level.SEVERE,
                             "Error Getting Property(" + pd.getName() +
                             " ) Value ", e);
                  continue;
                }
              }
            }
          }
        } catch (Throwable ex) {
          logger.log(Level.WARNING,
                     "Unable toString Class -> ( " + tasRestModelBean.getClass().getName() +
                     " )...Error : " + ex.getMessage(), ex);
        }
        sb.append(NL + indent + CLASS_SEP_END +
                  tasRestModelBean.getClass().getSimpleName());
      }
    }

    private static void toStringCollection(StringBuilder sb,
                                           String propNameFormat,
                                           PropertyDescriptor pd, Collection c,
                                           String key) {
      if (c.size() == 0 ||
          !c.toArray()[0].getClass().getPackage().getName().equalsIgnoreCase(TAS_REST_MODEL_PKG_NM)) {
        sb.append(propNameFormat + c.toString());
      } else {
        String colIndent = buildIndent(propNameFormat.length() - 1);
        String colObjIndent =
          buildIndent(propNameFormat.length() + COLLECTION_SEP_START.length() -
                      1);
        sb.append(propNameFormat + COLLECTION_SEP_START);
        for (Object tasRestModelBean : c) {
          buildRestApiModelString(sb, tasRestModelBean, colObjIndent, key);
        }
        sb.append(NL + colIndent + COLLECTION_SEP_END + pd.getName());
      }
    }

    private static void toStringMap(StringBuilder sb, String propNameFormat,
                                    PropertyDescriptor pd, Map m) {
      if (m.size() == 0 ||
          !m.values().toArray()[0].getClass().getPackage().getName().equalsIgnoreCase(TAS_REST_MODEL_PKG_NM)) {
        sb.append(propNameFormat + m.toString());
      } else {
        String colIndent = buildIndent(propNameFormat.length() - 1);
        String colObjIndent =
          buildIndent(propNameFormat.length() + COLLECTION_SEP_START.length() -
                      1);
        sb.append(propNameFormat + COLLECTION_SEP_START);

        for (Object k : m.keySet()) {
          Object val = m.get(k);
          if (val instanceof Collection) {
            buildRestApiModelString(sb, m.get(k), colObjIndent, k.toString());
          } else {

          }
        }
        sb.append(NL + colIndent + COLLECTION_SEP_END + pd.getName());

      }
    }

    private static String buildIndent(int length) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < length; ++i) {
        sb.append(" ");
      }
      return sb.toString();
    }
  }


  private static ApacheHttpClient4Handler createClientHandler(final ClientConfig cc) {

    Object connectionManager = null;
    Object httpParams = null;
    try {
      if (cc != null) {
        connectionManager =
            cc.getProperties().get(ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER);
        if (connectionManager != null) {
          if (!(connectionManager instanceof ClientConnectionManager)) {
            Logger.getLogger(ApacheHttpClient4.class.getName()).log(Level.WARNING,
                                                                    "Ignoring value of property " +
                                                                    ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER +
                                                                    " (" +
                                                                    connectionManager.getClass().getName() +
                                                                    ") - not instance of org.apache.http.conn.ClientConnectionManager.");
            connectionManager = null;
          }
        }

        httpParams =
            cc.getProperties().get(ApacheHttpClient4Config.PROPERTY_HTTP_PARAMS);
        if (httpParams != null) {
          if (!(httpParams instanceof HttpParams)) {
            Logger.getLogger(ApacheHttpClient4.class.getName()).log(Level.WARNING,
                                                                    "Ignoring value of property " +
                                                                    ApacheHttpClient4Config.PROPERTY_HTTP_PARAMS +
                                                                    " (" +
                                                                    httpParams.getClass().getName() +
                                                                    ") - not instance of org.apache.http.params.HttpParams.");
            httpParams = null;
          }
        }
      }

      TrustStrategy ts = new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates,
                                 String s) throws CertificateException {
          return true;
        }
      };

      final DefaultHttpClient client =
        new DefaultHttpClient((ClientConnectionManager)connectionManager,
                              (HttpParams)httpParams);
      Scheme http =
        new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
      SSLSocketFactory sf =
        new SSLSocketFactory(ts, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      Scheme https = new Scheme("https", 443, sf);
      SchemeRegistry sr = client.getConnectionManager().getSchemeRegistry();
      sr.register(http);
      sr.register(https);


      CookieStore cookieStore = null;
      boolean preemptiveBasicAuth = false;

      if (cc != null) {
        for (Map.Entry<String, Object> entry : cc.getProperties().entrySet())
          client.getParams().setParameter(entry.getKey(), entry.getValue());

        if (cc.getPropertyAsFeature(ApacheHttpClient4Config.PROPERTY_DISABLE_COOKIES))
          client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                                          CookiePolicy.IGNORE_COOKIES);

        Object credentialsProvider =
          cc.getProperty(ApacheHttpClient4Config.PROPERTY_CREDENTIALS_PROVIDER);
        if (credentialsProvider != null &&
            (credentialsProvider instanceof CredentialsProvider)) {
          client.setCredentialsProvider((CredentialsProvider)credentialsProvider);
        }

        final Object proxyUri =
          cc.getProperties().get(ApacheHttpClient4Config.PROPERTY_PROXY_URI);
        if (proxyUri != null) {
          final URI u = getProxyUri(proxyUri);
          final HttpHost proxy =
            new HttpHost(u.getHost(), u.getPort(), u.getScheme());

          if (cc.getProperties().containsKey(ApacheHttpClient4Config.PROPERTY_PROXY_USERNAME) &&
              cc.getProperties().containsKey(ApacheHttpClient4Config.PROPERTY_PROXY_PASSWORD)) {

            client.getCredentialsProvider().setCredentials(new AuthScope(u.getHost(),
                                                                         u.getPort()),
                                                           new UsernamePasswordCredentials(cc.getProperty(ApacheHttpClient4Config.PROPERTY_PROXY_USERNAME).toString(),
                                                                                           cc.getProperty(ApacheHttpClient4Config.PROPERTY_PROXY_PASSWORD).toString()));

          }
          client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                                          proxy);
        }

        preemptiveBasicAuth =
            cc.getPropertyAsFeature(ApacheHttpClient4Config.PROPERTY_PREEMPTIVE_BASIC_AUTHENTICATION);
      }

      if (client.getParams().getParameter(ClientPNames.COOKIE_POLICY) ==
          null ||
          !client.getParams().getParameter(ClientPNames.COOKIE_POLICY).equals(CookiePolicy.IGNORE_COOKIES)) {
        cookieStore = new BasicCookieStore();
        client.setCookieStore(cookieStore);
      }

      return new ApacheHttpClient4Handler(client, cookieStore,
                                          preemptiveBasicAuth);
    } catch (Throwable t) {
      RuntimeException rex =
        new RuntimeException("Error Creating ApacheHttpClient4Handler...Error : " +
                             t.getMessage(), t);
      logger.log(Level.SEVERE, rex.getMessage(), t);
      throw rex;
    }
  }


  private static URI getProxyUri(final Object proxy) {
    if (proxy instanceof URI) {
      return (URI)proxy;
    } else if (proxy instanceof String) {
      return URI.create((String)proxy);
    } else {
      throw new ClientHandlerException("The proxy URI (" +
                                       ApacheHttpClient4Config.PROPERTY_PROXY_URI +
                                       ") property MUST be an instance of String or URI");
    }
  }


  private static XMLGregorianCalendar buildXMLGregorianCalendar(Calendar calendar) throws DatatypeConfigurationException {
    if (calendar == null) {
      throw new IllegalArgumentException("calendar Param is Required");
    }
    GregorianCalendar gCal = new GregorianCalendar();
    gCal.setTime(calendar.getTime());
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
  }

  private static Calendar buildCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
    if (xmlGregorianCalendar == null) {
      throw new IllegalArgumentException("xmlGregorianCalendar Param is Required");
    }
    return xmlGregorianCalendar.toGregorianCalendar().getInstance();
  }

  private static void verifyResponse(ClientResponse response) {

    logger.info("Response Returned -> ( " + response + " ) Location -> ( " +
                (response.getLocation() != null ?
                 response.getLocation().toString() : "NULL") + " )");

    if (response.getStatus() >= 400) {
      throw new WebApplicationException(Response.status(response.getClientResponseStatus()).build());
    }
  }

}
