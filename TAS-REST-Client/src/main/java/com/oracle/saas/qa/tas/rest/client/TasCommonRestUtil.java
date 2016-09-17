package com.oracle.saas.qa.tas.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;


/**
 * TAS REST Client Utility Methods.
 * Has Helper Methods for Printing and Streaming Model Objects and Date Conversion among Others.
 * You get a  Handle to This Utilility voa TasCommonRestClient Interface.
 *
 * @author Haris Shah.
 *
 * @see com.oracle.saas.qa.tas.rest.client.TasCommonRestClient#getUtilityHandle()
 */
public interface TasCommonRestUtil {


  /**
   * Utility method to Convert a TAS Rest API Model Object to its String Representation.
   * Primary used or Debugging.
   *
   * @param tasRestModelObject The TAS REST Model Object to convert to String.
   *
   * @return String Representation of TAS REST Model Object.
   *
   */
  String toString(Object tasRestModelObject);


  /**
   * Utility method to Convert a TAS Rest API Model Object to its XML String Representation.
   * Primary used or Debugging.
   *<p>
   * Note : the XML TAS Model XML Is Wrapped in a XMLRoot Object.
   *
   * @param tasRestModelObject The TAS REST Model Object to convert to its XML Representation.
   *
   * @return String XML Representation of TAS REST Model Object Wrapped in an XMLRoot Object
   *
   */
  String toXMLString(Object tasRestModelObject);

  /**
   * Utility method to Convert a TAS Rest API Model Object to its JSON String Representation.
   * Primary used or Debugging.
   *
   * @param tasRestModelObject The TAS REST Model Object to convert to its JSON Representation.
   *
   * @return String JSON Representation of TAS REST Model Object
   *
   */
  String toJSONString(Object tasRestModelObject) throws IOException,
                                                        JsonGenerationException,
                                                        JsonMappingException;


  /**
   * Utility method to Stream TAS Rest API Model Object as XML to the Passed OutputStream.
   *<p>
   * Note : the XML TAS Model XML Is Wrapped in a XMLRoot Object.
   *
   * @param tasRestModelObject The TAS REST Model Object to convert to its XML Representation.
   * @param outputStream The Stream to write the XML to.
   *
   */
  void toXMLStream(Object tasRestModelObject,
                   OutputStream outputStream) throws IOException;


  /**
   * Utility method to Stream TAS Rest API Model Object as JSON to the Passed OutputStream.
   *
   * @param tasRestModelObject The TAS REST Model Object to convert to its JSON Representation.
   * @param outputStream The Stream to write the JSON to.
   *
   */
  void toJSONStream(Object tasRestModelObject,
                    OutputStream outputStream) throws IOException;


  /**
   * Utility method to Convert a TAS REST API Model Object XML Created by
   * {@link #toXMLString(Object tasRestModelObject)} or  {@link #toXMLStream(Object tasRestModelObject,OutputStream outputStream)} method
   * to its Object Representation.
   *<p>
   * Note : the XML Is Wrapped in a XMLRoot Object.
   *
   * @param theXMLString  XML Representation of TAS REST Model Object Wrapped in an XMLRoot Object
   * @param clazz Class Of the TAS Model Object to Return
   *
   * @return The Tas ModelObject.
   *
   */
  <T> T fromXMLString(String theXMLString, Class<T> clazz);


  /**
   * Utility method to Convert a TAS REST API Model Object JSON Created by
   * {@link #toJSONString(Object tasRestModelObject)} or  {@link #toJSONStream(Object tasRestModelObject,OutputStream outputStream)} method
   * to its Object Representation.
   *
   * @param theJSONString  JSON Representation of TAS REST Model Object
   * @param clazz Class Of the TAS Model Object to Return
   *
   * @return The Tas ModelObject.
   *
   */
  <T> T fromJSONString(String theJSONString,
                       Class<T> clazz) throws IOException, JsonParseException,
                                              JsonMappingException;


  /**
   * Utility method to Convert a TAS REST API Model XML Stream Created by
   * {@link #toXMLString(Object tasRestModelObject)} or  {@link #toXMLStream(Object tasRestModelObject,OutputStream outputStream)} method
   * to its Object Representation.
   *<p>
   * Note : the XML TAS Model XML Is Wrapped in a XMLRoot Object.
   *
   * @param inputStream  The  inputStream to read input XML from.
   * @param clazz Class Of the TAS Model Object to Return
   *
   * @return The Tas ModelObject.
   *
   */
  <T> T fromXMLStream(InputStream inputStream,
                      Class<T> clazz) throws IOException;


  /**
   * Utility method to Convert a TAS REST API Model JSON Stream Created by
   * {@link #toJSONString(Object tasRestModelObject)} or  {@link #toJSONStream(Object tasRestModelObject,OutputStream outputStream)} method
   * to its Object Representation.
   *
   * @param inputStream  The  inputStream to read input JSON from.
   * @param clazz Class Of the TAS Model Object to Return
   *
   * @return The Tas ModelObject.
   *
   */
  <T> T fromJSONStream(InputStream inputStream,
                       Class<T> clazz) throws IOException, JsonParseException,
                                              JsonMappingException;


  /**
   * Utlity Method to Convert a Date to XMLGregorianCalendar.
   *
   * @param date required
   * @return XMLGregorianCalendar
   * @throws DatatypeConfigurationException
   */
  XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException;


  /**
   *
   * Utlity Method to Convert a Calendar to XMLGregorianCalendar.
   *
   * @param calendar required
   * @return XMLGregorianCalendar
   */
  XMLGregorianCalendar toXMLGregorianCalendar(Calendar calendar) throws DatatypeConfigurationException;

  /**
   *
   * Utlity Method to Convert a XMLGregorianCalendar to Calendar.
   *
   * @param xmlGregorianCalendar required
   * @return Calendar
   */
  Calendar toCalendar(XMLGregorianCalendar xmlGregorianCalendar);

  /**
   *
   * Utlity Method to Convert a XMLGregorianCalendar to Date.
   *
   * @param xmlGregorianCalendar required
   * @return Date
   */
  Date toDate(XMLGregorianCalendar xmlGregorianCalendar);
}
