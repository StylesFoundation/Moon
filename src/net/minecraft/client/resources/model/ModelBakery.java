package net.minecraft.client.resources.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ITransformation;
import net.minecraftforge.fml.common.registry.RegistryDelegate;
import net.optifine.CustomItems;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet(
      new ResourceLocation[]{
         new ResourceLocation("blocks/water_flow"),
         new ResourceLocation("blocks/water_still"),
         new ResourceLocation("blocks/lava_flow"),
         new ResourceLocation("blocks/lava_still"),
         new ResourceLocation("blocks/destroy_stage_0"),
         new ResourceLocation("blocks/destroy_stage_1"),
         new ResourceLocation("blocks/destroy_stage_2"),
         new ResourceLocation("blocks/destroy_stage_3"),
         new ResourceLocation("blocks/destroy_stage_4"),
         new ResourceLocation("blocks/destroy_stage_5"),
         new ResourceLocation("blocks/destroy_stage_6"),
         new ResourceLocation("blocks/destroy_stage_7"),
         new ResourceLocation("blocks/destroy_stage_8"),
         new ResourceLocation("blocks/destroy_stage_9"),
         new ResourceLocation("items/empty_armor_slot_helmet"),
         new ResourceLocation("items/empty_armor_slot_chestplate"),
         new ResourceLocation("items/empty_armor_slot_leggings"),
         new ResourceLocation("items/empty_armor_slot_boots")
      }
   );
   private static final Logger LOGGER = LogManager.getLogger();
   protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
   private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap();
   private static final Joiner JOINER = Joiner.on(" -> ");
   private final IResourceManager resourceManager;
   private final Map<ResourceLocation, TextureAtlasSprite> sprites = Maps.newHashMap();
   private final Map<ResourceLocation, ModelBlock> models = Maps.newLinkedHashMap();
   private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants = Maps.newLinkedHashMap();
   private final TextureMap textureMap;
   private final BlockModelShapes blockModelShapes;
   private final FaceBakery faceBakery = new FaceBakery();
   private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
   private final RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry = new RegistrySimple<>();
   private static final ModelBlock MODEL_GENERATED = ModelBlock.deserialize(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock MODEL_COMPASS = ModelBlock.deserialize(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock MODEL_CLOCK = ModelBlock.deserialize(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private static final ModelBlock MODEL_ENTITY = ModelBlock.deserialize(
      "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}"
   );
   private final Map<String, ResourceLocation> itemLocations = Maps.newLinkedHashMap();
   private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions = Maps.newHashMap();
   private final Map<Item, List<String>> variantNames = Maps.newIdentityHashMap();
   private static final Map<RegistryDelegate<Item>, Set<String>> customVariantNames = Maps.newHashMap();

   public ModelBakery(IResourceManager iResourceManager, TextureMap textureMap, BlockModelShapes blockModelShapes) {
      this.resourceManager = iResourceManager;
      this.textureMap = textureMap;
      this.blockModelShapes = blockModelShapes;
   }

   public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
      this.loadVariantItemModels();
      this.loadModelsCheck();
      this.loadSprites();
      this.bakeItemModels();
      this.bakeBlockModels();
      return this.bakedRegistry;
   }

   private void loadVariantItemModels() {
      this.Method217(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
      this.variants
         .put(
            MODEL_MISSING,
            new ModelBlockDefinition.Variants(
               MODEL_MISSING.getVariant(),
               Lists.newArrayList(
                  new ModelBlockDefinition.Variant[]{
                     new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1)
                  }
               )
            )
         );
      ResourceLocation var1 = new ResourceLocation("item_frame");
      ModelBlockDefinition var2 = this.getModelBlockDefinition(var1);
      this.registerVariant(var2, new ModelResourceLocation(var1, "normal"));
      this.registerVariant(var2, new ModelResourceLocation(var1, "map"));
      this.loadVariantModels();
      this.loadItemModels();
   }

   private void Method217(Collection<ModelResourceLocation> collection) {
      for (ModelResourceLocation var6 : collection) {
         try {
            ModelBlockDefinition var7 = this.getModelBlockDefinition(var6);

            try {
               this.registerVariant(var7, var6);
            } catch (Exception var9) {
               LOGGER.warn("Unable to load variant: " + var6.getVariant() + " from " + var6, var9);
            }
         } catch (Exception var10) {
            LOGGER.warn("Unable to load definition " + var6, var10);
         }
      }
   }

   private void registerVariant(ModelBlockDefinition modelBlockDefinition, ModelResourceLocation modelResourceLocation) {
      this.variants.put(modelResourceLocation, modelBlockDefinition.getVariants(modelResourceLocation.getVariant()));
   }

   private ModelBlockDefinition getModelBlockDefinition(ResourceLocation resourceLocation) {
      ResourceLocation var5 = this.Method218(resourceLocation);
      ModelBlockDefinition var6 = this.blockDefinitions.get(var5);
      if (var6 == null) {
         List<ModelBlockDefinition> var7 = Lists.newArrayList();

         try {
            for (IResource var9 : this.resourceManager.getAllResources(var5)) {
               InputStream var10 = null;

               try {
                  var10 = var9.getInputStream();
                  ModelBlockDefinition var11 = ModelBlockDefinition.parseFromReader(new InputStreamReader(var10, Charsets.UTF_8));
                  var7.add(var11);
               } catch (Exception var16) {
                  throw new RuntimeException(
                     "Encountered an exception when loading model definition of '"
                        + resourceLocation
                        + "' from: '"
                        + var9.getResourceLocation()
                        + "' in resourcepack: '"
                        + var9.getResourcePackName()
                        + "'",
                     var16
                  );
               } finally {
                  IOUtils.closeQuietly(var10);
               }
            }
         } catch (IOException var18) {
            throw new RuntimeException("Encountered an exception when loading model definition of model " + var5, var18);
         }

         var6 = new ModelBlockDefinition(var7);
         this.blockDefinitions.put(var5, var6);
      }

      return var6;
   }

   private ResourceLocation Method218(ResourceLocation resourceLocation) {
      return new ResourceLocation(resourceLocation.getResourceDomain(), "blockstates/" + resourceLocation.getResourcePath() + ".json");
   }

   private void loadVariantModels() {
      for (ModelResourceLocation var5 : this.variants.keySet()) {
         Iterator var6 = this.variants.get(var5).getVariants().iterator();

         while (var6.hasNext()) {
            ResourceLocation var8 = ((ModelBlockDefinition.Variant)var6.next()).getModelLocation();
            if (this.models.get(var8) == null) {
               try {
                  ModelBlock var9 = this.loadModel(var8);
                  this.models.put(var8, var9);
               } catch (Exception var10) {
                  LOGGER.warn("Unable to load block model: '" + var8 + "' for variant: '" + var5 + "'", var10);
               }
            }
         }
      }
   }

   private ModelBlock loadModel(ResourceLocation resourceLocation) throws IOException {
      String var5 = resourceLocation.getResourcePath();
      if ("builtin/generated".equals(var5)) {
         return MODEL_GENERATED;
      }

      if ("builtin/compass".equals(var5)) {
         return MODEL_COMPASS;
      }

      if ("builtin/clock".equals(var5)) {
         return MODEL_CLOCK;
      }

      if ("builtin/entity".equals(var5)) {
         return MODEL_ENTITY;
      }

      Reader var6;
      if (var5.startsWith("builtin/")) {
         String var7 = var5.substring(8);
         String var8 = BUILT_IN_MODELS.get(var7);
         if (var8 == null) {
            throw new FileNotFoundException(resourceLocation.toString());
         }

         var6 = new StringReader(var8);
      } else {
         resourceLocation = this.getModelLocation(resourceLocation);
         IResource var13 = this.resourceManager.getResource(resourceLocation);
         var6 = new InputStreamReader(var13.getInputStream(), Charsets.UTF_8);
      }

      ModelBlock var14;
      try {
         ModelBlock var15 = ModelBlock.deserialize(var6);
         var15.name = resourceLocation.toString();
         var14 = var15;
         String var9 = TextureUtils.getBasePath(resourceLocation.getResourcePath());
         fixModelLocations(var15, var9);
      } finally {
         var6.close();
      }

      return var14;
   }

   private ResourceLocation getModelLocation(ResourceLocation resourceLocation) {
      ResourceLocation var5 = resourceLocation;
      String var6 = resourceLocation.getResourcePath();
      if (!var6.startsWith("mcpatcher") && !var6.startsWith("optifine")) {
         return new ResourceLocation(resourceLocation.getResourceDomain(), "models/" + resourceLocation.getResourcePath() + ".json");
      }

      if (!var6.endsWith(".json")) {
         var5 = new ResourceLocation(resourceLocation.getResourceDomain(), var6 + ".json");
      }

      return var5;
   }

   private void loadItemModels() {
      this.registerVariantNames();

      for (Item var5 : Item.itemRegistry) {
         for (String var7 : this.getVariantNames(var5)) {
            ResourceLocation var8 = this.Method219(var7);
            this.itemLocations.put(var7, var8);
            if (this.models.get(var8) == null) {
               try {
                  ModelBlock var9 = this.loadModel(var8);
                  this.models.put(var8, var9);
               } catch (Exception var10) {
                  LOGGER.warn("Unable to load item model: '" + var8 + "' for item: '" + Item.itemRegistry.getNameForObject(var5) + "'", var10);
               }
            }
         }
      }
   }

   public void loadItemModel(String string, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3) {
      this.itemLocations.put(string, resourceLocation2);
      if (this.models.get(resourceLocation2) == null) {
         try {
            ModelBlock var7 = this.loadModel(resourceLocation2);
            this.models.put(resourceLocation2, var7);
         } catch (Exception var8) {
            LOGGER.warn("Unable to load item model: '{}' for item: '{}'", resourceLocation2, resourceLocation3);
            LOGGER.warn(var8.getClass().getName() + ": " + var8.getMessage());
         }
      }
   }

   private void registerVariantNames() {
      this.variantNames.clear();
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stone),
            Lists.newArrayList(new String[]{"stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth"})
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList(new String[]{"dirt", "coarse_dirt", "podzol"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.planks),
            Lists.newArrayList(new String[]{"oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks"})
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.sapling),
            Lists.newArrayList(new String[]{"oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling"})
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.sand), Lists.newArrayList(new String[]{"sand", "red_sand"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList(new String[]{"oak_log", "spruce_log", "birch_log", "jungle_log"}));
      this.variantNames
         .put(Item.getItemFromBlock(Blocks.leaves), Lists.newArrayList(new String[]{"oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList(new String[]{"sponge", "sponge_wet"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList(new String[]{"sandstone", "chiseled_sandstone", "smooth_sandstone"}));
      this.variantNames
         .put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList(new String[]{"red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.tallgrass), Lists.newArrayList(new String[]{"dead_bush", "tall_grass", "fern"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.deadbush), Lists.newArrayList(new String[]{"dead_bush"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.wool),
            Lists.newArrayList(
               new String[]{
                  "black_wool",
                  "red_wool",
                  "green_wool",
                  "brown_wool",
                  "blue_wool",
                  "purple_wool",
                  "cyan_wool",
                  "silver_wool",
                  "gray_wool",
                  "pink_wool",
                  "lime_wool",
                  "yellow_wool",
                  "light_blue_wool",
                  "magenta_wool",
                  "orange_wool",
                  "white_wool"
               }
            )
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.yellow_flower), Lists.newArrayList(new String[]{"dandelion"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.red_flower),
            Lists.newArrayList(
               new String[]{"poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"}
            )
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stone_slab),
            Lists.newArrayList(
               new String[]{"stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"}
            )
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab2), Lists.newArrayList(new String[]{"red_sandstone_slab"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stained_glass),
            Lists.newArrayList(
               new String[]{
                  "black_stained_glass",
                  "red_stained_glass",
                  "green_stained_glass",
                  "brown_stained_glass",
                  "blue_stained_glass",
                  "purple_stained_glass",
                  "cyan_stained_glass",
                  "silver_stained_glass",
                  "gray_stained_glass",
                  "pink_stained_glass",
                  "lime_stained_glass",
                  "yellow_stained_glass",
                  "light_blue_stained_glass",
                  "magenta_stained_glass",
                  "orange_stained_glass",
                  "white_stained_glass"
               }
            )
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.monster_egg),
            Lists.newArrayList(
               new String[]{
                  "stone_monster_egg",
                  "cobblestone_monster_egg",
                  "stone_brick_monster_egg",
                  "mossy_brick_monster_egg",
                  "cracked_brick_monster_egg",
                  "chiseled_brick_monster_egg"
               }
            )
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stonebrick),
            Lists.newArrayList(new String[]{"stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick"})
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.wooden_slab),
            Lists.newArrayList(new String[]{"oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab"})
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList(new String[]{"cobblestone_wall", "mossy_cobblestone_wall"}));
      this.variantNames
         .put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList(new String[]{"anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"}));
      this.variantNames
         .put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList(new String[]{"quartz_block", "chiseled_quartz_block", "quartz_column"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stained_hardened_clay),
            Lists.newArrayList(
               new String[]{
                  "black_stained_hardened_clay",
                  "red_stained_hardened_clay",
                  "green_stained_hardened_clay",
                  "brown_stained_hardened_clay",
                  "blue_stained_hardened_clay",
                  "purple_stained_hardened_clay",
                  "cyan_stained_hardened_clay",
                  "silver_stained_hardened_clay",
                  "gray_stained_hardened_clay",
                  "pink_stained_hardened_clay",
                  "lime_stained_hardened_clay",
                  "yellow_stained_hardened_clay",
                  "light_blue_stained_hardened_clay",
                  "magenta_stained_hardened_clay",
                  "orange_stained_hardened_clay",
                  "white_stained_hardened_clay"
               }
            )
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.stained_glass_pane),
            Lists.newArrayList(
               new String[]{
                  "black_stained_glass_pane",
                  "red_stained_glass_pane",
                  "green_stained_glass_pane",
                  "brown_stained_glass_pane",
                  "blue_stained_glass_pane",
                  "purple_stained_glass_pane",
                  "cyan_stained_glass_pane",
                  "silver_stained_glass_pane",
                  "gray_stained_glass_pane",
                  "pink_stained_glass_pane",
                  "lime_stained_glass_pane",
                  "yellow_stained_glass_pane",
                  "light_blue_stained_glass_pane",
                  "magenta_stained_glass_pane",
                  "orange_stained_glass_pane",
                  "white_stained_glass_pane"
               }
            )
         );
      this.variantNames.put(Item.getItemFromBlock(Blocks.leaves2), Lists.newArrayList(new String[]{"acacia_leaves", "dark_oak_leaves"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList(new String[]{"acacia_log", "dark_oak_log"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList(new String[]{"prismarine", "prismarine_bricks", "dark_prismarine"}));
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.carpet),
            Lists.newArrayList(
               new String[]{
                  "black_carpet",
                  "red_carpet",
                  "green_carpet",
                  "brown_carpet",
                  "blue_carpet",
                  "purple_carpet",
                  "cyan_carpet",
                  "silver_carpet",
                  "gray_carpet",
                  "pink_carpet",
                  "lime_carpet",
                  "yellow_carpet",
                  "light_blue_carpet",
                  "magenta_carpet",
                  "orange_carpet",
                  "white_carpet"
               }
            )
         );
      this.variantNames
         .put(
            Item.getItemFromBlock(Blocks.double_plant),
            Lists.newArrayList(new String[]{"sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia"})
         );
      this.variantNames.put(Items.bow, Lists.newArrayList(new String[]{"bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2"}));
      this.variantNames.put(Items.coal, Lists.newArrayList(new String[]{"coal", "charcoal"}));
      this.variantNames.put(Items.fishing_rod, Lists.newArrayList(new String[]{"fishing_rod", "fishing_rod_cast"}));
      this.variantNames.put(Items.fish, Lists.newArrayList(new String[]{"cod", "salmon", "clownfish", "pufferfish"}));
      this.variantNames.put(Items.cooked_fish, Lists.newArrayList(new String[]{"cooked_cod", "cooked_salmon"}));
      this.variantNames
         .put(
            Items.dye,
            Lists.newArrayList(
               new String[]{
                  "dye_black",
                  "dye_red",
                  "dye_green",
                  "dye_brown",
                  "dye_blue",
                  "dye_purple",
                  "dye_cyan",
                  "dye_silver",
                  "dye_gray",
                  "dye_pink",
                  "dye_lime",
                  "dye_yellow",
                  "dye_light_blue",
                  "dye_magenta",
                  "dye_orange",
                  "dye_white"
               }
            )
         );
      this.variantNames.put(Items.potionitem, Lists.newArrayList(new String[]{"bottle_drinkable", "bottle_splash"}));
      this.variantNames.put(Items.skull, Lists.newArrayList(new String[]{"skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList(new String[]{"oak_fence_gate"}));
      this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList(new String[]{"oak_fence"}));
      this.variantNames.put(Items.oak_door, Lists.newArrayList(new String[]{"oak_door"}));

      for (Entry var5 : customVariantNames.entrySet()) {
         this.variantNames.put((Item)((RegistryDelegate)var5.getKey()).get(), Lists.newArrayList(((Set)var5.getValue()).iterator()));
      }

      CustomItems.update();
      CustomItems.loadModels(this);
   }

   private List<String> getVariantNames(Item item) {
      List var5 = this.variantNames.get(item);
      if (var5 == null) {
         var5 = Collections.singletonList(Item.itemRegistry.getNameForObject(item).toString());
      }

      return var5;
   }

   private ResourceLocation Method219(String string) {
      ResourceLocation var2 = new ResourceLocation(string);
      return new ResourceLocation(var2.getResourceDomain(), "item/" + var2.getResourcePath());
   }

   private void bakeBlockModels() {
      for (ModelResourceLocation var5 : this.variants.keySet()) {
         WeightedBakedModel.Builder var6 = new WeightedBakedModel.Builder();
         int var7 = 0;

         for (ModelBlockDefinition.Variant var9 : this.variants.get(var5).getVariants()) {
            ModelBlock var10 = this.models.get(var9.getModelLocation());
            if (var10 != null && var10.isResolved()) {
               var7++;
               var6.add(this.bakeModel(var10, var9.getRotation(), var9.isUvLocked()), var9.getWeight());
            } else {
               LOGGER.warn("Missing model for: " + var5);
            }
         }

         if (var7 == 0) {
            LOGGER.warn("No weighted models for: " + var5);
         } else if (var7 == 1) {
            this.bakedRegistry.putObject(var5, var6.first());
         } else {
            this.bakedRegistry.putObject(var5, var6.build());
         }
      }

      for (Entry var12 : this.itemLocations.entrySet()) {
         ResourceLocation var13 = (ResourceLocation)var12.getValue();
         ModelResourceLocation var14 = new ModelResourceLocation((String)var12.getKey(), "inventory");
         ModelBlock var15 = this.models.get(var13);
         if (var15 == null || !var15.isResolved()) {
            LOGGER.warn("Missing model for: " + var13);
         } else if (this.isCustomRenderer(var15)) {
            this.bakedRegistry.putObject(var14, new BuiltInModel(var15.getAllTransforms()));
         } else {
            this.bakedRegistry.putObject(var14, this.bakeModel(var15, ModelRotation.X0_Y0, false));
         }
      }
   }

   private Set<ResourceLocation> Method220() {
      HashSet var4 = Sets.newHashSet();
      List<ModelResourceLocation> var5 = Lists.newArrayList(this.variants.keySet());
      var5.sort(Comparator.comparing(ModelResourceLocation::toString));

      for (ModelResourceLocation var7 : var5) {
         ModelBlockDefinition.Variants var8 = this.variants.get(var7);

         for (ModelBlockDefinition.Variant var10 : var8.getVariants()) {
            ModelBlock var11 = this.models.get(var10.getModelLocation());
            if (var11 == null) {
               LOGGER.warn("Missing model for: " + var7);
            } else {
               var4.addAll(this.getTextureLocations(var11));
            }
         }
      }

      var4.addAll(LOCATIONS_BUILTIN_TEXTURES);
      return var4;
   }

   public IBakedModel bakeModel(ModelBlock modelBlock, ModelRotation modelRotation, boolean boolean3) {
      return this.bakeModel(modelBlock, (ITransformation)modelRotation, boolean3);
   }

   protected IBakedModel bakeModel(ModelBlock modelBlock, ITransformation iTransformation, boolean boolean3) {
      TextureAtlasSprite var7 = this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName("particle")));
      SimpleBakedModel.Builder var8 = new SimpleBakedModel.Builder(modelBlock).setTexture(var7);

      for (BlockPart var10 : modelBlock.getElements()) {
         for (EnumFacing var12 : var10.mapFaces.keySet()) {
            BlockPartFace var13 = var10.mapFaces.get(var12);
            TextureAtlasSprite var14 = this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName(var13.texture)));
            if (var13.cullFace != null) {
               var8.addFaceQuad(iTransformation.rotate(var13.cullFace), this.makeBakedQuad(var10, var13, var14, var12, iTransformation, boolean3));
            } else {
               var8.addGeneralQuad(this.makeBakedQuad(var10, var13, var14, var12, iTransformation, boolean3));
            }
         }
      }

      return var8.makeBakedModel();
   }

   private BakedQuad Method221(
      BlockPart blockPart,
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      ModelRotation modelRotation,
      boolean boolean6
   ) {
      return this.faceBakery
         .makeBakedQuad(
            blockPart.positionFrom,
            blockPart.positionTo,
            blockPartFace,
            textureAtlasSprite,
            enumFacing,
            modelRotation,
            blockPart.partRotation,
            boolean6,
            blockPart.shade
         );
   }

   protected BakedQuad makeBakedQuad(
      BlockPart blockPart,
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      ITransformation iTransformation,
      boolean boolean6
   ) {
      return this.faceBakery
         .makeBakedQuad(
            blockPart.positionFrom,
            blockPart.positionTo,
            blockPartFace,
            textureAtlasSprite,
            enumFacing,
            iTransformation,
            blockPart.partRotation,
            boolean6,
            blockPart.shade
         );
   }

   private void loadModelsCheck() {
      this.loadModels();

      for (ModelBlock var5 : this.models.values()) {
         var5.getParentFromMap(this.models);
      }

      ModelBlock.checkModelHierarchy(this.models);
   }

   private void loadModels() {
      ArrayDeque var4 = Queues.newArrayDeque();
      HashSet var5 = Sets.newHashSet();

      for (ResourceLocation var7 : this.models.keySet()) {
         var5.add(var7);
         ResourceLocation var8 = this.models.get(var7).getParentLocation();
         if (var8 != null) {
            var4.add(var8);
         }
      }

      while (!var4.isEmpty()) {
         ResourceLocation var10 = (ResourceLocation)var4.pop();

         try {
            if (this.models.get(var10) != null) {
               continue;
            }

            ModelBlock var11 = this.loadModel(var10);
            this.models.put(var10, var11);
            ResourceLocation var12 = var11.getParentLocation();
            if (var12 != null && !var5.contains(var12)) {
               var4.add(var12);
            }
         } catch (Exception var9) {
            LOGGER.warn("In parent chain: " + JOINER.join(this.getParentPath(var10)) + "; unable to load model: '" + var10 + "'");
         }

         var5.add(var10);
      }
   }

   private List<ResourceLocation> getParentPath(ResourceLocation resourceLocation) {
      ArrayList var5 = Lists.newArrayList(new ResourceLocation[]{resourceLocation});
      ResourceLocation var6 = resourceLocation;

      while ((var6 = this.getParentLocation(var6)) != null) {
         var5.add(0, var6);
      }

      return var5;
   }

   private ResourceLocation getParentLocation(ResourceLocation resourceLocation) {
      for (Entry var6 : this.models.entrySet()) {
         ModelBlock var7 = (ModelBlock)var6.getValue();
         if (var7 != null && resourceLocation.equals(var7.getParentLocation())) {
            return (ResourceLocation)var6.getKey();
         }
      }

      return null;
   }

   private Set<ResourceLocation> getTextureLocations(ModelBlock modelBlock) {
      HashSet var5 = Sets.newHashSet();

      for (BlockPart var7 : modelBlock.getElements()) {
         for (BlockPartFace var9 : var7.mapFaces.values()) {
            ResourceLocation var10 = new ResourceLocation(modelBlock.resolveTextureName(var9.texture));
            var5.add(var10);
         }
      }

      var5.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
      return var5;
   }

   private void loadSprites() {
      Set<ResourceLocation> var1 = this.Method220();
      var1.addAll(this.getItemsTextureLocations());
      var1.remove(TextureMap.LOCATION_MISSING_TEXTURE);
      IIconCreator var2 = textureMap -> {
         for (ResourceLocation var7 : var1) {
            TextureAtlasSprite var8 = textureMap.registerSprite(var7);
            this.sprites.put(var7, var8);
         }
      };
      this.textureMap.loadSprites(this.resourceManager, var2);
      this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
   }

   private Set<ResourceLocation> getItemsTextureLocations() {
      HashSet var4 = Sets.newHashSet();

      for (ResourceLocation var6 : this.itemLocations.values()) {
         ModelBlock var7 = this.models.get(var6);
         if (var7 != null) {
            var4.add(new ResourceLocation(var7.resolveTextureName("particle")));
            if (this.hasItemModel(var7)) {
               for (String var9 : ItemModelGenerator.LAYERS) {
                  ResourceLocation var14 = new ResourceLocation(var7.resolveTextureName(var9));
                  if (var7.getRootModel() == MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(var14)) {
                     TextureAtlasSprite.setLocationNameCompass(var14.toString());
                  } else if (var7.getRootModel() == MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(var14)) {
                     TextureAtlasSprite.setLocationNameClock(var14.toString());
                  }

                  var4.add(var14);
               }
            } else if (!this.isCustomRenderer(var7)) {
               Iterator var8 = var7.getElements().iterator();

               while (var8.hasNext()) {
                  for (BlockPartFace var11 : ((BlockPart)var8.next()).mapFaces.values()) {
                     ResourceLocation var12 = new ResourceLocation(var7.resolveTextureName(var11.texture));
                     var4.add(var12);
                  }
               }
            }
         }
      }

      return var4;
   }

   private boolean hasItemModel(ModelBlock modelBlock) {
      if (modelBlock == null) {
         return false;
      }

      ModelBlock var5 = modelBlock.getRootModel();
      return var5 == MODEL_GENERATED || var5 == MODEL_COMPASS || var5 == MODEL_CLOCK;
   }

   private boolean isCustomRenderer(ModelBlock modelBlock) {
      if (modelBlock == null) {
         return false;
      }

      ModelBlock var5 = modelBlock.getRootModel();
      return var5 == MODEL_ENTITY;
   }

   private void bakeItemModels() {
      for (ResourceLocation var5 : this.itemLocations.values()) {
         ModelBlock var6 = this.models.get(var5);
         if (this.hasItemModel(var6)) {
            ModelBlock var7 = this.makeItemModel(var6);
            if (var7 != null) {
               var7.name = var5.toString();
            }

            this.models.put(var5, var7);
         } else if (this.isCustomRenderer(var6)) {
            this.models.put(var5, var6);
         }
      }

      for (TextureAtlasSprite var9 : this.sprites.values()) {
         if (!var9.hasAnimationMetadata()) {
            var9.clearFramesTextureData();
         }
      }
   }

   private ModelBlock makeItemModel(ModelBlock modelBlock) {
      return this.itemModelGenerator.makeItemModel(this.textureMap, modelBlock);
   }

   public ModelBlock getModelBlock(ResourceLocation resourceLocation) {
      return this.models.get(resourceLocation);
   }

   public static void fixModelLocations(ModelBlock modelBlock, String string) {
      ResourceLocation var5 = fixModelLocation(modelBlock.getParentLocation(), string);
      if (var5 != modelBlock.getParentLocation()) {
         modelBlock.parentLocation = var5;
      }

      Map<String, String> var6 = modelBlock.textures;
      if (var6 != null) {
         for (Entry<String, String> var8 : var6.entrySet()) {
            String var9 = (String)var8.getValue();
            String var10 = fixResourcePath(var9, string);
            if (var10 != var9) {
               var8.setValue(var10);
            }
         }
      }
   }

   public static ResourceLocation fixModelLocation(ResourceLocation resourceLocation, String string) {
      if (resourceLocation != null && string != null && resourceLocation.getResourceDomain().equals("minecraft")) {
         String var5 = resourceLocation.getResourcePath();
         String var6 = fixResourcePath(var5, string);
         if (var6 != var5) {
            resourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(), var6);
         }
      }

      return resourceLocation;
   }

   private static String fixResourcePath(String string1, String string2) {
      return StrUtils.removeSuffix(StrUtils.removeSuffix(TextureUtils.fixResourcePath(string1, string2), ".json"), ".png");
   }

   @Deprecated
   public static void Method222(Item item, String... arr) {
   }

   public static <T extends ResourceLocation> void Method223(Item item, T... arr) {
   }

   static {
      BUILT_IN_MODELS.put(
         "missing",
         "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}"
      );
      MODEL_GENERATED.name = "generation marker";
      MODEL_COMPASS.name = "compass generation marker";
      MODEL_CLOCK.name = "class generation marker";
      MODEL_ENTITY.name = "block entity marker";
   }
}
