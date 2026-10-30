package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements IResource {
   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
   private final String resourcePackName;
   private final ResourceLocation srResourceLocation;
   private final InputStream resourceInputStream;
   private final InputStream mcmetaInputStream;
   private final IMetadataSerializer srMetadataSerializer;
   private boolean mcmetaJsonChecked;
   private JsonObject mcmetaJson;

   public SimpleResource(
      String string, ResourceLocation resourceLocation, InputStream inputStream3, InputStream inputStream4, IMetadataSerializer iMetadataSerializer
   ) {
      this.resourcePackName = string;
      this.srResourceLocation = resourceLocation;
      this.resourceInputStream = inputStream3;
      this.mcmetaInputStream = inputStream4;
      this.srMetadataSerializer = iMetadataSerializer;
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.srResourceLocation;
   }

   @Override
   public InputStream getInputStream() {
      return this.resourceInputStream;
   }

   @Override
   public boolean hasMetadata() {
      return this.mcmetaInputStream != null;
   }

   @Override
   public <T extends IMetadataSection> T getMetadata(String string) {
      if (!this.hasMetadata()) {
         return null;
      }

      if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
         this.mcmetaJsonChecked = true;
         BufferedReader var5 = null;

         try {
            var5 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
            this.mcmetaJson = JsonParser.parseReader(var5).getAsJsonObject();
         } finally {
            IOUtils.closeQuietly(var5);
         }
      }

      IMetadataSection var9 = this.mapMetadataSections.get(string);
      if (var9 == null) {
         var9 = this.srMetadataSerializer.parseMetadataSection(string, this.mcmetaJson);
      }

      return (T)var9;
   }

   @Override
   public String getResourcePackName() {
      return this.resourcePackName;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (object instanceof SimpleResource var5) {
         if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(var5.srResourceLocation)) {
               return false;
            }
         } else if (var5.srResourceLocation != null) {
            return false;
         }

         return this.resourcePackName != null ? this.resourcePackName.equals(var5.resourcePackName) : var5.resourcePackName == null;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var4 = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
      return 31 * var4 + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
   }
}
