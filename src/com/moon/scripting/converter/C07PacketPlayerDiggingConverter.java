package com.moon.scripting.converter;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C07PacketPlayerDiggingConverter extends PacketConverter<C07PacketPlayerDigging> {
   public C07PacketPlayerDiggingConverter(Class<C07PacketPlayerDigging> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C07PacketPlayerDigging c07PacketPlayerDigging) {
      LuaTable table = new LuaTable();
      BlockPos pos = c07PacketPlayerDigging.getPosition();
      table.set("status", LuaValue.valueOf(c07PacketPlayerDigging.getStatus().ordinal()));
      table.set("x", LuaValue.valueOf(pos.getX()));
      table.set("y", LuaValue.valueOf(pos.getY()));
      table.set("z", LuaValue.valueOf(pos.getZ()));
      table.set("facing", LuaValue.valueOf(c07PacketPlayerDigging.getFacing().ordinal()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C07PacketPlayerDigging c07PacketPlayerDigging) {
      int x = luaTable.get("x").toint();
      int y = luaTable.get("y").toint();
      int z = luaTable.get("z").toint();
      c07PacketPlayerDigging.Method702(C07PacketPlayerDigging.Action.values()[luaTable.get("status").toint()]);
      c07PacketPlayerDigging.Method700(new BlockPos(x, y, z));
      c07PacketPlayerDigging.Method701(EnumFacing.values()[luaTable.get("facing").toint()]);
   }
}
