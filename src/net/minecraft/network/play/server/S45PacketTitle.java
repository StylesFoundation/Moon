package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S45PacketTitle implements Packet<INetHandlerPlayClient> {
   private S45PacketTitle.Type type;
   private IChatComponent message;
   private int fadeInTime;
   private int displayTime;
   private int fadeOutTime;

   public S45PacketTitle() {
   }

   public S45PacketTitle(S45PacketTitle.Type type, IChatComponent iChatComponent) {
      this(type, iChatComponent, -1, -1, -1);
   }

   public S45PacketTitle(int integer1, int integer2, int integer3) {
      this(S45PacketTitle.Type.TIMES, null, integer1, integer2, integer3);
   }

   public S45PacketTitle(S45PacketTitle.Type type, IChatComponent iChatComponent, int integer3, int integer4, int integer5) {
      this.type = type;
      this.message = iChatComponent;
      this.fadeInTime = integer3;
      this.displayTime = integer4;
      this.fadeOutTime = integer5;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.type = buffer.readEnumValue(S45PacketTitle.Type.class);
      if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
         this.message = buffer.readChatComponent();
      }

      if (this.type == S45PacketTitle.Type.TIMES) {
         this.fadeInTime = buffer.readInt();
         this.displayTime = buffer.readInt();
         this.fadeOutTime = buffer.readInt();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.type);
      if (this.type == S45PacketTitle.Type.TITLE || this.type == S45PacketTitle.Type.SUBTITLE) {
         buffer.writeChatComponent(this.message);
      }

      if (this.type == S45PacketTitle.Type.TIMES) {
         buffer.writeInt(this.fadeInTime);
         buffer.writeInt(this.displayTime);
         buffer.writeInt(this.fadeOutTime);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleTitle(this);
   }

   public S45PacketTitle.Type getType() {
      return this.type;
   }

   public IChatComponent getMessage() {
      return this.message;
   }

   public int getFadeInTime() {
      return this.fadeInTime;
   }

   public int getDisplayTime() {
      return this.displayTime;
   }

   public int getFadeOutTime() {
      return this.fadeOutTime;
   }

   public enum Type {
      TITLE,
      SUBTITLE,
      TIMES,
      CLEAR,
      RESET;

      public static S45PacketTitle.Type byName(String string) {
         for (S45PacketTitle.Type var7 : values()) {
            if (var7.name().equalsIgnoreCase(string)) {
               return var7;
            }
         }

         return TITLE;
      }

      public static String[] getNames() {
         String[] var3 = new String[values().length];
         int var4 = 0;

         for (S45PacketTitle.Type var8 : values()) {
            var3[var4++] = var8.name().toLowerCase();
         }

         return var3;
      }

      private static S45PacketTitle.Type[] Method345() {
         return new S45PacketTitle.Type[]{TITLE, SUBTITLE, TIMES, CLEAR, RESET};
      }
   }
}
