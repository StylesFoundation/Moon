package com.moon.scripting.api.functions.impl;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.visual.Effects;
import com.moon.scripting.api.functions.ClientFunctions;
import com.moon.util.client.ChatUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ClientFunctionsImpl implements ClientFunctions {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final Map<String, String> formattingTags = new HashMap<>();
   private KillAura killAura;
   private Effects effects;

   @Override
   public void print(String message) {
      for (String tag : formattingTags.keySet()) {
         String replacement = formattingTags.get(tag);
         message = message.replaceAll(tag, replacement);
      }

      ChatUtil.sendMessage(message);
   }

   @Override
   public void message(String message) {
      if (mc.thePlayer != null) {
         mc.thePlayer.sendChatMessage(message);
      }
   }

   @Override
   public void set_timer_speed(double speed) {
      Minecraft.getMinecraft().timer.timerSpeed = (float)speed;
   }

   @Override
   public void force_effects_redraw() {
      if (this.effects == null) {
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      this.effects.getGaussianRenderer().requestRender();
      this.effects.getKawaseRenderer().setNeedsRender(true);
   }

   @Override
   public boolean is_key_down(int key) {
      return Keyboard.isKeyDown(key);
   }

   @Override
   public boolean is_mouse_down(int button) {
      return Mouse.isButtonDown(button);
   }

   @Override
   public int fps() {
      return Minecraft.getDebugFPS();
   }

   @Override
   public long time() {
      return System.currentTimeMillis();
   }

   @Override
   public int current_kill_aura_target() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }

      return this.killAura.getTargetSelector().getTarget() == null ? -1 : this.killAura.getTargetSelector().getTarget().getEntityId();
   }

   @Override
   public float get_scaled_width() {
      return ScaleHandler.getScaledResolution().Method237();
   }

   @Override
   public float get_scaled_height() {
      return ScaleHandler.getScaledResolution().Method238();
   }

   @Override
   public float get_width() {
      return mc.displayWidth;
   }

   @Override
   public float get_height() {
      return mc.displayHeight;
   }

   static {
      formattingTags.put("§0", "<black>");
      formattingTags.put("§1", "<dark_blue>");
      formattingTags.put("§2", "<dark_green>");
      formattingTags.put("§3", "<dark_aqua>");
      formattingTags.put("§4", "<dark_red>");
      formattingTags.put("§5", "<dark_purple>");
      formattingTags.put("§6", "<gold>");
      formattingTags.put("§7", "<gray>");
      formattingTags.put("§8", "<dark_gray>");
      formattingTags.put("§9", "<blue>");
      formattingTags.put("§a", "<green>");
      formattingTags.put("§b", "<aqua>");
      formattingTags.put("§c", "<red>");
      formattingTags.put("§d", "<light_purple>");
      formattingTags.put("§e", "<yellow>");
      formattingTags.put("§f", "<white>");
      formattingTags.put("§k", "<obfuscated>");
      formattingTags.put("§l", "<bold>");
      formattingTags.put("§m", "<strikethrough>");
      formattingTags.put("§n", "<underline>");
      formattingTags.put("§o", "<italic>");
      formattingTags.put("§r", "<reset>");
   }
}
