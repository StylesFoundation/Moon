package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.BetterGrass;
import net.optifine.ConnectedTextures;
import net.optifine.CustomItems;
import net.optifine.EmissiveTextures;
import net.optifine.SmartAnimations;
import net.optifine.shaders.ShadersTex;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject {
   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
   private static final Logger logger = LogManager.getLogger();
   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
   private final List<TextureAtlasSprite> listAnimatedSprites;
   private final Map<String, TextureAtlasSprite> mapRegisteredSprites;
   private final Map<String, TextureAtlasSprite> mapUploadedSprites;
   private final String basePath;
   private final IIconCreator iconCreator;
   private int mipmapLevels;
   private final TextureAtlasSprite missingImage;
   private boolean skipFirst = false;
   private TextureAtlasSprite[] iconGrid = null;
   private int iconGridSize = -1;
   private int iconGridCountX = -1;
   private int iconGridCountY = -1;
   private double iconGridSizeU = -1.0;
   private double iconGridSizeV = -1.0;
   private final CounterInt counterIndexInMap = new CounterInt(0);
   public int atlasWidth = 0;
   public int atlasHeight = 0;
   private int countAnimationsActive;
   private int frameCountAnimations;

   public TextureMap(String string) {
      this(string, null);
   }

   public TextureMap(String string, boolean boolean2) {
      this(string, null, boolean2);
   }

   public TextureMap(String string, IIconCreator iIconCreator) {
      this(string, iIconCreator, false);
   }

   public TextureMap(String string, IIconCreator iIconCreator, boolean boolean3) {
      this.listAnimatedSprites = Lists.newArrayList();
      this.mapRegisteredSprites = Maps.newHashMap();
      this.mapUploadedSprites = Maps.newHashMap();
      this.missingImage = new TextureAtlasSprite("missingno");
      this.basePath = string;
      this.iconCreator = iIconCreator;
      this.skipFirst = boolean3 && ENABLE_SKIP;
   }

   private void initMissingImage() {
      int var1 = this.getMinSpriteSize();
      int[] var2 = this.getMissingImageData(var1);
      this.missingImage.setIconWidth(var1);
      this.missingImage.setIconHeight(var1);
      int[][] var3 = new int[this.mipmapLevels + 1][];
      var3[0] = var2;
      this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][]{var3}));
      this.missingImage.setIndexInMap(this.counterIndexInMap.nextValue());
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
      if (this.iconCreator != null) {
         this.loadSprites(iResourceManager, this.iconCreator);
      }
   }

   public void loadSprites(IResourceManager iResourceManager, IIconCreator iIconCreator) {
      this.mapRegisteredSprites.clear();
      this.counterIndexInMap.reset();
      iIconCreator.registerSprites(this);
      if (this.mipmapLevels >= 4) {
         this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, iResourceManager);
         Config.log("Mipmap levels: " + this.mipmapLevels);
      }

      this.initMissingImage();
      this.deleteGlTexture();
      this.loadTextureAtlas(iResourceManager);
   }

   public void loadTextureAtlas(IResourceManager iResourceManager) {
      Config.Method251("Multitexture: " + Config.isMultiTexture());
      if (Config.isMultiTexture()) {
         Iterator var4 = this.mapUploadedSprites.values().iterator();

         while (var4.hasNext()) {
            ((TextureAtlasSprite)var4.next()).deleteSpriteTexture();
         }
      }

      ConnectedTextures.updateIcons(this);
      CustomItems.updateIcons(this);
      BetterGrass.updateIcons(this);
      int var33 = TextureUtils.getGLMaximumTextureSize();
      Stitcher var5 = new Stitcher(var33, var33, true, 0, this.mipmapLevels);
      this.mapUploadedSprites.clear();
      this.listAnimatedSprites.clear();
      int var6 = Integer.MAX_VALUE;
      int var7 = this.getMinSpriteSize();
      this.iconGridSize = var7;
      int var8 = 1 << this.mipmapLevels;
      int var9 = 0;
      int var10 = 0;

      for (Entry var12 : this.mapRegisteredSprites.entrySet()) {
         if (!this.skipFirst) {
            TextureAtlasSprite var35 = (TextureAtlasSprite)var12.getValue();
            ResourceLocation var37 = new ResourceLocation(var35.getIconName());
            ResourceLocation var41 = this.completeResourceLocation(var37, 0);
            var35.updateIndexInMap(this.counterIndexInMap);
            if (var35.hasCustomLoader(iResourceManager, var37)) {
               if (!var35.load(iResourceManager, var37)) {
                  var6 = Math.min(var6, Math.min(var35.getIconWidth(), var35.getIconHeight()));
                  var5.addSprite(var35);
                  Config.Method250("Custom loader (skipped): " + var35);
                  var10++;
               }

               Config.Method250("Custom loader: " + var35);
               var9++;
            } else {
               try {
                  IResource var43 = iResourceManager.getResource(var41);
                  BufferedImage[] var47 = new BufferedImage[1 + this.mipmapLevels];
                  var47[0] = TextureUtil.readBufferedImage(var43.getInputStream());
                  int var49 = var47[0].getWidth();
                  int var51 = var47[0].getHeight();
                  if (var49 < 1 || var51 < 1) {
                     Config.Method252("Invalid sprite size: " + var35);
                     continue;
                  }

                  if (var49 < var7 || this.mipmapLevels > 0) {
                     int var53 = this.mipmapLevels > 0 ? TextureUtils.scaleToGrid(var49, var7) : TextureUtils.scaleToMin(var49, var7);
                     if (var53 != var49) {
                        if (!TextureUtils.isPowerOfTwo(var49)) {
                           Config.log("Scaled non power of 2: " + var35.getIconName() + ", " + var49 + " -> " + var53);
                        } else {
                           Config.log("Scaled too small texture: " + var35.getIconName() + ", " + var49 + " -> " + var53);
                        }

                        int var10000 = var51 * var53 / var49;
                        var47[0] = TextureUtils.scaleImage(var47[0], var53);
                     }
                  }

                  TextureMetadataSection var54 = var43.getMetadata("texture");
                  if (var54 != null) {
                     List<Integer> var21 = var54.getListMipmaps();
                     if (!var21.isEmpty()) {
                        int var22 = var47[0].getWidth();
                        int var23 = var47[0].getHeight();
                        if (MathHelper.roundUpToPowerOfTwo(var22) != var22 || MathHelper.roundUpToPowerOfTwo(var23) != var23) {
                           throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                        }
                     }

                     for (int var57 : var21) {
                        if (var57 > 0 && var57 < var47.length - 1 && var47[var57] == null) {
                           ResourceLocation var24 = this.completeResourceLocation(var37, var57);

                           try {
                              var47[var57] = TextureUtil.readBufferedImage(iResourceManager.getResource(var24).getInputStream());
                           } catch (IOException var30) {
                              logger.error("Unable to load miplevel {} from: {}", var57, var24, var30);
                           }
                        }
                     }
                  }

                  AnimationMetadataSection var55 = var43.getMetadata("animation");
                  var35.loadSprite(var47, var55);
               } catch (RuntimeException var31) {
                  logger.error("Unable to parse metadata from " + var41, var31);
                  continue;
               } catch (IOException var32) {
                  logger.error("Using missing texture, unable to load " + var41 + ", " + var32.getClass().getName());
                  continue;
               }

               var6 = Math.min(var6, Math.min(var35.getIconWidth(), var35.getIconHeight()));
               int var44 = Math.min(Integer.lowestOneBit(var35.getIconWidth()), Integer.lowestOneBit(var35.getIconHeight()));
               if (var44 < var8) {
                  logger.warn(
                     "Texture {} with size {}x{} limits mip level from {} to {}",
                     var41,
                     var35.getIconWidth(),
                     var35.getIconHeight(),
                     MathHelper.calculateLogBaseTwo(var8),
                     MathHelper.calculateLogBaseTwo(var44)
                  );
                  var8 = var44;
               }

               var5.addSprite(var35);
            }
            continue;
         }
         break;
      }

      if (var9 > 0) {
         Config.Method251("Custom loader sprites: " + var9);
      }

      if (var10 > 0) {
         Config.Method251("Custom loader sprites (skipped): " + var10);
      }

      int var34 = Math.min(var6, var8);
      int var13 = MathHelper.calculateLogBaseTwo(var34);
      if (var13 < 0) {
         var13 = 0;
      }

      if (var13 < this.mipmapLevels) {
         logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.basePath, this.mipmapLevels, var13, var34);
         this.mipmapLevels = var13;
      }

      for (TextureAtlasSprite var15 : this.mapRegisteredSprites.values()) {
         if (this.skipFirst) {
            break;
         }

         try {
            var15.generateMipmaps(this.mipmapLevels);
         } catch (Throwable var29) {
            CrashReport var17 = CrashReport.makeCrashReport(var29, "Applying mipmap");
            CrashReportCategory var18 = var17.makeCategory("Sprite being mipmapped");
            var18.addCrashSectionCallable("Sprite name", () -> var15.getIconName());
            var18.addCrashSectionCallable("Sprite size", () -> var15.getIconWidth() + " x " + var15.getIconHeight());
            var18.addCrashSectionCallable("Sprite frames", () -> var15.getFrameCount() + " frames");
            var18.addCrashSection("Mipmap levels", this.mipmapLevels);
            throw new ReportedException(var17);
         }
      }

      this.missingImage.generateMipmaps(this.mipmapLevels);
      var5.addSprite(this.missingImage);
      this.skipFirst = false;

      try {
         var5.doStitch();
      } catch (StitcherException var28) {
         throw var28;
      }

      logger.info("Created: {}x{} {}-atlas", var5.Method375(), var5.Method376(), this.basePath);
      if (Config.isShaders()) {
         ShadersTex.allocateTextureMap(this.getGlTextureId(), this.mipmapLevels, var5.Method375(), var5.Method376(), var5, this);
      } else {
         TextureUtil.Method460(this.getGlTextureId(), this.mipmapLevels, var5.Method375(), var5.Method376());
      }

      HashMap var36 = Maps.newHashMap(this.mapRegisteredSprites);

      for (TextureAtlasSprite var16 : var5.getStichSlots()) {
         String var45 = var16.getIconName();
         var36.remove(var45);
         this.mapUploadedSprites.put(var45, var16);

         try {
            if (Config.isShaders()) {
               ShadersTex.uploadTexSubForLoadAtlas(
                  this,
                  var16.getIconName(),
                  var16.getFrameTextureData(0),
                  var16.getIconWidth(),
                  var16.getIconHeight(),
                  var16.getOriginX(),
                  var16.getOriginY(),
                  false,
                  false
               );
            } else {
               TextureUtil.uploadTextureMipmap(
                  var16.getFrameTextureData(0), var16.getIconWidth(), var16.getIconHeight(), var16.getOriginX(), var16.getOriginY(), false, false
               );
            }
         } catch (Throwable var27) {
            CrashReport var19 = CrashReport.makeCrashReport(var27, "Stitching texture atlas");
            CrashReportCategory var20 = var19.makeCategory("Texture being stitched together");
            var20.addCrashSection("Atlas path", this.basePath);
            var20.addCrashSection("Sprite", var16);
            throw new ReportedException(var19);
         }

         if (var16.hasAnimationMetadata()) {
            var16.setAnimationIndex(this.listAnimatedSprites.size());
            this.listAnimatedSprites.add(var16);
         }
      }

      Iterator var39 = var36.values().iterator();

      while (var39.hasNext()) {
         ((TextureAtlasSprite)var39.next()).copyFrom(this.missingImage);
      }

      Config.log("Animated sprites: " + this.listAnimatedSprites.size());
      if (Config.isMultiTexture()) {
         int var40 = var5.Method375();
         int var42 = var5.Method376();

         for (TextureAtlasSprite var48 : var5.getStichSlots()) {
            var48.sheetWidth = var40;
            var48.sheetHeight = var42;
            var48.mipmapLevels = this.mipmapLevels;
            TextureAtlasSprite var50 = var48.spriteSingle;
            if (var50 != null) {
               if (var50.getIconWidth() <= 0) {
                  var50.setIconWidth(var48.getIconWidth());
                  var50.setIconHeight(var48.getIconHeight());
                  var50.initSprite(var48.getIconWidth(), var48.getIconHeight(), 0, 0, false);
                  var50.clearFramesTextureData();
                  List var52 = var48.getFramesTextureData();
                  var50.setFramesTextureData(var52);
                  var50.setAnimationMetadata(var48.getAnimationMetadata());
               }

               var50.sheetWidth = var40;
               var50.sheetHeight = var42;
               var50.mipmapLevels = this.mipmapLevels;
               var50.setAnimationIndex(var48.getAnimationIndex());
               var48.bindSpriteTexture();

               try {
                  TextureUtil.uploadTextureMipmap(
                     var50.getFrameTextureData(0), var50.getIconWidth(), var50.getIconHeight(), var50.getOriginX(), var50.getOriginY(), false, true
                  );
               } catch (Exception var26) {
                  Config.Method251("Error uploading sprite single: " + var50 + ", parent: " + var48);
                  var26.printStackTrace();
               }
            }
         }

         Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
      }

      this.updateIconGrid(var5.Method375(), var5.Method376());
      if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
         Config.Method251("Exporting texture map: " + this.basePath);
         TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, var5.Method375(), var5.Method376());
      }
   }

   public ResourceLocation completeResourceLocation(ResourceLocation resourceLocation) {
      return this.completeResourceLocation(resourceLocation, 0);
   }

   public ResourceLocation completeResourceLocation(ResourceLocation resourceLocation, int integer) {
      return this.isAbsoluteLocation(resourceLocation)
         ? new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + ".png")
         : (
            integer == 0
               ? new ResourceLocation(resourceLocation.getResourceDomain(), String.format("%s/%s%s", this.basePath, resourceLocation.getResourcePath(), ".png"))
               : new ResourceLocation(
                  resourceLocation.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", this.basePath, resourceLocation.getResourcePath(), integer, ".png")
               )
         );
   }

   public TextureAtlasSprite getAtlasSprite(String string) {
      TextureAtlasSprite var2 = this.mapUploadedSprites.get(string);
      if (var2 == null) {
         var2 = this.missingImage;
      }

      return var2;
   }

   public void updateAnimations() {
      boolean var1 = false;
      boolean var2 = false;
      TextureUtil.bindTexture(this.getGlTextureId());
      int var3 = 0;

      for (TextureAtlasSprite var5 : this.listAnimatedSprites) {
         if (this.isTerrainAnimationActive(var5)) {
            var5.updateAnimation();
            if (var5.isAnimationActive()) {
               var3++;
            }

            if (var5.spriteNormal != null) {
               var1 = true;
            }

            if (var5.spriteSpecular != null) {
               var2 = true;
            }
         }
      }

      if (Config.isMultiTexture()) {
         for (TextureAtlasSprite var11 : this.listAnimatedSprites) {
            if (this.isTerrainAnimationActive(var11)) {
               TextureAtlasSprite var6 = var11.spriteSingle;
               if (var6 != null) {
                  if (var11 == TextureUtils.iconClock || var11 == TextureUtils.iconCompass) {
                     var6.frameCounter = var11.frameCounter;
                  }

                  var11.bindSpriteTexture();
                  var6.updateAnimation();
                  if (var6.isAnimationActive()) {
                     var3++;
                  }
               }
            }
         }

         TextureUtil.bindTexture(this.getGlTextureId());
      }

      if (Config.isShaders()) {
         if (var1) {
            TextureUtil.bindTexture(this.getMultiTexID().norm);

            for (TextureAtlasSprite var12 : this.listAnimatedSprites) {
               if (var12.spriteNormal != null && this.isTerrainAnimationActive(var12)) {
                  if (var12 == TextureUtils.iconClock || var12 == TextureUtils.iconCompass) {
                     var12.spriteNormal.frameCounter = var12.frameCounter;
                  }

                  var12.spriteNormal.updateAnimation();
                  if (var12.spriteNormal.isAnimationActive()) {
                     var3++;
                  }
               }
            }
         }

         if (var2) {
            TextureUtil.bindTexture(this.getMultiTexID().spec);

            for (TextureAtlasSprite var13 : this.listAnimatedSprites) {
               if (var13.spriteSpecular != null && this.isTerrainAnimationActive(var13)) {
                  if (var13 == TextureUtils.iconClock || var13 == TextureUtils.iconCompass) {
                     var13.spriteNormal.frameCounter = var13.frameCounter;
                  }

                  var13.spriteSpecular.updateAnimation();
                  if (var13.spriteSpecular.isAnimationActive()) {
                     var3++;
                  }
               }
            }
         }

         if (var1 || var2) {
            TextureUtil.bindTexture(this.getGlTextureId());
         }
      }

      int var10 = Config.getMinecraft().entityRenderer.frameCount;
      if (var10 != this.frameCountAnimations) {
         this.countAnimationsActive = var3;
         this.frameCountAnimations = var10;
      }

      if (SmartAnimations.isActive()) {
         SmartAnimations.resetSpritesRendered();
      }
   }

   public TextureAtlasSprite registerSprite(ResourceLocation resourceLocation) {
      if (resourceLocation == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      }

      TextureAtlasSprite var2 = this.mapRegisteredSprites.get(resourceLocation.toString());
      if (var2 == null) {
         var2 = TextureAtlasSprite.makeAtlasSprite(resourceLocation);
         this.mapRegisteredSprites.put(resourceLocation.toString(), var2);
         var2.updateIndexInMap(this.counterIndexInMap);
         if (Config.isEmissiveTextures()) {
            this.checkEmissive(resourceLocation, var2);
         }
      }

      return var2;
   }

   @Override
   public void tick() {
      this.updateAnimations();
   }

   public void setMipmapLevels(int integer) {
      this.mipmapLevels = integer;
   }

   public TextureAtlasSprite getMissingSprite() {
      return this.missingImage;
   }

   public TextureAtlasSprite getTextureExtry(String string) {
      return this.mapRegisteredSprites.get(string);
   }

   public boolean setTextureEntry(String string, TextureAtlasSprite textureAtlasSprite) {
      if (!this.mapRegisteredSprites.containsKey(string)) {
         this.mapRegisteredSprites.put(string, textureAtlasSprite);
         textureAtlasSprite.updateIndexInMap(this.counterIndexInMap);
         return true;
      } else {
         return false;
      }
   }

   public boolean setTextureEntry(TextureAtlasSprite textureAtlasSprite) {
      return this.setTextureEntry(textureAtlasSprite.getIconName(), textureAtlasSprite);
   }

   public String getBasePath() {
      return this.basePath;
   }

   public int getMipmapLevels() {
      return this.mipmapLevels;
   }

   private boolean isAbsoluteLocation(ResourceLocation resourceLocation) {
      String var2 = resourceLocation.getResourcePath();
      return this.isAbsoluteLocationPath(var2);
   }

   private boolean isAbsoluteLocationPath(String string) {
      String var2 = string.toLowerCase();
      return var2.startsWith("mcpatcher/") || var2.startsWith("optifine/");
   }

   public TextureAtlasSprite getSpriteSafe(String string) {
      ResourceLocation var2 = new ResourceLocation(string);
      return this.mapRegisteredSprites.get(var2.toString());
   }

   public TextureAtlasSprite getRegisteredSprite(ResourceLocation resourceLocation) {
      return this.mapRegisteredSprites.get(resourceLocation.toString());
   }

   private boolean isTerrainAnimationActive(TextureAtlasSprite textureAtlasSprite) {
      return textureAtlasSprite == TextureUtils.iconWaterStill || textureAtlasSprite == TextureUtils.iconWaterFlow
         ? Config.Method259()
         : (
            textureAtlasSprite == TextureUtils.iconLavaStill || textureAtlasSprite == TextureUtils.iconLavaFlow
               ? Config.Method262()
               : (
                  textureAtlasSprite == TextureUtils.iconFireLayer0 || textureAtlasSprite == TextureUtils.iconFireLayer1
                     ? Config.Method264()
                     : (
                        textureAtlasSprite == TextureUtils.iconPortal
                           ? Config.Method261()
                           : textureAtlasSprite == TextureUtils.iconClock || textureAtlasSprite == TextureUtils.iconCompass || Config.ab()
                     )
               )
         );
   }

   public int getCountRegisteredSprites() {
      return this.counterIndexInMap.getValue();
   }

   private int detectMaxMipmapLevel(Map map, IResourceManager iResourceManager) {
      int var5 = this.detectMinimumSpriteSize(map, iResourceManager, 20);
      if (var5 < 16) {
         var5 = 16;
      }

      var5 = MathHelper.roundUpToPowerOfTwo(var5);
      if (var5 > 16) {
         Config.log("Sprite size: " + var5);
      }

      int var6 = MathHelper.calculateLogBaseTwo(var5);
      if (var6 < 4) {
         var6 = 4;
      }

      return var6;
   }

   private int detectMinimumSpriteSize(Map<Object, Object> map, IResourceManager iResourceManager, int integer) {
      Map<Integer, Integer> var6 = new HashMap<Integer, Integer>();
      Iterator var7 = map.entrySet().iterator();

      while (var7.hasNext()) {
         TextureAtlasSprite var9 = (TextureAtlasSprite)((Entry)var7.next()).getValue();
         ResourceLocation var10 = new ResourceLocation(var9.getIconName());
         ResourceLocation var11 = this.completeResourceLocation(var10);
         if (!var9.hasCustomLoader(iResourceManager, var10)) {
            try {
               IResource var12 = iResourceManager.getResource(var11);
               if (var12 != null) {
                  InputStream var13 = var12.getInputStream();
                  if (var13 != null) {
                     Dimension var14 = TextureUtils.getImageSize(var13, "png");
                     var13.close();
                     if (var14 != null) {
                        int var16 = MathHelper.roundUpToPowerOfTwo(var14.width);
                        if (!var6.containsKey(var16)) {
                           var6.put(var16, 1);
                        } else {
                           int var17 = (Integer)var6.get(var16);
                           var6.put(var16, var17 + 1);
                        }
                     }
                  }
               }
            } catch (Exception var18) {
            }
         }
      }

      int var19 = 0;
      Set var8 = var6.keySet();
      TreeSet<Integer> var20 = new TreeSet<Integer>(var8);

      for (int var25 : var20) {
         int var21 = (Integer)var6.get(var25);
         var19 += var21;
      }

      int var24 = 16;
      int var26 = 0;
      int var22 = var19 * integer / 100;

      for (int var15 : var20) {
         int var28 = (Integer)var6.get(var15);
         var26 += var28;
         if (var15 > var24) {
            var24 = var15;
         }

         if (var26 > var22) {
            return var24;
         }
      }

      return var24;
   }

   private int getMinSpriteSize() {
      int var3 = 1 << this.mipmapLevels;
      if (var3 < 8) {
         var3 = 8;
      }

      return var3;
   }

   private int[] getMissingImageData(int integer) {
      BufferedImage var4 = new BufferedImage(16, 16, 2);
      var4.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
      BufferedImage var5 = TextureUtils.scaleImage(var4, integer);
      int[] var6 = new int[integer * integer];
      var5.getRGB(0, 0, integer, integer, var6, 0, integer);
      return var6;
   }

   public boolean isTextureBound() {
      int var1 = GlStateManager.getBoundTexture();
      int var2 = this.getGlTextureId();
      return var1 == var2;
   }

   private void updateIconGrid(int integer1, int integer2) {
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGrid = null;
      if (this.iconGridSize > 0) {
         this.iconGridCountX = integer1 / this.iconGridSize;
         this.iconGridCountY = integer2 / this.iconGridSize;
         this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
         this.iconGridSizeU = 1.0 / this.iconGridCountX;
         this.iconGridSizeV = 1.0 / this.iconGridCountY;

         for (TextureAtlasSprite var4 : this.mapUploadedSprites.values()) {
            double var5 = 0.5 / integer1;
            double var7 = 0.5 / integer2;
            double var9 = Math.min(var4.getMinU(), var4.getMaxU()) + var5;
            double var11 = Math.min(var4.getMinV(), var4.getMaxV()) + var7;
            double var13 = Math.max(var4.getMinU(), var4.getMaxU()) - var5;
            double var15 = Math.max(var4.getMinV(), var4.getMaxV()) - var7;
            int var17 = (int)(var9 / this.iconGridSizeU);
            int var18 = (int)(var11 / this.iconGridSizeV);
            int var19 = (int)(var13 / this.iconGridSizeU);
            int var20 = (int)(var15 / this.iconGridSizeV);

            for (int var21 = var17; var21 <= var19; var21++) {
               if (var21 >= 0 && var21 < this.iconGridCountX) {
                  for (int var22 = var18; var22 <= var20; var22++) {
                     if (var22 >= 0 && var22 < this.iconGridCountX) {
                        int var23 = var22 * this.iconGridCountX + var21;
                        this.iconGrid[var23] = var4;
                     } else {
                        Config.Method252("Invalid grid V: " + var22 + ", icon: " + var4.getIconName());
                     }
                  }
               } else {
                  Config.Method252("Invalid grid U: " + var21 + ", icon: " + var4.getIconName());
               }
            }
         }
      }
   }

   public TextureAtlasSprite getIconByUV(double double1, double double2) {
      if (this.iconGrid == null) {
         return null;
      }

      int var5 = (int)(double1 / this.iconGridSizeU);
      int var7 = (int)(double2 / this.iconGridSizeV) * this.iconGridCountX + var5;
      return var7 >= 0 && var7 <= this.iconGrid.length ? this.iconGrid[var7] : null;
   }

   private void checkEmissive(ResourceLocation resourceLocation, TextureAtlasSprite textureAtlasSprite) {
      String var3 = EmissiveTextures.getSuffixEmissive();
      if (var3 != null && !resourceLocation.getResourcePath().endsWith(var3)) {
         ResourceLocation var4 = new ResourceLocation(resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + var3);
         if (Config.hasResource(this.completeResourceLocation(var4))) {
            TextureAtlasSprite var6 = this.registerSprite(var4);
            var6.isEmissive = true;
            textureAtlasSprite.spriteEmissive = var6;
         }
      }
   }

   public int getCountAnimations() {
      return this.listAnimatedSprites.size();
   }

   public int getCountAnimationsActive() {
      return this.countAnimationsActive;
   }
}
