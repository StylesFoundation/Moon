package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "block", path = "types/block")
@Description("Contains information about a block retrieved from the local world")
public class Block {
   @Description("The name of the block")
   public String name;
   @Description("The display name of the block")
   public String display_name;
   @Description("Whether the block has a full bounding box or not")
   public boolean full_block;
   @Description("Whether the block is translucent or not (used while rendering)")
   public boolean translucent;
   @Description("The hardness of the block")
   public float block_hardness;
   @Description("The relative hardness of the block")
   public float relative_hardness;
   @Description("The slipperiness of the block")
   public float slipperiness;

   public Block(String name, String displayName, boolean fullBlock, boolean translucent, float blockHardness, float relativeHardness, float slipperiness) {
      this.name = name;
      this.display_name = displayName;
      this.full_block = fullBlock;
      this.translucent = translucent;
      this.block_hardness = blockHardness;
      this.slipperiness = slipperiness;
      this.relative_hardness = relativeHardness;
   }

   public Block() {
   }

   @Override
   public String toString() {
      return "Block(name="
         + this.name
         + ", display_name="
         + this.display_name
         + ", full_block="
         + this.full_block
         + ", translucent="
         + this.translucent
         + ", block_hardness="
         + this.block_hardness
         + ", relative_hardness="
         + this.relative_hardness
         + ", slipperiness="
         + this.slipperiness
         + ")";
   }
}
