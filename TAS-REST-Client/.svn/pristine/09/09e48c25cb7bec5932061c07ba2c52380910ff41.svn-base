package com.oracle.saas.qa.tas.rest.client.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;


/**
 * Dynamically Skip Test Methods as Requested and remove them from dependency list of other methods.
 *
 * @author Haris Shah.
 */
public class TasProvisioningAnnotationTransformer implements IAnnotationTransformer {

  private static final Logger logger =
    Logger.getLogger(TasProvisioningAnnotationTransformer.class.getName());

  public TasProvisioningAnnotationTransformer() {
  }


  private static class EnabledMethods {
    ITestAnnotation annotation;
    String methodName;

    EnabledMethods(ITestAnnotation annotation, String methodName) {
      this.annotation = annotation;
      this.methodName = methodName;
    }


    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (!(object instanceof
            TasProvisioningAnnotationTransformer.EnabledMethods)) {
        return false;
      }
      final TasProvisioningAnnotationTransformer.EnabledMethods other =
        (TasProvisioningAnnotationTransformer.EnabledMethods)object;
      if (!(methodName == null ? other.methodName == null :
            methodName.equals(other.methodName))) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      final int PRIME = 37;
      int result = 1;
      result =
          PRIME * result + ((methodName == null) ? 0 : methodName.hashCode());
      return result;
    }
  }
  private HashSet<EnabledMethods> enabledMethods =
    new HashSet<EnabledMethods>();


  public void transform(ITestAnnotation annotation, Class testClass,
                        Constructor testConstructor, Method testMethod) {

    if (testMethod != null) {
      logger.fine("Processing Test Method Name : " +
                  (testMethod != null ? testMethod.getName() : "NULL"));
      boolean isTestDisabled = false;
      boolean isMock = false;
      if (testMethod.getDeclaringClass().getSimpleName().equalsIgnoreCase(TasProvisioningTest.class.getSimpleName())) {
        isTestDisabled =
            TasProvisioningTest.isTestDisabled(testMethod.getName(),
                                               annotation);
      } else {
        isMock = true;
        isTestDisabled =
            TasProvisioningMockTest.isTestDisabled(testMethod.getName(),
                                                   annotation);
      }
      if (isTestDisabled) {
        annotation.setEnabled(false);
        for (EnabledMethods enabledMethod : enabledMethods) {
          deleteDisabledDependency(isMock, enabledMethod.annotation,
                                   enabledMethod.methodName,
                                   testMethod.getName());

        }
      } else {
        enabledMethods.add(new EnabledMethods(annotation,
                                              testMethod.getName()));
        deleteDisabledDependency(isMock, annotation, testMethod.getName(),
                                 null);
      }
    }
  }

  private void deleteDisabledDependency(boolean isMock,
                                        ITestAnnotation enabledMethod,
                                        String enabledTestMethodName,
                                        String disabledMethodName) {
    if (enabledMethod.getDependsOnMethods() != null) {
      List<String> dependentMethods =
        Arrays.asList(enabledMethod.getDependsOnMethods());
      dependentMethods = new ArrayList<String>(dependentMethods);
      Iterator<String> it = dependentMethods.iterator();
      boolean removedDependencies = false;
      while (it.hasNext()) {
        String dependentMethod = it.next();
        boolean removeDependency = false;
        boolean isTestDisabled = false;
        if (!isMock) {
          isTestDisabled =
              TasProvisioningTest.isTestDisabled(dependentMethod, null);
        } else {
          isTestDisabled =
              TasProvisioningMockTest.isTestDisabled(dependentMethod, null);

        }
        if (disabledMethodName != null) {
          if (dependentMethod.equalsIgnoreCase(disabledMethodName)) {
            removeDependency = true;
          }
        } else if (isTestDisabled) {
          removeDependency = true;

        }
        if (removeDependency) {
          it.remove();
          removedDependencies = true;
          logger.info("Removed Disabled Dependent Test Method : " +
                      dependentMethod + " From Enabled Test Method Name : " +
                      enabledTestMethodName);
        }
      }
      if (removedDependencies) {
        enabledMethod.setDependsOnMethods(dependentMethods.size() > 0 ?
                                          dependentMethods.toArray(new String[dependentMethods.size()]) :
                                          new String[0]);
      }
    }
  }

}
