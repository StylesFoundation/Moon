package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
   protected final Minecraft mc;
   protected final List<ResourcePackListEntry> field_148204_l;

   public GuiResourcePackList(Minecraft minecraft, int integer2, int integer3, List<ResourcePackListEntry> list) {
      super(minecraft, integer2, integer3, 32, integer3 - 55 + 4, 36);
      this.mc = minecraft;
      this.field_148204_l = list;
      this.field_148163_i = false;
      this.setHasListHeader(true, (int)(minecraft.fontRendererObj.FONT_HEIGHT * 1.5F));
   }

   @Override
   protected void drawListHeader(int integer1, int integer2, Tessellator tessellator) {
      String var6 = "" + EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + this.getListHeader();
      this.mc
         .fontRendererObj
         .drawString(var6, integer1 + this.width / 2 - this.mc.fontRendererObj.getStringWidth(var6) / 2, Math.min(this.top + 3, integer2), 16777215);
   }

   protected abstract String getListHeader();

   public List<ResourcePackListEntry> getList() {
      return this.field_148204_l;
   }

   @Override
   protected int getSize() {
      return this.getList().size();
   }

   public ResourcePackListEntry getListEntry(int integer) {
      return this.getList().get(integer);
   }

   @Override
   public int getListWidth() {
      return this.width;
   }

   @Override
   protected int getScrollBarX() {
      return this.right - 6;
   }
}
