package org.lwjgl.system.jni;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;

public class JNINativeInterface {
   public static final int JNI_VERSION_1_1 = 65537;
   public static final int JNI_VERSION_1_2 = 65538;
   public static final int JNI_VERSION_1_4 = 65540;
   public static final int JNI_VERSION_1_6 = 65542;
   public static final int JNI_VERSION_1_8 = 65544;
   public static final int JNI_VERSION_9 = 589824;
   public static final int JNI_VERSION_10 = 655360;
   public static final int JNIInvalidRefType = 0;
   public static final int JNILocalRefType = 1;
   public static final int JNIGlobalRefType = 2;
   public static final int JNIWeakGlobalRefType = 3;
   public static final int JNI_FALSE = 0;
   public static final int JNI_TRUE = 1;
   public static final int JNI_OK = 0;
   public static final int JNI_ERR = -1;
   public static final int JNI_EDETACHED = -2;
   public static final int JNI_EVERSION = -3;
   public static final int JNI_ENOMEM = -4;
   public static final int JNI_EEXIST = -5;
   public static final int JNI_EINVAL = -6;
   public static final int JNI_COMMIT = 1;
   public static final int JNI_ABORT = 2;

   protected JNINativeInterface() {
      throw new UnsupportedOperationException();
   }

   @NativeType("jint")
   public static native int GetVersion();

   @NativeType("jmethodID")
   public static native long FromReflectedMethod(@NativeType("jobject") Method var0);

   @NativeType("jfieldID")
   public static native long FromReflectedField(@NativeType("jobject") Field var0);

   @Nullable
   public static native Method nToReflectedMethod(Class<?> var0, long var1, boolean var3);

   @Nullable
   @NativeType("jobject")
   public static Method ToReflectedMethod(@NativeType("jclass") Class<?> cls, @NativeType("jmethodID") long methodID, @NativeType("jboolean") boolean isStatic) {
      if (Checks.CHECKS) {
         Checks.check(methodID);
      }

      return nToReflectedMethod(cls, methodID, isStatic);
   }

   @Nullable
   public static native Field nToReflectedField(Class<?> var0, long var1, boolean var3);

   @Nullable
   @NativeType("jobject")
   public static Field ToReflectedField(@NativeType("jclass") Class<?> cls, @NativeType("jfieldID") long fieldID, @NativeType("jboolean") boolean isStatic) {
      if (Checks.CHECKS) {
         Checks.check(fieldID);
      }

      return nToReflectedField(cls, fieldID, isStatic);
   }

   @NativeType("void *")
   public static native long NewGlobalRef(@NativeType("jobject") Object var0);

   public static native void nDeleteGlobalRef(long var0);

   public static void DeleteGlobalRef(@NativeType("void *") long globalRef) {
      if (Checks.CHECKS) {
         Checks.check(globalRef);
      }

      nDeleteGlobalRef(globalRef);
   }

   public static native long nGetBooleanArrayElements(byte[] var0, long var1);

   @Nullable
   @NativeType("jboolean *")
   public static ByteBuffer GetBooleanArrayElements(@NativeType("jbooleanArray") byte[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetBooleanArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memByteBufferSafe(__result, array.length);
   }

   public static native void nReleaseBooleanArrayElements(byte[] var0, long var1, int var3);

   public static void ReleaseBooleanArrayElements(
      @NativeType("jbooleanArray") byte[] array, @NativeType("jboolean *") ByteBuffer elems, @NativeType("jint") int mode
   ) {
      nReleaseBooleanArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetByteArrayElements(byte[] var0, long var1);

   @Nullable
   @NativeType("jbyte *")
   public static ByteBuffer GetByteArrayElements(@NativeType("jbyteArray") byte[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetByteArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memByteBufferSafe(__result, array.length);
   }

   public static native void nReleaseByteArrayElements(byte[] var0, long var1, int var3);

   public static void ReleaseByteArrayElements(@NativeType("jbyteArray") byte[] array, @NativeType("jbyte *") ByteBuffer elems, @NativeType("jint") int mode) {
      nReleaseByteArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetCharArrayElements(char[] var0, long var1);

   @Nullable
   @NativeType("jchar *")
   public static ShortBuffer GetCharArrayElements(@NativeType("jcharArray") char[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetCharArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memShortBufferSafe(__result, array.length);
   }

   public static native void nReleaseCharArrayElements(char[] var0, long var1, int var3);

   public static void ReleaseCharArrayElements(@NativeType("jcharArray") char[] array, @NativeType("jchar *") ShortBuffer elems, @NativeType("jint") int mode) {
      nReleaseCharArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetShortArrayElements(short[] var0, long var1);

   @Nullable
   @NativeType("jshort *")
   public static ShortBuffer GetShortArrayElements(@NativeType("jshortArray") short[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetShortArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memShortBufferSafe(__result, array.length);
   }

   public static native void nReleaseShortArrayElements(short[] var0, long var1, int var3);

   public static void ReleaseShortArrayElements(
      @NativeType("jshortArray") short[] array, @NativeType("jshort *") ShortBuffer elems, @NativeType("jint") int mode
   ) {
      nReleaseShortArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetIntArrayElements(int[] var0, long var1);

   @Nullable
   @NativeType("jint *")
   public static IntBuffer GetIntArrayElements(@NativeType("jintArray") int[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetIntArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memIntBufferSafe(__result, array.length);
   }

   public static native void nReleaseIntArrayElements(int[] var0, long var1, int var3);

   public static void ReleaseIntArrayElements(@NativeType("jintArray") int[] array, @NativeType("jint *") IntBuffer elems, @NativeType("jint") int mode) {
      nReleaseIntArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetLongArrayElements(long[] var0, long var1);

   @Nullable
   @NativeType("jlong *")
   public static LongBuffer GetLongArrayElements(@NativeType("jlongArray") long[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetLongArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memLongBufferSafe(__result, array.length);
   }

   public static native void nReleaseLongArrayElements(long[] var0, long var1, int var3);

   public static void ReleaseLongArrayElements(@NativeType("jlongArray") long[] array, @NativeType("jlong *") LongBuffer elems, @NativeType("jint") int mode) {
      nReleaseLongArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetFloatArrayElements(float[] var0, long var1);

   @Nullable
   @NativeType("jfloat *")
   public static FloatBuffer GetFloatArrayElements(@NativeType("jfloatArray") float[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetFloatArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memFloatBufferSafe(__result, array.length);
   }

   public static native void nReleaseFloatArrayElements(float[] var0, long var1, int var3);

   public static void ReleaseFloatArrayElements(
      @NativeType("jfloatArray") float[] array, @NativeType("jfloat *") FloatBuffer elems, @NativeType("jint") int mode
   ) {
      nReleaseFloatArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native long nGetDoubleArrayElements(double[] var0, long var1);

   @Nullable
   @NativeType("jdouble *")
   public static DoubleBuffer GetDoubleArrayElements(@NativeType("jdoubleArray") double[] array, @Nullable @NativeType("jboolean *") ByteBuffer isCopy) {
      if (Checks.CHECKS) {
         Checks.checkSafe(isCopy, 1);
      }

      long __result = nGetDoubleArrayElements(array, MemoryUtil.memAddressSafe(isCopy));
      return MemoryUtil.memDoubleBufferSafe(__result, array.length);
   }

   public static native void nReleaseDoubleArrayElements(double[] var0, long var1, int var3);

   public static void ReleaseDoubleArrayElements(
      @NativeType("jdoubleArray") double[] array, @NativeType("jdouble *") DoubleBuffer elems, @NativeType("jint") int mode
   ) {
      nReleaseDoubleArrayElements(array, MemoryUtil.memAddress(elems), mode);
   }

   public static native void nGetBooleanArrayRegion(byte[] var0, int var1, int var2, long var3);

   public static void GetBooleanArrayRegion(@NativeType("jbooleanArray") byte[] array, @NativeType("jsize") int start, @NativeType("jboolean *") ByteBuffer buf) {
      nGetBooleanArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetBooleanArrayRegion(byte[] var0, int var1, int var2, long var3);

   public static void SetBooleanArrayRegion(
      @NativeType("jbooleanArray") byte[] array, @NativeType("jsize") int start, @NativeType("jboolean const *") ByteBuffer buf
   ) {
      nSetBooleanArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetByteArrayRegion(byte[] var0, int var1, int var2, long var3);

   public static void GetByteArrayRegion(@NativeType("jbyteArray") byte[] array, @NativeType("jsize") int start, @NativeType("jbyte *") ByteBuffer buf) {
      nGetByteArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetByteArrayRegion(byte[] var0, int var1, int var2, long var3);

   public static void SetByteArrayRegion(@NativeType("jbyteArray") byte[] array, @NativeType("jsize") int start, @NativeType("jbyte const *") ByteBuffer buf) {
      nSetByteArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetCharArrayRegion(char[] var0, int var1, int var2, long var3);

   public static void GetCharArrayRegion(@NativeType("jcharArray") char[] array, @NativeType("jsize") int start, @NativeType("jchar *") ShortBuffer buf) {
      nGetCharArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetCharArrayRegion(char[] var0, int var1, int var2, long var3);

   public static void SetCharArrayRegion(@NativeType("jcharArray") char[] array, @NativeType("jsize") int start, @NativeType("jchar const *") ShortBuffer buf) {
      nSetCharArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetShortArrayRegion(short[] var0, int var1, int var2, long var3);

   public static void GetShortArrayRegion(@NativeType("jshortArray") short[] array, @NativeType("jsize") int start, @NativeType("jshort *") ShortBuffer buf) {
      nGetShortArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetShortArrayRegion(short[] var0, int var1, int var2, long var3);

   public static void SetShortArrayRegion(
      @NativeType("jshortArray") short[] array, @NativeType("jsize") int start, @NativeType("jshort const *") ShortBuffer buf
   ) {
      nSetShortArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetIntArrayRegion(int[] var0, int var1, int var2, long var3);

   public static void GetIntArrayRegion(@NativeType("jintArray") int[] array, @NativeType("jsize") int start, @NativeType("jint *") IntBuffer buf) {
      nGetIntArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetIntArrayRegion(int[] var0, int var1, int var2, long var3);

   public static void SetIntArrayRegion(@NativeType("jintArray") int[] array, @NativeType("jsize") int start, @NativeType("jint const *") IntBuffer buf) {
      nSetIntArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetLongArrayRegion(long[] var0, int var1, int var2, long var3);

   public static void GetLongArrayRegion(@NativeType("jlongArray") long[] array, @NativeType("jsize") int start, @NativeType("jlong *") LongBuffer buf) {
      nGetLongArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetLongArrayRegion(long[] var0, int var1, int var2, long var3);

   public static void SetLongArrayRegion(@NativeType("jlongArray") long[] array, @NativeType("jsize") int start, @NativeType("jlong const *") LongBuffer buf) {
      nSetLongArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetFloatArrayRegion(float[] var0, int var1, int var2, long var3);

   public static void GetFloatArrayRegion(@NativeType("jfloatArray") float[] array, @NativeType("jsize") int start, @NativeType("jfloat *") FloatBuffer buf) {
      nGetFloatArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetFloatArrayRegion(float[] var0, int var1, int var2, long var3);

   public static void SetFloatArrayRegion(
      @NativeType("jfloatArray") float[] array, @NativeType("jsize") int start, @NativeType("jfloat const *") FloatBuffer buf
   ) {
      nSetFloatArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nGetDoubleArrayRegion(double[] var0, int var1, int var2, long var3);

   public static void GetDoubleArrayRegion(
      @NativeType("jdoubleArray") double[] array, @NativeType("jsize") int start, @NativeType("jdouble *") DoubleBuffer buf
   ) {
      nGetDoubleArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native void nSetDoubleArrayRegion(double[] var0, int var1, int var2, long var3);

   public static void SetDoubleArrayRegion(
      @NativeType("jdoubleArray") double[] array, @NativeType("jsize") int start, @NativeType("jdouble const *") DoubleBuffer buf
   ) {
      nSetDoubleArrayRegion(array, start, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static native int nRegisterNatives(Class<?> var0, long var1, int var3);

   @NativeType("jint")
   public static int RegisterNatives(@NativeType("jclass") Class<?> targetClass, @NativeType("JNINativeMethod const *") JNINativeMethod.Buffer methods) {
      if (Checks.CHECKS) {
         Struct.validate(methods.address(), methods.remaining(), JNINativeMethod.SIZEOF, JNINativeMethod::validate);
      }

      return nRegisterNatives(targetClass, methods.address(), methods.remaining());
   }

   @NativeType("jint")
   public static native int UnregisterNatives(@NativeType("jclass") Class<?> var0);

   public static native int nGetJavaVM(long var0);

   @NativeType("jint")
   public static int GetJavaVM(@NativeType("JavaVM **") PointerBuffer vm) {
      if (Checks.CHECKS) {
         Checks.check(vm, 1);
      }

      return nGetJavaVM(MemoryUtil.memAddress(vm));
   }

   public static native void nGetStringRegion(String var0, int var1, int var2, long var3);

   public static void GetStringRegion(@NativeType("jstring") String str, @NativeType("jsize") int start, @NativeType("jchar *") ByteBuffer buf) {
      nGetStringRegion(str, start, buf.remaining() >> 1, MemoryUtil.memAddress(buf));
   }

   public static native void nGetStringUTFRegion(String var0, int var1, int var2, long var3);

   public static void GetStringUTFRegion(
      @NativeType("jstring") String str, @NativeType("jsize") int start, @NativeType("jsize") int len, @NativeType("char *") ByteBuffer buf
   ) {
      if (Checks.CHECKS) {
         Checks.check(buf, len);
      }

      nGetStringUTFRegion(str, start, len, MemoryUtil.memAddress(buf));
   }

   @NativeType("void *")
   public static native long NewWeakGlobalRef(@NativeType("jobject") Object var0);

   public static native void nDeleteWeakGlobalRef(long var0);

   public static void DeleteWeakGlobalRef(@NativeType("void *") long weakGlobalRef) {
      if (Checks.CHECKS) {
         Checks.check(weakGlobalRef);
      }

      nDeleteWeakGlobalRef(weakGlobalRef);
   }

   @Nullable
   public static native ByteBuffer nNewDirectByteBuffer(long var0, long var2);

   @Nullable
   @NativeType("jobject")
   public static ByteBuffer NewDirectByteBuffer(@NativeType("void *") long address, @NativeType("jlong") long capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return nNewDirectByteBuffer(address, capacity);
   }

   @NativeType("void *")
   public static native long GetDirectBufferAddress(@NativeType("jobject") Buffer var0);

   @NativeType("jobjectRefType")
   public static native int GetObjectRefType(@NativeType("jobject") Object var0);

   static {
      Library.initialize();
   }
}
