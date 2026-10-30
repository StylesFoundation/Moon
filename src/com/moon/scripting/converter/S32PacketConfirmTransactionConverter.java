package com.moon.scripting.converter;

import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S32PacketConfirmTransactionConverter extends PacketConverter<S32PacketConfirmTransaction> {
   public S32PacketConfirmTransactionConverter(Class<S32PacketConfirmTransaction> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S32PacketConfirmTransaction packet) {
      LuaTable table = new LuaTable();
      table.set("window_id", LuaValue.valueOf(packet.getWindowId()));
      table.set("action_number", LuaValue.valueOf(packet.getActionNumber()));
      table.set("accepted", LuaValue.valueOf(packet.Method419()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S32PacketConfirmTransaction packet) {
      packet.Method420(luaTable.get("window_id").toint());
      packet.Method421(luaTable.get("action_number").toshort());
      packet.Method422(luaTable.get("accepted").toboolean());
   }
}
