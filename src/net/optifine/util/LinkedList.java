package net.optifine.util;

import java.util.Iterator;

public class LinkedList<T> {
   private LinkedList.Node<T> first;
   private LinkedList.Node<T> last;
   private int size;

   public void addFirst(LinkedList.Node<T> node) {
      this.Method393(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         LinkedList.Node var5 = this.first;
         node.setNext(var5);
         var5.setPrev(node);
         this.first = node;
      }

      node.setParent(this);
      this.size++;
   }

   public void addLast(LinkedList.Node<T> node) {
      this.Method393(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         LinkedList.Node var5 = this.last;
         node.setPrev(var5);
         var5.setNext(node);
         this.last = node;
      }

      node.setParent(this);
      this.size++;
   }

   public void addAfter(LinkedList.Node<T> node1, LinkedList.Node<T> node2) {
      if (node1 == null) {
         this.addFirst(node2);
      } else if (node1 == this.last) {
         this.addLast(node2);
      } else {
         this.Method392(node1);
         this.Method393(node2);
         LinkedList.Node var6 = node1.getNext();
         node1.setNext(node2);
         node2.setPrev(node1);
         var6.setPrev(node2);
         node2.setNext(var6);
         node2.setParent(this);
         this.size++;
      }
   }

   public LinkedList.Node<T> remove(LinkedList.Node<T> node) {
      this.Method392(node);
      LinkedList.Node var5 = node.getPrev();
      LinkedList.Node var6 = node.getNext();
      if (var5 != null) {
         var5.setNext(var6);
      } else {
         this.first = var6;
      }

      if (var6 != null) {
         var6.setPrev(var5);
      } else {
         this.last = var5;
      }

      node.setPrev(null);
      node.setNext(null);
      node.setParent(null);
      this.size--;
      return node;
   }

   public void moveAfter(LinkedList.Node<T> node1, LinkedList.Node<T> node2) {
      this.remove(node2);
      this.addAfter(node1, node2);
   }

   public boolean find(LinkedList.Node<T> node1, LinkedList.Node<T> node2, LinkedList.Node<T> node3) {
      this.Method392(node2);
      if (node3 != null) {
         this.Method392(node3);
      }

      LinkedList.Node var7;
      for (var7 = node2; var7 != null && var7 != node3; var7 = var7.getNext()) {
         if (var7 == node1) {
            return true;
         }
      }

      if (var7 != node3) {
         throw new IllegalArgumentException("Sublist is not linked, from: " + node2 + ", to: " + node3);
      } else {
         return false;
      }
   }

   private void Method392(LinkedList.Node<T> node) {
      if (node.parent != this) {
         LinkedList var4 = this;
         LinkedList var5 = node.parent;
         LinkedList.Node var6 = node;
         throw new IllegalArgumentException("Node has different parent, node: " + var6 + ", parent: " + var5 + ", this: " + var4);
      }
   }

   private void Method393(LinkedList.Node<T> node) {
      if (node.parent != null) {
         LinkedList var4 = this;
         LinkedList var5 = node.parent;
         LinkedList.Node var6 = node;
         throw new IllegalArgumentException("Node has different parent, node: " + var6 + ", parent: " + var5 + ", this: " + var4);
      }
   }

   public boolean Method394(LinkedList.Node<T> node) {
      return node.parent == this;
   }

   public Iterator<LinkedList.Node<T>> iterator() {
      return new Iterator<LinkedList.Node<T>>() {
         LinkedList.Node<T> node = LinkedList.this.getFirst();

         @Override
         public boolean hasNext() {
            return this.node != null;
         }

         public LinkedList.Node<T> next() {
            LinkedList.Node var4 = this.node;
            if (this.node != null) {
               this.node = this.node.next;
            }

            return var4;
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("remove");
         }
      };
   }

   public LinkedList.Node<T> getFirst() {
      return this.first;
   }

   public LinkedList.Node<T> getLast() {
      return this.last;
   }

   public int getSize() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size <= 0;
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder();

      for (Iterator<LinkedList.Node<T>> it = this.iterator(); it.hasNext(); ) {
         LinkedList.Node<T> var6 = it.next();
         if (var4.length() > 0) {
            var4.append(", ");
         }

         var4.append(var6.getItem());
      }

      return this.size + " [" + var4 + "]";
   }

   public static class Node<T> {
      private final T item;
      private LinkedList.Node<T> prev;
      private LinkedList.Node<T> next;
      private LinkedList<T> parent;

      public Node(T object) {
         this.item = (T)object;
      }

      public T getItem() {
         return this.item;
      }

      public LinkedList.Node<T> getPrev() {
         return this.prev;
      }

      public LinkedList.Node<T> getNext() {
         return this.next;
      }

      private void setPrev(LinkedList.Node<T> node) {
         this.prev = node;
      }

      private void setNext(LinkedList.Node<T> node) {
         this.next = node;
      }

      private void setParent(LinkedList<T> linkedList) {
         this.parent = linkedList;
      }

      @Override
      public String toString() {
         return this.item + "";
      }
   }
}
