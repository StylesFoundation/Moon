package com.moon.account;

import com.google.gson.annotations.SerializedName;

public class SessionServerStats {
   @SerializedName("kills")
   private int kills;
   @SerializedName("deaths")
   private int deaths;
   @SerializedName("playTime")
   private long playTime;

   public int getKills() {
      return this.kills;
   }

   public int getDeaths() {
      return this.deaths;
   }

   public long getPlayTime() {
      return this.playTime;
   }

   public SessionServerStats setKills(int kills) {
      this.kills = kills;
      return this;
   }

   public SessionServerStats setDeaths(int deaths) {
      this.deaths = deaths;
      return this;
   }

   public SessionServerStats setPlayTime(long playTime) {
      this.playTime = playTime;
      return this;
   }

   public SessionServerStats() {
   }

   public SessionServerStats(int kills, int deaths, long playTime) {
      this.kills = kills;
      this.deaths = deaths;
      this.playTime = playTime;
   }
}
