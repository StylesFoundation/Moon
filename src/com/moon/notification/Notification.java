package com.moon.notification;

import com.moon.Moon;
import com.moon.handlers.NotificationHandler;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.msdf.MsdfGlyphRenderer;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;

public class Notification {
   private final String title;
   private final String message;
   private final NotificationType type;
   private final long duration;
   private ClickGUI moduleClickGui;
   private float x;
   private float y;
   private float width;
   private float height;
   private float screenOffset;
   private long expirationTime;
   private boolean finished;
   private boolean expired;
   private final Animation animation = new Animation(250L, Easing.LINEAR);

   public void initialize() {
      this.calculateHeight();
      this.calculateWidth();
      this.screenOffset = this.width + 2.0F;
      this.expirationTime = System.currentTimeMillis() + this.duration;
      this.moduleClickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
   }

   public void render(RenderTask backgroundTask, RenderTask iconTask, RenderTask textTask, RenderTask overlayTask, float offset) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      ColorScheme accentColor = this.moduleClickGui.getAccentColor();
      this.y = scaledResolution.Method238() - this.height - 4.0F + offset;
      float animationValue = this.animation.getValue();
      this.x = scaledResolution.Method237() - (this.screenOffset + 2.0F) * animationValue;
      backgroundTask.addCommand(
         RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 6.0F, new RenderColor(Integer.MIN_VALUE).withAlphaFraction(0.5F * animationValue).getRGB())
      );
      int primaryColor = new RenderColor(accentColor.getPrimary()).withAlphaFraction(animationValue).getRGB();
      int onPrimaryColor = new RenderColor(accentColor.getOnPrimary()).withAlphaFraction(animationValue).getRGB();
      int textColor = new RenderColor(Color.WHITE.getRGB()).withAlphaFraction(animationValue).getRGB();
      float iconBoxSize = Math.round(this.height - 12.0F) + 0.5F;
      backgroundTask.addCommand(RoundedRectangleCommand.of(this.x + 6.0F + 0.5F, this.y + 6.0F, iconBoxSize, iconBoxSize, 6.0F, primaryColor));
      MsdfGlyphRenderer glyphRenderer = NotificationHandler.iconFont.getFont().getProvider().getGlyph(this.type.icon().charAt(0));
      float iconAdvance = glyphRenderer.getAdvance() * 16.0F;
      float iconPlaneHeight = glyphRenderer.getPlaneHeight() * 16.0F;
      NotificationHandler.iconFont
         .draw(
            iconTask,
            this.type.icon(),
            16.0F,
            this.x + 0.5F + 6.0F + iconBoxSize / 2.0F - iconAdvance / 2.0F,
            this.y + 6.0F + 1.5F + iconBoxSize / 2.0F - 2.0F - iconPlaneHeight / 4.0F,
            onPrimaryColor
         );
      NotificationHandler.textFont.draw(textTask, this.title, 12.0F, this.x + 6.0F + iconBoxSize + 6.0F, this.y + 6.0F - 0.5F, textColor);
      NotificationHandler.textFont
         .draw(textTask, this.message, 8.0F, this.x + 6.0F + iconBoxSize + 6.0F, this.y + 6.0F - 0.5F + NotificationHandler.textFont.getLineHeight(12.0F), textColor);
      overlayTask.addCommand(
         RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 6.0F, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(animationValue).getRGB())
      );
      RoundedRectangleCommand overlayCommand = RoundedRectangleCommand.of(this.x + 6.0F + 0.5F, this.y + 6.0F, iconBoxSize, iconBoxSize, 0.0F, primaryColor);
      overlayCommand.setPixelAligned(false);
      overlayTask.addCommand(overlayCommand);
      this.animation.animate(0.0F, 1.0F, !this.finished);
      if (this.finished && animationValue == 0.0F) {
         this.expired = true;
      }
   }

   private void calculateWidth() {
      float iconBoxSize = Math.round(this.height - 12.0F) + 0.5F;
      float titleWidth = NotificationHandler.textFont.getTextWidth(this.title, 12.0F);
      float messageWidth = NotificationHandler.textFont.getTextWidth(this.message, 8.0F);
      this.width = Math.max(titleWidth, messageWidth) + 18.0F + iconBoxSize;
   }

   private void calculateHeight() {
      float titleHeight = NotificationHandler.textFont.getLineHeight(12.0F);
      float messageHeight = NotificationHandler.textFont.getLineHeight(8.0F);
      this.height = Math.round(titleHeight + messageHeight) + 12.0F;
   }

   public float getAnimatedHeight() {
      return this.height * this.animation.getValue();
   }

   Notification(
      String title,
      String message,
      NotificationType type,
      long duration,
      ClickGUI moduleClickGui,
      float x,
      float y,
      float width,
      float height,
      float screenOffset,
      long expirationTime,
      boolean expired,
      boolean finished
   ) {
      this.title = title;
      this.message = message;
      this.type = type;
      this.duration = duration;
      this.moduleClickGui = moduleClickGui;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.screenOffset = screenOffset;
      this.expirationTime = expirationTime;
      this.finished = expired;
      this.expired = finished;
   }

   public static Notification.Builder builder() {
      return new Notification.Builder();
   }

   public String getTitle() {
      return this.title;
   }

   public String getMessage() {
      return this.message;
   }

   public NotificationType getType() {
      return this.type;
   }

   public long getDuration() {
      return this.duration;
   }

   public ClickGUI getModuleClickGui() {
      return this.moduleClickGui;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public float getScreenOffset() {
      return this.screenOffset;
   }

   public long getExpirationTime() {
      return this.expirationTime;
   }

   public boolean isFinished() {
      return this.finished;
   }

   public boolean isExpired() {
      return this.expired;
   }

   public Animation getAnimation() {
      return this.animation;
   }

   public Notification setFinished(boolean finished) {
      this.finished = finished;
      return this;
   }

   public Notification setExpired(boolean expired) {
      this.expired = expired;
      return this;
   }

   public static class Builder {
      private String title;
      private String message;
      private NotificationType type;
      private long duration;
      private ClickGUI moduleClickGui;
      private float x;
      private float y;
      private float width;
      private float height;
      private float screenOffset;
      private long expirationTime;
      private boolean expired;
      private boolean finished;

      Builder() {
      }

      public Notification.Builder title(String title) {
         this.title = title;
         return this;
      }

      public Notification.Builder message(String message) {
         this.message = message;
         return this;
      }

      public Notification.Builder type(NotificationType type) {
         this.type = type;
         return this;
      }

      public Notification.Builder duration(long duration) {
         this.duration = duration;
         return this;
      }

      public Notification.Builder moduleClickGui(ClickGUI moduleClickGui) {
         this.moduleClickGui = moduleClickGui;
         return this;
      }

      public Notification.Builder Method387(float x) {
         this.x = x;
         return this;
      }

      public Notification.Builder Method388(float y) {
         this.y = y;
         return this;
      }

      public Notification.Builder width(float width) {
         this.width = width;
         return this;
      }

      public Notification.Builder height(float height) {
         this.height = height;
         return this;
      }

      public Notification.Builder screenOffset(float screenOffset) {
         this.screenOffset = screenOffset;
         return this;
      }

      public Notification.Builder expirationTime(long expirationTime) {
         this.expirationTime = expirationTime;
         return this;
      }

      public Notification.Builder expired(boolean expired) {
         this.expired = expired;
         return this;
      }

      public Notification.Builder finished(boolean finished) {
         this.finished = finished;
         return this;
      }

      public Notification build() {
         return new Notification(
            this.title,
            this.message,
            this.type,
            this.duration,
            this.moduleClickGui,
            this.x,
            this.y,
            this.width,
            this.height,
            this.screenOffset,
            this.expirationTime,
            this.expired,
            this.finished
         );
      }

      @Override
      public String toString() {
         boolean isFinished = this.finished;
         boolean isExpired = this.expired;
         long expirationTimeVal = this.expirationTime;
         float screenOffsetVal = this.screenOffset;
         float heightVal = this.height;
         float widthVal = this.width;
         float yVal = this.y;
         float xVal = this.x;
         ClickGUI clickGui = this.moduleClickGui;
         long durationVal = this.duration;
         NotificationType notifType = this.type;
         String messageVal = this.message;
         String titleVal = this.title;
         return "Notification.NotificationBuilder(title="
            + titleVal
            + ", message="
            + messageVal
            + ", type="
            + notifType
            + ", duration="
            + durationVal
            + ", moduleClickGui="
            + clickGui
            + ", x="
            + xVal
            + ", y="
            + yVal
            + ", width="
            + widthVal
            + ", height="
            + heightVal
            + ", screenOffset="
            + screenOffsetVal
            + ", expirationTime="
            + expirationTimeVal
            + ", expired="
            + isExpired
            + ", finished="
            + isFinished
            + ")";
      }
   }
}
