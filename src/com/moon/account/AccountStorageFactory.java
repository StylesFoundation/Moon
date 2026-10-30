package com.moon.account;

import java.util.HashMap;

public class AccountStorageFactory {
   public static Account createMicrosoft(String username, String accessToken, String refreshToken) {
      MicrosoftAccountSession session = new MicrosoftAccountSession(refreshToken, accessToken);
      session.setName(username);
      return new Account(session, AccountType.MICROSOFT);
   }

   public static Account createCracked(String username) {
      return new Account(new AccountSession(username, new HashMap<>()), AccountType.CRACKED);
   }
}
