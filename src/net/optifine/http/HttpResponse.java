package net.optifine.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
   private int status = 0;
   private String statusLine = null;
   private Map<String, String> headers = new LinkedHashMap<>();
   private byte[] body = null;

   public HttpResponse(int integer, String string, Map map, byte[] arr) {
      this.status = integer;
      this.statusLine = string;
      this.headers = map;
      this.body = arr;
   }

   public int getStatus() {
      return this.status;
   }

   public String getStatusLine() {
      return this.statusLine;
   }

   public Map getHeaders() {
      return this.headers;
   }

   public String getHeader(String string) {
      return this.headers.get(string);
   }

   public byte[] getBody() {
      return this.body;
   }
}
