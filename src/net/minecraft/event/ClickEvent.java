package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent {
   private final ClickEvent.Action action;
   private final String value;

   public ClickEvent(ClickEvent.Action action, String string) {
      this.action = action;
      this.value = string;
   }

   public ClickEvent.Action getAction() {
      return this.action;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (object != null && this.getClass() == object.getClass()) {
         ClickEvent var5 = (ClickEvent)object;
         if (this.action != var5.action) {
            return false;
         } else {
            return this.value != null ? this.value.equals(var5.value) : var5.value == null;
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
   }

   @Override
   public int hashCode() {
      int var4 = this.action.hashCode();
      return 31 * var4 + (this.value != null ? this.value.hashCode() : 0);
   }

   public enum Action {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true);

      private static final Map<String, ClickEvent.Action> nameMapping = Maps.newHashMap();
      private final boolean allowedInChat;
      private final String canonicalName;

      Action(String string3, boolean boolean4) {
         this.canonicalName = string3;
         this.allowedInChat = boolean4;
      }

      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      public String getCanonicalName() {
         return this.canonicalName;
      }

      public static ClickEvent.Action getValueByCanonicalName(String string) {
         return nameMapping.get(string);
      }

      private static ClickEvent.Action[] Method1000() {
         return new ClickEvent.Action[]{OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND, CHANGE_PAGE};
      }

      static {
         for (ClickEvent.Action var3 : values()) {
            nameMapping.put(var3.getCanonicalName(), var3);
         }
      }
   }
}
