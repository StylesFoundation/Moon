package com.moon.scripting.converter;

import com.moon.scripting.api.types.Block;
import com.moon.scripting.api.types.Enchantment;
import com.moon.scripting.api.types.Item;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.api.types.RayCastResult;
import com.moon.scripting.api.types.render.TemplateRenderBatch;
import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class LuaConverterRegistry {
   private static final Map<Class<?>, LuaConverter<?>> CONVERTERS = new HashMap<>();

   public static LuaValue toLuaValue(Object object) {
      if (object == null) {
         return LuaTable.NIL;
      }

      LuaConverter converter = CONVERTERS.get(object.getClass());
      return converter == null ? LuaTable.NIL : converter.convert(Method892(object));
   }

   private static <T> T Method892(Object object) {
      try {
         return (T)object;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   static {
      CONVERTERS.put(Block.class, new BlockConverter());
      CONVERTERS.put(Potion.class, new PotionConverter());
      CONVERTERS.put(RayCastResult.class, new RaycastConverter());
      CONVERTERS.put(Item.class, new ItemConverter());
      CONVERTERS.put(Enchantment.class, new EnchantmentConverter());
      CONVERTERS.put(TemplateRenderBatch.class, new RenderBatchConverter());
   }
}
