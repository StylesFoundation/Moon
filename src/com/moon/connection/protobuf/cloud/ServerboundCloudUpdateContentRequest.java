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

public final class ServerboundCloudUpdateContentRequest extends GeneratedMessageV3 implements ServerboundCloudUpdateContentRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private volatile Object newContentEncoded_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudUpdateContentRequest DEFAULT_INSTANCE = new ServerboundCloudUpdateContentRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudUpdateContentRequest> PARSER = new ServerboundCloudUpdateContentRequest.Parser();

   private ServerboundCloudUpdateContentRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudUpdateContentRequest() {
      this.id_ = "";
      this.newContentEncoded_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudUpdateContentRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudUpdateContentRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudUpdateContentRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudUpdateContentRequest.class, ServerboundCloudUpdateContentRequest.Builder.class);
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
   public String getNewContentEncoded() {
      Object ref = this.newContentEncoded_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.newContentEncoded_ = s;
      return s;
   }

   @Override
   public ByteString getNewContentEncodedBytes() {
      Object ref = this.newContentEncoded_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.newContentEncoded_ = b;
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

      if (!GeneratedMessageV3.isStringEmpty(this.newContentEncoded_)) {
         GeneratedMessageV3.writeString(output, 2, this.newContentEncoded_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.newContentEncoded_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.newContentEncoded_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudUpdateContentRequest that)) {
         return super.equals(other);
      } else if (!this.getId().equals(that.getId())) {
         return false;
      } else {
         return !this.getNewContentEncoded().equals(that.getNewContentEncoded()) ? false : this.getUnknownFields().equals(that.getUnknownFields());
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
      hash = 53 * hash + this.getNewContentEncoded().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUpdateContentRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateContentRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateContentRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudUpdateContentRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudUpdateContentRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudUpdateContentRequest.Builder newBuilder(ServerboundCloudUpdateContentRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudUpdateContentRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudUpdateContentRequest.Builder() : new ServerboundCloudUpdateContentRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudUpdateContentRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudUpdateContentRequest.Builder(parent);
   }

   public static ServerboundCloudUpdateContentRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudUpdateContentRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudUpdateContentRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudUpdateContentRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method557(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method558(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudUpdateContentRequest.Builder>
      implements ServerboundCloudUpdateContentRequestOrBuilder {
      private Object id_ = "";
      private Object newContentEncoded_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudUpdateContentRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudUpdateContentRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudUpdateContentRequest.class, ServerboundCloudUpdateContentRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudUpdateContentRequest.Builder clear() {
         super.clear();
         this.id_ = "";
         this.newContentEncoded_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudUpdateContentRequest_descriptor;
      }

      public ServerboundCloudUpdateContentRequest getDefaultInstanceForType() {
         return ServerboundCloudUpdateContentRequest.getDefaultInstance();
      }

      public ServerboundCloudUpdateContentRequest build() {
         ServerboundCloudUpdateContentRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudUpdateContentRequest buildPartial() {
         ServerboundCloudUpdateContentRequest result = new ServerboundCloudUpdateContentRequest(this);
         result.id_ = this.id_;
         result.newContentEncoded_ = this.newContentEncoded_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudUpdateContentRequest.Builder clone() {
         return (ServerboundCloudUpdateContentRequest.Builder)super.clone();
      }

      public ServerboundCloudUpdateContentRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudUpdateContentRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudUpdateContentRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudUpdateContentRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudUpdateContentRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudUpdateContentRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudUpdateContentRequest) {
            return this.mergeFrom((ServerboundCloudUpdateContentRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudUpdateContentRequest.Builder mergeFrom(ServerboundCloudUpdateContentRequest other) {
         if (other == ServerboundCloudUpdateContentRequest.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (!other.getNewContentEncoded().isEmpty()) {
            this.newContentEncoded_ = other.newContentEncoded_;
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

      public ServerboundCloudUpdateContentRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.newContentEncoded_ = input.readStringRequireUtf8();
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

      public ServerboundCloudUpdateContentRequest.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateContentRequest.Builder clearId() {
         this.id_ = ServerboundCloudUpdateContentRequest.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateContentRequest.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUpdateContentRequest.Method557(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getNewContentEncoded() {
         Object ref = this.newContentEncoded_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.newContentEncoded_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getNewContentEncodedBytes() {
         Object ref = this.newContentEncoded_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.newContentEncoded_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundCloudUpdateContentRequest.Builder setNewContentEncoded(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.newContentEncoded_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateContentRequest.Builder clearNewContentEncoded() {
         this.newContentEncoded_ = ServerboundCloudUpdateContentRequest.getDefaultInstance().getNewContentEncoded();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateContentRequest.Builder setNewContentEncodedBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUpdateContentRequest.Method558(value);
         this.newContentEncoded_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudUpdateContentRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudUpdateContentRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUpdateContentRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudUpdateContentRequest> {
      public ServerboundCloudUpdateContentRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudUpdateContentRequest.Builder builder = ServerboundCloudUpdateContentRequest.newBuilder();

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
