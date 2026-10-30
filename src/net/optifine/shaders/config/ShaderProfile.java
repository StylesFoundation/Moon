package net.optifine.shaders.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShaderProfile {
   private String name = null;
   private final Map<String, String> mapOptionValues = new LinkedHashMap<>();
   private final Set<String> disabledPrograms = new LinkedHashSet<>();

   public ShaderProfile(String string) {
      this.name = string;
   }

   public String getName() {
      return this.name;
   }

   public void addOptionValue(String string1, String string2) {
      this.mapOptionValues.put(string1, string2);
   }

   public void addOptionValues(ShaderProfile shaderProfile) {
      if (shaderProfile != null) {
         this.mapOptionValues.putAll(shaderProfile.mapOptionValues);
      }
   }

   public void applyOptionValues(ShaderOption[] arr) {
      for (ShaderOption var8 : arr) {
         String var9 = var8.getName();
         String var10 = this.mapOptionValues.get(var9);
         if (var10 != null) {
            var8.setValue(var10);
         }
      }
   }

   public String[] getOptions() {
      return this.mapOptionValues.keySet().toArray(new String[0]);
   }

   public String getValue(String string) {
      return this.mapOptionValues.get(string);
   }

   public void addDisabledProgram(String string) {
      this.disabledPrograms.add(string);
   }

   public void removeDisabledProgram(String string) {
      this.disabledPrograms.remove(string);
   }

   public Collection<String> getDisabledPrograms() {
      return new LinkedHashSet<>(this.disabledPrograms);
   }

   public void addDisabledPrograms(Collection<String> collection) {
      this.disabledPrograms.addAll(collection);
   }

   public boolean isProgramDisabled(String string) {
      return this.disabledPrograms.contains(string);
   }
}
