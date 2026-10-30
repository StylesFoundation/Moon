package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;

public class BiomeCache {
   private final WorldChunkManager a;
   private long lastCleanupTime;
   private final LongHashMap<BiomeCache.Block> cacheMap = new LongHashMap<>();
   private final List<BiomeCache.Block> cache = Lists.newArrayList();

   public BiomeCache(WorldChunkManager worldChunkManager) {
      this.a = worldChunkManager;
   }

   public BiomeCache.Block getBiomeCacheBlock(int integer1, int integer2) {
      integer1 >>= 4;
      integer2 >>= 4;
      long var6 = integer1 & 4294967295L | (integer2 & 4294967295L) << 32;
      BiomeCache.Block var8 = this.cacheMap.getValueByKey(var6);
      if (var8 == null) {
         var8 = new BiomeCache.Block(integer1, integer2);
         this.cacheMap.add(var6, var8);
         this.cache.add(var8);
      }

      var8.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
      return var8;
   }

   public BiomeGenBase func_180284_a(int integer1, int integer2, BiomeGenBase biomeGenBase) {
      BiomeGenBase var7 = this.getBiomeCacheBlock(integer1, integer2).getBiomeGenAt(integer1, integer2);
      return var7 == null ? biomeGenBase : var7;
   }

   public void cleanupCache() {
      long var4 = MinecraftServer.getCurrentTimeMillis();
      long var6 = var4 - this.lastCleanupTime;
      if (var6 > 7500L || var6 < 0L) {
         this.lastCleanupTime = var4;

         for (int var8 = 0; var8 < this.cache.size(); var8++) {
            BiomeCache.Block var9 = this.cache.get(var8);
            long var10 = var4 - var9.lastAccessTime;
            if (var10 > 30000L || var10 < 0L) {
               this.cache.remove(var8--);
               long var12 = var9.xPosition & 4294967295L | (var9.zPosition & 4294967295L) << 32;
               this.cacheMap.remove(var12);
            }
         }
      }
   }

   public BiomeGenBase[] getCachedBiomes(int integer1, int integer2) {
      return this.getBiomeCacheBlock(integer1, integer2).biomes;
   }

   public class Block {
      public float[] rainfallValues = new float[256];
      public BiomeGenBase[] biomes = new BiomeGenBase[256];
      public int xPosition;
      public int zPosition;
      public long lastAccessTime;

      public Block(int integer2, int integer3) {
         this.xPosition = integer2;
         this.zPosition = integer3;
         BiomeCache.this.a.getRainfall(this.rainfallValues, integer2 << 4, integer3 << 4, 16, 16);
         BiomeCache.this.a.getBiomeGenAt(this.biomes, integer2 << 4, integer3 << 4, 16, 16, false);
      }

      public BiomeGenBase getBiomeGenAt(int integer1, int integer2) {
         return this.biomes[integer1 & 15 | (integer2 & 15) << 4];
      }
   }
}
