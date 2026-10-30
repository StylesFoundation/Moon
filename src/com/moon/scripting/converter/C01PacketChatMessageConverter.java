package com.moon.scripting.converter;

import net.minecraft.network.play.client.C01PacketChatMessage;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C01PacketChatMessageConverter extends PacketConverter<C01PacketChatMessage> {
   public C01PacketChatMessageConverter(Class<C01PacketChatMessage> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C01PacketChatMessage c01PacketChatMessage) {
      LuaTable table = new LuaTable();
      table.set("message", LuaValue.valueOf(c01PacketChatMessage.getMessage()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C01PacketChatMessage c01PacketChatMessage) {
      String message = luaTable.get("message").tojstring();
      if (message.length() > 100) {
         message = message.substring(0, 100);
      }

      c01PacketChatMessage.Method354(message);
   }
}
