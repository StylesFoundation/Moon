package com.moon.module.impl.visual.hud.modulelist;

import com.moon.Moon;
import com.moon.events.impl.module.CategoryHideEvent;
import com.moon.events.impl.module.ModuleHideEvent;
import com.moon.events.impl.module.ModuleSuffixChangedEvent;
import com.moon.events.impl.module.ModuleToggleEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.setting.Setting;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.HudElement;
import com.moon.render.RenderTask;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.util.math.MathUtil;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.ScaledResolution;

public class ModuleListElement implements HudElement {
   private final List<Module> modules = new CopyOnWriteArrayList<>();
   private final List<Module> removalQueue = new CopyOnWriteArrayList<>();
   private final Map<Module, Integer> moduleColors = new HashMap<>();
   private long lastAnimationTime = System.currentTimeMillis();
   private Effects effects = null;
   private HUD hud = null;

   @Subscribe
   public void onCategoryVisibility(CategoryHideEvent event) {
      for (Module module : Moon.getInstance().getModuleManager().getModulesByCategory(event.getCategory())) {
         if (module.isEnabled() && !module.isHidden()) {
            boolean shouldRemove = !event.getState();
            if (shouldRemove) {
               this.removalQueue.add(module);
            } else {
               this.modules.add(module);
               module.getAnimation().setValue(0.0F);
            }
         }
      }
   }

   @Subscribe
   public void onModuleToggle(ModuleToggleEvent event) {
      this.resolveModules();
      Module module = event.getModule();
      if (!(module instanceof ClickGUI)) {
         if (!module.isHidden()) {
            if (event.isEnabled() && !this.modules.contains(module)) {
               if (this.hud.getShownCategories().hasChecked(module.getCategory())) {
                  this.modules.add(module);
                  module.getAnimation().setValue(0.0F);
               }
            } else {
               module.getAnimation().setValue(module.getAnimation().getValue());
            }

            this.sortModules();
         }
      }
   }

   @Subscribe
   public void onModuleHide(ModuleHideEvent event) {
      this.resolveModules();
      Module module = event.getModule();
      if (!(module instanceof ClickGUI)) {
         if (!event.isHidden() && event.isEnabled() && !this.modules.contains(module)) {
            if (this.hud.getShownCategories().hasChecked(module.getCategory())) {
               this.modules.add(module);
               module.getAnimation().setValue(0.0F);
            }
         } else if (event.isHidden()) {
            this.removalQueue.add(module);
         }

         this.sortModules();
      }
   }

   @Subscribe
   public void onModuleSuffixChanged(ModuleSuffixChangedEvent event) {
      this.resolveModules();
      this.sortModules();
   }

   public void reloadModules() {
      this.resolveModules();
      if (Moon.getInstance().getModuleManager() != null) {
         this.modules.clear();
         for (Module module : Moon.getInstance().getModuleManager().getModules()) {
            if (module.isEnabled() && !module.isHidden() && !(module instanceof ClickGUI)) {
               if (this.hud == null || this.hud.getShownCategories().hasChecked(module.getCategory())) {
                  this.modules.add(module);
                  module.getAnimation().setValue(1.0F);
               }
            }
         }
         this.sortModules();
      }
   }

   private int getModuleColor(Module module) {
      this.moduleColors.computeIfAbsent(module, m -> {
         Setting setting = m.getSetting("individual-module-color");
         return setting != null && setting.getValue() instanceof Integer ? (Integer)setting.getValue() : -1;
      });
      Integer color = this.moduleColors.get(module);
      return color != null ? color : -1;
   }

   private void sortModules() {
      this.modules
         .sort(
            Comparator.comparingDouble(
               module -> -this.getTextWidth(this.formatModuleName(module) + this.formatSuffix(module), this.hud.getAlFontSize().getValue())
            )
         );
      this.hud.setRebuildRequested(true);
   }

   @Override
   public void setRectangleTask(RenderTask task) {
      this.resolveModules();
   }

   @Override
   public void setRoundedTask(RenderTask task) {
      this.resolveModules();
      if (this.hud.getArrayListEnabled().getValue()) {
         if (this.effects != null && System.currentTimeMillis() - this.lastAnimationTime < 250L) {
            this.effects.getGaussianRenderer().requestRender();
            this.hud.setRebuildRequested(true);
         }

         this.renderEntries(task, false);
      }
   }

   @Override
   public void setBackgroundTask(RenderTask task) {
      this.resolveModules();
      if (this.hud.getArrayListEnabled().getValue()) {
         this.renderEntries(task, true);
      }
   }

   @Override
   public void setTextTask(RenderTask task) {
      this.resolveModules();
      if (this.hud.getArrayListEnabled().getValue()) {
         if (this.hud.getAlCustomFont().getValue()) {
            FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
            ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
            scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
            float yOffset = 0.0F;
            int index = 0;
            float sidebarOffset = this.hud.getAlSidebar().getValue() && this.hud.getSidebarType().getValue() == HUD.SidebarType.RIGHT ? 1.0F : 0.0F;

            for (Module module : this.modules) {
               int offset = this.hud.getAlOffset().getValue();
               float x = scaledResolution.Method237() - module.Method537() - this.hud.getAlHorizontalSpacing().getValue() - sidebarOffset - offset + 0.5F;
               float y = yOffset + offset + this.hud.getAlSpacing().getValue().intValue() / 2.0F - 0.5F;
               String suffix = this.formatSuffix(module);
               int moduleColor = this.getModuleColor(module);
               int textColor = this.hud.getTextColor().getValue() == HUD.TextColorMode.INDIVIDUAL
                  ? moduleColor
                  : this.getWaveColor(index * this.hud.getAlFadeOffset().getValue());
               String moduleName = this.formatModuleName(module);
               if (!this.hud.getAlTextShadow().getValue()) {
                  fontRenderer.draw(task, moduleName, this.hud.getAlFontSize().getValue(), x, y + 0.5F, textColor, true);
                  if (!suffix.isEmpty()) {
                     fontRenderer.draw(
                        this.hud.getTitleTask(),
                        suffix,
                        this.hud.getAlFontSize().getValue(),
                        x + fontRenderer.getTextWidth(moduleName, this.hud.getAlFontSize().getValue()),
                        y + 0.5F,
                        -1,
                        true
                     );
                  }
               } else {
                  fontRenderer.draw(this.hud.getTitleTask(), moduleName, this.hud.getAlFontSize().getValue(), x + 0.5F, y + 1.0F, Integer.MIN_VALUE, false);
                  fontRenderer.draw(task, moduleName, this.hud.getAlFontSize().getValue(), x, y + 0.5F, textColor, false);
                  if (!suffix.isEmpty()) {
                     fontRenderer.drawWithShadow(
                        this.hud.getTitleTask(),
                        suffix,
                        this.hud.getAlFontSize().getValue(),
                        x + fontRenderer.getTextWidth(moduleName, this.hud.getAlFontSize().getValue()),
                        y + 0.5F,
                        -1
                     );
                  }
               }

               yOffset += module.Method538();
               index++;
            }

            scaledResolution.Method236();

            for (Module module : this.removalQueue) {
               this.modules.remove(module);
            }

            this.removalQueue.clear();
         }
      }
   }

   @Override
   public void renderPost() {
      this.resolveModules();
      if (this.hud.getArrayListEnabled().getValue()) {
         if (!this.hud.getAlCustomFont().getValue()) {
            ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
            scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
            float yOffset = 0.0F;
            int index = 0;
            float sidebarOffset = this.hud.getAlSidebar().getValue() && this.hud.getSidebarType().getValue() == HUD.SidebarType.RIGHT ? 1.0F : 0.0F;

            for (Module module : this.modules) {
               int offset = this.hud.getAlOffset().getValue();
               float x = scaledResolution.Method237() - module.Method537() - this.hud.getAlHorizontalSpacing().getValue() - sidebarOffset - offset + 0.5F;
               float y = yOffset + offset + this.hud.getAlSpacing().getValue().intValue() / 2.0F;
               String suffix = this.formatSuffix(module);
               int moduleColor = this.getModuleColor(module);
               int textColor = this.hud.getTextColor().getValue() == HUD.TextColorMode.INDIVIDUAL
                  ? moduleColor
                  : this.getWaveColor(index * this.hud.getAlFadeOffset().getValue());
               String moduleName = this.formatModuleName(module);
               if (!this.hud.getAlTextShadow().getValue()) {
                  minecraft.fontRendererObj.drawString(moduleName + suffix, x, y + 0.5F, textColor);
               } else {
                  minecraft.fontRendererObj.drawStringWithShadow(moduleName + suffix, x, y + 0.5F, textColor);
               }

               yOffset += module.Method538();
               index++;
            }

            scaledResolution.Method236();

            for (Module module : this.removalQueue) {
               this.modules.remove(module);
            }

            this.removalQueue.clear();
         }
      }
   }

   private void renderEntries(RenderTask task, boolean background) {
      if (this.hud.getArrayListEnabled().getValue()) {
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
         float yOffset = 2.5F;
         boolean hasSidebar = this.hud.getAlSidebar().getValue();
         float sidebarOffset = hasSidebar && this.hud.getSidebarType().getValue() == HUD.SidebarType.RIGHT ? 1.0F : 0.0F;
         Module prevModule = null;
         int index = 0;

         for (int i = 0; i < this.modules.size(); i++) {
            Module module = this.modules.get(i);
            Module nextModule = i < this.modules.size() - 1 ? this.modules.get(i + 1) : null;
            if (!module.getAnimation().isDone()) {
               this.lastAnimationTime = System.currentTimeMillis();
            }

            if (!module.isEnabled() && module.getAnimation().isDone() && !this.removalQueue.contains(module)) {
               this.removalQueue.add(module);
            }

            if (!background) {
               module.getAnimation().animate(0.0F, 1.0F, module.isEnabled());
            }

            float animProgress = (float)MathUtil.round(module.getAnimation().getValue(), 2);
            String suffix = this.formatSuffix(module);
            String formattedText = this.formatModuleName(module) + suffix;
            float textWidth = this.getTextWidth(formattedText, this.hud.getAlFontSize().getValue());
            float lineHeight = this.getLineHeight(this.hud.getAlFontSize().getValue());
            module.Method539(textWidth * animProgress);
            module.Method540((lineHeight + this.hud.getAlSpacing().getValue().intValue()) * Math.max(Math.min(animProgress, 1.0F), 0.0F));
            int offset = this.hud.getAlOffset().getValue();
            float spacingOffset = -this.hud.getAlHorizontalSpacing().getValue() - sidebarOffset - offset;
            float scaledWidth = scaledResolution.Method237();
            float rectX = scaledWidth - module.Method537() + spacingOffset + 1.0F;
            float nextX = nextModule == null ? 0.0F : scaledWidth - nextModule.Method537() + spacingOffset;
            float rectY = yOffset + offset;
            int roundingFactor = this.hud.getAlRounded().getValue() ? this.hud.getRoundingFactor().getValue() : 0;
            if (this.hud.getAlSidebar().getValue() && this.hud.getSidebarType().getValue() == HUD.SidebarType.OUTLINE) {
               roundingFactor = 0;
            }

            float diffX = module.getAnimation().isDone() ? Math.abs(nextX - rectX) : roundingFactor;
            float cornerRadius = Math.min(roundingFactor, diffX);
            float rectHeight = lineHeight + this.hud.getAlSpacing().getValue().intValue() + 1.0F;
            if (this.hud.getAlBackground().getValue()) {
               int waveColor = this.getWaveColor(index * this.hud.getAlFadeOffset().getValue());
               RoundedRectangleCommand bgCommand = RoundedRectangleCommand.of(
                  rectX - this.hud.getAlHorizontalSpacing().getValue(),
                  rectY - 3.5F,
                  textWidth + this.hud.getAlHorizontalSpacing().getValue() * 2.0F,
                  rectHeight,
                  cornerRadius,
                  background
                     ? (this.hud.getBackgroundColoredGlow().getValue() ? waveColor : Color.BLACK.getRGB())
                     : new RenderColor(this.hud.getBackgroundColor().getValue())
                        .withAlphaFraction(this.hud.getBackgroundOpacity().getValue().intValue() / 255.0F)
                        .getRGB()
               );
               if (i != this.modules.size() - 1 || hasSidebar && this.hud.getSidebarType().getValue() == HUD.SidebarType.RIGHT) {
                  bgCommand.setRoundBottomLeft(false);
               }

               if (prevModule != null) {
                  bgCommand.setRoundTopRight(false);
               }

               if (hasSidebar && this.hud.getSidebarType().getValue() != HUD.SidebarType.RIGHT || prevModule != null) {
                  bgCommand.setRoundTopLeft(false);
               }

               if (hasSidebar && this.hud.getSidebarType().getValue() == HUD.SidebarType.RIGHT) {
                  bgCommand.setRoundTopRight(false);
               }

               if (hasSidebar && this.hud.getSidebarType().getValue() != HUD.SidebarType.RIGHT) {
                  bgCommand.setRoundBottomRight(false);
               }

               bgCommand.setPixelAligned(false);
               task.addCommand(bgCommand);
            }

            if (hasSidebar && !background) {
               int moduleColor = this.getModuleColor(module);
               int textColor = this.hud.getTextColor().getValue() == HUD.TextColorMode.INDIVIDUAL
                  ? moduleColor
                  : this.getWaveColor(index * this.hud.getAlFadeOffset().getValue());
               int sidebarColor = this.hud.getSidebarColor().getValue() == HUD.SidebarColorMode.SYNC
                  ? textColor
                  : this.getSidebarColor(index * this.hud.getSidebarFadeOffset().getValue());
               float barX = rectX + textWidth + this.hud.getAlHorizontalSpacing().getValue() - 1.5F;
               float barY = rectY - 3.5F;
               switch ((HUD.SidebarType)this.hud.getSidebarType().getValue()) {
                  case RIGHT:
                     RoundedRectangleCommand rightBarCommand = RoundedRectangleCommand.of(
                        barX, barY, 2.0F, rectHeight, this.hud.getAlRounded().getValue() ? 1.0F : 0.0F, sidebarColor
                     );
                     rightBarCommand.setRoundTopLeft(false);
                     rightBarCommand.setRoundTopRight(prevModule == null);
                     rightBarCommand.setRoundBottomRight(false);
                     rightBarCommand.setRoundBottomLeft(i == this.modules.size() - 1);
                     rightBarCommand.setPixelAligned(false);
                     this.hud.getSidebarTask().addCommand(rightBarCommand);
                     break;
                  case LEFT:
                     barX = rectX - this.hud.getAlHorizontalSpacing().getValue() - 1.0F;
                     RoundedRectangleCommand leftBarCommand = RoundedRectangleCommand.of(
                        barX, barY, 2.0F, rectHeight, this.hud.getAlRounded().getValue() ? 2.0F : 0.0F, sidebarColor
                     );
                     leftBarCommand.setRoundTopRight(false);
                     leftBarCommand.setRoundTopLeft(prevModule == null);
                     leftBarCommand.setRoundBottomLeft(false);
                     leftBarCommand.setRoundBottomRight(i == this.modules.size() - 1 || cornerRadius > 1.0F);
                     leftBarCommand.setPixelAligned(false);
                     this.hud.getSidebarTask().addCommand(leftBarCommand);
                     break;
                  case OUTLINE:
                     float widthDiff = nextModule != null
                        ? Math.abs(textWidth - this.getTextWidth(this.formatModuleName(nextModule) + this.formatSuffix(nextModule), this.hud.getAlFontSize().getValue()))
                        : 0.0F;
                     barX = rectX - this.hud.getAlHorizontalSpacing().getValue() - 1.0F;
                     RoundedRectangleCommand topOutlineCommand = RoundedRectangleCommand.of(
                        barX, barY - 1.0F, textWidth + this.hud.getAlHorizontalSpacing().getValue() * 2.0F + 2.0F, 2.0F, 0.0F, sidebarColor
                     );
                     RoundedRectangleCommand leftOutlineCommand = RoundedRectangleCommand.of(barX, barY, 2.0F, rectHeight, 0.0F, sidebarColor);
                     RoundedRectangleCommand rightOutlineCommand = RoundedRectangleCommand.of(
                        rectX + textWidth + this.hud.getAlHorizontalSpacing().getValue() - 1.0F, barY, 2.0F, rectHeight, 0.0F, sidebarColor
                     );
                     leftOutlineCommand.setPixelAligned(false);
                     rightOutlineCommand.setPixelAligned(false);
                     this.hud.getSidebarTask().addCommand(leftOutlineCommand);
                     this.hud.getSidebarTask().addCommand(rightOutlineCommand);
                     if (module.getAnimation().isDone() && module.isEnabled()) {
                        RoundedRectangleCommand bottomOutlineCommand = RoundedRectangleCommand.of(
                           barX,
                           barY + rectHeight - 1.0F,
                           nextModule == null ? textWidth + this.hud.getAlHorizontalSpacing().getValue() * 2.0F + 2.0F : widthDiff + 2.0F,
                           2.0F,
                           0.0F,
                           sidebarColor
                        );
                        bottomOutlineCommand.setPixelAligned(false);
                        this.hud.getSidebarTask().addCommand(bottomOutlineCommand);
                     }

                     if (prevModule == null) {
                        this.hud.getSidebarTask().addCommand(topOutlineCommand);
                     }
               }
            }

            yOffset += module.Method538();
            prevModule = module;
            index++;
         }

         scaledResolution.Method236();
      }
   }

   private float getTextWidth(String text, float fontSize) {
      return this.hud.getAlCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getTextWidth(text, fontSize)
         : minecraft.fontRendererObj.getStringWidth(text);
   }

   private float getLineHeight(float fontSize) {
      return this.hud.getAlCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getLineHeight(fontSize)
         : minecraft.fontRendererObj.FONT_HEIGHT;
   }

   public int getWaveColor(float offset) {
      return this.computeColor(offset, 4500.0);
   }

   public int computeColor(float offset, double period) {
      long time = System.currentTimeMillis();
      int colorOne = this.hud.getAlColorOne().getValue();
      int colorTwo = this.hud.getAlColorTwo().getValue();
      int color;
      switch ((HUD.TextColorMode)this.hud.getTextColor().getValue()) {
         case PULSING : {
            long adjustedPeriod = (long)(1.5 * period);
            int darkerColor = new Color(colorOne).darker().darker().getRGB();
            int[] gradientColors = new int[]{colorOne, darkerColor, colorOne, darkerColor, colorOne, darkerColor, colorOne};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         case GRADIENT : {
            long adjustedPeriod = (long)(1.5 * period);
            int[] gradientColors = new int[]{colorOne, colorTwo, colorOne, colorTwo, colorOne, colorTwo, colorOne};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         case MOON : {
            long adjustedPeriod = (long)(1.5 * period);
            int[] gradientColors = new int[]{-7196417, -50248, -14423809, -7196417, -50248, -14423809};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         case ASTOLFO : {
            long adjustedPeriod = (long)(1.5 * period);
            int[] gradientColors = new int[]{-295297, -8093701, -8523526, -8523526, -8093701, -295297};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         default:
            return this.hud.getAlColorOne().getValue();
      }

      return color;
   }

   public int getSidebarColor(float offset) {
      return this.computeSidebarColor(offset, 4500.0);
   }

   public int computeSidebarColor(float offset, double period) {
      long time = System.currentTimeMillis();
      int colorOne = this.hud.getSidebarColorOne().getValue();
      int colorTwo = this.hud.getSidebarColorTwo().getValue();
      int color;
      switch ((HUD.SidebarColorMode)this.hud.getSidebarColor().getValue()) {
         case PULSING : {
            long adjustedPeriod = (long)(1.5 * period);
            int darkerColor = new Color(colorOne).darker().darker().getRGB();
            int[] gradientColors = new int[]{colorOne, darkerColor, colorOne, darkerColor, colorOne, darkerColor, colorOne};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         case GRADIENT : {
            long adjustedPeriod = (long)(1.5 * period);
            int[] gradientColors = new int[]{colorOne, colorTwo, colorOne, colorTwo, colorOne, colorTwo, colorOne};
            color = GradientUtil.getColorAt(gradientColors, (float)((time + (int)offset * -100L) % adjustedPeriod) / ((float)adjustedPeriod / 2.0F));
            break;
         }
         default:
            return this.hud.getAlColorOne().getValue();
      }

      return color;
   }

   private String formatSuffix(Module module) {
      if (!module.getSuffix().isEmpty()) {
         String formattedSuffix = switch ((HUD.SuffixType)this.hud.getSuffixType().getValue()) {
            case DASH -> " §7- " + module.getSuffix();
            case SPACE -> " §7" + module.getSuffix();
            case BRACES -> " §7(" + module.getSuffix() + ")";
            case NONE -> "";
            default -> throw new IncompatibleClassChangeError();
         };
         return this.hud.getNameFormatting().getValue().getFormatter().apply(formattedSuffix);
      } else {
         return "";
      }
   }

   private String formatModuleName(Module module) {
      return this.hud.getNameFormatting().getValue().getFormatter().apply(module.getDisplayName());
   }

   private void resolveModules() {
      if (this.effects == null) {
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      if (this.hud == null) {
         this.hud = Moon.getInstance().getModuleManager().getModule(HUD.class);
      }
   }

   public List<Module> getModules() {
      return this.modules;
   }

   public List<Module> getRemovalQueue() {
      return this.removalQueue;
   }

   public Map<Module, Integer> getModuleColors() {
      return this.moduleColors;
   }

   public long getLastAnimationTime() {
      return this.lastAnimationTime;
   }

   public Effects getEffects() {
      return this.effects;
   }

   public HUD getHud() {
      return this.hud;
   }
}
