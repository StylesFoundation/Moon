package com.moon.account;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class AccountSession {
   @SerializedName("name")
   private String name;
   @SerializedName("sessions")
   private Map<String, SessionServerStats> sessions = new HashMap<>();

   public String getName() {
      return this.name;
   }

   public Map<String, SessionServerStats> getSessions() {
      return this.sessions;
   }

   public AccountSession setName(String name) {
      this.name = name;
      return this;
   }

   public AccountSession setSessions(Map<String, SessionServerStats> sessions) {
      this.sessions = sessions;
      return this;
   }

   public AccountSession() {
   }

   public AccountSession(String name, Map<String, SessionServerStats> sessions) {
      this.name = name;
      this.sessions = sessions;
   }
}
