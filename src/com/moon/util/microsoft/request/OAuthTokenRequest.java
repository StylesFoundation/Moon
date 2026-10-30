package com.moon.util.microsoft.request;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OAuthTokenRequest extends FormRequestBody {
   private String clientId;
   private String code;
   private String grantType;
   private String redirectUri;
   private String scope;

   @Override
   public String toFormBody() {
      String tokenKey = this.grantType.equals("refresh_token") ? "refresh_token" : "code";
      String encodedClientId = URLEncoder.encode(this.clientId, StandardCharsets.UTF_8);
      String encodedCode = URLEncoder.encode(this.code, StandardCharsets.UTF_8);
      String grantTypeStr = this.grantType;
      String encodedRedirectUri = URLEncoder.encode(this.redirectUri, StandardCharsets.UTF_8);
      String scopeStr = this.scope;
      String redirectUriParam = encodedRedirectUri;
      String grantTypeParam = grantTypeStr;
      String codeParam = encodedCode;
      String tokenKeyParam = tokenKey;
      String clientIdParam = encodedClientId;
      return "client_id=" + clientIdParam + "&" + tokenKeyParam + "=" + codeParam + "&grant_type=" + grantTypeParam + "&redirect_uri=" + redirectUriParam + "&scope=" + scopeStr;
   }

   public String getClientId() {
      return this.clientId;
   }

   public String getCode() {
      return this.code;
   }

   public String getGrantType() {
      return this.grantType;
   }

   public String getRedirectUri() {
      return this.redirectUri;
   }

   public String getScope() {
      return this.scope;
   }

   public OAuthTokenRequest withClientId(String clientId) {
      this.clientId = clientId;
      return this;
   }

   public OAuthTokenRequest withCode(String code) {
      this.code = code;
      return this;
   }

   public OAuthTokenRequest withGrantType(String grantType) {
      this.grantType = grantType;
      return this;
   }

   public OAuthTokenRequest withRedirectUri(String redirectUri) {
      this.redirectUri = redirectUri;
      return this;
   }

   public OAuthTokenRequest withScope(String scope) {
      this.scope = scope;
      return this;
   }

   public OAuthTokenRequest(String clientId, String code, String grantType, String redirectUri, String scope) {
      this.clientId = clientId;
      this.code = code;
      this.grantType = grantType;
      this.redirectUri = redirectUri;
      this.scope = scope;
   }
}
