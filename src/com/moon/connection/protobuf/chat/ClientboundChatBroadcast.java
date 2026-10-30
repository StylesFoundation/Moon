package com.moon.connection.protobuf.chat;

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

public final class ClientboundChatBroadcast extends GeneratedMessageV3 implements ClientboundChatBroadcastOrBuilder {
   private static final long serialVersionUID = 0L;
   private long senderId_;
   private volatile Object senderName_;
   private volatile Object senderColor_;
   private volatile Object message_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundChatBroadcast DEFAULT_INSTANCE = new ClientboundChatBroadcast();
   private static final com.google.protobuf.Parser<ClientboundChatBroadcast> PARSER = new ClientboundChatBroadcast.Parser();

   private ClientboundChatBroadcast(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundChatBroadcast() {
      this.senderName_ = "";
      this.senderColor_ = "";
      this.message_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundChatBroadcast();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return ChatProto.internal_static_ClientboundChatBroadcast_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return ChatProto.internal_static_ClientboundChatBroadcast_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundChatBroadcast.class, ClientboundChatBroadcast.Builder.class);
   }

   @Override
   public long getSenderId() {
      return this.senderId_;
   }

   @Override
   public String getSenderName() {
      Object ref = this.senderName_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.senderName_ = s;
      return s;
   }

   @Override
   public ByteString getSenderNameBytes() {
      Object ref = this.senderName_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.senderName_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getSenderColor() {
      Object ref = this.senderColor_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.senderColor_ = s;
      return s;
   }

   @Override
   public ByteString getSenderColorBytes() {
      Object ref = this.senderColor_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.senderColor_ = b;
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
      if (this.senderId_ != 0L) {
         output.writeInt64(1, this.senderId_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.senderName_)) {
         GeneratedMessageV3.writeString(output, 2, this.senderName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.senderColor_)) {
         GeneratedMessageV3.writeString(output, 3, this.senderColor_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         GeneratedMessageV3.writeString(output, 4, this.message_);
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
      if (this.senderId_ != 0L) {
         size += CodedOutputStream.computeInt64Size(1, this.senderId_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.senderName_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.senderName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.senderColor_)) {
         size += GeneratedMessageV3.computeStringSize(3, this.senderColor_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.message_)) {
         size += GeneratedMessageV3.computeStringSize(4, this.message_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundChatBroadcast otherBroadcast)) {
         return super.equals(other);
      } else if (this.getSenderId() != otherBroadcast.getSenderId()) {
         return false;
      } else if (!this.getSenderName().equals(otherBroadcast.getSenderName())) {
         return false;
      } else if (!this.getSenderColor().equals(otherBroadcast.getSenderColor())) {
         return false;
      } else {
         return !this.getMessage().equals(otherBroadcast.getMessage()) ? false : this.getUnknownFields().equals(otherBroadcast.getUnknownFields());
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
      hash = 53 * hash + Internal.hashLong(this.getSenderId());
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getSenderName().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getSenderColor().hashCode();
      hash = 37 * hash + 4;
      hash = 53 * hash + this.getMessage().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundChatBroadcast parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatBroadcast parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatBroadcast parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatBroadcast parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatBroadcast parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundChatBroadcast parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundChatBroadcast parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundChatBroadcast parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundChatBroadcast parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundChatBroadcast parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundChatBroadcast parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundChatBroadcast parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundChatBroadcast.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundChatBroadcast.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundChatBroadcast.Builder newBuilder(ClientboundChatBroadcast prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundChatBroadcast.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundChatBroadcast.Builder() : new ClientboundChatBroadcast.Builder().mergeFrom(this);
   }

   protected ClientboundChatBroadcast.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundChatBroadcast.Builder(parent);
   }

   public static ClientboundChatBroadcast getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundChatBroadcast> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundChatBroadcast> getParserForType() {
      return PARSER;
   }

   public ClientboundChatBroadcast getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method509(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method510(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ClientboundChatBroadcast.Builder> implements ClientboundChatBroadcastOrBuilder {
      private long senderId_;
      private Object senderName_ = "";
      private Object senderColor_ = "";
      private Object message_ = "";

      public static final Descriptor getDescriptor() {
         return ChatProto.internal_static_ClientboundChatBroadcast_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return ChatProto.internal_static_ClientboundChatBroadcast_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundChatBroadcast.class, ClientboundChatBroadcast.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ClientboundChatBroadcast.Builder clear() {
         super.clear();
         this.senderId_ = 0L;
         this.senderName_ = "";
         this.senderColor_ = "";
         this.message_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return ChatProto.internal_static_ClientboundChatBroadcast_descriptor;
      }

      public ClientboundChatBroadcast getDefaultInstanceForType() {
         return ClientboundChatBroadcast.getDefaultInstance();
      }

      public ClientboundChatBroadcast build() {
         ClientboundChatBroadcast result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundChatBroadcast buildPartial() {
         ClientboundChatBroadcast result = new ClientboundChatBroadcast(this);
         result.senderId_ = this.senderId_;
         result.senderName_ = this.senderName_;
         result.senderColor_ = this.senderColor_;
         result.message_ = this.message_;
         this.onBuilt();
         return result;
      }

      public ClientboundChatBroadcast.Builder clone() {
         return (ClientboundChatBroadcast.Builder)super.clone();
      }

      public ClientboundChatBroadcast.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundChatBroadcast.Builder)super.setField(field, value);
      }

      public ClientboundChatBroadcast.Builder clearField(FieldDescriptor field) {
         return (ClientboundChatBroadcast.Builder)super.clearField(field);
      }

      public ClientboundChatBroadcast.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundChatBroadcast.Builder)super.clearOneof(oneof);
      }

      public ClientboundChatBroadcast.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundChatBroadcast.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundChatBroadcast.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundChatBroadcast.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundChatBroadcast.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundChatBroadcast) {
            return this.mergeFrom((ClientboundChatBroadcast)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundChatBroadcast.Builder mergeFrom(ClientboundChatBroadcast other) {
         if (other == ClientboundChatBroadcast.getDefaultInstance()) {
            return this;
         }

         if (other.getSenderId() != 0L) {
            this.setSenderId(other.getSenderId());
         }

         if (!other.getSenderName().isEmpty()) {
            this.senderName_ = other.senderName_;
            this.onChanged();
         }

         if (!other.getSenderColor().isEmpty()) {
            this.senderColor_ = other.senderColor_;
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

      public ClientboundChatBroadcast.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.senderId_ = input.readInt64();
                     break;
                  case 18:
                     this.senderName_ = input.readStringRequireUtf8();
                     break;
                  case 26:
                     this.senderColor_ = input.readStringRequireUtf8();
                     break;
                  case 34:
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
      public long getSenderId() {
         return this.senderId_;
      }

      public ClientboundChatBroadcast.Builder setSenderId(long value) {
         this.senderId_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder clearSenderId() {
         this.senderId_ = 0L;
         this.onChanged();
         return this;
      }

      @Override
      public String getSenderName() {
         Object ref = this.senderName_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.senderName_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getSenderNameBytes() {
         Object ref = this.senderName_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.senderName_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundChatBroadcast.Builder setSenderName(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.senderName_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder clearSenderName() {
         this.senderName_ = ClientboundChatBroadcast.getDefaultInstance().getSenderName();
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder setSenderNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundChatBroadcast.checkByteStringIsUtf8(value);
         this.senderName_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getSenderColor() {
         Object ref = this.senderColor_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.senderColor_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getSenderColorBytes() {
         Object ref = this.senderColor_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.senderColor_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundChatBroadcast.Builder setSenderColor(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.senderColor_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder clearSenderColor() {
         this.senderColor_ = ClientboundChatBroadcast.getDefaultInstance().getSenderColor();
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder setSenderColorBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundChatBroadcast.Method509(value);
         this.senderColor_ = value;
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

      public ClientboundChatBroadcast.Builder setMessage(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.message_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder clearMessage() {
         this.message_ = ClientboundChatBroadcast.getDefaultInstance().getMessage();
         this.onChanged();
         return this;
      }

      public ClientboundChatBroadcast.Builder setMessageBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundChatBroadcast.Method510(value);
         this.message_ = value;
         this.onChanged();
         return this;
      }

      public final ClientboundChatBroadcast.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundChatBroadcast.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundChatBroadcast.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundChatBroadcast.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundChatBroadcast> {
      public ClientboundChatBroadcast parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundChatBroadcast.Builder builder = ClientboundChatBroadcast.newBuilder();

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
