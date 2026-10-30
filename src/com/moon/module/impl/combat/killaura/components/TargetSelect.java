package com.moon.module.impl.combat.killaura.components;

import com.moon.Moon;
import com.moon.module.impl.combat.AntiBot;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.combat.Teams;
import com.moon.module.impl.combat.killaura.TargetSelector;
import com.moon.module.impl.combat.killaura.select.DynamicTargetSelector;
import com.moon.module.impl.combat.killaura.select.SingleTargetSelector;
import com.moon.module.impl.combat.killaura.select.SwitchTargetSelector;
import com.moon.storage.FriendStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class TargetSelect {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final Map<KillAura.SelectionMode, TargetSelector> selectors = new HashMap<>();
   private final KillAura killAura;
   private AntiBot antiBot;
   private Teams teams;
   private EntityLivingBase target;

   public void enable() {
      this.target = null;
      this.resolveSelector();
      this.selectors.get(this.killAura.getSelectionMode().getValue()).onEnable();
   }

   public void disable() {
      this.target = null;
      this.resolveSelector();
      this.selectors.get(this.killAura.getSelectionMode().getValue()).onDisable();
   }

   public void updateTarget() {
      this.resolveModules();
      FriendStorage friendStorage = Moon.getInstance().getFriendStorage();
      this.resolveSelector();
      List<EntityLivingBase> validTargets = mc.theWorld
         .loadedEntityList
         .stream()
         .filter(
            entity -> entity.getDistanceToEntity(mc.thePlayer) <= this.killAura.getAimReach().getValue()
               && entity != mc.thePlayer
               && entity.getEntityId() != -84
               && entity.getEntityId() != -42
         )
         .filter(entity -> {
            if (!(entity instanceof EntityPlayer player && friendStorage.isFriend(player)) && !this.antiBot.isBot(entity.getEntityId())) {
               boolean targetInvisibles = false;
               boolean targetMatches = false;
               Map<KillAura.Targets, Boolean> targetsMap = this.killAura.getTargets().getValue();

               for (KillAura.Targets targetType : targetsMap.keySet()) {
                  boolean enabled = (Boolean)targetsMap.get(targetType);
                  if (enabled) {
                     if (targetType == KillAura.Targets.INVISIBLES) {
                        targetInvisibles = true;
                     } else if (targetType.getPredicate().test(entity)) {
                        targetMatches = true;
                     }
                  }
               }

               if (this.teams.isEnabled() && entity instanceof EntityPlayer teammate && this.teams.isOnSameTeam(teammate)) {
                  return false;
               } else if (!targetInvisibles && entity.isInvisible()) {
                  return false;
               } else {
                  return !(entity instanceof EntityLivingBase) ? false : targetMatches;
               }
            } else {
               return false;
            }
         })
         .map(entity -> (EntityLivingBase)entity)
         .toList();
      this.target = this.selectors.get(this.killAura.getSelectionMode().getValue()).selectTarget(validTargets);
   }

   public boolean hasTarget() {
      return this.target != null;
   }

   private void resolveModules() {
      if (this.antiBot == null || this.teams == null) {
         this.antiBot = Moon.getInstance().getModuleManager().getModule(AntiBot.class);
         this.teams = Moon.getInstance().getModuleManager().getModule(Teams.class);
      }
   }

   private void resolveSelector() {
      this.selectors.computeIfAbsent(this.killAura.getSelectionMode().getValue(), selectionMode -> {
         return switch (selectionMode) {
            case SINGLE -> new SingleTargetSelector(this.killAura);
            case SWITCH -> new SwitchTargetSelector(this.killAura);
            case DYNAMIC -> new DynamicTargetSelector(this.killAura);
         };
      });
   }

   public Map<KillAura.SelectionMode, TargetSelector> getSelectors() {
      return this.selectors;
   }

   public KillAura getKillAura() {
      return this.killAura;
   }

   public AntiBot getAntiBot() {
      return this.antiBot;
   }

   public Teams getTeams() {
      return this.teams;
   }

   public EntityLivingBase getTarget() {
      return this.target;
   }

   public TargetSelect(KillAura killAura) {
      this.killAura = killAura;
   }
}
