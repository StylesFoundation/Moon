package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class AttributeModifier {
   private final double amount;
   private final int operation;
   private final String name;
   private final UUID id;
   private boolean isSaved = true;

   public AttributeModifier(String string, double double2, int integer) {
      this(MathHelper.getRandomUuid(ThreadLocalRandom.current()), string, double2, integer);
   }

   public AttributeModifier(UUID uUID, String string, double double3, int integer) {
      this.id = uUID;
      this.name = string;
      this.amount = double3;
      this.operation = integer;
      Validate.notEmpty(string, "Modifier name cannot be empty", new Object[0]);
      Validate.inclusiveBetween(0L, 2L, integer, "Invalid operation");
   }

   public UUID getID() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public int getOperation() {
      return this.operation;
   }

   public double getAmount() {
      return this.amount;
   }

   public boolean isSaved() {
      return this.isSaved;
   }

   public AttributeModifier setSaved(boolean boolean1) {
      this.isSaved = boolean1;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         AttributeModifier var5 = (AttributeModifier)object;
         return this.id != null ? this.id.equals(var5.id) : var5.id == null;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.id != null ? this.id.hashCode() : 0;
   }

   @Override
   public String toString() {
      return "AttributeModifier{amount="
         + this.amount
         + ", operation="
         + this.operation
         + ", name='"
         + this.name
         + "', id="
         + this.id
         + ", serialize="
         + this.isSaved
         + "}";
   }

   public static int Method585() {
      return 0;
   }
}
