package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<String> PROTOCOLS = Sets.newHashSet(new String[]{"http", "https"});
   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
   protected Minecraft mc;
   protected RenderItem itemRender;
   public int width;
   public int height;
   protected List<GuiButton> m = Lists.newArrayList();
   protected List<GuiLabel> labelList = Lists.newArrayList();
   public boolean allowUserInput;
   protected FontRenderer fontRendererObj;
   private GuiButton selectedButton;
   private int eventButton;
   private long lastMouseEvent;
   private int touchValue;
   private URI clickedLinkURI;

   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      for (int var4 = 0; var4 < this.m.size(); var4++) {
         this.m.get(var4).drawButton(this.mc, mouseX, mouseY);
      }

      for (int var5 = 0; var5 < this.labelList.size(); var5++) {
         this.labelList.get(var5).drawLabel(this.mc, mouseX, mouseY);
      }
   }

   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.mc.displayGuiScreen(null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }
   }

   public static String getClipboardString() {
      try {
         Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)var0.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
      }

      return "";
   }

   public static void setClipboardString(String string) {
      if (!StringUtils.isEmpty(string)) {
         try {
            StringSelection var1 = new StringSelection(string);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
         } catch (Exception var2) {
         }
      }
   }

   protected void renderToolTip(ItemStack itemStack, int integer2, int integer3) {
      List var4 = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

      for (int var5 = 0; var5 < var4.size(); var5++) {
         if (var5 == 0) {
            var4.set(var5, itemStack.getRarity().rarityColor + (String)var4.get(var5));
         } else {
            var4.set(var5, EnumChatFormatting.GRAY + (String)var4.get(var5));
         }
      }

      this.drawHoveringText(var4, integer2, integer3);
   }

   protected void drawCreativeTabHoveringText(String string, int integer2, int integer3) {
      this.drawHoveringText(Collections.singletonList(string), integer2, integer3);
   }

   protected void drawHoveringText(List<String> list, int integer2, int integer3) {
      if (!list.isEmpty()) {
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         int var6 = 0;

         for (String var8 : list) {
            int var9 = this.fontRendererObj.getStringWidth(var8);
            if (var9 > var6) {
               var6 = var9;
            }
         }

         int var15 = integer2 + 12;
         int var16 = integer3 - 12;
         int var17 = 8;
         if (list.size() > 1) {
            var17 += 2 + (list.size() - 1) * 10;
         }

         if (var15 + var6 > this.width) {
            var15 -= 28 + var6;
         }

         if (var16 + var17 + 6 > this.height) {
            var16 = this.height - var17 - 6;
         }

         this.zLevel = 300.0F;
         this.itemRender.zLevel = 300.0F;
         int var10 = -267386864;
         this.drawGradientRect(var15 - 3, var16 - 4, var15 + var6 + 3, var16 - 3, var10, var10);
         this.drawGradientRect(var15 - 3, var16 + var17 + 3, var15 + var6 + 3, var16 + var17 + 4, var10, var10);
         this.drawGradientRect(var15 - 3, var16 - 3, var15 + var6 + 3, var16 + var17 + 3, var10, var10);
         this.drawGradientRect(var15 - 4, var16 - 3, var15 - 3, var16 + var17 + 3, var10, var10);
         this.drawGradientRect(var15 + var6 + 3, var16 - 3, var15 + var6 + 4, var16 + var17 + 3, var10, var10);
         int var11 = 1347420415;
         int var12 = 1344798847;
         this.drawGradientRect(var15 - 3, var16 - 3 + 1, var15 - 3 + 1, var16 + var17 + 3 - 1, var11, var12);
         this.drawGradientRect(var15 + var6 + 2, var16 - 3 + 1, var15 + var6 + 3, var16 + var17 + 3 - 1, var11, var12);
         this.drawGradientRect(var15 - 3, var16 - 3, var15 + var6 + 3, var16 - 3 + 1, var11, var11);
         this.drawGradientRect(var15 - 3, var16 + var17 + 2, var15 + var6 + 3, var16 + var17 + 3, var12, var12);

         for (int var13 = 0; var13 < list.size(); var13++) {
            String var14 = (String)list.get(var13);
            this.fontRendererObj.drawStringWithShadow(var14, var15, var16, -1);
            if (var13 == 0) {
               var16 += 2;
            }

            var16 += 10;
         }

         this.zLevel = 0.0F;
         this.itemRender.zLevel = 0.0F;
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.enableRescaleNormal();
      }
   }

   protected void handleComponentHover(IChatComponent iChatComponent, int integer2, int integer3) {
      if (iChatComponent != null && iChatComponent.getChatStyle().getChatHoverEvent() != null) {
         HoverEvent var6 = iChatComponent.getChatStyle().getChatHoverEvent();
         if (var6.getAction() == HoverEvent.Action.SHOW_ITEM) {
            ItemStack var7 = null;

            try {
               NBTTagCompound var8 = JsonToNBT.getTagFromJson(var6.getValue().getUnformattedText());
               if (var8 != null) {
                  var7 = ItemStack.loadItemStackFromNBT(var8);
               }
            } catch (NBTException var13) {
            }

            if (var7 != null) {
               this.renderToolTip(var7, integer2, integer3);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", integer2, integer3);
            }
         } else if (var6.getAction() == HoverEvent.Action.SHOW_ENTITY) {
            if (this.mc.gameSettings.advancedItemTooltips) {
               try {
                  NBTTagCompound var16 = JsonToNBT.getTagFromJson(var6.getValue().getUnformattedText());
                  if (var16 != null) {
                     ArrayList var9 = Lists.newArrayList();
                     var9.add(var16.getString("name"));
                     if (var16.hasKey("type", 8)) {
                        String var10 = var16.getString("type");
                        var9.add("Type: " + var10 + " (" + EntityList.getIDFromString(var10) + ")");
                     }

                     var9.add(var16.getString("id"));
                     this.drawHoveringText(var9, integer2, integer3);
                  } else {
                     this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", integer2, integer3);
                  }
               } catch (NBTException var12) {
                  this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", integer2, integer3);
               }
            }
         } else if (var6.getAction() == HoverEvent.Action.SHOW_TEXT) {
            this.drawHoveringText(NEWLINE_SPLITTER.splitToList(var6.getValue().getFormattedText()), integer2, integer3);
         } else if (var6.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
            StatBase var15 = StatList.getOneShotStat(var6.getValue().getUnformattedText());
            if (var15 != null) {
               IChatComponent var17 = var15.getStatName();
               ChatComponentTranslation var18 = new ChatComponentTranslation("stats.tooltip.type." + (var15.isAchievement() ? "achievement" : "statistic"));
               var18.getChatStyle().setItalic(Boolean.TRUE);
               String var19 = var15 instanceof Achievement ? ((Achievement)var15).getDescription() : null;
               ArrayList var11 = Lists.newArrayList(new String[]{var17.getFormattedText(), var18.getFormattedText()});
               if (var19 != null) {
                  var11.addAll(this.fontRendererObj.listFormattedStringToWidth(var19, 150));
               }

               this.drawHoveringText(var11, integer2, integer3);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", integer2, integer3);
            }
         }

         GlStateManager.disableLighting();
      }
   }

   protected void setText(String string, boolean boolean2) {
   }

   protected boolean handleComponentClick(IChatComponent iChatComponent) {
      if (iChatComponent != null) {
         ClickEvent var4 = iChatComponent.getChatStyle().getChatClickEvent();
         if (isShiftKeyDown()) {
            if (iChatComponent.getChatStyle().getInsertion() != null) {
               this.setText(iChatComponent.getChatStyle().getInsertion(), false);
            }
         } else if (var4 != null) {
            if (var4.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.mc.gameSettings.chatLinks) {
                  return false;
               }

               try {
                  URI var5 = new URI(var4.getValue());
                  String var6 = var5.getScheme();
                  if (var6 == null) {
                     throw new URISyntaxException(var4.getValue(), "Missing protocol");
                  }

                  if (!PROTOCOLS.contains(var6.toLowerCase())) {
                     throw new URISyntaxException(var4.getValue(), "Unsupported protocol: " + var6.toLowerCase());
                  }

                  if (this.mc.gameSettings.chatLinksPrompt) {
                     this.clickedLinkURI = var5;
                     this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var4.getValue(), 31102009, false));
                  } else {
                     this.openWebLink(var5);
                  }
               } catch (URISyntaxException var7) {
                  LOGGER.error("Can't open url for " + var4, var7);
               }
            } else if (var4.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI var8 = new File(var4.getValue()).toURI();
               this.openWebLink(var8);
            } else if (var4.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.setText(var4.getValue(), true);
            } else if (var4.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendChatMessage(var4.getValue(), false);
            } else {
               LOGGER.error("Don't know how to handle " + var4);
            }

            return true;
         }
      }

      return false;
   }

   public void sendChatMessage(String string) {
      this.sendChatMessage(string, true);
   }

   public void sendChatMessage(String string, boolean boolean2) {
      if (boolean2) {
         this.mc.ingameGUI.getChatGUI().addToSentMessages(string);
      }

      this.mc.thePlayer.sendChatMessage(string);
   }

   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (button == 0) {
         for (int var4 = 0; var4 < this.m.size(); var4++) {
            GuiButton var5 = this.m.get(var4);
            if (var5.mousePressed(this.mc, mouseX, mouseY)) {
               this.selectedButton = var5;
               var5.playPressSound(this.mc.getSoundHandler());
               this.actionPerformed(var5);
            }
         }
      }
   }

   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      if (this.selectedButton != null && button == 0) {
         this.selectedButton.mouseReleased(mouseX, mouseY);
         this.selectedButton = null;
      }
   }

   protected void mouseClickMove(int integer1, int integer2, int integer3, long long4) {
   }

   protected void actionPerformed(GuiButton guiButton) throws IOException {
   }

   public void setWorldAndResolution(Minecraft minecraft, int integer2, int integer3) {
      this.mc = minecraft;
      this.itemRender = minecraft.getRenderItem();
      this.fontRendererObj = minecraft.fontRendererObj;
      this.width = integer2;
      this.height = integer3;
      this.m.clear();
      this.onInitGui();
   }

   public void setGuiSize(int integer1, int integer2) {
      this.width = integer1;
      this.height = integer2;
   }

   public void onInitGui() {
   }

   public void handleInput() throws IOException {
      if (Mouse.isCreated()) {
         while (Mouse.next()) {
            this.handleMouseInput();
         }
      }

      if (Keyboard.isCreated()) {
         while (Keyboard.next()) {
            this.handleKeyboardInput();
         }
      }
   }

   public void handleMouseInput() throws IOException {
      int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
      int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
      int var3 = Mouse.getEventButton();
      if (Mouse.getEventButtonState()) {
         if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
            return;
         }

         this.eventButton = var3;
         this.lastMouseEvent = Minecraft.getSystemTime();
         this.onMouseClicked(var1, var2, this.eventButton);
      } else if (var3 != -1) {
         if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
            return;
         }

         this.eventButton = -1;
         this.onMouseReleased(var1, var2, var3);
      } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
         long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
         this.mouseClickMove(var1, var2, this.eventButton, var4);
      }
   }

   public void handleKeyboardInput() throws IOException {
      if (Keyboard.getEventKeyState()) {
         this.onKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
      }

      this.mc.dispatchKeypresses();
   }

   public void onUpdateScreen() {
   }

   public void onGuiClosed() {
   }

   public void drawDefaultBackground() {
      this.drawWorldBackground(0);
   }

   public void drawWorldBackground(int integer) {
      if (this.mc.theWorld != null) {
         this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
      } else {
         this.drawBackground(integer);
      }
   }

   public void drawBackground(int integer) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var5.pos(0.0, this.height, 0.0).tex(0.0, this.height / 32.0F + integer).color(64, 64, 64, 255).endVertex();
      var5.pos(this.width, this.height, 0.0).tex(this.width / 32.0F, this.height / 32.0F + integer).color(64, 64, 64, 255).endVertex();
      var5.pos(this.width, 0.0, 0.0).tex(this.width / 32.0F, integer).color(64, 64, 64, 255).endVertex();
      var5.pos(0.0, 0.0, 0.0).tex(0.0, integer).color(64, 64, 64, 255).endVertex();
      var4.draw();
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      if (integer == 31102009) {
         if (boolean1) {
            this.openWebLink(this.clickedLinkURI);
         }

         this.clickedLinkURI = null;
         this.mc.displayGuiScreen(this);
      }
   }

   private void openWebLink(URI uRI) {
      try {
         Class var2 = Class.forName("java.awt.Desktop");
         Object var3 = var2.getMethod("getDesktop").invoke(null);
         var2.getMethod("browse", URI.class).invoke(var3, uRI);
      } catch (Throwable var4) {
         LOGGER.error("Couldn't open link", var4);
      }
   }

   public static boolean isCtrlKeyDown() {
      return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public static boolean isShiftKeyDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public static boolean isAltKeyDown() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public static boolean isKeyComboCtrlX(int integer) {
      return integer == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlV(int integer) {
      return integer == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlC(int integer) {
      return integer == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlA(int integer) {
      return integer == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public void onResize(Minecraft minecraft, int integer2, int integer3) {
      this.setWorldAndResolution(minecraft, integer2, integer3);
   }
}
