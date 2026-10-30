package com.moon.ui.account;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.account.AccountStorageFactory;
import com.moon.account.cookie.CookieFileParser;
import com.moon.account.cookie.CookieMap;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.util.microsoft.MicrosoftAuthUtil;
import java.awt.FileDialog;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import net.minecraft.util.Session;

class CookieAddListener implements ComponentListener {
   final FileDialog fileDialog;
   final AddAccountScreen parentScreen;

   CookieAddListener(AddAccountScreen parentScreen, FileDialog fileDialog) {
      this.parentScreen = parentScreen;
      this.fileDialog = fileDialog;
   }

   @Override
   public void componentResized(ComponentEvent event) {
   }

   @Override
   public void componentMoved(ComponentEvent event) {
   }

   @Override
   public void componentShown(ComponentEvent event) {
   }

   @Override
   public void componentHidden(ComponentEvent event) {
      for (File file : this.fileDialog.getFiles()) {
         try {
            CookieMap cookieMap = CookieFileParser.parse(file);
            if (cookieMap == null) {
               return;
            }

            AddAccountScreen.AUTH_SERVER
               .setCallback(
                  authData -> {
                     if (authData != null) {
                        String accessToken = authData.getMinecraftAccessToken();
                        String refreshToken = authData.getRefreshToken();
                        Session session = AddAccountScreen.getMinecraft(this.parentScreen).Method1016();
                        Account account = AccountStorageFactory.createMicrosoft(session.getUsername(), accessToken, refreshToken);
                        Moon.getInstance().setCurrentAccount(account);
                        Moon.getInstance().getAccountManager().addAccount(account);
                        Moon.getInstance()
                           .getNotificationManager()
                           .pop(
                              Notification.builder()
                                 .title("Account Manager")
                                 .message("Successfully added account with name " + session.getUsername())
                                 .type(NotificationType.SUCCESS)
                                 .duration(3000L)
                                 .build()
                           );
                        AddAccountScreen.getMinecraftInstance(this.parentScreen).displayGuiScreen(Moon.getInstance().getAccountScreen());
                     } else {
                        Moon.getInstance()
                           .getNotificationManager()
                           .pop(
                              Notification.builder()
                                 .title("Account Manager")
                                 .message("Failed to add account (invalid credentials)")
                                 .type(NotificationType.ERROR)
                                 .duration(3000L)
                                 .build()
                           );
                     }
                  }
               );
            MicrosoftAuthUtil.loginWithCookies(cookieMap);
         } catch (IOException | InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
}
