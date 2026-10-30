package com.moon.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moon.Moon;
import com.moon.events.EventBus;
import com.moon.events.impl.module.ModuleHideEvent;
import com.moon.events.impl.module.ModuleSuffixChangedEvent;
import com.moon.events.impl.module.ModuleToggleEvent;
import com.moon.module.bind.KeyBind;
import com.moon.module.bind.element.BindAction;
import com.moon.module.bind.element.BindType;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingContainer;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModuleRegistry;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import net.minecraft.client.Minecraft;

public class Module extends SettingContainer implements KeyBind {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   private final String name;
   private final String displayName;
   private final String description;
   private String suffix = "";
   private final ModuleCategory category;
   private final Animation animation = new Animation(300L, Easing.LINEAR);
   private boolean state;
   private boolean hidden;
   private int key = Integer.MIN_VALUE;
   private BindType bindType = BindType.KEYBOARD;
   private BindAction bindAction = BindAction.PRESS;
   private float p;
   private float q;

   public Module(String name, String displayName, String description, ModuleCategory category) {
      this.name = name;
      this.displayName = displayName;
      this.description = description;
      this.category = category;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void setEnabled(boolean state) {
      if (this.state != state) {
         EventBus eventBus = Moon.getInstance().getEventBus();
         if (eventBus != null) {
            eventBus.publish(new ModuleToggleEvent(state, this));
            this.state = state;
            if (state) {
               this.onEnable();
               eventBus.subscribe(this);
            } else {
               this.onDisable();
               eventBus.unsubscribe(this);
            }
         }

         for (Setting setting : this.getSettings()) {
            if (setting instanceof ModeSetting) {
               SubModuleRegistry subModuleRegistry = (SubModuleRegistry)setting.getValue();
               if (subModuleRegistry.getSubModule() != null) {
                  if (state) {
                     subModuleRegistry.getSubModule().onEnable();
                     eventBus.subscribe(subModuleRegistry.getSubModule());
                  } else {
                     subModuleRegistry.getSubModule().onDisable();
                     eventBus.unsubscribe(subModuleRegistry.getSubModule());
                  }
               }
            }
         }
      }
   }

   public void setHidden(boolean hidden) {
      Moon.getInstance().getEventBus().publish(new ModuleHideEvent(hidden, this.state, this));
      this.hidden = hidden;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
      Moon.getInstance().getEventBus().publish(new ModuleSuffixChangedEvent(suffix, this));
   }

   public void toggle() {
      this.setEnabled(!this.state);
   }

   @Override
   public int value() {
      return this.key;
   }

   @Override
   public BindType getType() {
      return this.bindType;
   }

   @Override
   public BindAction getAction() {
      return this.bindAction;
   }

   @Override
   public void trigger() {
      this.toggle();
   }

   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", this.name);
      jsonObject.addProperty("state", this.state);
      jsonObject.addProperty("hidden", this.hidden);
      jsonObject.addProperty("key", this.key);
      jsonObject.addProperty("inputSource", this.bindType.ordinal());
      jsonObject.addProperty("pressType", this.bindAction.ordinal());
      JsonArray settingsArray = new JsonArray();

      for (Setting setting : this.getSettings()) {
         JsonObject serialized = setting.serialize();
         if (serialized != null) {
            settingsArray.add(serialized);
         }
      }

      jsonObject.add("settings", settingsArray);
      return jsonObject;
   }

   public void deserialize(JsonObject jsonObject, boolean loadBinds) {
      if (jsonObject.has("state")) {
         this.setEnabled(jsonObject.get("state").getAsBoolean());
      }

      if (loadBinds && jsonObject.has("inputSource") && jsonObject.has("pressType") && jsonObject.has("key")) {
         int bindTypeIndex = jsonObject.get("inputSource").getAsInt();
         int bindActionIndex = jsonObject.get("pressType").getAsInt();
         if (bindTypeIndex >= 0 && bindTypeIndex < BindType.values().length && bindActionIndex >= 0 && bindActionIndex < BindAction.values().length) {
            this.key = jsonObject.get("key").getAsInt();
            this.bindType = BindType.values()[bindTypeIndex];
            this.bindAction = BindAction.values()[bindActionIndex];
         }
      }

      if (jsonObject.has("hidden")) {
         this.setHidden(jsonObject.get("hidden").getAsBoolean());
      }

      JsonElement settingsElement = jsonObject.get("settings");
      if (settingsElement != null && settingsElement.isJsonArray()) {
         for (JsonElement settingElement : settingsElement.getAsJsonArray()) {
            if (settingElement.isJsonObject()) {
               JsonObject settingObject = settingElement.getAsJsonObject();
               if (settingObject.has("name")) {
                  Setting setting = this.getSetting(settingObject.get("name").getAsString());
                  if (setting != null) {
                     try {
                        setting.deserialize(settingObject);
                     } catch (Exception e) {
                        Moon.getInstance().getLogger().error("Unable to load " + setting.getDisplayName() + " setting of module " + this.displayName);
                     }
                  }
               }
            }
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getDescription() {
      return this.description;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public ModuleCategory getCategory() {
      return this.category;
   }

   public Animation getAnimation() {
      return this.animation;
   }

   public boolean isEnabled() {
      return this.state;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public float Method537() {
      return this.p;
   }

   public float Method538() {
      return this.q;
   }

   public Module setKey(int key) {
      this.key = key;
      return this;
   }

   public Module setBindType(BindType bindType) {
      this.bindType = bindType;
      return this;
   }

   public Module setBindAction(BindAction bindAction) {
      this.bindAction = bindAction;
      return this;
   }

   public Module Method539(float width) {
      this.p = width;
      return this;
   }

   public Module Method540(float height) {
      this.q = height;
      return this;
   }

   public static int Method541() {
      return 95;
   }
}
