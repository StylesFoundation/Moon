package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface CloudFileOrBuilder extends MessageOrBuilder {
   String getId();

   ByteString getIdBytes();

   String getDisplayName();

   ByteString getDisplayNameBytes();

   int getUpvoteCount();

   int getDownvoteCount();

   boolean hasUploader();

   CloudFileUploader getUploader();

   CloudFileUploaderOrBuilder getUploaderOrBuilder();

   long getUploadDate();

   boolean getHasUpVoted();

   boolean getHasDownVoted();

   boolean getFeatured();
}
