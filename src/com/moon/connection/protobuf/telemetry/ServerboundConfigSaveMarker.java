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

public final class ServerboundConfigSaveMarker extends GeneratedMessageV3 implements ServerboundConfigSaveMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object name_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundConfigSaveMarker DEFAULT_INSTANCE = new ServerboundConfigSaveMarker();
   private static final com.google.protobuf.Parser<ServerboundConfigSaveMarker> PARSER = new ServerboundConfigSaveMarker.Parser();

   private ServerboundConfigSaveMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundConfigSaveMarker() {
      this.name_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundConfigSaveMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundConfigSaveMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundConfigSaveMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundConfigSaveMarker.class, ServerboundConfigSaveMarker.Builder.class);
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
      } else if (!(other instanceof ServerboundConfigSaveMarker otherMarker)) {
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

   public static ServerboundConfigSaveMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigSaveMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigSaveMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigSaveMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigSaveMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundConfigSaveMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundConfigSaveMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundConfigSaveMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundConfigSaveMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundConfigSaveMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundConfigSaveMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundConfigSaveMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundConfigSaveMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundConfigSaveMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundConfigSaveMarker.Builder newBuilder(ServerboundConfigSaveMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundConfigSaveMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundConfigSaveMarker.Builder() : new ServerboundConfigSaveMarker.Builder().mergeFrom(this);
   }

   protected ServerboundConfigSaveMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundConfigSaveMarker.Builder(parent);
   }

   public static ServerboundConfigSaveMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundConfigSaveMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundConfigSaveMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundConfigSaveMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundConfigSaveMarker.Builder> implements ServerboundConfigSaveMarkerOrBuilder {
      private Object name_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundConfigSaveMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundConfigSaveMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundConfigSaveMarker.class, ServerboundConfigSaveMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundConfigSaveMarker.Builder clear() {
         super.clear();
         this.name_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundConfigSaveMarker_descriptor;
      }

      public ServerboundConfigSaveMarker getDefaultInstanceForType() {
         return ServerboundConfigSaveMarker.getDefaultInstance();
      }

      public ServerboundConfigSaveMarker build() {
         ServerboundConfigSaveMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundConfigSaveMarker buildPartial() {
         ServerboundConfigSaveMarker result = new ServerboundConfigSaveMarker(this);
         result.name_ = this.name_;
         this.onBuilt();
         return result;
      }

      public ServerboundConfigSaveMarker.Builder clone() {
         return (ServerboundConfigSaveMarker.Builder)super.clone();
      }

      public ServerboundConfigSaveMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundConfigSaveMarker.Builder)super.setField(field, value);
      }

      public ServerboundConfigSaveMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundConfigSaveMarker.Builder)super.clearField(field);
      }

      public ServerboundConfigSaveMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundConfigSaveMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundConfigSaveMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundConfigSaveMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundConfigSaveMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundConfigSaveMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundConfigSaveMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundConfigSaveMarker) {
            return this.mergeFrom((ServerboundConfigSaveMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundConfigSaveMarker.Builder mergeFrom(ServerboundConfigSaveMarker other) {
         if (other == ServerboundConfigSaveMarker.getDefaultInstance()) {
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

      public ServerboundConfigSaveMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundConfigSaveMarker.Builder setName(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.name_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundConfigSaveMarker.Builder clearName() {
         this.name_ = ServerboundConfigSaveMarker.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public ServerboundConfigSaveMarker.Builder setNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundConfigSaveMarker.checkByteStringIsUtf8(value);
         this.name_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundConfigSaveMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundConfigSaveMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundConfigSaveMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundConfigSaveMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundConfigSaveMarker> {
      public ServerboundConfigSaveMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundConfigSaveMarker.Builder builder = ServerboundConfigSaveMarker.newBuilder();

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
