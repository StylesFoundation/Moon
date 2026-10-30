package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen {
   private static final Logger field_146488_a = LogManager.getLogger();
   private GuiTextField commandTextField;
   private GuiTextField previousOutputTextField;
   private final CommandBlockLogic localCommandBlock;
   private GuiButton doneBtn;
   private GuiButton cancelBtn;
   private GuiButton field_175390_s;
   private boolean field_175389_t;

   public GuiCommandBlock(CommandBlockLogic commandBlockLogic) {
      this.localCommandBlock = commandBlockLogic;
   }

   @Override
   public void onUpdateScreen() {
      this.commandTextField.updateCursorCounter();
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.m.clear();
      this.m.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
      this.m.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
      this.m.add(this.field_175390_s = new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
      this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
      this.commandTextField.setMaxStringLength(32767);
      this.commandTextField.setFocused(true);
      this.commandTextField.setText(this.localCommandBlock.getCommand());
      this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
      this.previousOutputTextField.setMaxStringLength(32767);
      this.previousOutputTextField.setEnabled(false);
      this.previousOutputTextField.setText("-");
      this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
      this.func_175388_a();
      this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 1) {
            this.localCommandBlock.setTrackOutput(this.field_175389_t);
            this.mc.displayGuiScreen(null);
         } else if (guiButton.l == 0) {
            PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
            var2.writeByte(this.localCommandBlock.func_145751_f());
            this.localCommandBlock.func_145757_a(var2);
            var2.writeString(this.commandTextField.getText());
            var2.writeBoolean(this.localCommandBlock.shouldTrackOutput());
            this.mc.getNetHandler().Method754(new C17PacketCustomPayload("MC|AdvCdm", var2));
            if (!this.localCommandBlock.shouldTrackOutput()) {
               this.localCommandBlock.setLastOutput(null);
            }

            this.mc.displayGuiScreen(null);
         } else if (guiButton.l == 4) {
            this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
            this.func_175388_a();
         }
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      this.commandTextField.textboxKeyTyped(charTyped, keyCode);
      this.previousOutputTextField.textboxKeyTyped(charTyped, keyCode);
      this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
      if (keyCode == 28 || keyCode == 156) {
         this.actionPerformed(this.doneBtn);
      } else if (keyCode == 1) {
         this.actionPerformed(this.cancelBtn);
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.commandTextField.mouseClicked(mouseX, mouseY, button);
      this.previousOutputTextField.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("advMode.command"), this.width / 2 - 150, 37, 10526880);
      this.commandTextField.drawTextBox();
      int var6 = 75;
      int var7 = 0;
      FontRenderer var10001 = this.fontRendererObj;
      String var10002 = I18n.format("advMode.nearestPlayer");
      int var10003 = this.width / 2 - 150;
      var7++;
      this.drawString(var10001, var10002, var10003, var6 + 0 * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(
         this.fontRendererObj, I18n.format("advMode.randomPlayer"), this.width / 2 - 150, var6 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880
      );
      this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers"), this.width / 2 - 150, var6 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(
         this.fontRendererObj, I18n.format("advMode.allEntities"), this.width / 2 - 150, var6 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880
      );
      this.drawString(this.fontRendererObj, "", this.width / 2 - 150, var6 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      if (this.previousOutputTextField.getText().length() > 0) {
         var6 = var6 + var7 * this.fontRendererObj.FONT_HEIGHT + 16;
         this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput"), this.width / 2 - 150, var6, 10526880);
         this.previousOutputTextField.drawTextBox();
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   private void func_175388_a() {
      if (this.localCommandBlock.shouldTrackOutput()) {
         this.field_175390_s.displayString = "O";
         if (this.localCommandBlock.getLastOutput() != null) {
            this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
         }
      } else {
         this.field_175390_s.displayString = "X";
         this.previousOutputTextField.setText("-");
      }
   }
}
