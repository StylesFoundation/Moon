package org.lwjgl.system.macosx;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.CLongBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;

public class CoreGraphics {
   private static final SharedLibrary COREGRAPHICS = Library.loadNative(CoreGraphics.class, "org.lwjgl", "/System/Library/Frameworks/CoreGraphics.framework");
   public static final int kCGErrorSuccess = 0;
   public static final int kCGErrorFailure = 1000;
   public static final int kCGErrorIllegalArgument = 1001;
   public static final int kCGErrorInvalidConnection = 1002;
   public static final int kCGErrorInvalidContext = 1003;
   public static final int kCGErrorCannotComplete = 1004;
   public static final int kCGErrorNotImplemented = 1006;
   public static final int kCGErrorRangeCheck = 1007;
   public static final int kCGErrorTypeCheck = 1008;
   public static final int kCGErrorInvalidOperation = 1010;
   public static final int kCGErrorNoneAvailable = 1011;
   public static final int kCGEventNull = 0;
   public static final int kCGEventLeftMouseDown = 1;
   public static final int kCGEventLeftMouseUp = 2;
   public static final int kCGEventRightMouseDown = 3;
   public static final int kCGEventRightMouseUp = 4;
   public static final int kCGEventMouseMoved = 5;
   public static final int kCGEventLeftMouseDragged = 6;
   public static final int kCGEventRightMouseDragged = 7;
   public static final int kCGEventKeyDown = 10;
   public static final int kCGEventKeyUp = 11;
   public static final int kCGEventFlagsChanged = 12;
   public static final int kCGEventScrollWheel = 22;
   public static final int kCGEventTabletPointer = 23;
   public static final int kCGEventTabletProximity = 24;
   public static final int kCGEventOtherMouseDown = 25;
   public static final int kCGEventOtherMouseUp = 26;
   public static final int kCGEventOtherMouseDragged = 27;
   public static final int kCGEventTapDisabledByTimeout = -2;
   public static final int kCGEventTapDisabledByUserInput = -1;
   public static final int kCGMouseButtonLeft = 0;
   public static final int kCGMouseButtonRight = 1;
   public static final int kCGMouseButtonCenter = 2;
   public static final int kCGHIDEventTap = 0;
   public static final int kCGSessionEventTap = 1;
   public static final int kCGAnnotatedSessionEventTap = 2;
   public static final int kCGScrollEventUnitPixel = 0;
   public static final int kCGScrollEventUnitLine = 1;
   public static final int kCGMouseEventNumber = 0;
   public static final int kCGMouseEventClickState = 1;
   public static final int kCGMouseEventPressure = 2;
   public static final int kCGMouseEventButtonNumber = 3;
   public static final int kCGMouseEventDeltaX = 4;
   public static final int kCGMouseEventDeltaY = 5;
   public static final int kCGMouseEventInstantMouser = 6;
   public static final int kCGMouseEventSubtype = 7;
   public static final int kCGKeyboardEventAutorepeat = 8;
   public static final int kCGKeyboardEventKeycode = 9;
   public static final int kCGKeyboardEventKeyboardType = 10;
   public static final int kCGScrollWheelEventDeltaAxis1 = 11;
   public static final int kCGScrollWheelEventDeltaAxis2 = 12;
   public static final int kCGScrollWheelEventDeltaAxis3 = 13;
   public static final int kCGScrollWheelEventFixedPtDeltaAxis1 = 93;
   public static final int kCGScrollWheelEventFixedPtDeltaAxis2 = 94;
   public static final int kCGScrollWheelEventFixedPtDeltaAxis3 = 95;
   public static final int kCGScrollWheelEventPointDeltaAxis1 = 96;
   public static final int kCGScrollWheelEventPointDeltaAxis2 = 97;
   public static final int kCGScrollWheelEventPointDeltaAxis3 = 98;
   public static final int kCGScrollWheelEventScrollPhase = 99;
   public static final int kCGScrollWheelEventScrollCount = 100;
   public static final int kCGScrollWheelEventMomentumPhase = 123;
   public static final int kCGScrollWheelEventInstantMouser = 14;
   public static final int kCGTabletEventPointX = 15;
   public static final int kCGTabletEventPointY = 16;
   public static final int kCGTabletEventPointZ = 17;
   public static final int kCGTabletEventPointButtons = 18;
   public static final int kCGTabletEventPointPressure = 19;
   public static final int kCGTabletEventTiltX = 20;
   public static final int kCGTabletEventTiltY = 21;
   public static final int kCGTabletEventRotation = 22;
   public static final int kCGTabletEventTangentialPressure = 23;
   public static final int kCGTabletEventDeviceID = 24;
   public static final int kCGTabletEventVendor1 = 25;
   public static final int kCGTabletEventVendor2 = 26;
   public static final int kCGTabletEventVendor3 = 27;
   public static final int kCGTabletProximityEventVendorID = 28;
   public static final int kCGTabletProximityEventTabletID = 29;
   public static final int kCGTabletProximityEventPointerID = 30;
   public static final int kCGTabletProximityEventDeviceID = 31;
   public static final int kCGTabletProximityEventSystemTabletID = 32;
   public static final int kCGTabletProximityEventVendorPointerType = 33;
   public static final int kCGTabletProximityEventVendorPointerSerialNumber = 34;
   public static final int kCGTabletProximityEventVendorUniqueID = 35;
   public static final int kCGTabletProximityEventCapabilityMask = 36;
   public static final int kCGTabletProximityEventPointerType = 37;
   public static final int kCGTabletProximityEventEnterProximity = 38;
   public static final int kCGEventTargetProcessSerialNumber = 39;
   public static final int kCGEventTargetUnixProcessID = 40;
   public static final int kCGEventSourceUnixProcessID = 41;
   public static final int kCGEventSourceUserData = 42;
   public static final int kCGEventSourceUserID = 43;
   public static final int kCGEventSourceGroupID = 44;
   public static final int kCGEventSourceStateID = 45;
   public static final int kCGScrollWheelEventIsContinuous = 88;
   public static final int kCGMouseEventWindowUnderMousePointer = 91;
   public static final int kCGMouseEventWindowUnderMousePointerThatCanHandleThisEvent = 92;
   public static final int kCGEventMouseSubtypeDefault = 0;
   public static final int kCGEventMouseSubtypeTabletPoint = 1;
   public static final int kCGEventMouseSubtypeTabletProximity = 2;

   public static SharedLibrary getLibrary() {
      return COREGRAPHICS;
   }

   protected CoreGraphics() {
      throw new UnsupportedOperationException();
   }

   @NativeType("CFTypeID")
   public static long CGEventGetTypeID() {
      long __functionAddress = CoreGraphics.Functions.EventGetTypeID;
      return JNI.invokeJ(__functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreate(@NativeType("CGEventSourceRef") long source) {
      long __functionAddress = CoreGraphics.Functions.EventCreate;
      return JNI.invokePP(source, __functionAddress);
   }

   @NativeType("CFDataRef")
   public static long CGEventCreateData(@NativeType("CFAllocatorRef") long allocator, @NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventCreateData;
      return JNI.invokePPP(allocator, event, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateFromData(@NativeType("CFAllocatorRef") long allocator, @NativeType("CFDataRef") long data) {
      long __functionAddress = CoreGraphics.Functions.EventCreateFromData;
      return JNI.invokePPP(allocator, data, __functionAddress);
   }

   public static native long nCGEventCreateMouseEvent(long var0, int var2, long var3, int var5, long var6);

   public static long nCGEventCreateMouseEvent(long source, int mouseType, long mouseCursorPosition, int mouseButton) {
      long __functionAddress = CoreGraphics.Functions.EventCreateMouseEvent;
      return nCGEventCreateMouseEvent(source, mouseType, mouseCursorPosition, mouseButton, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateMouseEvent(
      @NativeType("CGEventSourceRef") long source,
      @NativeType("CGEventType") int mouseType,
      CGPoint mouseCursorPosition,
      @NativeType("CGMouseButton") int mouseButton
   ) {
      return nCGEventCreateMouseEvent(source, mouseType, mouseCursorPosition.address(), mouseButton);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateKeyboardEvent(
      @NativeType("CGEventSourceRef") long source, @NativeType("CGKeyCode") short virtualKey, @NativeType("bool") boolean keyDown
   ) {
      long __functionAddress = CoreGraphics.Functions.EventCreateKeyboardEvent;
      return JNI.invokePP(source, virtualKey, keyDown, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateScrollWheelEvent(
      @NativeType("CGEventSourceRef") long source,
      @NativeType("CGScrollEventUnit") int units,
      @NativeType("uint32_t") int wheelCount,
      @NativeType("int32_t") int wheel1
   ) {
      long __functionAddress = CoreGraphics.Functions.EventCreateScrollWheelEvent;
      return JNI.invokePP(source, units, wheelCount, wheel1, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateScrollWheelEvent(
      @NativeType("CGEventSourceRef") long source, @NativeType("CGScrollEventUnit") int units, @NativeType("int32_t") int wheel1
   ) {
      long __functionAddress = CoreGraphics.Functions.EventCreateScrollWheelEvent;
      return JNI.invokePP(source, units, 1, wheel1, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateScrollWheelEvent2(
      @NativeType("CGEventSourceRef") long source,
      @NativeType("CGScrollEventUnit") int units,
      @NativeType("uint32_t") int wheelCount,
      @NativeType("int32_t") int wheel1,
      @NativeType("int32_t") int wheel2,
      @NativeType("int32_t") int wheel3
   ) {
      long __functionAddress = CoreGraphics.Functions.EventCreateScrollWheelEvent2;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.invokePP(source, units, wheelCount, wheel1, wheel2, wheel3, __functionAddress);
   }

   @NativeType("CGEventRef")
   public static long CGEventCreateCopy(@NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventCreateCopy;
      return JNI.invokePP(event, __functionAddress);
   }

   @NativeType("CGEventSourceRef")
   public static long CGEventCreateSourceFromEvent(@NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventCreateSourceFromEvent;
      return JNI.invokePP(event, __functionAddress);
   }

   public static void CGEventSetSource(@NativeType("CGEventRef") long event, @NativeType("CGEventSourceRef") long source) {
      long __functionAddress = CoreGraphics.Functions.EventSetSource;
      JNI.invokePPV(event, source, __functionAddress);
   }

   @NativeType("CGEventType")
   public static int CGEventGetType(@NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventGetType;
      return JNI.invokePI(event, __functionAddress);
   }

   public static void CGEventSetType(@NativeType("CGEventRef") long event, @NativeType("CGEventType") int type) {
      long __functionAddress = CoreGraphics.Functions.EventSetType;
      JNI.invokePV(event, type, __functionAddress);
   }

   @NativeType("CGEventTimestamp")
   public static long CGEventGetTimestamp(@NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventGetTimestamp;
      return JNI.invokePJ(event, __functionAddress);
   }

   public static void CGEventSetTimestamp(@NativeType("CGEventRef") long event, @NativeType("CGEventTimestamp") long timestamp) {
      long __functionAddress = CoreGraphics.Functions.EventSetTimestamp;
      JNI.invokePJV(event, timestamp, __functionAddress);
   }

   public static native void nCGEventGetLocation(long var0, long var2, long var4);

   public static void nCGEventGetLocation(long event, long __result) {
      long __functionAddress = CoreGraphics.Functions.EventGetLocation;
      nCGEventGetLocation(event, __functionAddress, __result);
   }

   public static CGPoint CGEventGetLocation(@NativeType("CGEventRef") long event, CGPoint __result) {
      nCGEventGetLocation(event, __result.address());
      return __result;
   }

   public static native void nCGEventGetUnflippedLocation(long var0, long var2, long var4);

   public static void nCGEventGetUnflippedLocation(long event, long __result) {
      long __functionAddress = CoreGraphics.Functions.EventGetUnflippedLocation;
      nCGEventGetUnflippedLocation(event, __functionAddress, __result);
   }

   public static CGPoint CGEventGetUnflippedLocation(@NativeType("CGEventRef") long event, CGPoint __result) {
      nCGEventGetUnflippedLocation(event, __result.address());
      return __result;
   }

   public static native void nCGEventSetLocation(long var0, long var2, long var4);

   public static void nCGEventSetLocation(long event, long location) {
      long __functionAddress = CoreGraphics.Functions.EventSetLocation;
      nCGEventSetLocation(event, location, __functionAddress);
   }

   public static void CGEventSetLocation(@NativeType("CGEventRef") long event, CGPoint location) {
      nCGEventSetLocation(event, location.address());
   }

   @NativeType("CGEventFlags")
   public static long CGEventGetFlags(@NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventGetFlags;
      return JNI.invokePJ(event, __functionAddress);
   }

   public static void CGEventSetFlags(@NativeType("CGEventRef") long event, @NativeType("CGEventFlags") long flags) {
      long __functionAddress = CoreGraphics.Functions.EventSetFlags;
      JNI.invokePJV(event, flags, __functionAddress);
   }

   public static void nCGEventKeyboardGetUnicodeString(long event, long maxStringLength, long actualStringLength, long unicodeString) {
      long __functionAddress = CoreGraphics.Functions.EventKeyboardGetUnicodeString;
      JNI.invokePNPPV(event, maxStringLength, actualStringLength, unicodeString, __functionAddress);
   }

   public static void CGEventKeyboardGetUnicodeString(
      @NativeType("CGEventRef") long event,
      @Nullable @NativeType("UniCharCount *") CLongBuffer actualStringLength,
      @Nullable @NativeType("UniChar *") ShortBuffer unicodeString
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(actualStringLength, 1);
      }

      nCGEventKeyboardGetUnicodeString(
         event, Checks.remainingSafe(unicodeString), MemoryUtil.memAddressSafe(actualStringLength), MemoryUtil.memAddressSafe(unicodeString)
      );
   }

   public static void nCGEventKeyboardSetUnicodeString(long event, long stringLength, long unicodeString) {
      long __functionAddress = CoreGraphics.Functions.EventKeyboardSetUnicodeString;
      JNI.invokePNPV(event, stringLength, unicodeString, __functionAddress);
   }

   public static void CGEventKeyboardSetUnicodeString(@NativeType("CGEventRef") long event, @NativeType("UniChar const *") ShortBuffer unicodeString) {
      nCGEventKeyboardSetUnicodeString(event, unicodeString.remaining(), MemoryUtil.memAddress(unicodeString));
   }

   @NativeType("int64_t")
   public static long CGEventGetIntegerValueField(@NativeType("CGEventRef") long event, @NativeType("CGEventField") int field) {
      long __functionAddress = CoreGraphics.Functions.EventGetIntegerValueField;
      return JNI.invokePJ(event, field, __functionAddress);
   }

   public static void CGEventSetIntegerValueField(
      @NativeType("CGEventRef") long event, @NativeType("CGEventField") int field, @NativeType("int64_t") long value
   ) {
      long __functionAddress = CoreGraphics.Functions.EventSetIntegerValueField;
      JNI.invokePJV(event, field, value, __functionAddress);
   }

   public static double CGEventGetDoubleValueField(@NativeType("CGEventRef") long event, @NativeType("CGEventField") int field) {
      long __functionAddress = CoreGraphics.Functions.EventGetDoubleValueField;
      return JNI.invokePD(event, field, __functionAddress);
   }

   public static void CGEventSetDoubleValueField(@NativeType("CGEventRef") long event, @NativeType("CGEventField") int field, double value) {
      long __functionAddress = CoreGraphics.Functions.EventSetDoubleValueField;
      JNI.invokePV(event, field, value, __functionAddress);
   }

   public static long nCGEventTapCreate(int tap, int place, int options, long eventsOfInterest, long callback, long userInfo) {
      long __functionAddress = CoreGraphics.Functions.EventTapCreate;
      return JNI.invokeJPPP(tap, place, options, eventsOfInterest, callback, userInfo, __functionAddress);
   }

   @NativeType("CFMachPortRef")
   public static long CGEventTapCreate(
      @NativeType("CGEventTapLocation") int tap,
      @NativeType("CGEventTapPlacement") int place,
      @NativeType("CGEventTapOptions") int options,
      @NativeType("CGEventMask") long eventsOfInterest,
      @NativeType("CGEventRef (*) (CGEventTapProxy, CGEventType, CGEventRef, void *)") CGEventTapCallBackI callback,
      @NativeType("void *") long userInfo
   ) {
      return nCGEventTapCreate(tap, place, options, eventsOfInterest, callback.address(), userInfo);
   }

   public static long nCGEventTapCreateForPid(long pid, int place, int options, long eventsOfInterest, long callback, long userInfo) {
      long __functionAddress = CoreGraphics.Functions.EventTapCreateForPid;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pid);
      }

      return JNI.invokePJPPP(pid, place, options, eventsOfInterest, callback, userInfo, __functionAddress);
   }

   @NativeType("CFMachPortRef")
   public static long CGEventTapCreateForPid(
      @NativeType("pid_t") long pid,
      @NativeType("CGEventTapPlacement") int place,
      @NativeType("CGEventTapOptions") int options,
      @NativeType("CGEventMask") long eventsOfInterest,
      @NativeType("CGEventRef (*) (CGEventTapProxy, CGEventType, CGEventRef, void *)") CGEventTapCallBackI callback,
      @NativeType("void *") long userInfo
   ) {
      return nCGEventTapCreateForPid(pid, place, options, eventsOfInterest, callback.address(), userInfo);
   }

   public static void CGEventTapEnable(@NativeType("CFMachPortRef") long tap, @NativeType("bool") boolean enable) {
      long __functionAddress = CoreGraphics.Functions.EventTapEnable;
      if (Checks.CHECKS) {
         Checks.check(tap);
      }

      JNI.invokePV(tap, enable, __functionAddress);
   }

   @NativeType("bool")
   public static boolean CGEventTapIsEnabled(@NativeType("CFMachPortRef") long tap) {
      long __functionAddress = CoreGraphics.Functions.EventTapIsEnabled;
      if (Checks.CHECKS) {
         Checks.check(tap);
      }

      return JNI.invokePZ(tap, __functionAddress);
   }

   public static void CGEventTapPostEvent(@NativeType("CGEventTapProxy") long proxy, @NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventTapPostEvent;
      JNI.invokePPV(proxy, event, __functionAddress);
   }

   public static void CGEventPost(@NativeType("CGEventTapLocation") int tap, @NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventPost;
      if (Checks.CHECKS) {
         Checks.check(event);
      }

      JNI.invokePV(tap, event, __functionAddress);
   }

   public static void CGEventPostToPid(@NativeType("pid_t") long pid, @NativeType("CGEventRef") long event) {
      long __functionAddress = CoreGraphics.Functions.EventPostToPid;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pid);
      }

      JNI.invokePPV(pid, event, __functionAddress);
   }

   public static int nCGGetEventTapList(int maxNumberOfTaps, long tapList, long eventTapCount) {
      long __functionAddress = CoreGraphics.Functions.GetEventTapList;
      return JNI.invokePPI(maxNumberOfTaps, tapList, eventTapCount, __functionAddress);
   }

   @NativeType("CGError")
   public static int CGGetEventTapList(
      @Nullable @NativeType("CGEventTapInformation *") CGEventTapInformation.Buffer tapList, @Nullable @NativeType("uint32_t *") IntBuffer eventTapCount
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(eventTapCount, 1);
      }

      return nCGGetEventTapList(Checks.remainingSafe(tapList), MemoryUtil.memAddressSafe(tapList), MemoryUtil.memAddressSafe(eventTapCount));
   }

   public static void CGEventKeyboardGetUnicodeString(
      @NativeType("CGEventRef") long event,
      @Nullable @NativeType("UniCharCount *") CLongBuffer actualStringLength,
      @Nullable @NativeType("UniChar *") short[] unicodeString
   ) {
      long __functionAddress = CoreGraphics.Functions.EventKeyboardGetUnicodeString;
      if (Checks.CHECKS) {
         Checks.checkSafe(actualStringLength, 1);
      }

      JNI.invokePNPPV(event, Checks.lengthSafe(unicodeString), MemoryUtil.memAddressSafe(actualStringLength), unicodeString, __functionAddress);
   }

   public static void CGEventKeyboardSetUnicodeString(@NativeType("CGEventRef") long event, @NativeType("UniChar const *") short[] unicodeString) {
      long __functionAddress = CoreGraphics.Functions.EventKeyboardSetUnicodeString;
      JNI.invokePNPV(event, unicodeString.length, unicodeString, __functionAddress);
   }

   @NativeType("CGError")
   public static int CGGetEventTapList(
      @Nullable @NativeType("CGEventTapInformation *") CGEventTapInformation.Buffer tapList, @Nullable @NativeType("uint32_t *") int[] eventTapCount
   ) {
      long __functionAddress = CoreGraphics.Functions.GetEventTapList;
      if (Checks.CHECKS) {
         Checks.checkSafe(eventTapCount, 1);
      }

      return JNI.invokePPI(Checks.remainingSafe(tapList), MemoryUtil.memAddressSafe(tapList), eventTapCount, __functionAddress);
   }

   public static final class Functions {
      public static final long EventGetTypeID = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetTypeID");
      public static final long EventCreate = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreate");
      public static final long EventCreateData = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateData");
      public static final long EventCreateFromData = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateFromData");
      public static final long EventCreateMouseEvent = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateMouseEvent");
      public static final long EventCreateKeyboardEvent = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateKeyboardEvent");
      public static final long EventCreateScrollWheelEvent = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateScrollWheelEvent");
      public static final long EventCreateScrollWheelEvent2 = CoreGraphics.COREGRAPHICS.getFunctionAddress("CGEventCreateScrollWheelEvent2");
      public static final long EventCreateCopy = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateCopy");
      public static final long EventCreateSourceFromEvent = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventCreateSourceFromEvent");
      public static final long EventSetSource = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetSource");
      public static final long EventGetType = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetType");
      public static final long EventSetType = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetType");
      public static final long EventGetTimestamp = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetTimestamp");
      public static final long EventSetTimestamp = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetTimestamp");
      public static final long EventGetLocation = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetLocation");
      public static final long EventGetUnflippedLocation = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetUnflippedLocation");
      public static final long EventSetLocation = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetLocation");
      public static final long EventGetFlags = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetFlags");
      public static final long EventSetFlags = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetFlags");
      public static final long EventKeyboardGetUnicodeString = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventKeyboardGetUnicodeString");
      public static final long EventKeyboardSetUnicodeString = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventKeyboardSetUnicodeString");
      public static final long EventGetIntegerValueField = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetIntegerValueField");
      public static final long EventSetIntegerValueField = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetIntegerValueField");
      public static final long EventGetDoubleValueField = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventGetDoubleValueField");
      public static final long EventSetDoubleValueField = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventSetDoubleValueField");
      public static final long EventTapCreate = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventTapCreate");
      public static final long EventTapCreateForPid = CoreGraphics.COREGRAPHICS.getFunctionAddress("CGEventTapCreateForPid");
      public static final long EventTapEnable = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventTapEnable");
      public static final long EventTapIsEnabled = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventTapIsEnabled");
      public static final long EventTapPostEvent = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventTapPostEvent");
      public static final long EventPost = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGEventPost");
      public static final long EventPostToPid = CoreGraphics.COREGRAPHICS.getFunctionAddress("CGEventPostToPid");
      public static final long GetEventTapList = APIUtil.apiGetFunctionAddress(CoreGraphics.COREGRAPHICS, "CGGetEventTapList");

      private Functions() {
      }
   }
}
