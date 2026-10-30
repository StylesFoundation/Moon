package com.moon.util.microsoft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moon.Moon;
import com.moon.account.cookie.CookieMap;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.ui.account.MicrosoftAuthData;
import com.moon.util.microsoft.request.MinecraftLoginRequest;
import com.moon.util.microsoft.request.MinecraftProfileNameRequest;
import com.moon.util.microsoft.request.OAuthTokenRequest;
import com.moon.util.microsoft.request.XboxLiveAuthProperties;
import com.moon.util.microsoft.request.XboxLiveAuthRequest;
import com.moon.util.microsoft.request.XstsAuthProperties;
import com.moon.util.microsoft.request.XstsAuthRequest;
import com.moon.util.microsoft.response.MinecraftLoginResponse;
import com.moon.util.microsoft.response.MinecraftProfile;
import com.moon.util.microsoft.response.MinecraftProfileResponse;
import com.moon.util.microsoft.response.OAuthTokenResponse;
import com.moon.util.microsoft.response.XboxLiveAuthResponse;
import com.moon.util.microsoft.response.XstsAuthResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.Tuple;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.util.Cookie;
import org.jetbrains.annotations.NotNull;

public class MicrosoftAuthUtil {
   private static final Gson GSON = new GsonBuilder().create();
   private static final CookieManager COOKIE_MANAGER = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
   public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(Version.HTTP_1_1).build();

   public static void loginWithCookies(CookieMap cookie) throws IOException, InterruptedException {
      openOAuthRedirect(cookie.serialize());
      startCookieAuthFlow(cookie);
   }

   public static void openOAuthRedirect(String cookie) throws IOException, InterruptedException {
      Builder requestBuilder = HttpRequest.newBuilder(
         URI.create(
            "https://login.live.com/oauth20_authorize.srf?redirect_uri=https://sisu.xboxlive.com/connect/oauth/XboxLive&response_type=code&state=LAAAAAEB2OQKA4AphougPWXz6j8KrT_KxS1BLPFjAd7VgjpBw60PmPFt0diwZWZkMzMwZmY5ZjgzNDU2YWEzZTdiMWYyYmQ4NWNkMWMx&client_id=000000004420578E&scope=XboxLive.Signin&lw=1&fl=dob,easi2&xsup=1&cobrandid=8058f65d-ce06-4c30-9559-473c9275a65d"
         )
      );
      requestBuilder.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
      requestBuilder.setHeader("Accept-Encoding", "gzip, deflate, br");
      requestBuilder.setHeader("Accept-Language", "en-US,en;q=0.5");
      requestBuilder.setHeader("Cache-Control", "no-cache");
      requestBuilder.setHeader("Cookie", cookie);
      requestBuilder.setHeader("Pragma", "no-cache");
      requestBuilder.setHeader("Sec-Fetch-Dest", "document");
      requestBuilder.setHeader("Sec-Fetch-Mode", "navigate");
      requestBuilder.setHeader("Sec-Fetch-Site", "none");
      requestBuilder.setHeader("Sec-Fetch-User", "?1");
      requestBuilder.setHeader("Upgrade-Insecure-Requests", "1");
      requestBuilder.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:105.0) Gecko/20100101 Firefox/105.0");
      HttpResponse response = HTTP_CLIENT.send(requestBuilder.build(), BodyHandlers.ofString());
      Optional location = response.headers().firstValue("location");
      if (location.isPresent() && response.statusCode() == 302) {
         followRedirect((String)location.get(), response, cookie);
      }
   }

   public static void startCookieAuthFlow(CookieMap cookie) {
      String authUrl = "https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:42000";

      try {
         WebClient webClient = new WebClient(BrowserVersion.FIREFOX);

         label44: {
            try {
               org.htmlunit.CookieManager cookieManager = new org.htmlunit.CookieManager();
               cookie.getCookies().forEach((key, value) -> cookieManager.addCookie(new Cookie(".live.com", key, value)));
               webClient.setCookieManager(cookieManager);
               HtmlPage page = (HtmlPage)webClient.getPage(authUrl);
               DomElement acceptButton = page.getElementById("idBtn_Accept");
               if (acceptButton != null) {
                  acceptButton.click();
                  break label44;
               }

               Moon.getInstance().getLogger().info("Got page title while logging into cookie account: " + page.getTitleText());
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Account Manager")
                        .message("The account you provided might be locked, cannot log in")
                        .type(NotificationType.ERROR)
                        .duration(4000L)
                        .build()
                  );
            } catch (Throwable t) {
               try {
                  webClient.close();
               } catch (Throwable suppressed) {
                  t.addSuppressed(suppressed);
               }

               throw t;
            }

            webClient.close();
            return;
         }

         webClient.close();
      } catch (ClassCastException ignored) {
      } catch (Exception e) {
         Moon.getInstance().getLogger().error("Failed to create cookie auth-flow!");
         Moon.getInstance().getLogger().error(e);
         Moon.getInstance()
            .getNotificationManager()
            .pop(
               Notification.builder()
                  .title("Account Manager")
                  .message("Unable to create cookie login auth flow, please create a ticket")
                  .type(NotificationType.ERROR)
                  .duration(4000L)
                  .build()
            );
      }
   }

   public static OAuthTokenResponse requestAccessToken(String code) throws IOException, InterruptedException {
      return requestAccessAndRefreshTokenFrom(code, "authorization_code");
   }

   public static OAuthTokenResponse refreshAccessToken(String refreshToken) throws IOException, InterruptedException {
      return requestAccessAndRefreshTokenFrom(refreshToken, "refresh_token");
   }

   private static OAuthTokenResponse requestAccessAndRefreshTokenFrom(String token, String grantType) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://login.live.com/oauth20_token.srf"))
         .headers("Content-Type", "application/x-www-form-urlencoded")
         .timeout(Duration.ofSeconds(15L))
         .POST(
            BodyPublishers.ofString(
               new OAuthTokenRequest(
                     "54fd49e4-2103-4044-9603-2b028c814ec3", token, grantType, "http://localhost:42000", "XboxLive.signin%20XboxLive.offline_access"
                  )
                  .toFormBody()
            )
         )
         .build();
      HttpResponse response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
      if (isErrorStatus(response.statusCode())) {
         throw new IllegalStateException("OAuth token request for Microsoft failed (" + response.statusCode() + " / " + (String)response.body() + ")");
      }

      String responseBody = (String)response.body();
      return (OAuthTokenResponse)GSON.fromJson(responseBody, OAuthTokenResponse.class);
   }

   public static XboxLiveAuthResponse authenticateXboxLive(OAuthTokenResponse tokenResponse, boolean useXboxLive) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://user.auth.xboxlive.com/user/authenticate"))
         .headers("Content-Type", "application/json", "Accept", "application/json")
         .timeout(Duration.ofSeconds(15L))
         .POST(
            BodyPublishers.ofString(
               GSON.toJson(
                  new XboxLiveAuthRequest(
                     new XboxLiveAuthProperties("RPS", "user.auth.xboxlive.com", (useXboxLive ? "" : "d=") + tokenResponse.getAccessToken()),
                     "http://auth.xboxlive.com",
                     "JWT"
                  )
               )
            )
         )
         .build();
      HttpResponse response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
      if (isErrorStatus(response.statusCode())) {
         throw new IllegalStateException("Xbl token request for Microsoft failed (" + response.statusCode() + " / " + (String)response.body() + ")");
      }

      String responseBody = (String)response.body();
      return (XboxLiveAuthResponse)GSON.fromJson(responseBody, XboxLiveAuthResponse.class);
   }

   public static XstsAuthResponse authorizeXsts(@NotNull XboxLiveAuthResponse tokenResponse) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://xsts.auth.xboxlive.com/xsts/authorize"))
         .headers("Content-Type", "application/json", "Accept", "application/json")
         .timeout(Duration.ofSeconds(15L))
         .POST(
            BodyPublishers.ofString(
               GSON.toJson(
                  new XstsAuthRequest(new XstsAuthProperties(new String[]{tokenResponse.getToken()}, "RETAIL"), "rp://api.minecraftservices.com/", "JWT")
               )
            )
         )
         .build();
      HttpResponse response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
      if (isErrorStatus(response.statusCode())) {
         throw new IllegalStateException("XstsXuiUhs token request for Microsoft failed (" + response.statusCode() + " / " + (String)response.body() + ")");
      }

      String responseBody = (String)response.body();
      return (XstsAuthResponse)GSON.fromJson(responseBody, XstsAuthResponse.class);
   }

   public static MinecraftLoginResponse loginMinecraft(String uhs, String identityToken) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://api.minecraftservices.com/authentication/login_with_xbox"))
         .headers("Content-Type", "application/json", "Accept", "application/json")
         .timeout(Duration.ofSeconds(15L))
         .POST(BodyPublishers.ofString(GSON.toJson(new MinecraftLoginRequest("XBL3.0 x=" + uhs + ";" + identityToken))))
         .build();
      HttpResponse response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
      if (isErrorStatus(response.statusCode())) {
         throw new IllegalStateException("Access token request for Minecraft failed (" + response.statusCode() + " / " + (String)response.body() + ")");
      }

      String responseBody = (String)response.body();
      return (MinecraftLoginResponse)GSON.fromJson(responseBody, MinecraftLoginResponse.class);
   }

   public static MinecraftProfile fetchMinecraftProfile(@NotNull MicrosoftAuthData profile) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create("https://api.minecraftservices.com/minecraft/profile"))
         .header("Authorization", "Bearer " + profile.getMinecraftAccessToken())
         .timeout(Duration.ofSeconds(15L))
         .GET()
         .build();
      HttpResponse response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
      if (isErrorStatus(response.statusCode())) {
         throw new IllegalStateException("Profile request to Minecraft failed (" + response.statusCode() + " / " + (String)response.body() + ")");
      }

      String responseBody = (String)response.body();
      MinecraftProfileResponse profileResponse = (MinecraftProfileResponse)GSON.fromJson(responseBody, MinecraftProfileResponse.class);
      return new MinecraftProfile(parseUndashedUuid(profileResponse.getId()), profileResponse.getName(), profile);
   }

   public static void setProfileName(@NotNull MicrosoftAuthData authData, String profileName) throws IOException, InterruptedException {
      COOKIE_MANAGER.getCookieStore().removeAll();

      try {
         HttpRequest storeRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api.minecraftservices.com/entitlements/mcstore"))
            .header("Authorization", "Bearer " + authData.getMinecraftAccessToken())
            .header("User-Agent", "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .header("Accept", "*/*")
            .header("Accept-Encoding", "gzip, deflate")
            .GET()
            .build();
         HttpResponse storeResponse = HTTP_CLIENT.send(storeRequest, BodyHandlers.ofString());
         if (isErrorStatus(storeResponse.statusCode())) {
            throw new IllegalStateException("Profile set request to Minecraft failed (" + storeResponse.statusCode() + " / " + (String)storeResponse.body() + ")");
         }

         HttpRequest availableRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api.minecraftservices.com/minecraft/profile/name/" + profileName + "/available"))
            .header("Authorization", "Bearer " + authData.getMinecraftAccessToken())
            .header("User-Agent", "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .header("Accept", "*/*")
            .header("Accept-Encoding", "gzip, deflate")
            .GET()
            .build();
         HttpResponse availableResponse = HTTP_CLIENT.send(availableRequest, BodyHandlers.ofString());
         if (isErrorStatus(availableResponse.statusCode())) {
            throw new IllegalStateException("Profile set request to Minecraft failed (" + availableResponse.statusCode() + " / " + (String)availableResponse.body() + ")");
         }

         HttpRequest profileRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api.minecraftservices.com/minecraft/profile"))
            .header("Authorization", "Bearer " + authData.getMinecraftAccessToken())
            .header("User-Agent", "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .header("Accept", "*/*")
            .header("Accept-Encoding", "gzip, deflate")
            .POST(BodyPublishers.ofString(GSON.toJson(new MinecraftProfileNameRequest(profileName))))
            .build();
         HttpResponse profileResponse = HTTP_CLIENT.send(profileRequest, BodyHandlers.ofString());
         if (isErrorStatus(profileResponse.statusCode())) {
            throw new IllegalStateException("Profile set request to Minecraft failed (" + profileResponse.statusCode() + " / " + (String)profileResponse.body() + ")");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static boolean isErrorStatus(int statusCode) {
      return statusCode < 200 || statusCode > 299;
   }

   private static UUID parseUndashedUuid(String uuidString) {
      return UUID.fromString(uuidString.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
   }

   private static Tuple<String, HttpResponse<String>> followRedirect(String url, HttpResponse<String> response, String cookie) throws IOException, InterruptedException {
      Optional locationHeader = response.headers().firstValue("Location");
      if (locationHeader.isPresent()) {
         String redirectUrl = (String)locationHeader.get();
         HttpRequest redirectRequest = HttpRequest.newBuilder()
            .uri(URI.create(redirectUrl))
            .header("User-Agent", "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
            .header("Cookie", cookie)
            .build();
         HttpResponse redirectResponse = HTTP_CLIENT.send(redirectRequest, BodyHandlers.ofString());
         return followRedirect(redirectUrl, redirectResponse, cookie);
      } else {
         return new Tuple<>(url, response);
      }
   }

   private static String extractGroup(String regex, String input) {
      Matcher matcher = Pattern.compile(regex).matcher(input);
      return !matcher.find() ? null : matcher.group(1);
   }

   private static String buildParams(Map<String, String> params) {
      StringBuilder sb = new StringBuilder();
      params.forEach((key, value) -> {
         if (sb.length() > 0) {
            sb.append('&');
         }

         sb.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8));
      });
      return sb.toString();
   }

   private static String decodeQueryParam(String input, String key) throws IllegalStateException {
      String value = extractGroup(key + "=([^&]*)", input);
      if (value == null) {
         throw new IllegalStateException("Invalid credentials or tokens");
      }

      try {
         return URLDecoder.decode(value, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException(e);
      }
   }
}
