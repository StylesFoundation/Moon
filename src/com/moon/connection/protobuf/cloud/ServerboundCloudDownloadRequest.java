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

public final class ServerboundCloudDownloadRequest extends GeneratedMessageV3 implements ServerboundCloudDownloadRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudDownloadRequest DEFAULT_INSTANCE = new ServerboundCloudDownloadRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudDownloadRequest> PARSER = new ServerboundCloudDownloadRequest.Parser();

   private ServerboundCloudDownloadRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudDownloadRequest() {
      this.id_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudDownloadRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudDownloadRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudDownloadRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudDownloadRequest.class, ServerboundCloudDownloadRequest.Builder.class);
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
      } else if (!(other instanceof ServerboundCloudDownloadRequest otherRequest)) {
         return super.equals(other);
      } else {
         return !this.getId().equals(otherRequest.getId()) ? false : this.getUnknownFields().equals(otherRequest.getUnknownFields());
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

   public static ServerboundCloudDownloadRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDownloadRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDownloadRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDownloadRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDownloadRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudDownloadRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudDownloadRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudDownloadRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudDownloadRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudDownloadRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudDownloadRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudDownloadRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudDownloadRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudDownloadRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudDownloadRequest.Builder newBuilder(ServerboundCloudDownloadRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudDownloadRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudDownloadRequest.Builder() : new ServerboundCloudDownloadRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudDownloadRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudDownloadRequest.Builder(parent);
   }

   public static ServerboundCloudDownloadRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudDownloadRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudDownloadRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudDownloadRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudDownloadRequest.Builder>
      implements ServerboundCloudDownloadRequestOrBuilder {
      private Object id_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudDownloadRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudDownloadRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudDownloadRequest.class, ServerboundCloudDownloadRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudDownloadRequest.Builder clear() {
         super.clear();
         this.id_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudDownloadRequest_descriptor;
      }

      public ServerboundCloudDownloadRequest getDefaultInstanceForType() {
         return ServerboundCloudDownloadRequest.getDefaultInstance();
      }

      public ServerboundCloudDownloadRequest build() {
         ServerboundCloudDownloadRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudDownloadRequest buildPartial() {
         ServerboundCloudDownloadRequest result = new ServerboundCloudDownloadRequest(this);
         result.id_ = this.id_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudDownloadRequest.Builder clone() {
         return (ServerboundCloudDownloadRequest.Builder)super.clone();
      }

      public ServerboundCloudDownloadRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudDownloadRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudDownloadRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudDownloadRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudDownloadRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudDownloadRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudDownloadRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudDownloadRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudDownloadRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudDownloadRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudDownloadRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudDownloadRequest) {
            return this.mergeFrom((ServerboundCloudDownloadRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudDownloadRequest.Builder mergeFrom(ServerboundCloudDownloadRequest other) {
         if (other == ServerboundCloudDownloadRequest.getDefaultInstance()) {
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

      public ServerboundCloudDownloadRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.id_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundCloudDownloadRequest.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudDownloadRequest.Builder clearId() {
         this.id_ = ServerboundCloudDownloadRequest.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ServerboundCloudDownloadRequest.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudDownloadRequest.checkByteStringIsUtf8(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudDownloadRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudDownloadRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudDownloadRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudDownloadRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudDownloadRequest> {
      public ServerboundCloudDownloadRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudDownloadRequest.Builder builder = ServerboundCloudDownloadRequest.newBuilder();

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
