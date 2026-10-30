package net.minecraft.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ChatComponentScore extends ChatComponentStyle {
   private final String name;
   private final String objective;
   private String value = "";

   public ChatComponentScore(String string1, String string2) {
      this.name = string1;
      this.objective = string2;
   }

   public String getName() {
      return this.name;
   }

   public String getObjective() {
      return this.objective;
   }

   public void setValue(String string) {
      this.value = string;
   }

   @Override
   public String getUnformattedTextForChat() {
      MinecraftServer var4 = MinecraftServer.getServer();
      if (var4 != null && var4.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
         Scoreboard var5 = var4.worldServerForDimension(0).getScoreboard();
         ScoreObjective var6 = var5.getObjective(this.objective);
         if (var5.entityHasObjective(this.name, var6)) {
            Score var7 = var5.Method281(this.name, var6);
            this.setValue(String.format("%d", var7.getScorePoints()));
         } else {
            this.value = "";
         }
      }

      return this.value;
   }

   public ChatComponentScore createCopy() {
      ChatComponentScore var4 = new ChatComponentScore(this.name, this.objective);
      var4.setValue(this.value);
      var4.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent var6 : this.getSiblings()) {
         var4.appendSibling(var6.createCopy());
      }

      return var4;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof ChatComponentScore var5)
            ? false
            : this.name.equals(var5.name) && this.objective.equals(var5.objective) && super.equals(object);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='" + this.name + "'objective='" + this.objective + "', siblings=" + this.siblings + ", style=" + this.getChatStyle() + "}";
   }
}
