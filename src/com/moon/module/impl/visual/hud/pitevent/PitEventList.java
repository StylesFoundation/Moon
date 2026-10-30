package com.moon.module.impl.visual.hud.pitevent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class PitEventList {
   private final List<PitEvent> events = new ArrayList<>();

   public static PitEventList fromJson(JsonElement jsonElement) {
      PitEventList eventList = new PitEventList();
      if (jsonElement.isJsonArray()) {
         for (JsonElement element : jsonElement.getAsJsonArray()) {
            if (element.isJsonObject()) {
               JsonObject eventObject = element.getAsJsonObject();
               PitEvent event = new PitEvent();
               if (eventObject.has("event")) {
                  event.setName(eventObject.get("event").getAsString());
               }

               if (eventObject.has("timestamp")) {
                  event.setTimestamp(eventObject.get("timestamp").getAsLong());
               }

               if (eventObject.has("type")) {
                  event.setType(eventObject.get("type").getAsString());
               }

               eventList.events.add(event);
            }
         }
      }

      return eventList;
   }

   public List<PitEvent> getEvents() {
      return this.events;
   }
}
