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

public final class ServerboundJoinMarker extends GeneratedMessageV3 implements ServerboundJoinMarkerOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object server_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundJoinMarker DEFAULT_INSTANCE = new ServerboundJoinMarker();
   private static final com.google.protobuf.Parser<ServerboundJoinMarker> PARSER = new ServerboundJoinMarker.Parser();

   private ServerboundJoinMarker(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundJoinMarker() {
      this.server_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundJoinMarker();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return TelemetryProto.internal_static_ServerboundJoinMarker_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return TelemetryProto.internal_static_ServerboundJoinMarker_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundJoinMarker.class, ServerboundJoinMarker.Builder.class);
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
      } else if (!(other instanceof ServerboundJoinMarker otherMarker)) {
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

   public static ServerboundJoinMarker parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundJoinMarker parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundJoinMarker parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundJoinMarker parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundJoinMarker parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundJoinMarker parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundJoinMarker parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundJoinMarker parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundJoinMarker parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundJoinMarker parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundJoinMarker parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundJoinMarker parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundJoinMarker.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundJoinMarker.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundJoinMarker.Builder newBuilder(ServerboundJoinMarker prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundJoinMarker.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundJoinMarker.Builder() : new ServerboundJoinMarker.Builder().mergeFrom(this);
   }

   protected ServerboundJoinMarker.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundJoinMarker.Builder(parent);
   }

   public static ServerboundJoinMarker getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundJoinMarker> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundJoinMarker> getParserForType() {
      return PARSER;
   }

   public ServerboundJoinMarker getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundJoinMarker.Builder> implements ServerboundJoinMarkerOrBuilder {
      private Object server_ = "";

      public static final Descriptor getDescriptor() {
         return TelemetryProto.internal_static_ServerboundJoinMarker_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return TelemetryProto.internal_static_ServerboundJoinMarker_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundJoinMarker.class, ServerboundJoinMarker.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundJoinMarker.Builder clear() {
         super.clear();
         this.server_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return TelemetryProto.internal_static_ServerboundJoinMarker_descriptor;
      }

      public ServerboundJoinMarker getDefaultInstanceForType() {
         return ServerboundJoinMarker.getDefaultInstance();
      }

      public ServerboundJoinMarker build() {
         ServerboundJoinMarker result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundJoinMarker buildPartial() {
         ServerboundJoinMarker result = new ServerboundJoinMarker(this);
         result.server_ = this.server_;
         this.onBuilt();
         return result;
      }

      public ServerboundJoinMarker.Builder clone() {
         return (ServerboundJoinMarker.Builder)super.clone();
      }

      public ServerboundJoinMarker.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundJoinMarker.Builder)super.setField(field, value);
      }

      public ServerboundJoinMarker.Builder clearField(FieldDescriptor field) {
         return (ServerboundJoinMarker.Builder)super.clearField(field);
      }

      public ServerboundJoinMarker.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundJoinMarker.Builder)super.clearOneof(oneof);
      }

      public ServerboundJoinMarker.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundJoinMarker.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundJoinMarker.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundJoinMarker.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundJoinMarker.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundJoinMarker) {
            return this.mergeFrom((ServerboundJoinMarker)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundJoinMarker.Builder mergeFrom(ServerboundJoinMarker other) {
         if (other == ServerboundJoinMarker.getDefaultInstance()) {
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

      public ServerboundJoinMarker.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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

      public ServerboundJoinMarker.Builder setServer(String server) {
         if (server == null) {
            throw new NullPointerException();
         }

         this.server_ = server;
         this.onChanged();
         return this;
      }

      public ServerboundJoinMarker.Builder clearServer() {
         this.server_ = ServerboundJoinMarker.getDefaultInstance().getServer();
         this.onChanged();
         return this;
      }

      public ServerboundJoinMarker.Builder setServerBytes(ByteString serverBytes) {
         if (serverBytes == null) {
            throw new NullPointerException();
         }

         ServerboundJoinMarker.checkByteStringIsUtf8(serverBytes);
         this.server_ = serverBytes;
         this.onChanged();
         return this;
      }

      public final ServerboundJoinMarker.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundJoinMarker.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundJoinMarker.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundJoinMarker.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundJoinMarker> {
      public ServerboundJoinMarker parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundJoinMarker.Builder builder = ServerboundJoinMarker.newBuilder();

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
