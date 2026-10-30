package net.minecraft.util;

public class Tuple<A, B> {
   private final A a;
   private final B b;

   public Tuple(A object1, B object2) {
      this.a = (A)object1;
      this.b = (B)object2;
   }

   public A getFirst() {
      return this.a;
   }

   public B getSecond() {
      return this.b;
   }
}
