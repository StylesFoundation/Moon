package org.lwjgl.system.macosx;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;

public abstract class CGEventTapCallBack extends Callback implements CGEventTapCallBackI {
   public static CGEventTapCallBack create(long functionPointer) {
      CGEventTapCallBackI instance = Callback.get(functionPointer);
      return instance instanceof CGEventTapCallBack ? (CGEventTapCallBack)instance : new CGEventTapCallBack.Container(functionPointer, instance);
   }

   @Nullable
   public static CGEventTapCallBack createSafe(long functionPointer) {
      return functionPointer == 0L ? null : create(functionPointer);
   }

   public static CGEventTapCallBack create(CGEventTapCallBackI instance) {
      return instance instanceof CGEventTapCallBack ? (CGEventTapCallBack)instance : new CGEventTapCallBack.Container(instance.address(), instance);
   }

   protected CGEventTapCallBack() {
      super(CIF);
   }

   CGEventTapCallBack(long functionPointer) {
      super(functionPointer);
   }

   private static final class Container extends CGEventTapCallBack {
      private final CGEventTapCallBackI delegate;

      Container(long functionPointer, CGEventTapCallBackI delegate) {
         super(functionPointer);
         this.delegate = delegate;
      }

      @Override
      public long invoke(long proxy, int type, long event, long userInfo) {
         return this.delegate.invoke(proxy, type, event, userInfo);
      }
   }
}
