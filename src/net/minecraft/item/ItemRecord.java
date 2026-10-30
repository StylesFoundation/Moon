package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRecord extends Item {
   private static final Map<String, ItemRecord> RECORDS = Maps.newHashMap();
   public final String recordName;

   protected ItemRecord(String string) {
      this.recordName = string;
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabMisc);
      RECORDS.put("records." + string, this);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      IBlockState var12 = world.getBlockState(blockPos);
      if (var12.getBlock() == Blocks.jukebox && !var12.getValue(BlockJukebox.HAS_RECORD)) {
         if (!world.isRemote) {
            ((BlockJukebox)Blocks.jukebox).insertRecord(world, blockPos, var12, itemStack);
            world.playAuxSFXAtEntity(null, 1005, blockPos, Item.getIdFromItem(this));
            itemStack.stackSize--;
            entityPlayer.triggerAchievement(StatList.field_181740_X);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List<String> list, boolean boolean4) {
      list.add(this.getRecordNameLocal());
   }

   public String getRecordNameLocal() {
      return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
   }

   @Override
   public EnumRarity getRarity(ItemStack itemStack) {
      return EnumRarity.RARE;
   }

   public static ItemRecord getRecord(String string) {
      return RECORDS.get(string);
   }
}
