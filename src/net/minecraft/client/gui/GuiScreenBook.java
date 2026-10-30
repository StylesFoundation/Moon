package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
   private final EntityPlayer editingPlayer;
   private final ItemStack bookObj;
   private final boolean bookIsUnsigned;
   private boolean bookIsModified;
   private boolean bookGettingSigned;
   private int updateCount;
   private final int bookImageWidth = 192;
   private final int bookImageHeight = 192;
   private int bookTotalPages = 1;
   private int currPage;
   private NBTTagList bookPages;
   private String bookTitle = "";
   private List<IChatComponent> field_175386_A;
   private int field_175387_B = -1;
   private GuiScreenBook.NextPageButton buttonNextPage;
   private GuiScreenBook.NextPageButton buttonPreviousPage;
   private GuiButton buttonDone;
   private GuiButton buttonSign;
   private GuiButton buttonFinalize;
   private GuiButton buttonCancel;

   public GuiScreenBook(EntityPlayer entityPlayer, ItemStack itemStack, boolean boolean3) {
      this.editingPlayer = entityPlayer;
      this.bookObj = itemStack;
      this.bookIsUnsigned = boolean3;
      if (itemStack.hasTagCompound()) {
         NBTTagCompound var6 = itemStack.getTagCompound();
         this.bookPages = var6.getTagList("pages", 8);
         if (this.bookPages != null) {
            this.bookPages = (NBTTagList)this.bookPages.copy();
            this.bookTotalPages = this.bookPages.tagCount();
            if (this.bookTotalPages < 1) {
               this.bookTotalPages = 1;
            }
         }
      }

      if (this.bookPages == null && boolean3) {
         this.bookPages = new NBTTagList();
         this.bookPages.appendTag(new NBTTagString(""));
         this.bookTotalPages = 1;
      }
   }

   @Override
   public void onUpdateScreen() {
      super.onUpdateScreen();
      this.updateCount++;
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      Keyboard.enableRepeatEvents(true);
      if (this.bookIsUnsigned) {
         this.m.add(this.buttonSign = new GuiButton(3, this.width / 2 - 100, 196, 98, 20, I18n.format("book.signButton")));
         this.m.add(this.buttonDone = new GuiButton(0, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.done")));
         this.m.add(this.buttonFinalize = new GuiButton(5, this.width / 2 - 100, 196, 98, 20, I18n.format("book.finalizeButton")));
         this.m.add(this.buttonCancel = new GuiButton(4, this.width / 2 + 2, 196, 98, 20, I18n.format("gui.cancel")));
      } else {
         this.m.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 196, 200, 20, I18n.format("gui.done")));
      }

      int var3 = (this.width - 192) / 2;
      this.m.add(this.buttonNextPage = new GuiScreenBook.NextPageButton(1, var3 + 120, 156, true));
      this.m.add(this.buttonPreviousPage = new GuiScreenBook.NextPageButton(2, var3 + 38, 156, false));
      this.updateButtons();
   }

   @Override
   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   private void updateButtons() {
      this.buttonNextPage.visible = !this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
      this.buttonPreviousPage.visible = !this.bookGettingSigned && this.currPage > 0;
      this.buttonDone.visible = !this.bookIsUnsigned || !this.bookGettingSigned;
      if (this.bookIsUnsigned) {
         this.buttonSign.visible = !this.bookGettingSigned;
         this.buttonCancel.visible = this.bookGettingSigned;
         this.buttonFinalize.visible = this.bookGettingSigned;
         this.buttonFinalize.enabled = this.bookTitle.trim().length() > 0;
      }
   }

   private void sendBookToServer(boolean boolean1) throws IOException {
      if (this.bookIsUnsigned && this.bookIsModified && this.bookPages != null) {
         while (this.bookPages.tagCount() > 1) {
            String var2 = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
            if (var2.length() != 0) {
               break;
            }

            this.bookPages.removeTag(this.bookPages.tagCount() - 1);
         }

         if (this.bookObj.hasTagCompound()) {
            this.bookObj.getTagCompound().setTag("pages", this.bookPages);
         } else {
            this.bookObj.setTagInfo("pages", this.bookPages);
         }

         String var5 = "MC|BEdit";
         if (boolean1) {
            var5 = "MC|BSign";
            this.bookObj.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
            this.bookObj.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));

            for (int var3 = 0; var3 < this.bookPages.tagCount(); var3++) {
               String var4 = this.bookPages.getStringTagAt(var3);
               var4 = IChatComponent.Serializer.componentToJson(new ChatComponentText(var4));
               this.bookPages.set(var3, new NBTTagString(var4));
            }

            this.bookObj.setItem(Items.written_book);
         }

         PacketBuffer var6 = new PacketBuffer(Unpooled.buffer());
         var6.writeItemStackToBuffer(this.bookObj);
         this.mc.getNetHandler().Method754(new C17PacketCustomPayload(var5, var6));
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 0) {
            this.mc.displayGuiScreen(null);
            this.sendBookToServer(false);
         } else if (guiButton.l == 3 && this.bookIsUnsigned) {
            this.bookGettingSigned = true;
         } else if (guiButton.l == 1) {
            if (this.currPage < this.bookTotalPages - 1) {
               this.currPage++;
            } else if (this.bookIsUnsigned) {
               this.addNewPage();
               if (this.currPage < this.bookTotalPages - 1) {
                  this.currPage++;
               }
            }
         } else if (guiButton.l == 2) {
            if (this.currPage > 0) {
               this.currPage--;
            }
         } else if (guiButton.l == 5 && this.bookGettingSigned) {
            this.sendBookToServer(true);
            this.mc.displayGuiScreen(null);
         } else if (guiButton.l == 4 && this.bookGettingSigned) {
            this.bookGettingSigned = false;
         }

         this.updateButtons();
      }
   }

   private void addNewPage() {
      if (this.bookPages != null && this.bookPages.tagCount() < 50) {
         this.bookPages.appendTag(new NBTTagString(""));
         this.bookTotalPages++;
         this.bookIsModified = true;
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      super.onKeyTyped(charTyped, keyCode);
      if (this.bookIsUnsigned) {
         if (this.bookGettingSigned) {
            this.keyTypedInTitle(charTyped, keyCode);
         } else {
            this.keyTypedInBook(charTyped, keyCode);
         }
      }
   }

   private void keyTypedInBook(char character, int integer) {
      if (GuiScreen.isKeyComboCtrlV(integer)) {
         this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
      } else {
         switch (integer) {
            case 14:
               String var3 = this.pageGetCurrent();
               if (var3.length() > 0) {
                  this.pageSetCurrent(var3.substring(0, var3.length() - 1));
               }

               return;
            case 28:
            case 156:
               this.pageInsertIntoCurrent("\n");
               return;
            default:
               if (ChatAllowedCharacters.isAllowedCharacter(character)) {
                  this.pageInsertIntoCurrent(Character.toString(character));
               }
         }
      }
   }

   private void keyTypedInTitle(char character, int integer) throws IOException {
      switch (integer) {
         case 14:
            if (!this.bookTitle.isEmpty()) {
               this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
               this.updateButtons();
            }

            return;
         case 28:
         case 156:
            if (!this.bookTitle.isEmpty()) {
               this.sendBookToServer(true);
               this.mc.displayGuiScreen(null);
            }

            return;
         default:
            if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(character)) {
               this.bookTitle = this.bookTitle + character;
               this.updateButtons();
               this.bookIsModified = true;
            }
      }
   }

   private String pageGetCurrent() {
      return this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount() ? this.bookPages.getStringTagAt(this.currPage) : "";
   }

   private void pageSetCurrent(String string) {
      if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
         this.bookPages.set(this.currPage, new NBTTagString(string));
         this.bookIsModified = true;
      }
   }

   private void pageInsertIntoCurrent(String string) {
      String var5 = this.pageGetCurrent() + string;
      int var6 = this.fontRendererObj.splitStringWidth(var5 + EnumChatFormatting.BLACK + "_", 118);
      if (var6 <= 128 && var5.length() < 256) {
         this.pageSetCurrent(var5);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(bookGuiTextures);
      int var6 = (this.width - 192) / 2;
      this.drawTexturedModalRect(var6, 2, 0, 0, 192, 192);
      if (this.bookGettingSigned) {
         String var8 = this.bookTitle;
         if (this.bookIsUnsigned) {
            if (this.updateCount / 6 % 2 == 0) {
               var8 = var8 + EnumChatFormatting.BLACK + "_";
            } else {
               var8 = var8 + EnumChatFormatting.GRAY + "_";
            }
         }

         String var9 = I18n.format("book.editTitle");
         int var10 = this.fontRendererObj.getStringWidth(var9);
         this.fontRendererObj.drawString(var9, var6 + 36 + (116 - var10) / 2, 34.0F, 0);
         int var11 = this.fontRendererObj.getStringWidth(var8);
         this.fontRendererObj.drawString(var8, var6 + 36 + (116 - var11) / 2, 50.0F, 0);
         String var12 = I18n.format("book.byAuthor", this.editingPlayer.getName());
         int var13 = this.fontRendererObj.getStringWidth(var12);
         this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var12, var6 + 36 + (116 - var13) / 2, 60.0F, 0);
         String var14 = I18n.format("book.finalizeWarning");
         this.fontRendererObj.drawSplitString(var14, var6 + 36, 82, 116, 0);
      } else {
         String var16 = I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
         String var17 = "";
         if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            var17 = this.bookPages.getStringTagAt(this.currPage);
         }

         if (this.bookIsUnsigned) {
            if (this.fontRendererObj.getBidiFlag()) {
               var17 = var17 + "_";
            } else if (this.updateCount / 6 % 2 == 0) {
               var17 = var17 + EnumChatFormatting.BLACK + "_";
            } else {
               var17 = var17 + EnumChatFormatting.GRAY + "_";
            }
         } else if (this.field_175387_B != this.currPage) {
            if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
               try {
                  IChatComponent var18 = IChatComponent.Serializer.jsonToComponent(var17);
                  this.field_175386_A = var18 != null ? GuiUtilRenderComponents.Method379(var18, 116, this.fontRendererObj, true, true, false, null) : null;
               } catch (JsonParseException var15) {
                  this.field_175386_A = null;
               }
            } else {
               ChatComponentText var19 = new ChatComponentText(EnumChatFormatting.DARK_RED + "* Invalid book tag *");
               this.field_175386_A = Lists.newArrayList(var19);
            }

            this.field_175387_B = this.currPage;
         }

         int var20 = this.fontRendererObj.getStringWidth(var16);
         this.fontRendererObj.drawString(var16, var6 - var20 + 192 - 44, 18.0F, 0);
         if (this.field_175386_A == null) {
            this.fontRendererObj.drawSplitString(var17, var6 + 36, 34, 116, 0);
         } else {
            int var21 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());

            for (int var22 = 0; var22 < var21; var22++) {
               IChatComponent var24 = this.field_175386_A.get(var22);
               this.fontRendererObj.drawString(var24.getUnformattedText(), var6 + 36, 34 + var22 * this.fontRendererObj.FONT_HEIGHT, 0);
            }

            IChatComponent var23 = this.func_175385_b(mouseX, mouseY);
            if (var23 != null) {
               this.handleComponentHover(var23, mouseX, mouseY);
            }
         }
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (button == 0) {
         IChatComponent var4 = this.func_175385_b(mouseX, mouseY);
         if (this.handleComponentClick(var4)) {
            return;
         }
      }

      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected boolean handleComponentClick(IChatComponent iChatComponent) {
      ClickEvent var2 = iChatComponent == null ? null : iChatComponent.getChatStyle().getChatClickEvent();
      if (var2 == null) {
         return false;
      }

      if (var2.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String var6 = var2.getValue();

         try {
            int var4 = Integer.parseInt(var6) - 1;
            if (var4 >= 0 && var4 < this.bookTotalPages && var4 != this.currPage) {
               this.currPage = var4;
               this.updateButtons();
               return true;
            }
         } catch (Throwable var5) {
         }

         return false;
      } else {
         boolean var3 = super.handleComponentClick(iChatComponent);
         if (var3 && var2.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.mc.displayGuiScreen(null);
         }

         return var3;
      }
   }

   public IChatComponent func_175385_b(int integer1, int integer2) {
      if (this.field_175386_A == null) {
         return null;
      }

      int var5 = integer1 - (this.width - 192) / 2 - 36;
      int var6 = integer2 - 2 - 16 - 16;
      if (var5 >= 0 && var6 >= 0) {
         int var7 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
         if (var5 <= 116 && var6 < this.mc.fontRendererObj.FONT_HEIGHT * var7 + var7) {
            int var8 = var6 / this.mc.fontRendererObj.FONT_HEIGHT;
            if (var8 >= 0 && var8 < this.field_175386_A.size()) {
               IChatComponent var9 = this.field_175386_A.get(var8);
               int var10 = 0;

               for (IChatComponent var12 : var9) {
                  if (var12 instanceof ChatComponentText) {
                     var10 += this.mc.fontRendererObj.getStringWidth(((ChatComponentText)var12).getChatComponentText_TextValue());
                     if (var10 > var5) {
                        return var12;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   static class NextPageButton extends GuiButton {
      private final boolean field_146151_o;

      public NextPageButton(int integer1, int integer2, int integer3, boolean boolean4) {
         super(integer1, integer2, integer3, 23, 13, "");
         this.field_146151_o = boolean4;
      }

      @Override
      public void drawButton(Minecraft minecraft, int integer2, int integer3) {
         if (this.visible) {
            boolean var6 = integer2 >= this.xPosition
               && integer3 >= this.yPosition
               && integer2 < this.xPosition + this.width
               && integer3 < this.yPosition + this.h;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            minecraft.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
            byte var7 = 0;
            short var8 = 192;
            if (var6) {
               var7 += 23;
            }

            if (!this.field_146151_o) {
               var8 += 13;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var7, var8, 23, 13);
         }
      }
   }
}
