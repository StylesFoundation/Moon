package com.moon.connection.services;

import com.moon.connection.Gateway;
import com.moon.connection.protobuf.cloud.CloudStorageType;
import com.moon.connection.protobuf.cloud.ServerboundCloudDeleteRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudDownloadRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudQueryRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUpdateContentRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUpdateDisplayNameRequest;
import com.moon.connection.protobuf.cloud.ServerboundCloudUploadRequest;

public class CloudService {
   private final Gateway gateway;

   public void upload(CloudStorageType type, String displayName, String content) {
      this.gateway.getClient().send(ServerboundCloudUploadRequest.newBuilder().setType(type).setDisplayName(displayName).setContentEncoded(content).build());
   }

   public void updateContent(String id, String content) {
      this.gateway.getClient().send(ServerboundCloudUpdateContentRequest.newBuilder().setId(id).setNewContentEncoded(content).build());
   }

   public void updateDisplayName(String id, String displayName) {
      this.gateway.getClient().send(ServerboundCloudUpdateDisplayNameRequest.newBuilder().setId(id).setNewName(displayName).build());
   }

   public void delete(String id) {
      this.gateway.getClient().send(ServerboundCloudDeleteRequest.newBuilder().setId(id).build());
   }

   public void download(String id) {
      this.gateway.getClient().send(ServerboundCloudDownloadRequest.newBuilder().setId(id).build());
   }

   public void query(CloudStorageType type) {
      this.gateway.getClient().send(ServerboundCloudQueryRequest.newBuilder().setType(type).build());
   }

   public CloudService(Gateway gateway) {
      this.gateway = gateway;
   }
}
