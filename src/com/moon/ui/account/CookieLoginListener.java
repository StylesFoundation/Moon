package com.moon.ui.account;

import com.moon.Moon;
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

class CookieLoginListener implements ComponentListener {
   final FileDialog fileDialog;
   final DirectLoginScreen parentScreen;

   CookieLoginListener(DirectLoginScreen parentScreen, FileDialog fileDialog) {
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
      File[] files = this.fileDialog.getFiles();
      if (files.length > 0) {
         try {
            CookieMap cookieMap = CookieFileParser.parse(files[0]);
            if (cookieMap == null) {
               return;
            }

            AddAccountScreen.AUTH_SERVER
               .setCallback(
                  authData -> {
                     if (authData != null) {
                        Session session = DirectLoginScreen.getMinecraft(this.parentScreen).Method1016();
                        Moon.getInstance()
                           .getNotificationManager()
                           .pop(
                              Notification.builder()
                                 .title("Account Manager")
                                 .message("Successfully logged into account with name " + session.getUsername())
                                 .type(NotificationType.SUCCESS)
                                 .duration(3000L)
                                 .build()
                           );
                        Moon.getInstance().setCurrentAccount(null);
                        DirectLoginScreen.getMinecraftInstance(this.parentScreen).displayGuiScreen(Moon.getInstance().getAccountScreen());
                     } else {
                        Moon.getInstance()
                           .getNotificationManager()
                           .pop(
                              Notification.builder()
                                 .title("Account Manager")
                                 .message("Failed to log into account (invalid credentials)")
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
