package com.moon.module.impl.visual.hud.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moon.Moon;
import com.moon.module.impl.visual.HUD;
import com.moon.render.RenderTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import net.minecraft.client.Minecraft;

public abstract class DraggableHudElement {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final String name;
   protected final HUD hud;
   private float x;
   private float y;
   private float width;
   private float height;
   private float contentX;
   private float contentY;

   public void savePosition(File directory) {
      File file = new File(directory, this.name + ".json");
      String json = GSON.toJson(new HudElementPosition(this.x, this.y));

      try {
         Files.writeString(file.toPath(), json, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
         Moon.getInstance().getLogger().error("Failed to save component data of " + this.name, new Object[]{e});
      }
   }

   public void loadPosition(File directory) {
      File file = new File(directory, this.name + ".json");
      if (!file.exists()) {
         Moon.getInstance().getLogger().info("Failed to load component data of " + this.name + " as the component does not exist!");
      } else {
         try {
            HudElementPosition position = (HudElementPosition)GSON.fromJson(new FileReader(file), HudElementPosition.class);
            this.x = position.getX();
            this.y = position.getY();
         } catch (FileNotFoundException e) {
            Moon.getInstance().getLogger().error("Failed to load component data of " + this.name, new Object[]{e});
         }
      }
   }

   public abstract void setRectangleTask(RenderTask task);

   public abstract void setRoundedTask(RenderTask task);

   public abstract void setBackgroundTask(RenderTask task);

   public abstract void setTextTask(RenderTask task);

   public abstract void renderPost();

   public abstract DraggableComponentProperties getTitle();

   public abstract boolean isVisible();

   public String getName() {
      return this.name;
   }

   public HUD getHud() {
      return this.hud;
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

   public float getContentX() {
      return this.contentX;
   }

   public float getContentY() {
      return this.contentY;
   }

   public DraggableHudElement setX(float value) {
      this.x = value;
      return this;
   }

   public DraggableHudElement setY(float value) {
      this.y = value;
      return this;
   }

   public DraggableHudElement setWidth(float value) {
      this.width = value;
      return this;
   }

   public DraggableHudElement setHeight(float value) {
      this.height = value;
      return this;
   }

   public DraggableHudElement setContentX(float value) {
      this.contentX = value;
      return this;
   }

   public DraggableHudElement setContentY(float value) {
      this.contentY = value;
      return this;
   }

   public DraggableHudElement(String name, HUD hud) {
      this.name = name;
      this.hud = hud;
   }
}
