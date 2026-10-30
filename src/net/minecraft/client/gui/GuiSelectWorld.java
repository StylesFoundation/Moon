package net.minecraft.client.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {
   private static final Logger logger = LogManager.getLogger();
   private final DateFormat field_146633_h = new SimpleDateFormat();
   protected GuiScreen parentScreen;
   protected String screenTitle = "Select world";
   private boolean field_146634_i;
   private int selectedIndex;
   private java.util.List<SaveFormatComparator> field_146639_s;
   private GuiSelectWorld.List availableWorlds;
   private String field_146637_u;
   private String field_146636_v;
   private final String[] field_146635_w = new String[4];
   private boolean confirmingDelete;
   private GuiButton deleteButton;
   private GuiButton selectButton;
   private GuiButton renameButton;
   private GuiButton recreateButton;

   public GuiSelectWorld(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
   }

   @Override
   public void onInitGui() {
      this.screenTitle = I18n.format("selectWorld.title");

      try {
         this.loadLevelList();
      } catch (AnvilConverterException var2) {
         logger.error("Couldn't load level list", var2);
         this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
         return;
      }

      this.field_146637_u = I18n.format("selectWorld.world");
      this.field_146636_v = I18n.format("selectWorld.conversion");
      this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival");
      this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative");
      this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure");
      this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator");
      this.availableWorlds = new GuiSelectWorld.List(this.mc);
      this.availableWorlds.registerScrollButtons(4, 5);
      this.addWorldSelectionButtons();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.availableWorlds.handleMouseInput();
   }

   private void loadLevelList() throws AnvilConverterException {
      ISaveFormat var1 = this.mc.getSaveLoader();
      this.field_146639_s = var1.getSaveList();
      Collections.sort(this.field_146639_s);
      this.selectedIndex = -1;
   }

   protected String func_146621_a(int integer) {
      return this.field_146639_s.get(integer).getFileName();
   }

   protected String func_146614_d(int integer) {
      String var2 = this.field_146639_s.get(integer).getDisplayName();
      if (StringUtils.isEmpty(var2)) {
         var2 = I18n.format("selectWorld.world") + " " + (integer + 1);
      }

      return var2;
   }

   public void addWorldSelectionButtons() {
      this.m.add(this.selectButton = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select")));
      this.m.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create")));
      this.m.add(this.renameButton = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename")));
      this.m.add(this.deleteButton = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete")));
      this.m.add(this.recreateButton = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate")));
      this.m.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel")));
      this.selectButton.enabled = false;
      this.deleteButton.enabled = false;
      this.renameButton.enabled = false;
      this.recreateButton.enabled = false;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 2) {
            String var4 = this.func_146614_d(this.selectedIndex);
            if (var4 != null) {
               this.confirmingDelete = true;
               GuiYesNo var5 = makeDeleteWorldYesNo(this, var4, this.selectedIndex);
               this.mc.displayGuiScreen(var5);
            }
         } else if (guiButton.l == 1) {
            this.func_146615_e(this.selectedIndex);
         } else if (guiButton.l == 3) {
            this.mc.displayGuiScreen(new GuiCreateWorld(this));
         } else if (guiButton.l == 6) {
            this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.selectedIndex)));
         } else if (guiButton.l == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton.l == 7) {
            GuiCreateWorld var7 = new GuiCreateWorld(this);
            ISaveHandler var8 = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.selectedIndex), false);
            WorldInfo var6 = var8.loadWorldInfo();
            var8.flush();
            var7.recreateFromExistingWorld(var6);
            this.mc.displayGuiScreen(var7);
         } else {
            this.availableWorlds.actionPerformed(guiButton);
         }
      }
   }

   public void func_146615_e(int integer) {
      this.mc.displayGuiScreen(null);
      if (!this.field_146634_i) {
         this.field_146634_i = true;
         String var2 = this.func_146621_a(integer);
         if (var2 == null) {
            var2 = "World" + integer;
         }

         String var3 = this.func_146614_d(integer);
         if (var3 == null) {
            var3 = "World" + integer;
         }

         if (this.mc.getSaveLoader().canLoadWorld(var2)) {
            this.mc.launchIntegratedServer(var2, var3, null);
         }
      }
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      if (this.confirmingDelete) {
         this.confirmingDelete = false;
         if (boolean1) {
            ISaveFormat var3 = this.mc.getSaveLoader();
            var3.flushCache();
            var3.deleteWorldDirectory(this.func_146621_a(integer));

            try {
               this.loadLevelList();
            } catch (AnvilConverterException var5) {
               logger.error("Couldn't load level list", var5);
            }
         }

         this.mc.displayGuiScreen(this);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.availableWorlds.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public static GuiYesNo makeDeleteWorldYesNo(GuiYesNoCallback guiYesNoCallback, String string, int integer) {
      String var3 = I18n.format("selectWorld.deleteQuestion");
      String var4 = "'" + string + "' " + I18n.format("selectWorld.deleteWarning");
      String var5 = I18n.format("selectWorld.deleteButton");
      String var6 = I18n.format("gui.cancel");
      return new GuiYesNo(guiYesNoCallback, var3, var4, var5, var6, integer);
   }

   class List extends GuiSlot {
      public List(Minecraft minecraft) {
         super(minecraft, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
      }

      @Override
      protected int getSize() {
         return GuiSelectWorld.this.field_146639_s.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
         GuiSelectWorld.this.selectedIndex = index;
         boolean var5 = GuiSelectWorld.this.selectedIndex >= 0 && GuiSelectWorld.this.selectedIndex < this.getSize();
         GuiSelectWorld.this.selectButton.enabled = var5;
         GuiSelectWorld.this.deleteButton.enabled = var5;
         GuiSelectWorld.this.renameButton.enabled = var5;
         GuiSelectWorld.this.recreateButton.enabled = var5;
         if (doubleClick && var5) {
            GuiSelectWorld.this.func_146615_e(index);
         }
      }

      @Override
      protected boolean isSelected(int index) {
         return index == GuiSelectWorld.this.selectedIndex;
      }

      @Override
      protected int getContentHeight() {
         return GuiSelectWorld.this.field_146639_s.size() * 36;
      }

      @Override
      protected void drawBackground() {
         GuiSelectWorld.this.drawDefaultBackground();
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         SaveFormatComparator var9 = GuiSelectWorld.this.field_146639_s.get(index);
         String var10 = var9.getDisplayName();
         if (StringUtils.isEmpty(var10)) {
            var10 = GuiSelectWorld.this.field_146637_u + " " + (index + 1);
         }

         String var11 = var9.getFileName() + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(var9.getLastTimePlayed())) + ")";
         String var12;
         if (var9.Method1018()) {
            var12 = GuiSelectWorld.this.field_146636_v + " " + "";
         } else {
            var12 = GuiSelectWorld.this.field_146635_w[var9.getEnumGameType().getID()];
            if (var9.Method1019()) {
               var12 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore") + EnumChatFormatting.RESET;
            }

            if (var9.Method1020()) {
               var12 = var12 + ", " + I18n.format("selectWorld.cheats");
            }
         }

         GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var10, left + 2, top + 1, 16777215);
         GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var11, left + 2, top + 12, 8421504);
         GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, var12, left + 2, top + 12 + 10, 8421504);
      }
   }
}
