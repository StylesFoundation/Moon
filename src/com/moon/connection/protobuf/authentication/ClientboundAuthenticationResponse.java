package com.moon.connection.protobuf.authentication;

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

public final class ClientboundAuthenticationResponse extends GeneratedMessageV3 implements ClientboundAuthenticationResponseOrBuilder {
   private static final long serialVersionUID = 0L;
   private int bitField0_;
   private boolean success_;
   private volatile Object trafficKey_;
   private volatile Object trafficIv_;
   private volatile Object error_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundAuthenticationResponse DEFAULT_INSTANCE = new ClientboundAuthenticationResponse();
   private static final com.google.protobuf.Parser<ClientboundAuthenticationResponse> PARSER = new ClientboundAuthenticationResponse.Parser();

   private ClientboundAuthenticationResponse(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundAuthenticationResponse() {
      this.trafficKey_ = "";
      this.trafficIv_ = "";
      this.error_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundAuthenticationResponse();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return AuthenticationProto.internal_static_ClientboundAuthenticationResponse_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return AuthenticationProto.internal_static_ClientboundAuthenticationResponse_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundAuthenticationResponse.class, ClientboundAuthenticationResponse.Builder.class);
   }

   @Override
   public boolean getSuccess() {
      return this.success_;
   }

   @Override
   public String getTrafficKey() {
      Object ref = this.trafficKey_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.trafficKey_ = s;
      return s;
   }

   @Override
   public ByteString getTrafficKeyBytes() {
      Object ref = this.trafficKey_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.trafficKey_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getTrafficIv() {
      Object ref = this.trafficIv_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.trafficIv_ = s;
      return s;
   }

   @Override
   public ByteString getTrafficIvBytes() {
      Object ref = this.trafficIv_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.trafficIv_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public boolean hasError() {
      return (this.bitField0_ & 1) != 0;
   }

   @Override
   public String getError() {
      Object ref = this.error_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.error_ = s;
      return s;
   }

   @Override
   public ByteString getErrorBytes() {
      Object ref = this.error_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.error_ = b;
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
      if (this.success_) {
         output.writeBool(1, this.success_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.trafficKey_)) {
         GeneratedMessageV3.writeString(output, 2, this.trafficKey_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.trafficIv_)) {
         GeneratedMessageV3.writeString(output, 3, this.trafficIv_);
      }

      if ((this.bitField0_ & 1) != 0) {
         GeneratedMessageV3.writeString(output, 4, this.error_);
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
      if (this.success_) {
         size += CodedOutputStream.computeBoolSize(1, this.success_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.trafficKey_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.trafficKey_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.trafficIv_)) {
         size += GeneratedMessageV3.computeStringSize(3, this.trafficIv_);
      }

      if ((this.bitField0_ & 1) != 0) {
         size += GeneratedMessageV3.computeStringSize(4, this.error_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundAuthenticationResponse otherResponse)) {
         return super.equals(other);
      } else if (this.getSuccess() != otherResponse.getSuccess()) {
         return false;
      } else if (!this.getTrafficKey().equals(otherResponse.getTrafficKey())) {
         return false;
      } else if (!this.getTrafficIv().equals(otherResponse.getTrafficIv())) {
         return false;
      } else if (this.hasError() != otherResponse.hasError()) {
         return false;
      } else {
         return this.hasError() && !this.getError().equals(otherResponse.getError()) ? false : this.getUnknownFields().equals(otherResponse.getUnknownFields());
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
      hash = 53 * hash + Internal.hashBoolean(this.getSuccess());
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getTrafficKey().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getTrafficIv().hashCode();
      if (this.hasError()) {
         hash = 37 * hash + 4;
         hash = 53 * hash + this.getError().hashCode();
      }

      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundAuthenticationResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundAuthenticationResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundAuthenticationResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundAuthenticationResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundAuthenticationResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundAuthenticationResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundAuthenticationResponse parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundAuthenticationResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundAuthenticationResponse parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundAuthenticationResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundAuthenticationResponse parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundAuthenticationResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundAuthenticationResponse.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundAuthenticationResponse.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundAuthenticationResponse.Builder newBuilder(ClientboundAuthenticationResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundAuthenticationResponse.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundAuthenticationResponse.Builder() : new ClientboundAuthenticationResponse.Builder().mergeFrom(this);
   }

   protected ClientboundAuthenticationResponse.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundAuthenticationResponse.Builder(parent);
   }

   public static ClientboundAuthenticationResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundAuthenticationResponse> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundAuthenticationResponse> getParserForType() {
      return PARSER;
   }

   public ClientboundAuthenticationResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method542(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method543(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ClientboundAuthenticationResponse.Builder>
      implements ClientboundAuthenticationResponseOrBuilder {
      private int bitField0_;
      private boolean success_;
      private Object trafficKey_ = "";
      private Object trafficIv_ = "";
      private Object error_ = "";

      public static final Descriptor getDescriptor() {
         return AuthenticationProto.internal_static_ClientboundAuthenticationResponse_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return AuthenticationProto.internal_static_ClientboundAuthenticationResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundAuthenticationResponse.class, ClientboundAuthenticationResponse.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ClientboundAuthenticationResponse.Builder clear() {
         super.clear();
         this.success_ = false;
         this.trafficKey_ = "";
         this.trafficIv_ = "";
         this.error_ = "";
         this.bitField0_ &= -2;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return AuthenticationProto.internal_static_ClientboundAuthenticationResponse_descriptor;
      }

      public ClientboundAuthenticationResponse getDefaultInstanceForType() {
         return ClientboundAuthenticationResponse.getDefaultInstance();
      }

      public ClientboundAuthenticationResponse build() {
         ClientboundAuthenticationResponse result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundAuthenticationResponse buildPartial() {
         ClientboundAuthenticationResponse result = new ClientboundAuthenticationResponse(this);
         int fromBitField = this.bitField0_;
         byte toBitField = 0;
         result.success_ = this.success_;
         result.trafficKey_ = this.trafficKey_;
         result.trafficIv_ = this.trafficIv_;
         if ((fromBitField & 1) != 0) {
            toBitField = 1;
         }

         result.error_ = this.error_;
         result.bitField0_ = toBitField;
         this.onBuilt();
         return result;
      }

      public ClientboundAuthenticationResponse.Builder clone() {
         return (ClientboundAuthenticationResponse.Builder)super.clone();
      }

      public ClientboundAuthenticationResponse.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundAuthenticationResponse.Builder)super.setField(field, value);
      }

      public ClientboundAuthenticationResponse.Builder clearField(FieldDescriptor field) {
         return (ClientboundAuthenticationResponse.Builder)super.clearField(field);
      }

      public ClientboundAuthenticationResponse.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundAuthenticationResponse.Builder)super.clearOneof(oneof);
      }

      public ClientboundAuthenticationResponse.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundAuthenticationResponse.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundAuthenticationResponse.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundAuthenticationResponse.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundAuthenticationResponse.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundAuthenticationResponse) {
            return this.mergeFrom((ClientboundAuthenticationResponse)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundAuthenticationResponse.Builder mergeFrom(ClientboundAuthenticationResponse other) {
         if (other == ClientboundAuthenticationResponse.getDefaultInstance()) {
            return this;
         }

         if (other.getSuccess()) {
            this.setSuccess(other.getSuccess());
         }

         if (!other.getTrafficKey().isEmpty()) {
            this.trafficKey_ = other.trafficKey_;
            this.onChanged();
         }

         if (!other.getTrafficIv().isEmpty()) {
            this.trafficIv_ = other.trafficIv_;
            this.onChanged();
         }

         if (other.hasError()) {
            this.bitField0_ |= 1;
            this.error_ = other.error_;
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

      public ClientboundAuthenticationResponse.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.success_ = input.readBool();
                     break;
                  case 18:
                     this.trafficKey_ = input.readStringRequireUtf8();
                     break;
                  case 26:
                     this.trafficIv_ = input.readStringRequireUtf8();
                     break;
                  case 34:
                     this.error_ = input.readStringRequireUtf8();
                     this.bitField0_ |= 1;
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
      public boolean getSuccess() {
         return this.success_;
      }

      public ClientboundAuthenticationResponse.Builder setSuccess(boolean success) {
         this.success_ = success;
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder clearSuccess() {
         this.success_ = false;
         this.onChanged();
         return this;
      }

      @Override
      public String getTrafficKey() {
         Object ref = this.trafficKey_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.trafficKey_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getTrafficKeyBytes() {
         Object ref = this.trafficKey_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.trafficKey_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundAuthenticationResponse.Builder setTrafficKey(String trafficKey) {
         if (trafficKey == null) {
            throw new NullPointerException();
         }

         this.trafficKey_ = trafficKey;
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder clearTrafficKey() {
         this.trafficKey_ = ClientboundAuthenticationResponse.getDefaultInstance().getTrafficKey();
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder setTrafficKeyBytes(ByteString trafficKey) {
         if (trafficKey == null) {
            throw new NullPointerException();
         }

         ClientboundAuthenticationResponse.checkByteStringIsUtf8(trafficKey);
         this.trafficKey_ = trafficKey;
         this.onChanged();
         return this;
      }

      @Override
      public String getTrafficIv() {
         Object ref = this.trafficIv_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.trafficIv_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getTrafficIvBytes() {
         Object ref = this.trafficIv_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.trafficIv_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundAuthenticationResponse.Builder setTrafficIv(String trafficIv) {
         if (trafficIv == null) {
            throw new NullPointerException();
         }

         this.trafficIv_ = trafficIv;
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder clearTrafficIv() {
         this.trafficIv_ = ClientboundAuthenticationResponse.getDefaultInstance().getTrafficIv();
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder setTrafficIvBytes(ByteString trafficIv) {
         if (trafficIv == null) {
            throw new NullPointerException();
         }

         ClientboundAuthenticationResponse.Method542(trafficIv);
         this.trafficIv_ = trafficIv;
         this.onChanged();
         return this;
      }

      @Override
      public boolean hasError() {
         return (this.bitField0_ & 1) != 0;
      }

      @Override
      public String getError() {
         Object ref = this.error_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.error_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getErrorBytes() {
         Object ref = this.error_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.error_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundAuthenticationResponse.Builder setError(String error) {
         if (error == null) {
            throw new NullPointerException();
         }

         this.bitField0_ |= 1;
         this.error_ = error;
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder clearError() {
         this.bitField0_ &= -2;
         this.error_ = ClientboundAuthenticationResponse.getDefaultInstance().getError();
         this.onChanged();
         return this;
      }

      public ClientboundAuthenticationResponse.Builder setErrorBytes(ByteString error) {
         if (error == null) {
            throw new NullPointerException();
         }

         ClientboundAuthenticationResponse.Method543(error);
         this.bitField0_ |= 1;
         this.error_ = error;
         this.onChanged();
         return this;
      }

      public final ClientboundAuthenticationResponse.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundAuthenticationResponse.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundAuthenticationResponse.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundAuthenticationResponse.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundAuthenticationResponse> {
      public ClientboundAuthenticationResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundAuthenticationResponse.Builder builder = ClientboundAuthenticationResponse.newBuilder();

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
