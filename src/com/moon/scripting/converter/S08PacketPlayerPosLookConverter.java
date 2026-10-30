package com.moon.scripting.converter;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S08PacketPlayerPosLookConverter extends PacketConverter<S08PacketPlayerPosLook> {
   public S08PacketPlayerPosLookConverter(Class<S08PacketPlayerPosLook> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S08PacketPlayerPosLook packet) {
      LuaTable table = new LuaTable();
      table.set("x", LuaValue.valueOf(packet.getX()));
      table.set("y", LuaValue.valueOf(packet.getY()));
      table.set("z", LuaValue.valueOf(packet.getZ()));
      table.set("yaw", LuaValue.valueOf(packet.getYaw()));
      table.set("pitch", LuaValue.valueOf(packet.Method909()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S08PacketPlayerPosLook packet) {
      double x = luaTable.get("x").todouble();
      double y = luaTable.get("y").todouble();
      double z = luaTable.get("z").todouble();
      float yaw = luaTable.get("yaw").tofloat();
      float pitch = luaTable.get("pitch").tofloat();
      packet.Method910(x);
      packet.Method911(y);
      packet.Method912(z);
      packet.Method913(yaw);
      packet.Method914(pitch);
   }
}
