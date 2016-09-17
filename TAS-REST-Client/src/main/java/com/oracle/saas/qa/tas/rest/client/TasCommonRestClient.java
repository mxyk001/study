package com.oracle.saas.qa.tas.rest.client;


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


/**
 * TAS REST Client Common API Operations Suppported by BOTH TAS Central and TAS DataCenter
 *
 * @author Haris Shah.
 */
public interface TasCommonRestClient {

  /**
   *  GET the accounts which the logged in user can administer.
   *
   * @return Accounts
   */
  Accounts getAllUserAdministeredAccounts();

  /**
   *  GET the account identified by the acccountid which the logged in user can administer.
   *
   *  @param accountid The AccountID of the Account the Retrieve
   *
   * @return Account
   */
  Account getUserAdministeredAccount(String accountid);

  /**
   * Get a list of administrators, Sort Criteria is "lastName:asc,firstName:asc"
   *
   * @return Admins
   */
  Admins getAllAdmins();

  /**
   * Get All Admins with the given Criteria
   *
   * @param accountid administrators of the specified accountId
   * @param identitydomainid administrator of the specified identity domain
   * @param subscriptionid Administrator of the specified subscription
   * @param username administrators matching the specified username, note that the same username can be listed twice if a user has both IdentityDomain and Subscription admin roles
   * @param scopetypes admins by scope type (subscription, identityDomain or service)
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return Admins matching the Passed Criteria
   */
  Admins getAllAdmins(String accountid, String identitydomainid,
                      String subscriptionid, String username,
                      String scopetypes, String limit, String offset);

  /**
   *  Get an administator Identified by the Admin ID
   *
   *  @param adminid - The AdminID for the Administrator
   *
   * @return Admin
   */
  Admin getAdmin(String adminid);


  /**
   * Get ALL Supported Countries, Sort ordering is "name:asc"
   *
   * @return Countries
   */
  Countries getAllSupportedCountries();

  /**
   * Get ALL Supported Countries with the Passed Criteria Sort ordering is "name:asc"
   *
   * @param common
   * @param countryId Id of a specific country
   * @param expands
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return Countries
   */
  Countries getAllSupportedCountries(String common, String countryId,
                                     String expands, String limit,
                                     String offset);

  /**
   * Get the Supported Country identified by countryid
   *
   * @param countryid - The Supported CountryID
   *
   * @return Country
   */
  Country getSupportedCountry(String countryid);

  /**
   * Get ALL the subdivisions of a supported country Identified by countryid
   *
   * @param countryid The countryid of the Country whoes subdvidions to get.
   *
   * @return Subdivisions.
   */
  Subdivisions getAllSubDivisionsOfCountry(String countryid);

  /**
   * Get a subdivision of a country identified by a subdivisionid and countryid
   *
   * @param countryid The countryid of the Country whoes subdvidion to get.
   * @param subdivisionid - The subdividionid of the subdivision to get
   *
   * @return Subdivision
   */
  Subdivision getSubDivisionOfCountry(String countryid, String subdivisionid);

  /**
   * Get a List of CustomAttributeValueSets for ALL Services, Sort ordering is "attributeid:asc
   *
   * @return CustomAttributeValueSets
   */
  CustomAttributeValueSets getAllCustomAttributeValueSets();

  /**
   * Get a List of CustomAttributeValueSets for a service identified by serviceid, Sort ordering is "attributeid:asc
   *
   * @param serviceid - The serviceid of the service for which to get ALL customAttrubuteValueSets
   *
   * @return CustomAttributeValueSets for a specific service
   */
  CustomAttributeValueSets getCustomAttributeValueSets(String serviceid);

  /**
   * Get a CustomAttributeValueSet identified by its customattrid.
   *
   * @param customattrid - The Id of the CustomAttributeValueSet to get
   *
   * @return CustomAttributeValueSet
   */
  CustomAttributeValueSet getCustomAttributeValueSet(String customattrid);

  /**
   * GET the list of data centers supported. Sort Criteria: "name:asc"
   *
   * @return DataCenters
   */
  DataCenters getAllSupportedDataCenters();

  /**
   * GET the list of data centers supported for a trialserviceType or productionsericettype
   * OR BOTH. Sort Criteria: "name:asc"
   *
   * @param trialservicetype The Trial Service Type Supported
   * @param productionservicetype The Production SeriviceType Suppored.
   * @param regional  Filter by regional data centers (true/false)
   *
   * @return DataCenters
   */
  DataCenters getAllSupportedDataCenters(String trialservicetype,
                                         String productionservicetype,
                                         boolean regional);

  /**
   * Get a Supported DataCenter Identified by datacenterid
   *
   * @param datacenterid The datacenterid of the Supported DataCenter to get.
   *
   * @return DataCenter
   */
  DataCenter getSupportedDataCenter(String datacenterid);

  /**
   * Get ALL Supported Data Center Regions. Default sort Criteria is "name:asc"
   *
   * @return DataRegions
   */
  DataRegions getAllSupportedDataCenterRegions();

  /**
   * GET the list of data Center Regions supported for a trialserviceType or productionsericettype
   * OR BOTH. Sort Criteria: "name:asc"
   *
   * @param trialservicetype The Trial Service Type Supported
   * @param productionservicetype The Production SeriviceType Suppored.
   *
   * @return DataRegions
   */
  DataRegions getAllSupportedDataCenterRegions(String trialservicetype,
                                               String productionservicetype);

  /**
   * Get Supported Data Center Region identified by dataregionid
   * @param dataregionid The Id of the data center region.
   *
   * @return DataRegion
   */
  DataRegion getSupportedDataCenterRegion(String dataregionid);


  /**
   * Get ALL Identify domains. Sort Order "name:asc"
   *
   * @return IdentityDomains
   */
  IdentityDomains getAllIdentityDomains();

  /**
   * Get ALL Identify domains identified by either accountid,datacenterid,identitydomainname
   * OR ALL three.
   *
   * @param accountid The AccountId associated with the Identity Domain
   * @param datacenterid The Data Center Id of the Identity Domain
   * @param customerZoneid customer zone id
   * @param idmName The Name of the Identity Domain
   * @param idmDisplayName DisplayName of the identity domain
   * @param statuses  List all identity domains for the given statuses
   * @param excludedstatuses List all identity domains not matching the specified statuses
   * @param hasImpersonateeTypes List all identity domains for which the current user has an impersonatee of the specified types. Supported types are: ADMIN, USER.
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return IdentityDomains
   */
  IdentityDomains getAllIdentityDomains(String accountid, String datacenterid,
                                        String customerZoneid, String idmName,
                                        String idmDisplayName, String statuses,
                                        String excludedstatuses,
                                        String hasImpersonateeTypes,
                                        String limit, String offset);

  /**
   * Get Identity Domain identified with the identitydomainid
   *
   * @param identitydomainid The Id of the identity domain to return
   *
   * @return IdentityDomain
   */
  IdentityDomain getIdentityDomain(String identitydomainid);

  /**
   * Get ALL system wide notifications, default ordering is "startTime:asc"
   *
   * @return Notifications
   */
  Notifications getAllNotifications();

  /**
   * Get ALL system wide notifications that match the passed criteria.
   *
   * @param accountid Optional account id
   * @param subscriptionid Optional subscription Id
   * @param servicetype Service type filter
   * @param serviceinstancename Service instance name filter
   * @param servicecategory  Service category filter
   * @param textmatch Filter notifications, currently matching text strings from service type, subscription name, subscription display name, event type, details and description
   * @param incidentnumber   Incident number associated with the notification
   * @param communicationtypeid  Communication type asscociated with the notification
   * @param communicationtypeclassids  communication Type Class Ids asscociated with the notification
   * @param creditserviceinstanceid Optional metered service Id
   * @param rangestart Optional start range
   * @param rangeend  Optional end range
   * @param source Optional source of the notification, a value of "COMMUNICATION" will return notifications created from the Customer Communication API. When the source query parameter is set to "EMWS", it will return sources with a value of EMWS or sources that contain the EM blackout endpoint.
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return Notifications that match the given criteria.
   */
  Notifications getAllNotifications(String accountid, String subscriptionid,
                                    String creditentitlementserviceinstanceid,
                                    String creditserviceinstanceid,
                                    String servicetype,
                                    String serviceinstancename,
                                    String servicecategory, String textmatch,
                                    String incidentnumber,
                                    String communicationtypeid,
                                    String communicationtypeclassids,
                                    String rangestart, String rangeend,
                                    String source, String limit,
                                    String offset);

  /**
   * Get Notification identified by notificationid
   *
   * @param notificationid the Id of the notification to return
   *
   * @return Notification
   */
  Notification getNotification(String notificationid);

  /**
   * Get ALL Orders
   *
   * @return Orders
   */
  Orders getAllOrders();

  /**
   * Get All orders matching the passed criteria.(One or More )
   *
   * @param accountid The Account ID
   * @param subscriptionid - The Subscription ID
   * @param subscriptiontype - The Type of Subscription
   * @param statuses - The status of the Order
   * @param excludedstatuses Filter results for orders not containing specified statuses
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return Orders
   */
  Orders getAllOrders(String accountid, String subscriptionid,
                      String subscriptiontype, String statuses,
                      String excludedstatuses, String limit, String offset);

  /**
   * Get Order Identified by the given OrderId
   *
   * @param orderid The Id of the Order to return
   *
   * @return Order
   */
  Order getOrder(String orderid);

  /**
   * Get the list of services supported, Sort ordering is "type:asc"
   *
   * @return Services  supported
   */
  Services getAllSupportedServices();

  /**
   * Get ALL of services supported. with Limits and offset criteria passed
   *
   * @param  limit Limits the number of resources to return
   * @param offset  Offset of the resources to return
   * @param name filter by service name
   *
   * @return Services  supported
   */
  Services getAllSupportedServices(String limit, String offset, String name);

  /**
   * Get Supported Service Identified by the passed serviceid
   * @param serviceid the id of the service to return. sort  ordering is "plan:asc"
   * @return Service
   */
  Service getSupportedService(String serviceid);

  /**
   * Get ALL of the Sevice Plans of the Service identified by the passed serviceid
   * @param serviceid The id of the service whoes plans to return
   *
   * @return ServicePlans
   */
  ServicePlans getAllServicePlans(String serviceid);

  /**
   * Get the Service Plan identified by its Serviceid and Planid
   * @param serviceid The serviceid of the Plan to get
   * @param planid The Planid of the plan to get
   *
   * @return ServicePlan
   */
  ServicePlan getServicePlan(String serviceid, String planid);

  /**
   * Get ALL subscriptions Sort ordering is "subscriptionType:desc,name:asc"
   *
   * @return Subscriptions
   */
  Subscriptions getAllSubscriptions();

  /**
   * Get Subscription  identified by the subscriptionid
   *
   * @param subscriptionid the id of the subscription to return
   *
   * @return Subscription
   */
  Subscription getSubscription(String subscriptionid);

  /**
   * Get Subscription matching the passed criteria.
   *
   * @param subscriptionid the id of the subscription to return
   * @param statesrangestart start range of the states to return for each subscriptions, if both statesRangeStart and statesRangeEnd is null, only the latest available state will be returned
   * @param statesrangeend end range of the states to return for each subscriptions
   * @param aggregateduptimesrangestart startDate range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the startDate range date will be adjusted to midnight of the each subscription data-center time-zone and is inclusive, if aggregatedUpTimesRangeStart is specified and aggregatedUpTimesRangeEnd is not specified, the aggregation will be done on the day specified by aggregatedUpTimesRangeStart only.
   * @param aggregateduptimesrangeend endDate range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the endDate range date will be adjusted to midnight of the each subscription data-center time-zone and is exclusive.
   *
   * @return Subscription matched passed critria
   */
  Subscription getSubscription(String subscriptionid, String statesrangestart,
                               String statesrangeend,
                               String aggregateduptimesrangestart,
                               String aggregateduptimesrangeend);

  /**
   * Get ALL states for a Subscription Id. Sort t ordering is "startTime:asc"
   *
   * @param subscriptionid The SubscriptionId to get States for.
   *
   * @return ServiceInstanceStates
   */
  ServiceInstanceStates getAllServiceInstanceStates(String subscriptionid);

  /**
   * Get ALL states for a Subscription Id within since and until range.
   *
   * @param subscriptionid
   * @param since start range
   * @param until end range
   * @param componentid component id filter, use '-' for returning only resources with no component set.
   * @param componenttype  component type filter.

   *
   * @return ServiceInstanceStates
   */
  ServiceInstanceStates getAllServiceInstanceStates(String subscriptionid,
                                                    String since,
                                                    String componentid,
                                                    String componenttype,
                                                    String until);

  /**
   * Get ALL uptimes for Services with the passed subscriptionid
   *
   * @param subscriptionid the subscriptionid to check updtimes for
   *
   * @return UpTimes.
   */
  UpTimes getAllUpTimes(String subscriptionid);

  /**
   * Get ALL uptimes for a Services with the passed subscriptionid and the given range
   *
   * @param subscriptionid the subscriptionid to check updtimes for
   * @param componentid component id filter, use '-' for returning only resources with no component set.
   * @param componenttype component type filter.
   * @param rangestart  Optional start range mutually exclusive with aggregatedRange parameters
   * @param rangeend    Optional end range mutually exclusive with aggregatedRange parameters
   * @param aggregatedrangestart  start range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the start range date will be adjusted to midnight of the each subscription data-center time-zone and is inclusive, if aggregatedUpTimesRangeStart is specified and aggregatedUpTimesRangeEnd is not specified, the aggregation will be done on the day specified by aggregatedUpTimesRangeStart only.
   * @param aggregationinterval  Describe the interval to use when aggregating the uptimes, can be ALL, MONTHLY or NONE
   * @param aggregatedrangeend  end range of the uptimes aggregation, only floating dates in the format YYYY-MM-DD are supported, the end range date will be adjusted to midnight of the each subscription data-center time-zone and is exclusive.
   *
   * @return UpTimes.
   */
  UpTimes getAllUpTimes(String subscriptionid, String componentid,
                        String componenttype, String rangestart,
                        String rangeend, String aggregatedrangestart,
                        String aggregatedrangeend, String aggregationinterval);

  /**
   * Get ALL Usages for the passed subscription id. Sort  Criteria is "groupName:asc,displayOrder:asc,startTime:asc,endTime:asc"
   *
   * @param subscriptionid the subscriptionid to check usages for
   *
   * @return Usages
   */
  Usages getAllUsages(String subscriptionid);

  /**
   * Get ALL Usages for subscriptionid id with other criteria.
   *
   * @param subscriptionid the subscriptionid to check usages for
   * @param rangestart Start range of usages to return
   * @param rangeend End range of usages to return
   * @param specificinfocontents  Names of the specific info used to filter subscription usages, default=NULL, supported=NULL,APPLICATION_NAME
   * @param specificinfos  Names of specific information instances used to filter subscription usages
   *
   * @return Usages that match the passed critria
   */
  Usages getAllUsages(String subscriptionid, String rangestart,
                      String rangeend, String specificinfocontents,
                      String specificinfos);

  /**
   * Get ALL  timezones, Sort ordering is "utcOffsetRaw:asc"
   *
   * @return TimeZones
   */
  TimeZones getAllTimeZones();

  /**
   * Get ALL timezones with the passed criteria. Can pass on or more criteria
   *
   * @param common Whehther to return only common/uncommon timezones
   * @param tzid tzId of a specific timezone
   * @param countryid Only return timezones corresponding to a specific country.
   * @param includedeprecated Whehther to include deprecated timezones
   * @param limit Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return TimeZones matching the passed criteria
   */
  TimeZones getAllTimeZones(String common, String tzid, String countryid,
                            String includedeprecated, String limit,
                            String offset);

  /**
   * Get timezone via its unique base64UrlId
   * @param base64urlid the base64 id to get the timezone for
   * @return TimeZone
   */
  TimeZone getTimeZone(String base64urlid);

  /**
   * Get Handle on the Tas REST Client Utility Interface
   * Which has Helper Methods for Printing and Streaming Model Objects and Date Conversion among Others.
   *
   * @return TasCommonRestUtil
   */
  TasCommonRestUtil getUtilityHandle();

}
