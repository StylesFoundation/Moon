package net.minecraft.realms;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public final class Tezzelator {
   public static final Predicate<Entity> a = Entity::isEntityAlive;
   public static final Predicate<Entity> b = entity -> entity.isEntityAlive() && entity.riddenByEntity == null && entity.ridingEntity == null;
   public static final Predicate<Entity> c = entity -> entity instanceof IInventory && entity.isEntityAlive();
   public static final Predicate<Entity> d = entity -> !(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator();
}
