package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;

@com.moon.scripting.documentation.annotation.FunctionContainer(value = "client", path = "functions/client")
@Description("Contains several functions to control and get information from the client")
public interface ClientFunctions {
   @Description("Prints a message into the minecraft chat")
   void print(@Parameter("message") @Description("The message which is going to be printed") String message);

   @Description("Sends a message into the minecraft chat")
   void message(@Parameter("message") @Description("The message which is going to be sent in chat") String message);

   @Description("Sets the timer speed of the current game")
   void set_timer_speed(@Parameter("speed") @Description("The new timer speed") double speed);

   @Description("Forces blur effects to re-draw for smooth animations")
   void force_effects_redraw();

   @Description("Returns if a given key is pressed")
   boolean is_key_down(@Parameter("key") @Description("The key") int key);

   @Description("Returns if a given mouse button is pressed")
   boolean is_mouse_down(@Parameter("button") @Description("The button") int button);

   @Description("Returns the current fps")
   int fps();

   @Description("Returns the current time in millis")
   long time();

   @Description("Returns the current kill-aura target id or -1 if none is present")
   int current_kill_aura_target();

   @Description("Returns the scaled screen width")
   float get_scaled_width();

   @Description("Returns the scaled screen height")
   float get_scaled_height();

   @Description("Returns the screen width")
   float get_width();

   @Description("Returns the screen height")
   float get_height();
}
