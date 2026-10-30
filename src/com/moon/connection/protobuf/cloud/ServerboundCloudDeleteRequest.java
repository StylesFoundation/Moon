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

public final class ServerboundCloudDeleteRequest extends GeneratedMessageV3 implements ServerboundCloudDeleteRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudDeleteRequest DEFAULT_INSTANCE = new ServerboundCloudDeleteRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudDeleteRequest> PARSER = new ServerboundCloudDeleteRequest.Parser();

   private ServerboundCloudDeleteRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudDeleteRequest() {
      this.id_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudDeleteRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudDeleteRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudDeleteRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudDeleteRequest.class, ServerboundCloudDeleteRequest.Builder.class);
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
         ByteString bs = ByteString.copyFromUtf8((String)ref);
         this.id_ = bs;
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
      if (!GeneratedMessageV3.isStringEmpty(this.id_)) {
         GeneratedMessageV3.writeString(output, 1, this.id_);
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

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudDeleteRequest that)) {
         return super.equals(other);
      } else {
         return !this.getId().equals(that.getId()) ? false : this.getUnknownFields().equals(that.getUnknownFields());
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
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudDeleteRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDeleteRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDeleteRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDeleteRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDeleteRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDeleteRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDeleteRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudDeleteRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudDeleteRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudDeleteRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudDeleteRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudDeleteRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudDeleteRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudDeleteRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudDeleteRequest.Builder newBuilder(ServerboundCloudDeleteRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudDeleteRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudDeleteRequest.Builder() : new ServerboundCloudDeleteRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudDeleteRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudDeleteRequest.Builder(parent);
   }

   public static ServerboundCloudDeleteRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudDeleteRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudDeleteRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudDeleteRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudDeleteRequest.Builder>
      implements ServerboundCloudDeleteRequestOrBuilder {
      private Object id_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudDeleteRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudDeleteRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudDeleteRequest.class, ServerboundCloudDeleteRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudDeleteRequest.Builder clear() {
         super.clear();
         this.id_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudDeleteRequest_descriptor;
      }

      public ServerboundCloudDeleteRequest getDefaultInstanceForType() {
         return ServerboundCloudDeleteRequest.getDefaultInstance();
      }

      public ServerboundCloudDeleteRequest build() {
         ServerboundCloudDeleteRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudDeleteRequest buildPartial() {
         ServerboundCloudDeleteRequest result = new ServerboundCloudDeleteRequest(this);
         result.id_ = this.id_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudDeleteRequest.Builder clone() {
         return (ServerboundCloudDeleteRequest.Builder)super.clone();
      }

      public ServerboundCloudDeleteRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudDeleteRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudDeleteRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudDeleteRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudDeleteRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudDeleteRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudDeleteRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudDeleteRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudDeleteRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudDeleteRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudDeleteRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudDeleteRequest) {
            return this.mergeFrom((ServerboundCloudDeleteRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudDeleteRequest.Builder mergeFrom(ServerboundCloudDeleteRequest other) {
         if (other == ServerboundCloudDeleteRequest.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
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

      public ServerboundCloudDeleteRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
            ByteString bs = ByteString.copyFromUtf8((String)ref);
            this.id_ = bs;
            return bs;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundCloudDeleteRequest.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudDeleteRequest.Builder clearId() {
         this.id_ = ServerboundCloudDeleteRequest.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ServerboundCloudDeleteRequest.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudDeleteRequest.checkByteStringIsUtf8(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudDeleteRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudDeleteRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudDeleteRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudDeleteRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudDeleteRequest> {
      public ServerboundCloudDeleteRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudDeleteRequest.Builder builder = ServerboundCloudDeleteRequest.newBuilder();

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
