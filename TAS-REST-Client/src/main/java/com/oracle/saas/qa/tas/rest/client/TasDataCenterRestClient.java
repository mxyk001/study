package com.oracle.saas.qa.tas.rest.client;

import com.oracle.tas.rest.api.model.DataCenterVersion;
import com.oracle.tas.rest.api.model.ServiceRegistration;
import com.oracle.tas.rest.api.model.ServiceRegistrations;
import com.oracle.tas.rest.api.model.Subscriptions;

/**
 * TAS REST Client API Operations Only Supported by TAS DataCenter.
 * The Operations are in the Context of the oracleUserId and identitydomainname Passed to the Factory.
 *
 * @author Haris Shah
 */
public interface TasDataCenterRestClient extends TasCommonRestClient {


  /**
   * Get Details of TAS REST DataCenterVersion (Returns canonicalLinks to All the Resources)
   * Note : These cononicalLinks should be used if there is a need to pass any canonical
   *        Links to the TAS DataCenter REST Service. Clients Should NOT hardcode canonical Links.
   *
   * @return DataCenterVersion
   */
  DataCenterVersion getDataCenterVersion();

  /**
   * Get ALL Service Registrations for the given IdentityDomainName
   *
   * @return ServiceRegistrations
   */
  ServiceRegistrations getAllServiceRegistrations();

  /**
   * Get ALL Service Registrations matching the passed criteria
   *
   * @param associatedserviceregistrationid to find all the associated service registrations
   * @param servicetype ServiceType of any subscription(such as APEX, JAVA, CMR etc.)
   * @param serviceinstancename  Service Name used during service subscription
   * @param javaadminrole
   * @param includeassociatedserviceregistrations
   * @param limit    Limits the number of resources to return
   * @param offset Offset of the resources to return
   *
   * @return ServiceRegistrations matching the passed criteria
   */
  ServiceRegistrations getAllServiceRegistrations(String associatedserviceregistrationid,
                                                  String servicetype,
                                                  String serviceinstancename,
                                                  String javaadminrole,
                                                  String includeassociatedserviceregistrations,
                                                  String limit, String offset);


  /**
   * Get Service Registration identified with by the passed svcregistrationid
   *
   * @param svcregistrationid the Id of the ServiceRegistration to get
   *
   * @return ServiceRegistrations
   */
  ServiceRegistration getServiceRegistration(String svcregistrationid);

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
                                           String serviceinstancetype,
                                           String limit, String offset);


}
