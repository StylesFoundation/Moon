package com.moon.module.impl.combat.killaura.sort;

import com.moon.module.impl.combat.killaura.Sort;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class HealthSort implements Sort {
   @Override
   public Comparator<Entity> comparator() {
      return Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getHealth());
   }
}
