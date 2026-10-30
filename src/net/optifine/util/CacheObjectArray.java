package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import net.minecraft.block.state.IBlockState;
import net.minecraft.src.Config;

public class CacheObjectArray {
   private static final ArrayDeque<int[]> arrays = new ArrayDeque<>();

   private static synchronized int[] allocateArray(int integer) {
      int[] var4 = arrays.pollLast();
      if (var4 == null || var4.length < integer) {
         var4 = new int[integer];
      }

      return var4;
   }

   public static synchronized void freeArray(int[] arr) {
      if (arrays.size() < 10) {
         arrays.add(arr);
      }
   }

   public static void main(String[] arr) throws Exception {
      short var4 = 4096;
      int var5 = 500000;
      testNew(var4, var5);
      testClone(var4, var5);
      testNewObj(var4, var5);
      testCloneObj(var4, var5);
      testNewObjDyn(IBlockState.class, var4, var5);
      long var6 = testNew(var4, var5);
      long var8 = testClone(var4, var5);
      long var10 = testNewObj(var4, var5);
      long var12 = testCloneObj(var4, var5);
      long var14 = testNewObjDyn(IBlockState.class, var4, var5);
      Config.Method251("New: " + var6);
      Config.Method251("Clone: " + var8);
      Config.Method251("NewObj: " + var10);
      Config.Method251("CloneObj: " + var12);
      Config.Method251("NewObjDyn: " + var14);
   }

   private static long testClone(int integer1, int integer2) {
      long var5 = System.currentTimeMillis();
      int[] var7 = new int[integer1];

      for (int var8 = 0; var8 < integer2; var8++) {
         int[] var10000 = (int[])var7.clone();
      }

      long var10 = System.currentTimeMillis();
      return var10 - var5;
   }

   private static long testNew(int integer1, int integer2) {
      long var5 = System.currentTimeMillis();

      for (int var7 = 0; var7 < integer2; var7++) {
         int[] var10000 = (int[])Array.newInstance(int.class, integer1);
      }

      long var9 = System.currentTimeMillis();
      return var9 - var5;
   }

   private static long testCloneObj(int integer1, int integer2) {
      long var5 = System.currentTimeMillis();
      IBlockState[] var7 = new IBlockState[integer1];

      for (int var8 = 0; var8 < integer2; var8++) {
         IBlockState[] var10000 = (IBlockState[])var7.clone();
      }

      long var10 = System.currentTimeMillis();
      return var10 - var5;
   }

   private static long testNewObj(int integer1, int integer2) {
      long var5 = System.currentTimeMillis();

      for (int var7 = 0; var7 < integer2; var7++) {
         IBlockState[] var10000 = new IBlockState[integer1];
      }

      long var9 = System.currentTimeMillis();
      return var9 - var5;
   }

   private static long testNewObjDyn(Class class1, int integer2, int integer3) {
      long var6 = System.currentTimeMillis();

      for (int var8 = 0; var8 < integer3; var8++) {
         Object[] var10000 = (Object[])Array.newInstance(class1, integer2);
      }

      long var10 = System.currentTimeMillis();
      return var10 - var6;
   }
}
