package net.optifine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;

public class LinkedListTest {
   public static void main(String[] arr) throws Exception {
      LinkedList var4 = new LinkedList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();
      Random var7 = new Random();
      byte var8 = 100;

      for (int var9 = 0; var9 < var8; var9++) {
         VboRange var10 = new VboRange();
         var10.setPosition(var9);
         var5.add(var10);
      }

      for (int var14 = 0; var14 < 100000; var14++) {
         checkLists(var5, var6, var8);
         checkLinkedList(var4, var6.size());
         if (var14 % 5 == 0) {
            dbgLinkedList(var4);
         }

         if (var7.nextBoolean()) {
            if (!var5.isEmpty()) {
               VboRange var15 = (VboRange)var5.get(var7.nextInt(var5.size()));
               LinkedList.Node var11 = var15.getNode();
               if (var7.nextBoolean()) {
                  var4.addFirst(var11);
                  dbg("Add first: " + var15.getPosition());
               } else if (var7.nextBoolean()) {
                  var4.addLast(var11);
                  dbg("Add last: " + var15.getPosition());
               } else {
                  if (var6.isEmpty()) {
                     continue;
                  }

                  VboRange var12 = (VboRange)var6.get(var7.nextInt(var6.size()));
                  LinkedList.Node var13 = var12.getNode();
                  var4.addAfter(var13, var11);
                  dbg("Add after: " + var12.getPosition() + ", " + var15.getPosition());
               }

               var5.remove(var15);
               var6.add(var15);
            }
         } else if (!var6.isEmpty()) {
            VboRange var16 = (VboRange)var6.get(var7.nextInt(var6.size()));
            LinkedList.Node var17 = var16.getNode();
            var4.remove(var17);
            dbg("Remove: " + var16.getPosition());
            var6.remove(var16);
            var5.add(var16);
         }
      }
   }

   private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
      StringBuffer var3 = new StringBuffer();
      linkedList.iterator().forEachRemaining(node -> {
         if (node.getItem() != null) {
            VboRange var5 = (VboRange)node.getItem();
            if (var3.length() > 0) {
               var3.append(", ");
            }

            var3.append(var5.getPosition());
         }
      });
      dbg("List: " + var3);
   }

   private static void checkLinkedList(LinkedList<VboRange> linkedList, int integer) {
      if (linkedList.getSize() != integer) {
         throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + integer);
      }

      int var5 = 0;

      for (LinkedList.Node var6 = linkedList.getFirst(); var6 != null; var6 = var6.getNext()) {
         var5++;
      }

      if (linkedList.getSize() != var5) {
         throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + var5);
      }

      int var8 = 0;

      for (LinkedList.Node var7 = linkedList.getLast(); var7 != null; var7 = var7.getPrev()) {
         var8++;
      }

      if (linkedList.getSize() != var8) {
         throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + var8);
      }
   }

   private static void checkLists(List<VboRange> list1, List<VboRange> list2, int integer) {
      int var5 = list1.size() + list2.size();
      if (var5 != integer) {
         throw new RuntimeException("Total size: " + var5);
      }
   }

   private static void dbg(String string) {
      System.out.println(string);
   }
}
