package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MusicTicker implements ITickable {
   private final Random rand = new Random();
   private final Minecraft mc;
   private ISound currentMusic;
   private int timeUntilNextMusic = 100;

   public MusicTicker(Minecraft minecraft) {
      this.mc = minecraft;
   }

   @Override
   public void update() {
      MusicTicker.MusicType var4 = this.mc.getAmbientMusicType();
      if (this.currentMusic != null) {
         if (!var4.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, var4.getMinDelay() / 2);
         }

         if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
            this.currentMusic = null;
            this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, var4.getMinDelay(), var4.getMaxDelay()), this.timeUntilNextMusic);
         }
      }

      if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
         this.func_181558_a(var4);
      }
   }

   public void func_181558_a(MusicTicker.MusicType musicType) {
      this.currentMusic = PositionedSoundRecord.create(musicType.getMusicLocation());
      this.mc.getSoundHandler().playSound(this.currentMusic);
      this.timeUntilNextMusic = Integer.MAX_VALUE;
   }

   public void func_181557_a() {
      if (this.currentMusic != null) {
         this.mc.getSoundHandler().stopSound(this.currentMusic);
         this.currentMusic = null;
         this.timeUntilNextMusic = 0;
      }
   }

   public enum MusicType {
      MENU(new ResourceLocation("minecraft:music.menu"), 20, 600),
      GAME(new ResourceLocation("minecraft:music.game"), 12000, 24000),
      CREATIVE(new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
      CREDITS(new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE),
      NETHER(new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
      END_BOSS(new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
      END(new ResourceLocation("minecraft:music.game.end"), 6000, 24000);

      private final ResourceLocation musicLocation;
      private final int minDelay;
      private final int maxDelay;

      MusicType(ResourceLocation resourceLocation, int integer4, int integer5) {
         this.musicLocation = resourceLocation;
         this.minDelay = integer4;
         this.maxDelay = integer5;
      }

      public ResourceLocation getMusicLocation() {
         return this.musicLocation;
      }

      public int getMinDelay() {
         return this.minDelay;
      }

      public int getMaxDelay() {
         return this.maxDelay;
      }

      private static MusicTicker.MusicType[] Method430() {
         return new MusicTicker.MusicType[]{MENU, GAME, CREATIVE, CREDITS, NETHER, END_BOSS, END};
      }
   }
}
