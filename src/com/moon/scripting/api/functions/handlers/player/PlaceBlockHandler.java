package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import com.moon.scripting.api.types.Vec3i;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class PlaceBlockHandler extends VarArgFunction {
   final PlayerFunctionContainer container;

   public PlaceBlockHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue slot = args.arg(1);
      LuaValue blockPos = args.arg(2);
      LuaValue hitVec = args.arg(3);
      LuaValue facing = args.arg(4);
      if (slot.isnil() || !slot.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + slot.typename());
      } else if (blockPos.isnil() || !blockPos.isuserdata(Vec3i.class)) {
         return LuaValue.error("Expected type vec3i at 1, got " + blockPos.typename());
      } else if (hitVec.isnil() || !hitVec.isuserdata(Vec3d.class)) {
         return LuaValue.error("Expected type vec3d at 2, got " + hitVec.typename());
      } else {
         return !facing.isnil() && facing.isuserdata(Facing.class)
            ? LuaValue.valueOf(
               this.container
                  .implementation
                  .place_block(slot.toint(), (Vec3i)blockPos.touserdata(Vec3i.class), (Vec3d)hitVec.touserdata(Vec3d.class), (Facing)facing.touserdata(Facing.class))
            )
            : LuaValue.error("Expected type facing at 3, got " + facing.typename());
      }
   }
}
