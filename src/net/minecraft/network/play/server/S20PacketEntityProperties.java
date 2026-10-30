package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S20PacketEntityProperties implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private final List<S20PacketEntityProperties.Snapshot> field_149444_b = Lists.newArrayList();

   public S20PacketEntityProperties() {
   }

   public S20PacketEntityProperties(int integer, Collection<IAttributeInstance> collection) {
      this.entityId = integer;

      for (IAttributeInstance var7 : collection) {
         this.field_149444_b
            .add(new S20PacketEntityProperties.Snapshot(var7.getAttribute().getAttributeUnlocalizedName(), var7.getBaseValue(), var7.func_111122_c()));
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      int var5 = buffer.readInt();

      for (int var6 = 0; var6 < var5; var6++) {
         String var7 = buffer.readStringFromBuffer(64);
         double var8 = buffer.readDouble();
         ArrayList var10 = Lists.newArrayList();
         int var11 = buffer.readVarIntFromBuffer();

         for (int var12 = 0; var12 < var11; var12++) {
            UUID var13 = buffer.readUuid();
            var10.add(new AttributeModifier(var13, "Unknown synced attribute modifier", buffer.readDouble(), buffer.readByte()));
         }

         this.field_149444_b.add(new S20PacketEntityProperties.Snapshot(var7, var8, var10));
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeInt(this.field_149444_b.size());

      for (S20PacketEntityProperties.Snapshot var6 : this.field_149444_b) {
         buffer.writeString(var6.func_151409_a());
         buffer.writeDouble(var6.func_151410_b());
         buffer.writeVarIntToBuffer(var6.func_151408_c().size());

         for (AttributeModifier var8 : var6.func_151408_c()) {
            buffer.writeUuid(var8.getID());
            buffer.writeDouble(var8.getAmount());
            buffer.writeByte(var8.getOperation());
         }
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityProperties(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public List<S20PacketEntityProperties.Snapshot> func_149441_d() {
      return this.field_149444_b;
   }

   public static class Snapshot {
      private final String field_151412_b;
      private final double field_151413_c;
      private final Collection<AttributeModifier> field_151411_d;

      public Snapshot(String string, double double2, Collection<AttributeModifier> collection) {
         this.field_151412_b = string;
         this.field_151413_c = double2;
         this.field_151411_d = collection;
      }

      public String func_151409_a() {
         return this.field_151412_b;
      }

      public double func_151410_b() {
         return this.field_151413_c;
      }

      public Collection<AttributeModifier> func_151408_c() {
         return this.field_151411_d;
      }
   }
}
