package com.moon.scripting.api.functions;

import com.moon.scripting.api.functions.handlers.client.CurrentKillAuraTargetHandler;
import com.moon.scripting.api.functions.handlers.client.ForceEffectsRedrawHandler;
import com.moon.scripting.api.functions.handlers.client.FpsHandler;
import com.moon.scripting.api.functions.handlers.client.GetHeightHandler;
import com.moon.scripting.api.functions.handlers.client.GetScaledHeightHandler;
import com.moon.scripting.api.functions.handlers.client.GetScaledWidthHandler;
import com.moon.scripting.api.functions.handlers.client.GetWidthHandler;
import com.moon.scripting.api.functions.handlers.client.IsKeyDownHandler;
import com.moon.scripting.api.functions.handlers.client.IsMouseDownHandler;
import com.moon.scripting.api.functions.handlers.client.MessageHandler;
import com.moon.scripting.api.functions.handlers.client.PrintHandler;
import com.moon.scripting.api.functions.handlers.client.SetTimerSpeedHandler;
import com.moon.scripting.api.functions.handlers.client.TimeHandler;
import com.moon.scripting.api.functions.impl.ClientFunctionsImpl;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class ClientFunctionContainer extends FunctionContainer {
   public final ClientFunctionsImpl implementation = new ClientFunctionsImpl();

   public ClientFunctionContainer() {
      super("client");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("print", new PrintHandler(this));
      table.set("message", new MessageHandler(this));
      table.set("set_timer_speed", new SetTimerSpeedHandler(this));
      table.set("force_effects_redraw", new ForceEffectsRedrawHandler(this));
      table.set("is_key_down", new IsKeyDownHandler(this));
      table.set("is_mouse_down", new IsMouseDownHandler(this));
      table.set("fps", new FpsHandler(this));
      table.set("time", new TimeHandler(this));
      table.set("current_kill_aura_target", new CurrentKillAuraTargetHandler(this));
      table.set("get_scaled_width", new GetScaledWidthHandler(this));
      table.set("get_scaled_height", new GetScaledHeightHandler(this));
      table.set("get_width", new GetWidthHandler(this));
      table.set("get_height", new GetHeightHandler(this));
   }

   private String luaValueToString(LuaValue value) {
      return value.isnil() ? "nil" : value.tojstring();
   }
}
