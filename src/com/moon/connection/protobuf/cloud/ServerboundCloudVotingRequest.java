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

public final class ServerboundCloudVotingRequest extends GeneratedMessageV3 implements ServerboundCloudVotingRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private boolean positive_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudVotingRequest DEFAULT_INSTANCE = new ServerboundCloudVotingRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudVotingRequest> PARSER = new ServerboundCloudVotingRequest.Parser();

   private ServerboundCloudVotingRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudVotingRequest() {
      this.id_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudVotingRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudVotingRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudVotingRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudVotingRequest.class, ServerboundCloudVotingRequest.Builder.class);
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
   public boolean getPositive() {
      return this.positive_;
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

      if (this.positive_) {
         output.writeBool(2, this.positive_);
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

      if (this.positive_) {
         size += CodedOutputStream.computeBoolSize(2, this.positive_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudVotingRequest otherMessage)) {
         return super.equals(other);
      } else if (!this.getId().equals(otherMessage.getId())) {
         return false;
      } else {
         return this.getPositive() != otherMessage.getPositive() ? false : this.getUnknownFields().equals(otherMessage.getUnknownFields());
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
      hash = 53 * hash + Internal.hashBoolean(this.getPositive());
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudVotingRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudVotingRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudVotingRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudVotingRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudVotingRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudVotingRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudVotingRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudVotingRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudVotingRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudVotingRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudVotingRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudVotingRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudVotingRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudVotingRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudVotingRequest.Builder newBuilder(ServerboundCloudVotingRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudVotingRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundCloudVotingRequest.Builder() : new ServerboundCloudVotingRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudVotingRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudVotingRequest.Builder(parent);
   }

   public static ServerboundCloudVotingRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudVotingRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudVotingRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudVotingRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudVotingRequest.Builder>
      implements ServerboundCloudVotingRequestOrBuilder {
      private Object id_ = "";
      private boolean positive_;

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudVotingRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudVotingRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudVotingRequest.class, ServerboundCloudVotingRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudVotingRequest.Builder clear() {
         super.clear();
         this.id_ = "";
         this.positive_ = false;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudVotingRequest_descriptor;
      }

      public ServerboundCloudVotingRequest getDefaultInstanceForType() {
         return ServerboundCloudVotingRequest.getDefaultInstance();
      }

      public ServerboundCloudVotingRequest build() {
         ServerboundCloudVotingRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudVotingRequest buildPartial() {
         ServerboundCloudVotingRequest result = new ServerboundCloudVotingRequest(this);
         result.id_ = this.id_;
         result.positive_ = this.positive_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudVotingRequest.Builder clone() {
         return (ServerboundCloudVotingRequest.Builder)super.clone();
      }

      public ServerboundCloudVotingRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudVotingRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudVotingRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudVotingRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudVotingRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudVotingRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudVotingRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudVotingRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudVotingRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudVotingRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudVotingRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudVotingRequest) {
            return this.mergeFrom((ServerboundCloudVotingRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudVotingRequest.Builder mergeFrom(ServerboundCloudVotingRequest other) {
         if (other == ServerboundCloudVotingRequest.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (other.getPositive()) {
            this.setPositive(other.getPositive());
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public ServerboundCloudVotingRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                  case 16:
                     this.positive_ = input.readBool();
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

      public ServerboundCloudVotingRequest.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudVotingRequest.Builder clearId() {
         this.id_ = ServerboundCloudVotingRequest.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ServerboundCloudVotingRequest.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudVotingRequest.checkByteStringIsUtf8(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public boolean getPositive() {
         return this.positive_;
      }

      public ServerboundCloudVotingRequest.Builder setPositive(boolean value) {
         this.positive_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudVotingRequest.Builder clearPositive() {
         this.positive_ = false;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudVotingRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudVotingRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudVotingRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudVotingRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudVotingRequest> {
      public ServerboundCloudVotingRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudVotingRequest.Builder builder = ServerboundCloudVotingRequest.newBuilder();

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
