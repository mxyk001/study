package com.oracle.saas.qa.tas.rest.client;


import com.oracle.tas.rest.api.model.AppCredential;
import com.oracle.tas.rest.api.model.AppCredentials;
import com.oracle.tas.rest.api.model.CentralVersion;
import com.oracle.tas.rest.api.model.ConsoleRegistration;
import com.oracle.tas.rest.api.model.ConsoleRegistrations;
import com.oracle.tas.rest.api.model.Coupon;
import com.oracle.tas.rest.api.model.CouponDistributor;
import com.oracle.tas.rest.api.model.Coupons;
import com.oracle.tas.rest.api.model.CreditAccount;
import com.oracle.tas.rest.api.model.CreditAccounts;
import com.oracle.tas.rest.api.model.CreditEntitlement;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstance;
import com.oracle.tas.rest.api.model.CreditEntitlementServiceInstances;
import com.oracle.tas.rest.api.model.CreditEntitlements;
import com.oracle.tas.rest.api.model.Documents;
import com.oracle.tas.rest.api.model.IdentityValidations;
import com.oracle.tas.rest.api.model.Messages;
import com.oracle.tas.rest.api.model.MetricStates;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.Redemption;
import com.oracle.tas.rest.api.model.Redemptions;
import com.oracle.tas.rest.api.model.Subscriptions;
import com.oracle.tas.rest.api.model.TrialValidations;
import com.oracle.tas.rest.api.model.UpTimes;
import com.oracle.tas.rest.api.model.Usages;


/**
 * TAS REST Client API Operations Only Supported by TAS Central.
 * The Operations are in the Context of the oracleUserId Passed to the Factory.
 * <p>
 * Order Builder Example :
 * <blockquote><pre>
 * //______________________________________
 * // 1. Get Handle of TasCentralRestClient
 * TasCentralRestClient tasCentralHandle =
 * TasRestFactory.getTasCentralRestClientHandle(versionid, baseURICentral,authUserId, authPassword,oracleUserId);
 *
 * //________________________________________________________________
 * // 2. Get the TasOrderBuilder Handle from the TasCentralRestClient
 * TasOrderBuilder tob = tasCentralHandle.getTasOrderBuilderHandle();
 *
 * //_______________________________________________________________________________________
 * // 3. Utiltiy Method which Shows the Order in which the Builders Methods should be called
 * tob.printMethodsOrder();
 *
 * //_____________________________________________________________________
 * // 4. Start the New Order Builder Process
 * // Note : i) The Order Builder uses the Builder Creational Pattern and returns
 * //        an instance of itself after each call so one can create the
 * //        Order in one Statement
 * //        ii) The Method calls have to follow an Order which you can
 * //            determine either by looking at the TasOrderBuilder interface
 * //            OR calling the TasOrderBuilder printMethodsOrder method.
 * //___________________________________________________________________
 * // i)  Start the New Order by Passing AdminUser Email and
 * //     AdminUserName(If Null Defaults to adminEmailAddress) and csiNumber
 * Order order = tob.startNewOrder(adminEmailAddress,adminUserName,csiNumber).
 * //_______________________
 * // ii)   Add Order Origin
 * addOrderSource(OrderSourceType.STORE).
 * //_________________________________________________
 * // iii)  Add DataCenter ID and DataCenter Region ID
 * addDataCenter(dataCenterId,dataCenterRegionId).
 * //____________________________
 * // iii)  Add Type of Operation
 * addOperation(OperationType.ONBOARDING).
 * //____________________________________________________________________
 * // iv)   Add Type of Subscription and IdentityDomainName and CSINumber
 * //       IDM Name Can be NULL in which case the Name will be Generated
 * addSubscription(SubscriptionType.PRODUCTION,null,null).
 * //_______________________________________________________________________
 * // v)    Add Type of Account, Account ID and AccountName.
 * //       AccountName Can be NULL in which case the Name will be Generated
 * addAccount(AccountType.SINGLE,accountId,null).
 * //____________________________________________________________
 * // vi)   Add Type of Service 1 and Its Category and OPTIONALLY
 * //       the ServiceDisplayName
 * addService(TasServiceType.JAVA,
 * ServiceSizeCategoryType.BASIC,null).
 * //_____________________________________________________________
 * // vii)  Add Type of Service 2 and Its Category and OPTIONALLY
 * //       the ServiceDisplayName
 * addService(TasServiceType.APEX,
 * ServiceSizeCategoryType.BASIC,null).
 * //______________________________________________________________________________
 * // viii) Associate Service 2 with Service 1
 * //       Note : The number is the Order the Service was Added in step vi and vii
 * associateServices(2, 1).
 * //_____________________
 * // ix)  Build the Order
 * build();
 * //____________________________________________________________________________
 * // x)    Make the REST Call to Create the Order Using TasCentralRestClient API
 * order = tasCentralApi.createOrder(order);
 *
 * </pre></blockquote>
 *
 * @author Haris Shah
 */
public interface TasCentralRestClient extends TasCommonRestClient {


  /**
   * Get a Handle on the TasOrderBuilder which is a Helper for Building New Tas Orders.
   *
   * @return TasOrderBuilder
   */
  TasOrderBuilder getTasOrderBuilderHandle();

  /**
   * Get Details of TAS REST CenterVersion (Returns canonicalLinks to All the Resources)
   * Note : These cononicalLinks should be used if there is a need to pass any canonical
   *        Links to the TAS Centeral REST Service. Clients Should NOT hardcode canonical Links.
   *
   * @return CentralVersion
   */
  CentralVersion getCentralVersion();


  /**
   * Get ALL documents with the given contracts Id and Name
   *
   * @param documentcontractid the Id of the Contract Document.
   * @param documentcontractname Name of the Contract Document
   *
   * @return Documents
   */
  Documents getAllDocumentContracts(String documentcontractid,
                                    String documentcontractname);


  /**
   * Create Identity Validations (Like Credit Card)
   *
   * @param identityvalidations Identity Validations to create
   *
   * @return IdentityValidations
   */
  IdentityValidations createIdentityValidations(IdentityValidations identityvalidations); //TODO  verify Return


  /**
   * Create an Order
   *
   * @param order The Details of the Order to Create
   *
   * @return the Created Order
   */
  Order createOrder(Order order);

  /**
   * Delete Order Identified by the given OrderId
   *
   * @param orderid The Id of the order to delete
   *
   * @return true for success false for failure
   */
  boolean deleteOrder(String orderid);

  /**
   * Activate the Order.
   *
   * @param order The Details of the Order to Activate
   *
   * @param orderid The Id of the order to activate
   *
   * @return The Activated Order.
   */
  Order activateOrder(Order order, String orderid);


  /**
   * Get ALL subscripitions that meet the passed criteria. One or more criteria
   * can be passed.
   *
   * @param ids List subscriptions by ids
   * @param accountid account id
   * @param associatedsubscriptionid subscriptions associated with this subscription id
   * @param relatedsubscriptionid subscriptions related with this subscription id
   * @param identitydomainid subscriptions for a given identity domain id
   * @param identitydomainname subscriptions for a given identity domain name
   * @param servicetype subscriptions for a given serviceType
   * @param subscriptiontype  subscriptions for a given subscription type
   * @param type  List all subscriptions for a given subscription type
   * @param subscriptionstatuses  List all subscriptions  for given statuses
   * @param statuses   List all subscriptions for given subscription statuses
   * @param excludedsubscriptionstatuses List all subscriptions not matching the list of statuses
   * @param excludedstatuses List all subscriptions not matching the list of statuses
   * @param excludepurgedterminatedbefore Exclude all subscriptions terminated before the specified date
   * @param statesrangestart  start range of the states to return for each subscriptions, if both statesRangeStart and statesRangeEnd is null, only the latest available state will be returned
   * @param statesrangeend  end range of the states to return for each subscriptions
   * @param aggregateduptimesrangestart startDate range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the startDate range date will be adjusted to midnight of the each subscription data-center time-zone and is inclusive, if aggregatedUpTimesRangeStart is specified and aggregatedUpTimesRangeEnd is not specified, the aggregation will be done on the day specified by aggregatedUpTimesRangeStart only.
   * @param aggregateduptimesrangeend endDate range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the endDate range date will be adjusted to midnight of the each subscription data-center time-zone and is exclusive.
   * @param servicecategory  Service category of the subscriptions
   * @param subscriptioncategory Subscription category of the subscriptions
   * @param category category of the subscriptions
   * @param serviceinstancetype List only subscription with specific service instance type
   * @param limit  Limits the number of resources to return
   * @param offset  Offset of the resources to return
   *
   * @return Subscriptions matching passed criteria
   */
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
                                           String limit, String offset);


  /**
   * Get a List of ALL Application  Credentials
   *
   * @return A list of app credentials, default ordering is "id:asc"
   */
  AppCredentials getAppCredentials();


  /**
   * Get a Application Credential Identified by the passed appcredentialid
   *
   * @param appcredentialid the App ID of the Credentials to Return
   * @return  AppCredential
   */
  AppCredential getAppCredential(String appcredentialid);


  /**
   * Get ALL Console Registrations
   *
   * @return collection of Console Registrations
   */
  ConsoleRegistrations getConsoleRegistrations();

  /**
   * Get a Console Registration identified by the passed registrationId
   *
   * @param registrationId
   * @return ConsoleRegistration
   */
  ConsoleRegistration getConsoleRegistration(String registrationId);

  /**
   * Create Coupon Distributor.
   *
   * @param couponDistributor The Coupon Distributor to create.
   * @return CouponDistributor created
   */
  CouponDistributor createCouponDistributor(CouponDistributor couponDistributor);

  /**
   * Get ALL coupons
   *
   * @return List of Coupons
   */
  Coupons getCoupons();

  /**
   * Get a specific coupon Identified by couponCode
   *
   * @param couponCode code of the Coupon to get
   * @return Coupon
   */
  Coupon getCoupon(String couponCode);

  /**
   * Get ALL Coupon Redemptions of a specific type of coupon
   *
   * @param couponCode The code the coupon.
   * @return the List of Redemptions.
   */
  Redemptions getCouponRedemptions(String couponCode);

  /**
   * Get a Redemption of a specific Type of Coupon and redemption ID
   *
   * @param couponCode The code the coupon.
   * @param redemptionId The redemption ID
   * @return Redemption
   */
  Redemption getCouponRedemption(String couponCode, String redemptionId);

  /**
   * Get ALL Credit Accounts
   *
   * @return a List of Credit Accounts
   */
  CreditAccounts getCreditAccounts();

  /**
   * Get CreditAccout identified by creditaccountid.
   *
   * @param creditaccountid ID of the CreditAccount to retrieve
   * @return CreditAccount
   */
  CreditAccount getCreditAccount(String creditaccountid);

  /**
   * Get ALL CreditEntitlementServiceInstances
   *
   * @return List of CreditEntitlementServiceInstances
   */
  CreditEntitlementServiceInstances getCreditEntitlementServiceInstances(String crediEntitlementId);

  /**
   * Get a specific CreditEntitlementServiceInstance identified by its creditentitlementserviceinstanceid
   * @param creditentitlementserviceinstanceid ID of CreditEntitlementServiceInstance
   * @return CreditEntitlementServiceInstance
   */
  CreditEntitlementServiceInstance getCreditEntitlementServiceInstance(String creditentitlementserviceinstanceid);

  /**
   * Get ALL CreditEntitlements
   *
   * @return List of CreditEntitlements
   */
  CreditEntitlements getCreditEntitlements();

  /**
   * Get a specific CreditEntitlement identified by its creditEntitlementId
   *
   * @param creditEntitlementId id of the credit account to return
   * @return CreditEntitlement
   */
  CreditEntitlement getCreditEntitlement(String creditEntitlementId);

  /**
   * Get ALL messages
   *
   * @return a List of Messages
   */
  Messages getMessages();

  /**
   *Get ALL subscription States Metrics
   *
   * @return List of subscription states Metrics
   */
  MetricStates getMetricStates();

  /**
   * Create Trial Validations.
   *
   *@param trialValidations The Trial Validations to Create
   * @return created TrialValidations.
   */
  TrialValidations createTrialValidations(TrialValidations trialValidations);

  /**
   * Get ALL Subscription Up Time Metrics
   *
   * @return List of Subscription Up Time Metrics
   */
  UpTimes getUpTimeMetrics();

  /**
   * Get ALL Subscription Usage metrics
   *
   * @param creditaccountid  id of the credit account
   * @param creditentitlementserviceinstanceids comma separated list of credit entitlement service instance ids to load (50 max) (OPTIONAL)
   * @return List of Subscription Usage metrics
   */
  Usages getUsageMetrics(String creditaccountid,
                         String creditentitlementserviceinstanceids);


}
