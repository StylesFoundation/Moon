package com.moon.connection.protobuf.cloud;

import com.google.protobuf.MessageOrBuilder;
import java.util.List;

public interface ClientboundCloudQueryResponseOrBuilder extends MessageOrBuilder {
   int getTypeValue();

   CloudStorageType getType();

   List<CloudFile> getFilesList();

   CloudFile getFiles(int index);

   int getFilesCount();

   List<? extends CloudFileOrBuilder> getFilesOrBuilderList();

   CloudFileOrBuilder getFilesOrBuilder(int index);
}
