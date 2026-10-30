package com.moon.ui.clickgui.imgui;

import com.moon.Moon;
import com.moon.module.ModuleCategory;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;

public class ImGuiClickGUIScreen extends GuiScreen {
   private static boolean contextCreated;
   private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
   private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
   private ModuleCategory currentCategory = ModuleCategory.COMBAT;
   private final Map<ModuleCategory, List<ImGuiWidget>> moduleWidgets = new HashMap<>();

   public void setup() {
      if (!contextCreated) {
         this.createImGuiContext();
         contextCreated = true;
      }

      ImGuiStyle style = ImGui.getStyle();
      style.setWindowMinSize(550.0F, 600.0F);
      style.setFrameRounding(2.0F);
      style.setWindowRounding(8.0F);
      style.setWindowBorderSize(0.0F);
      style.setPopupBorderSize(0.0F);
      style.setAntiAliasedFill(true);
      style.setAntiAliasedLines(true);
      this.moduleWidgets.clear();

      for (ModuleCategory category : ModuleCategory.values()) {
         Moon.getInstance().getModuleManager().getModulesByCategory(category).forEach(module -> {
            ImGuiModuleWidget moduleWidget = new ImGuiModuleWidget(module);
            moduleWidget.setup();
            this.moduleWidgets.computeIfAbsent(category, cat -> new ArrayList<>()).add(moduleWidget);
         });
      }
   }

   private byte[] loadResource(String resourcePath) {
      try {
         return Files.readAllBytes(Paths.get(this.getClass().getResource(resourcePath).toURI()));
      } catch (IOException | URISyntaxException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      GlStateManager.enableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      this.imGuiGlfw.newFrame();
      ImGui.newFrame();
      ImGui.begin("Moon");
      ImGui.beginTabBar("categories");

      for (ModuleCategory category : ModuleCategory.values()) {
         ImGui.pushID(category.name());
         if (ImGui.beginTabItem(category.getName(), 32)) {
            if (ImGui.isItemActive()) {
               this.currentCategory = category;
            }

            ImGui.endTabItem();
         }

         ImGui.popID();
      }

      ImGui.endTabBar();
      List<ImGuiWidget> widgets = this.moduleWidgets.get(this.currentCategory);
      if (widgets != null) {
         for (ImGuiWidget widget : widgets) {
            widget.render();
         }
      }

      ImGui.end();
      ImGui.render();
      this.imGuiGl3.renderDrawData(ImGui.getDrawData());
      GlStateManager.enableAlpha();
   }

   private void createImGuiContext() {
      ImGui.createContext();
      ImGuiIO io = ImGui.getIO();
      io.getFonts().addFontFromMemoryTTF(this.loadResource("/Tahoma.ttf"), 15.0F);
      io.getFonts().build();
      this.imGuiGlfw.init(Display.getWindow(), true);
      this.imGuiGl3.init("#version 130");
   }
}
