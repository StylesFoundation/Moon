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

public final class ServerboundConfigLoadMarker extends GeneratedMessageV3 implements ServerboundConfigLoadMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object name_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundConfigLoadMarker DEFAULT_INSTANCE = new ServerboundConfigLoadMarker();
   private static final com.google.protobuf.Parser<ServerboundConfigLoadMarker> PARSER = new ServerboundConfigLoadMarker.Parser();

   private ServerboundConfigLoadMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundConfigLoadMarker() {
      this.name_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundConfigLoadMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundConfigLoadMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundConfigLoadMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundConfigLoadMarker.class, ServerboundConfigLoadMarker.Builder.class);
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

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundConfigLoadMarker otherMarker)) {
         return super.equals(other);
      } else {
         return !this.getName().equals(otherMarker.getName()) ? false : this.getUnknownFields().equals(otherMarker.getUnknownFields());
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
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundConfigLoadMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigLoadMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigLoadMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigLoadMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigLoadMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigLoadMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigLoadMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundConfigLoadMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundConfigLoadMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundConfigLoadMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundConfigLoadMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundConfigLoadMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundConfigLoadMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundConfigLoadMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundConfigLoadMarker.Builder newBuilder(ServerboundConfigLoadMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundConfigLoadMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundConfigLoadMarker.Builder() : new ServerboundConfigLoadMarker.Builder().mergeFrom(this);
   }

   protected ServerboundConfigLoadMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundConfigLoadMarker.Builder(parent);
   }

   public static ServerboundConfigLoadMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundConfigLoadMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundConfigLoadMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundConfigLoadMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundConfigLoadMarker.Builder> implements ServerboundConfigLoadMarkerOrBuilder {
      private Object name_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundConfigLoadMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundConfigLoadMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundConfigLoadMarker.class, ServerboundConfigLoadMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundConfigLoadMarker.Builder clear() {
         super.clear();
         this.name_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundConfigLoadMarker_descriptor;
      }

      public ServerboundConfigLoadMarker getDefaultInstanceForType() {
         return ServerboundConfigLoadMarker.getDefaultInstance();
      }

      public ServerboundConfigLoadMarker build() {
         ServerboundConfigLoadMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundConfigLoadMarker buildPartial() {
         ServerboundConfigLoadMarker result = new ServerboundConfigLoadMarker(this);
         result.name_ = this.name_;
         this.onBuilt();
         return result;
      }

      public ServerboundConfigLoadMarker.Builder clone() {
         return (ServerboundConfigLoadMarker.Builder)super.clone();
      }

      public ServerboundConfigLoadMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundConfigLoadMarker.Builder)super.setField(field, value);
      }

      public ServerboundConfigLoadMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundConfigLoadMarker.Builder)super.clearField(field);
      }

      public ServerboundConfigLoadMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundConfigLoadMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundConfigLoadMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundConfigLoadMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundConfigLoadMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundConfigLoadMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundConfigLoadMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundConfigLoadMarker) {
            return this.mergeFrom((ServerboundConfigLoadMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundConfigLoadMarker.Builder mergeFrom(ServerboundConfigLoadMarker other) {
         if (other == ServerboundConfigLoadMarker.getDefaultInstance()) {
            return this;
         }

         if (!other.getName().isEmpty()) {
            this.name_ = other.name_;
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

      public ServerboundConfigLoadMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundConfigLoadMarker.Builder setName(String name) {
         if (name == null) {
            throw new NullPointerException();
         }

         this.name_ = name;
         this.onChanged();
         return this;
      }

      public ServerboundConfigLoadMarker.Builder clearName() {
         this.name_ = ServerboundConfigLoadMarker.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public ServerboundConfigLoadMarker.Builder setNameBytes(ByteString nameBytes) {
         if (nameBytes == null) {
            throw new NullPointerException();
         }

         ServerboundConfigLoadMarker.checkByteStringIsUtf8(nameBytes);
         this.name_ = nameBytes;
         this.onChanged();
         return this;
      }

      public final ServerboundConfigLoadMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundConfigLoadMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundConfigLoadMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundConfigLoadMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundConfigLoadMarker> {
      public ServerboundConfigLoadMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundConfigLoadMarker.Builder builder = ServerboundConfigLoadMarker.newBuilder();

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
