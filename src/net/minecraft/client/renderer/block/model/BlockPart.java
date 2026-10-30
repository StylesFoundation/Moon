package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class BlockPart {
   public final Vector3f positionFrom;
   public final Vector3f positionTo;
   public final Map<EnumFacing, BlockPartFace> mapFaces;
   public final BlockPartRotation partRotation;
   public final boolean shade;

   public BlockPart(Vector3f vector3f1, Vector3f vector3f2, Map<EnumFacing, BlockPartFace> map, BlockPartRotation blockPartRotation, boolean boolean5) {
      this.positionFrom = vector3f1;
      this.positionTo = vector3f2;
      this.mapFaces = map;
      this.partRotation = blockPartRotation;
      this.shade = boolean5;
      this.setDefaultUvs();
   }

   private void setDefaultUvs() {
      for (Entry var2 : this.mapFaces.entrySet()) {
         float[] var3 = this.getFaceUvs((EnumFacing)var2.getKey());
         ((BlockPartFace)var2.getValue()).blockFaceUV.setUvs(var3);
      }
   }

   private float[] getFaceUvs(EnumFacing enumFacing) {
      return switch (enumFacing) {
         case DOWN, UP -> new float[]{this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z};
         case NORTH, SOUTH -> new float[]{this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y};
         case WEST, EAST -> new float[]{this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y};
         default -> throw new NullPointerException();
      };
   }

   static class Deserializer implements JsonDeserializer<BlockPart> {
      public BlockPart deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         Vector3f var5 = this.parsePositionFrom(var4);
         Vector3f var6 = this.parsePositionTo(var4);
         BlockPartRotation var7 = this.parseRotation(var4);
         Map var8 = this.parseFacesCheck(jsonDeserializationContext, var4);
         if (var4.has("shade") && !JsonUtils.isBoolean(var4, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         }

         boolean var9 = JsonUtils.getBoolean(var4, "shade", true);
         return new BlockPart(var5, var6, var8, var7, var9);
      }

      private BlockPartRotation parseRotation(JsonObject jsonObject) {
         BlockPartRotation var2 = null;
         if (jsonObject.has("rotation")) {
            JsonObject var3 = JsonUtils.Method747(jsonObject, "rotation");
            Vector3f var4 = this.parsePosition(var3, "origin");
            var4.scale(0.0625F);
            EnumFacing.Axis var5 = this.parseAxis(var3);
            float var6 = this.parseAngle(var3);
            boolean var7 = JsonUtils.getBoolean(var3, "rescale", false);
            var2 = new BlockPartRotation(var4, var5, var6, var7);
         }

         return var2;
      }

      private float parseAngle(JsonObject jsonObject) {
         float var2 = JsonUtils.Method744(jsonObject, "angle");
         if (var2 != 0.0F && MathHelper.abs(var2) != 22.5F && MathHelper.abs(var2) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + var2 + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return var2;
         }
      }

      private EnumFacing.Axis parseAxis(JsonObject jsonObject) {
         String var2 = JsonUtils.Method741(jsonObject, "axis");
         EnumFacing.Axis var3 = EnumFacing.Axis.byName(var2.toLowerCase());
         if (var3 == null) {
            throw new JsonParseException("Invalid rotation axis: " + var2);
         } else {
            return var3;
         }
      }

      private Map<EnumFacing, BlockPartFace> parseFacesCheck(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
         Map var3 = this.parseFaces(jsonDeserializationContext, jsonObject);
         if (var3.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return var3;
         }
      }

      private Map<EnumFacing, BlockPartFace> parseFaces(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
         EnumMap var3 = Maps.newEnumMap(EnumFacing.class);

         for (Entry var6 : JsonUtils.Method747(jsonObject, "faces").entrySet()) {
            EnumFacing var7 = this.parseEnumFacing((String)var6.getKey());
            var3.put(var7, (BlockPartFace)jsonDeserializationContext.deserialize((JsonElement)var6.getValue(), BlockPartFace.class));
         }

         return var3;
      }

      private EnumFacing parseEnumFacing(String string) {
         EnumFacing var2 = EnumFacing.byName(string);
         if (var2 == null) {
            throw new JsonParseException("Unknown facing: " + string);
         } else {
            return var2;
         }
      }

      private Vector3f parsePositionTo(JsonObject jsonObject) {
         Vector3f var2 = this.parsePosition(jsonObject, "to");
         if (var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
            return var2;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + var2);
         }
      }

      private Vector3f parsePositionFrom(JsonObject jsonObject) {
         Vector3f var2 = this.parsePosition(jsonObject, "from");
         if (var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
            return var2;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + var2);
         }
      }

      private Vector3f parsePosition(JsonObject jsonObject, String string) {
         JsonArray var3 = JsonUtils.Method749(jsonObject, string);
         if (var3.size() != 3) {
            throw new JsonParseException("Expected 3 " + string + " values, found: " + var3.size());
         }

         float[] var4 = new float[3];

         for (int var5 = 0; var5 < var4.length; var5++) {
            var4[var5] = JsonUtils.getFloat(var3.get(var5), string + "[" + var5 + "]");
         }

         return new Vector3f(var4[0], var4[1], var4[2]);
      }
   }
}
