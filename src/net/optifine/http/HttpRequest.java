package net.optifine.http;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
   private String host = null;
   private int port = 0;
   private Proxy proxy = Proxy.NO_PROXY;
   private String method = null;
   private String file = null;
   private String http = null;
   private Map<String, String> headers = new LinkedHashMap<>();
   private byte[] body = null;
   private int redirects = 0;

   public HttpRequest(String string1, int integer, Proxy proxy, String string4, String string5, String string6, Map<String, String> map, byte[] arr) {
      this.host = string1;
      this.port = integer;
      this.proxy = proxy;
      this.method = string4;
      this.file = string5;
      this.http = string6;
      this.headers = map;
      this.body = arr;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getMethod() {
      return this.method;
   }

   public String getFile() {
      return this.file;
   }

   public String Method529() {
      return this.http;
   }

   public Map<String, String> getHeaders() {
      return this.headers;
   }

   public byte[] getBody() {
      return this.body;
   }

   public int getRedirects() {
      return this.redirects;
   }

   public void setRedirects(int integer) {
      this.redirects = integer;
   }

   public Proxy getProxy() {
      return this.proxy;
   }
}
