package com.moon.module.impl.combat.killaura.sort;

import com.moon.module.impl.combat.killaura.Sort;
import java.util.Comparator;
import net.minecraft.entity.Entity;

public class DistanceSort implements Sort {
   @Override
   public Comparator<Entity> comparator() {
      return Comparator.comparingDouble(entity -> client.thePlayer.getDistanceToEntity(entity));
   }
}
