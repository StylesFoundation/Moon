package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class SendPacketNoEventHandler extends VarArgFunction {
   final PlayerFunctionContainer container;

   public SendPacketNoEventHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue packetId = args.arg(1);
      LuaTable table = args.arg(2).checktable();
      if (packetId.isnil() || !packetId.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + packetId.typename());
      } else if (!table.isnil() && table.istable()) {
         Object[] arguments = (Object[])CoerceLuaToJava.coerce(table, Object[].class);
         this.container.implementation.send_packet_no_event(packetId.toint(), arguments);
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type table at 1, got " + table.typename());
      }
   }
}
