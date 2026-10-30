package com.moon.connection.protobuf.telemetry;

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

public final class ServerboundAccountLoginMarker extends GeneratedMessageV3 implements ServerboundAccountLoginMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object name_;
   private volatile Object uuid_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundAccountLoginMarker DEFAULT_INSTANCE = new ServerboundAccountLoginMarker();
   private static final com.google.protobuf.Parser<ServerboundAccountLoginMarker> PARSER = new ServerboundAccountLoginMarker.Parser();

   private ServerboundAccountLoginMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundAccountLoginMarker() {
      this.name_ = "";
      this.uuid_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundAccountLoginMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundAccountLoginMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundAccountLoginMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundAccountLoginMarker.class, ServerboundAccountLoginMarker.Builder.class);
   }

   @Override
   public String getName() {
      Object ref = this.name_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.name_ = s;
      return s;
   }

   @Override
   public ByteString getNameBytes() {
      Object ref = this.name_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.name_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getUuid() {
      Object ref = this.uuid_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.uuid_ = s;
      return s;
   }

   @Override
   public ByteString getUuidBytes() {
      Object ref = this.uuid_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.uuid_ = b;
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
      if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
         GeneratedMessageV3.writeString(output, 1, this.name_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.uuid_)) {
         GeneratedMessageV3.writeString(output, 2, this.uuid_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.name_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.name_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.uuid_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.uuid_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundAccountLoginMarker otherMarker)) {
         return super.equals(other);
      } else if (!this.getName().equals(otherMarker.getName())) {
         return false;
      } else {
         return !this.getUuid().equals(otherMarker.getUuid()) ? false : this.getUnknownFields().equals(otherMarker.getUnknownFields());
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
      hash = 53 * hash + this.getName().hashCode();
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getUuid().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundAccountLoginMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAccountLoginMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAccountLoginMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAccountLoginMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAccountLoginMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundAccountLoginMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundAccountLoginMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundAccountLoginMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundAccountLoginMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundAccountLoginMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundAccountLoginMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundAccountLoginMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundAccountLoginMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundAccountLoginMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundAccountLoginMarker.Builder newBuilder(ServerboundAccountLoginMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundAccountLoginMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundAccountLoginMarker.Builder() : new ServerboundAccountLoginMarker.Builder().mergeFrom(this);
   }

   protected ServerboundAccountLoginMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundAccountLoginMarker.Builder(parent);
   }

   public static ServerboundAccountLoginMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundAccountLoginMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundAccountLoginMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundAccountLoginMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void checkNameByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void checkUuidByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundAccountLoginMarker.Builder>
      implements ServerboundAccountLoginMarkerOrBuilder {
      private Object name_ = "";
      private Object uuid_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundAccountLoginMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundAccountLoginMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundAccountLoginMarker.class, ServerboundAccountLoginMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundAccountLoginMarker.Builder clear() {
         super.clear();
         this.name_ = "";
         this.uuid_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundAccountLoginMarker_descriptor;
      }

      public ServerboundAccountLoginMarker getDefaultInstanceForType() {
         return ServerboundAccountLoginMarker.getDefaultInstance();
      }

      public ServerboundAccountLoginMarker build() {
         ServerboundAccountLoginMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundAccountLoginMarker buildPartial() {
         ServerboundAccountLoginMarker result = new ServerboundAccountLoginMarker(this);
         result.name_ = this.name_;
         result.uuid_ = this.uuid_;
         this.onBuilt();
         return result;
      }

      public ServerboundAccountLoginMarker.Builder clone() {
         return (ServerboundAccountLoginMarker.Builder)super.clone();
      }

      public ServerboundAccountLoginMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundAccountLoginMarker.Builder)super.setField(field, value);
      }

      public ServerboundAccountLoginMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundAccountLoginMarker.Builder)super.clearField(field);
      }

      public ServerboundAccountLoginMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundAccountLoginMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundAccountLoginMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundAccountLoginMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundAccountLoginMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundAccountLoginMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundAccountLoginMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundAccountLoginMarker) {
            return this.mergeFrom((ServerboundAccountLoginMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundAccountLoginMarker.Builder mergeFrom(ServerboundAccountLoginMarker other) {
         if (other == ServerboundAccountLoginMarker.getDefaultInstance()) {
            return this;
         }

         if (!other.getName().isEmpty()) {
            this.name_ = other.name_;
            this.onChanged();
         }

         if (!other.getUuid().isEmpty()) {
            this.uuid_ = other.uuid_;
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

      public ServerboundAccountLoginMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.name_ = input.readStringRequireUtf8();
                     break;
                  case 18:
                     this.uuid_ = input.readStringRequireUtf8();
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
      public String getName() {
         Object ref = this.name_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.name_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getNameBytes() {
         Object ref = this.name_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundAccountLoginMarker.Builder setName(String name) {
         if (name == null) {
            throw new NullPointerException();
         }

         this.name_ = name;
         this.onChanged();
         return this;
      }

      public ServerboundAccountLoginMarker.Builder clearName() {
         this.name_ = ServerboundAccountLoginMarker.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public ServerboundAccountLoginMarker.Builder setNameBytes(ByteString nameBytes) {
         if (nameBytes == null) {
            throw new NullPointerException();
         }

         ServerboundAccountLoginMarker.checkNameByteStringIsUtf8(nameBytes);
         this.name_ = nameBytes;
         this.onChanged();
         return this;
      }

      @Override
      public String getUuid() {
         Object ref = this.uuid_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.uuid_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getUuidBytes() {
         Object ref = this.uuid_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.uuid_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundAccountLoginMarker.Builder setUuid(String uuid) {
         if (uuid == null) {
            throw new NullPointerException();
         }

         this.uuid_ = uuid;
         this.onChanged();
         return this;
      }

      public ServerboundAccountLoginMarker.Builder clearUuid() {
         this.uuid_ = ServerboundAccountLoginMarker.getDefaultInstance().getUuid();
         this.onChanged();
         return this;
      }

      public ServerboundAccountLoginMarker.Builder setUuidBytes(ByteString uuidBytes) {
         if (uuidBytes == null) {
            throw new NullPointerException();
         }

         ServerboundAccountLoginMarker.checkUuidByteStringIsUtf8(uuidBytes);
         this.uuid_ = uuidBytes;
         this.onChanged();
         return this;
      }

      public final ServerboundAccountLoginMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundAccountLoginMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundAccountLoginMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundAccountLoginMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundAccountLoginMarker> {
      public ServerboundAccountLoginMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundAccountLoginMarker.Builder builder = ServerboundAccountLoginMarker.newBuilder();

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
