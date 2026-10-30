package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityBanner extends TileEntity {
   private int baseColor;
   private NBTTagList patterns;
   private boolean field_175119_g;
   private List<TileEntityBanner.EnumBannerPattern> patternList;
   private List<EnumDyeColor> colorList;
   private String patternResourceLocation;

   public void setItemValues(ItemStack itemStack) {
      this.patterns = null;
      if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
         NBTTagCompound var5 = itemStack.getTagCompound().getCompoundTag("BlockEntityTag");
         if (var5.hasKey("Patterns")) {
            this.patterns = (NBTTagList)var5.getTagList("Patterns", 10).copy();
         }

         if (var5.hasKey("Base", 99)) {
            this.baseColor = var5.getInteger("Base");
         } else {
            this.baseColor = itemStack.getMetadata() & 15;
         }
      } else {
         this.baseColor = itemStack.getMetadata() & 15;
      }

      this.patternList = null;
      this.colorList = null;
      this.patternResourceLocation = "";
      this.field_175119_g = true;
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      setBaseColorAndPatterns(nBTTagCompound, this.baseColor, this.patterns);
   }

   public static void setBaseColorAndPatterns(NBTTagCompound nBTTagCompound, int integer, NBTTagList nBTTagList) {
      nBTTagCompound.setInteger("Base", integer);
      if (nBTTagList != null) {
         nBTTagCompound.setTag("Patterns", nBTTagList);
      }
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      this.baseColor = nBTTagCompound.getInteger("Base");
      this.patterns = nBTTagCompound.getTagList("Patterns", 10);
      this.patternList = null;
      this.colorList = null;
      this.patternResourceLocation = null;
      this.field_175119_g = true;
   }

   @Override
   public Packet getDescriptionPacket() {
      NBTTagCompound var3 = new NBTTagCompound();
      this.writeToNBT(var3);
      return new S35PacketUpdateTileEntity(this.pos, 6, var3);
   }

   public int getBaseColor() {
      return this.baseColor;
   }

   public static int getBaseColor(ItemStack itemStack) {
      NBTTagCompound var4 = itemStack.getSubCompound("BlockEntityTag", false);
      return var4 != null && var4.hasKey("Base") ? var4.getInteger("Base") : itemStack.getMetadata();
   }

   public static int getPatterns(ItemStack itemStack) {
      NBTTagCompound var4 = itemStack.getSubCompound("BlockEntityTag", false);
      return var4 != null && var4.hasKey("Patterns") ? var4.getTagList("Patterns", 10).tagCount() : 0;
   }

   public List<TileEntityBanner.EnumBannerPattern> getPatternList() {
      this.initializeBannerData();
      return this.patternList;
   }

   public NBTTagList getPatterns() {
      return this.patterns;
   }

   public List<EnumDyeColor> getColorList() {
      this.initializeBannerData();
      return this.colorList;
   }

   public String getPatternResourceLocation() {
      this.initializeBannerData();
      return this.patternResourceLocation;
   }

   private void initializeBannerData() {
      if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
         if (!this.field_175119_g) {
            this.patternResourceLocation = "";
         } else {
            this.patternList = Lists.newArrayList();
            this.colorList = Lists.newArrayList();
            this.patternList.add(TileEntityBanner.EnumBannerPattern.BASE);
            this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
            this.patternResourceLocation = "b" + this.baseColor;
            if (this.patterns != null) {
               for (int var4 = 0; var4 < this.patterns.tagCount(); var4++) {
                  NBTTagCompound var5 = this.patterns.getCompoundTagAt(var4);
                  TileEntityBanner.EnumBannerPattern var6 = TileEntityBanner.EnumBannerPattern.getPatternByID(var5.getString("Pattern"));
                  if (var6 != null) {
                     this.patternList.add(var6);
                     int var7 = var5.getInteger("Color");
                     this.colorList.add(EnumDyeColor.byDyeDamage(var7));
                     this.patternResourceLocation = this.patternResourceLocation + var6.getPatternID() + var7;
                  }
               }
            }
         }
      }
   }

   public static void removeBannerData(ItemStack itemStack) {
      NBTTagCompound var4 = itemStack.getSubCompound("BlockEntityTag", false);
      if (var4 != null && var4.hasKey("Patterns", 9)) {
         NBTTagList var5 = var4.getTagList("Patterns", 10);
         if (var5.tagCount() > 0) {
            var5.removeTag(var5.tagCount() - 1);
            if (var5.hasNoTags()) {
               itemStack.getTagCompound().removeTag("BlockEntityTag");
               if (itemStack.getTagCompound().hasNoTags()) {
                  itemStack.setTagCompound(null);
               }
            }
         }
      }
   }

   public enum EnumBannerPattern {
      BASE("base", "b"),
      SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
      SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
      SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
      SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
      STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
      STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
      STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
      STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
      STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
      STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
      STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
      STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
      STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
      CROSS("cross", "cr", "# #", " # ", "# #"),
      STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
      TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
      TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
      TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
      TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
      DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
      DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
      DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
      DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
      CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
      RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
      HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
      HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
      HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
      HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
      BORDER("border", "bo", "###", "# #", "###"),
      CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.vine)),
      CREEPER("creeper", "cre", new ItemStack(Items.skull, 1, 4)),
      GRADIENT("gradient", "gra", "# #", " # ", " # "),
      GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
      BRICKS("bricks", "bri", new ItemStack(Blocks.brick_block)),
      SKULL("skull", "sku", new ItemStack(Items.skull, 1, 1)),
      FLOWER("flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())),
      MOJANG("mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));

      private final String patternName;
      private final String patternID;
      private final String[] craftingLayers = new String[3];
      private ItemStack patternCraftingStack;

      EnumBannerPattern(String string3, String string4) {
         this.patternName = string3;
         this.patternID = string4;
      }

      EnumBannerPattern(String string3, String string4, ItemStack itemStack) {
         this(string3, string4);
         this.patternCraftingStack = itemStack;
      }

      EnumBannerPattern(String string3, String string4, String string5, String string6, String string7) {
         this(string3, string4);
         this.craftingLayers[0] = string5;
         this.craftingLayers[1] = string6;
         this.craftingLayers[2] = string7;
      }

      public String getPatternName() {
         return this.patternName;
      }

      public String getPatternID() {
         return this.patternID;
      }

      public String[] getCraftingLayers() {
         return this.craftingLayers;
      }

      public boolean hasValidCrafting() {
         return this.patternCraftingStack != null || this.craftingLayers[0] != null;
      }

      public boolean hasCraftingStack() {
         return this.patternCraftingStack != null;
      }

      public ItemStack getCraftingStack() {
         return this.patternCraftingStack;
      }

      public static TileEntityBanner.EnumBannerPattern getPatternByID(String string) {
         for (TileEntityBanner.EnumBannerPattern var7 : values()) {
            if (var7.patternID.equals(string)) {
               return var7;
            }
         }

         return null;
      }

      private static TileEntityBanner.EnumBannerPattern[] Method372() {
         return new TileEntityBanner.EnumBannerPattern[]{
            BASE,
            SQUARE_BOTTOM_LEFT,
            SQUARE_BOTTOM_RIGHT,
            SQUARE_TOP_LEFT,
            SQUARE_TOP_RIGHT,
            STRIPE_BOTTOM,
            STRIPE_TOP,
            STRIPE_LEFT,
            STRIPE_RIGHT,
            STRIPE_CENTER,
            STRIPE_MIDDLE,
            STRIPE_DOWNRIGHT,
            STRIPE_DOWNLEFT,
            STRIPE_SMALL,
            CROSS,
            STRAIGHT_CROSS,
            TRIANGLE_BOTTOM,
            TRIANGLE_TOP,
            TRIANGLES_BOTTOM,
            TRIANGLES_TOP,
            DIAGONAL_LEFT,
            DIAGONAL_RIGHT,
            DIAGONAL_LEFT_MIRROR,
            DIAGONAL_RIGHT_MIRROR,
            CIRCLE_MIDDLE,
            RHOMBUS_MIDDLE,
            HALF_VERTICAL,
            HALF_HORIZONTAL,
            HALF_VERTICAL_MIRROR,
            HALF_HORIZONTAL_MIRROR,
            BORDER,
            CURLY_BORDER,
            CREEPER,
            GRADIENT,
            GRADIENT_UP,
            BRICKS,
            SKULL,
            FLOWER,
            MOJANG
         };
      }
   }
}
