package com.moon.scripting.converter;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.Vec3;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C02PacketUseEntityConverter extends PacketConverter<C02PacketUseEntity> {
   public C02PacketUseEntityConverter(Class<C02PacketUseEntity> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C02PacketUseEntity c02PacketUseEntity) {
      LuaTable table = new LuaTable();
      Vec3 hitVec = c02PacketUseEntity.getHitVec();
      table.set("entity_id", LuaValue.valueOf(c02PacketUseEntity.Method931()));
      table.set("action", LuaValue.valueOf(c02PacketUseEntity.getAction().ordinal()));
      table.set("x", LuaValue.valueOf(hitVec.xCoord));
      table.set("y", LuaValue.valueOf(hitVec.yCoord));
      table.set("z", LuaValue.valueOf(hitVec.zCoord));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C02PacketUseEntity c02PacketUseEntity) {
      double x = luaTable.get("x").todouble();
      double y = luaTable.get("y").todouble();
      double z = luaTable.get("z").todouble();
      c02PacketUseEntity.Method927(luaTable.get("entity_id").toint());
      c02PacketUseEntity.Method928(C02PacketUseEntity.Action.values()[luaTable.get("action").toint()]);
      c02PacketUseEntity.Method929(new Vec3(x, y, z));
   }
}
