package com.moon.module.impl.combat.killaura;

import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public interface Sort {
   Minecraft client = Minecraft.getMinecraft();

   Comparator<Entity> comparator();
}
