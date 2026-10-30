package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class RegisterHandler extends VarArgFunction {
   final ModulesFunctionContainer container;

   public RegisterHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      if (varargs.narg() == 3) {
         LuaValue name = varargs.arg(1);
         LuaValue displayName = varargs.arg(2);
         LuaValue table = varargs.arg(3);
         if (name.isnil() || !name.isstring()) {
            return LuaValue.error("Expected type string at 0, got " + name.typename());
         }

         if (displayName.isnil() || !displayName.isstring()) {
            return LuaValue.error("Expected type string at 1, got " + displayName.typename());
         }

         if (table.isnil() || !table.istable()) {
            return LuaValue.error("Expected type table at 2, got " + table.typename());
         }

         this.container.implementation.register(name.tojstring(), displayName.tojstring(), "No description", table.checktable());
      } else {
         LuaValue name = varargs.arg(1);
         LuaValue displayName = varargs.arg(2);
         LuaValue description = varargs.arg(3);
         LuaValue table = varargs.arg(4);
         if (name.isnil() || !name.isstring()) {
            return LuaValue.error("Expected type string at 0, got " + name.typename());
         }

         if (displayName.isnil() || !displayName.isstring()) {
            return LuaValue.error("Expected type string at 1, got " + displayName.typename());
         }

         if (description.isnil() || !description.isstring()) {
            return LuaValue.error("Expected type string at 2, got " + displayName.typename());
         }

         if (table.isnil() || !table.istable()) {
            return LuaValue.error("Expected type table at 3, got " + table.typename());
         }

         this.container.implementation.register(name.tojstring(), displayName.tojstring(), description.tojstring(), table.checktable());
      }

      return LuaValue.NIL;
   }
}
