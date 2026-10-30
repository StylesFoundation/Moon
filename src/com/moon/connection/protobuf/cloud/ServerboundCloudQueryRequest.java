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

public final class ServerboundCloudQueryRequest extends GeneratedMessageV3 implements ServerboundCloudQueryRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private int type_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudQueryRequest DEFAULT_INSTANCE = new ServerboundCloudQueryRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudQueryRequest> PARSER = new ServerboundCloudQueryRequest.Parser();

   private ServerboundCloudQueryRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudQueryRequest() {
      this.type_ = 0;
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudQueryRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudQueryRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudQueryRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudQueryRequest.class, ServerboundCloudQueryRequest.Builder.class);
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

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudQueryRequest otherRequest)) {
         return super.equals(other);
      } else {
         return this.type_ != otherRequest.type_ ? false : this.getUnknownFields().equals(otherRequest.getUnknownFields());
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
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudQueryRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudQueryRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudQueryRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudQueryRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudQueryRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudQueryRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudQueryRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudQueryRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudQueryRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudQueryRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudQueryRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudQueryRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudQueryRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudQueryRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudQueryRequest.Builder newBuilder(ServerboundCloudQueryRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudQueryRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudQueryRequest.Builder() : new ServerboundCloudQueryRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudQueryRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudQueryRequest.Builder(parent);
   }

   public static ServerboundCloudQueryRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudQueryRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudQueryRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudQueryRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundCloudQueryRequest.Builder> implements ServerboundCloudQueryRequestOrBuilder {
      private int type_ = 0;

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudQueryRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudQueryRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudQueryRequest.class, ServerboundCloudQueryRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudQueryRequest.Builder clear() {
         super.clear();
         this.type_ = 0;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudQueryRequest_descriptor;
      }

      public ServerboundCloudQueryRequest getDefaultInstanceForType() {
         return ServerboundCloudQueryRequest.getDefaultInstance();
      }

      public ServerboundCloudQueryRequest build() {
         ServerboundCloudQueryRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudQueryRequest buildPartial() {
         ServerboundCloudQueryRequest result = new ServerboundCloudQueryRequest(this);
         result.type_ = this.type_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudQueryRequest.Builder clone() {
         return (ServerboundCloudQueryRequest.Builder)super.clone();
      }

      public ServerboundCloudQueryRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudQueryRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudQueryRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudQueryRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudQueryRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudQueryRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudQueryRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudQueryRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudQueryRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudQueryRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudQueryRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudQueryRequest) {
            return this.mergeFrom((ServerboundCloudQueryRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudQueryRequest.Builder mergeFrom(ServerboundCloudQueryRequest other) {
         if (other == ServerboundCloudQueryRequest.getDefaultInstance()) {
            return this;
         }

         if (other.type_ != 0) {
            this.setTypeValue(other.getTypeValue());
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public ServerboundCloudQueryRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundCloudQueryRequest.Builder setTypeValue(int value) {
         this.type_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public CloudStorageType getType() {
         CloudStorageType result = CloudStorageType.valueOf(this.type_);
         return result == null ? CloudStorageType.UNRECOGNIZED : result;
      }

      public ServerboundCloudQueryRequest.Builder setType(CloudStorageType type) {
         if (type == null) {
            throw new NullPointerException();
         }

         this.type_ = type.getNumber();
         this.onChanged();
         return this;
      }

      public ServerboundCloudQueryRequest.Builder clearType() {
         this.type_ = 0;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudQueryRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudQueryRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudQueryRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudQueryRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudQueryRequest> {
      public ServerboundCloudQueryRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudQueryRequest.Builder builder = ServerboundCloudQueryRequest.newBuilder();

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
