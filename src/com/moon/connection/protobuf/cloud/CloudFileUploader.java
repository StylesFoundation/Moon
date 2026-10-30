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
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class CloudFileUploader extends GeneratedMessageV3 implements CloudFileUploaderOrBuilder {
   private static final long serialVersionUID = 0L;
   private long uid_;
   private volatile Object name_;
   private volatile Object rank_;
   private volatile Object rankColor_;
   private byte memoizedIsInitialized = -1;
   private static final CloudFileUploader DEFAULT_INSTANCE = new CloudFileUploader();
   private static final com.google.protobuf.Parser<CloudFileUploader> PARSER = new CloudFileUploader.Parser();

   private CloudFileUploader(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private CloudFileUploader() {
      this.name_ = "";
      this.rank_ = "";
      this.rankColor_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new CloudFileUploader();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_CloudFileUploader_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_CloudFileUploader_fieldAccessorTable
         .ensureFieldAccessorsInitialized(CloudFileUploader.class, CloudFileUploader.Builder.class);
   }

   @Override
   public long getUid() {
      return this.uid_;
   }

   @Override
   public String getName() {
      Object ref = this.name_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.name_ = s;
      return s;
   }

   @Override
   public ByteString getNameBytes() {
      Object ref = this.name_;
      if (ref instanceof String) {
         ByteString bs = ByteString.copyFromUtf8((String)ref);
         this.name_ = bs;
         return bs;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getRank() {
      Object ref = this.rank_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.rank_ = s;
      return s;
   }

   @Override
   public ByteString getRankBytes() {
      Object ref = this.rank_;
      if (ref instanceof String) {
         ByteString bs = ByteString.copyFromUtf8((String)ref);
         this.rank_ = bs;
         return bs;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getRankColor() {
      Object ref = this.rankColor_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.rankColor_ = s;
      return s;
   }

   @Override
   public ByteString getRankColorBytes() {
      Object ref = this.rankColor_;
      if (ref instanceof String) {
         ByteString bs = ByteString.copyFromUtf8((String)ref);
         this.rankColor_ = bs;
         return bs;
      } else {
         return (ByteString)ref;
      }
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
      if (this.uid_ != 0L) {
         output.writeInt64(1, this.uid_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
         GeneratedMessageV3.writeString(output, 2, this.name_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.rank_)) {
         GeneratedMessageV3.writeString(output, 3, this.rank_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.rankColor_)) {
         GeneratedMessageV3.writeString(output, 4, this.rankColor_);
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
      if (this.uid_ != 0L) {
         size += CodedOutputStream.computeInt64Size(1, this.uid_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.name_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.rank_)) {
         size += GeneratedMessageV3.computeStringSize(3, this.rank_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.rankColor_)) {
         size += GeneratedMessageV3.computeStringSize(4, this.rankColor_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof CloudFileUploader that)) {
         return super.equals(other);
      } else if (this.getUid() != that.getUid()) {
         return false;
      } else if (!this.getName().equals(that.getName())) {
         return false;
      } else if (!this.getRank().equals(that.getRank())) {
         return false;
      } else {
         return !this.getRankColor().equals(that.getRankColor()) ? false : this.getUnknownFields().equals(that.getUnknownFields());
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
      hash = 53 * hash + Internal.hashLong(this.getUid());
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getName().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getRank().hashCode();
      hash = 37 * hash + 4;
      hash = 53 * hash + this.getRankColor().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static CloudFileUploader parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFileUploader parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFileUploader parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFileUploader parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFileUploader parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static CloudFileUploader parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static CloudFileUploader parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static CloudFileUploader parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static CloudFileUploader parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static CloudFileUploader parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static CloudFileUploader parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static CloudFileUploader parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public CloudFileUploader.Builder newBuilderForType() {
      return newBuilder();
   }

   public static CloudFileUploader.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static CloudFileUploader.Builder newBuilder(CloudFileUploader prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public CloudFileUploader.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new CloudFileUploader.Builder() : new CloudFileUploader.Builder().mergeFrom(this);
   }

   protected CloudFileUploader.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new CloudFileUploader.Builder(parent);
   }

   public static CloudFileUploader getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<CloudFileUploader> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<CloudFileUploader> getParserForType() {
      return PARSER;
   }

   public CloudFileUploader getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void checkNameByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void checkRankByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void checkRankColorByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder extends GeneratedMessageV3.Builder<CloudFileUploader.Builder> implements CloudFileUploaderOrBuilder {
      private long uid_;
      private Object name_ = "";
      private Object rank_ = "";
      private Object rankColor_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_CloudFileUploader_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_CloudFileUploader_fieldAccessorTable
            .ensureFieldAccessorsInitialized(CloudFileUploader.class, CloudFileUploader.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public CloudFileUploader.Builder clear() {
         super.clear();
         this.uid_ = 0L;
         this.name_ = "";
         this.rank_ = "";
         this.rankColor_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_CloudFileUploader_descriptor;
      }

      public CloudFileUploader getDefaultInstanceForType() {
         return CloudFileUploader.getDefaultInstance();
      }

      public CloudFileUploader build() {
         CloudFileUploader result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public CloudFileUploader buildPartial() {
         CloudFileUploader result = new CloudFileUploader(this);
         result.uid_ = this.uid_;
         result.name_ = this.name_;
         result.rank_ = this.rank_;
         result.rankColor_ = this.rankColor_;
         this.onBuilt();
         return result;
      }

      public CloudFileUploader.Builder clone() {
         return (CloudFileUploader.Builder)super.clone();
      }

      public CloudFileUploader.Builder setField(FieldDescriptor field, Object value) {
         return (CloudFileUploader.Builder)super.setField(field, value);
      }

      public CloudFileUploader.Builder clearField(FieldDescriptor field) {
         return (CloudFileUploader.Builder)super.clearField(field);
      }

      public CloudFileUploader.Builder clearOneof(OneofDescriptor oneof) {
         return (CloudFileUploader.Builder)super.clearOneof(oneof);
      }

      public CloudFileUploader.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (CloudFileUploader.Builder)super.setRepeatedField(field, index, value);
      }

      public CloudFileUploader.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (CloudFileUploader.Builder)super.addRepeatedField(field, value);
      }

      public CloudFileUploader.Builder mergeFrom(Message other) {
         if (other instanceof CloudFileUploader) {
            return this.mergeFrom((CloudFileUploader)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public CloudFileUploader.Builder mergeFrom(CloudFileUploader other) {
         if (other == CloudFileUploader.getDefaultInstance()) {
            return this;
         }

         if (other.getUid() != 0L) {
            this.setUid(other.getUid());
         }

         if (!other.getName().isEmpty()) {
            this.name_ = other.name_;
            this.onChanged();
         }

         if (!other.getRank().isEmpty()) {
            this.rank_ = other.rank_;
            this.onChanged();
         }

         if (!other.getRankColor().isEmpty()) {
            this.rankColor_ = other.rankColor_;
            this.onChanged();
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public CloudFileUploader.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.uid_ = input.readInt64();
                     break;
                  case 18:
                     this.name_ = input.readStringRequireUtf8();
                     break;
                  case 26:
                     this.rank_ = input.readStringRequireUtf8();
                     break;
                  case 34:
                     this.rankColor_ = input.readStringRequireUtf8();
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
      public long getUid() {
         return this.uid_;
      }

      public CloudFileUploader.Builder setUid(long uid) {
         this.uid_ = uid;
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder clearUid() {
         this.uid_ = 0L;
         this.onChanged();
         return this;
      }

      @Override
      public String getName() {
         Object ref = this.name_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.name_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getNameBytes() {
         Object ref = this.name_;
         if (ref instanceof String) {
            ByteString bs = ByteString.copyFromUtf8((String)ref);
            this.name_ = bs;
            return bs;
         } else {
            return (ByteString)ref;
         }
      }

      public CloudFileUploader.Builder setName(String name) {
         if (name == null) {
            throw new NullPointerException();
         }

         this.name_ = name;
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder clearName() {
         this.name_ = CloudFileUploader.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder setNameBytes(ByteString nameBytes) {
         if (nameBytes == null) {
            throw new NullPointerException();
         }

         CloudFileUploader.checkNameByteStringIsUtf8(nameBytes);
         this.name_ = nameBytes;
         this.onChanged();
         return this;
      }

      @Override
      public String getRank() {
         Object ref = this.rank_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.rank_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getRankBytes() {
         Object ref = this.rank_;
         if (ref instanceof String) {
            ByteString bs = ByteString.copyFromUtf8((String)ref);
            this.rank_ = bs;
            return bs;
         } else {
            return (ByteString)ref;
         }
      }

      public CloudFileUploader.Builder setRank(String rank) {
         if (rank == null) {
            throw new NullPointerException();
         }

         this.rank_ = rank;
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder clearRank() {
         this.rank_ = CloudFileUploader.getDefaultInstance().getRank();
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder setRankBytes(ByteString rankBytes) {
         if (rankBytes == null) {
            throw new NullPointerException();
         }

         CloudFileUploader.checkRankByteStringIsUtf8(rankBytes);
         this.rank_ = rankBytes;
         this.onChanged();
         return this;
      }

      @Override
      public String getRankColor() {
         Object ref = this.rankColor_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.rankColor_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getRankColorBytes() {
         Object ref = this.rankColor_;
         if (ref instanceof String) {
            ByteString bs = ByteString.copyFromUtf8((String)ref);
            this.rankColor_ = bs;
            return bs;
         } else {
            return (ByteString)ref;
         }
      }

      public CloudFileUploader.Builder setRankColor(String rankColor) {
         if (rankColor == null) {
            throw new NullPointerException();
         }

         this.rankColor_ = rankColor;
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder clearRankColor() {
         this.rankColor_ = CloudFileUploader.getDefaultInstance().getRankColor();
         this.onChanged();
         return this;
      }

      public CloudFileUploader.Builder setRankColorBytes(ByteString rankColorBytes) {
         if (rankColorBytes == null) {
            throw new NullPointerException();
         }

         CloudFileUploader.checkRankColorByteStringIsUtf8(rankColorBytes);
         this.rankColor_ = rankColorBytes;
         this.onChanged();
         return this;
      }

      public final CloudFileUploader.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (CloudFileUploader.Builder)super.setUnknownFields(unknownFields);
      }

      public final CloudFileUploader.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (CloudFileUploader.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<CloudFileUploader> {
      public CloudFileUploader parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         CloudFileUploader.Builder builder = CloudFileUploader.newBuilder();

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
