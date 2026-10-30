package com.moon.scripting.converter;

import net.minecraft.network.play.client.C11PacketEnchantItem;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C11PacketEnchantItemConverter extends PacketConverter<C11PacketEnchantItem> {
   public C11PacketEnchantItemConverter(Class<C11PacketEnchantItem> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C11PacketEnchantItem packet) {
      LuaTable table = new LuaTable();
      table.set("window_id", LuaValue.valueOf(packet.Method500()));
      table.set("button", LuaValue.valueOf(packet.Method501()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C11PacketEnchantItem packet) {
      packet.Method502(luaTable.get("window_id").toint());
      packet.Method503(luaTable.get("button").toint());
   }
}
