package net.optifine.render;

import net.optifine.util.LinkedList;

public class VboRange {
   private int position = -1;
   private int size = 0;
   private final LinkedList.Node<VboRange> node = new LinkedList.Node<>(this);

   public int getPosition() {
      return this.position;
   }

   public int getSize() {
      return this.size;
   }

   public int getPositionNext() {
      return this.position + this.size;
   }

   public void setPosition(int integer) {
      this.position = integer;
   }

   public void setSize(int integer) {
      this.size = integer;
   }

   public LinkedList.Node<VboRange> getNode() {
      return this.node;
   }

   public VboRange getPrev() {
      LinkedList.Node var4 = this.node.getPrev();
      return var4 == null ? null : (VboRange)var4.getItem();
   }

   public VboRange getNext() {
      LinkedList.Node var4 = this.node.getNext();
      return var4 == null ? null : (VboRange)var4.getItem();
   }

   @Override
   public String toString() {
      return this.position + "/" + this.size + "/" + (this.position + this.size);
   }
}
