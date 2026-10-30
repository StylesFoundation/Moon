package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam extends Team {
   private final Scoreboard theScoreboard;
   private final String registeredName;
   private final Set<String> membershipSet = Sets.newHashSet();
   private String teamNameSPT;
   private String namePrefixSPT = "";
   private String colorSuffix = "";
   private boolean allowFriendlyFire = true;
   private boolean canSeeFriendlyInvisibles = true;
   private Team.EnumVisible nameTagVisibility = Team.EnumVisible.ALWAYS;
   private Team.EnumVisible deathMessageVisibility = Team.EnumVisible.ALWAYS;
   private EnumChatFormatting chatFormat = EnumChatFormatting.RESET;

   public ScorePlayerTeam(Scoreboard scoreboard, String string) {
      this.theScoreboard = scoreboard;
      this.registeredName = string;
      this.teamNameSPT = string;
   }

   @Override
   public String getRegisteredName() {
      return this.registeredName;
   }

   public String getTeamName() {
      return this.teamNameSPT;
   }

   public void setTeamName(String string) {
      if (string == null) {
         throw new IllegalArgumentException("Name cannot be null");
      }

      this.teamNameSPT = string;
      this.theScoreboard.sendTeamUpdate(this);
   }

   @Override
   public Collection<String> getMembershipCollection() {
      return this.membershipSet;
   }

   public String getColorPrefix() {
      return this.namePrefixSPT;
   }

   public void setNamePrefix(String string) {
      if (string == null) {
         throw new IllegalArgumentException("Prefix cannot be null");
      }

      this.namePrefixSPT = string;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public String getColorSuffix() {
      return this.colorSuffix;
   }

   public void setNameSuffix(String string) {
      this.colorSuffix = string;
      this.theScoreboard.sendTeamUpdate(this);
   }

   @Override
   public String formatString(String string) {
      return this.getColorPrefix() + string + this.getColorSuffix();
   }

   public static String formatPlayerName(Team team, String string) {
      return team == null ? string : team.formatString(string);
   }

   @Override
   public boolean getAllowFriendlyFire() {
      return this.allowFriendlyFire;
   }

   public void setAllowFriendlyFire(boolean boolean1) {
      this.allowFriendlyFire = boolean1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   @Override
   public boolean getSeeFriendlyInvisiblesEnabled() {
      return this.canSeeFriendlyInvisibles;
   }

   public void setSeeFriendlyInvisiblesEnabled(boolean boolean1) {
      this.canSeeFriendlyInvisibles = boolean1;
      this.theScoreboard.sendTeamUpdate(this);
   }

   @Override
   public Team.EnumVisible getNameTagVisibility() {
      return this.nameTagVisibility;
   }

   @Override
   public Team.EnumVisible getDeathMessageVisibility() {
      return this.deathMessageVisibility;
   }

   public void setNameTagVisibility(Team.EnumVisible enumVisible) {
      this.nameTagVisibility = enumVisible;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public void setDeathMessageVisibility(Team.EnumVisible enumVisible) {
      this.deathMessageVisibility = enumVisible;
      this.theScoreboard.sendTeamUpdate(this);
   }

   public int func_98299_i() {
      byte var4 = 0;
      if (this.getAllowFriendlyFire()) {
         var4 = 1;
      }

      if (this.getSeeFriendlyInvisiblesEnabled()) {
         var4 |= 2;
      }

      return var4;
   }

   public void func_98298_a(int integer) {
      this.setAllowFriendlyFire((integer & 1) > 0);
      this.setSeeFriendlyInvisiblesEnabled((integer & 2) > 0);
   }

   public void setChatFormat(EnumChatFormatting enumChatFormatting) {
      this.chatFormat = enumChatFormatting;
   }

   public EnumChatFormatting getChatFormat() {
      return this.chatFormat;
   }
}
