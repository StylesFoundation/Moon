package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiStats extends GuiScreen implements IProgressMeter {
   protected GuiScreen parentScreen;
   protected String screenTitle = "Select world";
   private GuiStats.StatsGeneral generalStats;
   private GuiStats.StatsItem itemStats;
   private GuiStats.StatsBlock blockStats;
   private GuiStats.StatsMobsList mobStats;
   private final StatFileWriter C;
   private GuiSlot displaySlot;
   private boolean doesGuiPauseGame = true;

   public GuiStats(GuiScreen guiScreen, StatFileWriter statFileWriter) {
      this.parentScreen = guiScreen;
      this.C = statFileWriter;
   }

   @Override
   public void onInitGui() {
      this.screenTitle = I18n.format("gui.stats");
      this.doesGuiPauseGame = true;
      this.mc.getNetHandler().Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      if (this.displaySlot != null) {
         this.displaySlot.handleMouseInput();
      }
   }

   public void func_175366_f() {
      this.generalStats = new GuiStats.StatsGeneral(this.mc);
      this.generalStats.registerScrollButtons(1, 1);
      this.itemStats = new GuiStats.StatsItem(this.mc);
      this.itemStats.registerScrollButtons(1, 1);
      this.blockStats = new GuiStats.StatsBlock(this.mc);
      this.blockStats.registerScrollButtons(1, 1);
      this.mobStats = new GuiStats.StatsMobsList(this.mc);
      this.mobStats.registerScrollButtons(1, 1);
   }

   public void createButtons() {
      this.m.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done")));
      this.m.add(new GuiButton(1, this.width / 2 - 160, this.height - 52, 80, 20, I18n.format("stat.generalButton")));
      GuiButton var3;
      this.m.add(var3 = new GuiButton(2, this.width / 2 - 80, this.height - 52, 80, 20, I18n.format("stat.blocksButton")));
      GuiButton var4;
      this.m.add(var4 = new GuiButton(3, this.width / 2, this.height - 52, 80, 20, I18n.format("stat.itemsButton")));
      GuiButton var5;
      this.m.add(var5 = new GuiButton(4, this.width / 2 + 80, this.height - 52, 80, 20, I18n.format("stat.mobsButton")));
      if (this.blockStats.getSize() == 0) {
         var3.enabled = false;
      }

      if (this.itemStats.getSize() == 0) {
         var4.enabled = false;
      }

      if (this.mobStats.getSize() == 0) {
         var5.enabled = false;
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.enabled) {
         if (guiButton.l == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if (guiButton.l == 1) {
            this.displaySlot = this.generalStats;
         } else if (guiButton.l == 3) {
            this.displaySlot = this.itemStats;
         } else if (guiButton.l == 2) {
            this.displaySlot = this.blockStats;
         } else if (guiButton.l == 4) {
            this.displaySlot = this.mobStats;
         } else {
            this.displaySlot.actionPerformed(guiButton);
         }
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.doesGuiPauseGame) {
         this.drawDefaultBackground();
         this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats"), this.width / 2, this.height / 2, 16777215);
         this.drawCenteredString(
            this.fontRendererObj,
            lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length)],
            this.width / 2,
            this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2,
            16777215
         );
      } else {
         this.displaySlot.drawScreen(mouseX, mouseY, partialTicks);
         this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 20, 16777215);
         super.onDrawScreen(mouseX, mouseY, partialTicks);
      }
   }

   @Override
   public void doneLoading() {
      if (this.doesGuiPauseGame) {
         this.func_175366_f();
         this.createButtons();
         this.displaySlot = this.generalStats;
         this.doesGuiPauseGame = false;
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return !this.doesGuiPauseGame;
   }

   private void drawStatsScreen(int integer1, int integer2, Item item) {
      this.drawButtonBackground(integer1 + 1, integer2 + 1);
      GlStateManager.enableRescaleNormal();
      RenderHelper.enableGUIStandardItemLighting();
      this.itemRender.Method679(new ItemStack(item, 1, 0), integer1 + 2, integer2 + 2);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
   }

   private void drawButtonBackground(int integer1, int integer2) {
      this.drawSprite(integer1, integer2, 0, 0);
   }

   private void drawSprite(int integer1, int integer2, int integer3, int integer4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(statIcons);
      Tessellator var11 = Tessellator.getInstance();
      WorldRenderer var12 = var11.getWorldRenderer();
      var12.begin(7, DefaultVertexFormats.POSITION_TEX);
      var12.pos(integer1, integer2 + 18, this.zLevel).tex(integer3 * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
      var12.pos(integer1 + 18, integer2 + 18, this.zLevel).tex((integer3 + 18) * 0.0078125F, (integer4 + 18) * 0.0078125F).endVertex();
      var12.pos(integer1 + 18, integer2, this.zLevel).tex((integer3 + 18) * 0.0078125F, integer4 * 0.0078125F).endVertex();
      var12.pos(integer1, integer2, this.zLevel).tex(integer3 * 0.0078125F, integer4 * 0.0078125F).endVertex();
      var11.draw();
   }

   static FontRenderer Method659(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method660(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method661(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method662(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method663(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static void access$700(GuiStats guiStats, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      guiStats.drawGradientRect(integer2, integer3, integer4, integer5, integer6, integer7);
   }

   static FontRenderer Method664(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method665(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static void access$1000(GuiStats guiStats, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      guiStats.drawGradientRect(integer2, integer3, integer4, integer5, integer6, integer7);
   }

   static FontRenderer Method666(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method667(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method668(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method669(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer access$1600(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer access$1700(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method670(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method671(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method672(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method673(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   static FontRenderer Method674(GuiStats guiStats) {
      return guiStats.fontRendererObj;
   }

   abstract class Stats extends GuiSlot {
      protected int field_148218_l = -1;
      protected List<StatCrafting> statsHolder;
      protected Comparator<StatCrafting> statSorter;
      protected int field_148217_o = -1;
      protected int field_148215_p;

      protected Stats(Minecraft minecraft) {
         super(minecraft, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 20);
         this.setShowSelectionBox(false);
         this.setHasListHeader(true, 20);
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
         GuiStats.this.drawDefaultBackground();
      }

      @Override
      protected void drawListHeader(int integer1, int integer2, Tessellator tessellator) {
         if (!Mouse.isButtonDown(0)) {
            this.field_148218_l = -1;
         }

         if (this.field_148218_l == 0) {
            GuiStats.this.drawSprite(integer1 + 115 - 18, integer2 + 1, 0, 0);
         } else {
            GuiStats.this.drawSprite(integer1 + 115 - 18, integer2 + 1, 0, 18);
         }

         if (this.field_148218_l == 1) {
            GuiStats.this.drawSprite(integer1 + 165 - 18, integer2 + 1, 0, 0);
         } else {
            GuiStats.this.drawSprite(integer1 + 165 - 18, integer2 + 1, 0, 18);
         }

         if (this.field_148218_l == 2) {
            GuiStats.this.drawSprite(integer1 + 215 - 18, integer2 + 1, 0, 0);
         } else {
            GuiStats.this.drawSprite(integer1 + 215 - 18, integer2 + 1, 0, 18);
         }

         if (this.field_148217_o != -1) {
            short var6 = 79;
            byte var7 = 18;
            if (this.field_148217_o == 1) {
               var6 = 129;
            } else if (this.field_148217_o == 2) {
               var6 = 179;
            }

            if (this.field_148215_p == 1) {
               var7 = 36;
            }

            GuiStats.this.drawSprite(integer1 + var6, integer2 + 1, var7, 0);
         }
      }

      @Override
      protected void func_148132_a(int integer1, int integer2) {
         this.field_148218_l = -1;
         if (integer1 >= 79 && integer1 < 115) {
            this.field_148218_l = 0;
         } else if (integer1 >= 129 && integer1 < 165) {
            this.field_148218_l = 1;
         } else if (integer1 >= 179 && integer1 < 215) {
            this.field_148218_l = 2;
         }

         if (this.field_148218_l >= 0) {
            this.func_148212_h(this.field_148218_l);
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
         }
      }

      @Override
      protected final int getSize() {
         return this.statsHolder.size();
      }

      protected final StatCrafting func_148211_c(int integer) {
         return this.statsHolder.get(integer);
      }

      protected abstract String func_148210_b(int integer);

      protected void func_148209_a(StatBase statBase, int integer2, int integer3, boolean boolean4) {
         if (statBase != null) {
            String var7 = statBase.format(GuiStats.this.C.readStat(statBase));
            GuiStats.this.drawString(
               GuiStats.Method659(GuiStats.this),
               var7,
               integer2 - GuiStats.Method660(GuiStats.this).getStringWidth(var7),
               integer3 + 5,
               boolean4 ? 16777215 : 9474192
            );
         } else {
            GuiStats.this.drawString(
               GuiStats.Method661(GuiStats.this),
               "-",
               integer2 - GuiStats.Method662(GuiStats.this).getStringWidth("-"),
               integer3 + 5,
               boolean4 ? 16777215 : 9474192
            );
         }
      }

      @Override
      protected void func_148142_b(int integer1, int integer2) {
         if (integer2 >= this.top && integer2 <= this.bottom) {
            int var5 = this.getSlotIndexFromScreenCoords(integer1, integer2);
            int var6 = this.width / 2 - 92 - 16;
            if (var5 >= 0) {
               if (integer1 < var6 + 40 || integer1 > var6 + 40 + 20) {
                  return;
               }

               StatCrafting var12 = this.func_148211_c(var5);
               this.func_148213_a(var12, integer1, integer2);
            } else {
               String var7;
               if (integer1 >= var6 + 115 - 18 && integer1 <= var6 + 115) {
                  var7 = this.func_148210_b(0);
               } else if (integer1 >= var6 + 165 - 18 && integer1 <= var6 + 165) {
                  var7 = this.func_148210_b(1);
               } else {
                  if (integer1 < var6 + 215 - 18 || integer1 > var6 + 215) {
                     return;
                  }

                  var7 = this.func_148210_b(2);
               }

               var7 = (I18n.format(var7) + "").trim();
               if (var7.length() > 0) {
                  int var8 = integer1 + 12;
                  int var9 = integer2 - 12;
                  int var10 = GuiStats.Method663(GuiStats.this).getStringWidth(var7);
                  GuiStats.access$700(GuiStats.this, var8 - 3, var9 - 3, var8 + var10 + 3, var9 + 8 + 3, -1073741824, -1073741824);
                  GuiStats.Method664(GuiStats.this).drawStringWithShadow(var7, var8, var9, -1);
               }
            }
         }
      }

      protected void func_148213_a(StatCrafting statCrafting, int integer2, int integer3) {
         if (statCrafting != null) {
            Item var6 = statCrafting.func_150959_a();
            String var9 = (I18n.format(new ItemStack(var6).getUnlocalizedName() + ".name") + "").trim();
            if (var9.length() > 0) {
               int var10 = integer2 + 12;
               int var11 = integer3 - 12;
               int var12 = GuiStats.Method665(GuiStats.this).getStringWidth(var9);
               GuiStats.access$1000(GuiStats.this, var10 - 3, var11 - 3, var10 + var12 + 3, var11 + 8 + 3, -1073741824, -1073741824);
               GuiStats.Method666(GuiStats.this).drawStringWithShadow(var9, var10, var11, -1);
            }
         }
      }

      protected void func_148212_h(int integer) {
         if (integer != this.field_148217_o) {
            this.field_148217_o = integer;
            this.field_148215_p = -1;
         } else if (this.field_148215_p == -1) {
            this.field_148215_p = 1;
         } else {
            this.field_148217_o = -1;
            this.field_148215_p = 0;
         }

         this.statsHolder.sort(this.statSorter);
      }
   }

   class StatsBlock extends GuiStats.Stats {
      public StatsBlock(Minecraft minecraft) {
         super(minecraft);
         this.statsHolder = Lists.newArrayList();

         for (StatCrafting var4 : StatList.objectMineStats) {
            boolean var5 = false;
            int var6 = Item.getIdFromItem(var4.func_150959_a());
            if (GuiStats.this.C.readStat(var4) > 0) {
               var5 = true;
            } else if (StatList.objectUseStats[var6] != null && GuiStats.this.C.readStat(StatList.objectUseStats[var6]) > 0) {
               var5 = true;
            } else if (StatList.objectCraftStats[var6] != null && GuiStats.this.C.readStat(StatList.objectCraftStats[var6]) > 0) {
               var5 = true;
            }

            if (var5) {
               this.statsHolder.add(var4);
            }
         }

         this.statSorter = (statCrafting1, statCrafting2) -> {
            int var3 = Item.getIdFromItem(statCrafting1.func_150959_a());
            int var4 = Item.getIdFromItem(statCrafting2.func_150959_a());
            StatBase var5 = null;
            StatBase var6 = null;
            if (this.field_148217_o == 2) {
               var5 = StatList.mineBlockStatArray[var3];
               var6 = StatList.mineBlockStatArray[var4];
            } else if (this.field_148217_o == 0) {
               var5 = StatList.objectCraftStats[var3];
               var6 = StatList.objectCraftStats[var4];
            } else if (this.field_148217_o == 1) {
               var5 = StatList.objectUseStats[var3];
               var6 = StatList.objectUseStats[var4];
            }

            if (var5 != null || var6 != null) {
               if (var5 == null) {
                  return 1;
               }

               if (var6 == null) {
                  return -1;
               }

               int var7 = GuiStats.this.C.readStat(var5);
               int var8 = GuiStats.this.C.readStat(var6);
               if (var7 != var8) {
                  return (var7 - var8) * this.field_148215_p;
               }
            }

            return var3 - var4;
         };
      }

      @Override
      protected void drawListHeader(int integer1, int integer2, Tessellator tessellator) {
         super.drawListHeader(integer1, integer2, tessellator);
         if (this.field_148218_l == 0) {
            GuiStats.this.drawSprite(integer1 + 115 - 18 + 1, integer2 + 1 + 1, 18, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 115 - 18, integer2 + 1, 18, 18);
         }

         if (this.field_148218_l == 1) {
            GuiStats.this.drawSprite(integer1 + 165 - 18 + 1, integer2 + 1 + 1, 36, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 165 - 18, integer2 + 1, 36, 18);
         }

         if (this.field_148218_l == 2) {
            GuiStats.this.drawSprite(integer1 + 215 - 18 + 1, integer2 + 1 + 1, 54, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 215 - 18, integer2 + 1, 54, 18);
         }
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         StatCrafting var9 = this.func_148211_c(index);
         Item var10 = var9.func_150959_a();
         GuiStats.this.drawStatsScreen(left + 40, top, var10);
         int var11 = Item.getIdFromItem(var10);
         this.func_148209_a(StatList.objectCraftStats[var11], left + 115, top, index % 2 == 0);
         this.func_148209_a(StatList.objectUseStats[var11], left + 165, top, index % 2 == 0);
         this.func_148209_a(var9, left + 215, top, index % 2 == 0);
      }

      @Override
      protected String func_148210_b(int integer) {
         return integer == 0 ? "stat.crafted" : (integer == 1 ? "stat.used" : "stat.mined");
      }
   }

   class StatsGeneral extends GuiSlot {
      public StatsGeneral(Minecraft minecraft) {
         super(minecraft, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
         this.setShowSelectionBox(false);
      }

      @Override
      protected int getSize() {
         return StatList.generalStats.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      }

      @Override
      protected boolean isSelected(int index) {
         return false;
      }

      @Override
      protected int getContentHeight() {
         return this.getSize() * 10;
      }

      @Override
      protected void drawBackground() {
         GuiStats.this.drawDefaultBackground();
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         StatBase var9 = StatList.generalStats.get(index);
         GuiStats.this.drawString(
            GuiStats.Method667(GuiStats.this), var9.getStatName().getUnformattedText(), left + 2, top + 1, index % 2 == 0 ? 16777215 : 9474192
         );
         String var10 = var9.format(GuiStats.this.C.readStat(var9));
         GuiStats.this.drawString(
            GuiStats.Method668(GuiStats.this),
            var10,
            left + 2 + 213 - GuiStats.Method669(GuiStats.this).getStringWidth(var10),
            top + 1,
            index % 2 == 0 ? 16777215 : 9474192
         );
      }
   }

   class StatsItem extends GuiStats.Stats {
      public StatsItem(Minecraft minecraft) {
         super(minecraft);
         this.statsHolder = Lists.newArrayList();

         for (StatCrafting var4 : StatList.itemStats) {
            boolean var5 = false;
            int var6 = Item.getIdFromItem(var4.func_150959_a());
            if (GuiStats.this.C.readStat(var4) > 0) {
               var5 = true;
            } else if (StatList.objectBreakStats[var6] != null && GuiStats.this.C.readStat(StatList.objectBreakStats[var6]) > 0) {
               var5 = true;
            } else if (StatList.objectCraftStats[var6] != null && GuiStats.this.C.readStat(StatList.objectCraftStats[var6]) > 0) {
               var5 = true;
            }

            if (var5) {
               this.statsHolder.add(var4);
            }
         }

         this.statSorter = (statCrafting1, statCrafting2) -> {
            int var3 = Item.getIdFromItem(statCrafting1.func_150959_a());
            int var4 = Item.getIdFromItem(statCrafting2.func_150959_a());
            StatBase var5 = null;
            StatBase var6 = null;
            if (this.field_148217_o == 0) {
               var5 = StatList.objectBreakStats[var3];
               var6 = StatList.objectBreakStats[var4];
            } else if (this.field_148217_o == 1) {
               var5 = StatList.objectCraftStats[var3];
               var6 = StatList.objectCraftStats[var4];
            } else if (this.field_148217_o == 2) {
               var5 = StatList.objectUseStats[var3];
               var6 = StatList.objectUseStats[var4];
            }

            if (var5 != null || var6 != null) {
               if (var5 == null) {
                  return 1;
               }

               if (var6 == null) {
                  return -1;
               }

               int var7 = GuiStats.this.C.readStat(var5);
               int var8 = GuiStats.this.C.readStat(var6);
               if (var7 != var8) {
                  return (var7 - var8) * this.field_148215_p;
               }
            }

            return var3 - var4;
         };
      }

      @Override
      protected void drawListHeader(int integer1, int integer2, Tessellator tessellator) {
         super.drawListHeader(integer1, integer2, tessellator);
         if (this.field_148218_l == 0) {
            GuiStats.this.drawSprite(integer1 + 115 - 18 + 1, integer2 + 1 + 1, 72, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 115 - 18, integer2 + 1, 72, 18);
         }

         if (this.field_148218_l == 1) {
            GuiStats.this.drawSprite(integer1 + 165 - 18 + 1, integer2 + 1 + 1, 18, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 165 - 18, integer2 + 1, 18, 18);
         }

         if (this.field_148218_l == 2) {
            GuiStats.this.drawSprite(integer1 + 215 - 18 + 1, integer2 + 1 + 1, 36, 18);
         } else {
            GuiStats.this.drawSprite(integer1 + 215 - 18, integer2 + 1, 36, 18);
         }
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         StatCrafting var9 = this.func_148211_c(index);
         Item var10 = var9.func_150959_a();
         GuiStats.this.drawStatsScreen(left + 40, top, var10);
         int var11 = Item.getIdFromItem(var10);
         this.func_148209_a(StatList.objectBreakStats[var11], left + 115, top, index % 2 == 0);
         this.func_148209_a(StatList.objectCraftStats[var11], left + 165, top, index % 2 == 0);
         this.func_148209_a(var9, left + 215, top, index % 2 == 0);
      }

      @Override
      protected String func_148210_b(int integer) {
         return integer == 1 ? "stat.crafted" : (integer == 2 ? "stat.used" : "stat.depleted");
      }
   }

   class StatsMobsList extends GuiSlot {
      private final List<EntityList.EntityEggInfo> field_148222_l = Lists.newArrayList();

      public StatsMobsList(Minecraft minecraft) {
         super(minecraft, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, GuiStats.access$1600(GuiStats.this).FONT_HEIGHT * 4);
         this.setShowSelectionBox(false);

         for (EntityList.EntityEggInfo var6 : EntityList.entityEggs.values()) {
            if (GuiStats.this.C.readStat(var6.field_151512_d) > 0 || GuiStats.this.C.readStat(var6.field_151513_e) > 0) {
               this.field_148222_l.add(var6);
            }
         }
      }

      @Override
      protected int getSize() {
         return this.field_148222_l.size();
      }

      @Override
      protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      }

      @Override
      protected boolean isSelected(int index) {
         return false;
      }

      @Override
      protected int getContentHeight() {
         return this.getSize() * GuiStats.access$1700(GuiStats.this).FONT_HEIGHT * 4;
      }

      @Override
      protected void drawBackground() {
         GuiStats.this.drawDefaultBackground();
      }

      @Override
      protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
         EntityList.EntityEggInfo var9 = this.field_148222_l.get(index);
         String var10 = I18n.format("entity." + EntityList.getStringFromID(var9.spawnedID) + ".name");
         int var11 = GuiStats.this.C.readStat(var9.field_151512_d);
         int var12 = GuiStats.this.C.readStat(var9.field_151513_e);
         String var13 = I18n.format("stat.entityKills", var11, var10);
         String var14 = I18n.format("stat.entityKilledBy", var10, var12);
         if (var11 == 0) {
            var13 = I18n.format("stat.entityKills.none", var10);
         }

         if (var12 == 0) {
            var14 = I18n.format("stat.entityKilledBy.none", var10);
         }

         GuiStats.this.drawString(GuiStats.Method670(GuiStats.this), var10, left + 2 - 10, top + 1, 16777215);
         GuiStats.this.drawString(
            GuiStats.Method671(GuiStats.this), var13, left + 2, top + 1 + GuiStats.Method672(GuiStats.this).FONT_HEIGHT, var11 == 0 ? 6316128 : 9474192
         );
         GuiStats.this.drawString(
            GuiStats.Method673(GuiStats.this), var14, left + 2, top + 1 + GuiStats.Method674(GuiStats.this).FONT_HEIGHT * 2, var12 == 0 ? 6316128 : 9474192
         );
      }
   }
}
