package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "potion", path = "types/potion")
@Description("Contains information about a potion effect")
public class Potion {
   @Description("The potion id")
   public int id;
   @Description("The potion name (locale)")
   public String name;
   @Description("The duration of the potion effect")
   public int duration;
   @Description("The strength of the potion effect")
   public int amplifier;
   @Description("Whether the effect is bad or not")
   public boolean bad_effect;
   @Description("The potion color (bottle color)")
   public int color;

   @Override
   public String toString() {
      return "Potion(id="
         + this.id
         + ", name="
         + this.name
         + ", duration="
         + this.duration
         + ", amplifier="
         + this.amplifier
         + ", bad_effect="
         + this.bad_effect
         + ", color="
         + this.color
         + ")";
   }
}
