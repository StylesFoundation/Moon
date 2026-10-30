package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityNameTagEvent;
import com.moon.events.impl.render.ShaderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.Teams;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.storage.dataset.Friend;
import com.moon.storage.dataset.Target;
import com.moon.util.client.PreciseTicker;
import com.moon.util.render.FrustumUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class Nametags extends Module {
   private final MultipleChoiceSetting<Nametags.Targets> targets = MultipleChoiceSetting.builder("targets", Nametags.Targets.class)
      .defaults("Targets", Nametags.Targets.PLAYER)
      .build();
   private final BooleanSetting showHealth = BooleanSetting.builder("health").defaults("Show health", true).build();
   private final BooleanSetting showDistance = BooleanSetting.builder("distance").defaults("Show distance", true).build();
   private final GroupSetting informationSettings = GroupSetting.builder().defaults("Information Settings", this.showHealth, this.showDistance).build();
   private final BooleanSetting separateInformation = BooleanSetting.builder("separate-information").defaults("Separate information", true).build();
   private final BooleanSetting roundBackground = BooleanSetting.builder("round").defaults("Round background", true).build();
   private final GroupSetting appearanceSettings = GroupSetting.builder()
      .defaults("Appearance Settings", this.separateInformation, this.roundBackground)
      .build();
   private Teams teams;
   private RenderTask backgroundTask;
   private RenderTask textTask;
   private final PreciseTicker ticker = new PreciseTicker();
   private boolean needsRebuild = true;

   public Nametags(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
      new Thread(() -> {
         while (true) {
            this.ticker.runAt(this.getTickRate());
            this.needsRebuild = true;
         }
      }, "Moon Nametag Ticking").start();
   }

   @Subscribe
   public void onShaderRender(ShaderEvent event) {
      if (this.needsRebuild || mc.gameSettings.enableVsync || mc.gameSettings.limitFramerate < 260) {
         this.rebuild();
      }

      GlStateManager.enableBlend();
      this.backgroundTask.render();
      this.textTask.render();
      this.needsRebuild = false;
   }

   @Subscribe
   public void onRenderEntityNameTag(RenderEntityNameTagEvent event) {
      if (this.isValidTarget(event.getEntity())) {
         event.setCancelled(true);
      }
   }

   private void rebuild() {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      float padding = this.roundBackground.getValue() ? 2.5F : 2.0F;
      if (this.teams == null) {
         this.teams = Moon.getInstance().getModuleManager().getModule(Teams.class);
      }

      if (this.backgroundTask != null) {
         this.backgroundTask.getState().releaseGpuResources();
      }

      if (this.textTask != null) {
         this.textTask.getState().releaseGpuResources();
      }

      this.textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).retainGpuBuffer().withGpuBuffers().build();
      this.backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).retainGpuBuffer().withGpuBuffers().build();

      for (Entity entity : mc.theWorld.loadedEntityList) {
         Vector4f screenBounds = entity.Method940();
         if (this.isValidTarget(entity) && FrustumUtil.isVisible(entity) && screenBounds != null) {
            float x = screenBounds.x;
            float y = screenBounds.y;
            float width = screenBounds.z - x;
            String name = entity.getName();
            if (entity instanceof EntityPlayer player) {
               Friend friend = Moon.getInstance().getFriendStorage().getFriend(player);
               Target target = Moon.getInstance().getTargetStorage().getTarget(player);
               boolean sameTeam = this.teams != null && this.teams.isOnSameTeam(player);
               if (friend != null) {
                  name = friend.getAlias().isEmpty() ? "§d§l" + friend.getName() : "§d§l" + friend.getAlias();
               }

               if (target != null) {
                  name = name + " §c§l[Target]";
               }

               if (sameTeam && this.teams.isEnabled()) {
                  name = name + " §a§l[Team]";
               }
            }

            if (!this.separateInformation.getValue() && entity instanceof EntityLivingBase livingEntity) {
               String healthStr = this.showHealth.getValue() ? "§c" + Math.round(livingEntity.getHealth()) + "♥ §r" : "";
               String distanceStr = this.showDistance.getValue() ? " §7" + Math.round(mc.thePlayer.getDistanceToEntity(livingEntity)) + "m" : "";
               name = healthStr + name + distanceStr;
            }

            float textWidth = fontRenderer.getTextWidth(name, 6.0F);
            float textHeight = fontRenderer.getTextHeight(name, 6.0F);
            RoundedRectangleCommand bgCommand = RoundedRectangleCommand.of(
               x + width / 2.0F - textWidth / 2.0F - padding,
               y - (textHeight + padding * 2.0F) - 1.0F,
               textWidth + padding * 2.0F,
               textHeight + padding * 2.0F,
               this.roundBackground.getValue() ? 3.0F : 0.0F,
               Integer.MIN_VALUE
            );
            if (!this.roundBackground.getValue()) {
               bgCommand.setPixelAligned(false);
            }

            fontRenderer.draw(this.textTask, name, 6.0F, x + width / 2.0F - textWidth / 2.0F, y - textHeight - padding - 0.5F, -1);
            this.backgroundTask.addCommand(bgCommand);
            if (this.separateInformation.getValue()) {
               if (this.showHealth.getValue()) {
                  String healthText = "§c" + Math.round(((EntityLivingBase)entity).getHealth()) + "♥";
                  float healthWidth = fontRenderer.getTextWidth(healthText, 6.0F);
                  RoundedRectangleCommand healthBgCommand = RoundedRectangleCommand.of(
                     x + width / 2.0F - textWidth / 2.0F - healthWidth - 2.0F - padding * 2.0F,
                     y - (textHeight + padding * 2.0F) - 1.0F,
                     healthWidth + padding * 2.0F,
                     textHeight + padding * 2.0F,
                     this.roundBackground.getValue() ? 3.0F : 0.0F,
                     Integer.MIN_VALUE
                  );
                  if (!this.roundBackground.getValue()) {
                     healthBgCommand.setPixelAligned(false);
                  }

                  fontRenderer.draw(this.textTask, healthText, 6.0F, x + width / 2.0F - textWidth / 2.0F - healthWidth - 2.0F - padding, y - textHeight - padding - 0.5F, -1);
                  this.backgroundTask.addCommand(healthBgCommand);
               }

               if (this.showDistance.getValue()) {
                  String distanceText = "§7" + Math.round(mc.thePlayer.getDistanceToEntity(entity)) + "m";
                  float distanceWidth = fontRenderer.getTextWidth(distanceText, 6.0F);
                  RoundedRectangleCommand distanceBgCommand = RoundedRectangleCommand.of(
                     x + width / 2.0F + textWidth / 2.0F + 2.0F,
                     y - (textHeight + padding * 2.0F) - 1.0F,
                     distanceWidth + padding * 2.0F,
                     textHeight + padding * 2.0F,
                     this.roundBackground.getValue() ? 3.0F : 0.0F,
                     Integer.MIN_VALUE
                  );
                  if (!this.roundBackground.getValue()) {
                     distanceBgCommand.setPixelAligned(false);
                  }

                  fontRenderer.draw(this.textTask, distanceText, 6.0F, x + width / 2.0F + textWidth / 2.0F + 2.0F + padding, y - textHeight - padding - 0.5F, -1);
                  this.backgroundTask.addCommand(distanceBgCommand);
               }
            }
         }
      }
   }

   public boolean isValidTarget(Entity entity) {
      if (entity == mc.thePlayer) {
         return mc.gameSettings.thirdPersonView != 0;
      } else if (entity.getEntityId() == -84 || entity.getEntityId() == -42) {
         return false;
      } else if (entity.isInvisible() && !this.targets.hasChecked(Nametags.Targets.INVISIBLE)) {
         return false;
      } else if (entity instanceof EntityMob && this.targets.hasChecked(Nametags.Targets.MOB)) {
         return true;
      } else {
         return (entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntitySquid)
               && this.targets.hasChecked(Nametags.Targets.ANIMAL)
            ? true
            : entity instanceof EntityPlayer && this.targets.hasChecked(Nametags.Targets.PLAYER);
      }
   }

   public int getTickRate() {
      return Math.max(Minecraft.getDebugFPS() / 5, Display.getDesktopDisplayMode().getFrequency());
   }

   public enum Targets implements LabelEnum {
      PLAYER("Players"),
      ANIMAL("Animals"),
      MOB("Mobs"),
      INVISIBLE("Invisibles");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Targets(String label) {
         this.label = label;
      }
   }
}
