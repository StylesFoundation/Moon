package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;

public class CustomModelRegistry {
   private static final Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();

   private static Map<String, ModelAdapter> makeMapModelAdapters() {
      LinkedHashMap var0 = new LinkedHashMap();
      addModelAdapter(var0, new ModelAdapterArmorStand());
      addModelAdapter(var0, new ModelAdapterBat());
      addModelAdapter(var0, new ModelAdapterBlaze());
      addModelAdapter(var0, new ModelAdapterBoat());
      addModelAdapter(var0, new ModelAdapterCaveSpider());
      addModelAdapter(var0, new ModelAdapterChicken());
      addModelAdapter(var0, new ModelAdapterCow());
      addModelAdapter(var0, new ModelAdapterCreeper());
      addModelAdapter(var0, new ModelAdapterDragon());
      addModelAdapter(var0, new ModelAdapterEnderCrystal());
      addModelAdapter(var0, new ModelAdapterEnderman());
      addModelAdapter(var0, new ModelAdapterEndermite());
      addModelAdapter(var0, new ModelAdapterGhast());
      addModelAdapter(var0, new ModelAdapterGuardian());
      addModelAdapter(var0, new ModelAdapterHorse());
      addModelAdapter(var0, new ModelAdapterIronGolem());
      addModelAdapter(var0, new ModelAdapterLeadKnot());
      addModelAdapter(var0, new ModelAdapterMagmaCube());
      addModelAdapter(var0, new ModelAdapterMinecart());
      addModelAdapter(var0, new ModelAdapterMinecartTnt());
      addModelAdapter(var0, new ModelAdapterMinecartMobSpawner());
      addModelAdapter(var0, new ModelAdapterMooshroom());
      addModelAdapter(var0, new ModelAdapterOcelot());
      addModelAdapter(var0, new ModelAdapterPig());
      addModelAdapter(var0, new ModelAdapterPigZombie());
      addModelAdapter(var0, new ModelAdapterRabbit());
      addModelAdapter(var0, new ModelAdapterSheep());
      addModelAdapter(var0, new ModelAdapterSilverfish());
      addModelAdapter(var0, new ModelAdapterSkeleton());
      addModelAdapter(var0, new ModelAdapterSlime());
      addModelAdapter(var0, new ModelAdapterSnowman());
      addModelAdapter(var0, new ModelAdapterSpider());
      addModelAdapter(var0, new ModelAdapterSquid());
      addModelAdapter(var0, new ModelAdapterVillager());
      addModelAdapter(var0, new ModelAdapterWitch());
      addModelAdapter(var0, new ModelAdapterWither());
      addModelAdapter(var0, new ModelAdapterWitherSkull());
      addModelAdapter(var0, new ModelAdapterWolf());
      addModelAdapter(var0, new ModelAdapterZombie());
      addModelAdapter(var0, new ModelAdapterSheepWool());
      addModelAdapter(var0, new ModelAdapterBanner());
      addModelAdapter(var0, new ModelAdapterBook());
      addModelAdapter(var0, new ModelAdapterChest());
      addModelAdapter(var0, new ModelAdapterChestLarge());
      addModelAdapter(var0, new ModelAdapterEnderChest());
      addModelAdapter(var0, new ModelAdapterHeadHumanoid());
      addModelAdapter(var0, new ModelAdapterHeadSkeleton());
      addModelAdapter(var0, new ModelAdapterSign());
      return var0;
   }

   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
      addModelAdapter(map, modelAdapter, modelAdapter.getName());
      String[] var4 = modelAdapter.getAliases();
      if (var4 != null) {
         for (String var8 : var4) {
            addModelAdapter(map, modelAdapter, var8);
         }
      }

      ModelBase var12 = modelAdapter.makeModel();

      for (String var10 : modelAdapter.getModelRendererNames()) {
         ModelRenderer var11 = modelAdapter.getModelRenderer(var12, var10);
         if (var11 == null) {
            Config.Method252("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + var10);
         }
      }
   }

   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String string) {
      if (map.containsKey(string)) {
         Config.Method252("Model adapter already registered for id: " + string + ", class: " + modelAdapter.getEntityClass().getName());
      }

      map.put(string, modelAdapter);
   }

   public static ModelAdapter getModelAdapter(String string) {
      return mapModelAdapters.get(string);
   }

   public static String[] getModelNames() {
      return mapModelAdapters.keySet().toArray(new String[0]);
   }
}
