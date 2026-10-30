package net.minecraft.util;

import java.util.Random;

public class EnchantmentNameParts {
   private static final EnchantmentNameParts instance = new EnchantmentNameParts();
   private final Random rand = new Random();
   private final String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale "
      .split(" ");

   public static EnchantmentNameParts getInstance() {
      return instance;
   }

   public String generateNewRandomName() {
      int var4 = this.rand.nextInt(2) + 3;
      StringBuilder var5 = new StringBuilder();

      for (int var6 = 0; var6 < var4; var6++) {
         if (var6 > 0) {
            var5.append(" ");
         }

         var5.append(this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)]);
      }

      return var5.toString();
   }

   public void reseedRandomGenerator(long long1) {
      this.rand.setSeed(long1);
   }
}
