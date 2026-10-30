package com.moon.scripting.documentation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataClassContainer {
   String value();

   String path();
}
