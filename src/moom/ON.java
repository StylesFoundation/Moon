package moom;

import net.minecraft.client.audio.SoundManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paulscode.sound.SoundSystemLogger;

public class ON extends SoundSystemLogger {
   private static final Logger logger = LogManager.getLogger(SoundManager.class);
   final SoundManager a;

   public ON(SoundManager soundManager) {
      this.a = soundManager;
   }

   @Override
   public void message(String string, int integer) {
      if (!string.isEmpty()) {
         logger.info(string);
      }
   }

   @Override
   public void importantMessage(String string, int integer) {
      if (!string.isEmpty()) {
         logger.warn(string);
      }
   }

   @Override
   public void errorMessage(String string1, String string2, int integer) {
      if (!string2.isEmpty()) {
         logger.error("Error in class '" + string1 + "'");
         logger.error(string2);
      }
   }
}
