package com.moon.scripting.converter;

import net.minecraft.network.play.server.S27PacketExplosion;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S27PacketExplosionConverter extends PacketConverter<S27PacketExplosion> {
   public S27PacketExplosionConverter(Class<S27PacketExplosion> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S27PacketExplosion packet) {
      LuaTable table = new LuaTable();
      table.set("x", LuaValue.valueOf(packet.getX()));
      table.set("y", LuaValue.valueOf(packet.getY()));
      table.set("z", LuaValue.valueOf(packet.getZ()));
      table.set("strength", LuaValue.valueOf(packet.getStrength()));
      table.set("relative_x", LuaValue.valueOf(packet.func_149149_c()));
      table.set("relative_y", LuaValue.valueOf(packet.func_149144_d()));
      table.set("relative_z", LuaValue.valueOf(packet.func_149147_e()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S27PacketExplosion packet) {
      double x = luaTable.get("x").todouble();
      double y = luaTable.get("y").todouble();
      double z = luaTable.get("z").todouble();
      float strength = luaTable.get("strength").tofloat();
      float relativeX = luaTable.get("relative_x").tofloat();
      float relativeY = luaTable.get("relative_y").tofloat();
      float relativeZ = luaTable.get("relative_z").tofloat();
      packet.Method403(x);
      packet.Method404(y);
      packet.Method405(z);
      packet.Method406(strength);
      packet.Method408(relativeX);
      packet.Method409(relativeY);
      packet.Method410(relativeZ);
   }
}
