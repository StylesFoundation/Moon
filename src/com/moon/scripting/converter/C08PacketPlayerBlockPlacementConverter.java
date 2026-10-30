package com.moon.scripting.converter;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C08PacketPlayerBlockPlacementConverter extends PacketConverter<C08PacketPlayerBlockPlacement> {
   private final Minecraft c = Minecraft.getMinecraft();

   public C08PacketPlayerBlockPlacementConverter(Class<C08PacketPlayerBlockPlacement> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
      LuaTable table = new LuaTable();
      BlockPos pos = c08PacketPlayerBlockPlacement.getPosition();
      table.set("x", LuaValue.valueOf(pos.getX()));
      table.set("y", LuaValue.valueOf(pos.getY()));
      table.set("z", LuaValue.valueOf(pos.getZ()));
      table.set("direction", LuaValue.valueOf(c08PacketPlayerBlockPlacement.getPlacedBlockDirection()));
      ItemStack placedItem = c08PacketPlayerBlockPlacement.Method308();
      boolean foundSlot = false;
      if (mc != null && mc.thePlayer != null) {
         ItemStack[] mainInventory = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

         for (int slot = 0; slot < mainInventory.length; slot++) {
            ItemStack currentStack = mainInventory[slot];
            if (placedItem == currentStack) {
               table.set("slot", LuaValue.valueOf(slot));
               foundSlot = true;
               break;
            }
         }
      }

      if (!foundSlot || placedItem == null) {
         table.set("slot", LuaTable.valueOf(-1));
      }

      table.set("facing_x", LuaValue.valueOf(c08PacketPlayerBlockPlacement.Method302()));
      table.set("facing_y", LuaValue.valueOf(c08PacketPlayerBlockPlacement.Method303()));
      table.set("facing_z", LuaValue.valueOf(c08PacketPlayerBlockPlacement.Method304()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
      int x = luaTable.get("x").toint();
      int y = luaTable.get("y").toint();
      int z = luaTable.get("z").toint();
      c08PacketPlayerBlockPlacement.Method312(new BlockPos(x, y, z));
      c08PacketPlayerBlockPlacement.Method313(luaTable.get("direction").toint());
      if (luaTable.get("slot").toint() != -1 && mc != null && mc.thePlayer != null) {
         c08PacketPlayerBlockPlacement.Method314(this.c.thePlayer.inventory.getStackInSlot(luaTable.get("slot").toint()));
      }

      c08PacketPlayerBlockPlacement.Method316(luaTable.get("facing_x").tofloat());
      c08PacketPlayerBlockPlacement.Method317(luaTable.get("facing_y").tofloat());
      c08PacketPlayerBlockPlacement.Method318(luaTable.get("facing_z").tofloat());
   }
}
