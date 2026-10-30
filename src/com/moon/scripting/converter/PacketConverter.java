package com.moon.scripting.converter;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import org.luaj.vm2.LuaTable;

public abstract class PacketConverter<T extends Packet> {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private final Class<T> packetType;

   public abstract LuaTable toLuaTable(T packet);

   public abstract void applyFromTable(LuaTable luaTable, T packet);

   public PacketConverter(Class<T> packetType) {
      this.packetType = packetType;
   }
}
