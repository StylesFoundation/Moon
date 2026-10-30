package com.moon.connection.protobuf.cloud;

import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;

public enum CloudStorageType implements ProtocolMessageEnum {
   CONFIG(0),
   SCRIPT(1),
   UNRECOGNIZED(-1);

   public static final int CONFIG_VALUE = 0;
   public static final int SCRIPT_VALUE = 1;
   private static final Internal.EnumLiteMap<CloudStorageType> internalValueMap = new CloudStorageType.EnumLiteMap();
   private static final CloudStorageType[] VALUES = values();
   private final int value;

   @Override
   public final int getNumber() {
      if (this == UNRECOGNIZED) {
         throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
      } else {
         return this.value;
      }
   }

   @Deprecated
   public static CloudStorageType valueOf(int value) {
      return forNumber(value);
   }

   public static CloudStorageType forNumber(int value) {
      switch (value) {
         case 0:
            return CONFIG;
         case 1:
            return SCRIPT;
         default:
            return null;
      }
   }

   public static Internal.EnumLiteMap<CloudStorageType> internalGetValueMap() {
      return internalValueMap;
   }

   @Override
   public final EnumValueDescriptor getValueDescriptor() {
      if (this == UNRECOGNIZED) {
         throw new IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
      } else {
         return (EnumValueDescriptor)getDescriptor().getValues().get(this.ordinal());
      }
   }

   @Override
   public final EnumDescriptor getDescriptorForType() {
      return getDescriptor();
   }

   public static final EnumDescriptor getDescriptor() {
      return (EnumDescriptor)CloudProto.getDescriptor().getEnumTypes().get(0);
   }

   public static CloudStorageType valueOf(EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
         throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
      } else {
         return desc.getIndex() == -1 ? UNRECOGNIZED : VALUES[desc.getIndex()];
      }
   }

   CloudStorageType(int value) {
      this.value = value;
   }

   static class EnumLiteMap implements Internal.EnumLiteMap<CloudStorageType> {
      public CloudStorageType findValueByNumber(int number) {
         return CloudStorageType.forNumber(number);
      }
   }
}
