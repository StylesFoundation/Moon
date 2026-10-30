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

public final class ServerboundChatMessage extends GeneratedMessageV3 implements ServerboundChatMessageOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object message_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundChatMessage DEFAULT_INSTANCE = new ServerboundChatMessage();
   private static final com.google.protobuf.Parser<ServerboundChatMessage> PARSER = new ServerboundChatMessage.Parser();

   private ServerboundChatMessage(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundChatMessage() {
      this.message_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundChatMessage();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return ChatProto.internal_static_ServerboundChatMessage_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return ChatProto.internal_static_ServerboundChatMessage_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundChatMessage.class, ServerboundChatMessage.Builder.class);
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
      } else if (!(other instanceof ServerboundChatMessage otherMessage)) {
         return super.equals(other);
      } else {
         return !this.getMessage().equals(otherMessage.getMessage()) ? false : this.getUnknownFields().equals(otherMessage.getUnknownFields());
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

   public static ServerboundChatMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundChatMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundChatMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundChatMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundChatMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundChatMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundChatMessage parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundChatMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundChatMessage parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundChatMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundChatMessage parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundChatMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundChatMessage.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundChatMessage.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundChatMessage.Builder newBuilder(ServerboundChatMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundChatMessage.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundChatMessage.Builder() : new ServerboundChatMessage.Builder().mergeFrom(this);
   }

   protected ServerboundChatMessage.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundChatMessage.Builder(parent);
   }

   public static ServerboundChatMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundChatMessage> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundChatMessage> getParserForType() {
      return PARSER;
   }

   public ServerboundChatMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundChatMessage.Builder> implements ServerboundChatMessageOrBuilder {
      private Object message_ = "";

      public static final Descriptor getDescriptor() {
         return ChatProto.internal_static_ServerboundChatMessage_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return ChatProto.internal_static_ServerboundChatMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundChatMessage.class, ServerboundChatMessage.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundChatMessage.Builder clear() {
         super.clear();
         this.message_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return ChatProto.internal_static_ServerboundChatMessage_descriptor;
      }

      public ServerboundChatMessage getDefaultInstanceForType() {
         return ServerboundChatMessage.getDefaultInstance();
      }

      public ServerboundChatMessage build() {
         ServerboundChatMessage result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundChatMessage buildPartial() {
         ServerboundChatMessage result = new ServerboundChatMessage(this);
         result.message_ = this.message_;
         this.onBuilt();
         return result;
      }

      public ServerboundChatMessage.Builder clone() {
         return (ServerboundChatMessage.Builder)super.clone();
      }

      public ServerboundChatMessage.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundChatMessage.Builder)super.setField(field, value);
      }

      public ServerboundChatMessage.Builder clearField(FieldDescriptor field) {
         return (ServerboundChatMessage.Builder)super.clearField(field);
      }

      public ServerboundChatMessage.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundChatMessage.Builder)super.clearOneof(oneof);
      }

      public ServerboundChatMessage.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundChatMessage.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundChatMessage.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundChatMessage.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundChatMessage.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundChatMessage) {
            return this.mergeFrom((ServerboundChatMessage)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundChatMessage.Builder mergeFrom(ServerboundChatMessage other) {
         if (other == ServerboundChatMessage.getDefaultInstance()) {
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

      public ServerboundChatMessage.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundChatMessage.Builder setMessage(String message) {
         if (message == null) {
            throw new NullPointerException();
         }

         this.message_ = message;
         this.onChanged();
         return this;
      }

      public ServerboundChatMessage.Builder clearMessage() {
         this.message_ = ServerboundChatMessage.getDefaultInstance().getMessage();
         this.onChanged();
         return this;
      }

      public ServerboundChatMessage.Builder setMessageBytes(ByteString byteString) {
         if (byteString == null) {
            throw new NullPointerException();
         }

         ServerboundChatMessage.checkByteStringIsUtf8(byteString);
         this.message_ = byteString;
         this.onChanged();
         return this;
      }

      public final ServerboundChatMessage.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundChatMessage.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundChatMessage.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundChatMessage.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundChatMessage> {
      public ServerboundChatMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundChatMessage.Builder builder = ServerboundChatMessage.newBuilder();

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
