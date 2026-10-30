package com.moon.connection.protobuf.chat;

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

public final class ClientboundChatError extends GeneratedMessageV3 implements ClientboundChatErrorOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object message_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundChatError DEFAULT_INSTANCE = new ClientboundChatError();
   private static final com.google.protobuf.Parser<ClientboundChatError> PARSER = new ClientboundChatError.Parser();

   private ClientboundChatError(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundChatError() {
      this.message_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundChatError();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return ChatProto.internal_static_ClientboundChatError_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return ChatProto.internal_static_ClientboundChatError_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundChatError.class, ClientboundChatError.Builder.class);
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
      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         GeneratedMessageV3.writeString(output, 1, this.message_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.message_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundChatError otherError)) {
         return super.equals(other);
      } else {
         return !this.getMessage().equals(otherError.getMessage()) ? false : this.getUnknownFields().equals(otherError.getUnknownFields());
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
      hash = 53 * hash + this.getMessage().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundChatError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatError parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatError parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatError parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundChatError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundChatError parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundChatError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundChatError parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundChatError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundChatError.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundChatError.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundChatError.Builder newBuilder(ClientboundChatError prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundChatError.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundChatError.Builder() : new ClientboundChatError.Builder().mergeFrom(this);
   }

   protected ClientboundChatError.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundChatError.Builder(parent);
   }

   public static ClientboundChatError getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundChatError> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundChatError> getParserForType() {
      return PARSER;
   }

   public ClientboundChatError getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ClientboundChatError.Builder> implements ClientboundChatErrorOrBuilder {
      private Object message_ = "";

      public static final Descriptor getDescriptor() {
         return ChatProto.internal_static_ClientboundChatError_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return ChatProto.internal_static_ClientboundChatError_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundChatError.class, ClientboundChatError.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ClientboundChatError.Builder clear() {
         super.clear();
         this.message_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return ChatProto.internal_static_ClientboundChatError_descriptor;
      }

      public ClientboundChatError getDefaultInstanceForType() {
         return ClientboundChatError.getDefaultInstance();
      }

      public ClientboundChatError build() {
         ClientboundChatError result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundChatError buildPartial() {
         ClientboundChatError result = new ClientboundChatError(this);
         result.message_ = this.message_;
         this.onBuilt();
         return result;
      }

      public ClientboundChatError.Builder clone() {
         return (ClientboundChatError.Builder)super.clone();
      }

      public ClientboundChatError.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundChatError.Builder)super.setField(field, value);
      }

      public ClientboundChatError.Builder clearField(FieldDescriptor field) {
         return (ClientboundChatError.Builder)super.clearField(field);
      }

      public ClientboundChatError.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundChatError.Builder)super.clearOneof(oneof);
      }

      public ClientboundChatError.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundChatError.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundChatError.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundChatError.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundChatError.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundChatError) {
            return this.mergeFrom((ClientboundChatError)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundChatError.Builder mergeFrom(ClientboundChatError other) {
         if (other == ClientboundChatError.getDefaultInstance()) {
            return this;
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

      public ClientboundChatError.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ClientboundChatError.Builder setMessage(String message) {
         if (message == null) {
            throw new NullPointerException();
         }

         this.message_ = message;
         this.onChanged();
         return this;
      }

      public ClientboundChatError.Builder clearMessage() {
         this.message_ = ClientboundChatError.getDefaultInstance().getMessage();
         this.onChanged();
         return this;
      }

      public ClientboundChatError.Builder setMessageBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundChatError.checkByteStringIsUtf8(value);
         this.message_ = value;
         this.onChanged();
         return this;
      }

      public final ClientboundChatError.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundChatError.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundChatError.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundChatError.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundChatError> {
      public ClientboundChatError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundChatError.Builder builder = ClientboundChatError.newBuilder();

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
