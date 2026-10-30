package com.moon.scripting.converter;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C0BPacketEntityActionConverter extends PacketConverter<C0BPacketEntityAction> {
   public C0BPacketEntityActionConverter(Class<C0BPacketEntityAction> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C0BPacketEntityAction packet) {
      LuaTable table = new LuaTable();
      table.set("entity_id", LuaValue.valueOf(packet.Method607()));
      table.set("action", LuaValue.valueOf(packet.getAction().ordinal()));
      table.set("aux", LuaValue.valueOf(packet.getAuxData()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C0BPacketEntityAction packet) {
      packet.Method610(luaTable.get("entity_id").toint());
      packet.Method611(C0BPacketEntityAction.Action.values()[luaTable.get("action").toint()]);
      packet.Method612(luaTable.get("aux").toint());
   }
}
