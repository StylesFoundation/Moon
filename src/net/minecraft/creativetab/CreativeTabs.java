package net.minecraft.creativetab;

import java.util.List;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class CreativeTabs {
   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
   public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks") {
      @Override
      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.brick_block);
      }
   };
   public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations") {
      @Override
      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.double_plant);
      }

      @Override
      public int getIconItemDamage() {
         return BlockDoublePlant.EnumPlantType.PAEONIA.getMeta();
      }
   };
   public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone") {
      @Override
      public Item getTabIconItem() {
         return Items.redstone;
      }
   };
   public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation") {
      @Override
      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.golden_rail);
      }
   };
   public static final CreativeTabs tabMisc = (new CreativeTabs(4, "misc") {
      @Override
      public Item getTabIconItem() {
         return Items.lava_bucket;
      }
   }).setRelevantEnchantmentTypes(EnumEnchantmentType.ALL);
   public static final CreativeTabs tabAllSearch = (new CreativeTabs(5, "search") {
      @Override
      public Item getTabIconItem() {
         return Items.compass;
      }
   }).setBackgroundImageName("item_search.png");
   public static final CreativeTabs tabFood = new CreativeTabs(6, "food") {
      @Override
      public Item getTabIconItem() {
         return Items.apple;
      }
   };
   public static final CreativeTabs tabTools = (new CreativeTabs(7, "tools") {
      @Override
      public Item getTabIconItem() {
         return Items.iron_axe;
      }
   }).setRelevantEnchantmentTypes(EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE);
   public static final CreativeTabs tabCombat = (new CreativeTabs(8, "combat") {
         @Override
         public Item getTabIconItem() {
            return Items.golden_sword;
         }
      })
      .setRelevantEnchantmentTypes(
         EnumEnchantmentType.ARMOR,
         EnumEnchantmentType.ARMOR_FEET,
         EnumEnchantmentType.ARMOR_HEAD,
         EnumEnchantmentType.ARMOR_LEGS,
         EnumEnchantmentType.ARMOR_TORSO,
         EnumEnchantmentType.BOW,
         EnumEnchantmentType.WEAPON
      );
   public static final CreativeTabs tabBrewing = new CreativeTabs(9, "brewing") {
      @Override
      public Item getTabIconItem() {
         return Items.potionitem;
      }
   };
   public static final CreativeTabs tabMaterials = new CreativeTabs(10, "materials") {
      @Override
      public Item getTabIconItem() {
         return Items.stick;
      }
   };
   public static final CreativeTabs tabInventory = (new CreativeTabs(11, "inventory") {
      @Override
      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.chest);
      }
   }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
   private final int tabIndex;
   private final String tabLabel;
   private String theTexture = "items.png";
   private boolean hasScrollbar = true;
   private boolean drawTitle = true;
   private EnumEnchantmentType[] enchantmentTypes;
   private ItemStack iconItemStack;

   public CreativeTabs(int integer, String string) {
      this.tabIndex = integer;
      this.tabLabel = string;
      creativeTabArray[integer] = this;
   }

   public int getTabIndex() {
      return this.tabIndex;
   }

   public String getTabLabel() {
      return this.tabLabel;
   }

   public String Method576() {
      return "itemGroup." + this.getTabLabel();
   }

   public ItemStack getIconItemStack() {
      if (this.iconItemStack == null) {
         this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
      }

      return this.iconItemStack;
   }

   public abstract Item getTabIconItem();

   public int getIconItemDamage() {
      return 0;
   }

   public String getBackgroundImageName() {
      return this.theTexture;
   }

   public CreativeTabs setBackgroundImageName(String string) {
      this.theTexture = string;
      return this;
   }

   public boolean drawInForegroundOfTab() {
      return this.drawTitle;
   }

   public CreativeTabs setNoTitle() {
      this.drawTitle = false;
      return this;
   }

   public boolean shouldHidePlayerInventory() {
      return this.hasScrollbar;
   }

   public CreativeTabs setNoScrollbar() {
      this.hasScrollbar = false;
      return this;
   }

   public int getTabColumn() {
      return this.tabIndex % 6;
   }

   public boolean isTabInFirstRow() {
      return this.tabIndex < 6;
   }

   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
      return this.enchantmentTypes;
   }

   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... arr) {
      this.enchantmentTypes = arr;
      return this;
   }

   public boolean hasRelevantEnchantmentType(EnumEnchantmentType enumEnchantmentType) {
      if (this.enchantmentTypes != null) {
         for (EnumEnchantmentType var8 : this.enchantmentTypes) {
            if (var8 == enumEnchantmentType) {
               return true;
            }
         }
      }

      return false;
   }

   public void displayAllReleventItems(List<ItemStack> list) {
      for (Item var6 : Item.itemRegistry) {
         if (var6 != null && var6.getCreativeTab() == this) {
            var6.getSubItems(var6, this, list);
         }
      }

      if (this.getRelevantEnchantmentTypes() != null) {
         this.addEnchantmentBooksToList(list, this.getRelevantEnchantmentTypes());
      }
   }

   public void addEnchantmentBooksToList(List<ItemStack> list, EnumEnchantmentType... arr) {
      for (Enchantment var9 : Enchantment.enchantmentsBookList) {
         if (var9 != null && var9.type != null) {
            boolean var10 = false;

            for (int var11 = 0; var11 < arr.length && !var10; var11++) {
               if (var9.type == arr[var11]) {
                  var10 = true;
                  break;
               }
            }

            if (var10) {
               list.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var9, var9.getMaxLevel())));
            }
         }
      }
   }
}
