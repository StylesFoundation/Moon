package com.moon.scripting.converter;

import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C0FPacketConfirmTransactionConverter extends PacketConverter<C0FPacketConfirmTransaction> {
   public C0FPacketConfirmTransactionConverter(Class<C0FPacketConfirmTransaction> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C0FPacketConfirmTransaction packet) {
      LuaTable table = new LuaTable();
      table.set("window_id", LuaValue.valueOf(packet.getWindowId()));
      table.set("uid", LuaValue.valueOf(packet.getUid()));
      table.set("accepted", LuaValue.valueOf(packet.Method468()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C0FPacketConfirmTransaction packet) {
      int windowId = luaTable.get("window_id").toint();
      short uid = luaTable.get("uid").toshort();
      boolean accepted = luaTable.get("accepted").toboolean();
      packet.Method465(windowId);
      packet.Method466(uid);
      packet.Method467(accepted);
   }
}
