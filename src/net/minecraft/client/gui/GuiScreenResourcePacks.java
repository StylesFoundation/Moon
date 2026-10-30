package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

public class GuiScreenResourcePacks extends GuiScreen {
   private static final Logger logger = LogManager.getLogger();
   private final GuiScreen parentScreen;
   private List<ResourcePackListEntry> availableResourcePacks;
   private List<ResourcePackListEntry> selectedResourcePacks;
   private GuiResourcePackAvailable availableResourcePacksList;
   private GuiResourcePackSelected selectedResourcePacksList;
   private boolean changed = false;

   public GuiScreenResourcePacks(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
   }

   @Override
   public void onInitGui() {
      this.m.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder")));
      this.m.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done")));
      if (!this.changed) {
         this.availableResourcePacks = Lists.newArrayList();
         this.selectedResourcePacks = Lists.newArrayList();
         ResourcePackRepository var3 = this.mc.getResourcePackRepository();
         var3.updateRepositoryEntriesAll();
         List<ResourcePackRepository.Entry> var4 = Lists.newArrayList(var3.getRepositoryEntriesAll());
         var4.removeAll(var3.getRepositoryEntries());

         for (ResourcePackRepository.Entry var6 : var4) {
            this.availableResourcePacks.add(new ResourcePackListEntryFound(this, var6));
         }

         for (ResourcePackRepository.Entry var8 : Lists.reverse(var3.getRepositoryEntries())) {
            this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, var8));
         }

         this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
      }

      this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
      this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
      this.availableResourcePacksList.registerScrollButtons(7, 8);
      this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
      this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
      this.selectedResourcePacksList.registerScrollButtons(7, 8);
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.selectedResourcePacksList.handleMouseInput();
      this.availableResourcePacksList.handleMouseInput();
   }

   public boolean hasResourcePackEntry(ResourcePackListEntry resourcePackListEntry) {
      return this.selectedResourcePacks.contains(resourcePackListEntry);
   }

   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry resourcePackListEntry) {
      return this.hasResourcePackEntry(resourcePackListEntry) ? this.selectedResourcePacks : this.availableResourcePacks;
   }

   public List<ResourcePackListEntry> getAvailableResourcePacks() {
      return this.availableResourcePacks;
   }

   public List<ResourcePackListEntry> getSelectedResourcePacks() {
      return this.selectedResourcePacks;
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 2) {
            File var2 = this.mc.getResourcePackRepository().getDirResourcepacks();
            String var3 = var2.getAbsolutePath();
            if (Util.getOSType() == Util.EnumOS.OSX) {
               try {
                  logger.info(var3);
                  Runtime.getRuntime().exec(new String[]{"/usr/bin/open", var3});
                  return;
               } catch (IOException var9) {
                  logger.error("Couldn't open file", var9);
               }
            } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
               String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", var3);

               try {
                  Runtime.getRuntime().exec(var4);
                  return;
               } catch (IOException var8) {
                  logger.error("Couldn't open file", var8);
               }
            }

            boolean var13 = false;

            try {
               Class var5 = Class.forName("java.awt.Desktop");
               Object var6 = var5.getMethod("getDesktop").invoke(null);
               var5.getMethod("browse", URI.class).invoke(var6, var2.toURI());
            } catch (Throwable var7) {
               logger.error("Couldn't open link", var7);
               var13 = true;
            }

            if (var13) {
               logger.info("Opening via system class!");
               Sys.openURL("file://" + var3);
            }
         } else if (guiButton.l == 1) {
            if (this.changed) {
               List<ResourcePackRepository.Entry> var10 = Lists.newArrayList();

               for (ResourcePackListEntry var14 : this.selectedResourcePacks) {
                  if (var14 instanceof ResourcePackListEntryFound) {
                     var10.add(((ResourcePackListEntryFound)var14).func_148318_i());
                  }
               }

               Collections.reverse(var10);
               this.mc.getResourcePackRepository().setRepositories(var10);
               this.mc.gameSettings.resourcePacks.clear();
               this.mc.gameSettings.incompatibleResourcePacks.clear();

               for (ResourcePackRepository.Entry var15 : var10) {
                  this.mc.gameSettings.resourcePacks.add(var15.getResourcePackName());
                  if (var15.func_183027_f() != 1) {
                     this.mc.gameSettings.incompatibleResourcePacks.add(var15.getResourcePackName());
                  }
               }

               this.mc.gameSettings.saveOptions();
               this.mc.refreshResources();
            }

            this.mc.displayGuiScreen(this.parentScreen);
         }
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      this.availableResourcePacksList.mouseClicked(mouseX, mouseY, button);
      this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawBackground(0);
      this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
      this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title"), this.width / 2, 16, 16777215);
      this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo"), this.width / 2 - 77, this.height - 26, 8421504);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void markChanged() {
      this.changed = true;
   }
}
