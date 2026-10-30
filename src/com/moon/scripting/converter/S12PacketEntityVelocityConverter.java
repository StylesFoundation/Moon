package com.moon.scripting.converter;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S12PacketEntityVelocityConverter extends PacketConverter<S12PacketEntityVelocity> {
   public S12PacketEntityVelocityConverter(Class<S12PacketEntityVelocity> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S12PacketEntityVelocity packet) {
      LuaTable table = new LuaTable();
      table.set("entity_id", LuaValue.valueOf(packet.getEntityID()));
      table.set("x", LuaValue.valueOf(packet.getMotionZ()));
      table.set("y", LuaValue.valueOf(packet.getMotionX()));
      table.set("z", LuaValue.valueOf(packet.getMotionY()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S12PacketEntityVelocity packet) {
      int x = luaTable.get("x").toint();
      int y = luaTable.get("y").toint();
      int z = luaTable.get("z").toint();
      packet.Method579(x);
      packet.Method580(y);
      packet.Method581(z);
   }
}
