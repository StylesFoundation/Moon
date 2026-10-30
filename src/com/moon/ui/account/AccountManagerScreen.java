package com.moon.ui.account;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.account.AccountManager;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.shader.blur.impl.GaussianBlurRenderer;
import com.moon.ui.clickgui.classic.component.Button;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.math.MathUtil;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

public class AccountManagerScreen extends GuiScreen {
   private final Framebuffer mainFramebuffer = new Framebuffer(1, 1, false);
   private final Framebuffer[] blurFramebuffers = new Framebuffer[6];
   private final GaussianBlurRenderer blurRenderer;
   private AccountListWidget accountList;
   private Button loginButton;
   private Button removeButton;
   private Button addButton;
   private Button directLoginButton;
   private Button randomAltButton;
   private Button clearListButton;

   public AccountManagerScreen() {
      for (int i = 0; i < this.blurFramebuffers.length; i++) {
         this.blurFramebuffers[i] = new Framebuffer(1, 1, false);
      }

      this.blurRenderer = new GaussianBlurRenderer(this.mainFramebuffer, this.blurFramebuffers, 2, 2);
   }

   public void renderAccountList() {
      if (this.mc.currentScreen == this && this.accountList != null) {
         this.accountList.drawEntries();
      }
   }

   @Override
   public void onInitGui() {
      this.blurRenderer.setRenderCallback(value -> this.renderAccountList());
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      this.accountList = new AccountListWidget(this);
      this.accountList.registerScrollButtons(7, 8);
      this.accountList.elementClicked(-1, false, 0, 0);
      this.loginButton = new Button(this.width / 2.0F + 52.0F, this.height - 51.0F, 100.0F, 22.0F, "Login", false, themeColor);
      this.removeButton = new Button(this.width / 2.0F - 152.0F, this.height - 27.0F, 100.0F, 22.0F, "Remove", false, themeColor);
      this.addButton = new Button(this.width / 2.0F - 152.0F, this.height - 51.0F, 100.0F, 22.0F, "Add", false, themeColor);
      this.directLoginButton = new Button(this.width / 2.0F - 50.0F, this.height - 51.0F, 100.0F, 22.0F, "Direct Login", false, themeColor);
      this.randomAltButton = new Button(this.width / 2.0F + 52.0F, this.height - 27.0F, 100.0F, 22.0F, "Random Alt", false, themeColor);
      this.clearListButton = new Button(this.width / 2.0F - 50.0F, this.height - 27.0F, 100.0F, 22.0F, "Clear List", false, themeColor);
      super.onInitGui();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      new ScaledResolution(Minecraft.getMinecraft());
      ColorScheme themeColor = Moon.getInstance().getAccountManager().getThemeColor();
      RenderTask backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      backgroundTask.addCommand(RoundedRectangleCommand.of(-1.0F, -1.0F, this.mc.displayWidth + 1, this.mc.displayHeight + 1, 0.0F, themeColor.getBackground()));
      backgroundTask.render();
      this.accountList.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.enableBlend();
      String loggedInText = "Logged in to: §7" + this.mc.Method1016().getUsername();
      String title = "Account Manager";
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.drawWithShadow(textTask, title, 12.0F, this.width / 2.0F - fontRenderer.getTextWidth(title, 12.0F) / 2.0F, 7.0F, themeColor.getPrimary());
      fontRenderer.drawWithShadow(textTask, loggedInText, 8.0F, this.width / 2.0F - fontRenderer.getTextWidth(loggedInText, 8.0F) / 2.0F, 20.0F, themeColor.getPrimary());
      textTask.render();
      this.loginButton.render(mouseX, mouseY);
      this.removeButton.render(mouseX, mouseY);
      this.addButton.render(mouseX, mouseY);
      this.directLoginButton.render(mouseX, mouseY);
      this.randomAltButton.render(mouseX, mouseY);
      this.clearListButton.render(mouseX, mouseY);
      GlStateManager.disableBlend();
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(1, 0);
      GlStateManager.disableDepth();
      this.blurRenderer.setOffset(6.0F);
      this.blurRenderer.requestRender();
      this.blurRenderer.render();
      GlStateManager.enableDepth();
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      AccountManager accountManager = Moon.getInstance().getAccountManager();
      if (this.loginButton.isClicked(mouseX, mouseY, button)) {
         if (this.accountList.selectedIndex == -1) {
            return;
         }

         Account selectedAccount = accountManager.getAccounts().get(this.accountList.selectedIndex);
         selectedAccount.login();
         Moon.getInstance().setCurrentAccount(selectedAccount);
      }

      if (this.removeButton.isClicked(mouseX, mouseY, button)) {
         if (accountManager.getAccounts().isEmpty()) {
            return;
         }

         if (Moon.getInstance().getCurrentAccount() == accountManager.getAccounts().get(this.accountList.selectedIndex)) {
            Moon.getInstance().setCurrentAccount(null);
         }

         accountManager.getAccounts().remove(this.accountList.selectedIndex);
      }

      if (this.addButton.isClicked(mouseX, mouseY, button)) {
         this.mc.displayGuiScreen(new AddAccountScreen());
      }

      if (this.directLoginButton.isClicked(mouseX, mouseY, button)) {
         this.mc.displayGuiScreen(new DirectLoginScreen());
      }

      if (this.randomAltButton.isClicked(mouseX, mouseY, button) && !accountManager.getAccounts().isEmpty()) {
         List accounts = Moon.getInstance().getAccountManager().getAccounts();
         Account randomAccount = (Account)accounts.get(MathUtil.randomInt(0, accounts.size() - 1));
         randomAccount.login();
         Moon.getInstance().setCurrentAccount(randomAccount);
      }

      if (this.clearListButton.isClicked(mouseX, mouseY, button)) {
         accountManager.getAccounts().clear();
      }

      super.onMouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.accountList.handleMouseInput();
   }

   public GaussianBlurRenderer getBlurRenderer() {
      return this.blurRenderer;
   }
}
