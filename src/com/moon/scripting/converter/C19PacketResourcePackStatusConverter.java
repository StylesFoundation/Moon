package com.moon.scripting.converter;

import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C19PacketResourcePackStatusConverter extends PacketConverter<C19PacketResourcePackStatus> {
   public C19PacketResourcePackStatusConverter(Class<C19PacketResourcePackStatus> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C19PacketResourcePackStatus packet) {
      LuaTable table = new LuaTable();
      table.set("hash", LuaValue.valueOf(packet.Method888()));
      table.set("status", LuaValue.valueOf(packet.Method889().ordinal()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C19PacketResourcePackStatus packet) {
      packet.Method890(luaTable.get("hash").tojstring());
      packet.Method891(C19PacketResourcePackStatus.Action.values()[luaTable.get("status").toint()]);
   }
}
