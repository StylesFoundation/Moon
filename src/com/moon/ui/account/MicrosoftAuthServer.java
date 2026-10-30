package com.moon.ui.account;

import com.moon.Moon;
import com.moon.util.microsoft.MicrosoftAuthUtil;
import com.moon.util.microsoft.response.MinecraftLoginResponse;
import com.moon.util.microsoft.response.MinecraftProfile;
import com.moon.util.microsoft.response.OAuthTokenResponse;
import com.moon.util.microsoft.response.XstsAuthResponse;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class MicrosoftAuthServer {
   private HttpServer server;
   private boolean shouldSetUsername;
   private String username;
   private Consumer<MicrosoftAuthData> callback;

   public void start() {
      try {
         this.server = HttpServer.create(new InetSocketAddress("localhost", 42000), 0);
         this.registerCallbackHandler();
         this.server.start();
      } catch (Throwable t) {
         throw new RuntimeException(t);
      }
   }

   private void registerCallbackHandler() {
      this.server.createContext("/", exchange -> {
         Moon.getInstance().getLogger().info("Trying to log in to account received through gateway");
         byte[] response = "Successfully authenticated! You can now close this window".getBytes();

         try {
            MicrosoftAuthData authData = this.exchangeAuthCode(exchange.getRequestURI().getQuery());
            if (this.shouldSetUsername) {
               System.out.println("Setting username to " + this.username);
               this.setProfileName(authData, this.username);
               this.shouldSetUsername = false;
               this.username = "";
            }

            MinecraftProfile profile = this.fetchMinecraftProfile(authData);
            Session session = new Session(profile.getName(), profile.getUuid().toString(), profile.getAuth().getMinecraftAccessToken(), "mojang");
            Minecraft.getMinecraft().setSession(session);
            Moon.getInstance().getLogger().info("Successfully logged in to account " + session.getUsername());
            this.callback.accept(authData);
         } catch (Exception e) {
            e.printStackTrace();
            response = "An error occurred while trying to authenticate you, please try again later or check your credentials".getBytes();
            Moon.getInstance().getLogger().error("Failed to log in to account with token " + exchange.getRequestURI().getQuery(), new Object[]{e});
            this.callback.accept(null);
         }

         exchange.sendResponseHeaders(200, response.length);
         exchange.getResponseBody().write(response);
      });
   }

   private MicrosoftAuthData exchangeAuthCode(String query) {
      try {
         if (query != null && query.startsWith("code=")) {
            OAuthTokenResponse tokenResponse = MicrosoftAuthUtil.requestAccessToken(query.replace("code=", ""));
            XstsAuthResponse xstsAuthResponse = MicrosoftAuthUtil.authorizeXsts(MicrosoftAuthUtil.authenticateXboxLive(tokenResponse, false));
            MinecraftLoginResponse loginResponse = MicrosoftAuthUtil.loginMinecraft(xstsAuthResponse.getDisplayClaims().getXui()[0].getUhs(), xstsAuthResponse.getToken());
            return new MicrosoftAuthData(tokenResponse.getAccessToken(), loginResponse.getAccessToken(), tokenResponse.getRefreshToken());
         } else {
            throw new IllegalStateException("query=" + query);
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }

   private MinecraftProfile fetchMinecraftProfile(MicrosoftAuthData authData) {
      try {
         return MicrosoftAuthUtil.fetchMinecraftProfile(authData);
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
         return null;
      }
   }

   private void setProfileName(MicrosoftAuthData authData, String username) {
      try {
         MicrosoftAuthUtil.setProfileName(authData, username);
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void openLoginPage(boolean shouldSetUsername, String username, Consumer<MicrosoftAuthData> callback) {
      try {
         Desktop.getDesktop()
            .browse(
               new URI(
                  "https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:42000&prompt=select_account"
               )
            );
         this.shouldSetUsername = shouldSetUsername;
         this.username = username;
         this.callback = callback;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      }
   }

   public void setCallback(Consumer<MicrosoftAuthData> callback) {
      this.shouldSetUsername = false;
      this.username = "";
      this.callback = callback;
   }
}
