package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

public class GuiUtilRenderComponents {
   public static String func_178909_a(String string, boolean boolean2) {
      return !boolean2 && !Minecraft.getMinecraft().gameSettings.chatColours ? EnumChatFormatting.getTextWithoutFormattingCodes(string) : string;
   }

   public static List<IChatComponent> Method379(
      IChatComponent iChatComponent,
      int integer,
      FontRenderer fontRenderer,
      boolean boolean4,
      boolean boolean5,
      boolean boolean6,
      com.moon.render.font.FontRenderer jm
   ) {
      int var9 = 0;
      ChatComponentText var10 = new ChatComponentText("");
      ArrayList var11 = Lists.newArrayList();
      ArrayList var12 = Lists.newArrayList(iChatComponent);

      for (int var13 = 0; var13 < var12.size(); var13++) {
         IChatComponent var14 = (IChatComponent)var12.get(var13);
         String var15 = var14.getUnformattedTextForChat();
         boolean var16 = false;
         if (var15.contains("\n")) {
            int var17 = var15.indexOf(10);
            String var18 = var15.substring(var17 + 1);
            var15 = var15.substring(0, var17 + 1);
            ChatComponentText var19 = new ChatComponentText(var18);
            var19.setChatStyle(var14.getChatStyle().createShallowCopy());
            var12.add(var13 + 1, var19);
            var16 = true;
         }

         String var25 = func_178909_a(var14.getChatStyle().getFormattingCode() + var15, boolean5);
         String var26 = var25.endsWith("\n") ? var25.substring(0, var25.length() - 1) : var25;
         int var27 = boolean6 ? (int)jm.getTextWidth(var26, 9.0F) : fontRenderer.getStringWidth(var26);
         ChatComponentText var20 = new ChatComponentText(var26);
         var20.setChatStyle(var14.getChatStyle().createShallowCopy());
         if (var9 + var27 > integer) {
            String var21 = boolean6 ? jm.trimToWidth(var25, integer - var9, 9.0F, false) : fontRenderer.trimStringToWidth(var25, integer - var9, false);
            String var22 = var21.length() < var25.length() ? var25.substring(var21.length()) : null;
            if (var22 != null && var22.length() > 0) {
               int var23 = var21.lastIndexOf(" ");
               if (var23 >= 0
                  && (
                        boolean6
                           ? MathHelper.ceiling_float_int(jm.getTextWidth(var25.substring(0, var23), 9.0F))
                           : fontRenderer.getStringWidth(var25.substring(0, var23))
                     )
                     > 0) {
                  var21 = var25.substring(0, var23);
                  if (boolean4) {
                     var23++;
                  }

                  var22 = var25.substring(var23);
               } else if (var9 > 0 && !var25.contains(" ")) {
                  var21 = "";
                  var22 = var25;
               }

               ChatComponentText var24 = new ChatComponentText(var22);
               var24.setChatStyle(var14.getChatStyle().createShallowCopy());
               var12.add(var13 + 1, var24);
            }

            var27 = boolean6 ? MathHelper.ceiling_double_int(jm.getTextWidth(var21, 9.0F)) : fontRenderer.getStringWidth(var21);
            var20 = new ChatComponentText(var21);
            var20.setChatStyle(var14.getChatStyle().createShallowCopy());
            var16 = true;
         }

         if (var9 + var27 <= integer) {
            var9 += var27;
            var10.appendSibling(var20);
         } else {
            var16 = true;
         }

         if (var16) {
            var11.add(var10);
            var9 = 0;
            var10 = new ChatComponentText("");
         }
      }

      var11.add(var10);
      return var11;
   }
}
