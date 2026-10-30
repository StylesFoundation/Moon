package com.moon.connection.protobuf.authentication;

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

public final class ServerboundAuthenticationRequest extends GeneratedMessageV3 implements ServerboundAuthenticationRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object secret_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundAuthenticationRequest DEFAULT_INSTANCE = new ServerboundAuthenticationRequest();
   private static final com.google.protobuf.Parser<ServerboundAuthenticationRequest> PARSER = new ServerboundAuthenticationRequest.Parser();

   private ServerboundAuthenticationRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundAuthenticationRequest() {
      this.secret_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundAuthenticationRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return AuthenticationProto.internal_static_ServerboundAuthenticationRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return AuthenticationProto.internal_static_ServerboundAuthenticationRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundAuthenticationRequest.class, ServerboundAuthenticationRequest.Builder.class);
   }

   @Override
   public String getSecret() {
      Object ref = this.secret_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.secret_ = s;
      return s;
   }

   @Override
   public ByteString getSecretBytes() {
      Object ref = this.secret_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.secret_ = b;
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
      if (!GeneratedMessageV3.isStringEmpty(this.secret_)) {
         GeneratedMessageV3.writeString(output, 1, this.secret_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.secret_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.secret_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundAuthenticationRequest otherRequest)) {
         return super.equals(other);
      } else {
         return !this.getSecret().equals(otherRequest.getSecret()) ? false : this.getUnknownFields().equals(otherRequest.getUnknownFields());
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
      hash = 53 * hash + this.getSecret().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundAuthenticationRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAuthenticationRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAuthenticationRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAuthenticationRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAuthenticationRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAuthenticationRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAuthenticationRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundAuthenticationRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundAuthenticationRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundAuthenticationRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundAuthenticationRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundAuthenticationRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundAuthenticationRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundAuthenticationRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundAuthenticationRequest.Builder newBuilder(ServerboundAuthenticationRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundAuthenticationRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundAuthenticationRequest.Builder() : new ServerboundAuthenticationRequest.Builder().mergeFrom(this);
   }

   protected ServerboundAuthenticationRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundAuthenticationRequest.Builder(parent);
   }

   public static ServerboundAuthenticationRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundAuthenticationRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundAuthenticationRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundAuthenticationRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundAuthenticationRequest.Builder>
      implements ServerboundAuthenticationRequestOrBuilder {
      private Object secret_ = "";

      public static final Descriptor getDescriptor() {
         return AuthenticationProto.internal_static_ServerboundAuthenticationRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return AuthenticationProto.internal_static_ServerboundAuthenticationRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundAuthenticationRequest.class, ServerboundAuthenticationRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundAuthenticationRequest.Builder clear() {
         super.clear();
         this.secret_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return AuthenticationProto.internal_static_ServerboundAuthenticationRequest_descriptor;
      }

      public ServerboundAuthenticationRequest getDefaultInstanceForType() {
         return ServerboundAuthenticationRequest.getDefaultInstance();
      }

      public ServerboundAuthenticationRequest build() {
         ServerboundAuthenticationRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundAuthenticationRequest buildPartial() {
         ServerboundAuthenticationRequest result = new ServerboundAuthenticationRequest(this);
         result.secret_ = this.secret_;
         this.onBuilt();
         return result;
      }

      public ServerboundAuthenticationRequest.Builder clone() {
         return (ServerboundAuthenticationRequest.Builder)super.clone();
      }

      public ServerboundAuthenticationRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundAuthenticationRequest.Builder)super.setField(field, value);
      }

      public ServerboundAuthenticationRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundAuthenticationRequest.Builder)super.clearField(field);
      }

      public ServerboundAuthenticationRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundAuthenticationRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundAuthenticationRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundAuthenticationRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundAuthenticationRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundAuthenticationRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundAuthenticationRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundAuthenticationRequest) {
            return this.mergeFrom((ServerboundAuthenticationRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundAuthenticationRequest.Builder mergeFrom(ServerboundAuthenticationRequest other) {
         if (other == ServerboundAuthenticationRequest.getDefaultInstance()) {
            return this;
         }

         if (!other.getSecret().isEmpty()) {
            this.secret_ = other.secret_;
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

      public ServerboundAuthenticationRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.secret_ = input.readStringRequireUtf8();
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
      public String getSecret() {
         Object ref = this.secret_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.secret_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getSecretBytes() {
         Object ref = this.secret_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.secret_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundAuthenticationRequest.Builder setSecret(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.secret_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundAuthenticationRequest.Builder clearSecret() {
         this.secret_ = ServerboundAuthenticationRequest.getDefaultInstance().getSecret();
         this.onChanged();
         return this;
      }

      public ServerboundAuthenticationRequest.Builder setSecretBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundAuthenticationRequest.checkByteStringIsUtf8(value);
         this.secret_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundAuthenticationRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundAuthenticationRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundAuthenticationRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundAuthenticationRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundAuthenticationRequest> {
      public ServerboundAuthenticationRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundAuthenticationRequest.Builder builder = ServerboundAuthenticationRequest.newBuilder();

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
