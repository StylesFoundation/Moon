package net.minecraft.client.gui.inventory;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class GuiEditSign extends GuiScreen {
   private final TileEntitySign tileSign;
   private int updateCounter;
   private int editLine;
   private GuiButton doneBtn;

   public GuiEditSign(TileEntitySign tileEntitySign) {
      this.tileSign = tileEntitySign;
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      Keyboard.enableRepeatEvents(true);
      this.m.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done")));
      this.tileSign.setEditable(false);
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      NetHandlerPlayClient var1 = this.mc.getNetHandler();
      if (var1 != null) {
         var1.Method754(new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
      }

      this.tileSign.setEditable(true);
   }

   @Override
   public void onUpdateScreen() {
      this.updateCounter++;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled && guiButton.l == 0) {
         this.tileSign.markDirty();
         this.mc.displayGuiScreen(null);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 200) {
         this.editLine = this.editLine - 1 & 3;
      }

      if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
         this.editLine = this.editLine + 1 & 3;
      }

      String var5 = this.tileSign.signText[this.editLine].getUnformattedText();
      if (keyCode == 14 && var5.length() > 0) {
         var5 = var5.substring(0, var5.length() - 1);
      }

      if (ChatAllowedCharacters.isAllowedCharacter(charTyped) && this.fontRendererObj.getStringWidth(var5 + charTyped) <= 90) {
         var5 = var5 + charTyped;
      }

      this.tileSign.signText[this.editLine] = new ChatComponentText(var5);
      if (keyCode == 1) {
         this.actionPerformed(this.doneBtn);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit"), this.width / 2, 40, 16777215);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.width / 2, 0.0F, 50.0F);
      GlStateManager.scale(-93.75F, -93.75F, -93.75F);
      GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
      if (this.tileSign.getBlockType() == Blocks.standing_sign) {
         GlStateManager.rotate(this.tileSign.getBlockMetadata() * 360 / 16.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -1.0625F, 0.0F);
      } else {
         int var8 = this.tileSign.getBlockMetadata();
         float var9 = 0.0F;
         if (var8 == 2) {
            var9 = 180.0F;
         }

         if (var8 == 4) {
            var9 = 90.0F;
         }

         if (var8 == 5) {
            var9 = -90.0F;
         }

         GlStateManager.rotate(var9, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -1.0625F, 0.0F);
      }

      if (this.updateCounter / 6 % 2 == 0) {
         this.tileSign.lineBeingEdited = this.editLine;
      }

      TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5, -0.75, -0.5, 0.0F);
      this.tileSign.lineBeingEdited = -1;
      GlStateManager.popMatrix();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }
}
