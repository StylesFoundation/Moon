package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moon.Moon;
import com.moon.render.font.manager.FontManager;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomGuis;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
   private final List<ITickable> listTickables = Lists.newArrayList();
   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
   private final IResourceManager theResourceManager;
   private ITextureObject boundTexture;
   private ResourceLocation boundTextureLocation;

   public TextureManager(IResourceManager iResourceManager) {
      this.theResourceManager = iResourceManager;
   }

   public void bindTexture(ResourceLocation resourceLocation) {
      if (Config.isRandomEntities()) {
         resourceLocation = RandomEntities.getTextureLocation(resourceLocation);
      }

      if (Config.isCustomGuis()) {
         resourceLocation = CustomGuis.getTextureLocation(resourceLocation);
      }

      ITextureObject var2 = this.mapTextureObjects.get(resourceLocation);
      if (EmissiveTextures.isActive()) {
         var2 = EmissiveTextures.getEmissiveTexture(var2, this.mapTextureObjects);
      }

      if (var2 == null) {
         var2 = new SimpleTexture(resourceLocation);
         this.loadTexture(resourceLocation, var2);
      }

      if (Config.isShaders()) {
         ShadersTex.bindTexture(var2);
      } else {
         TextureUtil.bindTexture(var2.getGlTextureId());
      }

      this.boundTexture = var2;
      this.boundTextureLocation = resourceLocation;
   }

   public boolean loadTickableTexture(ResourceLocation resourceLocation, ITickableTextureObject iTickableTextureObject) {
      if (this.loadTexture(resourceLocation, iTickableTextureObject)) {
         this.listTickables.add(iTickableTextureObject);
         return true;
      } else {
         return false;
      }
   }

   public boolean loadTexture(ResourceLocation resourceLocation, ITextureObject iTextureObject) {
      boolean var3 = true;

      try {
         iTextureObject.loadTexture(this.theResourceManager);
      } catch (IOException var8) {
         logger.warn("Failed to load texture: " + resourceLocation, var8);
         iTextureObject = TextureUtil.missingTexture;
         this.mapTextureObjects.put(resourceLocation, iTextureObject);
         var3 = false;
      } catch (Throwable var9) {
         ITextureObject var5 = iTextureObject;
         CrashReport var6 = CrashReport.makeCrashReport(var9, "Registering texture");
         CrashReportCategory var7 = var6.makeCategory("Resource location being registered");
         var7.addCrashSection("Resource location", resourceLocation);
         var7.addCrashSectionCallable("Texture object class", () -> var5.getClass().getName());
         throw new ReportedException(var6);
      }

      this.mapTextureObjects.put(resourceLocation, iTextureObject);
      return var3;
   }

   public ITextureObject getTexture(ResourceLocation resourceLocation) {
      return this.mapTextureObjects.get(resourceLocation);
   }

   public ResourceLocation getDynamicTextureLocation(String string, DynamicTexture dynamicTexture) {
      if (string.equals("logo")) {
         dynamicTexture = Config.getMojangLogoTexture(dynamicTexture);
      }

      Integer var3 = this.mapTextureCounters.get(string);
      if (var3 == null) {
         var3 = 1;
      } else {
         var3 = var3 + 1;
      }

      this.mapTextureCounters.put(string, var3);
      ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", string, var3));
      this.loadTexture(var4, dynamicTexture);
      return var4;
   }

   @Override
   public void tick() {
      Iterator var1 = this.listTickables.iterator();

      while (var1.hasNext()) {
         ((ITickable)var1.next()).tick();
      }
   }

   public void deleteTexture(ResourceLocation resourceLocation) {
      ITextureObject var2 = this.getTexture(resourceLocation);
      if (var2 != null) {
         this.mapTextureObjects.remove(resourceLocation);
         TextureUtil.deleteTexture(var2.getGlTextureId());
      }
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      Config.Method251("*** Reloading textures ***");
      Config.log("Resource packs: " + Config.getResourcePackNames());
      Iterator var2 = this.mapTextureObjects.keySet().iterator();

      while (var2.hasNext()) {
         ResourceLocation var3 = (ResourceLocation)var2.next();
         String var4 = var3.getResourcePath();
         if (var4.startsWith("mcpatcher/") || var4.startsWith("optifine/") || EmissiveTextures.isEmissive(var3)) {
            ITextureObject var5 = this.mapTextureObjects.get(var3);
            if (var5 instanceof AbstractTexture) {
               ((AbstractTexture)var5).deleteGlTexture();
            }

            var2.remove();
         }
      }

      EmissiveTextures.update();

      for (Entry var8 : new HashSet<>(this.mapTextureObjects.entrySet())) {
         this.loadTexture((ResourceLocation)var8.getKey(), (ITextureObject)var8.getValue());
      }

      Minecraft var7 = Minecraft.getMinecraft();
      if (var7.fontRendererObj != null) {
         var7.fontRendererObj.Method569();
      }

      Moon var9 = Moon.getInstance();
      if (var9 != null) {
         FontManager var10 = var9.getFontManager();
         if (var10 != null) {
            var10.refreshTextureStates();
         }
      }
   }

   public void reloadBannerTextures() {
      for (Entry var2 : new HashSet<>(this.mapTextureObjects.entrySet())) {
         ResourceLocation var3 = (ResourceLocation)var2.getKey();
         ITextureObject var4 = (ITextureObject)var2.getValue();
         if (var4 instanceof LayeredColorMaskTexture) {
            this.loadTexture(var3, var4);
         }
      }
   }

   public ITextureObject getBoundTexture() {
      return this.boundTexture;
   }

   public ResourceLocation getBoundTextureLocation() {
      return this.boundTextureLocation;
   }
}
