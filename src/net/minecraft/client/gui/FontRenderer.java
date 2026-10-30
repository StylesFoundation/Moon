package net.minecraft.client.gui;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;
import net.optifine.render.GlBlendState;
import net.optifine.util.FontUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

public class FontRenderer implements IResourceManagerReloadListener {
   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
   private final int[] charWidth = new int[256];
   public int FONT_HEIGHT = 9;
   public Random fontRandom = new Random();
   private final byte[] glyphWidth = new byte[65536];
   private final int[] colorCode = new int[32];
   private ResourceLocation locationFontTexture;
   private final TextureManager renderEngine;
   private float posX;
   private float posY;
   private boolean unicodeFlag;
   private boolean bidiFlag;
   private float red;
   private float blue;
   private float green;
   private float alpha;
   private int textColor;
   private boolean randomStyle;
   private boolean boldStyle;
   private boolean italicStyle;
   private boolean underlineStyle;
   private boolean strikethroughStyle;
   public GameSettings gameSettings;
   public ResourceLocation locationFontTextureBase;
   public float offsetBold = 1.0F;
   private final float[] charWidthFloat = new float[256];
   private boolean blend = false;
   private final GlBlendState oldBlendState = new GlBlendState();
   private final HashMap<String, Integer> C = new HashMap<>();
   private boolean D;

   public FontRenderer(GameSettings gameSettings, ResourceLocation resourceLocation, TextureManager textureManager, boolean boolean4) {
      this.gameSettings = gameSettings;
      this.locationFontTextureBase = resourceLocation;
      this.locationFontTexture = resourceLocation;
      this.renderEngine = textureManager;
      this.unicodeFlag = boolean4;
      this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
      this.bindTexture(this.locationFontTexture);

      for (int var7 = 0; var7 < 32; var7++) {
         int var8 = (var7 >> 3 & 1) * 85;
         int var9 = (var7 >> 2 & 1) * 170 + var8;
         int var10 = (var7 >> 1 & 1) * 170 + var8;
         int var11 = (var7 & 1) * 170 + var8;
         if (var7 == 6) {
            var9 += 85;
         }

         if (gameSettings.anaglyph) {
            int var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
            int var13 = (var9 * 30 + var10 * 70) / 100;
            int var14 = (var9 * 30 + var11 * 70) / 100;
            var9 = var12;
            var10 = var13;
            var11 = var14;
         }

         if (var7 >= 16) {
            var9 /= 4;
            var10 /= 4;
            var11 /= 4;
         }

         this.colorCode[var7] = (var9 & 0xFF) << 16 | (var10 & 0xFF) << 8 | var11 & 0xFF;
      }

      this.readGlyphSizes();
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
      Arrays.fill(unicodePageLocations, null);
      this.readFontTexture();
      this.readGlyphSizes();
   }

   private void readFontTexture() {
      BufferedImage var3;
      try {
         var3 = TextureUtil.readBufferedImage(this.getResourceInputStream(this.locationFontTexture));
      } catch (IOException var21) {
         throw new RuntimeException(var21);
      }

      Properties var4 = FontUtils.readFontProperties(this.locationFontTexture);
      this.blend = FontUtils.readBoolean(var4, "blend", false);
      int var5 = var3.getWidth();
      int var6 = var3.getHeight();
      int var7 = var5 / 16;
      int var8 = var6 / 16;
      float var9 = var5 / 128.0F;
      float var10 = Config.Method256(var9, 1.0F, 2.0F);
      this.offsetBold = 1.0F / var10;
      float var11 = FontUtils.readFloat(var4, "offsetBold", -1.0F);
      if (var11 >= 0.0F) {
         this.offsetBold = var11;
      }

      int[] var12 = new int[var5 * var6];
      var3.getRGB(0, 0, var5, var6, var12, 0, var5);

      for (int var13 = 0; var13 < 256; var13++) {
         int var14 = var13 % 16;
         int var15 = var13 / 16;

         int var16;
         for (var16 = var7 - 1; var16 >= 0; var16--) {
            int var17 = var14 * var7 + var16;
            boolean var18 = true;

            for (int var19 = 0; var19 < var8 && true; var19++) {
               int var20 = (var15 * var8 + var19) * var5;
               if ((var12[var17 + var20] >> 24 & 0xFF) > 16) {
                  var18 = false;
                  break;
               }
            }

            if (!var18) {
               break;
            }
         }

         if (var13 == 65) {
         }

         if (var13 == 32) {
            if (var7 <= 8) {
               var16 = (int)(2.0F * var9);
            } else {
               var16 = (int)(1.5F * var9);
            }
         }

         this.charWidthFloat[var13] = (var16 + 1) / var9 + 1.0F;
      }

      FontUtils.readCustomCharWidths(var4, this.charWidthFloat);

      for (int var22 = 0; var22 < this.charWidth.length; var22++) {
         this.charWidth[var22] = Math.round(this.charWidthFloat[var22]);
      }
   }

   private void readGlyphSizes() {
      InputStream var1 = null;

      try {
         var1 = this.getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
         var1.read(this.glyphWidth);
      } catch (IOException var6) {
         throw new RuntimeException(var6);
      } finally {
         IOUtils.closeQuietly(var1);
      }
   }

   private float renderChar(char character, boolean boolean2) {
      if (character != ' ' && character != 160) {
         int var5 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000"
            .indexOf(character);
         return var5 != -1 && !this.unicodeFlag ? this.renderDefaultChar(var5, boolean2) : this.renderUnicodeChar(character, boolean2);
      } else {
         return !this.unicodeFlag ? this.charWidthFloat[character] : 4.0F;
      }
   }

   private float renderDefaultChar(int integer, boolean boolean2) {
      int var5 = integer % 16 * 8;
      int var6 = integer / 16 * 8;
      int var7 = boolean2 ? 1 : 0;
      this.bindTexture(this.locationFontTexture);
      float var8 = this.charWidthFloat[integer];
      GL11.glBegin(5);
      GL11.glTexCoord2f(var5 / 128.0F, var6 / 128.0F);
      GL11.glVertex3f(this.posX + var7, this.posY, 0.0F);
      GL11.glTexCoord2f(var5 / 128.0F, (var6 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.posX - var7, this.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f((var5 + 7.99F - 1.0F) / 128.0F, var6 / 128.0F);
      GL11.glVertex3f(this.posX + 7.99F - 1.0F + var7, this.posY, 0.0F);
      GL11.glTexCoord2f((var5 + 7.99F - 1.0F) / 128.0F, (var6 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.posX + 7.99F - 1.0F - var7, this.posY + 7.99F, 0.0F);
      GL11.glEnd();
      return var8;
   }

   private ResourceLocation getUnicodePageLocation(int integer) {
      if (unicodePageLocations[integer] == null) {
         unicodePageLocations[integer] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", integer));
         unicodePageLocations[integer] = FontUtils.getHdFontLocation(unicodePageLocations[integer]);
      }

      return unicodePageLocations[integer];
   }

   private void loadGlyphTexture(int integer) {
      this.bindTexture(this.getUnicodePageLocation(integer));
   }

   private float renderUnicodeChar(char character, boolean boolean2) {
      if (this.glyphWidth[character] == 0) {
         return 0.0F;
      }

      int var5 = character / 256;
      this.loadGlyphTexture(var5);
      int var6 = this.glyphWidth[character] >>> 4;
      int var7 = this.glyphWidth[character] & 15;
      float var8 = var6;
      float var9 = var7 + 1;
      float var10 = character % 16 * 16 + var8;
      float var11 = (character & 255) / 16 * 16;
      float var12 = var9 - var8 - 0.02F;
      float var13 = boolean2 ? 1.0F : 0.0F;
      GL11.glBegin(5);
      GL11.glTexCoord2f(var10 / 256.0F, var11 / 256.0F);
      GL11.glVertex3f(this.posX + var13, this.posY, 0.0F);
      GL11.glTexCoord2f(var10 / 256.0F, (var11 + 15.98F) / 256.0F);
      GL11.glVertex3f(this.posX - var13, this.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f((var10 + var12) / 256.0F, var11 / 256.0F);
      GL11.glVertex3f(this.posX + var12 / 2.0F + var13, this.posY, 0.0F);
      GL11.glTexCoord2f((var10 + var12) / 256.0F, (var11 + 15.98F) / 256.0F);
      GL11.glVertex3f(this.posX + var12 / 2.0F - var13, this.posY + 7.99F, 0.0F);
      GL11.glEnd();
      return (var9 - var8) / 2.0F + 1.0F;
   }

   public int drawStringWithShadow(String string, float float2, float float3, int integer) {
      return this.drawString(string, float2, float3, integer, true);
   }

   public int drawString(String string, float float2, float float3, int integer) {
      return this.drawString(string, float2, float3, integer, false);
   }

   public int drawString(String string, float float2, float float3, int integer, boolean boolean5) {
      this.D = true;
      this.enableAlpha();
      if (this.blend) {
         GlStateManager.getBlendState(this.oldBlendState);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
      }

      this.resetStyles();
      int var8;
      if (boolean5) {
         var8 = Math.max(this.renderString(string, float2 + 1.0F, float3 + 1.0F, integer, true), this.renderString(string, float2, float3, integer, false));
      } else {
         var8 = this.renderString(string, float2, float3, integer, false);
      }

      if (this.blend) {
         GlStateManager.setBlendState(this.oldBlendState);
      }

      return var8;
   }

   private String bidiReorder(String string) {
      try {
         Bidi var4 = new Bidi(new ArabicShaping(8).shape(string), 127);
         var4.setReorderingMode(0);
         return var4.writeReordered(2);
      } catch (ArabicShapingException var5) {
         return string;
      }
   }

   private void resetStyles() {
      this.randomStyle = false;
      this.boldStyle = false;
      this.italicStyle = false;
      this.underlineStyle = false;
      this.strikethroughStyle = false;
   }

   private void renderStringAtPos(String string, boolean boolean2) {
      for (int var5 = 0; var5 < string.length(); var5++) {
         char var6 = string.charAt(var5);
         if (var6 == 167 && var5 + 1 < string.length()) {
            int var11 = "0123456789abcdefklmnor".indexOf(string.toLowerCase(Locale.ENGLISH).charAt(var5 + 1));
            if (var11 < 16) {
               this.randomStyle = false;
               this.boldStyle = false;
               this.strikethroughStyle = false;
               this.underlineStyle = false;
               this.italicStyle = false;
               if (var11 < 0 || var11 > 15) {
                  var11 = 15;
               }

               if (boolean2) {
                  var11 += 16;
               }

               int var13 = this.colorCode[var11];
               if (Config.isCustomColors()) {
                  var13 = CustomColors.getTextColor(var11, var13);
               }

               this.textColor = var13;
               this.setColor((var13 >> 16) / 255.0F, (var13 >> 8 & 0xFF) / 255.0F, (var13 & 0xFF) / 255.0F, this.alpha);
            } else if (var11 == 16) {
               this.randomStyle = true;
            } else if (var11 == 17) {
               this.boldStyle = true;
            } else if (var11 == 18) {
               this.strikethroughStyle = true;
            } else if (var11 == 19) {
               this.underlineStyle = true;
            } else if (var11 == 20) {
               this.italicStyle = true;
            } else if (var11 == 21) {
               this.randomStyle = false;
               this.boldStyle = false;
               this.strikethroughStyle = false;
               this.underlineStyle = false;
               this.italicStyle = false;
               this.setColor(this.red, this.blue, this.green, this.alpha);
            }

            var5++;
         } else {
            int var7 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000"
               .indexOf(var6);
            if (this.randomStyle && var7 != -1) {
               int var8 = this.getCharWidth(var6);

               char var9;
               do {
                  var7 = this.fontRandom.nextInt(256);
                  var9 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000"
                     .charAt(var7);
               } while (var8 != this.getCharWidth(var9));

               var6 = var9;
            }

            float var12 = var7 != -1 && !this.unicodeFlag ? this.offsetBold : 0.5F;
            boolean var14 = (var6 == 0 || var7 == -1 || this.unicodeFlag) && boolean2;
            if (var14) {
               this.posX -= var12;
               this.posY -= var12;
            }

            float var10 = this.renderChar(var6, this.italicStyle);
            if (var14) {
               this.posX += var12;
               this.posY += var12;
            }

            if (this.boldStyle) {
               this.posX += var12;
               if (var14) {
                  this.posX -= var12;
                  this.posY -= var12;
               }

               this.renderChar(var6, this.italicStyle);
               this.posX -= var12;
               if (var14) {
                  this.posX += var12;
                  this.posY += var12;
               }

               var10 += var12;
            }

            this.doDraw(var10);
         }
      }
   }

   protected void doDraw(float float1) {
      if (this.strikethroughStyle) {
         Tessellator var4 = Tessellator.getInstance();
         WorldRenderer var5 = var4.getWorldRenderer();
         GlStateManager.disableTexture2D();
         var5.begin(7, DefaultVertexFormats.POSITION);
         var5.pos(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0).endVertex();
         var5.pos(this.posX + float1, this.posY + this.FONT_HEIGHT / 2, 0.0).endVertex();
         var5.pos(this.posX + float1, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0).endVertex();
         var5.pos(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0).endVertex();
         var4.draw();
         GlStateManager.enableTexture2D();
      }

      if (this.underlineStyle) {
         Tessellator var7 = Tessellator.getInstance();
         WorldRenderer var8 = var7.getWorldRenderer();
         GlStateManager.disableTexture2D();
         var8.begin(7, DefaultVertexFormats.POSITION);
         int var6 = this.underlineStyle ? -1 : 0;
         var8.pos(this.posX + var6, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
         var8.pos(this.posX + float1, this.posY + this.FONT_HEIGHT, 0.0).endVertex();
         var8.pos(this.posX + float1, this.posY + this.FONT_HEIGHT - 1.0F, 0.0).endVertex();
         var8.pos(this.posX + var6, this.posY + this.FONT_HEIGHT - 1.0F, 0.0).endVertex();
         var7.draw();
         GlStateManager.enableTexture2D();
      }

      this.posX += float1;
   }

   private int renderStringAligned(String string, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
      if (this.bidiFlag) {
         int var7 = this.getStringWidth(this.bidiReorder(string));
         integer2 = integer2 + integer4 - var7;
      }

      return this.renderString(string, integer2, integer3, integer5, boolean6);
   }

   private int renderString(String string, float float2, float float3, int integer, boolean boolean5) {
      if (string == null) {
         return 0;
      }

      if (this.bidiFlag) {
         string = this.bidiReorder(string);
      }

      if ((integer & -67108864) == 0) {
         integer |= -16777216;
      }

      if (boolean5) {
         integer = (integer & 16579836) >> 2 | integer & 0xFF000000;
      }

      this.red = (integer >> 16 & 0xFF) / 255.0F;
      this.blue = (integer >> 8 & 0xFF) / 255.0F;
      this.green = (integer & 0xFF) / 255.0F;
      this.alpha = (integer >> 24 & 0xFF) / 255.0F;
      this.setColor(this.red, this.blue, this.green, this.alpha);
      this.posX = float2;
      this.posY = float3;
      this.renderStringAtPos(string, boolean5);
      return (int)this.posX;
   }

   public int getStringWidth(String string) {
      if (this.C.containsKey(string)) {
         return this.C.get(string);
      }

      if (string == null) {
         return 0;
      }

      float var4 = 0.0F;
      boolean var5 = false;

      for (int var6 = 0; var6 < string.length(); var6++) {
         char var7 = string.charAt(var6);
         float var8 = this.getCharWidthFloat(var7);
         if (var8 < 0.0F && var6 < string.length() - 1) {
            var7 = string.charAt(++var6);
            if (var7 == 'l' || var7 == 'L') {
               var5 = true;
            } else if (var7 == 'r' || var7 == 'R') {
               var5 = false;
            }

            var8 = 0.0F;
         }

         var4 += var8;
         if (var5 && var8 > 0.0F) {
            var4 += this.unicodeFlag ? 1.0F : this.offsetBold;
         }
      }

      this.C.put(string, Math.round(var4));
      return Math.round(var4);
   }

   public int getCharWidth(char character) {
      return Math.round(this.getCharWidthFloat(character));
   }

   private float getCharWidthFloat(char character) {
      if (character == 167) {
         return -1.0F;
      }

      if (character != ' ' && character != 160) {
         int var4 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000"
            .indexOf(character);
         if (character > 0 && var4 != -1 && !this.unicodeFlag) {
            return this.charWidthFloat[var4];
         }

         if (this.glyphWidth[character] != 0) {
            int var5 = this.glyphWidth[character] >>> 4;
            int var6 = this.glyphWidth[character] & 15;
            if (var6 > 7) {
               var6 = 15;
               var5 = 0;
            }

            var6++;
            return (var6 - var5) / 2 + 1;
         } else {
            return 0.0F;
         }
      } else {
         return this.charWidthFloat[32];
      }
   }

   public String trimStringToWidth(String string, int integer) {
      return this.trimStringToWidth(string, integer, false);
   }

   public String trimStringToWidth(String string, int integer, boolean boolean3) {
      StringBuilder var6 = new StringBuilder();
      float var7 = 0.0F;
      int var8 = boolean3 ? string.length() - 1 : 0;
      int var9 = boolean3 ? -1 : 1;
      boolean var10 = false;
      boolean var11 = false;

      for (int var12 = var8; var12 >= 0 && var12 < string.length() && var7 < integer; var12 += var9) {
         char var13 = string.charAt(var12);
         float var14 = this.getCharWidthFloat(var13);
         if (var10) {
            var10 = false;
            if (var13 == 'l' || var13 == 'L') {
               var11 = true;
            } else if (var13 == 'r' || var13 == 'R') {
               var11 = false;
            }
         } else if (var14 < 0.0F) {
            var10 = true;
         } else {
            var7 += var14;
            if (var11) {
               var7++;
            }
         }

         if (var7 > integer) {
            break;
         }

         if (boolean3) {
            var6.insert(0, var13);
         } else {
            var6.append(var13);
         }
      }

      return var6.toString();
   }

   private String trimStringNewline(String string) {
      while (string != null && string.endsWith("\n")) {
         string = string.substring(0, string.length() - 1);
      }

      return string;
   }

   public void drawSplitString(String string, int integer2, int integer3, int integer4, int integer5) {
      if (this.blend) {
         GlStateManager.getBlendState(this.oldBlendState);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
      }

      this.resetStyles();
      this.textColor = integer5;
      string = this.trimStringNewline(string);
      this.renderSplitString(string, integer2, integer3, integer4, false);
      if (this.blend) {
         GlStateManager.setBlendState(this.oldBlendState);
      }
   }

   private void renderSplitString(String string, int integer2, int integer3, int integer4, boolean boolean5) {
      for (String var7 : this.listFormattedStringToWidth(string, integer4)) {
         this.renderStringAligned(var7, integer2, integer3, integer4, this.textColor, boolean5);
         integer3 += this.FONT_HEIGHT;
      }
   }

   public int splitStringWidth(String string, int integer) {
      return this.FONT_HEIGHT * this.listFormattedStringToWidth(string, integer).size();
   }

   public void setUnicodeFlag(boolean boolean1) {
      this.unicodeFlag = boolean1;
   }

   public boolean getUnicodeFlag() {
      return this.unicodeFlag;
   }

   public void setBidiFlag(boolean boolean1) {
      this.bidiFlag = boolean1;
   }

   public List<String> listFormattedStringToWidth(String string, int integer) {
      return Arrays.asList(this.Method568(string, integer, 0).split("\n"));
   }

   String Method568(String string, int integer2, int integer3) {
      if (integer3 > 100) {
         return string;
      }

      if (string.length() <= 1) {
         return string;
      }

      int var6 = this.sizeStringToWidth(string, integer2);
      if (string.length() <= var6) {
         return string;
      }

      String var7 = string.substring(0, var6);
      char var8 = string.charAt(var6);
      boolean var9 = var8 == ' ' || var8 == '\n';
      String var10 = getFormatFromString(var7) + string.substring(var6 + (var9 ? 1 : 0));
      return var7 + "\n" + this.Method568(var10, integer2, integer3 + 1);
   }

   private int sizeStringToWidth(String string, int integer) {
      int var5 = string.length();
      float var6 = 0.0F;
      int var7 = 0;
      int var8 = -1;
      boolean var9 = false;

      while (var7 < var5) {
         char var10 = string.charAt(var7);
         switch (var10) {
            case '\n':
               var7--;
               break;
            case ' ':
               var8 = var7;
            default:
               var6 += this.getCharWidth(var10);
               if (var9) {
                  var6++;
               }
               break;
            case '§':
               if (var7 < var5 - 1) {
                  char var11 = string.charAt(++var7);
                  if (var11 == 'l' || var11 == 'L') {
                     var9 = true;
                  } else if (var11 == 'r' || var11 == 'R' || isFormatColor(var11)) {
                     var9 = false;
                  }
               }
         }

         if (var10 == '\n') {
            var8 = ++var7;
            break;
         }

         if (Math.round(var6) > integer) {
            break;
         }

         var7++;
      }

      return var7 != var5 && var8 != -1 && var8 < var7 ? var8 : var7;
   }

   private static boolean isFormatColor(char character) {
      return character >= '0' && character <= '9' || character >= 'a' && character <= 'f' || character >= 'A' && character <= 'F';
   }

   private static boolean isFormatSpecial(char character) {
      return character >= 'k' && character <= 'o' || character >= 'K' && character <= 'O' || character == 'r' || character == 'R';
   }

   public static String getFormatFromString(String string) {
      StringBuilder var3 = new StringBuilder();
      int var4 = -1;
      int var5 = string.length();

      while ((var4 = string.indexOf(167, var4 + 1)) != -1) {
         if (var4 < var5 - 1) {
            char var6 = string.charAt(var4 + 1);
            if (isFormatColor(var6)) {
               var3 = new StringBuilder("§" + var6);
            } else if (isFormatSpecial(var6)) {
               var3.append("§").append(var6);
            }
         }
      }

      return var3.toString();
   }

   public boolean getBidiFlag() {
      return this.bidiFlag;
   }

   public int getColorCode(char character) {
      int var4 = "0123456789abcdef".indexOf(character);
      if (var4 >= 0 && var4 < this.colorCode.length) {
         int var5 = this.colorCode[var4];
         if (Config.isCustomColors()) {
            var5 = CustomColors.getTextColor(var4, var5);
         }

         return var5;
      } else {
         return 16777215;
      }
   }

   protected void setColor(float float1, float float2, float float3, float float4) {
      GlStateManager.color(float1, float2, float3, float4);
   }

   protected void enableAlpha() {
      GlStateManager.enableAlpha();
   }

   protected void bindTexture(ResourceLocation resourceLocation) {
      if (this.renderEngine.getBoundTextureLocation() != resourceLocation || this.D) {
         this.renderEngine.bindTexture(resourceLocation);
         this.D = false;
      }
   }

   public void Method569() {
      this.C.clear();
   }

   protected InputStream getResourceInputStream(ResourceLocation resourceLocation) throws IOException {
      return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
   }
}
