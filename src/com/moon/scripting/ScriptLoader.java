package com.moon.scripting;

import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.ModuleManager;
import com.moon.scripting.api.enums.ClientStatusActions;
import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.enums.DiggingActions;
import com.moon.scripting.api.enums.EntityAction;
import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.enums.HitType;
import com.moon.scripting.api.enums.InteractActions;
import com.moon.scripting.api.enums.TemplateType;
import com.moon.scripting.api.functions.ClientFunctionContainer;
import com.moon.scripting.api.functions.FontFunctionContainer;
import com.moon.scripting.api.functions.FunctionContainer;
import com.moon.scripting.api.functions.InventoryFunctionContainer;
import com.moon.scripting.api.functions.ModulesFunctionContainer;
import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.functions.WorldFunctionContainer;
import com.moon.scripting.api.functions.render.BatchedRenderFunctionContainer;
import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import com.moon.scripting.api.functions.render.StatesRenderFunctionContainer;
import com.moon.scripting.api.types.handlers.templates.CreateGradientHandler;
import com.moon.scripting.api.types.handlers.templates.CreateOutlinedRoundedRectHandler;
import com.moon.scripting.api.types.handlers.templates.CreateRectHandler;
import com.moon.scripting.api.types.handlers.templates.CreateRoundedRectHandler;
import com.moon.scripting.api.types.handlers.templates.CreateTextHandler;
import com.moon.scripting.api.types.handlers.templates.CreateTriangleHandler;
import com.moon.scripting.api.types.handlers.vectors.CreateVec2fHandler;
import com.moon.scripting.api.types.handlers.vectors.CreateVec3dHandler;
import com.moon.scripting.api.types.handlers.vectors.CreateVec3fHandler;
import com.moon.scripting.api.types.handlers.vectors.CreateVec3iHandler;
import com.moon.scripting.api.types.handlers.vectors.EmptyVec2fHandler;
import com.moon.scripting.api.types.handlers.vectors.EmptyVec3dHandler;
import com.moon.scripting.api.types.handlers.vectors.EmptyVec3fHandler;
import com.moon.scripting.api.types.handlers.vectors.EmptyVec3iHandler;
import com.moon.util.client.ChatUtil;
import java.util.ArrayList;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.luaj.vm2.lib.jse.LuajavaLib;

public class ScriptLoader {
   public void load(Script script) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      ArrayList existingModules = new ArrayList<>(moduleManager.getRegistry().getModules());
      Globals globals = new Globals();
      this.setupGlobals(globals);
      LoadState.install(globals);
      LuaC.install(globals);

      try {
         globals.load(script.getSource()).call();
      } catch (LuaError e) {
         ChatUtil.sendMessage("<red>" + e.getMessage());
      }

      for (Module module : moduleManager.getRegistry().getModules()) {
         if (!existingModules.contains(module) && module instanceof ScriptModule scriptModule) {
            scriptModule.setScript(script);
            script.getModules().add(scriptModule);
         }
      }

      script.setLoaded(true);
   }

   private void setupGlobals(Globals globals) {
      globals.load(new JseBaseLib());
      globals.load(new PackageLib());
      globals.load(new Bit32Lib());
      globals.load(new TableLib());
      globals.load(new StringLib());
      globals.load(new CoroutineLib());
      globals.load(new JseMathLib());
      globals.load(new JseIoLib());
      globals.load(new JseOsLib());
      globals.load(new LuajavaLib());
      globals.set("collectgarbage", LuaValue.NIL);
      globals.set("dofile", LuaValue.NIL);
      globals.set("loadfile", LuaValue.NIL);
      globals.set("module", LuaValue.NIL);
      globals.set("require", LuaValue.NIL);
      globals.set("package", LuaValue.NIL);
      globals.set("io", LuaValue.NIL);
      globals.set("os", LuaValue.NIL);
      globals.set("print", LuaValue.NIL);
      globals.set("luajava", LuaValue.NIL);
      globals.set("debug", LuaValue.NIL);
      globals.set("newproxy", LuaValue.NIL);
      globals.set("__inext", LuaValue.NIL);
      FunctionContainer[] containers = new FunctionContainer[]{
         new ClientFunctionContainer(),
         new ModulesFunctionContainer(),
         new WorldFunctionContainer(),
         new PlayerFunctionContainer(),
         new InventoryFunctionContainer(),
         new FontFunctionContainer()
      };

      for (FunctionContainer container : containers) {
         LuaTable containerTable = new LuaTable();
         container.registerFunctions(containerTable);
         globals.set(container.getTableName(), containerTable);
      }

      LuaTable renderTable = new LuaTable();
      ImmediateRenderFunctionContainer immediateContainer = new ImmediateRenderFunctionContainer();
      LuaTable immediateTable = new LuaTable();
      immediateContainer.registerFunctions(immediateTable);
      renderTable.set("immediate", immediateTable);
      BatchedRenderFunctionContainer batchedContainer = new BatchedRenderFunctionContainer();
      LuaTable batchedTable = new LuaTable();
      batchedContainer.registerFunctions(batchedTable);
      renderTable.set("batched", batchedTable);
      StatesRenderFunctionContainer statesContainer = new StatesRenderFunctionContainer();
      LuaTable statesTable = new LuaTable();
      statesContainer.registerFunctions(statesTable);
      renderTable.set("state", statesTable);
      globals.set("render", renderTable);
      globals.set("enums", this.createEnums());
      globals.set("types", this.createTypes());
   }

   private LuaValue createEnums() {
      LuaTable enumsTable = new LuaTable();
      enumsTable.set("facing", CoerceJavaToLua.coerce(Facing.class));
      enumsTable.set("hittype", CoerceJavaToLua.coerce(HitType.class));
      enumsTable.set("digging_action", CoerceJavaToLua.coerce(DiggingActions.class));
      enumsTable.set("entity_action", CoerceJavaToLua.coerce(EntityAction.class));
      enumsTable.set("interact_actions", CoerceJavaToLua.coerce(InteractActions.class));
      enumsTable.set("custom_font_type", CoerceJavaToLua.coerce(CustomFontType.class));
      enumsTable.set("template_type", CoerceJavaToLua.coerce(TemplateType.class));
      enumsTable.set("client_status_actions", CoerceJavaToLua.coerce(ClientStatusActions.class));
      return enumsTable;
   }

   private LuaValue createTypes() {
      LuaTable typesTable = new LuaTable();
      typesTable.set("vec2f", this.createVec2fType());
      typesTable.set("vec3f", this.createVec3fType());
      typesTable.set("vec3d", this.createVec3dType());
      typesTable.set("vec3i", this.createVec3iType());
      typesTable.set("template", this.createTemplateType());
      return typesTable;
   }

   private LuaTable createTemplateType() {
      LuaTable templateTable = new LuaTable();
      templateTable.set("outlined_rounded_rect", this.createOutlinedRoundedRectType());
      templateTable.set("gradient", this.createGradientType());
      templateTable.set("rect", this.createRectType());
      templateTable.set("rounded_rect", this.createRoundedRectType());
      templateTable.set("text", this.createTextType());
      templateTable.set("triangle", this.createTriangleType());
      return templateTable;
   }

   private LuaTable createOutlinedRoundedRectType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateOutlinedRoundedRectHandler(this));
      return table;
   }

   private LuaTable createGradientType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateGradientHandler(this));
      return table;
   }

   private LuaTable createRectType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateRectHandler(this));
      return table;
   }

   private LuaTable createRoundedRectType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateRoundedRectHandler(this));
      return table;
   }

   private LuaTable createTextType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateTextHandler(this));
      return table;
   }

   private LuaTable createTriangleType() {
      LuaTable table = new LuaTable();
      table.set("new", new CreateTriangleHandler(this));
      return table;
   }

   private LuaTable createVec2fType() {
      LuaTable table = new LuaTable();
      table.set("empty", new EmptyVec2fHandler(this));
      table.set("new", new CreateVec2fHandler(this));
      return table;
   }

   private LuaTable createVec3dType() {
      LuaTable table = new LuaTable();
      table.set("empty", new EmptyVec3dHandler(this));
      table.set("new", new CreateVec3dHandler(this));
      return table;
   }

   private LuaTable createVec3fType() {
      LuaTable table = new LuaTable();
      table.set("empty", new EmptyVec3fHandler(this));
      table.set("new", new CreateVec3fHandler(this));
      return table;
   }

   private LuaTable createVec3iType() {
      LuaTable table = new LuaTable();
      table.set("empty", new EmptyVec3iHandler(this));
      table.set("new", new CreateVec3iHandler(this));
      return table;
   }
}
