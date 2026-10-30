package com.moon.account;

import com.moon.account.serializer.AccountSessionSerializer;
import com.moon.account.serializer.CrackedAccountSerializer;
import com.moon.account.serializer.MicrosoftAccountSerializer;

public enum AccountType {
   MICROSOFT(MicrosoftAccountSession.class, new MicrosoftAccountSerializer()),
   CRACKED(AccountSession.class, new CrackedAccountSerializer());

   private final Class<? extends AccountSession> sessionClass;
   private final AccountSessionSerializer serializer;

   public Class<? extends AccountSession> getSessionClass() {
      return this.sessionClass;
   }

   public AccountSessionSerializer getSerializer() {
      return this.serializer;
   }

   AccountType(Class<? extends AccountSession> sessionClass, AccountSessionSerializer serializer) {
      this.sessionClass = sessionClass;
      this.serializer = serializer;
   }
}
