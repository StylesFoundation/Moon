package com.moon.connection.protobuf.sync;

import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ClientboundUpdateUserSyncOrBuilder extends MessageOrBuilder {
   List<SynchronizedUser> getUsersList();

   SynchronizedUser getUsers(int index);

   int getUsersCount();

   List<? extends SynchronizedUserOrBuilder> getUsersOrBuilderList();

   SynchronizedUserOrBuilder getUsersOrBuilder(int index);
}
