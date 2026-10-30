package com.moon.scripting.converter;

import java.util.UUID;
import net.minecraft.network.play.client.C18PacketSpectate;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C18PacketSpectateConverter extends PacketConverter<C18PacketSpectate> {
   public C18PacketSpectateConverter(Class<C18PacketSpectate> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C18PacketSpectate packet) {
      LuaTable table = new LuaTable();
      table.set("uuid", LuaValue.valueOf(packet.Method232().toString()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C18PacketSpectate packet) {
      String uuidStr = luaTable.get("uuid").tojstring();
      packet.Method233(UUID.fromString(uuidStr));
   }
}
