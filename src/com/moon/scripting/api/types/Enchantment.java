package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "enchantment", path = "types/enchantment")
@Description("Contains information about an enchantment")
public class Enchantment {
   @Description("The id of the enchantment")
   public int id;
   @Description("The level of the enchantment")
   public int level;

   @Override
   public String toString() {
      return "Enchantment(id=" + this.id + ", level=" + this.level + ")";
   }
}
