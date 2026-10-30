package org.lwjgl.system;

import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nullable;

final class StackWalkUtil {
   private StackWalkUtil() {
   }

   static StackTraceElement[] stackWalkArray(Object[] a) {
      return (StackTraceElement[])a;
   }

   static Object stackWalkGetMethod(Class<?> after) {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

      for (int i = 3; i < stackTrace.length; i++) {
         if (!stackTrace[i].getClassName().startsWith(after.getName())) {
            return stackTrace[i];
         }
      }

      throw new IllegalStateException();
   }

   private static boolean isSameMethod(StackTraceElement a, StackTraceElement b) {
      return isSameMethod(a, b, b.getMethodName());
   }

   private static boolean isSameMethod(StackTraceElement a, StackTraceElement b, String methodName) {
      return a.getMethodName().equals(methodName) && a.getClassName().equals(b.getClassName()) && Objects.equals(a.getFileName(), b.getFileName());
   }

   private static boolean isAutoCloseable(StackTraceElement element, StackTraceElement pushed) {
      return isSameMethod(element, pushed, "$closeResource")
         ? true
         : "closeFinally".equals(element.getMethodName()) && "AutoCloseable.kt".equals(element.getFileName());
   }

   @Nullable
   static Object stackWalkCheckPop(Class<?> after, Object pushedObj) {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

      for (int i = 3; i < stackTrace.length; i++) {
         StackTraceElement element = stackTrace[i];
         if (!element.getClassName().startsWith(after.getName())) {
            StackTraceElement pushed = (StackTraceElement)pushedObj;
            if (isSameMethod(element, pushed)) {
               return null;
            }

            if (isAutoCloseable(element, pushed) && i + 1 < stackTrace.length) {
               element = stackTrace[i + 1];
               if (isSameMethod(pushed, stackTrace[i + 1])) {
                  return null;
               }
            }

            return element;
         }
      }

      throw new IllegalStateException();
   }

   static Object[] stackWalkGetTrace() {
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      int i = 3;

      while (i < stackTrace.length && stackTrace[i].getClassName().startsWith("org.lwjgl.system.Memory")) {
         i++;
      }

      return Arrays.copyOfRange(stackTrace, i, stackTrace.length);
   }
}
