package com.oracle.saas.qa.tas.rest.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for the Order the Methods should be Called when Using the
 * TasOrderBuilder.
 *<p><blockquote><pre>
 * methodId -> The Id of this Method
 * precedingMethodId  -> The Ids of the Methods that Should have been invoked.
 *                       ALL or just one will depend on the context) before this Method is Invoked.
 *                       Note : A Value of <=0 Implies the method does NOT have any preceding
 *                              methods that should be called before it is invoked.
 * </pre></blockquote>
 *
 * @author Haris Shah
 *
 * @see com.oracle.saas.qa.tas.rest.client.TasOrderBuilder
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TasOrderBuilderMethodsOrder {

  int methodId();

  int[] precedingMethodsId();
}
