package com.oracle.saas.qa.tas.rest.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class SortedProperties extends Properties {
  private static final long serialVersionUID = 1L;

  @SuppressWarnings("unchecked")
  @Override
  public synchronized Enumeration<Object> keys() {
    Set<?> set = keySet();
    return (Enumeration<Object>)sortKeys((Set<String>)set);
  }

  static public Enumeration<?> sortKeys(Set<String> keySet) {
    List<String> sortedList = new ArrayList<String>();
    sortedList.addAll(keySet);
    Collections.sort(sortedList);
    return Collections.enumeration(sortedList);
  }

  @Override
  public Set<String> stringPropertyNames() {
    Set<String> tmpSet = new TreeSet<String>();
    for (Object key : keySet()) {
      tmpSet.add(key.toString());
    }
    return tmpSet;
  }

}
