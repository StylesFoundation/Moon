package com.moon.render.profiler;

import java.util.ArrayList;
import java.util.List;

public class RenderProfiler {
   private final List<ProfilerCategory> categories = new ArrayList<>();
   private final ProfilerCategory rendering = ProfilerCategory.of("rendering", "Rendering Tasks");
   private final ProfilerCategory postProcessing = ProfilerCategory.of("post_processing", "Post Processing");
   private final ProfilerCategory particle = ProfilerCategory.of("particle", "Particle Tasks");
   private final EventProfiler events = EventProfiler.of("events", "Events");

   public void registerTimers() {
      this.rendering.register(ProfilerTimer.of("total", "Total"));
      this.rendering.register(ProfilerTimer.of("init", "Initialization"));
      this.rendering.register(ProfilerTimer.of("attribute_apply", "Attributes"));
      this.rendering.register(ProfilerTimer.of("mask_apply", "Masks"));
      this.rendering.register(ProfilerTimer.of("buffer_upload", "Buffer Upload"));
      this.rendering.register(ProfilerTimer.of("draw_call", "Draw Call"));
      this.categories.add(this.rendering);
      this.postProcessing.register(ProfilerTimer.of("blur_apply", "Blur Apply"));
      this.postProcessing.register(ProfilerTimer.of("shadow_apply", "Shadow Apply"));
      this.categories.add(this.postProcessing);
      this.particle.register(ProfilerTimer.of("total", "Total"));
      this.particle.register(ProfilerTimer.of("writing", "Buffer Writing"));
      this.categories.add(this.particle);
   }

   public void endFrame() {
      for (ProfilerCategory category : this.categories) {
         category.stopAll();
      }

      this.events.endFrame();
   }

   public List<ProfilerCategory> getCategories() {
      return this.categories;
   }

   public ProfilerCategory getRenderingCategory() {
      return this.rendering;
   }

   public ProfilerCategory getPostProcessingCategory() {
      return this.postProcessing;
   }

   public ProfilerCategory getParticleCategory() {
      return this.particle;
   }

   public EventProfiler getEvents() {
      return this.events;
   }
}
