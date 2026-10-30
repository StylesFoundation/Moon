package com.moon.scripting.converter;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C03PacketPlayerConverter extends PacketConverter<C03PacketPlayer> {
   public C03PacketPlayerConverter(Class<C03PacketPlayer> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C03PacketPlayer c03PacketPlayer) {
      LuaTable table = new LuaTable();
      table.set("on_ground", LuaValue.valueOf(c03PacketPlayer.isOnGround()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C03PacketPlayer c03PacketPlayer) {
      boolean onGround = luaTable.get("on_ground").toboolean();
      c03PacketPlayer.Method487(onGround);
   }
}
