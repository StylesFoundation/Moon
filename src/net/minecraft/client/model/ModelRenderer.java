package net.minecraft.client.model;

import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.model.ModelSprite;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
   public float textureWidth;
   public float textureHeight;
   private int textureOffsetX;
   private int textureOffsetY;
   public float rotationPointX;
   public float rotationPointY;
   public float rotationPointZ;
   public float rotateAngleX;
   public float rotateAngleY;
   public float rotateAngleZ;
   private boolean compiled;
   private int displayList;
   public boolean mirror;
   public boolean showModel;
   public boolean isHidden;
   public List<ModelBox> cubeList;
   public List<ModelRenderer> childModels;
   public final String boxName;
   private final ModelBase baseModel;
   public float offsetX;
   public float offsetY;
   public float offsetZ;
   public List spriteList = new ArrayList();
   public boolean mirrorV = false;
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   private int countResetDisplayList;
   private ResourceLocation textureLocation = null;
   private String id = null;
   private ModelUpdater modelUpdater;
   private final RenderGlobal renderGlobal = Config.getRenderGlobal();
   private Camera G;

   public ModelRenderer(ModelBase modelBase, String string) {
      this.textureWidth = 64.0F;
      this.textureHeight = 32.0F;
      this.showModel = true;
      this.cubeList = Lists.newArrayList();
      this.baseModel = modelBase;
      modelBase.boxList.add(this);
      this.boxName = string;
      this.setTextureSize(modelBase.textureWidth, modelBase.textureHeight);
   }

   public ModelRenderer(ModelBase modelBase) {
      this(modelBase, null);
   }

   public ModelRenderer(ModelBase modelBase, int integer2, int integer3) {
      this(modelBase);
      this.setTextureOffset(integer2, integer3);
   }

   public void addChild(ModelRenderer modelRenderer) {
      if (this.childModels == null) {
         this.childModels = Lists.newArrayList();
      }

      this.childModels.add(modelRenderer);
   }

   public ModelRenderer setTextureOffset(int integer1, int integer2) {
      this.textureOffsetX = integer1;
      this.textureOffsetY = integer2;
      return this;
   }

   public ModelRenderer addBox(String string, float float2, float float3, float float4, int integer5, int integer6, int integer7) {
      string = this.boxName + "." + string;
      TextureOffset var8 = this.baseModel.getTextureOffset(string);
      this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
      this.cubeList
         .add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, float2, float3, float4, integer5, integer6, integer7, 0.0F).setBoxName(string));
      return this;
   }

   public ModelRenderer addBox(float float1, float float2, float float3, int integer4, int integer5, int integer6) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, float1, float2, float3, integer4, integer5, integer6, 0.0F));
      return this;
   }

   public ModelRenderer addBox(float float1, float float2, float float3, int integer4, int integer5, int integer6, boolean boolean7) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, float1, float2, float3, integer4, integer5, integer6, 0.0F, boolean7));
      return this;
   }

   public void addBox(float float1, float float2, float float3, int integer4, int integer5, int integer6, float float7) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, float1, float2, float3, integer4, integer5, integer6, float7));
   }

   public void setRotationPoint(float float1, float float2, float float3) {
      this.rotationPointX = float1;
      this.rotationPointY = float2;
      this.rotationPointZ = float3;
   }

   public void render(float float1) {
      if (!this.isHidden && this.showModel) {
         this.checkResetDisplayList();
         if (!this.compiled) {
            this.compileDisplayList(float1);
         }

         int var5 = 0;
         if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
            if (this.renderGlobal.renderOverlayEyes) {
               return;
            }

            var5 = GlStateManager.getBoundTexture();
            Config.getTextureManager().bindTexture(this.textureLocation);
         }

         if (this.modelUpdater != null) {
            this.modelUpdater.update();
         }

         boolean var6 = this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F;
         GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
         if (this.rotateAngleX != 0.0F || this.rotateAngleY != 0.0F || this.rotateAngleZ != 0.0F) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
            if (this.rotateAngleZ != 0.0F) {
               GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
               GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
               GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (var6) {
               GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
            }

            GlStateManager.callList(this.displayList);
            if (this.childModels != null) {
               for (ModelRenderer var12 : this.childModels) {
                  var12.render(float1);
               }
            }

            GlStateManager.popMatrix();
         } else if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
            if (var6) {
               GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
            }

            GlStateManager.callList(this.displayList);
            if (this.childModels != null) {
               for (ModelRenderer var11 : this.childModels) {
                  var11.render(float1);
               }
            }

            if (var6) {
               GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
            }
         } else {
            GlStateManager.translate(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
            if (var6) {
               GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
            }

            GlStateManager.callList(this.displayList);
            if (this.childModels != null) {
               for (ModelRenderer var8 : this.childModels) {
                  var8.render(float1);
               }
            }

            if (var6) {
               GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
            }

            GlStateManager.translate(-this.rotationPointX * float1, -this.rotationPointY * float1, -this.rotationPointZ * float1);
         }

         GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
         if (var5 != 0) {
            GlStateManager.bindTexture(var5);
         }
      }
   }

   public void renderWithRotation(float float1) {
      if (!this.isHidden && this.showModel) {
         this.checkResetDisplayList();
         if (!this.compiled) {
            this.compileDisplayList(float1);
         }

         int var5 = 0;
         if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
            if (this.renderGlobal.renderOverlayEyes) {
               return;
            }

            var5 = GlStateManager.getBoundTexture();
            Config.getTextureManager().bindTexture(this.textureLocation);
         }

         if (this.modelUpdater != null) {
            this.modelUpdater.update();
         }

         boolean var6 = this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
         if (this.rotateAngleY != 0.0F) {
            GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         }

         if (this.rotateAngleX != 0.0F) {
            GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         }

         if (this.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
         }

         if (var6) {
            GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
         }

         GlStateManager.callList(this.displayList);
         if (this.childModels != null) {
            for (ModelRenderer var8 : this.childModels) {
               var8.render(float1);
            }
         }

         GlStateManager.popMatrix();
         if (var5 != 0) {
            GlStateManager.bindTexture(var5);
         }
      }
   }

   public void postRender(float float1) {
      if (!this.isHidden && this.showModel) {
         this.checkResetDisplayList();
         if (!this.compiled) {
            this.compileDisplayList(float1);
         }

         if (this.rotateAngleX != 0.0F || this.rotateAngleY != 0.0F || this.rotateAngleZ != 0.0F) {
            GlStateManager.translate(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
            if (this.rotateAngleZ != 0.0F) {
               GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
               GlStateManager.rotate(this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
               GlStateManager.rotate(this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }
         } else if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
            GlStateManager.translate(this.rotationPointX * float1, this.rotationPointY * float1, this.rotationPointZ * float1);
         }
      }
   }

   private void compileDisplayList(float float1) {
      if (this.G == null) {
         this.G = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      if (this.displayList == 0) {
         this.displayList = GLAllocation.generateDisplayLists(1);
      }

      GL11.glNewList(this.displayList, GL11.GL_COMPILE);
      WorldRenderer var5 = Tessellator.getInstance().getWorldRenderer();
      if (this.G.isEnabled() && this.G.batchRenderingEntity.getValue()) {
         Tessellator.getInstance().getWorldRenderer().begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
      }

      for (ModelBox var7 : this.cubeList) {
         var7.render(var5, float1);
      }

      for (ModelSprite var8 : (Iterable<ModelSprite>) this.spriteList) {
         var8.render(Tessellator.getInstance(), float1);
      }

      if (this.G.isEnabled() && this.G.batchRenderingEntity.getValue()) {
         Tessellator.getInstance().draw();
      }

      GL11.glEndList();
      this.compiled = true;
   }

   public ModelRenderer setTextureSize(int integer1, int integer2) {
      this.textureWidth = integer1;
      this.textureHeight = integer2;
      return this;
   }

   public void addSprite(float float1, float float2, float float3, int integer4, int integer5, int integer6, float float7) {
      this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, float1, float2, float3, integer4, integer5, integer6, float7));
   }

   public boolean getCompiled() {
      return this.compiled;
   }

   public int getDisplayList() {
      return this.displayList;
   }

   private void checkResetDisplayList() {
      if (this.countResetDisplayList != Shaders.countResetDisplayLists) {
         this.compiled = false;
         this.countResetDisplayList = Shaders.countResetDisplayLists;
      }
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(ResourceLocation resourceLocation) {
      this.textureLocation = resourceLocation;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String string) {
      this.id = string;
   }

   public void addBox(int[][] arr, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      this.cubeList.add(new ModelBox(this, arr, float2, float3, float4, float5, float6, float7, float8, this.mirror));
   }

   public ModelRenderer getChild(String string) {
      if (string != null && this.childModels != null) {
         for (ModelRenderer var6 : this.childModels) {
            if (string.equals(var6.getId())) {
               return var6;
            }
         }
      }

      return null;
   }

   public ModelRenderer getChildDeep(String string) {
      if (string == null) {
         return null;
      }

      ModelRenderer var5 = this.getChild(string);
      if (var5 != null) {
         return var5;
      }

      if (this.childModels != null) {
         Iterator var6 = this.childModels.iterator();

         while (var6.hasNext()) {
            ModelRenderer var8 = ((ModelRenderer)var6.next()).getChildDeep(string);
            if (var8 != null) {
               return var8;
            }
         }
      }

      return null;
   }

   public void setModelUpdater(ModelUpdater modelUpdater) {
      this.modelUpdater = modelUpdater;
   }

   @Override
   public String toString() {
      return "id: "
         + this.id
         + ", boxes: "
         + (this.cubeList != null ? this.cubeList.size() : null)
         + ", submodels: "
         + (this.childModels != null ? this.childModels.size() : null);
   }
}
