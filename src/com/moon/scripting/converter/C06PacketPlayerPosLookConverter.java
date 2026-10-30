package com.moon.scripting.converter;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C06PacketPlayerPosLookConverter extends PacketConverter<C03PacketPlayer.C06PacketPlayerPosLook> {
   public C06PacketPlayerPosLookConverter(Class<C03PacketPlayer.C06PacketPlayerPosLook> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C03PacketPlayer.C06PacketPlayerPosLook c06PacketPlayerPosLook) {
      LuaTable table = new LuaTable();
      table.set("on_ground", LuaValue.valueOf(c06PacketPlayerPosLook.isOnGround()));
      table.set("x", LuaValue.valueOf(c06PacketPlayerPosLook.getPositionX()));
      table.set("y", LuaValue.valueOf(c06PacketPlayerPosLook.Method469()));
      table.set("z", LuaValue.valueOf(c06PacketPlayerPosLook.getPositionY()));
      table.set("yaw", LuaValue.valueOf(c06PacketPlayerPosLook.getYaw()));
      table.set("pitch", LuaValue.valueOf(c06PacketPlayerPosLook.Method470()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C03PacketPlayer.C06PacketPlayerPosLook c06PacketPlayerPosLook) {
      boolean onGround = luaTable.get("on_ground").toboolean();
      double x = luaTable.get("x").todouble();
      double y = luaTable.get("y").todouble();
      double z = luaTable.get("z").todouble();
      float yaw = luaTable.get("yaw").tofloat();
      float pitch = luaTable.get("pitch").tofloat();
      c06PacketPlayerPosLook.Method482(x);
      c06PacketPlayerPosLook.Method483(y);
      c06PacketPlayerPosLook.Method484(z);
      c06PacketPlayerPosLook.Method485(yaw);
      c06PacketPlayerPosLook.Method486(pitch);
      c06PacketPlayerPosLook.Method487(onGround);
   }
}
