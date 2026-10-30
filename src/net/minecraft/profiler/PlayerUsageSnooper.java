package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.util.HttpUtil;

public class PlayerUsageSnooper {
   private final Map<String, Object> snooperStats = Maps.newHashMap();
   private final Map<String, Object> clientStats = Maps.newHashMap();
   private final String uniqueID = UUID.randomUUID().toString();
   private final URL serverUrl;
   private final IPlayerUsage playerStatsCollector;
   private final Timer threadTrigger = new Timer("Snooper Timer", true);
   private final Object syncLock = new Object();
   private final long minecraftStartTimeMilis;
   private boolean isRunning;
   private int selfCounter;

   public PlayerUsageSnooper(String string, IPlayerUsage iPlayerUsage, long long3) {
      try {
         this.serverUrl = new URL("http://snoop.minecraft.net/" + string + "?version=2");
      } catch (MalformedURLException var5) {
         throw new IllegalArgumentException();
      }

      this.playerStatsCollector = iPlayerUsage;
      this.minecraftStartTimeMilis = long3;
   }

   public void startSnooper() {
      if (!this.isRunning) {
         this.isRunning = true;
         this.addOSData();
         this.threadTrigger.schedule(new TimerTask() {
            @Override
            public void run() {
               if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled()) {
                  HashMap var4;
                  synchronized (PlayerUsageSnooper.this.syncLock) {
                     var4 = Maps.newHashMap(PlayerUsageSnooper.this.clientStats);
                     if (PlayerUsageSnooper.this.selfCounter == 0) {
                        var4.putAll(PlayerUsageSnooper.this.snooperStats);
                     }

                     var4.put("snooper_count", PlayerUsageSnooper.this.selfCounter++);
                     var4.put("snooper_token", PlayerUsageSnooper.this.uniqueID);
                  }

                  HttpUtil.postMap(PlayerUsageSnooper.this.serverUrl, var4, true);
               }
            }
         }, 0L, 900000L);
      }
   }

   private void addOSData() {
      this.addJvmArgsToSnooper();
      this.addClientStat("snooper_token", this.uniqueID);
      this.addStatToSnooper("snooper_token", this.uniqueID);
      this.addStatToSnooper("os_name", System.getProperty("os.name"));
      this.addStatToSnooper("os_version", System.getProperty("os.version"));
      this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
      this.addStatToSnooper("java_version", System.getProperty("java.version"));
      this.addClientStat("version", "1.8.9");
      this.playerStatsCollector.addServerTypeToSnooper(this);
   }

   private void addJvmArgsToSnooper() {
      List<String> var5 = ManagementFactory.getRuntimeMXBean().getInputArguments();
      int var6 = 0;

      for (String var8 : var5) {
         if (var8.startsWith("-X")) {
            this.addClientStat("jvm_arg[" + var6++ + "]", var8);
         }
      }

      this.addClientStat("jvm_args", var6);
   }

   public void addMemoryStatsToSnooper() {
      this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
      this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
      this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
      this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
      this.playerStatsCollector.addServerStatsToSnooper(this);
   }

   public void addClientStat(String string, Object object) {
      synchronized (this.syncLock) {
         this.clientStats.put(string, object);
      }
   }

   public void addStatToSnooper(String string, Object object) {
      synchronized (this.syncLock) {
         this.snooperStats.put(string, object);
      }
   }

   public Map<String, String> getCurrentStats() {
      LinkedHashMap var4 = Maps.newLinkedHashMap();
      synchronized (this.syncLock) {
         this.addMemoryStatsToSnooper();

         for (Entry var7 : this.snooperStats.entrySet()) {
            var4.put((String)var7.getKey(), var7.getValue().toString());
         }

         for (Entry var11 : this.clientStats.entrySet()) {
            var4.put((String)var11.getKey(), var11.getValue().toString());
         }

         return var4;
      }
   }

   public boolean isSnooperRunning() {
      return this.isRunning;
   }

   public void stopSnooper() {
      this.threadTrigger.cancel();
   }

   public String getUniqueID() {
      return this.uniqueID;
   }

   public long getMinecraftStartTimeMillis() {
      return this.minecraftStartTimeMilis;
   }
}
