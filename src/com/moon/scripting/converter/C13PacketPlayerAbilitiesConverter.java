package com.moon.scripting.converter;

import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C13PacketPlayerAbilitiesConverter extends PacketConverter<C13PacketPlayerAbilities> {
   public C13PacketPlayerAbilitiesConverter(Class<C13PacketPlayerAbilities> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C13PacketPlayerAbilities packet) {
      LuaTable table = new LuaTable();
      table.set("invulnerable", LuaValue.valueOf(packet.Method290()));
      table.set("flying", LuaValue.valueOf(packet.Method291()));
      table.set("allow_flying", LuaValue.valueOf(packet.Method292()));
      table.set("creative", LuaValue.valueOf(packet.Method293()));
      table.set("fly_speed", LuaValue.valueOf(packet.Method294()));
      table.set("walk_speed", LuaValue.valueOf(packet.Method295()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C13PacketPlayerAbilities packet) {
      packet.Method296(luaTable.get("invulnerable").toboolean());
      packet.Method297(luaTable.get("flying").toboolean());
      packet.Method298(luaTable.get("allow_flying").toboolean());
      packet.Method299(luaTable.get("creative").toboolean());
      packet.Method300(luaTable.get("fly_speed").tofloat());
      packet.Method301(luaTable.get("walk_speed").tofloat());
   }
}
