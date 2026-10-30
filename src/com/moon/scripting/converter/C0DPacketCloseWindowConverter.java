package com.moon.scripting.converter;

import net.minecraft.network.play.client.C0DPacketCloseWindow;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C0DPacketCloseWindowConverter extends PacketConverter<C0DPacketCloseWindow> {
   public C0DPacketCloseWindowConverter(Class<C0DPacketCloseWindow> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C0DPacketCloseWindow packet) {
      LuaTable table = new LuaTable();
      table.set("window_id", LuaValue.valueOf(packet.Method775()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C0DPacketCloseWindow packet) {
      packet.Method776(luaTable.get("window_id").toint());
   }
}
