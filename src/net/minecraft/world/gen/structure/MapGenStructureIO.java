package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapGenStructureIO {
   private static final Logger logger = LogManager.getLogger();
   private static final Map<String, Class<? extends StructureStart>> startNameToClassMap = Maps.newHashMap();
   private static final Map<Class<? extends StructureStart>, String> startClassToNameMap = Maps.newHashMap();
   private static final Map<String, Class<? extends StructureComponent>> componentNameToClassMap = Maps.newHashMap();
   private static final Map<Class<? extends StructureComponent>, String> componentClassToNameMap = Maps.newHashMap();

   private static void registerStructure(Class<? extends StructureStart> class1, String string) {
      startNameToClassMap.put(string, class1);
      startClassToNameMap.put(class1, string);
   }

   static void registerStructureComponent(Class<? extends StructureComponent> class1, String string) {
      componentNameToClassMap.put(string, class1);
      componentClassToNameMap.put(class1, string);
   }

   public static String getStructureStartName(StructureStart structureStart) {
      return startClassToNameMap.get(structureStart.getClass());
   }

   public static String getStructureComponentName(StructureComponent structureComponent) {
      return componentClassToNameMap.get(structureComponent.getClass());
   }

   public static StructureStart getStructureStart(NBTTagCompound nBTTagCompound, World world) {
      StructureStart var5 = null;

      try {
         Class var6 = startNameToClassMap.get(nBTTagCompound.getString("id"));
         if (var6 != null) {
            var5 = (StructureStart)var6.newInstance();
         }
      } catch (Exception var7) {
         logger.warn("Failed Start with id " + nBTTagCompound.getString("id"));
         var7.printStackTrace();
      }

      if (var5 != null) {
         var5.readStructureComponentsFromNBT(world, nBTTagCompound);
      } else {
         logger.warn("Skipping Structure with id " + nBTTagCompound.getString("id"));
      }

      return var5;
   }

   public static StructureComponent getStructureComponent(NBTTagCompound nBTTagCompound, World world) {
      StructureComponent var5 = null;

      try {
         Class var6 = componentNameToClassMap.get(nBTTagCompound.getString("id"));
         if (var6 != null) {
            var5 = (StructureComponent)var6.newInstance();
         }
      } catch (Exception var7) {
         logger.warn("Failed Piece with id " + nBTTagCompound.getString("id"));
         var7.printStackTrace();
      }

      if (var5 != null) {
         var5.readStructureBaseNBT(world, nBTTagCompound);
      } else {
         logger.warn("Skipping Piece with id " + nBTTagCompound.getString("id"));
      }

      return var5;
   }

   static {
      registerStructure(StructureMineshaftStart.class, "Mineshaft");
      registerStructure(MapGenVillage.Start.class, "Village");
      registerStructure(MapGenNetherBridge.Start.class, "Fortress");
      registerStructure(MapGenStronghold.Start.class, "Stronghold");
      registerStructure(MapGenScatteredFeature.Start.class, "Temple");
      registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
      StructureMineshaftPieces.registerStructurePieces();
      StructureVillagePieces.registerVillagePieces();
      StructureNetherBridgePieces.registerNetherFortressPieces();
      StructureStrongholdPieces.registerStrongholdPieces();
      ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
      StructureOceanMonumentPieces.registerOceanMonumentPieces();
   }
}
