package com.moon.scripting.converter;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S48PacketResourcePackSend;

public class PacketConverterRegistry {
   private static final Map<Class<? extends Packet<?>>, PacketConverter<? extends Packet<?>>> CONVERTERS = new HashMap<>();

   public static <T extends Packet<?>> PacketConverter<T> getConverter(Class<T> packetClass) {
      return (PacketConverter<T>)(!CONVERTERS.containsKey(packetClass) ? null : CONVERTERS.get(packetClass));
   }

   private static void register(Class<? extends Packet<?>> packetClass, PacketConverter<? extends Packet<?>> converter) {
      CONVERTERS.put(packetClass, converter);
   }

   static {
      register(C00PacketKeepAlive.class, new C00PacketKeepAliveConverter(C00PacketKeepAlive.class));
      register(C03PacketPlayer.class, new C03PacketPlayerConverter(C03PacketPlayer.class));
      register(C03PacketPlayer.C04PacketPlayerPosition.class, new C04PacketPlayerPositionConverter(C03PacketPlayer.C04PacketPlayerPosition.class));
      register(C03PacketPlayer.C05PacketPlayerLook.class, new C05PacketPlayerLookConverter(C03PacketPlayer.C05PacketPlayerLook.class));
      register(C03PacketPlayer.C06PacketPlayerPosLook.class, new C06PacketPlayerPosLookConverter(C03PacketPlayer.C06PacketPlayerPosLook.class));
      register(C0FPacketConfirmTransaction.class, new C0FPacketConfirmTransactionConverter(C0FPacketConfirmTransaction.class));
      register(C08PacketPlayerBlockPlacement.class, new C08PacketPlayerBlockPlacementConverter(C08PacketPlayerBlockPlacement.class));
      register(C0BPacketEntityAction.class, new C0BPacketEntityActionConverter(C0BPacketEntityAction.class));
      register(C0CPacketInput.class, new C0CPacketInputConverter(C0CPacketInput.class));
      register(C0DPacketCloseWindow.class, new C0DPacketCloseWindowConverter(C0DPacketCloseWindow.class));
      register(C0EPacketClickWindow.class, new C0EPacketClickWindowConverter(C0EPacketClickWindow.class));
      register(C01PacketChatMessage.class, new C01PacketChatMessageConverter(C01PacketChatMessage.class));
      register(C02PacketUseEntity.class, new C02PacketUseEntityConverter(C02PacketUseEntity.class));
      register(C07PacketPlayerDigging.class, new C07PacketPlayerDiggingConverter(C07PacketPlayerDigging.class));
      register(C09PacketHeldItemChange.class, new C09PacketHeldItemChangeConverter(C09PacketHeldItemChange.class));
      register(C11PacketEnchantItem.class, new C11PacketEnchantItemConverter(C11PacketEnchantItem.class));
      register(C13PacketPlayerAbilities.class, new C13PacketPlayerAbilitiesConverter(C13PacketPlayerAbilities.class));
      register(C14PacketTabComplete.class, new C14PacketTabCompleteConverter(C14PacketTabComplete.class));
      register(C18PacketSpectate.class, new C18PacketSpectateConverter(C18PacketSpectate.class));
      register(C19PacketResourcePackStatus.class, new C19PacketResourcePackStatusConverter(C19PacketResourcePackStatus.class));
      register(S02PacketChat.class, new S02PacketChatConverter(S02PacketChat.class));
      register(S08PacketPlayerPosLook.class, new S08PacketPlayerPosLookConverter(S08PacketPlayerPosLook.class));
      register(S12PacketEntityVelocity.class, new S12PacketEntityVelocityConverter(S12PacketEntityVelocity.class));
      register(S27PacketExplosion.class, new S27PacketExplosionConverter(S27PacketExplosion.class));
      register(S00PacketKeepAlive.class, new S00PacketKeepAliveConverter(S00PacketKeepAlive.class));
      register(S32PacketConfirmTransaction.class, new S32PacketConfirmTransactionConverter(S32PacketConfirmTransaction.class));
      register(S48PacketResourcePackSend.class, new S48PacketResourcePackSendConverter(S48PacketResourcePackSend.class));
   }
}
