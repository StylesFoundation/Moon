package org.lwjgl.openal;

import java.nio.IntBuffer;

public class EFX10 {
   public static final int AL_EFFECT_TYPE = 32769;
   public static final int AL_EFFECTSLOT_EFFECT = 1;
   public static final int AL_EFFECT_ECHO = 4;
   public static final float AL_ECHO_MIN_DAMPING = 0.0F;
   public static final int AL_ECHO_DAMPING = 3;
   public static final float AL_ECHO_MAX_DAMPING = 0.99F;
   public static final float AL_ECHO_MIN_DELAY = 0.0F;
   public static final int AL_ECHO_DELAY = 1;
   public static final float AL_ECHO_MAX_DELAY = 0.207F;
   public static final float AL_ECHO_MIN_FEEDBACK = 0.0F;
   public static final int AL_ECHO_FEEDBACK = 4;
   public static final float AL_ECHO_MAX_FEEDBACK = 1.0F;
   public static final float AL_ECHO_MIN_LRDELAY = 0.0F;
   public static final int AL_ECHO_LRDELAY = 2;
   public static final float AL_ECHO_MAX_LRDELAY = 0.404F;
   public static final float AL_ECHO_MIN_SPREAD = -1.0F;
   public static final int AL_ECHO_SPREAD = 5;
   public static final float AL_ECHO_MAX_SPREAD = 1.0F;
   public static final int AL_EFFECT_REVERB = 1;
   public static final int AL_RING_MODULATOR_SINUSOID = 0;
   public static final int AL_RING_MODULATOR_SAWTOOTH = 1;
   public static final int AL_RING_MODULATOR_SQUARE = 2;
   public static final int AL_EFFECT_RING_MODULATOR = 9;
   public static final float AL_RING_MODULATOR_MAX_FREQUENCY = 8000.0F;
   public static final int AL_RING_MODULATOR_FREQUENCY = 1;
   public static final float AL_RING_MODULATOR_MIN_FREQUENCY = 0.0F;
   public static final float AL_RING_MODULATOR_MAX_HIGHPASS_CUTOFF = 24000.0F;
   public static final int AL_RING_MODULATOR_HIGHPASS_CUTOFF = 2;
   public static final float AL_RING_MODULATOR_MIN_HIGHPASS_CUTOFF = 0.0F;
   public static final int AL_RING_MODULATOR_WAVEFORM = 3;
   public static final int AL_FILTER_TYPE = 32769;
   public static final int AL_FILTER_LOWPASS = 1;
   public static final int AL_LOWPASS_GAIN = 1;
   public static final int AL_LOWPASS_GAINHF = 2;
   public static final int AL_EFFECTSLOT_NULL = 0;
   public static final int AL_FILTER_NULL = 0;
   public static final int AL_AUXILIARY_SEND_FILTER = 131078;
   public static final int AL_DIRECT_FILTER = 131077;
   public static final int ALC_MAX_AUXILIARY_SENDS = 131075;
   public static final int ALC_EFX_MAJOR_VERSION = 131073;
   public static final int AL_REVERB_DECAY_TIME = 5;
   public static final int AL_FILTER_HIGHPASS = 2;
   public static final int AL_FILTER_BANDPASS = 3;
   public static final int AL_EFFECT_NULL = 0;
   public static final int AL_EFFECT_EAXREVERB = 32768;
   public static final int AL_EFFECT_CHORUS = 2;
   public static final int AL_EFFECT_DISTORTION = 3;
   public static final int AL_EFFECT_FLANGER = 5;
   public static final int AL_EFFECT_FREQUENCY_SHIFTER = 6;
   public static final int AL_EFFECT_VOCAL_MORPHER = 7;
   public static final int AL_EFFECT_PITCH_SHIFTER = 8;
   public static final int AL_EFFECT_AUTOWAH = 10;
   public static final int AL_EFFECT_COMPRESSOR = 11;
   public static final int AL_EFFECT_EQUALIZER = 12;

   public static int alGenAuxiliaryEffectSlots() {
      return EXTEfx.alGenAuxiliaryEffectSlots();
   }

   public static int alGenFilters() {
      return EXTEfx.alGenFilters();
   }

   public static void alDeleteFilters(int filter) {
      EXTEfx.alDeleteFilters(filter);
   }

   public static void alFilteri(int filter, int param, int value) {
      EXTEfx.alFilteri(filter, param, value);
   }

   public static void alFilterf(int filter, int param, float value) {
      EXTEfx.alFilterf(filter, param, value);
   }

   public static float alGetFilterf(int filter, int param) {
      return EXTEfx.alGetFilterf(filter, param);
   }

   public static int alGenEffects() {
      return EXTEfx.alGenEffects();
   }

   public static void alDeleteEffects(int effect) {
      EXTEfx.alDeleteAuxiliaryEffectSlots(effect);
   }

   public static void alDeleteAuxiliaryEffectSlots(int effectSlot) {
      EXTEfx.alDeleteAuxiliaryEffectSlots(effectSlot);
   }

   public static void alEffecti(int effect, int param, int value) {
      EXTEfx.alEffecti(effect, param, value);
   }

   public static float alGetEffectf(int effect, int param) {
      return EXTEfx.alGetEffectf(effect, param);
   }

   public static int alGetEffecti(int effect, int param) {
      return EXTEfx.alGetEffecti(effect, param);
   }

   public static void alEffectf(int effect, int param, float value) {
      EXTEfx.alEffectf(effect, param, value);
   }

   public static void alAuxiliaryEffectSloti(int effectSlot, int param, int value) {
      EXTEfx.alAuxiliaryEffectSloti(effectSlot, param, value);
   }

   public static void alGenAuxiliaryEffectSlots(IntBuffer effectSlots) {
      EXTEfx.alGenAuxiliaryEffectSlots(effectSlots);
   }

   public static void alGenEffects(IntBuffer effects) {
      EXTEfx.alGenEffects(effects);
   }

   public static void alDeleteEffects(IntBuffer effects) {
      EXTEfx.alDeleteEffects(effects);
   }

   public static void alDeleteAuxiliaryEffectSlots(IntBuffer effectSlots) {
      EXTEfx.alDeleteAuxiliaryEffectSlots(effectSlots);
   }

   public static void alGenFilters(IntBuffer filters) {
      EXTEfx.alGenFilters(filters);
   }

   public static void alDeleteFilters(IntBuffer filters) {
      EXTEfx.alDeleteFilters(filters);
   }
}
