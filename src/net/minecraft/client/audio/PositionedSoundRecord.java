package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class PositionedSoundRecord extends PositionedSound {
   public static PositionedSoundRecord create(ResourceLocation resourceLocation, float float2) {
      return new PositionedSoundRecord(resourceLocation, 0.25F, float2, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static PositionedSoundRecord create(ResourceLocation resourceLocation) {
      return new PositionedSoundRecord(resourceLocation, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static PositionedSoundRecord create(ResourceLocation resourceLocation, float float2, float float3, float float4) {
      return new PositionedSoundRecord(resourceLocation, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, float2, float3, float4);
   }

   public PositionedSoundRecord(ResourceLocation resourceLocation, float float2, float float3, float float4, float float5, float float6) {
      this(resourceLocation, float2, float3, false, 0, ISound.AttenuationType.LINEAR, float4, float5, float6);
   }

   private PositionedSoundRecord(
      ResourceLocation resourceLocation,
      float float2,
      float float3,
      boolean boolean4,
      int integer,
      ISound.AttenuationType attenuationType,
      float float7,
      float float8,
      float float9
   ) {
      super(resourceLocation);
      this.volume = float2;
      this.pitch = float3;
      this.xPosF = float7;
      this.yPosF = float8;
      this.zPosF = float9;
      this.repeat = boolean4;
      this.repeatDelay = integer;
      this.attenuationType = attenuationType;
   }
}
