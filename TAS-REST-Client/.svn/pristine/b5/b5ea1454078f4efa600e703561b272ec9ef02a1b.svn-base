package com.oracle.saas.qa.tas.rest.client;


import com.oracle.tas.rest.api.model.AccountType;
import com.oracle.tas.rest.api.model.AttributeValue;
import com.oracle.tas.rest.api.model.OperationType;
import com.oracle.tas.rest.api.model.Order;
import com.oracle.tas.rest.api.model.OrderSource;
import com.oracle.tas.rest.api.model.ServiceSizeCategoryType;
import com.oracle.tas.rest.api.model.SubscriptionType;
import com.oracle.tas.rest.api.model.TasServiceType;

import javax.xml.datatype.DatatypeConfigurationException;


/**
 * TASOrderBuilder Helper Inteface for Creating TAS Orders.
 *
 * NOTE : The Builder should be called in the Sequence Defined by the TasOrderBuilderMethodsOrder Annotation.
 *        Utility Method {@link  com.oracle.saas.qa.tas.rest.client.TasOrderBuilder#printMethodsOrder} shows the Sequence.
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
 * //_________________________________________________________________________
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
 * ServiceSizeCategoryType.BASIC,null,).
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
 *
 * @see  com.oracle.saas.qa.tas.rest.client.TasOrderBuilderMethodsOrder
 * @see  com.oracle.saas.qa.tas.rest.client.TasOrderBuilder#printMethodsOrder
 */
public interface TasOrderBuilder {

  /**
   * Utility Method that Prints the Order in which the OrderBuilder methods should be called.
   */
  public void printMethodsOrder();

  /**
   * Start a New Order Building Process Passing in the Admin Email Address
   *
   * @param adminEmailAddress The Tenant Admin Email Address
   * @param adminUserName The Tenant Admin User Name (If Null Defaults to adminEmailAddress)
   * @param csiNumber - The CSI Number for the Customer
   * @param isMeteredService - Whether this Order is For a Metered Service
   * @param isAutoComplete - Whether service Auto Completion is Turned on.
   *
   * @return TasOrderBuilder
   *
   * @throws DatatypeConfigurationException
   */
  @TasOrderBuilderMethodsOrder(methodId = 1, precedingMethodsId = { -1 })
  public TasOrderBuilder startNewOrder(String adminEmailAddress,
                                       String adminUserName, String csiNumber,
                                       boolean isMeteredService,
                                       boolean isAutoComplete) throws DatatypeConfigurationException;

  /**
   * Start Update Existing Order Building Process Passing in the Admin Email Address and OrderId of the Order to Update.
   *
   * @param adminEmailAddress The Tenant Admin Email Address
   * @param adminUserName The Tenant Admin User Name (If Null Defaults to adminEmailAddress)
   * @param orderId The Existing OrderId
   * @param csiNumber - The CSI Number for the Customer
   * @param isMeteredService - Whether this Order is For a Metered Service
   *
   * @return TasOrderBuilder
   *
   * @throws DatatypeConfigurationException
   */
  @TasOrderBuilderMethodsOrder(methodId = 2, precedingMethodsId = { -1 })
  public TasOrderBuilder startUpdateOrder(String adminEmailAddress,
                                          String adminUserName, String orderId,
                                          String csiNumber,
                                          boolean isMeteredService) throws DatatypeConfigurationException;


  /**
   * Add the Source of this Order.
   *
   * @param orderSource The Source of this Order.
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 3, precedingMethodsId = { 1 })
  public TasOrderBuilder addOrderSource(OrderSource orderSource);

  /**
   * Add the DataCenter where these Services will Run.
   *
   * @param dataCenterId The ID of the DataCenter.
   * @param dataCenterRegionId The ID of the DataCenter Region.
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 4, precedingMethodsId = { 3 })
  public TasOrderBuilder addDataCenter(String dataCenterId,
                                       String dataCenterRegionId);

  /**
   * Add the Operation of this Order.
   *
   * @param operationType The Operation to Add
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 5, precedingMethodsId = { 4 })
  public TasOrderBuilder addOperation(OperationType operationType);

  /**
   * Add the Type of Subscription and Optinally the Identity Domain Name. Identity Domain Name Param can be NULL in which case the Name will be Generated.
   *
   * @param subscriptionType The Type of Subscription
   * @param identityDomainName The Identity Domain Name. Can be NULL in which case name will be Generated.
   * @param gsiCsiNumber the CSI/GSI Number. Is Optional.
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 6, precedingMethodsId = { 5 })
  public TasOrderBuilder addSubscription(SubscriptionType subscriptionType,
                                         String identityDomainName,
                                         String gsiCsiNumber);

  /**
   * Add Account Identified with the Passed  AccountType and Name. AccountName Param can be NULL in which case the Account Name is Generated
   *
   * @param acountType Type of Account
   * @param accountId The Account ID
   * @param accountName CAN be NULL in which case the Account Name is Generated.
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 7, precedingMethodsId = { 6 })
  public TasOrderBuilder addAccount(AccountType acountType, String accountId,
                                    String accountName);

  /**
   * Added Service Identified by its ServiceType and its ServiceSizeCategory and Optionally its DisplayName and whether its Auto Enabled.Service Display Name is Generated if NULL value is Passed.Auto Enabled is Set to True if NULL value is Passed.
   *
   * @param serviceType The Type of Service to be Added
   * @param serviceSizeCategoryType The Category of Service To be Added
   * @param serviceDisplayName The Display Name of the Service. Can be NULL which case the Name is Generated.
   * @param customAttributes Any Custom Attrubutes associated with this service. Could be NULL
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 8, precedingMethodsId = { 7 })
  public TasOrderBuilder addService(TasServiceType serviceType,
                                    ServiceSizeCategoryType serviceSizeCategoryType,
                                    String serviceDisplayName,
                                    AttributeValue... customAttributes);

  /**
   * Associate Services.The Params are the Order in which the Services were added by this Order Builder.This Step of the Order Builder is OPTIONAL.
   *
   * @param addOrderOfServiceToAssociate The Add Order Number of the Service to Associate
   * @param addOrderOfServiceToAssociateWith The Add Order Number of the Service to Associate the Service To.
   *
   * @return TasOrderBuilder
   */
  @TasOrderBuilderMethodsOrder(methodId = 9, precedingMethodsId = { 8 })
  public TasOrderBuilder associateServices(int addOrderOfServiceToAssociate,
                                           int addOrderOfServiceToAssociateWith);


  /**
   *  Build the Order.
   *
   * @return Order The Order Constructed by this Order Builder
   */
  @TasOrderBuilderMethodsOrder(methodId = 10, precedingMethodsId = { 7, 2 })
  public Order build();

  /**
   * Create an Update Order to Active the Services.
   *
   * @param orderId The OrderId of the Existing Order.(Reqd)
   * @param identityDomainName The Identity Domain Name. If NULL will be generated.
   * @param creditAccountName The credit Account Name if Using Cloud Credit else NULL
   *
   * @return Order The Activation Order Constructed by this Order Builder
   */
  public Order createActivationOrder(String orderId, String identityDomainName,
                                     String creditAccountName);
}
