package net.minecraft.entity;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes {
   private static final Logger logger = LogManager.getLogger();
   public static final IAttribute maxHealth = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, 1024.0)
      .setDescription("Max Health")
      .setShouldWatch(true);
   public static final IAttribute followRange = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).setDescription("Follow Range");
   public static final IAttribute knockbackResistance = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0)
      .setDescription("Knockback Resistance");
   public static final IAttribute movementSpeed = new RangedAttribute(null, "generic.movementSpeed", 0.7F, 0.0, 1024.0)
      .setDescription("Movement Speed")
      .setShouldWatch(true);
   public static final IAttribute attackDamage = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, 2048.0);

   public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap baseAttributeMap) {
      NBTTagList var4 = new NBTTagList();

      for (IAttributeInstance var6 : baseAttributeMap.getAllAttributes()) {
         var4.appendTag(writeAttributeInstanceToNBT(var6));
      }

      return var4;
   }

   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance iAttributeInstance) {
      NBTTagCompound var4 = new NBTTagCompound();
      IAttribute var5 = iAttributeInstance.getAttribute();
      var4.setString("Name", var5.getAttributeUnlocalizedName());
      var4.setDouble("Base", iAttributeInstance.getBaseValue());
      Collection<AttributeModifier> var6 = iAttributeInstance.func_111122_c();
      if (var6 != null && !var6.isEmpty()) {
         NBTTagList var7 = new NBTTagList();

         for (AttributeModifier var9 : var6) {
            if (var9.isSaved()) {
               var7.appendTag(writeAttributeModifierToNBT(var9));
            }
         }

         var4.setTag("Modifiers", var7);
      }

      return var4;
   }

   private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier attributeModifier) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setString("Name", attributeModifier.getName());
      var1.setDouble("Amount", attributeModifier.getAmount());
      var1.setInteger("Operation", attributeModifier.getOperation());
      var1.setLong("UUIDMost", attributeModifier.getID().getMostSignificantBits());
      var1.setLong("UUIDLeast", attributeModifier.getID().getLeastSignificantBits());
      return var1;
   }

   public static void setAttributeModifiers(BaseAttributeMap baseAttributeMap, NBTTagList nBTTagList) {
      for (int var5 = 0; var5 < nBTTagList.tagCount(); var5++) {
         NBTTagCompound var6 = nBTTagList.getCompoundTagAt(var5);
         IAttributeInstance var7 = baseAttributeMap.getAttributeInstanceByName(var6.getString("Name"));
         if (var7 != null) {
            applyModifiersToAttributeInstance(var7, var6);
         } else {
            logger.warn("Ignoring unknown attribute '" + var6.getString("Name") + "'");
         }
      }
   }

   private static void applyModifiersToAttributeInstance(IAttributeInstance iAttributeInstance, NBTTagCompound nBTTagCompound) {
      iAttributeInstance.setBaseValue(nBTTagCompound.getDouble("Base"));
      if (nBTTagCompound.hasKey("Modifiers", 9)) {
         NBTTagList var5 = nBTTagCompound.getTagList("Modifiers", 10);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            AttributeModifier var7 = readAttributeModifierFromNBT(var5.getCompoundTagAt(var6));
            if (var7 != null) {
               AttributeModifier var8 = iAttributeInstance.getModifier(var7.getID());
               if (var8 != null) {
                  iAttributeInstance.removeModifier(var8);
               }

               iAttributeInstance.applyModifier(var7);
            }
         }
      }
   }

   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound nBTTagCompound) {
      UUID var1 = new UUID(nBTTagCompound.getLong("UUIDMost"), nBTTagCompound.getLong("UUIDLeast"));

      try {
         return new AttributeModifier(var1, nBTTagCompound.getString("Name"), nBTTagCompound.getDouble("Amount"), nBTTagCompound.getInteger("Operation"));
      } catch (Exception var3) {
         logger.warn("Unable to create attribute: " + var3.getMessage());
         return null;
      }
   }

   public static int Method738() {
      return 122;
   }
}
