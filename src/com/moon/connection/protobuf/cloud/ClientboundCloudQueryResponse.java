package com.moon.connection.protobuf.cloud;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ClientboundCloudQueryResponse extends GeneratedMessageV3 implements ClientboundCloudQueryResponseOrBuilder {
   private static final long serialVersionUID = 0L;
   private int type_;
   private List<CloudFile> files_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundCloudQueryResponse DEFAULT_INSTANCE = new ClientboundCloudQueryResponse();
   private static final com.google.protobuf.Parser<ClientboundCloudQueryResponse> PARSER = new ClientboundCloudQueryResponse.Parser();

   private ClientboundCloudQueryResponse(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundCloudQueryResponse() {
      this.type_ = 0;
      this.files_ = Collections.emptyList();
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundCloudQueryResponse();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ClientboundCloudQueryResponse_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ClientboundCloudQueryResponse_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundCloudQueryResponse.class, ClientboundCloudQueryResponse.Builder.class);
   }

   @Override
   public int getTypeValue() {
      return this.type_;
   }

   @Override
   public CloudStorageType getType() {
      CloudStorageType result = CloudStorageType.valueOf(this.type_);
      return result == null ? CloudStorageType.UNRECOGNIZED : result;
   }

   @Override
   public List<CloudFile> getFilesList() {
      return this.files_;
   }

   @Override
   public List<? extends CloudFileOrBuilder> getFilesOrBuilderList() {
      return this.files_;
   }

   @Override
   public int getFilesCount() {
      return this.files_.size();
   }

   @Override
   public CloudFile getFiles(int index) {
      return this.files_.get(index);
   }

   @Override
   public CloudFileOrBuilder getFilesOrBuilder(int index) {
      return this.files_.get(index);
   }

   @Override
   public final boolean isInitialized() {
      byte isInitialized = this.memoizedIsInitialized;
      if (isInitialized == 1) {
         return true;
      }

      if (isInitialized == 0) {
         return false;
      }

      this.memoizedIsInitialized = 1;
      return true;
   }

   @Override
   public void writeTo(CodedOutputStream output) throws IOException {
      if (this.type_ != CloudStorageType.CONFIG.getNumber()) {
         output.writeEnum(1, this.type_);
      }

      for (int i = 0; i < this.files_.size(); i++) {
         output.writeMessage(2, this.files_.get(i));
      }

      this.getUnknownFields().writeTo(output);
   }

   @Override
   public int getSerializedSize() {
      int size = this.memoizedSize;
      if (size != -1) {
         return size;
      }

      size = 0;
      if (this.type_ != CloudStorageType.CONFIG.getNumber()) {
         size += CodedOutputStream.computeEnumSize(1, this.type_);
      }

      for (int i = 0; i < this.files_.size(); i++) {
         size += CodedOutputStream.computeMessageSize(2, this.files_.get(i));
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundCloudQueryResponse otherResponse)) {
         return super.equals(other);
      } else if (this.type_ != otherResponse.type_) {
         return false;
      } else {
         return !this.getFilesList().equals(otherResponse.getFilesList()) ? false : this.getUnknownFields().equals(otherResponse.getUnknownFields());
      }
   }

   @Override
   public int hashCode() {
      if (this.memoizedHashCode != 0) {
         return this.memoizedHashCode;
      }

      int hash = 41;
      hash = 779 + getDescriptor().hashCode();
      hash = 37 * hash + 1;
      hash = 53 * hash + this.type_;
      if (this.getFilesCount() > 0) {
         hash = 37 * hash + 2;
         hash = 53 * hash + this.getFilesList().hashCode();
      }

      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundCloudQueryResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudQueryResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudQueryResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudQueryResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudQueryResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudQueryResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudQueryResponse parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundCloudQueryResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundCloudQueryResponse parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundCloudQueryResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundCloudQueryResponse parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundCloudQueryResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundCloudQueryResponse.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundCloudQueryResponse.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundCloudQueryResponse.Builder newBuilder(ClientboundCloudQueryResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundCloudQueryResponse.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundCloudQueryResponse.Builder() : new ClientboundCloudQueryResponse.Builder().mergeFrom(this);
   }

   protected ClientboundCloudQueryResponse.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundCloudQueryResponse.Builder(parent);
   }

   public static ClientboundCloudQueryResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundCloudQueryResponse> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundCloudQueryResponse> getParserForType() {
      return PARSER;
   }

   public ClientboundCloudQueryResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static boolean alwaysUseFieldBuilders() {
      return alwaysUseFieldBuilders;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ClientboundCloudQueryResponse.Builder>
      implements ClientboundCloudQueryResponseOrBuilder {
      private int bitField0_;
      private int type_ = 0;
      private List<CloudFile> files_ = Collections.emptyList();
      private RepeatedFieldBuilderV3<CloudFile, CloudFile.Builder, CloudFileOrBuilder> filesBuilder_;

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ClientboundCloudQueryResponse_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ClientboundCloudQueryResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundCloudQueryResponse.class, ClientboundCloudQueryResponse.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ClientboundCloudQueryResponse.Builder clear() {
         super.clear();
         this.type_ = 0;
         if (this.filesBuilder_ == null) {
            this.files_ = Collections.emptyList();
         } else {
            this.files_ = null;
            this.filesBuilder_.clear();
         }

         this.bitField0_ &= -2;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ClientboundCloudQueryResponse_descriptor;
      }

      public ClientboundCloudQueryResponse getDefaultInstanceForType() {
         return ClientboundCloudQueryResponse.getDefaultInstance();
      }

      public ClientboundCloudQueryResponse build() {
         ClientboundCloudQueryResponse result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundCloudQueryResponse buildPartial() {
         ClientboundCloudQueryResponse result = new ClientboundCloudQueryResponse(this);
         result.type_ = this.type_;
         if (this.filesBuilder_ == null) {
            if ((this.bitField0_ & 1) != 0) {
               this.files_ = Collections.unmodifiableList(this.files_);
               this.bitField0_ &= -2;
            }

            result.files_ = this.files_;
         } else {
            result.files_ = this.filesBuilder_.build();
         }

         this.onBuilt();
         return result;
      }

      public ClientboundCloudQueryResponse.Builder clone() {
         return (ClientboundCloudQueryResponse.Builder)super.clone();
      }

      public ClientboundCloudQueryResponse.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundCloudQueryResponse.Builder)super.setField(field, value);
      }

      public ClientboundCloudQueryResponse.Builder clearField(FieldDescriptor field) {
         return (ClientboundCloudQueryResponse.Builder)super.clearField(field);
      }

      public ClientboundCloudQueryResponse.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundCloudQueryResponse.Builder)super.clearOneof(oneof);
      }

      public ClientboundCloudQueryResponse.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundCloudQueryResponse.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundCloudQueryResponse.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundCloudQueryResponse.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundCloudQueryResponse.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundCloudQueryResponse) {
            return this.mergeFrom((ClientboundCloudQueryResponse)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundCloudQueryResponse.Builder mergeFrom(ClientboundCloudQueryResponse other) {
         if (other == ClientboundCloudQueryResponse.getDefaultInstance()) {
            return this;
         }

         if (other.type_ != 0) {
            this.setTypeValue(other.getTypeValue());
         }

         if (this.filesBuilder_ == null) {
            if (!other.files_.isEmpty()) {
               if (this.files_.isEmpty()) {
                  this.files_ = other.files_;
                  this.bitField0_ &= -2;
               } else {
                  this.ensureFilesIsMutable();
                  this.files_.addAll(other.files_);
               }

               this.onChanged();
            }
         } else if (!other.files_.isEmpty()) {
            if (this.filesBuilder_.isEmpty()) {
               this.filesBuilder_.dispose();
               this.filesBuilder_ = null;
               this.files_ = other.files_;
               this.bitField0_ &= -2;
               this.filesBuilder_ = ClientboundCloudQueryResponse.alwaysUseFieldBuilders() ? this.getFilesFieldBuilder() : null;
            } else {
               this.filesBuilder_.addAllMessages(other.files_);
            }
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public ClientboundCloudQueryResponse.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         if (extensionRegistry == null) {
            throw new NullPointerException();
         }

         try {
            boolean done = false;

            while (!done) {
               int tag = input.readTag();
               switch (tag) {
                  case 0:
                     done = true;
                     break;
                  case 8:
                     this.type_ = input.readEnum();
                     break;
                  case 18:
                     CloudFile m = (CloudFile)input.readMessage(CloudFile.parser(), extensionRegistry);
                     if (this.filesBuilder_ == null) {
                        this.ensureFilesIsMutable();
                        this.files_.add(m);
                     } else {
                        this.filesBuilder_.addMessage(m);
                     }
                     break;
                  default:
                     if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                        done = true;
                     }
               }
            }
         } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
         } finally {
            this.onChanged();
         }

         return this;
      }

      @Override
      public int getTypeValue() {
         return this.type_;
      }

      public ClientboundCloudQueryResponse.Builder setTypeValue(int value) {
         this.type_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public CloudStorageType getType() {
         CloudStorageType result = CloudStorageType.valueOf(this.type_);
         return result == null ? CloudStorageType.UNRECOGNIZED : result;
      }

      public ClientboundCloudQueryResponse.Builder setType(CloudStorageType value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.type_ = value.getNumber();
         this.onChanged();
         return this;
      }

      public ClientboundCloudQueryResponse.Builder clearType() {
         this.type_ = 0;
         this.onChanged();
         return this;
      }

      private void ensureFilesIsMutable() {
         if ((this.bitField0_ & 1) == 0) {
            this.files_ = new ArrayList<>(this.files_);
            this.bitField0_ |= 1;
         }
      }

      @Override
      public List<CloudFile> getFilesList() {
         return this.filesBuilder_ == null ? Collections.unmodifiableList(this.files_) : this.filesBuilder_.getMessageList();
      }

      @Override
      public int getFilesCount() {
         return this.filesBuilder_ == null ? this.files_.size() : this.filesBuilder_.getCount();
      }

      @Override
      public CloudFile getFiles(int index) {
         return this.filesBuilder_ == null ? this.files_.get(index) : (CloudFile)this.filesBuilder_.getMessage(index);
      }

      public ClientboundCloudQueryResponse.Builder setFiles(int index, CloudFile value) {
         if (this.filesBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureFilesIsMutable();
            this.files_.set(index, value);
            this.onChanged();
         } else {
            this.filesBuilder_.setMessage(index, value);
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder setFiles(int index, CloudFile.Builder builder) {
         if (this.filesBuilder_ == null) {
            this.ensureFilesIsMutable();
            this.files_.set(index, builder.build());
            this.onChanged();
         } else {
            this.filesBuilder_.setMessage(index, builder.build());
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder addFiles(CloudFile value) {
         if (this.filesBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureFilesIsMutable();
            this.files_.add(value);
            this.onChanged();
         } else {
            this.filesBuilder_.addMessage(value);
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder addFiles(int index, CloudFile value) {
         if (this.filesBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureFilesIsMutable();
            this.files_.add(index, value);
            this.onChanged();
         } else {
            this.filesBuilder_.addMessage(index, value);
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder addFiles(CloudFile.Builder builder) {
         if (this.filesBuilder_ == null) {
            this.ensureFilesIsMutable();
            this.files_.add(builder.build());
            this.onChanged();
         } else {
            this.filesBuilder_.addMessage(builder.build());
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder addFiles(int index, CloudFile.Builder builder) {
         if (this.filesBuilder_ == null) {
            this.ensureFilesIsMutable();
            this.files_.add(index, builder.build());
            this.onChanged();
         } else {
            this.filesBuilder_.addMessage(index, builder.build());
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder addAllFiles(Iterable<? extends CloudFile> values) {
         if (this.filesBuilder_ == null) {
            this.ensureFilesIsMutable();
            AbstractMessageLite.Builder.addAll(values, this.files_);
            this.onChanged();
         } else {
            this.filesBuilder_.addAllMessages(values);
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder clearFiles() {
         if (this.filesBuilder_ == null) {
            this.files_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.onChanged();
         } else {
            this.filesBuilder_.clear();
         }

         return this;
      }

      public ClientboundCloudQueryResponse.Builder removeFiles(int index) {
         if (this.filesBuilder_ == null) {
            this.ensureFilesIsMutable();
            this.files_.remove(index);
            this.onChanged();
         } else {
            this.filesBuilder_.remove(index);
         }

         return this;
      }

      public CloudFile.Builder getFilesBuilder(int index) {
         return (CloudFile.Builder)this.getFilesFieldBuilder().getBuilder(index);
      }

      @Override
      public CloudFileOrBuilder getFilesOrBuilder(int index) {
         return this.filesBuilder_ == null ? this.files_.get(index) : (CloudFileOrBuilder)this.filesBuilder_.getMessageOrBuilder(index);
      }

      @Override
      public List<? extends CloudFileOrBuilder> getFilesOrBuilderList() {
         return this.filesBuilder_ != null ? this.filesBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.files_);
      }

      public CloudFile.Builder addFilesBuilder() {
         return (CloudFile.Builder)this.getFilesFieldBuilder().addBuilder(CloudFile.getDefaultInstance());
      }

      public CloudFile.Builder addFilesBuilder(int index) {
         return (CloudFile.Builder)this.getFilesFieldBuilder().addBuilder(index, CloudFile.getDefaultInstance());
      }

      public List<CloudFile.Builder> getFilesBuilderList() {
         return this.getFilesFieldBuilder().getBuilderList();
      }

      private RepeatedFieldBuilderV3<CloudFile, CloudFile.Builder, CloudFileOrBuilder> getFilesFieldBuilder() {
         if (this.filesBuilder_ == null) {
            this.filesBuilder_ = new RepeatedFieldBuilderV3(this.files_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
            this.files_ = null;
         }

         return this.filesBuilder_;
      }

      public final ClientboundCloudQueryResponse.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudQueryResponse.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundCloudQueryResponse.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudQueryResponse.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundCloudQueryResponse> {
      public ClientboundCloudQueryResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundCloudQueryResponse.Builder builder = ClientboundCloudQueryResponse.newBuilder();

         try {
            builder.mergeFrom(input, extensionRegistry);
         } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(builder.buildPartial());
         } catch (UninitializedMessageException e) {
            throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
         } catch (IOException e) {
            throw new InvalidProtocolBufferException(e).setUnfinishedMessage(builder.buildPartial());
         }

         return builder.buildPartial();
      }
   }
}
