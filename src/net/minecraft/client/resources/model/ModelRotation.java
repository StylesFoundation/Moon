package net.minecraft.client.resources.model;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.IModelPart;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ITransformation;
import net.minecraftforge.client.model.TRSRTransformation;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public enum ModelRotation implements IModelState, ITransformation {
   X0_Y0(0, 0),
   X0_Y90(0, 90),
   X0_Y180(0, 180),
   X0_Y270(0, 270),
   X90_Y0(90, 0),
   X90_Y90(90, 90),
   X90_Y180(90, 180),
   X90_Y270(90, 270),
   X180_Y0(180, 0),
   X180_Y90(180, 90),
   X180_Y180(180, 180),
   X180_Y270(180, 270),
   X270_Y0(270, 0),
   X270_Y90(270, 90),
   X270_Y180(270, 180),
   X270_Y270(270, 270);

   private static final Map<Integer, ModelRotation> mapRotations = Maps.newHashMap();
   private final int combinedXY;
   private final Matrix4f matrix4d;
   private final int quartersX;
   private final int quartersY;

   private static int combineXY(int integer1, int integer2) {
      return integer1 * 360 + integer2;
   }

   ModelRotation(int integer3, int integer4) {
      this.combinedXY = combineXY(integer3, integer4);
      this.matrix4d = new Matrix4f();
      Matrix4f var7 = new Matrix4f();
      var7.setIdentity();
      Matrix4f.rotate(-integer3 * (float) (Math.PI / 180.0), new Vector3f(1.0F, 0.0F, 0.0F), var7, var7);
      this.quartersX = MathHelper.abs_int(integer3 / 90);
      Matrix4f var8 = new Matrix4f();
      var8.setIdentity();
      Matrix4f.rotate(-integer4 * (float) (Math.PI / 180.0), new Vector3f(0.0F, 1.0F, 0.0F), var8, var8);
      this.quartersY = MathHelper.abs_int(integer4 / 90);
      Matrix4f.mul(var8, var7, this.matrix4d);
   }

   public Matrix4f getMatrix4d() {
      return this.matrix4d;
   }

   public EnumFacing rotateFace(EnumFacing enumFacing) {
      EnumFacing var5 = enumFacing;

      for (int var6 = 0; var6 < this.quartersX; var6++) {
         var5 = var5.rotateAround(EnumFacing.Axis.X);
      }

      if (var5.getAxis() != EnumFacing.Axis.Y) {
         for (int var7 = 0; var7 < this.quartersY; var7++) {
            var5 = var5.rotateAround(EnumFacing.Axis.Y);
         }
      }

      return var5;
   }

   public int rotateVertex(EnumFacing enumFacing, int integer) {
      int var6 = integer;
      if (enumFacing.getAxis() == EnumFacing.Axis.X) {
         var6 = (integer + this.quartersX) % 4;
      }

      EnumFacing var7 = enumFacing;

      for (int var8 = 0; var8 < this.quartersX; var8++) {
         var7 = var7.rotateAround(EnumFacing.Axis.X);
      }

      if (var7.getAxis() == EnumFacing.Axis.Y) {
         var6 = (var6 + this.quartersY) % 4;
      }

      return var6;
   }

   public static ModelRotation getModelRotation(int integer1, int integer2) {
      return mapRotations.get(combineXY(MathHelper.normalizeAngle(integer1, 360), MathHelper.normalizeAngle(integer2, 360)));
   }

   @Override
   public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> optional) {
      return null;
   }

   @Override
   public Matrix4f Method363() {
      return null;
   }

   @Override
   public EnumFacing rotate(EnumFacing enumFacing) {
      return this.rotateFace(enumFacing);
   }

   @Override
   public int rotate(EnumFacing enumFacing, int integer) {
      return this.rotateVertex(enumFacing, integer);
   }

   private static ModelRotation[] Method364() {
      return new ModelRotation[]{
         X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270
      };
   }

   static {
      for (ModelRotation var16 : values()) {
         mapRotations.put(var16.combinedXY, var16);
      }
   }
}
