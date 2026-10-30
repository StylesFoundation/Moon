package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public abstract class Team {
   public boolean isSameTeam(Team team) {
      return this == team;
   }

   public abstract String getRegisteredName();

   public abstract String formatString(String string);

   public abstract boolean getSeeFriendlyInvisiblesEnabled();

   public abstract boolean getAllowFriendlyFire();

   public abstract Team.EnumVisible getNameTagVisibility();

   public abstract Collection<String> getMembershipCollection();

   public abstract Team.EnumVisible getDeathMessageVisibility();

   public enum EnumVisible {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

      private static final Map<String, Team.EnumVisible> field_178828_g = Maps.newHashMap();
      public final String internalName;
      public final int id;

      public static String[] func_178825_a() {
         return field_178828_g.keySet().toArray(new String[0]);
      }

      public static Team.EnumVisible func_178824_a(String string) {
         return field_178828_g.get(string);
      }

      EnumVisible(String string3, int integer4) {
         this.internalName = string3;
         this.id = integer4;
      }

      private static Team.EnumVisible[] Method231() {
         return new Team.EnumVisible[]{ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM};
      }

      static {
         for (Team.EnumVisible var3 : values()) {
            field_178828_g.put(var3.internalName, var3);
         }
      }
   }
}
