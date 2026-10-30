package com.moon.obfuscator.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeObfuscation {
   NativeObfuscation.ProtectionMode value() default NativeObfuscation.ProtectionMode.NONE;

   NativeObfuscation.ProtectionFlag[] flags() default {};

   enum ProtectionFlag {
      VIRTUALIZE_STRINGS;
   }

   enum ProtectionMode {
      NONE,
      VIRTUALIZATION,
      MUTATION,
      ULTRA;
   }
}
