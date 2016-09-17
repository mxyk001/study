package com.oracle.saas.qa.tas.rest.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *  Factory For Creating TAS Central and DataCenter REST Clients
 *
 *  @author Haris Shah.
 *
 *  @see com.oracle.saas.qa.tas.rest.client.TasCentralRestClient
 *  @see com.oracle.saas.qa.tas.rest.client.TasDataCenterRestClient
 */
public final class TasRestFactory {

  private static Logger logger =
    Logger.getLogger(TasRestFactory.class.getName());

  private static List<TasRestClientImpl> tasRestClients =
    new ArrayList<TasRestClientImpl>();

  private TasRestFactory() {

  }


  /**
   * Get a Handle on the TAS Central REST Client.
   * Note : The Requests are in the Context of the oracleUserId and identitydomainname
   *
   * @param versionid The Version of the API to Use (Example "v1")
   * @param baseURI The Base URI for TAS Central REST API ( Example : https://tascentral.c9qa132.oraclecorp.com/tas-central )
   * @param authUserId The userId used to authenticate
   * @param authPassword The Password used to Authenticate.
   * @param oracleUserId Operations are in the Context of the oracleUserId which is the Identity of the User to Impersonate.
   *
   * @return TasCentralRestClient Handle
   */
  public static TasCentralRestClient getTasCentralRestClientHandle(String versionid,
                                                                   String baseURI,
                                                                   String authUserId,
                                                                   String authPassword,
                                                                   String oracleUserId) {
    return getTasRestClient(TasRestClientImpl.TasRestAPIType.TAS_CENTRAL,
                            versionid, baseURI, authUserId, authPassword,
                            oracleUserId, null);
  }


  /**
   * Get a Handle on the TAS Central REST Client.
   * Note : The Requests are in the Context of the oracleUserId and identitydomainname
   *
   * @param versionid The Version of the API to Use (Example "v1")
   * @param baseURI The Base URI for TAS Central REST API ( Example : https://tascentral.c9qa132.oraclecorp.com/tas-central )
   * @param authUserId The userId used to authenticate
   * @param authPassword The Password used to Authenticate.
   * @param oracleUserId Operations are in the Context of the oracleUserId which is the Identity of the User to Impersonate.
   * @param identitydomainname The identitydomain/Tenant Name.
   *
   * @return TasCentralRestClient Handle
   */
  public static TasCentralRestClient getTasCentralRestClientHandle(String versionid,
                                                                   String baseURI,
                                                                   String authUserId,
                                                                   String authPassword,
                                                                   String oracleUserId,
                                                                   String identitydomainname) {
    return getTasRestClient(TasRestClientImpl.TasRestAPIType.TAS_CENTRAL,
                            versionid, baseURI, authUserId, authPassword,
                            oracleUserId, identitydomainname);
  }


  /**
   * Get a Handle on the TAS DataCenter REST Client.
   * Note : The Requests are in the Context of the oracleUserId and identitydomainname
   *
   * @param versionid The Version of the API to Use (Example "v1")
   * @param baseURI The Base URI for TAS Central REST API ( Example : http://slcn06vmf0021.us.oracle.com:8101/tas-datacenter )
   * @param authUserId The userId used to authenticate
   * @param authPassword The Password used to Authenticate.
   * @param oracleUserId Operations are in the Context of the oracleUserId which is the Identity of the User to Impersonate.
   *
   * @return TasDataCenterRestClient Handle
   */
  public static TasDataCenterRestClient getTasDataCenterRestClientHandle(String versionid,
                                                                         String baseURI,
                                                                         String authUserId,
                                                                         String authPassword,
                                                                         String oracleUserId) {
    return getTasRestClient(TasRestClientImpl.TasRestAPIType.TAS_DATACENTER,
                            versionid, baseURI, authUserId, authPassword,
                            oracleUserId, null);
  }

  /**
   * Get a Handle on the TAS DataCenter REST Client.
   * Note : The Requests are in the Context of the oracleUserId and identitydomainname
   *
   * @param versionid The Version of the API to Use (Example "v1")
   * @param baseURI The Base URI for TAS Central REST API ( Example : http://slcn06vmf0021.us.oracle.com:8101/tas-datacenter )
   * @param authUserId The userId used to authenticate
   * @param authPassword The Password used to Authenticate.
   * @param oracleUserId Operations are in the Context of the oracleUserId which is the Identity of the User to Impersonate.
   * @param identitydomainname The identitydomain/Tenant Name.
   *
   * @return TasDataCenterRestClient Handle
   */
  public static TasDataCenterRestClient getTasDataCenterRestClientHandle(String versionid,
                                                                         String baseURI,
                                                                         String authUserId,
                                                                         String authPassword,
                                                                         String oracleUserId,
                                                                         String identitydomainname) {
    return getTasRestClient(TasRestClientImpl.TasRestAPIType.TAS_DATACENTER,
                            versionid, baseURI, authUserId, authPassword,
                            oracleUserId, identitydomainname);

  }

  /**
   * Get Handle on the Tas REST Client Utility Interface
   * Which has Helper Methods for Printing and Streaming Model Objects and Date Conversion among Others.
   *
   * @return TasCommonRestUtil
   */
  public static TasCommonRestUtil getTasRestUtil() {
    return new TasRestClientImpl();
  }

  private static synchronized TasRestClientImpl getTasRestClient(TasRestClientImpl.TasRestAPIType apiType,
                                                                 String versionid,
                                                                 String baseURI,
                                                                 String authUserId,
                                                                 String authPassword,
                                                                 String oracleUserId,
                                                                 String identitydomainname) {

    logger.info("Checking For Existing TAS REST Client...BaseURI -> ( " +
                baseURI + " ) API Type -> ( " + apiType + " )");

    TasRestClientImpl retTasRestClient = null;
    for (TasRestClientImpl tasRestClient : tasRestClients) {
      if (tasRestClient.matchBaseURI(authUserId, authPassword, baseURI,
                                     versionid, apiType,
                                     (identitydomainname != null ?
                                      identitydomainname :
                                      TasRestClientImpl.COMMON_PATH_URI))) {
        logger.info("Found Existing TAS REST Client...BaseURI -> ( " +
                    baseURI + " ) For ApiType -> ( " + apiType + " )");
        retTasRestClient = tasRestClient;
        break;
      }
    }
    if (retTasRestClient == null) {
      logger.info("Creating New TAS REST Client...BaseURI -> ( " + baseURI +
                  " ) For ApiType -> ( " + apiType + " )");
      retTasRestClient =
          new TasRestClientImpl(apiType, versionid, baseURI, authUserId,
                                authPassword, oracleUserId,
                                identitydomainname);
      tasRestClients.add(retTasRestClient);
    }
    return retTasRestClient;
  }
}
