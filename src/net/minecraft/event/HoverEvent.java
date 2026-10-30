package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class HoverEvent {
   private final HoverEvent.Action action;
   private final IChatComponent value;

   public HoverEvent(HoverEvent.Action action, IChatComponent iChatComponent) {
      this.action = action;
      this.value = iChatComponent;
   }

   public HoverEvent.Action getAction() {
      return this.action;
   }

   public IChatComponent getValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (object != null && this.getClass() == object.getClass()) {
         HoverEvent var5 = (HoverEvent)object;
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
      return "HoverEvent{action=" + this.action + ", value='" + this.value + "'}";
   }

   @Override
   public int hashCode() {
      int var4 = this.action.hashCode();
      return 31 * var4 + (this.value != null ? this.value.hashCode() : 0);
   }

   public enum Action {
      SHOW_TEXT("show_text", true),
      SHOW_ACHIEVEMENT("show_achievement", true),
      SHOW_ITEM("show_item", true),
      SHOW_ENTITY("show_entity", true);

      private static final Map<String, HoverEvent.Action> nameMapping = Maps.newHashMap();
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

      public static HoverEvent.Action getValueByCanonicalName(String string) {
         return nameMapping.get(string);
      }

      private static HoverEvent.Action[] Method207() {
         return new HoverEvent.Action[]{SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY};
      }

      static {
         for (HoverEvent.Action var3 : values()) {
            nameMapping.put(var3.getCanonicalName(), var3);
         }
      }
   }
}
