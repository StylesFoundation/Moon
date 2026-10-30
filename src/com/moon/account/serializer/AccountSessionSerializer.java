package com.moon.account.serializer;

import com.moon.account.Account;

public interface AccountSessionSerializer {
   void authenticateWith(Account account);
}
