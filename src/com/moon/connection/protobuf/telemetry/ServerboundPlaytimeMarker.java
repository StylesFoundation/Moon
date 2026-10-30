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

public final class ServerboundPlaytimeMarker extends GeneratedMessageV3 implements ServerboundPlaytimeMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundPlaytimeMarker DEFAULT_INSTANCE = new ServerboundPlaytimeMarker();
   private static final com.google.protobuf.Parser<ServerboundPlaytimeMarker> PARSER = new ServerboundPlaytimeMarker.Parser();

   private ServerboundPlaytimeMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundPlaytimeMarker() {
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundPlaytimeMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundPlaytimeMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundPlaytimeMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundPlaytimeMarker.class, ServerboundPlaytimeMarker.Builder.class);
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
      this.getUnknownFields().writeTo(output);
   }

   @Override
   public int getSerializedSize() {
      int size = this.memoizedSize;
      if (size != -1) {
         return size;
      }

      size = 0 + this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof ServerboundPlaytimeMarker otherMarker) ? super.equals(other) : this.getUnknownFields().equals(otherMarker.getUnknownFields());
      }
   }

   @Override
   public int hashCode() {
      if (this.memoizedHashCode != 0) {
         return this.memoizedHashCode;
      }

      int hash = 41;
      hash = 779 + getDescriptor().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundPlaytimeMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundPlaytimeMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundPlaytimeMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundPlaytimeMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundPlaytimeMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundPlaytimeMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundPlaytimeMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundPlaytimeMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundPlaytimeMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundPlaytimeMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundPlaytimeMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundPlaytimeMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundPlaytimeMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundPlaytimeMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundPlaytimeMarker.Builder newBuilder(ServerboundPlaytimeMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundPlaytimeMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundPlaytimeMarker.Builder() : new ServerboundPlaytimeMarker.Builder().mergeFrom(this);
   }

   protected ServerboundPlaytimeMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundPlaytimeMarker.Builder(parent);
   }

   public static ServerboundPlaytimeMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundPlaytimeMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundPlaytimeMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundPlaytimeMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundPlaytimeMarker.Builder> implements ServerboundPlaytimeMarkerOrBuilder {
      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundPlaytimeMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundPlaytimeMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundPlaytimeMarker.class, ServerboundPlaytimeMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundPlaytimeMarker.Builder clear() {
         super.clear();
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundPlaytimeMarker_descriptor;
      }

      public ServerboundPlaytimeMarker getDefaultInstanceForType() {
         return ServerboundPlaytimeMarker.getDefaultInstance();
      }

      public ServerboundPlaytimeMarker build() {
         ServerboundPlaytimeMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundPlaytimeMarker buildPartial() {
         ServerboundPlaytimeMarker result = new ServerboundPlaytimeMarker(this);
         this.onBuilt();
         return result;
      }

      public ServerboundPlaytimeMarker.Builder clone() {
         return (ServerboundPlaytimeMarker.Builder)super.clone();
      }

      public ServerboundPlaytimeMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundPlaytimeMarker.Builder)super.setField(field, value);
      }

      public ServerboundPlaytimeMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundPlaytimeMarker.Builder)super.clearField(field);
      }

      public ServerboundPlaytimeMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundPlaytimeMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundPlaytimeMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundPlaytimeMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundPlaytimeMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundPlaytimeMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundPlaytimeMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundPlaytimeMarker) {
            return this.mergeFrom((ServerboundPlaytimeMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundPlaytimeMarker.Builder mergeFrom(ServerboundPlaytimeMarker other) {
         if (other == ServerboundPlaytimeMarker.getDefaultInstance()) {
            return this;
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public ServerboundPlaytimeMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public final ServerboundPlaytimeMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundPlaytimeMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundPlaytimeMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundPlaytimeMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundPlaytimeMarker> {
      public ServerboundPlaytimeMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundPlaytimeMarker.Builder builder = ServerboundPlaytimeMarker.newBuilder();

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
