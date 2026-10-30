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

public final class ServerboundCloudUpdateDisplayNameRequest extends GeneratedMessageV3 implements ServerboundCloudUpdateDisplayNameRequestOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object id_;
   private volatile Object newName_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundCloudUpdateDisplayNameRequest DEFAULT_INSTANCE = new ServerboundCloudUpdateDisplayNameRequest();
   private static final com.google.protobuf.Parser<ServerboundCloudUpdateDisplayNameRequest> PARSER = new ServerboundCloudUpdateDisplayNameRequest.Parser();

   private ServerboundCloudUpdateDisplayNameRequest(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundCloudUpdateDisplayNameRequest() {
      this.id_ = "";
      this.newName_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundCloudUpdateDisplayNameRequest();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return CloudProto.internal_static_ServerboundCloudUpdateDisplayNameRequest_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return CloudProto.internal_static_ServerboundCloudUpdateDisplayNameRequest_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundCloudUpdateDisplayNameRequest.class, ServerboundCloudUpdateDisplayNameRequest.Builder.class);
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
   public String getNewName() {
      Object ref = this.newName_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.newName_ = s;
      return s;
   }

   @Override
   public ByteString getNewNameBytes() {
      Object ref = this.newName_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.newName_ = b;
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

      if (!GeneratedMessageV3.isStringEmpty(this.newName_)) {
         GeneratedMessageV3.writeString(output, 2, this.newName_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.newName_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.newName_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundCloudUpdateDisplayNameRequest that)) {
         return super.equals(other);
      } else if (!this.getId().equals(that.getId())) {
         return false;
      } else {
         return !this.getNewName().equals(that.getNewName()) ? false : this.getUnknownFields().equals(that.getUnknownFields());
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
      hash = 53 * hash + this.getNewName().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteString);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(byteString, extensionRegistryLite);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundCloudUpdateDisplayNameRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundCloudUpdateDisplayNameRequest.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundCloudUpdateDisplayNameRequest.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundCloudUpdateDisplayNameRequest.Builder newBuilder(ServerboundCloudUpdateDisplayNameRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundCloudUpdateDisplayNameRequest.Builder toBuilder() {
      return this == DEFAULT_INSTANCE
         ? new ServerboundCloudUpdateDisplayNameRequest.Builder()
         : new ServerboundCloudUpdateDisplayNameRequest.Builder().mergeFrom(this);
   }

   protected ServerboundCloudUpdateDisplayNameRequest.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundCloudUpdateDisplayNameRequest.Builder(parent);
   }

   public static ServerboundCloudUpdateDisplayNameRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundCloudUpdateDisplayNameRequest> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundCloudUpdateDisplayNameRequest> getParserForType() {
      return PARSER;
   }

   public ServerboundCloudUpdateDisplayNameRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method898(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method899(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundCloudUpdateDisplayNameRequest.Builder>
      implements ServerboundCloudUpdateDisplayNameRequestOrBuilder {
      private Object id_ = "";
      private Object newName_ = "";

      public static final Descriptor getDescriptor() {
         return CloudProto.internal_static_ServerboundCloudUpdateDisplayNameRequest_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return CloudProto.internal_static_ServerboundCloudUpdateDisplayNameRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundCloudUpdateDisplayNameRequest.class, ServerboundCloudUpdateDisplayNameRequest.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clear() {
         super.clear();
         this.id_ = "";
         this.newName_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return CloudProto.internal_static_ServerboundCloudUpdateDisplayNameRequest_descriptor;
      }

      public ServerboundCloudUpdateDisplayNameRequest getDefaultInstanceForType() {
         return ServerboundCloudUpdateDisplayNameRequest.getDefaultInstance();
      }

      public ServerboundCloudUpdateDisplayNameRequest build() {
         ServerboundCloudUpdateDisplayNameRequest result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundCloudUpdateDisplayNameRequest buildPartial() {
         ServerboundCloudUpdateDisplayNameRequest result = new ServerboundCloudUpdateDisplayNameRequest(this);
         result.id_ = this.id_;
         result.newName_ = this.newName_;
         this.onBuilt();
         return result;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clone() {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.clone();
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.setField(field, value);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clearField(FieldDescriptor field) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.clearField(field);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.clearOneof(oneof);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundCloudUpdateDisplayNameRequest) {
            return this.mergeFrom((ServerboundCloudUpdateDisplayNameRequest)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder mergeFrom(ServerboundCloudUpdateDisplayNameRequest other) {
         if (other == ServerboundCloudUpdateDisplayNameRequest.getDefaultInstance()) {
            return this;
         }

         if (!other.getId().isEmpty()) {
            this.id_ = other.id_;
            this.onChanged();
         }

         if (!other.getNewName().isEmpty()) {
            this.newName_ = other.newName_;
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

      public ServerboundCloudUpdateDisplayNameRequest.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.newName_ = input.readStringRequireUtf8();
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

      public ServerboundCloudUpdateDisplayNameRequest.Builder setId(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.id_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clearId() {
         this.id_ = ServerboundCloudUpdateDisplayNameRequest.getDefaultInstance().getId();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder setIdBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUpdateDisplayNameRequest.Method898(value);
         this.id_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getNewName() {
         Object ref = this.newName_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.newName_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getNewNameBytes() {
         Object ref = this.newName_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.newName_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder setNewName(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.newName_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder clearNewName() {
         this.newName_ = ServerboundCloudUpdateDisplayNameRequest.getDefaultInstance().getNewName();
         this.onChanged();
         return this;
      }

      public ServerboundCloudUpdateDisplayNameRequest.Builder setNewNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundCloudUpdateDisplayNameRequest.Method899(value);
         this.newName_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundCloudUpdateDisplayNameRequest.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundCloudUpdateDisplayNameRequest.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundCloudUpdateDisplayNameRequest.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundCloudUpdateDisplayNameRequest> {
      public ServerboundCloudUpdateDisplayNameRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundCloudUpdateDisplayNameRequest.Builder builder = ServerboundCloudUpdateDisplayNameRequest.newBuilder();

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
