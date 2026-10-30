package com.moon.account;

import com.moon.ui.theme.ColorScheme;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
   private final List<Account> accounts = new ArrayList<>();
   private ColorScheme themeColor = ColorScheme.darkScheme(-1);

   public void addAccount(Account account) {
      this.accounts.add(account);
   }

   public void setThemeColor(int color) {
      this.themeColor = ColorScheme.darkScheme(color);
   }

   public List<Account> getAccounts() {
      return this.accounts;
   }

   public ColorScheme getThemeColor() {
      return this.themeColor;
   }
}
