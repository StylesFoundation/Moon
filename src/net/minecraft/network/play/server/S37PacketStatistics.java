package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class S37PacketStatistics implements Packet<INetHandlerPlayClient> {
   private Map<StatBase, Integer> field_148976_a;

   public S37PacketStatistics() {
   }

   public S37PacketStatistics(Map<StatBase, Integer> map) {
      this.field_148976_a = map;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleStatistics(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      int var5 = buffer.readVarIntFromBuffer();
      this.field_148976_a = Maps.newHashMap();

      for (int var6 = 0; var6 < var5; var6++) {
         StatBase var7 = StatList.getOneShotStat(buffer.readStringFromBuffer(32767));
         int var8 = buffer.readVarIntFromBuffer();
         if (var7 != null) {
            this.field_148976_a.put(var7, var8);
         }
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.field_148976_a.size());

      for (Entry var6 : this.field_148976_a.entrySet()) {
         buffer.writeString(((StatBase)var6.getKey()).statId);
         buffer.writeVarIntToBuffer((Integer)var6.getValue());
      }
   }

   public Map<StatBase, Integer> func_148974_c() {
      return this.field_148976_a;
   }
}
