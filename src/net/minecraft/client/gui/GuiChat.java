package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.events.impl.module.CommandSuggestionEvent;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen {
   private static final Logger logger = LogManager.getLogger();
   private final List<String> foundPlayerNames = Lists.newArrayList();
   protected GuiTextField inputField;
   private String historyBuffer = "";
   private int sentHistoryCursor = -1;
   private boolean playerNamesFound;
   private boolean waitingOnAutocomplete;
   private int autocompleteIndex;
   private String defaultInputFieldText = "";

   public GuiChat() {
   }

   public GuiChat(String string) {
      this.defaultInputFieldText = string;
   }

   @Override
   public void onInitGui() {
      Keyboard.enableRepeatEvents(true);
      this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
      this.inputField.setMaxStringLength(100);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setFocused(true);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setCanLoseFocus(false);
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.mc.ingameGUI.getChatGUI().resetScroll();
   }

   @Override
   public void onUpdateScreen() {
      this.inputField.updateCursorCounter();
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      this.waitingOnAutocomplete = false;
      if (keyCode == 15) {
         if (this.inputField.text.startsWith(".")) {
            String var5 = this.inputField.getText();
            CommandSuggestionEvent var6 = new CommandSuggestionEvent(var5, "", false);
            Moon.getInstance().getEventBus().publish(var6);
            if (var6.getSuggestion() != null && var6.isAccepted()) {
               String[] var7 = var5.split(" ");
               String[] var8 = var6.getSuggestion().split(" ");
               String var9 = var8[var8.length - 1];
               String var10 = var7[var7.length - 1];
               if (!var9.equals(var10)) {
                  if (!this.inputField.text.endsWith(" ")) {
                     this.inputField.deleteWords(-1);
                  }

                  this.inputField.writeText(var9);
                  return;
               }
            }
         }

         this.autocompletePlayerNames();
      } else {
         this.playerNamesFound = false;
      }

      if (keyCode == 1) {
         this.mc.displayGuiScreen(null);
      } else if (keyCode == 28 || keyCode == 156) {
         String var11 = this.inputField.getText().trim();
         if (var11.length() > 0) {
            this.sendChatMessage(var11);
         }

         this.mc.displayGuiScreen(null);
      } else if (keyCode == 200) {
         this.getSentHistory(-1);
      } else if (keyCode == 208) {
         this.getSentHistory(1);
      } else if (keyCode == 201) {
         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
      } else if (keyCode == 209) {
         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
      } else {
         this.inputField.textboxKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var3 = Mouse.getEventDWheel();
      if (var3 != 0) {
         if (var3 > 1) {
            var3 = 1;
         }

         if (var3 < -1) {
            var3 = -1;
         }

         if (!isShiftKeyDown()) {
            var3 *= 7;
         }

         this.mc.ingameGUI.getChatGUI().scroll(var3);
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (button == 0) {
         IChatComponent var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
         if (this.handleComponentClick(var4)) {
            return;
         }
      }

      this.inputField.mouseClicked(mouseX, mouseY, button);
      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void setText(String string, boolean boolean2) {
      if (boolean2) {
         this.inputField.setText(string);
      } else {
         this.inputField.writeText(string);
      }
   }

   public void autocompletePlayerNames() {
      if (this.playerNamesFound) {
         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
         if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
            this.autocompleteIndex = 0;
         }
      } else {
         int var1 = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
         this.foundPlayerNames.clear();
         this.autocompleteIndex = 0;
         String var2 = this.inputField.getText().substring(var1).toLowerCase();
         String var3 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
         this.sendAutocompleteRequest(var3, var2);
         if (this.foundPlayerNames.isEmpty()) {
            return;
         }

         this.playerNamesFound = true;
         this.inputField.deleteFromCursor(var1 - this.inputField.getCursorPosition());
      }

      if (this.foundPlayerNames.size() > 1) {
         StringBuilder var4 = new StringBuilder();

         for (String var6 : this.foundPlayerNames) {
            if (var4.length() > 0) {
               var4.append(", ");
            }

            var4.append(var6);
         }

         this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);
      }

      this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
   }

   private void sendAutocompleteRequest(String string1, String string2) {
      if (string1.length() >= 1) {
         BlockPos var3 = null;
         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            var3 = this.mc.objectMouseOver.getBlockPos();
         }

         this.mc.thePlayer.sendQueue.Method754(new C14PacketTabComplete(string1, var3));
         this.waitingOnAutocomplete = true;
      }
   }

   public void getSentHistory(int integer) {
      int var2 = this.sentHistoryCursor + integer;
      int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      var2 = MathHelper.clamp_int(var2, 0, var3);
      if (var2 != this.sentHistoryCursor) {
         if (var2 == var3) {
            this.sentHistoryCursor = var3;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == var3) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
            this.sentHistoryCursor = var2;
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
      String var6 = this.inputField.getText();
      CommandSuggestionEvent var7 = new CommandSuggestionEvent(var6, "", false);
      Moon.getInstance().getEventBus().publish(var7);
      if (var6.startsWith(".")) {
         drawRect(2, this.height - 27, this.width - 2, this.height - 15, Integer.MIN_VALUE);
         GlStateManager.enableBlend();
         if (var7.getSuggestion() == null || var7.getSuggestion().isBlank()) {
            this.mc.fontRendererObj.drawString(var6, 4.0F, this.height - 27 + 2, -2130706433);
         } else if (!var7.getSuggestion().isBlank()) {
            this.mc.fontRendererObj.drawStringWithShadow(var7.getSuggestion(), 4.0F, this.height - 27 + 2, -2130706433);
         }

         GlStateManager.disableBlend();
      }

      this.inputField.drawTextBox();
      IChatComponent var8 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
      if (var8 != null && var8.getChatStyle().getChatHoverEvent() != null) {
         this.handleComponentHover(var8, mouseX, mouseY);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void onAutocompleteResponse(String[] arr) {
      if (this.waitingOnAutocomplete) {
         this.playerNamesFound = false;
         this.foundPlayerNames.clear();

         for (String var5 : arr) {
            if (var5.length() > 0) {
               this.foundPlayerNames.add(var5);
            }
         }

         String var6 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
         String var7 = StringUtils.getCommonPrefix(arr);
         if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
            this.inputField
               .deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            this.inputField.writeText(var7);
         } else if (this.foundPlayerNames.size() > 0) {
            this.playerNamesFound = true;
            this.autocompletePlayerNames();
         }
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }
}
