package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.events.impl.input.MouseEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.storage.FriendStorage;
import com.moon.util.client.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriends extends Module {
   public MiddleClickFriends(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onMouse(MouseEvent event) {
      if (event.getButton() == 2 && event.isPressed() && mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer player) {
         String playerName = player.getName();
         FriendStorage friendStorage = Moon.getInstance().getFriendStorage();
         if (friendStorage.isFriend(player)) {
            friendStorage.removeFriend(playerName);
            ChatUtil.sendMessage("<red>Removed " + playerName + " as a friend!");
         } else {
            friendStorage.addFriend(playerName, "");
            ChatUtil.sendMessage("<green>Added " + playerName + " as a friend.");
         }
      }
   }
}
