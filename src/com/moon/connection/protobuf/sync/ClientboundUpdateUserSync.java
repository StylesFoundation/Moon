package com.moon.connection.protobuf.sync;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ClientboundUpdateUserSync extends GeneratedMessageV3 implements ClientboundUpdateUserSyncOrBuilder {
   private static final long serialVersionUID = 0L;
   private List<SynchronizedUser> users_;
   private byte memoizedIsInitialized = -1;
   private static final ClientboundUpdateUserSync DEFAULT_INSTANCE = new ClientboundUpdateUserSync();
   private static final com.google.protobuf.Parser<ClientboundUpdateUserSync> PARSER = new ClientboundUpdateUserSync.Parser();

   private ClientboundUpdateUserSync(GeneratedMessageV3.Builder<?> builder) {
      super(builder);
   }

   private ClientboundUpdateUserSync() {
      this.users_ = Collections.emptyList();
   }

   @Override
   protected Object newInstance(UnusedPrivateParameter unused) {
      return new ClientboundUpdateUserSync();
   }

   @Override
   public final UnknownFieldSet getUnknownFields() {
      return this.unknownFields;
   }

   public static final Descriptor getDescriptor() {
      return SyncProto.internal_static_ClientboundUpdateUserSync_descriptor;
   }

   @Override
   protected FieldAccessorTable internalGetFieldAccessorTable() {
      return SyncProto.internal_static_ClientboundUpdateUserSync_fieldAccessorTable
         .ensureFieldAccessorsInitialized(ClientboundUpdateUserSync.class, ClientboundUpdateUserSync.Builder.class);
   }

   @Override
   public List<SynchronizedUser> getUsersList() {
      return this.users_;
   }

   @Override
   public List<? extends SynchronizedUserOrBuilder> getUsersOrBuilderList() {
      return this.users_;
   }

   @Override
   public int getUsersCount() {
      return this.users_.size();
   }

   @Override
   public SynchronizedUser getUsers(int index) {
      return this.users_.get(index);
   }

   @Override
   public SynchronizedUserOrBuilder getUsersOrBuilder(int index) {
      return this.users_.get(index);
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
      for (int i = 0; i < this.users_.size(); i++) {
         output.writeMessage(1, this.users_.get(i));
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

      for (int i = 0; i < this.users_.size(); i++) {
         size += CodedOutputStream.computeMessageSize(1, this.users_.get(i));
      }

      size += this.getUnknownFields().getSerializedSize();
      this.memoizedSize = size;
      return size;
   }

   @Override
   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ClientboundUpdateUserSync otherMessage)) {
         return super.equals(other);
      } else {
         return !this.getUsersList().equals(otherMessage.getUsersList()) ? false : this.getUnknownFields().equals(otherMessage.getUnknownFields());
      }
   }

   @Override
   public int hashCode() {
      if (this.memoizedHashCode != 0) {
         return this.memoizedHashCode;
      }

      int hash = 41;
      hash = 779 + getDescriptor().hashCode();
      if (this.getUsersCount() > 0) {
         hash = 37 * hash + 1;
         hash = 53 * hash + this.getUsersList().hashCode();
      }

      hash = 29 * hash + this.getUnknownFields().hashCode();
      this.memoizedHashCode = hash;
      return hash;
   }

   public static ClientboundUpdateUserSync parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundUpdateUserSync parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundUpdateUserSync parseFrom(ByteString data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundUpdateUserSync parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundUpdateUserSync parseFrom(byte[] data) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
   }

   public static ClientboundUpdateUserSync parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
   }

   public static ClientboundUpdateUserSync parseFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundUpdateUserSync parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundUpdateUserSync parseDelimitedFrom(InputStream input) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
   }

   public static ClientboundUpdateUserSync parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
   }

   public static ClientboundUpdateUserSync parseFrom(CodedInputStream input) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input);
   }

   public static ClientboundUpdateUserSync parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
      return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
   }

   public ClientboundUpdateUserSync.Builder newBuilderForType() {
      return newBuilder();
   }

   public static ClientboundUpdateUserSync.Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
   }

   public static ClientboundUpdateUserSync.Builder newBuilder(ClientboundUpdateUserSync prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
   }

   public ClientboundUpdateUserSync.Builder toBuilder() {
      return this == DEFAULT_INSTANCE ? new ClientboundUpdateUserSync.Builder() : new ClientboundUpdateUserSync.Builder().mergeFrom(this);
   }

   protected ClientboundUpdateUserSync.Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      return new ClientboundUpdateUserSync.Builder(parent);
   }

   public static ClientboundUpdateUserSync getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static com.google.protobuf.Parser<ClientboundUpdateUserSync> parser() {
      return PARSER;
   }

   @Override
   public com.google.protobuf.Parser<ClientboundUpdateUserSync> getParserForType() {
      return PARSER;
   }

   public ClientboundUpdateUserSync getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
   }

   static boolean alwaysUseFieldBuilders() {
      return alwaysUseFieldBuilders;
   }

   public static final class Builder extends GeneratedMessageV3.Builder<ClientboundUpdateUserSync.Builder> implements ClientboundUpdateUserSyncOrBuilder {
      private int bitField0_;
      private List<SynchronizedUser> users_ = Collections.emptyList();
      private RepeatedFieldBuilderV3<SynchronizedUser, SynchronizedUser.Builder, SynchronizedUserOrBuilder> usersBuilder_;

      public static final Descriptor getDescriptor() {
         return SyncProto.internal_static_ClientboundUpdateUserSync_descriptor;
      }

      @Override
      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return SyncProto.internal_static_ClientboundUpdateUserSync_fieldAccessorTable
            .ensureFieldAccessorsInitialized(ClientboundUpdateUserSync.class, ClientboundUpdateUserSync.Builder.class);
      }

      private Builder() {
      }

      private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent builderParent) {
         super(builderParent);
      }

      public ClientboundUpdateUserSync.Builder clear() {
         super.clear();
         if (this.usersBuilder_ == null) {
            this.users_ = Collections.emptyList();
         } else {
            this.users_ = null;
            this.usersBuilder_.clear();
         }

         this.bitField0_ &= -2;
         return this;
      }

      @Override
      public Descriptor getDescriptorForType() {
         return SyncProto.internal_static_ClientboundUpdateUserSync_descriptor;
      }

      public ClientboundUpdateUserSync getDefaultInstanceForType() {
         return ClientboundUpdateUserSync.getDefaultInstance();
      }

      public ClientboundUpdateUserSync build() {
         ClientboundUpdateUserSync result = this.buildPartial();
         if (!result.isInitialized()) {
            throw newUninitializedMessageException(result);
         } else {
            return result;
         }
      }

      public ClientboundUpdateUserSync buildPartial() {
         ClientboundUpdateUserSync result = new ClientboundUpdateUserSync(this);
         if (this.usersBuilder_ == null) {
            if ((this.bitField0_ & 1) != 0) {
               this.users_ = Collections.unmodifiableList(this.users_);
               this.bitField0_ &= -2;
            }

            result.users_ = this.users_;
         } else {
            result.users_ = this.usersBuilder_.build();
         }

         this.onBuilt();
         return result;
      }

      public ClientboundUpdateUserSync.Builder clone() {
         return (ClientboundUpdateUserSync.Builder)super.clone();
      }

      public ClientboundUpdateUserSync.Builder setField(FieldDescriptor field, Object value) {
         return (ClientboundUpdateUserSync.Builder)super.setField(field, value);
      }

      public ClientboundUpdateUserSync.Builder clearField(FieldDescriptor field) {
         return (ClientboundUpdateUserSync.Builder)super.clearField(field);
      }

      public ClientboundUpdateUserSync.Builder clearOneof(OneofDescriptor oneof) {
         return (ClientboundUpdateUserSync.Builder)super.clearOneof(oneof);
      }

      public ClientboundUpdateUserSync.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
         return (ClientboundUpdateUserSync.Builder)super.setRepeatedField(field, index, value);
      }

      public ClientboundUpdateUserSync.Builder addRepeatedField(FieldDescriptor field, Object value) {
         return (ClientboundUpdateUserSync.Builder)super.addRepeatedField(field, value);
      }

      public ClientboundUpdateUserSync.Builder mergeFrom(Message other) {
         if (other instanceof ClientboundUpdateUserSync) {
            return this.mergeFrom((ClientboundUpdateUserSync)other);
         }

         super.mergeFrom(other);
         return this;
      }

      public ClientboundUpdateUserSync.Builder mergeFrom(ClientboundUpdateUserSync other) {
         if (other == ClientboundUpdateUserSync.getDefaultInstance()) {
            return this;
         }

         if (this.usersBuilder_ == null) {
            if (!other.users_.isEmpty()) {
               if (this.users_.isEmpty()) {
                  this.users_ = other.users_;
                  this.bitField0_ &= -2;
               } else {
                  this.ensureUsersIsMutable();
                  this.users_.addAll(other.users_);
               }

               this.onChanged();
            }
         } else if (!other.users_.isEmpty()) {
            if (this.usersBuilder_.isEmpty()) {
               this.usersBuilder_.dispose();
               this.usersBuilder_ = null;
               this.users_ = other.users_;
               this.bitField0_ &= -2;
               this.usersBuilder_ = ClientboundUpdateUserSync.alwaysUseFieldBuilders() ? this.getUsersFieldBuilder() : null;
            } else {
               this.usersBuilder_.addAllMessages(other.users_);
            }
         }

         this.mergeUnknownFields(other.getUnknownFields());
         this.onChanged();
         return this;
      }

      @Override
      public final boolean isInitialized() {
         return true;
      }

      public ClientboundUpdateUserSync.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
                     SynchronizedUser m = (SynchronizedUser)input.readMessage(SynchronizedUser.parser(), extensionRegistry);
                     if (this.usersBuilder_ == null) {
                        this.ensureUsersIsMutable();
                        this.users_.add(m);
                     } else {
                        this.usersBuilder_.addMessage(m);
                     }
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

      private void ensureUsersIsMutable() {
         if ((this.bitField0_ & 1) == 0) {
            this.users_ = new ArrayList<>(this.users_);
            this.bitField0_ |= 1;
         }
      }

      @Override
      public List<SynchronizedUser> getUsersList() {
         return this.usersBuilder_ == null ? Collections.unmodifiableList(this.users_) : this.usersBuilder_.getMessageList();
      }

      @Override
      public int getUsersCount() {
         return this.usersBuilder_ == null ? this.users_.size() : this.usersBuilder_.getCount();
      }

      @Override
      public SynchronizedUser getUsers(int index) {
         return this.usersBuilder_ == null ? this.users_.get(index) : (SynchronizedUser)this.usersBuilder_.getMessage(index);
      }

      public ClientboundUpdateUserSync.Builder setUsers(int index, SynchronizedUser value) {
         if (this.usersBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureUsersIsMutable();
            this.users_.set(index, value);
            this.onChanged();
         } else {
            this.usersBuilder_.setMessage(index, value);
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder setUsers(int index, SynchronizedUser.Builder builderForValue) {
         if (this.usersBuilder_ == null) {
            this.ensureUsersIsMutable();
            this.users_.set(index, builderForValue.build());
            this.onChanged();
         } else {
            this.usersBuilder_.setMessage(index, builderForValue.build());
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder addUsers(SynchronizedUser value) {
         if (this.usersBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureUsersIsMutable();
            this.users_.add(value);
            this.onChanged();
         } else {
            this.usersBuilder_.addMessage(value);
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder addUsers(int index, SynchronizedUser value) {
         if (this.usersBuilder_ == null) {
            if (value == null) {
               throw new NullPointerException();
            }

            this.ensureUsersIsMutable();
            this.users_.add(index, value);
            this.onChanged();
         } else {
            this.usersBuilder_.addMessage(index, value);
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder addUsers(SynchronizedUser.Builder builderForValue) {
         if (this.usersBuilder_ == null) {
            this.ensureUsersIsMutable();
            this.users_.add(builderForValue.build());
            this.onChanged();
         } else {
            this.usersBuilder_.addMessage(builderForValue.build());
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder addUsers(int index, SynchronizedUser.Builder builderForValue) {
         if (this.usersBuilder_ == null) {
            this.ensureUsersIsMutable();
            this.users_.add(index, builderForValue.build());
            this.onChanged();
         } else {
            this.usersBuilder_.addMessage(index, builderForValue.build());
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder addAllUsers(Iterable<? extends SynchronizedUser> values) {
         if (this.usersBuilder_ == null) {
            this.ensureUsersIsMutable();
            AbstractMessageLite.Builder.addAll(values, this.users_);
            this.onChanged();
         } else {
            this.usersBuilder_.addAllMessages(values);
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder clearUsers() {
         if (this.usersBuilder_ == null) {
            this.users_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.onChanged();
         } else {
            this.usersBuilder_.clear();
         }

         return this;
      }

      public ClientboundUpdateUserSync.Builder removeUsers(int index) {
         if (this.usersBuilder_ == null) {
            this.ensureUsersIsMutable();
            this.users_.remove(index);
            this.onChanged();
         } else {
            this.usersBuilder_.remove(index);
         }

         return this;
      }

      public SynchronizedUser.Builder getUsersBuilder(int index) {
         return (SynchronizedUser.Builder)this.getUsersFieldBuilder().getBuilder(index);
      }

      @Override
      public SynchronizedUserOrBuilder getUsersOrBuilder(int index) {
         return this.usersBuilder_ == null ? this.users_.get(index) : (SynchronizedUserOrBuilder)this.usersBuilder_.getMessageOrBuilder(index);
      }

      @Override
      public List<? extends SynchronizedUserOrBuilder> getUsersOrBuilderList() {
         return this.usersBuilder_ != null ? this.usersBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.users_);
      }

      public SynchronizedUser.Builder addUsersBuilder() {
         return (SynchronizedUser.Builder)this.getUsersFieldBuilder().addBuilder(SynchronizedUser.getDefaultInstance());
      }

      public SynchronizedUser.Builder addUsersBuilder(int index) {
         return (SynchronizedUser.Builder)this.getUsersFieldBuilder().addBuilder(index, SynchronizedUser.getDefaultInstance());
      }

      public List<SynchronizedUser.Builder> getUsersBuilderList() {
         return this.getUsersFieldBuilder().getBuilderList();
      }

      private RepeatedFieldBuilderV3<SynchronizedUser, SynchronizedUser.Builder, SynchronizedUserOrBuilder> getUsersFieldBuilder() {
         if (this.usersBuilder_ == null) {
            this.usersBuilder_ = new RepeatedFieldBuilderV3(this.users_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
            this.users_ = null;
         }

         return this.usersBuilder_;
      }

      public final ClientboundUpdateUserSync.Builder setUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundUpdateUserSync.Builder)super.setUnknownFields(unknownFields);
      }

      public final ClientboundUpdateUserSync.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
         return (ClientboundUpdateUserSync.Builder)super.mergeUnknownFields(unknownFields);
      }
   }

   static class Parser extends AbstractParser<ClientboundUpdateUserSync> {
      public ClientboundUpdateUserSync parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         ClientboundUpdateUserSync.Builder builder = ClientboundUpdateUserSync.newBuilder();

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
