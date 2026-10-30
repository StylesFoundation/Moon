package com.moon.scripting;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.WorldRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.scripting.converter.PacketConverter;
import com.moon.scripting.converter.PacketConverterRegistry;
import com.moon.util.client.ChatUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class ScriptModule extends Module {
   private final Map<String, LuaValue> callbackCache = new HashMap<>();
   private final LuaTable callbacks;
   private Script script;

   public ScriptModule(String name, String description, String category, LuaTable callbacks) {
      super(name, description, category, ModuleCategory.SCRIPTS);
      this.callbacks = callbacks;
   }

   @Override
   public void onEnable() {
      this.invokeCallback("on_enable");
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.invokeCallback("on_disable");
      super.onDisable();
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE) {
         this.invokeCallback("on_tick");
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() != UpdateEvent.Sequence.BEFORE) {
         LuaTable eventTable = new LuaTable();
         eventTable.set("pre", LuaValue.valueOf(event.getSequence() == UpdateEvent.Sequence.PRE));
         eventTable.set("x", (double)event.getX());
         eventTable.set("y", (double)event.getY());
         eventTable.set("z", (double)event.getZ());
         eventTable.set("yaw", event.getYaw());
         eventTable.set("pitch", event.getPitch());
         eventTable.set("ground", LuaValue.valueOf(event.isOnGround()));
         this.invokeCallback("on_update", eventTable);
         event.setX(eventTable.get("x").todouble());
         event.setY(eventTable.get("y").todouble());
         event.setZ(eventTable.get("z").todouble());
         event.setYaw(eventTable.get("yaw").tofloat());
         event.setPitch(eventTable.get("pitch").tofloat());
         event.setOnGround(eventTable.get("ground").toboolean());
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      LuaTable moveTable = new LuaTable();
      moveTable.set("x", (double)event.getX());
      moveTable.set("y", (double)event.getY());
      moveTable.set("z", (double)event.getZ());
      this.invokeCallback("on_move", moveTable);
      event.setX(moveTable.get("x").todouble());
      event.setY(moveTable.get("y").todouble());
      event.setZ(moveTable.get("z").todouble());
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();
      PacketConverter converter = PacketConverterRegistry.getConverter(packet.getClass());
      EnumConnectionState state = EnumConnectionState.getFromPacket(packet);
      if (state != EnumConnectionState.HANDSHAKING && state != EnumConnectionState.LOGIN) {
         EnumPacketDirection direction = event.isSend() ? EnumPacketDirection.SERVERBOUND : EnumPacketDirection.CLIENTBOUND;
         Integer packetId = state.getPacketId(direction, packet);
         if (packetId == null) {
            return;
         }

         LuaTable packetTable = converter != null ? converter.toLuaTable(this.castPacket(packet)) : new LuaTable();
         packetTable.set("packet_id", LuaTable.valueOf(packetId));
         packetTable.set("cancel", LuaValue.valueOf(event.isCancelled()));
         this.invokeCallback(event.isSend() ? "on_packet_send" : "on_packet_receive", packetTable);
         if (converter != null) {
            converter.applyFromTable(packetTable, this.castPacket(packet));
         }

         event.setCancelled(packetTable.get("cancel").toboolean());
      }
   }

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      GlStateManager.enableBlend();
      boolean isPre = event.getSequence() == OverlayRenderEvent.Sequence.PRE;
      LuaTable renderTable = new LuaTable();
      renderTable.set("pre", LuaValue.valueOf(isPre));
      renderTable.set("partial_ticks", LuaValue.valueOf(mc.timer.renderPartialTicks));
      this.invokeCallback("on_overlay_render", renderTable);
   }

   @Subscribe
   public void onWorldRender(WorldRenderEvent event) {
      if (event.getSequence() == WorldRenderEvent.Sequence.PRE) {
         GlStateManager.enableBlend();
         LuaTable renderTable = new LuaTable();
         renderTable.set("partial_ticks", LuaValue.valueOf(mc.timer.renderPartialTicks));
         this.invokeCallback("on_world_render", renderTable);
      }
   }

   @Subscribe
   public void onEffectRender(EffectRenderEvent event) {
      GlStateManager.enableBlend();
      boolean isBlur = !event.isGaussian();
      LuaTable effectTable = new LuaTable();
      effectTable.set("blur", LuaValue.valueOf(isBlur));
      effectTable.set("partial_ticks", LuaValue.valueOf(mc.timer.renderPartialTicks));
      this.invokeCallback("on_effect_render", effectTable);
   }

   private <T> T castPacket(Object packet) {
      try {
         return (T)packet;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private void invokeCallback(String callbackName) {
      LuaValue callback = this.callbackCache.computeIfAbsent(callbackName, this.callbacks::get);
      if (!callback.isnil()) {
         try {
            callback.invoke();
         } catch (LuaError e) {
            ChatUtil.sendMessage("<red>Error while executing script module" + this.getName() + " " + e.getMessage());
         }
      }
   }

   private void invokeCallback(String callbackName, LuaTable arguments) {
      try {
         LuaValue callback = this.callbackCache.computeIfAbsent(callbackName, this.callbacks::get);
         if (!callback.isnil()) {
            try {
               callback.invoke(arguments);
            } catch (LuaError e) {
               ChatUtil.sendMessage("<red>Error while executing script module" + this.getName() + " " + e.getMessage());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public Map<String, LuaValue> getCallbackCache() {
      return this.callbackCache;
   }

   public LuaTable getCallbacks() {
      return this.callbacks;
   }

   public Script getScript() {
      return this.script;
   }

   public ScriptModule setScript(Script script) {
      this.script = script;
      return this;
   }
}
