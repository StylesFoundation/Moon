package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Vector3f;

public class ItemModelGenerator {
   public static final List<String> LAYERS = Lists.newArrayList(new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});

   public ModelBlock makeItemModel(TextureMap textureMap, ModelBlock modelBlock) {
      HashMap var3 = Maps.newHashMap();
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < LAYERS.size(); var5++) {
         String var6 = LAYERS.get(var5);
         if (!modelBlock.isTexturePresent(var6)) {
            break;
         }

         String var7 = modelBlock.resolveTextureName(var6);
         var3.put(var6, var7);
         TextureAtlasSprite var8 = textureMap.getAtlasSprite(new ResourceLocation(var7).toString());
         var4.addAll(this.func_178394_a(var5, var6, var8));
      }

      if (var4.isEmpty()) {
         return null;
      }

      var3.put("particle", modelBlock.isTexturePresent("particle") ? modelBlock.resolveTextureName("particle") : (String)var3.get("layer0"));
      return new ModelBlock(var4, var3, false, false, modelBlock.getAllTransforms());
   }

   private List<BlockPart> func_178394_a(int integer, String string, TextureAtlasSprite textureAtlasSprite) {
      HashMap var4 = Maps.newHashMap();
      var4.put(EnumFacing.SOUTH, new BlockPartFace(null, integer, string, new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      var4.put(EnumFacing.NORTH, new BlockPartFace(null, integer, string, new BlockFaceUV(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      ArrayList var5 = Lists.newArrayList();
      var5.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), var4, null, true));
      var5.addAll(this.func_178397_a(textureAtlasSprite, string, integer));
      return var5;
   }

   private List<BlockPart> func_178397_a(TextureAtlasSprite textureAtlasSprite, String string, int integer) {
      float var4 = textureAtlasSprite.getIconWidth();
      float var5 = textureAtlasSprite.getIconHeight();
      ArrayList var6 = Lists.newArrayList();

      for (ItemModelGenerator.Span var8 : this.func_178393_a(textureAtlasSprite)) {
         float var9 = 0.0F;
         float var10 = 0.0F;
         float var11 = 0.0F;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;
         float var18 = 0.0F;
         float var19 = var8.func_178385_b();
         float var20 = var8.func_178384_c();
         float var21 = var8.func_178381_d();
         ItemModelGenerator.SpanFacing var22 = var8.func_178383_a();
         switch (var22) {
            case UP:
               var13 = var19;
               var9 = var19;
               var11 = var14 = var20 + 1.0F;
               var15 = var21;
               var10 = var21;
               var16 = var21;
               var12 = var21;
               var17 = 16.0F / var4;
               var18 = 16.0F / (var5 - 1.0F);
               break;
            case DOWN:
               var16 = var21;
               var15 = var21;
               var13 = var19;
               var9 = var19;
               var11 = var14 = var20 + 1.0F;
               var10 = var21 + 1.0F;
               var12 = var21 + 1.0F;
               var17 = 16.0F / var4;
               var18 = 16.0F / (var5 - 1.0F);
               break;
            case LEFT:
               var13 = var21;
               var9 = var21;
               var14 = var21;
               var11 = var21;
               var16 = var19;
               var10 = var19;
               var12 = var15 = var20 + 1.0F;
               var17 = 16.0F / (var4 - 1.0F);
               var18 = 16.0F / var5;
               break;
            case RIGHT:
               var14 = var21;
               var13 = var21;
               var9 = var21 + 1.0F;
               var11 = var21 + 1.0F;
               var16 = var19;
               var10 = var19;
               var12 = var15 = var20 + 1.0F;
               var17 = 16.0F / (var4 - 1.0F);
               var18 = 16.0F / var5;
         }

         float var23 = 16.0F / var4;
         float var24 = 16.0F / var5;
         var9 *= var23;
         var11 *= var23;
         var10 *= var24;
         var12 *= var24;
         var10 = 16.0F - var10;
         var12 = 16.0F - var12;
         var13 *= var17;
         var14 *= var17;
         var15 *= var18;
         var16 *= var18;
         HashMap var25 = Maps.newHashMap();
         var25.put(var22.getFacing(), new BlockPartFace(null, integer, string, new BlockFaceUV(new float[]{var13, var15, var14, var16}, 0)));
         switch (var22) {
            case UP:
               var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var11, var10, 8.5F), var25, null, true));
               break;
            case DOWN:
               var6.add(new BlockPart(new Vector3f(var9, var12, 7.5F), new Vector3f(var11, var12, 8.5F), var25, null, true));
               break;
            case LEFT:
               var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var9, var12, 8.5F), var25, null, true));
               break;
            case RIGHT:
               var6.add(new BlockPart(new Vector3f(var11, var10, 7.5F), new Vector3f(var11, var12, 8.5F), var25, null, true));
         }
      }

      return var6;
   }

   private List<ItemModelGenerator.Span> func_178393_a(TextureAtlasSprite textureAtlasSprite) {
      int var2 = textureAtlasSprite.getIconWidth();
      int var3 = textureAtlasSprite.getIconHeight();
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < textureAtlasSprite.getFrameCount(); var5++) {
         int[] var6 = textureAtlasSprite.getFrameTextureData(var5)[0];

         for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var2; var8++) {
               boolean var9 = !this.func_178391_a(var6, var8, var7, var2, var3);
               this.func_178396_a(ItemModelGenerator.SpanFacing.UP, var4, var6, var8, var7, var2, var3, var9);
               this.func_178396_a(ItemModelGenerator.SpanFacing.DOWN, var4, var6, var8, var7, var2, var3, var9);
               this.func_178396_a(ItemModelGenerator.SpanFacing.LEFT, var4, var6, var8, var7, var2, var3, var9);
               this.func_178396_a(ItemModelGenerator.SpanFacing.RIGHT, var4, var6, var8, var7, var2, var3, var9);
            }
         }
      }

      return var4;
   }

   private void func_178396_a(
      ItemModelGenerator.SpanFacing spanFacing,
      List<ItemModelGenerator.Span> list,
      int[] arr,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      boolean boolean8
   ) {
      boolean var9 = this.func_178391_a(arr, integer4 + spanFacing.func_178372_b(), integer5 + spanFacing.func_178371_c(), integer6, integer7) && boolean8;
      if (var9) {
         this.func_178395_a(list, spanFacing, integer4, integer5);
      }
   }

   private void func_178395_a(List<ItemModelGenerator.Span> list, ItemModelGenerator.SpanFacing spanFacing, int integer3, int integer4) {
      ItemModelGenerator.Span var5 = null;

      for (ItemModelGenerator.Span var7 : list) {
         if (var7.func_178383_a() == spanFacing) {
            int var8 = spanFacing.func_178369_d() ? integer4 : integer3;
            if (var7.func_178381_d() == var8) {
               var5 = var7;
               break;
            }
         }
      }

      int var9 = spanFacing.func_178369_d() ? integer4 : integer3;
      int var10 = spanFacing.func_178369_d() ? integer3 : integer4;
      if (var5 == null) {
         list.add(new ItemModelGenerator.Span(spanFacing, var10, var9));
      } else {
         var5.func_178382_a(var10);
      }
   }

   private boolean func_178391_a(int[] arr, int integer2, int integer3, int integer4, int integer5) {
      return integer2 < 0 || integer3 < 0 || integer2 >= integer4 || integer3 >= integer5 || (arr[integer3 * integer4 + integer2] >> 24 & 0xFF) == 0;
   }

   static class Span {
      private final ItemModelGenerator.SpanFacing spanFacing;
      private int field_178387_b;
      private int field_178388_c;
      private final int field_178386_d;

      public Span(ItemModelGenerator.SpanFacing spanFacing, int integer2, int integer3) {
         this.spanFacing = spanFacing;
         this.field_178387_b = integer2;
         this.field_178388_c = integer2;
         this.field_178386_d = integer3;
      }

      public void func_178382_a(int integer) {
         if (integer < this.field_178387_b) {
            this.field_178387_b = integer;
         } else if (integer > this.field_178388_c) {
            this.field_178388_c = integer;
         }
      }

      public ItemModelGenerator.SpanFacing func_178383_a() {
         return this.spanFacing;
      }

      public int func_178385_b() {
         return this.field_178387_b;
      }

      public int func_178384_c() {
         return this.field_178388_c;
      }

      public int func_178381_d() {
         return this.field_178386_d;
      }
   }

   enum SpanFacing {
      UP(EnumFacing.UP, 0, -1),
      DOWN(EnumFacing.DOWN, 0, 1),
      LEFT(EnumFacing.EAST, -1, 0),
      RIGHT(EnumFacing.WEST, 1, 0);

      private final EnumFacing facing;
      private final int field_178373_f;
      private final int field_178374_g;

      SpanFacing(EnumFacing enumFacing, int integer4, int integer5) {
         this.facing = enumFacing;
         this.field_178373_f = integer4;
         this.field_178374_g = integer5;
      }

      public EnumFacing getFacing() {
         return this.facing;
      }

      public int func_178372_b() {
         return this.field_178373_f;
      }

      public int func_178371_c() {
         return this.field_178374_g;
      }

      private boolean func_178369_d() {
         return this == DOWN || this == UP;
      }

      private static ItemModelGenerator.SpanFacing[] Method384() {
         return new ItemModelGenerator.SpanFacing[]{UP, DOWN, LEFT, RIGHT};
      }
   }
}
