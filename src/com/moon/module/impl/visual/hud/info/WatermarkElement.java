package com.moon.module.impl.visual.hud.info;

import com.moon.Moon;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.ColorMode;
import com.moon.module.impl.visual.hud.base.HudElement;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.font.msdf.MsdfFontRenderer;
import com.moon.render.state.impl.GradientRenderState;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WatermarkElement implements HudElement {
   private final ResourceLocation LOGO_TEXTURE = new ResourceLocation("logo.png");
   private HUD hud = null;
   private Effects effects;
   private boolean textureFilterSet = false;

   @Override
   public void setRectangleTask(RenderTask task) {
   }

   @Override
   public void setRoundedTask(RenderTask task) {
      if (this.effects == null) {
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      this.resolveHud();
      FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      if (this.hud.getWatermarkEnabled().getValue() && this.hud.getWatermarkMode().getValue() == HUD.WatermarkMode.INFORMATIVE) {
         String[] infoSegments = this.buildInfoSegments();
         String[] iconGlyphs = this.buildIconGlyphs();
         String infoText = String.join("    §7·  §r", infoSegments);
         String iconText = String.join("", iconGlyphs);
         float width = fontRenderer.getTextWidth(infoText, 5.85F) + iconFont.getTextWidth(iconText, 5.85F) + 11.0F;
         float height = fontRenderer.getLineHeight(5.85F) + 11.0F;
         this.computeColor(
            0.0F, this.hud, this.hud.getWatermarkColorMode().getValue(), this.hud.getWatermarkColorOne().getValue(), this.hud.getWatermarkColorTwo().getValue()
         );
         task.addCommand(
            RoundedRectangleCommand.of(
               7.25F,
               8.25F,
               width,
               height,
               6.5F,
               new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(this.effects.isEnabled() && this.effects.getBlur().getValue() ? 0.275F : 0.4F).getRGB()
            )
         );
      }
   }

   @Override
   public void setBackgroundTask(RenderTask task) {
      this.resolveHud();
      FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      if (this.hud.getWatermarkEnabled().getValue() && this.hud.getWatermarkMode().getValue() == HUD.WatermarkMode.INFORMATIVE) {
         String[] infoSegments = this.buildInfoSegments();
         String[] iconGlyphs = this.buildIconGlyphs();
         String infoText = String.join("    §7·  §r", infoSegments);
         String iconText = String.join("", iconGlyphs);
         float width = fontRenderer.getTextWidth(infoText, 5.85F) + iconFont.getTextWidth(iconText, 5.85F) + 11.0F;
         float height = fontRenderer.getLineHeight(5.85F) + 11.0F;
         this.computeColor(
            0.0F, this.hud, this.hud.getWatermarkColorMode().getValue(), this.hud.getWatermarkColorOne().getValue(), this.hud.getWatermarkColorTwo().getValue()
         );
         task.addCommand(RoundedRectangleCommand.of(7.0F, 8.0F, width + 0.5F, height + 0.5F, 6.5F, Color.BLACK.getRGB()));
      }
   }

   @Override
   public void setTextTask(RenderTask task) {
      this.resolveHud();
      MsdfFontRenderer fontRenderer = (MsdfFontRenderer)this.hud.getCustomFont().getValue().getFontSupplier().get();
      MsdfFontRenderer iconFont = (MsdfFontRenderer)Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      if (this.hud.getTitleTask() != null && this.hud.getWatermarkEnabled().getValue()) {
         switch ((HUD.WatermarkMode)this.hud.getWatermarkMode().getValue()) {
            case TEXT:
               if (this.hud.getWatermarkCustomFont().getValue()) {
                  fontRenderer.drawWithShadow(
                     this.hud.getTitleTask(),
                     this.hud.getClientName().getValue().replace("&", "§"),
                     9.0F,
                     2.0F,
                     2.0F,
                     this.computeColor(
                        0.0F,
                        this.hud,
                        this.hud.getWatermarkColorMode().getValue(),
                        this.hud.getWatermarkColorOne().getValue(),
                        this.hud.getWatermarkColorTwo().getValue()
                     )
                  );
               }
               break;
            case INFORMATIVE:
               String[] infoSegments = this.buildInfoSegments();
               String[] iconGlyphs = this.buildIconGlyphs();
               float offsetX = 0.0F;
               float height = fontRenderer.getLineHeight(5.85F) + 11.0F;

               for (int i = 0; i < infoSegments.length; i++) {
                  String segment = infoSegments[i];
                  boolean hasIcon = i > 0;
                  boolean hasSeparator = i < infoSegments.length - 1;
                  int iconIndex = i - 1;
                  float iconWidth = hasIcon ? iconFont.getTextWidth(iconGlyphs[iconIndex], 5.85F) : 0.0F;
                  float separatorWidth = hasSeparator ? fontRenderer.getTextWidth("  §7§l·§r  ", 5.85F) : 0.0F;
                  String separator = hasSeparator ? "  §7§l·§r  " : "";
                  String formattedSegment = hasIcon ? "  " + segment : segment;
                  fontRenderer.drawWithOffset(
                     this.hud.getTitleTask(),
                     formattedSegment + separator,
                     5.85F,
                     12.75F + offsetX + iconWidth,
                     8.25F + height / 2.0F - fontRenderer.getLineHeight(5.85F) / 2.0F + 0.5F,
                     hasIcon
                        ? -1
                        : this.computeColor(
                           0.0F,
                           this.hud,
                           this.hud.getWatermarkColorMode().getValue(),
                           this.hud.getWatermarkColorOne().getValue(),
                           this.hud.getWatermarkColorTwo().getValue()
                        ),
                     true,
                     false,
                     0.0F,
                     -0.02F
                  );
                  if (hasIcon) {
                     iconFont.drawInternal(
                        this.hud.getIconTask(),
                        iconGlyphs[iconIndex],
                        5.85F,
                        12.75F + offsetX,
                        8.25F + height / 2.0F - iconFont.getTextHeight(iconGlyphs[iconIndex], 5.85F) / 4.0F,
                        -3355444,
                        true,
                        false,
                        0.0F
                     );
                  }

                  offsetX += fontRenderer.getTextWidth(formattedSegment, 5.85F) + separatorWidth + iconWidth;
               }
         }
      }
   }

   @Override
   public void renderPost() {
      this.resolveHud();
      if (this.hud.getWatermarkEnabled().getValue()) {
         switch ((HUD.WatermarkMode)this.hud.getWatermarkMode().getValue()) {
            case TEXT:
               if (!this.hud.getWatermarkCustomFont().getValue()) {
                  minecraft.fontRendererObj
                     .drawStringWithShadow(
                        this.hud.getClientName().getValue().replace("&", "§"),
                        2.0F,
                        2.0F,
                        this.computeColor(
                           0.0F,
                           this.hud,
                           this.hud.getWatermarkColorMode().getValue(),
                           this.hud.getWatermarkColorOne().getValue(),
                           this.hud.getWatermarkColorTwo().getValue()
                        )
                     );
               }
               break;
            case LOGO:
               GlStateManager.enableTexture2D();
               Minecraft.getMinecraft().getTextureManager().bindTexture(this.LOGO_TEXTURE);
               if (!this.textureFilterSet) {
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                  this.textureFilterSet = true;
               }

               RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.TEXTURE).build();
               renderTask.addCommand(TextureCommand.of(4.0F, 4.0F, 68.0F, 68.0F, 0.0F, 0.0F, 1.0F, 1.0F, this.hud.getWatermarkColorOne().getValue()));
               int colorOne = -1;
               int colorTwo = -1;
               if (this.hud.getWatermarkColorMode().getValue() != ColorMode.STATIC) {
                  switch ((ColorMode)this.hud.getWatermarkColorMode().getValue()) {
                     case GRADIENT :
                        colorOne = this.hud.getWatermarkColorOne().getValue();
                        colorTwo = this.hud.getWatermarkColorTwo().getValue();
                        break;
                     case PULSING :
                        colorOne = this.hud.getWatermarkColorOne().getValue();
                        colorTwo = new Color(this.hud.getWatermarkColorOne().getValue()).darker().darker().getRGB();
                  }

                  renderTask.addRenderState(GradientRenderState.of(colorOne, colorTwo, 1.0F, 0.0F, false));
               }

               if (this.hud.getWatermarkColorMode().getValue() == ColorMode.SYNC) {
                  switch ((HUD.TextColorMode)this.hud.getTextColor().getValue()) {
                     case GRADIENT :
                        colorOne = this.hud.getAlColorOne().getValue();
                        colorTwo = this.hud.getAlColorTwo().getValue();
                        break;
                     case PULSING :
                        colorOne = this.hud.getAlColorOne().getValue();
                        colorTwo = new Color(this.hud.getAlColorOne().getValue()).darker().darker().getRGB();
                        break;
                     case MOON :
                     case ASTOLFO :
                        colorOne = this.getWaveColor(0.0F);
                        colorTwo = this.getWaveColor(10.0F);
                  }

                  renderTask.addRenderState(GradientRenderState.of(colorOne, colorTwo, 1.0F, 0.0F, false));
               }

               RenderTransformState.setScale(this.hud.getScale().getValue(), this.hud.getScale().getValue(), 1.0F);
               renderTask.render();
               RenderTransformState.resetScale();
               GlStateManager.bindTexture(0);
         }
      }
   }

   public int getWaveColor(float offset) {
      return this.computeColor(offset, 4500.0);
   }

   public int computeColor(float offset, double period) {
      long currentTime = System.currentTimeMillis();
      int color;
      switch ((HUD.TextColorMode)this.hud.getTextColor().getValue()) {
         case MOON :
            long wavePeriod = (long)(1.5 * period);
            int[] waveColors = new int[]{-7196417, -50248, -14423809, -7196417, -50248, -14423809};
            color = GradientUtil.getColorAt(waveColors, (float)((currentTime + (int)offset * -100L) % wavePeriod) / ((float)wavePeriod / 2.0F));
            break;
         case ASTOLFO :
            long astolfoPeriod = (long)(1.5 * period);
            int[] astolfoColors = new int[]{-295297, -8093701, -8523526, -8523526, -8093701, -295297};
            color = GradientUtil.getColorAt(astolfoColors, (float)((currentTime + (int)offset * -100L) % astolfoPeriod) / ((float)astolfoPeriod / 2.0F));
            break;
         default:
            return this.hud.getAlColorOne().getValue();
      }

      return color;
   }

   private String[] buildInfoSegments() {
      String serverIp = "Singleplayer";
      if (minecraft.getCurrentServerData() != null) {
         serverIp = minecraft.getCurrentServerData().serverIP;
      }

      return new String[]{
         "moon", "256ae016d1ab5f21124f38e269e06bb26cfdc63d".substring(0, 7), Moon.getInstance().getUsername(), serverIp, Minecraft.getDebugFPS() + " fps"
      };
   }

   private String[] buildIconGlyphs() {
      return new String[]{"\ue86f", "\ue7fd", "\ue157", "\ue8e5"};
   }

   private void resolveHud() {
      if (this.hud == null) {
         this.hud = Moon.getInstance().getModuleManager().getModule(HUD.class);
      }
   }
}
