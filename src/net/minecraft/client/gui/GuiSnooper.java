package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen {
   private final GuiScreen field_146608_a;
   private final GameSettings game_settings_2;
   private final java.util.List<String> field_146604_g = Lists.newArrayList();
   private final java.util.List<String> field_146609_h = Lists.newArrayList();
   private String field_146610_i;
   private String[] field_146607_r;
   private GuiSnooper.List field_146606_s;
   private GuiButton field_146605_t;

   public GuiSnooper(GuiScreen guiScreen, GameSettings gameSettings) {
      this.field_146608_a = guiScreen;
      this.game_settings_2 = gameSettings;
   }

   @Override
   public void onInitGui() {
      this.field_146610_i = I18n.format("options.snooper.title");
      String var3 = I18n.format("options.snooper.desc");
      ArrayList<String> var4 = Lists.newArrayList();
      var4.addAll(this.fontRendererObj.listFormattedStringToWidth(var3, this.width - 30));
      this.field_146607_r = var4.toArray(new String[0]);
      this.field_146604_g.clear();
      this.field_146609_h.clear();
      this.m
         .add(
            this.field_146605_t = new GuiButton(
               1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)
            )
         );
      this.m.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done")));
      boolean var5 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;

      for (Entry var7 : new TreeMap<>(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
         this.field_146604_g.add((var5 ? "C " : "") + (String)var7.getKey());
         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var7.getValue(), this.width - 220));
      }

      if (var5) {
         for (Entry var9 : new TreeMap<>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add("S " + (String)var9.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var9.getValue(), this.width - 220));
         }
      }

      this.field_146606_s = new GuiSnooper.List();
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146606_s.handleMouseInput();
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 2) {
            this.game_settings_2.saveOptions();
            this.game_settings_2.saveOptions();
            this.mc.displayGuiScreen(this.field_146608_a);
         }

         if (guiButton.l == 1) {
            this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
            this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.field_146606_s.drawScreen(mouseX, mouseY, partialTicks);
      this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
      int var6 = 22;

      for (String var10 : this.field_146607_r) {
         this.drawCenteredString(this.fontRendererObj, var10, this.width / 2, var6, 8421504);
         var6 += this.fontRendererObj.FONT_HEIGHT;
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   class List extends GuiSlot {
      public List() {
         super(
            GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1
         );
      }

      @Override
      protected int getSize() {
         return GuiSnooper.this.field_146604_g.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      }

      @Override
      protected boolean isSelected(int index) {
         return false;
      }

      @Override
      protected void drawBackground() {
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146604_g.get(index), 10.0F, top, 16777215);
         GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146609_h.get(index), 230.0F, top, 16777215);
      }

      @Override
      protected int getScrollBarX() {
         return this.width - 10;
      }
   }
}
