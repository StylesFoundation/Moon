package com.moon.scripting.converter;

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C09PacketHeldItemChangeConverter extends PacketConverter<C09PacketHeldItemChange> {
   public C09PacketHeldItemChangeConverter(Class<C09PacketHeldItemChange> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C09PacketHeldItemChange c09PacketHeldItemChange) {
      LuaTable table = new LuaTable();
      table.set("slot", LuaValue.valueOf(c09PacketHeldItemChange.getSlotId()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C09PacketHeldItemChange c09PacketHeldItemChange) {
      c09PacketHeldItemChange.Method897(luaTable.get("slot").toint());
   }
}
