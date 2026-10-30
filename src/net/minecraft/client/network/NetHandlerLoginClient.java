package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.math.BigInteger;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft mc;
   private final GuiScreen previousGuiScreen;
   private final NetworkManager networkManager;
   private GameProfile gameProfile;

   public NetHandlerLoginClient(NetworkManager networkManager, Minecraft minecraft, GuiScreen guiScreen) {
      this.networkManager = networkManager;
      this.mc = minecraft;
      this.previousGuiScreen = guiScreen;
   }

   @Override
   public void handleEncryptionRequest(S01PacketEncryptionRequest s01PacketEncryptionRequest) {
      SecretKey var5 = CryptManager.createNewSharedKey();
      String var6 = s01PacketEncryptionRequest.getServerId();
      PublicKey var7 = s01PacketEncryptionRequest.getPublicKey();
      String var8 = new BigInteger(CryptManager.getServerIdHash(var6, var7, var5)).toString(16);
      if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().isOnLAN()) {
         try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), var8);
         } catch (AuthenticationException var13) {
            logger.warn("Couldn't connect to auth servers but will continue to join LAN");
         }
      } else {
         try {
            this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), var8);
         } catch (AuthenticationUnavailableException var10) {
            this.networkManager
               .closeChannel(
                  new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable"))
               );
            return;
         } catch (InvalidCredentialsException var11) {
            this.networkManager
               .closeChannel(
                  new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession"))
               );
            return;
         } catch (AuthenticationException var12) {
            this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", var12.getMessage()));
            return;
         }
      }

      this.networkManager
         .sendPacket(
            new C01PacketEncryptionResponse(var5, var7, s01PacketEncryptionRequest.getVerifyToken()), future -> this.networkManager.enableEncryption(var5)
         );
   }

   private MinecraftSessionService getSessionService() {
      return this.mc.getSessionService();
   }

   @Override
   public void handleLoginSuccess(S02PacketLoginSuccess s02PacketLoginSuccess) {
      this.gameProfile = s02PacketLoginSuccess.getProfile();
      this.networkManager.setConnectionState(EnumConnectionState.PLAY);
      this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
   }

   @Override
   public void onDisconnect(IChatComponent iChatComponent) {
      this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", iChatComponent));
   }

   @Override
   public void handleDisconnect(S00PacketDisconnect s00PacketDisconnect) {
      this.networkManager.closeChannel(s00PacketDisconnect.func_149603_c());
   }

   @Override
   public void handleEnableCompression(S03PacketEnableCompression s03PacketEnableCompression) {
      if (!this.networkManager.isLocalChannel()) {
         this.networkManager.setCompressionTreshold(s03PacketEnableCompression.getCompressionTreshold());
      }
   }
}
