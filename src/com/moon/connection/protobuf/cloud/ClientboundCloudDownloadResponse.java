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

public final class ClientboundCloudDownloadResponse extends GeneratedMessageV3 implements ClientboundCloudDownloadResponseOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private volatile Object contentEncoded_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundCloudDownloadResponse DEFAULT_INSTANCE = new ClientboundCloudDownloadResponse();
   private static final com.google.protobuf.Parser<ClientboundCloudDownloadResponse> PARSER = new ClientboundCloudDownloadResponse.Parser();

   private ClientboundCloudDownloadResponse(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundCloudDownloadResponse() {
      this.id_ = "";
      this.contentEncoded_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundCloudDownloadResponse();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ClientboundCloudDownloadResponse_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ClientboundCloudDownloadResponse_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundCloudDownloadResponse.class, ClientboundCloudDownloadResponse.Builder.class);
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
   public String getContentEncoded() {
      Object ref = this.contentEncoded_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.contentEncoded_ = s;
      return s;
   }

   @Override
   public ByteString getContentEncodedBytes() {
      Object ref = this.contentEncoded_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.contentEncoded_ = b;
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

      if (!GeneratedMessageV3.isStringEmpty(this.contentEncoded_)) {
         GeneratedMessageV3.writeString(output, 2, this.contentEncoded_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.contentEncoded_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.contentEncoded_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundCloudDownloadResponse otherResponse)) {
         return super.equals(other);
      } else if (!this.getId().equals(otherResponse.getId())) {
         return false;
      } else {
         return !this.getContentEncoded().equals(otherResponse.getContentEncoded()) ? false : this.getUnknownFields().equals(otherResponse.getUnknownFields());
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
      hash = 53 * hash + this.getContentEncoded().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundCloudDownloadResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteBuffer);
   }

   public static ClientboundCloudDownloadResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
   }

   public static ClientboundCloudDownloadResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteString);
   }

   public static ClientboundCloudDownloadResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteString, extensionRegistryLite);
   }

   public static ClientboundCloudDownloadResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundCloudDownloadResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistryLite);
   }

   public static ClientboundCloudDownloadResponse parseFrom(InputStream inputStream) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
   }

   public static ClientboundCloudDownloadResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
   }

   public static ClientboundCloudDownloadResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
   }

   public static ClientboundCloudDownloadResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
   }

   public static ClientboundCloudDownloadResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
   }

   public static ClientboundCloudDownloadResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
   }

   public ClientboundCloudDownloadResponse.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundCloudDownloadResponse.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundCloudDownloadResponse.Builder newBuilder(ClientboundCloudDownloadResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundCloudDownloadResponse.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundCloudDownloadResponse.Builder() : new ClientboundCloudDownloadResponse.Builder().mergeFrom(this);
   }

   protected ClientboundCloudDownloadResponse.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundCloudDownloadResponse.Builder(parent);
   }

   public static ClientboundCloudDownloadResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundCloudDownloadResponse> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundCloudDownloadResponse> getParserForType() {
      return PARSER;
   }

   public ClientboundCloudDownloadResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method555(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method556(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ClientboundCloudDownloadResponse.Builder>
      implements ClientboundCloudDownloadResponseOrBuilder {
      private Object id_ = "";
      private Object contentEncoded_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ClientboundCloudDownloadResponse_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ClientboundCloudDownloadResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundCloudDownloadResponse.class, ClientboundCloudDownloadResponse.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ClientboundCloudDownloadResponse.Builder clear() {
         super.clear();
         this.id_ = "";
         this.contentEncoded_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ClientboundCloudDownloadResponse_descriptor;
      }

      public ClientboundCloudDownloadResponse getDefaultInstanceForType() {
         return ClientboundCloudDownloadResponse.getDefaultInstance();
      }

      public ClientboundCloudDownloadResponse build() {
         ClientboundCloudDownloadResponse result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundCloudDownloadResponse buildPartial() {
         ClientboundCloudDownloadResponse result = new ClientboundCloudDownloadResponse(this);
         result.id_ = this.id_;
         result.contentEncoded_ = this.contentEncoded_;
         this.onBuilt();
         return result;
      }

      public ClientboundCloudDownloadResponse.Builder clone() {
         return (ClientboundCloudDownloadResponse.Builder)super.clone();
      }

      public ClientboundCloudDownloadResponse.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundCloudDownloadResponse.Builder)super.setField(field, value);
      }

      public ClientboundCloudDownloadResponse.Builder clearField(FieldDescriptor field) {
         return (ClientboundCloudDownloadResponse.Builder)super.clearField(field);
      }

      public ClientboundCloudDownloadResponse.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundCloudDownloadResponse.Builder)super.clearOneof(oneof);
      }

      public ClientboundCloudDownloadResponse.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundCloudDownloadResponse.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundCloudDownloadResponse.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundCloudDownloadResponse.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundCloudDownloadResponse.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundCloudDownloadResponse) {
            return this.mergeFrom((ClientboundCloudDownloadResponse)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundCloudDownloadResponse.Builder mergeFrom(ClientboundCloudDownloadResponse other) {
         if (other == ClientboundCloudDownloadResponse.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (!other.getContentEncoded().isEmpty()) {
            this.contentEncoded_ = other.contentEncoded_;
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

      public ClientboundCloudDownloadResponse.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.contentEncoded_ = input.readStringRequireUtf8();
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

      public ClientboundCloudDownloadResponse.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundCloudDownloadResponse.Builder clearId() {
         this.id_ = ClientboundCloudDownloadResponse.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ClientboundCloudDownloadResponse.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundCloudDownloadResponse.Method555(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getContentEncoded() {
         Object ref = this.contentEncoded_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.contentEncoded_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getContentEncodedBytes() {
         Object ref = this.contentEncoded_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.contentEncoded_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ClientboundCloudDownloadResponse.Builder setContentEncoded(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.contentEncoded_ = value;
         this.onChanged();
         return this;
      }

      public ClientboundCloudDownloadResponse.Builder clearContentEncoded() {
         this.contentEncoded_ = ClientboundCloudDownloadResponse.getDefaultInstance().getContentEncoded();
         this.onChanged();
         return this;
      }

      public ClientboundCloudDownloadResponse.Builder setContentEncodedBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ClientboundCloudDownloadResponse.Method556(value);
         this.contentEncoded_ = value;
         this.onChanged();
         return this;
      }

      public final ClientboundCloudDownloadResponse.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudDownloadResponse.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundCloudDownloadResponse.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundCloudDownloadResponse.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundCloudDownloadResponse> {
      public ClientboundCloudDownloadResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundCloudDownloadResponse.Builder builder = ClientboundCloudDownloadResponse.newBuilder();

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
