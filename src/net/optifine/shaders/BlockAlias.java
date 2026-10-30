package net.optifine.shaders;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.src.Config;
import net.optifine.config.MatchBlock;

public class BlockAlias {
   private final int blockAliasId;
   private final MatchBlock[] matchBlocks;

   public BlockAlias(int integer, MatchBlock[] arr) {
      this.blockAliasId = integer;
      this.matchBlocks = arr;
   }

   public int getBlockAliasId() {
      return this.blockAliasId;
   }

   public boolean matches(int integer1, int integer2) {
      for (MatchBlock var9 : this.matchBlocks) {
         if (var9.matches(integer1, integer2)) {
            return true;
         }
      }

      return false;
   }

   public int[] getMatchBlockIds() {
      HashSet<Integer> var4 = new HashSet<>();
      MatchBlock[] var5 = this.matchBlocks;
      int var6 = var5.length;

      for (int var7 = 0; var7 < var6; var7++) {
         int var9 = var5[var7].getBlockId();
         var4.add(var9);
      }

      return Config.toPrimitive(var4.toArray(new Integer[0]));
   }

   public MatchBlock[] getMatchBlocks(int integer) {
      ArrayList<MatchBlock> var5 = new ArrayList<>();

      for (MatchBlock var9 : this.matchBlocks) {
         if (var9.getBlockId() == integer) {
            var5.add(var9);
         }
      }

      return var5.toArray(new MatchBlock[0]);
   }

   @Override
   public String toString() {
      return "block." + this.blockAliasId + "=" + Config.arrayToString(this.matchBlocks);
   }
}
