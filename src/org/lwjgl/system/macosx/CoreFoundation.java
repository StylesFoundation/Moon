package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class CoreFoundation {
   public static final byte TRUE = 1;
   public static final byte FALSE = 0;
   public static final int kCFStringEncodingMacRoman = 0;
   public static final int kCFStringEncodingWindowsLatin1 = 1280;
   public static final int kCFStringEncodingISOLatin1 = 513;
   public static final int kCFStringEncodingNextStepLatin = 2817;
   public static final int kCFStringEncodingASCII = 1536;
   public static final int kCFStringEncodingUnicode = 256;
   public static final int kCFStringEncodingUTF8 = 134217984;
   public static final int kCFStringEncodingNonLossyASCII = 3071;
   public static final int kCFStringEncodingUTF16 = 256;
   public static final int kCFStringEncodingUTF16BE = 268435712;
   public static final int kCFStringEncodingUTF16LE = 335544576;
   public static final int kCFStringEncodingUTF32 = 201326848;
   public static final int kCFStringEncodingUTF32BE = 402653440;
   public static final int kCFStringEncodingUTF32LE = 469762304;
   public static final int kCFURLPOSIXPathStyle = 0;
   public static final int kCFURLHFSPathStyle = 1;
   public static final int kCFURLWindowsPathStyle = 2;
   public static final long kCFAllocatorDefault = kCFAllocatorDefault();
   public static final long kCFAllocatorSystemDefault = kCFAllocatorSystemDefault();
   public static final long kCFAllocatorMalloc = kCFAllocatorMalloc();
   public static final long kCFAllocatorMallocZone = kCFAllocatorMallocZone();
   public static final long kCFAllocatorNull = kCFAllocatorNull();
   public static final long kCFAllocatorUseContext = kCFAllocatorUseContext();

   protected CoreFoundation() {
      throw new UnsupportedOperationException();
   }

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorDefault();

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorSystemDefault();

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorMalloc();

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorMallocZone();

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorNull();

   @NativeType("CFAllocatorRef")
   private static native long kCFAllocatorUseContext();

   public static native long nCFRetain(long var0);

   @NativeType("CFTypeRef")
   public static long CFRetain(@NativeType("CFTypeRef") long cf) {
      if (Checks.CHECKS) {
         Checks.check(cf);
      }

      return nCFRetain(cf);
   }

   public static native void nCFRelease(long var0);

   public static void CFRelease(@NativeType("CFTypeRef") long cf) {
      if (Checks.CHECKS) {
         Checks.check(cf);
      }

      nCFRelease(cf);
   }

   public static native long nCFBundleCreate(long var0, long var2);

   @NativeType("CFBundleRef")
   public static long CFBundleCreate(@NativeType("CFAllocatorRef") long allocator, @NativeType("CFURLRef") long bundleURL) {
      if (Checks.CHECKS) {
         Checks.check(bundleURL);
      }

      return nCFBundleCreate(allocator, bundleURL);
   }

   public static native long nCFBundleGetBundleWithIdentifier(long var0);

   @NativeType("CFBundleRef")
   public static long CFBundleGetBundleWithIdentifier(@NativeType("CFStringRef") long bundleID) {
      if (Checks.CHECKS) {
         Checks.check(bundleID);
      }

      return nCFBundleGetBundleWithIdentifier(bundleID);
   }

   public static native long nCFBundleGetFunctionPointerForName(long var0, long var2);

   @NativeType("void *")
   public static long CFBundleGetFunctionPointerForName(@NativeType("CFBundleRef") long bundle, @NativeType("CFStringRef") long functionName) {
      if (Checks.CHECKS) {
         Checks.check(bundle);
         Checks.check(functionName);
      }

      return nCFBundleGetFunctionPointerForName(bundle, functionName);
   }

   public static native long nCFStringCreateWithCString(long var0, long var2, int var4);

   @NativeType("CFStringRef")
   public static long CFStringCreateWithCString(
      @NativeType("CFAllocatorRef") long allocator, @NativeType("char const *") ByteBuffer cStr, @NativeType("CFStringEncoding") int encoding
   ) {
      return nCFStringCreateWithCString(allocator, MemoryUtil.memAddress(cStr), encoding);
   }

   public static native long nCFStringCreateWithCStringNoCopy(long var0, long var2, int var4, long var5);

   @NativeType("CFStringRef")
   public static long CFStringCreateWithCStringNoCopy(
      @NativeType("CFAllocatorRef") long allocator,
      @NativeType("char const *") ByteBuffer cStr,
      @NativeType("CFStringEncoding") int encoding,
      @NativeType("CFAllocatorRef") long contentsDeallocator
   ) {
      return nCFStringCreateWithCStringNoCopy(allocator, MemoryUtil.memAddress(cStr), encoding, contentsDeallocator);
   }

   public static native long nCFURLCreateWithFileSystemPath(long var0, long var2, long var4, boolean var6);

   @NativeType("CFURLRef")
   public static long CFURLCreateWithFileSystemPath(
      @NativeType("CFAllocatorRef") long allocator,
      @NativeType("CFStringRef") long filePath,
      @NativeType("CFURLPathStyle") long pathStyle,
      @NativeType("Boolean") boolean isDirectory
   ) {
      if (Checks.CHECKS) {
         Checks.check(filePath);
      }

      return nCFURLCreateWithFileSystemPath(allocator, filePath, pathStyle, isDirectory);
   }

   static {
      Library.initialize();
   }
}
