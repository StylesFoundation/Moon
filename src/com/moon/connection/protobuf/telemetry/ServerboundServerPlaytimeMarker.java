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

public final class ServerboundServerPlaytimeMarker extends GeneratedMessageV3 implements ServerboundServerPlaytimeMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object server_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundServerPlaytimeMarker DEFAULT_INSTANCE = new ServerboundServerPlaytimeMarker();
   private static final com.google.protobuf.Parser<ServerboundServerPlaytimeMarker> PARSER = new ServerboundServerPlaytimeMarker.Parser();

   private ServerboundServerPlaytimeMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundServerPlaytimeMarker() {
      this.server_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundServerPlaytimeMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundServerPlaytimeMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundServerPlaytimeMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundServerPlaytimeMarker.class, ServerboundServerPlaytimeMarker.Builder.class);
   }

   @Override
   public String getServer() {
      Object ref = this.server_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.server_ = s;
      return s;
   }

   @Override
   public ByteString getServerBytes() {
      Object ref = this.server_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.server_ = b;
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
      if (!GeneratedMessageV3.isStringEmpty(this.server_)) {
         GeneratedMessageV3.writeString(output, 1, this.server_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.server_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.server_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundServerPlaytimeMarker otherMarker)) {
         return super.equals(other);
      } else {
         return !this.getServer().equals(otherMarker.getServer()) ? false : this.getUnknownFields().equals(otherMarker.getUnknownFields());
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
      hash = 53 * hash + this.getServer().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundServerPlaytimeMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundServerPlaytimeMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundServerPlaytimeMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundServerPlaytimeMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundServerPlaytimeMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundServerPlaytimeMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundServerPlaytimeMarker.Builder newBuilder(ServerboundServerPlaytimeMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundServerPlaytimeMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundServerPlaytimeMarker.Builder() : new ServerboundServerPlaytimeMarker.Builder().mergeFrom(this);
   }

   protected ServerboundServerPlaytimeMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundServerPlaytimeMarker.Builder(parent);
   }

   public static ServerboundServerPlaytimeMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundServerPlaytimeMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundServerPlaytimeMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundServerPlaytimeMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder
      extends GeneratedMessageV3.Builder<ServerboundServerPlaytimeMarker.Builder>
      implements ServerboundServerPlaytimeMarkerOrBuilder {
      private Object server_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundServerPlaytimeMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundServerPlaytimeMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundServerPlaytimeMarker.class, ServerboundServerPlaytimeMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ServerboundServerPlaytimeMarker.Builder clear() {
         super.clear();
         this.server_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundServerPlaytimeMarker_descriptor;
      }

      public ServerboundServerPlaytimeMarker getDefaultInstanceForType() {
         return ServerboundServerPlaytimeMarker.getDefaultInstance();
      }

      public ServerboundServerPlaytimeMarker build() {
         ServerboundServerPlaytimeMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundServerPlaytimeMarker buildPartial() {
         ServerboundServerPlaytimeMarker result = new ServerboundServerPlaytimeMarker(this);
         result.server_ = this.server_;
         this.onBuilt();
         return result;
      }

      public ServerboundServerPlaytimeMarker.Builder clone() {
         return (ServerboundServerPlaytimeMarker.Builder)super.clone();
      }

      public ServerboundServerPlaytimeMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundServerPlaytimeMarker.Builder)super.setField(field, value);
      }

      public ServerboundServerPlaytimeMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundServerPlaytimeMarker.Builder)super.clearField(field);
      }

      public ServerboundServerPlaytimeMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundServerPlaytimeMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundServerPlaytimeMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundServerPlaytimeMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundServerPlaytimeMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundServerPlaytimeMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundServerPlaytimeMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundServerPlaytimeMarker) {
            return this.mergeFrom((ServerboundServerPlaytimeMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundServerPlaytimeMarker.Builder mergeFrom(ServerboundServerPlaytimeMarker other) {
         if (other == ServerboundServerPlaytimeMarker.getDefaultInstance()) {
            return this;
         }

         if (!other.getServer().isEmpty()) {
            this.server_ = other.server_;
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

      public ServerboundServerPlaytimeMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.server_ = input.readStringRequireUtf8();
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
      public String getServer() {
         Object ref = this.server_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.server_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getServerBytes() {
         Object ref = this.server_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.server_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundServerPlaytimeMarker.Builder setServer(String server) {
         if (server == null) {
            throw new NullPointerException();
         }

         this.server_ = server;
         this.onChanged();
         return this;
      }

      public ServerboundServerPlaytimeMarker.Builder clearServer() {
         this.server_ = ServerboundServerPlaytimeMarker.getDefaultInstance().getServer();
         this.onChanged();
         return this;
      }

      public ServerboundServerPlaytimeMarker.Builder setServerBytes(ByteString serverBytes) {
         if (serverBytes == null) {
            throw new NullPointerException();
         }

         ServerboundServerPlaytimeMarker.checkByteStringIsUtf8(serverBytes);
         this.server_ = serverBytes;
         this.onChanged();
         return this;
      }

      public final ServerboundServerPlaytimeMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundServerPlaytimeMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundServerPlaytimeMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundServerPlaytimeMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundServerPlaytimeMarker> {
      public ServerboundServerPlaytimeMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundServerPlaytimeMarker.Builder builder = ServerboundServerPlaytimeMarker.newBuilder();

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
