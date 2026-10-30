package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;

public class GuiResourcePackSelected extends GuiResourcePackList {
   public GuiResourcePackSelected(Minecraft minecraft, int integer2, int integer3, List<ResourcePackListEntry> list) {
      super(minecraft, integer2, integer3, list);
   }

   @Override
   protected String getListHeader() {
      return I18n.format("resourcePack.selected.title");
   }
}
