package com.moon.module.impl.player.nofall;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.NoFall;
import com.moon.module.sub.SubModule;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class VerusNoFall extends SubModule<NoFall> {
   public VerusNoFall() {
      super(NoFall.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && mc.thePlayer.fallDistance >= 2.0F) {
         double roundedY = Math.round(event.getY() / 0.25) * 0.25;
         BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
         ItemStack stoneStack = new ItemStack(Item.getItemFromBlock(Blocks.stone));
         mc.thePlayer.sendQueue.Method754(new C08PacketPlayerBlockPlacement(playerPos, 1, stoneStack, 0.5F, 0.5F, 0.5F));
         mc.thePlayer.sendQueue.Method754(new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), roundedY, event.getZ(), true));
         event.setCancelled(true);
      }
   }
}
