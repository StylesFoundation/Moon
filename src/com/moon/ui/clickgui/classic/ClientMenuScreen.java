package com.moon.ui.clickgui.classic;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.classic.menu.ConfigsTab;
import com.moon.ui.clickgui.classic.menu.ScriptsTab;
import com.moon.ui.clickgui.classic.menu.TabBar;
import com.moon.ui.clickgui.classic.menu.TabbedContainer;
import com.moon.ui.theme.ColorScheme;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClientMenuScreen extends GuiScreen {
   private final TabBar tabBar = new TabBar(new String[]{"Configs", "Scripts"}, "Configs");
   private TabbedContainer tabbedContainer;
   private ClickGUI clickGui;

   @Override
   public void onInitGui() {
      this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      int width = sr.Method237();
      int height = sr.Method238();
      float x = width / 2.0F - 212.5F;
      float y = height / 2.0F - 162.5F;
      this.tabbedContainer = new TabbedContainer(x, y + 35.0F, 425.0F, 290.0F, "Configs", new ConfigsTab("Configs", 0), new ScriptsTab("Scripts", 1));
      super.onInitGui();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      ColorScheme scheme = this.clickGui.getAccentColor();
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      FontRenderer font = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      GlStateManager.enableBlend();
      int width = sr.Method237();
      int height = sr.Method238();
      float x = width / 2.0F - 212.5F;
      float y = height / 2.0F - 162.5F;
      String commitHash = "256ae016d1ab5f21124f38e269e06bb26cfdc63d".substring(0, 7);
      this.tabBar.setX(x + 212.5F);
      this.tabBar.setY(y + 17.5F);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask fontTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(font).build();
      RenderTask gradientTask = RenderTask.builder().withRenderType(RenderType.GRADIENT).build();
      rectTask.addCommand(RoundedRectangleCommand.of(x, y, 425.0F, 325.0F, 12.0F, scheme.getBackground()));
      rectTask.addCommand(
         RoundedRectangleCommand.of(x, y, 425.0F, 325.0F, 12.0F, new RenderColor(scheme.getSecondaryContainer()).withAlphaFraction(0.5F).getRGB())
      );
      RoundedRectangleCommand bodyRect = RoundedRectangleCommand.of(x, y + 35.0F, 425.0F, 290.0F, 12.0F, scheme.getBackground());
      bodyRect.setPixelAligned(false);
      rectTask.addCommand(bodyRect);
      font.draw(fontTask, "Client Menu", 14.0F, x + 10.0F, y + 17.5F - font.getLineHeight(14.0F) / 2.0F + 1.5F, scheme.getPrimary());
      font.draw(
         fontTask,
         commitHash,
         11.0F,
         x + 425.0F - font.getTextWidth(commitHash, 11.0F) - 10.0F,
         y + 17.5F - font.getLineHeight(12.0F) / 2.0F + 1.5F,
         scheme.getSecondary()
      );
      rectTask.render();
      fontTask.render();
      gradientTask.render();
      this.tabBar.render(scheme, mouseX, mouseY);
      this.tabbedContainer.setSelectedTab(this.tabBar.getSelectedTab());
      this.tabbedContainer.render(scheme, mouseX, mouseY);
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      this.tabBar.onMouseClick(mouseX, mouseY, button);
      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      super.onKeyTyped(charTyped, keyCode);
   }
}
