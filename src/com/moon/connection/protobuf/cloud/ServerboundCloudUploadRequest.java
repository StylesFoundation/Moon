package com.moon.connection.protobuf.cloud;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
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

public final class ServerboundCloudUploadRequest extends GeneratedMessageV3 implements ServerboundCloudUploadRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private int type_;
   private volatile Object displayName_;
   private volatile Object contentEncoded_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudUploadRequest DEFAULT_INSTANCE = new ServerboundCloudUploadRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudUploadRequest> PARSER = new ServerboundCloudUploadRequest.Parser();

   private ServerboundCloudUploadRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudUploadRequest() {
      this.type_ = 0;
      this.displayName_ = "";
      this.contentEncoded_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudUploadRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudUploadRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudUploadRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudUploadRequest.class, ServerboundCloudUploadRequest.Builder.class);
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
   public String getContentEncoded() {
      Object ref = this.contentEncoded_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.contentEncoded_ = s;
      return s;
   }

   @Override
   public ByteString getContentEncodedBytes() {
      Object ref = this.contentEncoded_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.contentEncoded_ = b;
         return b;
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
      if (this.type_ != CloudStorageType.CONFIG.getNumber()) {
         output.writeEnum(1, this.type_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
         GeneratedMessageV3.writeString(output, 2, this.displayName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.contentEncoded_)) {
         GeneratedMessageV3.writeString(output, 3, this.contentEncoded_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.displayName_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.displayName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.contentEncoded_)) {
         size += GeneratedMessageV3.computeStringSize(3, this.contentEncoded_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudUploadRequest that)) {
         return super.equals(other);
      } else if (this.type_ != that.type_) {
         return false;
      } else if (!this.getDisplayName().equals(that.getDisplayName())) {
         return false;
      } else {
         return !this.getContentEncoded().equals(that.getContentEncoded()) ? false : this.getUnknownFields().equals(that.getUnknownFields());
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
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getDisplayName().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getContentEncoded().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudUploadRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUploadRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUploadRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUploadRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUploadRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUploadRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUploadRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUploadRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUploadRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudUploadRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUploadRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUploadRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudUploadRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudUploadRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudUploadRequest.Builder newBuilder(ServerboundCloudUploadRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudUploadRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudUploadRequest.Builder() : new ServerboundCloudUploadRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudUploadRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudUploadRequest.Builder(parent);
   }

   public static ServerboundCloudUploadRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudUploadRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudUploadRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudUploadRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method1022(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method1023(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudUploadRequest.Builder>
      implements ServerboundCloudUploadRequestOrBuilder {
      private int type_ = 0;
      private Object displayName_ = "";
      private Object contentEncoded_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudUploadRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudUploadRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudUploadRequest.class, ServerboundCloudUploadRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ServerboundCloudUploadRequest.Builder clear() {
         super.clear();
         this.type_ = 0;
         this.displayName_ = "";
         this.contentEncoded_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudUploadRequest_descriptor;
      }

      public ServerboundCloudUploadRequest getDefaultInstanceForType() {
         return ServerboundCloudUploadRequest.getDefaultInstance();
      }

      public ServerboundCloudUploadRequest build() {
         ServerboundCloudUploadRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudUploadRequest buildPartial() {
         ServerboundCloudUploadRequest result = new ServerboundCloudUploadRequest(this);
         result.type_ = this.type_;
         result.displayName_ = this.displayName_;
         result.contentEncoded_ = this.contentEncoded_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudUploadRequest.Builder clone() {
         return (ServerboundCloudUploadRequest.Builder)super.clone();
      }

      public ServerboundCloudUploadRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUploadRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudUploadRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudUploadRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudUploadRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudUploadRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudUploadRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudUploadRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudUploadRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUploadRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudUploadRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudUploadRequest) {
            return this.mergeFrom((ServerboundCloudUploadRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudUploadRequest.Builder mergeFrom(ServerboundCloudUploadRequest other) {
         if (other == ServerboundCloudUploadRequest.getDefaultInstance()) {
            return this;
         }

         if (other.type_ != 0) {
            this.setTypeValue(other.getTypeValue());
         }

         if (!other.getDisplayName().isEmpty()) {
            this.displayName_ = other.displayName_;
            this.onChanged();
         }

         if (!other.getContentEncoded().isEmpty()) {
            this.contentEncoded_ = other.contentEncoded_;
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

      public ServerboundCloudUploadRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.displayName_ = input.readStringRequireUtf8();
                     break;
                  case 26:
                     this.contentEncoded_ = input.readStringRequireUtf8();
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

      public ServerboundCloudUploadRequest.Builder setTypeValue(int value) {
         this.type_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public CloudStorageType getType() {
         CloudStorageType result = CloudStorageType.valueOf(this.type_);
         return result == null ? CloudStorageType.UNRECOGNIZED : result;
      }

      public ServerboundCloudUploadRequest.Builder setType(CloudStorageType type) {
         if (type == null) {
            throw new NullPointerException();
         }

         this.type_ = type.getNumber();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUploadRequest.Builder clearType() {
         this.type_ = 0;
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

      public ServerboundCloudUploadRequest.Builder setDisplayName(String displayName) {
         if (displayName == null) {
            throw new NullPointerException();
         }

         this.displayName_ = displayName;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUploadRequest.Builder clearDisplayName() {
         this.displayName_ = ServerboundCloudUploadRequest.getDefaultInstance().getDisplayName();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUploadRequest.Builder setDisplayNameBytes(ByteString byteString) {
         if (byteString == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUploadRequest.Method1022(byteString);
         this.displayName_ = byteString;
         this.onChanged();
         return this;
      }

      @Override
      public String getContentEncoded() {
         Object ref = this.contentEncoded_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.contentEncoded_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getContentEncodedBytes() {
         Object ref = this.contentEncoded_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.contentEncoded_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundCloudUploadRequest.Builder setContentEncoded(String contentEncoded) {
         if (contentEncoded == null) {
            throw new NullPointerException();
         }

         this.contentEncoded_ = contentEncoded;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUploadRequest.Builder clearContentEncoded() {
         this.contentEncoded_ = ServerboundCloudUploadRequest.getDefaultInstance().getContentEncoded();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUploadRequest.Builder setContentEncodedBytes(ByteString byteString) {
         if (byteString == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUploadRequest.Method1023(byteString);
         this.contentEncoded_ = byteString;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudUploadRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUploadRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudUploadRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUploadRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudUploadRequest> {
      public ServerboundCloudUploadRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudUploadRequest.Builder builder = ServerboundCloudUploadRequest.newBuilder();

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
