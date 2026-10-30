package com.moon.connection.protobuf.cloud;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class CloudFile extends GeneratedMessageV3 implements CloudFileOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private volatile Object displayName_;
   private int upvoteCount_;
   private int downvoteCount_;
   private CloudFileUploader uploader_;
   private long uploadDate_;
   private boolean hasUpVoted_;
   private boolean hasDownVoted_;
   private boolean featured_;
   private byte memoizedIsInitialized = -1;
   private static final CloudFile DEFAULT_INSTANCE = new CloudFile();
   private static final com.google.protobuf.Parser<CloudFile> PARSER = new CloudFile.Parser();

   private CloudFile(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private CloudFile() {
      this.id_ = "";
      this.displayName_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new CloudFile();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_CloudFile_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_CloudFile_fieldAccessorTable.ensureFieldAccessorsInitialized(CloudFile.class, CloudFile.Builder.class);
   }

   @Override
   public String getId() {
      Object ref = this.id_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.id_ = s;
      return s;
   }

   @Override
   public ByteString getIdBytes() {
      Object ref = this.id_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.id_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getDisplayName() {
      Object ref = this.displayName_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.displayName_ = s;
      return s;
   }

   @Override
   public ByteString getDisplayNameBytes() {
      Object ref = this.displayName_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.displayName_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public int getUpvoteCount() {
      return this.upvoteCount_;
   }

   @Override
   public int getDownvoteCount() {
      return this.downvoteCount_;
   }

   @Override
   public boolean hasUploader() {
      return this.uploader_ != null;
   }

   @Override
   public CloudFileUploader getUploader() {
      return this.uploader_ == null ? CloudFileUploader.getDefaultInstance() : this.uploader_;
   }

   @Override
   public CloudFileUploaderOrBuilder getUploaderOrBuilder() {
      return this.getUploader();
   }

   @Override
   public long getUploadDate() {
      return this.uploadDate_;
   }

   @Override
   public boolean getHasUpVoted() {
      return this.hasUpVoted_;
   }

   @Override
   public boolean getHasDownVoted() {
      return this.hasDownVoted_;
   }

   @Override
   public boolean getFeatured() {
      return this.featured_;
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
      if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
         GeneratedMessageV3.writeString(output, 1, this.id_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
         GeneratedMessageV3.writeString(output, 2, this.displayName_);
      }

      if (this.upvoteCount_ != 0) {
         output.writeInt32(3, this.upvoteCount_);
      }

      if (this.downvoteCount_ != 0) {
         output.writeInt32(4, this.downvoteCount_);
      }

      if (this.uploader_ != null) {
         output.writeMessage(5, this.getUploader());
      }

      if (this.uploadDate_ != 0L) {
         output.writeInt64(6, this.uploadDate_);
      }

      if (this.hasUpVoted_) {
         output.writeBool(7, this.hasUpVoted_);
      }

      if (this.hasDownVoted_) {
         output.writeBool(8, this.hasDownVoted_);
      }

      if (this.featured_) {
         output.writeBool(9, this.featured_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.id_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.displayName_);
      }

      if (this.upvoteCount_ != 0) {
         size += CodedOutputStream.computeInt32Size(3, this.upvoteCount_);
      }

      if (this.downvoteCount_ != 0) {
         size += CodedOutputStream.computeInt32Size(4, this.downvoteCount_);
      }

      if (this.uploader_ != null) {
         size += CodedOutputStream.computeMessageSize(5, this.getUploader());
      }

      if (this.uploadDate_ != 0L) {
         size += CodedOutputStream.computeInt64Size(6, this.uploadDate_);
      }

      if (this.hasUpVoted_) {
         size += CodedOutputStream.computeBoolSize(7, this.hasUpVoted_);
      }

      if (this.hasDownVoted_) {
         size += CodedOutputStream.computeBoolSize(8, this.hasDownVoted_);
      }

      if (this.featured_) {
         size += CodedOutputStream.computeBoolSize(9, this.featured_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof CloudFile otherFile)) {
         return super.equals(other);
      } else if (!this.getId().equals(otherFile.getId())) {
         return false;
      } else if (!this.getDisplayName().equals(otherFile.getDisplayName())) {
         return false;
      } else if (this.getUpvoteCount() != otherFile.getUpvoteCount()) {
         return false;
      } else if (this.getDownvoteCount() != otherFile.getDownvoteCount()) {
         return false;
      } else if (this.hasUploader() != otherFile.hasUploader()) {
         return false;
      } else if (this.hasUploader() && !this.getUploader().equals(otherFile.getUploader())) {
         return false;
      } else if (this.getUploadDate() != otherFile.getUploadDate()) {
         return false;
      } else if (this.getHasUpVoted() != otherFile.getHasUpVoted()) {
         return false;
      } else if (this.getHasDownVoted() != otherFile.getHasDownVoted()) {
         return false;
      } else {
         return this.getFeatured() != otherFile.getFeatured() ? false : this.getUnknownFields().equals(otherFile.getUnknownFields());
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
      hash = 53 * hash + this.getId().hashCode();
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getDisplayName().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getUpvoteCount();
      hash = 37 * hash + 4;
      hash = 53 * hash + this.getDownvoteCount();
      if (this.hasUploader()) {
         hash = 37 * hash + 5;
         hash = 53 * hash + this.getUploader().hashCode();
      }

      hash = 37 * hash + 6;
      hash = 53 * hash + Internal.hashLong(this.getUploadDate());
      hash = 37 * hash + 7;
      hash = 53 * hash + Internal.hashBoolean(this.getHasUpVoted());
      hash = 37 * hash + 8;
      hash = 53 * hash + Internal.hashBoolean(this.getHasDownVoted());
      hash = 37 * hash + 9;
      hash = 53 * hash + Internal.hashBoolean(this.getFeatured());
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static CloudFile parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFile parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFile parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFile parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFile parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFile parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFile parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static CloudFile parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static CloudFile parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static CloudFile parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static CloudFile parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static CloudFile parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public CloudFile.Builder newBuilderForType() {
      return newBuilder();
   }

   public static CloudFile.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static CloudFile.Builder newBuilder(CloudFile prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public CloudFile.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new CloudFile.Builder() : new CloudFile.Builder().mergeFrom(this);
   }

   protected CloudFile.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new CloudFile.Builder(parent);
   }

   public static CloudFile getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<CloudFile> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<CloudFile> getParserForType() {
      return PARSER;
   }

   public CloudFile getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method650(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method651(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder extends GeneratedMessageV3.Builder<CloudFile.Builder> implements CloudFileOrBuilder {
      private Object id_ = "";
      private Object displayName_ = "";
      private int upvoteCount_;
      private int downvoteCount_;
      private CloudFileUploader uploader_;
      private SingleFieldBuilderV3<CloudFileUploader, CloudFileUploader.Builder, CloudFileUploaderOrBuilder> uploaderBuilder_;
      private long uploadDate_;
      private boolean hasUpVoted_;
      private boolean hasDownVoted_;
      private boolean featured_;

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_CloudFile_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_CloudFile_fieldAccessorTable.ensureFieldAccessorsInitialized(CloudFile.class, CloudFile.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public CloudFile.Builder clear() {
         super.clear();
         this.id_ = "";
         this.displayName_ = "";
         this.upvoteCount_ = 0;
         this.downvoteCount_ = 0;
         if (this.uploaderBuilder_ == null) {
            this.uploader_ = null;
         } else {
            this.uploader_ = null;
            this.uploaderBuilder_ = null;
         }

         this.uploadDate_ = 0L;
         this.hasUpVoted_ = false;
         this.hasDownVoted_ = false;
         this.featured_ = false;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_CloudFile_descriptor;
      }

      public CloudFile getDefaultInstanceForType() {
         return CloudFile.getDefaultInstance();
      }

      public CloudFile build() {
         CloudFile result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public CloudFile buildPartial() {
         CloudFile result = new CloudFile(this);
         result.id_ = this.id_;
         result.displayName_ = this.displayName_;
         result.upvoteCount_ = this.upvoteCount_;
         result.downvoteCount_ = this.downvoteCount_;
         if (this.uploaderBuilder_ == null) {
            result.uploader_ = this.uploader_;
         } else {
            result.uploader_ = (CloudFileUploader)this.uploaderBuilder_.build();
         }

         result.uploadDate_ = this.uploadDate_;
         result.hasUpVoted_ = this.hasUpVoted_;
         result.hasDownVoted_ = this.hasDownVoted_;
         result.featured_ = this.featured_;
         this.onBuilt();
         return result;
      }

      public CloudFile.Builder clone() {
         return (CloudFile.Builder)super.clone();
      }

      public CloudFile.Builder setField(FieldDescriptor field, Object value) {
         return (CloudFile.Builder)super.setField(field, value);
      }

      public CloudFile.Builder clearField(FieldDescriptor field) {
         return (CloudFile.Builder)super.clearField(field);
      }

      public CloudFile.Builder clearOneof(OneofDescriptor oneof) {
         return (CloudFile.Builder)super.clearOneof(oneof);
      }

      public CloudFile.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (CloudFile.Builder)super.setRepeatedField(field, index, value);
      }

      public CloudFile.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (CloudFile.Builder)super.addRepeatedField(field, value);
      }

      public CloudFile.Builder mergeFrom(Message other) {
         if (other instanceof CloudFile) {
            return this.mergeFrom((CloudFile)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public CloudFile.Builder mergeFrom(CloudFile other) {
         if (other == CloudFile.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (!other.getDisplayName().isEmpty()) {
            this.displayName_ = other.displayName_;
            this.onChanged();
         }

         if (other.getUpvoteCount() != 0) {
            this.setUpvoteCount(other.getUpvoteCount());
         }

         if (other.getDownvoteCount() != 0) {
            this.setDownvoteCount(other.getDownvoteCount());
         }

         if (other.hasUploader()) {
            this.mergeUploader(other.getUploader());
         }

         if (other.getUploadDate() != 0L) {
            this.setUploadDate(other.getUploadDate());
         }

         if (other.getHasUpVoted()) {
            this.setHasUpVoted(other.getHasUpVoted());
         }

         if (other.getHasDownVoted()) {
            this.setHasDownVoted(other.getHasDownVoted());
         }

         if (other.getFeatured()) {
            this.setFeatured(other.getFeatured());
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public CloudFile.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                  case 10:
                     this.id_ = input.readStringRequireUtf8();
                     break;
                  case 18:
                     this.displayName_ = input.readStringRequireUtf8();
                     break;
                  case 24:
                     this.upvoteCount_ = input.readInt32();
                     break;
                  case 32:
                     this.downvoteCount_ = input.readInt32();
                     break;
                  case 42:
                     input.readMessage(this.getUploaderFieldBuilder().getBuilder(), extensionRegistry);
                     break;
                  case 48:
                     this.uploadDate_ = input.readInt64();
                     break;
                  case 56:
                     this.hasUpVoted_ = input.readBool();
                     break;
                  case 64:
                     this.hasDownVoted_ = input.readBool();
                     break;
                  case 72:
                     this.featured_ = input.readBool();
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
      public String getId() {
         Object ref = this.id_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.id_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getIdBytes() {
         Object ref = this.id_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.id_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public CloudFile.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearId() {
         this.id_ = CloudFile.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public CloudFile.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         CloudFile.Method650(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getDisplayName() {
         Object ref = this.displayName_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.displayName_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getDisplayNameBytes() {
         Object ref = this.displayName_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.displayName_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public CloudFile.Builder setDisplayName(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.displayName_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearDisplayName() {
         this.displayName_ = CloudFile.getDefaultInstance().getDisplayName();
         this.onChanged();
         return this;
      }

      public CloudFile.Builder setDisplayNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         CloudFile.Method651(value);
         this.displayName_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public int getUpvoteCount() {
         return this.upvoteCount_;
      }

      public CloudFile.Builder setUpvoteCount(int value) {
         this.upvoteCount_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearUpvoteCount() {
         this.upvoteCount_ = 0;
         this.onChanged();
         return this;
      }

      @Override
      public int getDownvoteCount() {
         return this.downvoteCount_;
      }

      public CloudFile.Builder setDownvoteCount(int value) {
         this.downvoteCount_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearDownvoteCount() {
         this.downvoteCount_ = 0;
         this.onChanged();
         return this;
      }

      @Override
      public boolean hasUploader() {
         return this.uploaderBuilder_ != null || this.uploader_ != null;
      }

      @Override
      public CloudFileUploader getUploader() {
         if (this.uploaderBuilder_ == null) {
            return this.uploader_ == null ? CloudFileUploader.getDefaultInstance() : this.uploader_;
         } else {
            return (CloudFileUploader)this.uploaderBuilder_.getMessage();
         }
      }

      public CloudFile.Builder setUploader(CloudFileUploader value) {
         if (this.uploaderBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.uploader_ = value;
            this.onChanged();
         } else {
            this.uploaderBuilder_.setMessage(value);
         }

         return this;
      }

      public CloudFile.Builder setUploader(CloudFileUploader.Builder builder) {
         if (this.uploaderBuilder_ == null) {
            this.uploader_ = builder.build();
            this.onChanged();
         } else {
            this.uploaderBuilder_.setMessage(builder.build());
         }

         return this;
      }

      public CloudFile.Builder mergeUploader(CloudFileUploader value) {
         if (this.uploaderBuilder_ == null) {
            if (this.uploader_ != null) {
               this.uploader_ = CloudFileUploader.newBuilder(this.uploader_).mergeFrom(value).buildPartial();
            } else {
               this.uploader_ = value;
            }

            this.onChanged();
         } else {
            this.uploaderBuilder_.mergeFrom(value);
         }

         return this;
      }

      public CloudFile.Builder clearUploader() {
         if (this.uploaderBuilder_ == null) {
            this.uploader_ = null;
            this.onChanged();
         } else {
            this.uploader_ = null;
            this.uploaderBuilder_ = null;
         }

         return this;
      }

      public CloudFileUploader.Builder getUploaderBuilder() {
         this.onChanged();
         return (CloudFileUploader.Builder)this.getUploaderFieldBuilder().getBuilder();
      }

      @Override
      public CloudFileUploaderOrBuilder getUploaderOrBuilder() {
         if (this.uploaderBuilder_ != null) {
            return (CloudFileUploaderOrBuilder)this.uploaderBuilder_.getMessageOrBuilder();
         } else {
            return this.uploader_ == null ? CloudFileUploader.getDefaultInstance() : this.uploader_;
         }
      }

      private SingleFieldBuilderV3<CloudFileUploader, CloudFileUploader.Builder, CloudFileUploaderOrBuilder> getUploaderFieldBuilder() {
         if (this.uploaderBuilder_ == null) {
            this.uploaderBuilder_ = new SingleFieldBuilderV3(this.getUploader(), this.getParentForChildren(), this.isClean());
            this.uploader_ = null;
         }

         return this.uploaderBuilder_;
      }

      @Override
      public long getUploadDate() {
         return this.uploadDate_;
      }

      public CloudFile.Builder setUploadDate(long value) {
         this.uploadDate_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearUploadDate() {
         this.uploadDate_ = 0L;
         this.onChanged();
         return this;
      }

      @Override
      public boolean getHasUpVoted() {
         return this.hasUpVoted_;
      }

      public CloudFile.Builder setHasUpVoted(boolean value) {
         this.hasUpVoted_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearHasUpVoted() {
         this.hasUpVoted_ = false;
         this.onChanged();
         return this;
      }

      @Override
      public boolean getHasDownVoted() {
         return this.hasDownVoted_;
      }

      public CloudFile.Builder setHasDownVoted(boolean value) {
         this.hasDownVoted_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearHasDownVoted() {
         this.hasDownVoted_ = false;
         this.onChanged();
         return this;
      }

      @Override
      public boolean getFeatured() {
         return this.featured_;
      }

      public CloudFile.Builder setFeatured(boolean value) {
         this.featured_ = value;
         this.onChanged();
         return this;
      }

      public CloudFile.Builder clearFeatured() {
         this.featured_ = false;
         this.onChanged();
         return this;
      }

      public final CloudFile.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (CloudFile.Builder)super.setUnknownFields(unknownFields);
      }

      public final CloudFile.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (CloudFile.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<CloudFile> {
      public CloudFile parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         CloudFile.Builder builder = CloudFile.newBuilder();

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
