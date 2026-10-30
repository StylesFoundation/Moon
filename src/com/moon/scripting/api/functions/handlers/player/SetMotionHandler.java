package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetMotionHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetMotionHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue motion = arg.arg(1);
      if (!motion.isnil() && motion.isuserdata(Vec3d.class)) {
         this.container.implementation.set_motion((Vec3d)motion.touserdata(Vec3d.class));
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type table at 0, got " + motion.typename());
      }
   }
}
