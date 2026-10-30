package com.moon.scripting.converter;

import net.minecraft.network.play.client.C0EPacketClickWindow;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C0EPacketClickWindowConverter extends PacketConverter<C0EPacketClickWindow> {
   public C0EPacketClickWindowConverter(Class<C0EPacketClickWindow> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C0EPacketClickWindow packet) {
      LuaTable table = new LuaTable();
      table.set("window_id", LuaValue.valueOf(packet.getWindowId()));
      table.set("slot", LuaValue.valueOf(packet.getSlotId()));
      table.set("button", LuaValue.valueOf(packet.getUsedButton()));
      table.set("action", LuaValue.valueOf(packet.getActionNumber()));
      table.set("mode", LuaValue.valueOf(packet.getMode()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C0EPacketClickWindow packet) {
      int slot = luaTable.get("slot").toint();
      packet.Method637(luaTable.get("window_id").toint());
      packet.Method639(luaTable.get("button").toint());
      packet.Method640(luaTable.get("action").toshort());
      if (mc != null && mc.thePlayer != null && slot != packet.Method632()) {
         packet.Method641(mc.thePlayer.inventory.getStackInSlot(slot));
      }

      packet.Method638(slot);
      packet.Method642(luaTable.get("mode").toint());
   }
}
