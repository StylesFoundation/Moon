package com.moon.ui.login;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.account.ThemedTextField;
import com.moon.ui.clickgui.classic.component.Button;
import com.moon.ui.theme.ColorScheme;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class LoginScreen extends GuiScreen {
   private final ResourceLocation logo = new ResourceLocation("logo.png");
   private final ColorScheme theme = ColorScheme.darkScheme(-9410305);
   private Button loginButton;
   private ThemedTextField uidField;
   private final boolean hasUidProperty = System.getProperty("uid") != null;
   private boolean loggingIn;

   @Override
   public void onInitGui() {
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      float panelX = sr.Method237() / 2.0F - 112.5F;
      float panelY = sr.Method238() / 2.0F - 54.0F;
      this.uidField = new ThemedTextField(0, Minecraft.getMinecraft().fontRendererObj, panelX + 7.0F, panelY + 50.0F, 211, 20, this.theme);
      this.loginButton = new Button(panelX + 112.5F - 50.0F, panelY + 78.25F, 100.0F, 22.0F, "Login", false, this.theme);
      if (this.hasUidProperty) {
         String uid = System.getProperty("uid");
         this.uidField.setText(uid);
         if (!this.loggingIn) {
            this.attemptLogin();
         } else {
            this.loginButton.setDisabled(true);
         }
      }

      super.onInitGui();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.enableAlpha();
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      bgTask.addCommand(RoundedRectangleCommand.of(-2.0F, -2.0F, this.width + 4, this.height + 4, 0.0F, this.theme.getBackground()));
      this.drawPanel(bgTask);
      bgTask.render();
      this.drawText();
      this.loginButton.render(mouseX, mouseY);
      this.uidField.drawTextBox();
      this.drawLogo();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   private void drawPanel(RenderTask renderTask) {
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      float panelX = sr.Method237() / 2.0F - 112.5F;
      float panelY = sr.Method238() / 2.0F - 54.0F;
      renderTask.addCommand(RoundedRectangleCommand.of(panelX, panelY, 225.0F, 108.0F, 7.0F, this.theme.getSurfaceVariant()));
   }

   private void drawText() {
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      float panelX = sr.Method237() / 2.0F - 112.5F;
      float panelY = sr.Method238() / 2.0F - 54.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.SOURCE_SANS);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(renderTask, "§lWelcome", 18.0F, panelX + 8.0F, panelY + 4.0F, this.theme.getPrimary(), true);
      fontRenderer.draw(renderTask, "§lPlease login to the client with your uid!", 8.0F, panelX + 8.0F, panelY + 23.0F, this.theme.getSecondary(), true);
      fontRenderer.draw(renderTask, "§lUID", 7.0F, panelX + 7.5F, this.uidField.y - 10.5F, this.theme.getSecondary(), true);
      renderTask.render();
   }

   public void drawLogo() {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.SOURCE_SANS);
      ScaledResolution sr = ScaleHandler.getScaledResolution();
      float panelY = sr.Method238() / 2.0F - 54.0F;
      float totalWidth = 64.0F + fontRenderer.getTextWidth(" §lMoon Client", 32.0F) + 12.5F;
      float startX = sr.Method237() / 2.0F - totalWidth / 2.0F;
      float startY = Math.max(panelY - 124.0F, 0.0F);
      RenderTask textureTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_COL).build();
      textureTask.addCommand(TextureCommand.of(startX, startY, startX + 64.0F, startY + 64.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1728053247));
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.logo);
      textureTask.render();
      GlStateManager.bindTexture(0);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(
         textTask,
         " §lMoon Client",
         32.0F,
         startX + 64.0F,
         startY + 32.0F - fontRenderer.getLineHeight(32.0F) / 2.0F,
         new RenderColor(this.theme.getPrimary()).withAlpha(102).getRGB(),
         false
      );
      textTask.render();
   }

   @Override
   public void onUpdateScreen() {
      this.uidField.updateCursorCounter();
      super.onUpdateScreen();
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      this.uidField.mouseClicked(mouseX, mouseY, button);
      if (this.loginButton.isClicked(mouseX, mouseY, button)) {
         this.attemptLogin();
      }

      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode != 1) {
         this.uidField.textboxKeyTyped(charTyped, keyCode);
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
   }

   private void attemptLogin() {
      if (!this.loggingIn) {
         this.loginButton.setDisabled(true);
         this.loginButton.setText("Logging in...");
         this.loggingIn = true;
         new Thread(() -> {
            try {
               Moon.getInstance().login(this.uidField.getText());
            } catch (Exception e) {
               Moon.getInstance().getLogger().error("Failed to login");
               e.printStackTrace();
               this.loginButton.setDisabled(false);
               this.loginButton.setText("Login");
               this.loggingIn = false;
            }
         }, "Moom Authentication Thread").start();
      }
   }
}
