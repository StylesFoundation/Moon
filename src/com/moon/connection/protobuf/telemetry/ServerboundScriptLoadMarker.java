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

public final class ServerboundScriptLoadMarker extends GeneratedMessageV3 implements ServerboundScriptLoadMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object name_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundScriptLoadMarker DEFAULT_INSTANCE = new ServerboundScriptLoadMarker();
   private static final com.google.protobuf.Parser<ServerboundScriptLoadMarker> PARSER = new ServerboundScriptLoadMarker.Parser();

   private ServerboundScriptLoadMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundScriptLoadMarker() {
      this.name_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundScriptLoadMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundScriptLoadMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundScriptLoadMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundScriptLoadMarker.class, ServerboundScriptLoadMarker.Builder.class);
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
      } else if (!(other instanceof ServerboundScriptLoadMarker otherMarker)) {
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

   public static ServerboundScriptLoadMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundScriptLoadMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundScriptLoadMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundScriptLoadMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundScriptLoadMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundScriptLoadMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundScriptLoadMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundScriptLoadMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundScriptLoadMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundScriptLoadMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundScriptLoadMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundScriptLoadMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundScriptLoadMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundScriptLoadMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundScriptLoadMarker.Builder newBuilder(ServerboundScriptLoadMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundScriptLoadMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundScriptLoadMarker.Builder() : new ServerboundScriptLoadMarker.Builder().mergeFrom(this);
   }

   protected ServerboundScriptLoadMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundScriptLoadMarker.Builder(parent);
   }

   public static ServerboundScriptLoadMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundScriptLoadMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundScriptLoadMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundScriptLoadMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundScriptLoadMarker.Builder> implements ServerboundScriptLoadMarkerOrBuilder {
      private Object name_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundScriptLoadMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundScriptLoadMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundScriptLoadMarker.class, ServerboundScriptLoadMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundScriptLoadMarker.Builder clear() {
         super.clear();
         this.name_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundScriptLoadMarker_descriptor;
      }

      public ServerboundScriptLoadMarker getDefaultInstanceForType() {
         return ServerboundScriptLoadMarker.getDefaultInstance();
      }

      public ServerboundScriptLoadMarker build() {
         ServerboundScriptLoadMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundScriptLoadMarker buildPartial() {
         ServerboundScriptLoadMarker result = new ServerboundScriptLoadMarker(this);
         result.name_ = this.name_;
         this.onBuilt();
         return result;
      }

      public ServerboundScriptLoadMarker.Builder clone() {
         return (ServerboundScriptLoadMarker.Builder)super.clone();
      }

      public ServerboundScriptLoadMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundScriptLoadMarker.Builder)super.setField(field, value);
      }

      public ServerboundScriptLoadMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundScriptLoadMarker.Builder)super.clearField(field);
      }

      public ServerboundScriptLoadMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundScriptLoadMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundScriptLoadMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundScriptLoadMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundScriptLoadMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundScriptLoadMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundScriptLoadMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundScriptLoadMarker) {
            return this.mergeFrom((ServerboundScriptLoadMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundScriptLoadMarker.Builder mergeFrom(ServerboundScriptLoadMarker other) {
         if (other == ServerboundScriptLoadMarker.getDefaultInstance()) {
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

      public ServerboundScriptLoadMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundScriptLoadMarker.Builder setName(String value) {
         if (value == null) {
            throw new NullPointerException();
         }

         this.name_ = value;
         this.onChanged();
         return this;
      }

      public ServerboundScriptLoadMarker.Builder clearName() {
         this.name_ = ServerboundScriptLoadMarker.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public ServerboundScriptLoadMarker.Builder setNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         ServerboundScriptLoadMarker.checkByteStringIsUtf8(value);
         this.name_ = value;
         this.onChanged();
         return this;
      }

      public final ServerboundScriptLoadMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundScriptLoadMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundScriptLoadMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundScriptLoadMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundScriptLoadMarker> {
      public ServerboundScriptLoadMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundScriptLoadMarker.Builder builder = ServerboundScriptLoadMarker.newBuilder();

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
