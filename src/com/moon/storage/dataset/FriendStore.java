package com.moon.storage.dataset;

import java.util.Arrays;

public class FriendStore {
   private Friend[] friends;

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof FriendStore other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : Arrays.deepEquals(this.getFriends(), other.getFriends());
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof FriendStore;
   }

   @Override
   public int hashCode() {
      return 59 + Arrays.deepHashCode(this.getFriends());
   }

   @Override
   public String toString() {
      return "FriendStore(friends=" + Arrays.deepToString(this.getFriends()) + ")";
   }

   public Friend[] getFriends() {
      return this.friends;
   }

   public FriendStore setFriends(Friend[] friends) {
      this.friends = friends;
      return this;
   }

   public FriendStore() {
   }

   public FriendStore(Friend[] friends) {
      this.friends = friends;
   }
}
