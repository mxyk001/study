package com.oracle.saas.qa.tas.rest.client;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;


/**
 *  Context Resolver for Resloving JAXB Annotations. Specifically for Enum XmlEnumValue annotation.
 *  Used For Serilization and DeSerialization of JSON.
 *
 * @author Haris Shah
 */
@Provider
public class JAXBObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
  private ObjectMapper mapper = null;

  public JAXBObjectMapperContextResolver() {
    super();
    AnnotationIntrospector jaxb = new JaxbAnnotationIntrospector();
    AnnotationIntrospector jackson = new JacksonAnnotationIntrospector();
    AnnotationIntrospector pair =
      new AnnotationIntrospector.Pair(jaxb, jackson);
    AnnotationIntrospector.nopInstance();
    mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    mapper.setDeserializationConfig(mapper.getDeserializationConfig().withAnnotationIntrospector(pair));
    mapper.setSerializationConfig(mapper.getSerializationConfig().withAnnotationIntrospector(pair));
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                     false);
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return mapper;
  }
}
