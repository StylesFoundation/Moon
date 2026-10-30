package com.moon.scripting.converter;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C05PacketPlayerLookConverter extends PacketConverter<C03PacketPlayer.C05PacketPlayerLook> {
   public C05PacketPlayerLookConverter(Class<C03PacketPlayer.C05PacketPlayerLook> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C03PacketPlayer.C05PacketPlayerLook c05PacketPlayerLook) {
      LuaTable table = new LuaTable();
      table.set("on_ground", LuaValue.valueOf(c05PacketPlayerLook.isOnGround()));
      table.set("yaw", LuaValue.valueOf(c05PacketPlayerLook.getYaw()));
      table.set("pitch", LuaValue.valueOf(c05PacketPlayerLook.Method470()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C03PacketPlayer.C05PacketPlayerLook c05PacketPlayerLook) {
      boolean onGround = luaTable.get("on_ground").toboolean();
      float yaw = luaTable.get("yaw").tofloat();
      float pitch = luaTable.get("pitch").tofloat();
      c05PacketPlayerLook.Method485(yaw);
      c05PacketPlayerLook.Method486(pitch);
      c05PacketPlayerLook.Method487(onGround);
   }
}
