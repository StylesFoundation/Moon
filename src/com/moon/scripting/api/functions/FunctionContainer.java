package com.moon.scripting.api.functions;

import org.luaj.vm2.LuaTable;

public abstract class FunctionContainer {
   private final String tableName;

   public abstract void registerFunctions(LuaTable table);

   public String getTableName() {
      return this.tableName;
   }

   public FunctionContainer(String tableName) {
      this.tableName = tableName;
   }

   public static int Method491() {
      return 0;
   }

   static {
      if (Method491() != 0) {
      }
   }
}
