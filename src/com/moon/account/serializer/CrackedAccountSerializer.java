package com.moon.account.serializer;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class CrackedAccountSerializer implements AccountSessionSerializer {
   @Override
   public void authenticateWith(Account account) {
      Session session = new Session(account.getSession().getName(), "", "", "mojang");
      Minecraft.getMinecraft().setSession(session);
      Moon.getInstance().setCurrentAccount(account);
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
   }
}
