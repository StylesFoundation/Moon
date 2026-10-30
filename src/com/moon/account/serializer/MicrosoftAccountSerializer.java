package com.moon.account.serializer;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.account.MicrosoftAccountSession;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.ui.account.AddAccountScreen;
import com.moon.ui.account.MicrosoftAuthData;
import com.moon.util.microsoft.MicrosoftAuthUtil;
import com.moon.util.microsoft.response.MinecraftLoginResponse;
import com.moon.util.microsoft.response.MinecraftProfile;
import com.moon.util.microsoft.response.OAuthTokenResponse;
import com.moon.util.microsoft.response.XstsAuthResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class MicrosoftAccountSerializer implements AccountSessionSerializer {
   private final ExecutorService executor = Executors.newSingleThreadExecutor();

   @Override
   public void authenticateWith(Account account) {
      if (account.getSession() instanceof MicrosoftAccountSession msSession) {
         this.executor
            .submit(
               () -> {
                  boolean accessSuccess = this.loginWithAccessToken(account, msSession.getAccessToken());
                  boolean refreshSuccess = false;
                  if (!accessSuccess) {
                     refreshSuccess = this.loginWithRefreshToken(account, msSession);
                  }

                  if (!refreshSuccess && !accessSuccess) {
                     AddAccountScreen.AUTH_SERVER
                        .openLoginPage(
                           false,
                           "",
                           authData -> {
                              if (authData != null) {
                                 String mcAccessToken = authData.getMinecraftAccessToken();
                                 String refreshToken = authData.getRefreshToken();
                                 msSession.setRefreshToken(refreshToken);
                                 msSession.setAccessToken(mcAccessToken);
                                 Session session = Minecraft.getMinecraft().Method1016();
                                 msSession.setName(session.getUsername());
                                 Moon.getInstance().setCurrentAccount(account);
                                 Moon.getInstance()
                                    .getNotificationManager()
                                    .pop(
                                       Notification.builder()
                                          .title("Account Manager")
                                          .message("Successfully logged into account with name " + account.getSession().getName())
                                          .type(NotificationType.SUCCESS)
                                          .duration(3000L)
                                          .build()
                                    );
                              } else {
                                 Moon.getInstance()
                                    .getNotificationManager()
                                    .pop(
                                       Notification.builder()
                                          .title("Account Manager")
                                          .message("Failed logging into account with name " + account.getSession().getName())
                                          .type(NotificationType.SUCCESS)
                                          .duration(3000L)
                                          .build()
                                    );
                              }
                           }
                        );
                  }
               }
            );
      }
   }

   private boolean loginWithAccessToken(Account account, String accessToken) {
      try {
         MinecraftProfile profile = MicrosoftAuthUtil.fetchMinecraftProfile(new MicrosoftAuthData("", accessToken, ""));
         Session session = new Session(profile.getName(), profile.getUuid().toString(), profile.getAuth().getMinecraftAccessToken(), "mojang");
         Minecraft.getMinecraft().setSession(session);
         Moon.getInstance().getLogger().info("Successfully logged in to account " + session.getUsername() + " from access token");
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
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   private boolean loginWithRefreshToken(Account account, MicrosoftAccountSession accountSession) {
      try {
         OAuthTokenResponse tokenResponse = MicrosoftAuthUtil.refreshAccessToken(accountSession.getRefreshToken());
         XstsAuthResponse xstsResponse = MicrosoftAuthUtil.authorizeXsts(MicrosoftAuthUtil.authenticateXboxLive(tokenResponse, false));
         MinecraftLoginResponse loginResponse = MicrosoftAuthUtil.loginMinecraft(xstsResponse.getDisplayClaims().getXui()[0].getUhs(), xstsResponse.getToken());
         MinecraftProfile profile = MicrosoftAuthUtil.fetchMinecraftProfile(
            new MicrosoftAuthData(tokenResponse.getAccessToken(), loginResponse.getAccessToken(), tokenResponse.getRefreshToken())
         );
         Session session = new Session(profile.getName(), profile.getUuid().toString(), profile.getAuth().getMinecraftAccessToken(), "mojang");
         Moon.getInstance().setCurrentAccount(account);
         Minecraft.getMinecraft().setSession(session);
         Moon.getInstance().getLogger().info("Successfully logged in to account " + session.getUsername() + " from refresh token");
         accountSession.setRefreshToken(tokenResponse.getRefreshToken());
         accountSession.setAccessToken(loginResponse.getAccessToken());
         Moon.getInstance()
            .getNotificationManager()
            .pop(
               Notification.builder()
                  .title("Account Manager")
                  .message("Successfully logged into account with name " + accountSession.getName())
                  .type(NotificationType.SUCCESS)
                  .duration(3000L)
                  .build()
            );
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }
}
