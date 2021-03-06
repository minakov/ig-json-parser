// Copyright 2004-present Facebook. All Rights Reserved.

package com.instagram.common.json.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * This annotation is applied to any class for which a json parser should be automatically
 * generated.
 */
@Retention(CLASS) @Target(TYPE)
public @interface JsonType {

  public static final String POSTPROCESSING_METHOD_NAME = "postprocess";
  public static final String DEFAULT_VALUE_EXTRACT_FORMATTER =
      "${subobject_helper_class}.parseFromJson(${parser_object})";

  /**
   * This annotation specifies that a method with the name specified by
   * {@link #POSTPROCESSING_METHOD_NAME} (currently "postprocess") on the class that is being
   * generated that should be called once parsing is finished.
   *
   * <p>Note that this will not be called when parsing a subclass of this JsonType. In that
   * case, you must specify postprocessingEnabled = true on the subclass, and call the super
   * method explicitly.
   */
  boolean postprocessingEnabled() default false;

  /**
   * Use the specified value extract formatter to parse this object whenever it is encountered by
   * the parser. This can be used as an 'escape hatch' to parse non-standard JSON or a way to add
   * additional default behavior to the standard parser.
   *
   * <p>This does not change the generated JsonHelper code for this object; rather it changes
   * the JsonHelper code for all objects that refer to this object. However, using this parameter
   * will change the visibility of the JsonHelper parse methods to <b>protected</b> to avoid
   * mistakes by client code which might call those methods directly by accident.
   *
   * <p>See {@link JsonField#valueExtractFormatter()} for a description of the available
   * substitutions.
   *
   * <p>Note that this will be disregarded when parsing a subclass of this JsonType.
   *
   * @return A value extract formatter
   */
  String valueExtractFormatter() default DEFAULT_VALUE_EXTRACT_FORMATTER;
}
