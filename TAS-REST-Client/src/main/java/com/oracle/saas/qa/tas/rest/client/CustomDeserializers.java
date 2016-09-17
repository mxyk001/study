package com.oracle.saas.qa.tas.rest.client;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.deser.std.EnumDeserializer;
import org.codehaus.jackson.map.module.SimpleDeserializers;
import org.codehaus.jackson.map.util.EnumResolver;


/**
 *  Custom CustomDeserialize for JSON from Enum to ignore case.
 *
 *  @author Haris Shah
 */
final class CustomDeserializers extends SimpleDeserializers {

  @SuppressWarnings("unchecked")
  @Override
  public JsonDeserializer<?> findEnumDeserializer(Class<?> c,
                                                  DeserializationConfig deserializationConfig,
                                                  BeanDescription beanDescription,
                                                  BeanProperty beanProperty) throws JsonMappingException {
    return createDeserializer((Class<Enum>)c);
  }

  private <T extends Enum<T>> JsonDeserializer<?> createDeserializer(Class<T> enumCls) {
    T[] enumValues = enumCls.getEnumConstants();
    HashMap<String, T> map = createEnumValuesMap(enumValues);
    return new EnumDeserializer(new EnumCaseInsensitiveResolver<T>(enumCls,
                                                                   enumValues,
                                                                   map));
  }

  private <T extends Enum<T>> HashMap<String, T> createEnumValuesMap(T[] enumValues) {
    HashMap<String, T> map = new HashMap<String, T>();
    // from last to first, so that in case of duplicate values, first wins
    for (int i = enumValues.length; --i >= 0; ) {
      T e = enumValues[i];
      map.put(e.toString(), e);
    }
    return map;
  }


  public static class EnumCaseInsensitiveResolver<T extends Enum<T>> extends EnumResolver<T> {
    protected EnumCaseInsensitiveResolver(Class<T> enumClass, T[] enums,
                                          HashMap<String, T> map) {
      super(enumClass, enums, map);
    }

    @Override
    public T findEnum(String key) {
      for (Map.Entry<String, T> entry : _enumsById.entrySet()) {
        String val = getValue(entry.getValue());
        if (entry.getKey().equalsIgnoreCase(key) ||
            (val != null && key.equalsIgnoreCase(val))) { // magic line <--
          return entry.getValue();
        }
      }
      return null;
    }

    private String getValue(T obj) {
      String val = null;
      try {
        Method m = obj.getClass().getDeclaredMethod("value", null);
        val = (String)m.invoke(obj, null);
      } catch (Exception e) {
      }
      return val;
    }

  }
}

