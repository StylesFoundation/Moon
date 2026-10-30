package com.moon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import org.tinylog.Logger;

public class MCHook extends Minecraft {
   public MCHook(GameConfiguration gameConfiguration) {
      super(gameConfiguration);
   }

   @Override
   public void checkGLError(String string) {
      switch (string) {
         case "Post startup":
            new Moon().init();
            break;
         case "Pre startup":
            Logger.info("Starting up Moon Client");
      }

      super.checkGLError(string);
   }
}
