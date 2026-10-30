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

public final class SynchronizedUser extends GeneratedMessageV3 implements SynchronizedUserOrBuilder {
   private static final long serialVersionUID = 0L;
   private volatile Object name_;
   private volatile Object gameName_;
   private volatile Object server_;
   private volatile Object color_;
   private byte memoizedIsInitialized = -1;
   private static final SynchronizedUser DEFAULT_INSTANCE = new SynchronizedUser();
   private static final com.google.protobuf.Parser<SynchronizedUser> PARSER = new SynchronizedUser.Parser();

   private SynchronizedUser(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private SynchronizedUser() {
      this.name_ = "";
      this.gameName_ = "";
      this.server_ = "";
      this.color_ = "";
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new SynchronizedUser();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return SyncProto.internal_static_SynchronizedUser_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return SyncProto.internal_static_SynchronizedUser_fieldAccessorTable
         .ensureFieldAccessorsInitialized(SynchronizedUser.class, SynchronizedUser.Builder.class);
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
   public String getColor() {
      Object ref = this.color_;
      if (ref instanceof String) {
         return (String)ref;
      }

      String s = ((ByteString)ref).toStringUtf8();
      this.color_ = s;
      return s;
   }

   @Override
   public ByteString getColorBytes() {
      Object ref = this.color_;
      if (ref instanceof String) {
         ByteString b = ByteString.copyFromUtf8((String)ref);
         this.color_ = b;
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

      if (!GeneratedMessageV3.isStringEmpty(this.gameName_)) {
         GeneratedMessageV3.writeString(output, 2, this.gameName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.server_)) {
         GeneratedMessageV3.writeString(output, 3, this.server_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.color_)) {
         GeneratedMessageV3.writeString(output, 4, this.color_);
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

      if (!GeneratedMessageV3.isStringEmpty(this.gameName_)) {
         size += GeneratedMessageV3.computeStringSize(2, this.gameName_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.server_)) {
         size += GeneratedMessageV3.computeStringSize(3, this.server_);
      }

      if (!GeneratedMessageV3.isStringEmpty(this.color_)) {
         size += GeneratedMessageV3.computeStringSize(4, this.color_);
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof SynchronizedUser otherUser)) {
         return super.equals(other);
      } else if (!this.getName().equals(otherUser.getName())) {
         return false;
      } else if (!this.getGameName().equals(otherUser.getGameName())) {
         return false;
      } else if (!this.getServer().equals(otherUser.getServer())) {
         return false;
      } else {
         return !this.getColor().equals(otherUser.getColor()) ? false : this.getUnknownFields().equals(otherUser.getUnknownFields());
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
      hash = 53 * hash + this.getGameName().hashCode();
      hash = 37 * hash + 3;
      hash = 53 * hash + this.getServer().hashCode();
      hash = 37 * hash + 4;
      hash = 53 * hash + this.getColor().hashCode();
      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static SynchronizedUser parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static SynchronizedUser parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static SynchronizedUser parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static SynchronizedUser parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static SynchronizedUser parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static SynchronizedUser parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static SynchronizedUser parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static SynchronizedUser parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static SynchronizedUser parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static SynchronizedUser parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static SynchronizedUser parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static SynchronizedUser parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public SynchronizedUser.Builder newBuilderForType() {
      return newBuilder();
   }

   public static SynchronizedUser.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static SynchronizedUser.Builder newBuilder(SynchronizedUser prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public SynchronizedUser.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new SynchronizedUser.Builder() : new SynchronizedUser.Builder().mergeFrom(this);
   }

   protected SynchronizedUser.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new SynchronizedUser.Builder(parent);
   }

   public static SynchronizedUser getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<SynchronizedUser> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<SynchronizedUser> getParserForType() {
      return PARSER;
   }

   public SynchronizedUser getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static void Method750(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method751(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method752(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   static void Method753(ByteString byteString) throws IllegalArgumentException {
      checkByteStringIsUtf8(byteString);
   }

   public static final class Builder extends GeneratedMessageV3.Builder<SynchronizedUser.Builder> implements SynchronizedUserOrBuilder {
      private Object name_ = "";
      private Object gameName_ = "";
      private Object server_ = "";
      private Object color_ = "";

      public static final Descriptor getDescriptor() {
         return SyncProto.internal_static_SynchronizedUser_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return SyncProto.internal_static_SynchronizedUser_fieldAccessorTable
            .ensureFieldAccessorsInitialized(SynchronizedUser.class, SynchronizedUser.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
         super(parent);
      }

      public SynchronizedUser.Builder clear() {
         super.clear();
         this.name_ = "";
         this.gameName_ = "";
         this.server_ = "";
         this.color_ = "";
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return SyncProto.internal_static_SynchronizedUser_descriptor;
      }

      public SynchronizedUser getDefaultInstanceForType() {
         return SynchronizedUser.getDefaultInstance();
      }

      public SynchronizedUser build() {
         SynchronizedUser result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public SynchronizedUser buildPartial() {
         SynchronizedUser result = new SynchronizedUser(this);
         result.name_ = this.name_;
         result.gameName_ = this.gameName_;
         result.server_ = this.server_;
         result.color_ = this.color_;
         this.onBuilt();
         return result;
      }

      public SynchronizedUser.Builder clone() {
         return (SynchronizedUser.Builder)super.clone();
      }

      public SynchronizedUser.Builder setField(FieldDescriptor field, Object value) {
         return (SynchronizedUser.Builder)super.setField(field, value);
      }

      public SynchronizedUser.Builder clearField(FieldDescriptor field) {
         return (SynchronizedUser.Builder)super.clearField(field);
      }

      public SynchronizedUser.Builder clearOneof(OneofDescriptor oneof) {
         return (SynchronizedUser.Builder)super.clearOneof(oneof);
      }

      public SynchronizedUser.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (SynchronizedUser.Builder)super.setRepeatedField(field, index, value);
      }

      public SynchronizedUser.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (SynchronizedUser.Builder)super.addRepeatedField(field, value);
      }

      public SynchronizedUser.Builder mergeFrom(Message other) {
         if (other instanceof SynchronizedUser) {
            return this.mergeFrom((SynchronizedUser)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public SynchronizedUser.Builder mergeFrom(SynchronizedUser other) {
         if (other == SynchronizedUser.getDefaultInstance()) {
            return this;
         }

         if (!other.getName().isEmpty()) {
            this.name_ = other.name_;
            this.onChanged();
         }

         if (!other.getGameName().isEmpty()) {
            this.gameName_ = other.gameName_;
            this.onChanged();
         }

         if (!other.getServer().isEmpty()) {
            this.server_ = other.server_;
            this.onChanged();
         }

         if (!other.getColor().isEmpty()) {
            this.color_ = other.color_;
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

      public SynchronizedUser.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     this.gameName_ = input.readStringRequireUtf8();
                     break;
                  case 26:
                     this.server_ = input.readStringRequireUtf8();
                     break;
                  case 34:
                     this.color_ = input.readStringRequireUtf8();
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

      public SynchronizedUser.Builder setName(String name) {
         if (name == null) {
            throw new NullPointerException();
         }

         this.name_ = name;
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder clearName() {
         this.name_ = SynchronizedUser.getDefaultInstance().getName();
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder setNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         SynchronizedUser.Method750(value);
         this.name_ = value;
         this.onChanged();
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

      public SynchronizedUser.Builder setGameName(String gameName) {
         if (gameName == null) {
            throw new NullPointerException();
         }

         this.gameName_ = gameName;
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder clearGameName() {
         this.gameName_ = SynchronizedUser.getDefaultInstance().getGameName();
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder setGameNameBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         SynchronizedUser.Method751(value);
         this.gameName_ = value;
         this.onChanged();
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

      public SynchronizedUser.Builder setServer(String server) {
         if (server == null) {
            throw new NullPointerException();
         }

         this.server_ = server;
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder clearServer() {
         this.server_ = SynchronizedUser.getDefaultInstance().getServer();
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder setServerBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         SynchronizedUser.Method752(value);
         this.server_ = value;
         this.onChanged();
         return this;
      }

      @Override
      public String getColor() {
         Object ref = this.color_;
         if (!(ref instanceof String)) {
            String s = ((ByteString)ref).toStringUtf8();
            this.color_ = s;
            return s;
         } else {
            return (String)ref;
         }
      }

      @Override
      public ByteString getColorBytes() {
         Object ref = this.color_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.color_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public SynchronizedUser.Builder setColor(String color) {
         if (color == null) {
            throw new NullPointerException();
         }

         this.color_ = color;
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder clearColor() {
         this.color_ = SynchronizedUser.getDefaultInstance().getColor();
         this.onChanged();
         return this;
      }

      public SynchronizedUser.Builder setColorBytes(ByteString value) {
         if (value == null) {
            throw new NullPointerException();
         }

         SynchronizedUser.Method753(value);
         this.color_ = value;
         this.onChanged();
         return this;
      }

      public final SynchronizedUser.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (SynchronizedUser.Builder)super.setUnknownFields(unknownFields);
      }

      public final SynchronizedUser.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (SynchronizedUser.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<SynchronizedUser> {
      public SynchronizedUser parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         SynchronizedUser.Builder builder = SynchronizedUser.newBuilder();

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
