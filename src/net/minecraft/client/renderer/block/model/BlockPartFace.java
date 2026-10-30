package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;

public class BlockPartFace {
   public static final EnumFacing FACING_DEFAULT = null;
   public final EnumFacing cullFace;
   public final int tintIndex;
   public final String texture;
   public final BlockFaceUV blockFaceUV;

   public BlockPartFace(EnumFacing enumFacing, int integer, String string, BlockFaceUV blockFaceUV) {
      this.cullFace = enumFacing;
      this.tintIndex = integer;
      this.texture = string;
      this.blockFaceUV = blockFaceUV;
   }

   static class Deserializer implements JsonDeserializer<BlockPartFace> {
      public BlockPartFace deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         EnumFacing var5 = this.parseCullFace(var4);
         int var6 = this.parseTintIndex(var4);
         String var7 = this.parseTexture(var4);
         BlockFaceUV var8 = (BlockFaceUV)jsonDeserializationContext.deserialize(var4, BlockFaceUV.class);
         return new BlockPartFace(var5, var6, var7, var8);
      }

      protected int parseTintIndex(JsonObject jsonObject) {
         return JsonUtils.getInt(jsonObject, "tintindex", -1);
      }

      private String parseTexture(JsonObject jsonObject) {
         return JsonUtils.Method741(jsonObject, "texture");
      }

      private EnumFacing parseCullFace(JsonObject jsonObject) {
         return EnumFacing.byName(JsonUtils.getString(jsonObject, "cullface", ""));
      }
   }
}
