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

public final class ClientboundCloudUpdateResponse extends GeneratedMessageV3 implements ClientboundCloudUpdateResponseOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private volatile Object message_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundCloudUpdateResponse DEFAULT_INSTANCE = new ClientboundCloudUpdateResponse();
   private static final com.google.protobuf.Parser<ClientboundCloudUpdateResponse> PARSER = new ClientboundCloudUpdateResponse.Parser();

   private ClientboundCloudUpdateResponse(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundCloudUpdateResponse() {
      this.id_ = "";
      this.message_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundCloudUpdateResponse();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ClientboundCloudUpdateResponse_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ClientboundCloudUpdateResponse_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundCloudUpdateResponse.class, ClientboundCloudUpdateResponse.Builder.class);
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
   public String getMessage() {
      Object ref = this.message_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.message_ = s;
      return s;
   }

   @Override
   public ByteString getMessageBytes() {
      Object ref = this.message_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.message_ = b;
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

      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         GeneratedMessageV3.writeString(output, 2, this.message_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.message_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundCloudUpdateResponse otherResponse)) {
         return super.equals(other);
      } else if (!this.getId().equals(otherResponse.getId())) {
         return false;
      } else {
         return !this.getMessage().equals(otherResponse.getMessage()) ? false : this.getUnknownFields().equals(otherResponse.getUnknownFields());
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
      hash = 53 * hash + this.getMessage().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundCloudUpdateResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudUpdateResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudUpdateResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudUpdateResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudUpdateResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudUpdateResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundCloudUpdateResponse parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundCloudUpdateResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundCloudUpdateResponse parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundCloudUpdateResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundCloudUpdateResponse parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundCloudUpdateResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundCloudUpdateResponse.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundCloudUpdateResponse.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundCloudUpdateResponse.Builder newBuilder(ClientboundCloudUpdateResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundCloudUpdateResponse.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundCloudUpdateResponse.Builder() : new ClientboundCloudUpdateResponse.Builder().mergeFrom(this);
   }

   protected ClientboundCloudUpdateResponse.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundCloudUpdateResponse.Builder(parent);
   }

   public static ClientboundCloudUpdateResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundCloudUpdateResponse> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundCloudUpdateResponse> getParserForType() {
      return PARSER;
   }

   public ClientboundCloudUpdateResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method504(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method505(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ClientboundCloudUpdateResponse.Builder>
      implements ClientboundCloudUpdateResponseOrBuilder {
      private Object id_ = "";
      private Object message_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ClientboundCloudUpdateResponse_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ClientboundCloudUpdateResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundCloudUpdateResponse.class, ClientboundCloudUpdateResponse.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ClientboundCloudUpdateResponse.Builder clear() {
         super.clear();
         this.id_ = "";
         this.message_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ClientboundCloudUpdateResponse_descriptor;
      }

      public ClientboundCloudUpdateResponse getDefaultInstanceForType() {
         return ClientboundCloudUpdateResponse.getDefaultInstance();
      }

      public ClientboundCloudUpdateResponse build() {
         ClientboundCloudUpdateResponse result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundCloudUpdateResponse buildPartial() {
         ClientboundCloudUpdateResponse result = new ClientboundCloudUpdateResponse(this);
         result.id_ = this.id_;
         result.message_ = this.message_;
         this.onBuilt();
         return result;
      }

      public ClientboundCloudUpdateResponse.Builder clone() {
         return (ClientboundCloudUpdateResponse.Builder)super.clone();
      }

      public ClientboundCloudUpdateResponse.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundCloudUpdateResponse.Builder)super.setField(field, value);
      }

      public ClientboundCloudUpdateResponse.Builder clearField(FieldDescriptor field) {
         return (ClientboundCloudUpdateResponse.Builder)super.clearField(field);
      }

      public ClientboundCloudUpdateResponse.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundCloudUpdateResponse.Builder)super.clearOneof(oneof);
      }

      public ClientboundCloudUpdateResponse.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundCloudUpdateResponse.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundCloudUpdateResponse.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundCloudUpdateResponse.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundCloudUpdateResponse.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundCloudUpdateResponse) {
            return this.mergeFrom((ClientboundCloudUpdateResponse)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundCloudUpdateResponse.Builder mergeFrom(ClientboundCloudUpdateResponse other) {
         if (other == ClientboundCloudUpdateResponse.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (!other.getMessage().isEmpty()) {
            this.message_ = other.message_;
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

      public ClientboundCloudUpdateResponse.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.message_ = input.readStringRequireUtf8();
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

      public ClientboundCloudUpdateResponse.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundCloudUpdateResponse.Builder clearId() {
         this.id_ = ClientboundCloudUpdateResponse.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ClientboundCloudUpdateResponse.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundCloudUpdateResponse.Method504(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getMessage() {
         Object ref = this.message_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.message_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getMessageBytes() {
         Object ref = this.message_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.message_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundCloudUpdateResponse.Builder setMessage(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.message_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundCloudUpdateResponse.Builder clearMessage() {
         this.message_ = ClientboundCloudUpdateResponse.getDefaultInstance().getMessage();
         this.onChanged();
         return this;
      }

      public ClientboundCloudUpdateResponse.Builder setMessageBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundCloudUpdateResponse.Method505(value);
         this.message_ = value;
         this.onChanged();
         return this;
      }

      public final ClientboundCloudUpdateResponse.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudUpdateResponse.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundCloudUpdateResponse.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudUpdateResponse.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundCloudUpdateResponse> {
      public ClientboundCloudUpdateResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundCloudUpdateResponse.Builder builder = ClientboundCloudUpdateResponse.newBuilder();

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
