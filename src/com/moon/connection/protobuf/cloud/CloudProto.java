package com.moon.connection.protobuf.cloud;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;

public final class CloudProto {
   static final Descriptor internal_static_CloudFileUploader_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
   static final FieldAccessorTable internal_static_CloudFileUploader_fieldAccessorTable = new FieldAccessorTable(
      internal_static_CloudFileUploader_descriptor, new String[]{"Uid", "Name", "Rank", "RankColor"}
   );
   static final Descriptor internal_static_CloudFile_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
   static final FieldAccessorTable internal_static_CloudFile_fieldAccessorTable = new FieldAccessorTable(
      internal_static_CloudFile_descriptor,
      new String[]{"Id", "DisplayName", "UpvoteCount", "DownvoteCount", "Uploader", "UploadDate", "HasUpVoted", "HasDownVoted", "Featured"}
   );
   static final Descriptor internal_static_ServerboundCloudUploadRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(2);
   static final FieldAccessorTable internal_static_ServerboundCloudUploadRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudUploadRequest_descriptor, new String[]{"Type", "DisplayName", "ContentEncoded"}
   );
   static final Descriptor internal_static_ServerboundCloudUpdateContentRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(3);
   static final FieldAccessorTable internal_static_ServerboundCloudUpdateContentRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudUpdateContentRequest_descriptor, new String[]{"Id", "NewContentEncoded"}
   );
   static final Descriptor internal_static_ServerboundCloudUpdateDisplayNameRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(4);
   static final FieldAccessorTable internal_static_ServerboundCloudUpdateDisplayNameRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudUpdateDisplayNameRequest_descriptor, new String[]{"Id", "NewName"}
   );
   static final Descriptor internal_static_ServerboundCloudDeleteRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(5);
   static final FieldAccessorTable internal_static_ServerboundCloudDeleteRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudDeleteRequest_descriptor, new String[]{"Id"}
   );
   static final Descriptor internal_static_ServerboundCloudDownloadRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(6);
   static final FieldAccessorTable internal_static_ServerboundCloudDownloadRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudDownloadRequest_descriptor, new String[]{"Id"}
   );
   static final Descriptor internal_static_ServerboundCloudQueryRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(7);
   static final FieldAccessorTable internal_static_ServerboundCloudQueryRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudQueryRequest_descriptor, new String[]{"Type"}
   );
   static final Descriptor internal_static_ServerboundCloudVotingRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(8);
   static final FieldAccessorTable internal_static_ServerboundCloudVotingRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudVotingRequest_descriptor, new String[]{"Id", "Positive"}
   );
   static final Descriptor internal_static_ServerboundCloudFeaturedRequest_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(9);
   static final FieldAccessorTable internal_static_ServerboundCloudFeaturedRequest_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ServerboundCloudFeaturedRequest_descriptor, new String[]{"Id"}
   );
   static final Descriptor internal_static_ClientboundCloudUpdateResponse_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(10);
   static final FieldAccessorTable internal_static_ClientboundCloudUpdateResponse_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundCloudUpdateResponse_descriptor, new String[]{"Id", "Message"}
   );
   static final Descriptor internal_static_ClientboundCloudDownloadResponse_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(11);
   static final FieldAccessorTable internal_static_ClientboundCloudDownloadResponse_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundCloudDownloadResponse_descriptor, new String[]{"Id", "ContentEncoded"}
   );
   static final Descriptor internal_static_ClientboundCloudQueryResponse_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(12);
   static final FieldAccessorTable internal_static_ClientboundCloudQueryResponse_fieldAccessorTable = new FieldAccessorTable(
      internal_static_ClientboundCloudQueryResponse_descriptor, new String[]{"Type", "Files"}
   );
   private static FileDescriptor descriptor = FileDescriptor.internalBuildGeneratedFileFrom(
      new String[]{
         "\n\u000bcloud.proto\"P\n\u0011CloudFileUploader\u0012\u000b\n\u0003uid\u0018\u0001 \u0001(\u0003\u0012\f\n\u0004name\u0018\u0002 \u0001(\t\u0012\f\n\u0004rank\u0018\u0003 \u0001(\t\u0012\u0012\n\nrank_color\u0018\u0004 \u0001(\t\"Ö\u0001\n\tCloudFile\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u0014\n\fdisplay_name\u0018\u0002 \u0001(\t\u0012\u0014\n\fupvote_count\u0018\u0003 \u0001(\u0005\u0012\u0016\n\u000edownvote_count\u0018\u0004 \u0001(\u0005\u0012$\n\buploader\u0018\u0005 \u0001(\u000b2\u0012.CloudFileUploader\u0012\u0013\n\u000bupload_date\u0018\u0006 \u0001(\u0003\u0012\u0014\n\fhas_up_voted\u0018\u0007 \u0001(\b\u0012\u0016\n\u000ehas_down_voted\u0018\b \u0001(\b\u0012\u0010\n\bfeatured\u0018\t \u0001(\b\"o\n\u001dServerboundCloudUploadRequest\u0012\u001f\n\u0004type\u0018\u0001 \u0001(\u000e2\u0011.CloudStorageType\u0012\u0014\n\fdisplay_name\u0018\u0002 \u0001(\t\u0012\u0017\n\u000fcontent_encoded\u0018\u0003 \u0001(\t\"O\n$ServerboundCloudUpdateContentRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u001b\n\u0013new_content_encoded\u0018\u0002 \u0001(\t\"H\n(ServerboundCloudUpdateDisplayNameRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u0010\n\bnew_name\u0018\u0002 \u0001(\t\"+\n\u001dServerboundCloudDeleteRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\"-\n\u001fServerboundCloudDownloadRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\"?\n\u001cServerboundCloudQueryRequest\u0012\u001f\n\u0004type\u0018\u0001 \u0001(\u000e2\u0011.CloudStorageType\"=\n\u001dServerboundCloudVotingRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u0010\n\bpositive\u0018\u0002 \u0001(\b\"-\n\u001fServerboundCloudFeaturedRequest\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\"=\n\u001eClientboundCloudUpdateResponse\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007message\u0018\u0002 \u0001(\t\"G\n ClientboundCloudDownloadResponse\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\u0017\n\u000fcontent_encoded\u0018\u0002 \u0001(\t\"[\n\u001dClientboundCloudQueryResponse\u0012\u001f\n\u0004type\u0018\u0001 \u0001(\u000e2\u0011.CloudStorageType\u0012\u0019\n\u0005files\u0018\u0002 \u0003(\u000b2\n.CloudFile**\n\u0010CloudStorageType\u0012\n\n\u0006CONFIG\u0010\u0000\u0012\n\n\u0006SCRIPT\u0010\u0001B\u001d\n\u0019com.moon.gateway.protocolP\u0001b\u0006proto3"
      },
      new FileDescriptor[0]
   );

   private CloudProto() {
   }

   public static void registerAllExtensions(ExtensionRegistryLite registry) {
   }

   public static void registerAllExtensions(ExtensionRegistry registry) {
      registerAllExtensions(registry);
   }

   public static FileDescriptor getDescriptor() {
      return descriptor;
   }
}
