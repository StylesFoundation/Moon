package com.moon.scripting.converter;

import net.minecraft.network.play.server.S48PacketResourcePackSend;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S48PacketResourcePackSendConverter extends PacketConverter<S48PacketResourcePackSend> {
   public S48PacketResourcePackSendConverter(Class<S48PacketResourcePackSend> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S48PacketResourcePackSend packet) {
      LuaTable table = new LuaTable();
      table.set("url", LuaValue.valueOf(packet.getURL()));
      table.set("hash", LuaValue.valueOf(packet.getHash()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S48PacketResourcePackSend packet) {
      packet.Method950(luaTable.get("url").tojstring());
      packet.Method951(luaTable.get("hash").tojstring());
   }
}
