package com.moon.scripting.converter;

import net.minecraft.network.play.client.C0CPacketInput;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C0CPacketInputConverter extends PacketConverter<C0CPacketInput> {
   public C0CPacketInputConverter(Class<C0CPacketInput> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C0CPacketInput packet) {
      LuaTable table = new LuaTable();
      table.set("strafe", LuaValue.valueOf(packet.Method961()));
      table.set("forward", LuaValue.valueOf(packet.Method962()));
      table.set("jumping", LuaValue.valueOf(packet.Method963()));
      table.set("sneaking", LuaValue.valueOf(packet.Method964()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C0CPacketInput packet) {
      packet.Method965(luaTable.get("strafe").tofloat());
      packet.Method966(luaTable.get("forward").tofloat());
      packet.Method967(luaTable.get("jumping").toboolean());
      packet.Method968(luaTable.get("sneaking").toboolean());
   }
}
