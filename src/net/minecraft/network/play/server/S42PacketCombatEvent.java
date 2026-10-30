package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;

public class S42PacketCombatEvent implements Packet<INetHandlerPlayClient> {
   public S42PacketCombatEvent.Event eventType;
   public int field_179774_b;
   public int field_179775_c;
   public int field_179772_d;
   public String deathMessage;

   public S42PacketCombatEvent() {
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public S42PacketCombatEvent(CombatTracker combatTracker, S42PacketCombatEvent.Event event) {
      this.eventType = event;
      EntityLivingBase var6 = combatTracker.func_94550_c();
      switch (event) {
         case END_COMBAT:
            this.field_179772_d = combatTracker.func_180134_f();
            this.field_179775_c = var6 == null ? -1 : var6.getEntityId();
            break;
         case ENTITY_DIED:
            this.field_179774_b = combatTracker.getFighter().getEntityId();
            this.field_179775_c = var6 == null ? -1 : var6.getEntityId();
            this.deathMessage = combatTracker.getDeathMessage().getUnformattedText();
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.eventType = buffer.readEnumValue(S42PacketCombatEvent.Event.class);
      if (this.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
         this.field_179772_d = buffer.readVarIntFromBuffer();
         this.field_179775_c = buffer.readInt();
      } else if (this.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
         this.field_179774_b = buffer.readVarIntFromBuffer();
         this.field_179775_c = buffer.readInt();
         this.deathMessage = buffer.readStringFromBuffer(32767);
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.eventType);
      if (this.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
         buffer.writeVarIntToBuffer(this.field_179772_d);
         buffer.writeInt(this.field_179775_c);
      } else if (this.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
         buffer.writeVarIntToBuffer(this.field_179774_b);
         buffer.writeInt(this.field_179775_c);
         buffer.writeString(this.deathMessage);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleCombatEvent(this);
   }

   public enum Event {
      ENTER_COMBAT,
      END_COMBAT,
      ENTITY_DIED;

      private static S42PacketCombatEvent.Event[] Method577() {
         return new S42PacketCombatEvent.Event[]{ENTER_COMBAT, END_COMBAT, ENTITY_DIED};
      }
   }
}
