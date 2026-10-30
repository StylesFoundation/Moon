package com.moon.module.impl.visual.hud.pitevent;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.moon.Moon;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.DraggableComponentProperties;
import com.moon.module.impl.visual.hud.base.DraggableHudElement;
import com.moon.render.RenderTask;
import com.moon.render.font.FontRenderer;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.List;
import net.minecraft.util.MathHelper;

public class PitEventTrackerElement extends DraggableHudElement {
   private final DraggableComponentProperties title = DraggableComponentProperties.builder().title("Event Tracker").build();
   private final HashMap<String, String> EVENT_COLORS = new HashMap<>();
   private final HttpClient httpClient = HttpClient.newBuilder().build();
   private long lastFetchTime = 0L;
   private PitEventList events = null;
   private PitEventList majorEvents = null;

   public PitEventTrackerElement(String name, HUD hud) {
      super(name, hud);
      this.EVENT_COLORS.put("Quick Maths", "§5");
      this.EVENT_COLORS.put("2x Rewards", "§2");
      this.EVENT_COLORS.put("Rage Pit", "§c§l");
      this.EVENT_COLORS.put("Giant Cake", "§d");
      this.EVENT_COLORS.put("KOTL", "§e");
      this.EVENT_COLORS.put("KOTH", "§3");
      this.EVENT_COLORS.put("All bounty", "§6");
      this.EVENT_COLORS.put("Blockhead", "§9§l");
      this.EVENT_COLORS.put("Raffle", "§e§l");
      this.EVENT_COLORS.put("Care Package", "§6");
      this.EVENT_COLORS.put("Team Deathmatch", "§d§l");
      this.EVENT_COLORS.put("Robbery", "§e§l");
      this.EVENT_COLORS.put("Squads", "§b§l");
      this.EVENT_COLORS.put("Spire", "§d§l");
      this.EVENT_COLORS.put("Auction", "§7");
      this.EVENT_COLORS.put("Beast", "§a§l");
      this.EVENT_COLORS.put("Pizza", "§c§l");
   }

   @Override
   public void setRectangleTask(RenderTask task) {
      this.title.setTitleDisabled(this.hud.getPitHideTitle().getValue());
   }

   @Override
   public void setRoundedTask(RenderTask task) {
      if (this.hud.getPitPadding().getValue()) {
         this.setWidth(150.0F);
      }
   }

   @Override
   public void setBackgroundTask(RenderTask task) {
   }

   @Override
   public void setTextTask(RenderTask task) {
      FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
      if (this.events == null) {
         fontRenderer.draw(task, "§eLoading...", 8.0F, this.getContentX(), this.getContentY(), -1, true);
         this.setHeight(fontRenderer.getLineHeight(12.0F) + fontRenderer.getLineHeight(8.0F) + 12.0F);
      } else {
         float maxWidth = 0.0F;
         List eventList = this.hud.getPitOnlyMajor().getValue() ? this.majorEvents.getEvents() : this.events.getEvents();
         int eventCount = Math.min(this.hud.getPitEventAmount().getValue(), eventList.size());

         for (int i = 0; i < eventCount; i++) {
            PitEvent event = (PitEvent)eventList.get(i);
            String color = this.EVENT_COLORS.getOrDefault(event.getName(), "§7");
            long timestamp = event.getTimestamp();
            String statusText = timestamp <= System.currentTimeMillis() ? "Active" : "active in " + this.formatDuration(timestamp - System.currentTimeMillis());
            float lineY = this.getContentY() + fontRenderer.getLineHeight(8.0F) * i;
            String text = color + event.getName();
            if (!this.hud.getPitPadding().getValue()) {
               text = text + " §7(" + statusText + ")";
            } else {
               fontRenderer.drawWithShadow(task, "§7" + statusText, 8.0F, this.getContentX() + this.getWidth() - fontRenderer.getTextWidth(statusText, 8.0F) - 8.0F, lineY, -1);
            }

            fontRenderer.drawWithShadow(task, text, 8.0F, this.getContentX(), lineY, -1);
            float textWidth = fontRenderer.getTextWidth(text, 8.0F) + 8.0F;
            if (maxWidth < textWidth) {
               maxWidth = textWidth;
            }
         }

         maxWidth = Math.max(maxWidth, fontRenderer.getTextWidth("Event Tracker", 12.0F) + 8.0F);
         if (!this.hud.getPitPadding().getValue()) {
            this.setWidth(maxWidth);
         }

         this.setHeight(
            (this.title.isTitleDisabled() ? 0.0F : fontRenderer.getLineHeight(12.0F)) + fontRenderer.getLineHeight(8.0F) * eventCount + (this.title.isTitleDisabled() ? 7 : 12)
         );
      }
   }

   @Override
   public void renderPost() {
      if (System.currentTimeMillis() - this.lastFetchTime > 10000L) {
         Moon.getInstance().getExecutor().submit(() -> {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://events.mcpqndq.dev/")).GET().build();

            try {
               HttpResponse response = this.httpClient.send(request, BodyHandlers.ofString());
               if (response.statusCode() == 200) {
                  JsonElement jsonElement = JsonParser.parseString((String)response.body());
                  this.events = PitEventList.fromJson(jsonElement);
                  this.majorEvents = PitEventList.fromJson(jsonElement);
                  this.majorEvents.getEvents().removeIf(pitEvent -> !pitEvent.getType().equals("major"));
               }

               this.lastFetchTime = System.currentTimeMillis();
            } catch (IOException | InterruptedException e) {
               Moon.getInstance().getLogger().error("Failed to create pit tracking request", new Object[]{e});
            }
         });
      }
   }

   @Override
   public boolean isVisible() {
      return this.hud.getPitEventTracker().getValue();
   }

   private String formatDuration(long durationMillis) {
      if (durationMillis <= 60000L) {
         return MathHelper.floor_double(durationMillis * 100L) / 100000 + "s";
      } else {
         return durationMillis <= 3600000L
            ? MathHelper.floor_double((float)durationMillis / 60000.0F) + "m" + MathHelper.floor_double((float)durationMillis / 1000.0F) % 60 + "s"
            : MathHelper.floor_double((float)durationMillis / 3600000.0F) + "h" + MathHelper.floor_double((float)durationMillis / 60000.0F) % 60 + "m";
      }
   }

   @Override
   public DraggableComponentProperties getTitle() {
      return this.title;
   }
}
