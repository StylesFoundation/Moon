package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiSpectator extends Gui implements ISpectatorMenuRecipient {
   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
   private final Minecraft field_175268_g;
   private long field_175270_h;
   private SpectatorMenu field_175271_i;

   public GuiSpectator(Minecraft minecraft) {
      this.field_175268_g = minecraft;
   }

   public void func_175260_a(int integer) {
      this.field_175270_h = Minecraft.getSystemTime();
      if (this.field_175271_i != null) {
         this.field_175271_i.func_178644_b(integer);
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }

   private float func_175265_c() {
      return MathHelper.clamp_float((float)(this.field_175270_h - Minecraft.getSystemTime() + 5000L) / 2000.0F, 0.0F, 1.0F);
   }

   public void renderTooltip(ScaledResolution scaledResolution, float float2) {
      if (this.field_175271_i != null) {
         float var3 = this.func_175265_c();
         if (var3 <= 0.0F) {
            this.field_175271_i.func_178641_d();
         } else {
            int var4 = scaledResolution.Method237() / 2;
            float var5 = this.zLevel;
            this.zLevel = -90.0F;
            float var6 = scaledResolution.Method238() - 22.0F * var3;
            SpectatorDetails var7 = this.field_175271_i.func_178646_f();
            this.func_175258_a(scaledResolution, var3, var4, var6, var7);
            this.zLevel = var5;
         }
      }
   }

   protected void func_175258_a(ScaledResolution scaledResolution, float float2, int integer, float float4, SpectatorDetails spectatorDetails) {
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, float2);
      this.field_175268_g.getTextureManager().bindTexture(field_175267_f);
      this.drawTexturedModalRect(integer - 91, float4, 0, 0, 182, 22);
      if (spectatorDetails.func_178681_b() >= 0) {
         this.drawTexturedModalRect(integer - 91 - 1 + spectatorDetails.func_178681_b() * 20, float4 - 1.0F, 0, 22, 24, 22);
      }

      RenderHelper.enableGUIStandardItemLighting();

      for (int var8 = 0; var8 < 9; var8++) {
         this.func_175266_a(var8, scaledResolution.Method237() / 2 - 90 + var8 * 20 + 2, float4 + 3.0F, float2, spectatorDetails.func_178680_a(var8));
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
   }

   private void func_175266_a(int integer1, int integer2, float float3, float float4, ISpectatorMenuObject iSpectatorMenuObject) {
      this.field_175268_g.getTextureManager().bindTexture(field_175269_a);
      if (iSpectatorMenuObject != SpectatorMenu.field_178657_a) {
         int var8 = (int)(float4 * 255.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(integer2, float3, 0.0F);
         float var9 = iSpectatorMenuObject.func_178662_A_() ? 1.0F : 0.25F;
         GlStateManager.color(var9, var9, var9, float4);
         iSpectatorMenuObject.func_178663_a(var9, var8);
         GlStateManager.popMatrix();
         String var10 = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[integer1].getKeyCode()));
         if (var8 > 3 && iSpectatorMenuObject.func_178662_A_()) {
            this.field_175268_g
               .fontRendererObj
               .drawStringWithShadow(
                  var10, integer2 + 19 - 2 - this.field_175268_g.fontRendererObj.getStringWidth(var10), float3 + 6.0F + 3.0F, 16777215 + (var8 << 24)
               );
         }
      }
   }

   public void renderSelectedItem(ScaledResolution scaledResolution) {
      int var4 = (int)(this.func_175265_c() * 255.0F);
      if (var4 > 3 && this.field_175271_i != null) {
         ISpectatorMenuObject var5 = this.field_175271_i.func_178645_b();
         String var6 = var5 != SpectatorMenu.field_178657_a
            ? var5.getSpectatorName().getFormattedText()
            : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
         if (var6 != null) {
            int var7 = (scaledResolution.Method237() - this.field_175268_g.fontRendererObj.getStringWidth(var6)) / 2;
            int var8 = scaledResolution.Method238() - 35;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.field_175268_g.fontRendererObj.drawStringWithShadow(var6, var7, var8, 16777215 + (var4 << 24));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }
      }
   }

   @Override
   public void func_175257_a(SpectatorMenu spectatorMenu) {
      this.field_175271_i = null;
      this.field_175270_h = 0L;
   }

   public boolean func_175262_a() {
      return this.field_175271_i != null;
   }

   public void func_175259_b(int integer) {
      int var4 = this.field_175271_i.func_178648_e() + integer;

      while (
         var4 >= 0
            && var4 <= 8
            && (this.field_175271_i.func_178643_a(var4) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(var4).func_178662_A_())
      ) {
         var4 += integer;
      }

      if (var4 >= 0 && var4 <= 8) {
         this.field_175271_i.func_178644_b(var4);
         this.field_175270_h = Minecraft.getSystemTime();
      }
   }

   public void func_175261_b() {
      this.field_175270_h = Minecraft.getSystemTime();
      if (this.func_175262_a()) {
         int var1 = this.field_175271_i.func_178648_e();
         if (var1 != -1) {
            this.field_175271_i.func_178644_b(var1);
         }
      } else {
         this.field_175271_i = new SpectatorMenu(this);
      }
   }
}
