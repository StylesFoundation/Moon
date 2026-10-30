package com.moon.scripting.converter;

import net.minecraft.network.play.server.S02PacketChat;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S02PacketChatConverter extends PacketConverter<S02PacketChat> {
   public S02PacketChatConverter(Class<S02PacketChat> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S02PacketChat s02PacketChat) {
      LuaTable table = new LuaTable();
      table.set("message", LuaValue.valueOf(s02PacketChat.getChatComponent().getFormattedText()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S02PacketChat s02PacketChat) {
   }
}
