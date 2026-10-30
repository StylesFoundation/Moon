package net.minecraft.util;

import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

public class ChatComponentProcessor {
   public static IChatComponent processComponent(ICommandSender iCommandSender, IChatComponent iChatComponent, Entity entity) throws CommandException {
      IChatComponent var6;
      if (iChatComponent instanceof ChatComponentScore var7) {
         String var8 = var7.getName();
         if (PlayerSelector.hasArguments(var8)) {
            List var9 = PlayerSelector.matchEntities(iCommandSender, var8, Entity.class);
            if (var9.size() != 1) {
               throw new EntityNotFoundException();
            }

            var8 = ((Entity)var9.get(0)).getName();
         }

         var6 = entity != null && var8.equals("*")
            ? new ChatComponentScore(entity.getName(), var7.getObjective())
            : new ChatComponentScore(var8, var7.getObjective());
         ((ChatComponentScore)var6).setValue(var7.getUnformattedTextForChat());
      } else if (iChatComponent instanceof ChatComponentSelector) {
         String var12 = ((ChatComponentSelector)iChatComponent).getSelector();
         var6 = PlayerSelector.matchEntitiesToChatComponent(iCommandSender, var12);
         if (var6 == null) {
            var6 = new ChatComponentText("");
         }
      } else if (iChatComponent instanceof ChatComponentText) {
         var6 = new ChatComponentText(((ChatComponentText)iChatComponent).getChatComponentText_TextValue());
      } else {
         if (!(iChatComponent instanceof ChatComponentTranslation)) {
            return iChatComponent;
         }

         Object[] var13 = ((ChatComponentTranslation)iChatComponent).getFormatArgs();

         for (int var15 = 0; var15 < var13.length; var15++) {
            Object var10 = var13[var15];
            if (var10 instanceof IChatComponent) {
               var13[var15] = processComponent(iCommandSender, (IChatComponent)var10, entity);
            }
         }

         var6 = new ChatComponentTranslation(((ChatComponentTranslation)iChatComponent).getKey(), var13);
      }

      ChatStyle var11 = iChatComponent.getChatStyle();
      if (var11 != null) {
         var6.setChatStyle(var11.createShallowCopy());
      }

      for (IChatComponent var16 : iChatComponent.getSiblings()) {
         var6.appendSibling(processComponent(iCommandSender, var16, entity));
      }

      return var6;
   }
}
