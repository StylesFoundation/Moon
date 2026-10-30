package com.moon.scripting.converter;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C04PacketPlayerPositionConverter extends PacketConverter<C03PacketPlayer.C04PacketPlayerPosition> {
   public C04PacketPlayerPositionConverter(Class<C03PacketPlayer.C04PacketPlayerPosition> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C03PacketPlayer.C04PacketPlayerPosition c04PacketPlayerPosition) {
      LuaTable table = new LuaTable();
      table.set("on_ground", LuaValue.valueOf(c04PacketPlayerPosition.isOnGround()));
      table.set("x", LuaValue.valueOf(c04PacketPlayerPosition.getPositionX()));
      table.set("y", LuaValue.valueOf(c04PacketPlayerPosition.Method469()));
      table.set("z", LuaValue.valueOf(c04PacketPlayerPosition.getPositionY()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C03PacketPlayer.C04PacketPlayerPosition c04PacketPlayerPosition) {
      boolean onGround = luaTable.get("on_ground").toboolean();
      double x = luaTable.get("x").todouble();
      double y = luaTable.get("y").todouble();
      double z = luaTable.get("z").todouble();
      c04PacketPlayerPosition.Method482(x);
      c04PacketPlayerPosition.Method483(y);
      c04PacketPlayerPosition.Method484(z);
      c04PacketPlayerPosition.Method487(onGround);
   }
}
