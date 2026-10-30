package com.moon.module.impl.visual;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.RenderEntityArmorEvent;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import net.minecraft.entity.player.EntityPlayer;

public class ModelModifier extends Module {
   public final MultipleChoiceSetting<ModelModifier.Modifiers> selfModifiers = MultipleChoiceSetting.builder("self-modifiers", ModelModifier.Modifiers.class)
      .defaults("Self modifiers", ModelModifier.Modifiers.BABY)
      .build();
   public final MultipleChoiceSetting<ModelModifier.Modifiers> otherModifiers = MultipleChoiceSetting.builder("other-modifiers", ModelModifier.Modifiers.class)
      .defaults("Other modifiers", ModelModifier.Modifiers.BABY)
      .build();

   public ModelModifier(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onDisable() {
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
   }

   @Subscribe
   public void onRenderEntityArmor(RenderEntityArmorEvent event) {
      if (event.getEntity() instanceof EntityPlayer) {
         if (event.getEntity().getEntityId() == mc.thePlayer.getEntityId()) {
            event.setCancelled(this.selfModifiers.hasChecked(ModelModifier.Modifiers.HIDE_ARMOR));
         } else {
            event.setCancelled(this.otherModifiers.hasChecked(ModelModifier.Modifiers.HIDE_ARMOR));
         }
      }
   }

   @Subscribe(priority = Priority.HIGH)
   public void onRenderEntity(RenderEntityEvent event) {
      if (event.getEntity() instanceof EntityPlayer) {
         if (event.getEntity().getEntityId() == mc.thePlayer.getEntityId()) {
            event.setChild(this.selfModifiers.hasChecked(ModelModifier.Modifiers.BABY));
            event.setRiding(this.selfModifiers.hasChecked(ModelModifier.Modifiers.RIDING));
         } else {
            event.setChild(this.otherModifiers.hasChecked(ModelModifier.Modifiers.BABY));
            event.setRiding(this.otherModifiers.hasChecked(ModelModifier.Modifiers.RIDING));
         }
      }
   }

   public enum Modifiers implements LabelEnum {
      BABY("Baby"),
      RIDING("Riding"),
      HIDE_ARMOR("Hide armor");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Modifiers(String label) {
         this.label = label;
      }
   }
}
