package com.moon.connection.protobuf.sync;

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

public final class ServerboundUpdateGameSync extends GeneratedMessageV3 implements ServerboundUpdateGameSyncOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object gameName_;
   private volatile Object gameServer_;
   private byte memoizedIsInitialized = -1;
   private static final ServerboundUpdateGameSync DEFAULT_INSTANCE = new ServerboundUpdateGameSync();
   private static final com.google.protobuf.Parser<ServerboundUpdateGameSync> PARSER = new ServerboundUpdateGameSync.Parser();

   private ServerboundUpdateGameSync(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ServerboundUpdateGameSync() {
      this.gameName_ = "";
      this.gameServer_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ServerboundUpdateGameSync();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return SyncProto.internal_static_ServerboundUpdateGameSync_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return SyncProto.internal_static_ServerboundUpdateGameSync_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ServerboundUpdateGameSync.class, ServerboundUpdateGameSync.Builder.class);
   }

   @Override
   public String getGameName() {
      Object ref = this.gameName_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.gameName_ = s;
      return s;
   }

   @Override
   public ByteString getGameNameBytes() {
      Object ref = this.gameName_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.gameName_ = b;
         return b;
      } else {
         return (ByteString)ref;
      }
   }

   @Override
   public String getGameServer() {
      Object ref = this.gameServer_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.gameServer_ = s;
      return s;
   }

   @Override
   public ByteString getGameServerBytes() {
      Object ref = this.gameServer_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.gameServer_ = b;
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
      if (!GeneratedMessageV3.isStringEmpty(this.gameName_)) {
         GeneratedMessageV3.writeString(output, 1, this.gameName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.gameServer_)) {
         GeneratedMessageV3.writeString(output, 2, this.gameServer_);
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
      if (!GeneratedMessageV3.isStringEmpty(this.gameName_)) {
         size += GeneratedMessageV3.computeStringSize(1, this.gameName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.gameServer_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.gameServer_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ServerboundUpdateGameSync otherMessage)) {
         return super.equals(other);
      } else if (!this.getGameName().equals(otherMessage.getGameName())) {
         return false;
      } else {
         return !this.getGameServer().equals(otherMessage.getGameServer()) ? false : this.getUnknownFields().equals(otherMessage.getUnknownFields());
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
      hash = 53 * hash + this.getGameName().hashCode();
      hash = 37 * hash + 2;
      hash = 53 * hash + this.getGameServer().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ServerboundUpdateGameSync parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundUpdateGameSync parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundUpdateGameSync parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundUpdateGameSync parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundUpdateGameSync parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ServerboundUpdateGameSync parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ServerboundUpdateGameSync parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundUpdateGameSync parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundUpdateGameSync parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ServerboundUpdateGameSync parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ServerboundUpdateGameSync parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ServerboundUpdateGameSync parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ServerboundUpdateGameSync.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ServerboundUpdateGameSync.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ServerboundUpdateGameSync.Builder newBuilder(ServerboundUpdateGameSync prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ServerboundUpdateGameSync.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ServerboundUpdateGameSync.Builder() : new ServerboundUpdateGameSync.Builder().mergeFrom(this);
   }

   protected ServerboundUpdateGameSync.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ServerboundUpdateGameSync.Builder(parent);
   }

   public static ServerboundUpdateGameSync getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ServerboundUpdateGameSync> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ServerboundUpdateGameSync> getParserForType() {
      return PARSER;
   }

   public ServerboundUpdateGameSync getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method536(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ServerboundUpdateGameSync.Builder> implements ServerboundUpdateGameSyncOrBuilder {
      private Object gameName_ = "";
      private Object gameServer_ = "";

      public static final Descriptor getDescriptor() {
         return SyncProto.internal_static_ServerboundUpdateGameSync_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return SyncProto.internal_static_ServerboundUpdateGameSync_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ServerboundUpdateGameSync.class, ServerboundUpdateGameSync.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public ServerboundUpdateGameSync.Builder clear() {
         super.clear();
         this.gameName_ = "";
         this.gameServer_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return SyncProto.internal_static_ServerboundUpdateGameSync_descriptor;
      }

      public ServerboundUpdateGameSync getDefaultInstanceForType() {
         return ServerboundUpdateGameSync.getDefaultInstance();
      }

      public ServerboundUpdateGameSync build() {
         ServerboundUpdateGameSync result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ServerboundUpdateGameSync buildPartial() {
         ServerboundUpdateGameSync result = new ServerboundUpdateGameSync(this);
         result.gameName_ = this.gameName_;
         result.gameServer_ = this.gameServer_;
         this.onBuilt();
         return result;
      }

      public ServerboundUpdateGameSync.Builder clone() {
         return (ServerboundUpdateGameSync.Builder)super.clone();
      }

      public ServerboundUpdateGameSync.Builder setField(FieldDescriptor field, Object value) {
         return (ServerboundUpdateGameSync.Builder)super.setField(field, value);
      }

      public ServerboundUpdateGameSync.Builder clearField(FieldDescriptor field) {
         return (ServerboundUpdateGameSync.Builder)super.clearField(field);
      }

      public ServerboundUpdateGameSync.Builder clearOneof(OneofDescriptor oneof) {
         return (ServerboundUpdateGameSync.Builder)super.clearOneof(oneof);
      }

      public ServerboundUpdateGameSync.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ServerboundUpdateGameSync.Builder)super.setRepeatedField(field, index, value);
      }

      public ServerboundUpdateGameSync.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ServerboundUpdateGameSync.Builder)super.addRepeatedField(field, value);
      }

      public ServerboundUpdateGameSync.Builder mergeFrom(Message other) {
         if (other instanceof ServerboundUpdateGameSync) {
            return this.mergeFrom((ServerboundUpdateGameSync)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ServerboundUpdateGameSync.Builder mergeFrom(ServerboundUpdateGameSync other) {
         if (other == ServerboundUpdateGameSync.getDefaultInstance()) {
            return this;
         }

         if (!other.getGameName().isEmpty()) {
            this.gameName_ = other.gameName_;
            this.onChanged();
         }

         if (!other.getGameServer().isEmpty()) {
            this.gameServer_ = other.gameServer_;
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

      public ServerboundUpdateGameSync.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.gameName_ = input.readStringRequireUtf8();
                     break;
                  case 18:
                     this.gameServer_ = input.readStringRequireUtf8();
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
      public String getGameName() {
         Object ref = this.gameName_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.gameName_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getGameNameBytes() {
         Object ref = this.gameName_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.gameName_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundUpdateGameSync.Builder setGameName(String gameName) {
         if (gameName == null) {
            throw new NullPointerException();
         }

         this.gameName_ = gameName;
         this.onChanged();
         return this;
      }

      public ServerboundUpdateGameSync.Builder clearGameName() {
         this.gameName_ = ServerboundUpdateGameSync.getDefaultInstance().getGameName();
         this.onChanged();
         return this;
      }

      public ServerboundUpdateGameSync.Builder setGameNameBytes(ByteString gameNameBytes) {
         if (gameNameBytes == null) {
            throw new NullPointerException();
         }

         ServerboundUpdateGameSync.checkByteStringIsUtf8(gameNameBytes);
         this.gameName_ = gameNameBytes;
         this.onChanged();
         return this;
      }

      @Override
      public String getGameServer() {
         Object ref = this.gameServer_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.gameServer_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getGameServerBytes() {
         Object ref = this.gameServer_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.gameServer_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public ServerboundUpdateGameSync.Builder setGameServer(String gameServer) {
         if (gameServer == null) {
            throw new NullPointerException();
         }

         this.gameServer_ = gameServer;
         this.onChanged();
         return this;
      }

      public ServerboundUpdateGameSync.Builder clearGameServer() {
         this.gameServer_ = ServerboundUpdateGameSync.getDefaultInstance().getGameServer();
         this.onChanged();
         return this;
      }

      public ServerboundUpdateGameSync.Builder setGameServerBytes(ByteString gameServerBytes) {
         if (gameServerBytes == null) {
            throw new NullPointerException();
         }

         ServerboundUpdateGameSync.Method536(gameServerBytes);
         this.gameServer_ = gameServerBytes;
         this.onChanged();
         return this;
      }

      public final ServerboundUpdateGameSync.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundUpdateGameSync.Builder)super.setUnknownFields(unknownFields);
      }

      public final ServerboundUpdateGameSync.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ServerboundUpdateGameSync.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ServerboundUpdateGameSync> {
      public ServerboundUpdateGameSync parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ServerboundUpdateGameSync.Builder builder = ServerboundUpdateGameSync.newBuilder();

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
