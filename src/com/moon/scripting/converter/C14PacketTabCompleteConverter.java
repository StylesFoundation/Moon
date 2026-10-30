package com.moon.scripting.converter;

import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C14PacketTabCompleteConverter extends PacketConverter<C14PacketTabComplete> {
   public C14PacketTabCompleteConverter(Class<C14PacketTabComplete> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C14PacketTabComplete packet) {
      LuaTable table = new LuaTable();
      table.set("message", LuaValue.valueOf(packet.getMessage()));
      BlockPos targetBlock = packet.getTargetBlock();
      if (targetBlock != null) {
         table.set("x", LuaValue.valueOf(targetBlock.getX()));
         table.set("y", LuaValue.valueOf(targetBlock.getY()));
         table.set("z", LuaValue.valueOf(targetBlock.getZ()));
      }

      return table;
   }

   public void applyFromTable(LuaTable luaTable, C14PacketTabComplete packet) {
      packet.Method688(luaTable.get("message").tojstring());
      if (!luaTable.get("x").isnil()) {
         int x = luaTable.get("x").toint();
         int y = luaTable.get("y").toint();
         int z = luaTable.get("z").toint();
         packet.Method689(new BlockPos(x, y, z));
      }
   }
}
