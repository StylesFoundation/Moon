package com.moon.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class Account {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final TaggedLogger logger = Logger.tag("accounts/account");
   private AccountSession session;
   private AccountType type;

   public void login() {
      this.type.getSerializer().authenticateWith(this);
   }

   public JsonObject toJson() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", this.type.name());
      jsonObject.add("data", GSON.toJsonTree(this.session));
      return jsonObject;
   }

   public static Account fromJson(JsonElement jsonElement) {
      if (jsonElement.isJsonObject()) {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         if (jsonObject.has("type") && jsonObject.has("data")) {
            AccountType accountType = AccountType.valueOf(jsonObject.get("type").getAsString());
            AccountSession accountSession = (AccountSession)GSON.fromJson(jsonObject.get("data"), accountType.getSessionClass());
            return new Account(accountSession, accountType);
         } else {
            logger.error("Invalid json object received to parse, continuing");
            return null;
         }
      } else {
         logger.error("Invalid json type received, continuing");
         return null;
      }
   }

   public AccountSession getSession() {
      return this.session;
   }

   public AccountType getType() {
      return this.type;
   }

   public Account setSession(AccountSession session) {
      this.session = session;
      return this;
   }

   public Account setType(AccountType type) {
      this.type = type;
      return this;
   }

   public Account() {
   }

   public Account(AccountSession session, AccountType type) {
      this.session = session;
      this.type = type;
   }
}
