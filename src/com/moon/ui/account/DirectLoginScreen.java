package com.moon.ui.account;

import com.moon.Moon;
import com.moon.account.AccountStorageFactory;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.classic.component.Button;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.microsoft.MicrosoftAuthUtil;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Session;
import org.lwjgl.opengl.Display;

public class DirectLoginScreen extends GuiScreen {
   private ThemedTextField usernameField;
   private Button loginCrackedButton;
   private Button loginMicrosoftButton;
   private Button loginCookiesButton;
   private Button backButton;
   private boolean nameTaken = false;

   @Override
   public void onInitGui() {
      float startY = this.height / 2.0F - 105.0F;
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      this.usernameField = new ThemedTextField(1, this.mc.fontRendererObj, this.width / 2.0F - 80.0F, this.height / 2.0F - 82.0F, 160, 20, themeColor);
      this.loginCookiesButton = new Button(this.width / 2.0F - 60.0F, startY + 165.0F - 27.0F - 66.0F, 120.0F, 20.0F, "Login with Cookies", false, themeColor);
      this.loginCrackedButton = new Button(this.width / 2.0F - 60.0F, startY + 165.0F - 27.0F - 44.0F, 120.0F, 20.0F, "Login Cracked", false, themeColor);
      this.loginMicrosoftButton = new Button(this.width / 2.0F - 60.0F, startY + 165.0F - 27.0F - 22.0F, 120.0F, 20.0F, "Login with Microsoft", false, themeColor);
      this.backButton = new Button(this.width / 2.0F - 60.0F, startY + 165.0F - 27.0F, 120.0F, 20.0F, "Back", false, themeColor);
      this.nameTaken = false;
      Moon.getInstance().getAccountScreen().getBlurRenderer().setRenderCallback(value -> {
         float x = this.width / 2.0F - 85.0F;
         float y = this.height / 2.0F - 105.0F;
         RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         task.addCommand(RoundedRectangleCommand.of(x - 1.0F, y - 1.0F, 172.0F, 165.0F, 6.0F, themeColor.getShadow()));
         task.render();
      });
      super.onInitGui();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      float boxX = this.width / 2.0F - 85.0F;
      float boxY = this.height / 2.0F - 105.0F;
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.enableAlpha();
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      rectTask.addCommand(RoundedRectangleCommand.of(-1.0F, -1.0F, this.mc.displayWidth + 1, this.mc.displayHeight + 1, 0.0F, themeColor.getBackground()));
      rectTask.addCommand(RoundedRectangleCommand.of(boxX - 1.0F, boxY - 1.0F, 172.0F, 165.0F, 6.0F, themeColor.getSurfaceVariant()));
      rectTask.render();
      this.usernameField.drawTextBox();
      String title = "§lDirect Login";
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(textTask, title, 12.0F, boxX + 6.0F, boxY + 4.0F, themeColor.getPrimary(), true);
      if (this.usernameField.getText().isEmpty() && !this.usernameField.isFocused()) {
         fontRenderer.draw(
            textTask,
            "Username",
            9.0F,
            this.usernameField.x + 4.0F,
            this.usernameField.y + this.usernameField.height / 2.0F - fontRenderer.getLineHeight(9.0F) / 2.0F + 0.5F,
            themeColor.getSecondary()
         );
      }

      if (this.nameTaken) {
         String errorText = "§cThis name is taken / invalid!";
         fontRenderer.drawWithShadow(
            textTask,
            errorText,
            8.0F,
            this.width / 2.0F - fontRenderer.getTextWidth(errorText, 8.0F) / 2.0F,
            this.usernameField.y + this.usernameField.height + 4.0F,
            themeColor.getSecondary()
         );
      }

      textTask.render();
      this.loginCookiesButton.render(mouseX, mouseY);
      this.loginCrackedButton.render(mouseX, mouseY);
      this.loginMicrosoftButton.render(mouseX, mouseY);
      this.backButton.render(mouseX, mouseY);
      if (mouseX > this.loginMicrosoftButton.getX()
         && mouseY > this.loginMicrosoftButton.getY()
         && mouseX < this.loginMicrosoftButton.getX() + this.loginMicrosoftButton.getWidth()
         && mouseY < this.loginMicrosoftButton.getY() + this.loginMicrosoftButton.getHeight()) {
         this.drawHoveringText(
            List.of(
               "§dInformation about Microsoft Login",
               "",
               "§bIf you are using an account without a username set",
               "§bmake sure to enter a username in the text-box.",
               "",
               "§aMoon will set it automatically for you!"
            ),
            mouseX,
            mouseY
         );
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(1, 0);
      GlStateManager.disableDepth();
      AccountManagerScreen accountScreen = Moon.getInstance().getAccountScreen();
      accountScreen.getBlurRenderer().requestRender();
      accountScreen.getBlurRenderer().render();
      GlStateManager.enableDepth();
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      this.usernameField.textboxKeyTyped(charTyped, keyCode);
      super.onKeyTyped(charTyped, keyCode);
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      this.usernameField.mouseClicked(mouseX, mouseY, button);
      if (this.loginCookiesButton.isClicked(mouseX, mouseY, button)) {
         if (Display.isFullscreen()) {
            this.mc.toggleFullscreen();
         }

         FileDialog fileDialog = new FileDialog((Frame)null);
         fileDialog.addComponentListener(new CookieLoginListener(this, fileDialog));
         fileDialog.setVisible(true);
      }

      if (this.loginCrackedButton.isClicked(mouseX, mouseY, button)) {
         if (this.usernameField.getText().isEmpty()) {
            return;
         }

         AccountStorageFactory.createCracked(this.usernameField.getText()).login();
         Moon.getInstance().setCurrentAccount(null);
         this.mc.displayGuiScreen(Moon.getInstance().getAccountScreen());
      }

      if (this.loginMicrosoftButton.isClicked(mouseX, mouseY, button)) {
         Moon.getInstance()
            .getExecutor()
            .submit(
               () -> {
                  this.nameTaken = false;
                  String urlFormat = "https://api.mojang.com/users/profiles/minecraft/%s";
                  if (!this.usernameField.getText().isEmpty()) {
                     try {
                        String url = String.format(urlFormat, this.usernameField.getText());
                        HttpRequest request = HttpRequest.newBuilder()
                           .uri(URI.create(url))
                           .headers("Content-Type", "application/json", "Accept", "application/json")
                           .timeout(Duration.ofSeconds(15L))
                           .build();
                        int statusCode = MicrosoftAuthUtil.HTTP_CLIENT.send(request, BodyHandlers.ofString()).statusCode();
                        if (statusCode == 400 || statusCode == 200) {
                           this.nameTaken = true;
                           return;
                        }
                     } catch (Exception e) {
                        this.nameTaken = true;
                        return;
                     }
                  }

                  AddAccountScreen.AUTH_SERVER
                     .openLoginPage(
                        !this.usernameField.getText().isEmpty(),
                        this.usernameField.getText(),
                        authData -> {
                           if (authData != null) {
                              Session session = this.mc.getSession();
                              Moon.getInstance()
                                 .getNotificationManager()
                                 .pop(
                                    Notification.builder()
                                       .title("Account Manager")
                                       .message("Successfully logged into account with name " + session.getUsername())
                                       .type(NotificationType.SUCCESS)
                                       .duration(3000L)
                                       .build()
                                 );
                              Moon.getInstance().setCurrentAccount(null);
                              this.mc.displayGuiScreen(Moon.getInstance().getAccountScreen());
                           } else {
                              Moon.getInstance()
                                 .getNotificationManager()
                                 .pop(
                                    Notification.builder()
                                       .title("Account Manager")
                                       .message("Failed to log into account (invalid credentials)")
                                       .type(NotificationType.ERROR)
                                       .duration(3000L)
                                       .build()
                                 );
                           }
                        }
                     );
               }
            );
      }

      if (this.backButton.isClicked(mouseX, mouseY, button)) {
         this.mc.displayGuiScreen(Moon.getInstance().getAccountScreen());
      }

      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void onUpdateScreen() {
      this.usernameField.updateCursorCounter();
      super.onUpdateScreen();
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      super.onMouseReleased(mouseX, mouseY, button);
   }

   static Minecraft getMinecraft(DirectLoginScreen screen) {
      return screen.mc;
   }

   static Minecraft getMinecraftInstance(DirectLoginScreen screen) {
      return screen.mc;
   }
}
