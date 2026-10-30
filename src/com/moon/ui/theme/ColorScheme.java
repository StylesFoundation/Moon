package com.moon.ui.theme;

import com.moon.ui.theme.palette.MaterialPalettes;

public class ColorScheme {
   private int primary;
   private int onPrimary;
   private int primaryContainer;
   private int onPrimaryContainer;
   private int secondary;
   private int onSecondary;
   private int secondaryContainer;
   private int onSecondaryContainer;
   private int tertiary;
   private int onTertiary;
   private int tertiaryContainer;
   private int onTertiaryContainer;
   private int error;
   private int onError;
   private int errorContainer;
   private int onErrorContainer;
   private int background;
   private int onBackground;
   private int surface;
   private int onSurface;
   private int surfaceVariant;
   private int onSurfaceVariant;
   private int outline;
   private int shadow;
   private int inverseSurface;
   private int inverseOnSurface;
   private int inversePrimary;

   public ColorScheme() {
   }

   public ColorScheme(
      int primary,
      int onPrimary,
      int primaryContainer,
      int onPrimaryContainer,
      int secondary,
      int onSecondary,
      int secondaryContainer,
      int onSecondaryContainer,
      int tertiary,
      int onTertiary,
      int tertiaryContainer,
      int onTertiaryContainer,
      int error,
      int onError,
      int errorContainer,
      int onErrorContainer,
      int background,
      int onBackground,
      int surface,
      int onSurface,
      int surfaceVariant,
      int onSurfaceVariant,
      int outline,
      int shadow,
      int inverseSurface,
      int inverseOnSurface,
      int inversePrimary
   ) {
      this.primary = primary;
      this.onPrimary = onPrimary;
      this.primaryContainer = primaryContainer;
      this.onPrimaryContainer = onPrimaryContainer;
      this.secondary = secondary;
      this.onSecondary = onSecondary;
      this.secondaryContainer = secondaryContainer;
      this.onSecondaryContainer = onSecondaryContainer;
      this.tertiary = tertiary;
      this.onTertiary = onTertiary;
      this.tertiaryContainer = tertiaryContainer;
      this.onTertiaryContainer = onTertiaryContainer;
      this.error = error;
      this.onError = onError;
      this.errorContainer = errorContainer;
      this.onErrorContainer = onErrorContainer;
      this.background = background;
      this.onBackground = onBackground;
      this.surface = surface;
      this.onSurface = onSurface;
      this.surfaceVariant = surfaceVariant;
      this.onSurfaceVariant = onSurfaceVariant;
      this.outline = outline;
      this.shadow = shadow;
      this.inverseSurface = inverseSurface;
      this.inverseOnSurface = inverseOnSurface;
      this.inversePrimary = inversePrimary;
   }

   public static ColorScheme lightScheme(int seedColor) {
      return createLight(MaterialPalettes.tonal(seedColor));
   }

   public static ColorScheme darkScheme(int seedColor) {
      return createDark(MaterialPalettes.tonal(seedColor));
   }

   public static ColorScheme lightContentScheme(int seedColor) {
      return createLight(MaterialPalettes.content(seedColor));
   }

   public static ColorScheme darkContentScheme(int seedColor) {
      return createDark(MaterialPalettes.content(seedColor));
   }

   private static ColorScheme createLight(MaterialPalettes palettes) {
      return new ColorScheme()
         .withPrimary(palettes.primary.getColor(40))
         .withOnPrimary(palettes.primary.getColor(100))
         .withPrimaryContainer(palettes.primary.getColor(90))
         .withOnPrimaryContainer(palettes.primary.getColor(10))
         .withSecondary(palettes.secondary.getColor(40))
         .withOnSecondary(palettes.secondary.getColor(100))
         .withSecondaryContainer(palettes.secondary.getColor(90))
         .withOnSecondaryContainer(palettes.secondary.getColor(10))
         .withTertiary(palettes.tertiary.getColor(40))
         .withOnTertiary(palettes.tertiary.getColor(100))
         .withTertiaryContainer(palettes.tertiary.getColor(90))
         .withOnTertiaryContainer(palettes.tertiary.getColor(10))
         .withError(palettes.error.getColor(40))
         .withOnError(palettes.error.getColor(100))
         .withErrorContainer(palettes.error.getColor(90))
         .withOnErrorContainer(palettes.error.getColor(10))
         .withBackground(palettes.neutral.getColor(99))
         .withOnBackground(palettes.neutral.getColor(10))
         .withSurface(palettes.neutral.getColor(99))
         .withOnSurface(palettes.neutral.getColor(10))
         .withSurfaceVariant(palettes.neutralVariant.getColor(90))
         .withOnSurfaceVariant(palettes.neutralVariant.getColor(30))
         .withOutline(palettes.neutralVariant.getColor(50))
         .withShadow(palettes.neutral.getColor(0))
         .withInverseSurface(palettes.neutral.getColor(20))
         .withInverseOnSurface(palettes.neutral.getColor(95))
         .withInversePrimary(palettes.primary.getColor(80));
   }

   private static ColorScheme createDark(MaterialPalettes palettes) {
      return new ColorScheme()
         .withPrimary(palettes.primary.getColor(80))
         .withOnPrimary(palettes.primary.getColor(20))
         .withPrimaryContainer(palettes.primary.getColor(30))
         .withOnPrimaryContainer(palettes.primary.getColor(90))
         .withSecondary(palettes.secondary.getColor(80))
         .withOnSecondary(palettes.secondary.getColor(20))
         .withSecondaryContainer(palettes.secondary.getColor(30))
         .withOnSecondaryContainer(palettes.secondary.getColor(90))
         .withTertiary(palettes.tertiary.getColor(80))
         .withOnTertiary(palettes.tertiary.getColor(20))
         .withTertiaryContainer(palettes.tertiary.getColor(30))
         .withOnTertiaryContainer(palettes.tertiary.getColor(90))
         .withError(palettes.error.getColor(80))
         .withOnError(palettes.error.getColor(20))
         .withErrorContainer(palettes.error.getColor(30))
         .withOnErrorContainer(palettes.error.getColor(80))
         .withBackground(palettes.neutral.getColor(10))
         .withOnBackground(palettes.neutral.getColor(90))
         .withSurface(palettes.neutral.getColor(10))
         .withOnSurface(palettes.neutral.getColor(90))
         .withSurfaceVariant(palettes.neutralVariant.getColor(30))
         .withOnSurfaceVariant(palettes.neutralVariant.getColor(80))
         .withOutline(palettes.neutralVariant.getColor(60))
         .withShadow(palettes.neutral.getColor(0))
         .withInverseSurface(palettes.neutral.getColor(90))
         .withInverseOnSurface(palettes.neutral.getColor(20))
         .withInversePrimary(palettes.primary.getColor(40));
   }

   public int getPrimary() {
      return this.primary;
   }

   public void setPrimary(int value) {
      this.primary = value;
   }

   public ColorScheme withPrimary(int primary) {
      this.primary = primary;
      return this;
   }

   public int getOnPrimary() {
      return this.onPrimary;
   }

   public void setOnPrimary(int value) {
      this.onPrimary = value;
   }

   public ColorScheme withOnPrimary(int onPrimary) {
      this.onPrimary = onPrimary;
      return this;
   }

   public int getPrimaryContainer() {
      return this.primaryContainer;
   }

   public void setPrimaryContainer(int value) {
      this.primaryContainer = value;
   }

   public ColorScheme withPrimaryContainer(int primaryContainer) {
      this.primaryContainer = primaryContainer;
      return this;
   }

   public int getOnPrimaryContainer() {
      return this.onPrimaryContainer;
   }

   public void setOnPrimaryContainer(int value) {
      this.onPrimaryContainer = value;
   }

   public ColorScheme withOnPrimaryContainer(int onPrimaryContainer) {
      this.onPrimaryContainer = onPrimaryContainer;
      return this;
   }

   public int getSecondary() {
      return this.secondary;
   }

   public void setSecondary(int value) {
      this.secondary = value;
   }

   public ColorScheme withSecondary(int secondary) {
      this.secondary = secondary;
      return this;
   }

   public int getOnSecondary() {
      return this.onSecondary;
   }

   public void setOnSecondary(int value) {
      this.onSecondary = value;
   }

   public ColorScheme withOnSecondary(int onSecondary) {
      this.onSecondary = onSecondary;
      return this;
   }

   public int getSecondaryContainer() {
      return this.secondaryContainer;
   }

   public void setSecondaryContainer(int value) {
      this.secondaryContainer = value;
   }

   public ColorScheme withSecondaryContainer(int secondaryContainer) {
      this.secondaryContainer = secondaryContainer;
      return this;
   }

   public int getOnSecondaryContainer() {
      return this.onSecondaryContainer;
   }

   public void setOnSecondaryContainer(int value) {
      this.onSecondaryContainer = value;
   }

   public ColorScheme withOnSecondaryContainer(int onSecondaryContainer) {
      this.onSecondaryContainer = onSecondaryContainer;
      return this;
   }

   public int getTertiary() {
      return this.tertiary;
   }

   public void setTertiary(int value) {
      this.tertiary = value;
   }

   public ColorScheme withTertiary(int tertiary) {
      this.tertiary = tertiary;
      return this;
   }

   public int getOnTertiary() {
      return this.onTertiary;
   }

   public void setOnTertiary(int value) {
      this.onTertiary = value;
   }

   public ColorScheme withOnTertiary(int onTertiary) {
      this.onTertiary = onTertiary;
      return this;
   }

   public int getTertiaryContainer() {
      return this.tertiaryContainer;
   }

   public void setTertiaryContainer(int value) {
      this.tertiaryContainer = value;
   }

   public ColorScheme withTertiaryContainer(int tertiaryContainer) {
      this.tertiaryContainer = tertiaryContainer;
      return this;
   }

   public int getOnTertiaryContainer() {
      return this.onTertiaryContainer;
   }

   public void setOnTertiaryContainer(int value) {
      this.onTertiaryContainer = value;
   }

   public ColorScheme withOnTertiaryContainer(int onTertiaryContainer) {
      this.onTertiaryContainer = onTertiaryContainer;
      return this;
   }

   public int getError() {
      return this.error;
   }

   public void setError(int value) {
      this.error = value;
   }

   public ColorScheme withError(int error) {
      this.error = error;
      return this;
   }

   public int getOnError() {
      return this.onError;
   }

   public void setOnError(int value) {
      this.onError = value;
   }

   public ColorScheme withOnError(int onError) {
      this.onError = onError;
      return this;
   }

   public int getErrorContainer() {
      return this.errorContainer;
   }

   public void setErrorContainer(int value) {
      this.errorContainer = value;
   }

   public ColorScheme withErrorContainer(int errorContainer) {
      this.errorContainer = errorContainer;
      return this;
   }

   public int getOnErrorContainer() {
      return this.onErrorContainer;
   }

   public void setOnErrorContainer(int value) {
      this.onErrorContainer = value;
   }

   public ColorScheme withOnErrorContainer(int onErrorContainer) {
      this.onErrorContainer = onErrorContainer;
      return this;
   }

   public int getBackground() {
      return this.background;
   }

   public void setBackground(int value) {
      this.background = value;
   }

   public ColorScheme withBackground(int background) {
      this.background = background;
      return this;
   }

   public int getOnBackground() {
      return this.onBackground;
   }

   public void setOnBackground(int value) {
      this.onBackground = value;
   }

   public ColorScheme withOnBackground(int onBackground) {
      this.onBackground = onBackground;
      return this;
   }

   public int getSurface() {
      return this.surface;
   }

   public void setSurface(int value) {
      this.surface = value;
   }

   public ColorScheme withSurface(int surface) {
      this.surface = surface;
      return this;
   }

   public int getOnSurface() {
      return this.onSurface;
   }

   public void setOnSurface(int value) {
      this.onSurface = value;
   }

   public ColorScheme withOnSurface(int onSurface) {
      this.onSurface = onSurface;
      return this;
   }

   public int getSurfaceVariant() {
      return this.surfaceVariant;
   }

   public void setSurfaceVariant(int value) {
      this.surfaceVariant = value;
   }

   public ColorScheme withSurfaceVariant(int surfaceVariant) {
      this.surfaceVariant = surfaceVariant;
      return this;
   }

   public int getOnSurfaceVariant() {
      return this.onSurfaceVariant;
   }

   public void setOnSurfaceVariant(int value) {
      this.onSurfaceVariant = value;
   }

   public ColorScheme withOnSurfaceVariant(int onSurfaceVariant) {
      this.onSurfaceVariant = onSurfaceVariant;
      return this;
   }

   public int getOutline() {
      return this.outline;
   }

   public void setOutline(int value) {
      this.outline = value;
   }

   public ColorScheme withOutline(int outline) {
      this.outline = outline;
      return this;
   }

   public int getShadow() {
      return this.shadow;
   }

   public void setShadow(int value) {
      this.shadow = value;
   }

   public ColorScheme withShadow(int shadow) {
      this.shadow = shadow;
      return this;
   }

   public int getInverseSurface() {
      return this.inverseSurface;
   }

   public void setInverseSurface(int value) {
      this.inverseSurface = value;
   }

   public ColorScheme withInverseSurface(int inverseSurface) {
      this.inverseSurface = inverseSurface;
      return this;
   }

   public int getInverseOnSurface() {
      return this.inverseOnSurface;
   }

   public void setInverseOnSurface(int value) {
      this.inverseOnSurface = value;
   }

   public ColorScheme withInverseOnSurface(int inverseOnSurface) {
      this.inverseOnSurface = inverseOnSurface;
      return this;
   }

   public int getInversePrimary() {
      return this.inversePrimary;
   }

   public void setInversePrimary(int value) {
      this.inversePrimary = value;
   }

   public ColorScheme withInversePrimary(int inversePrimary) {
      this.inversePrimary = inversePrimary;
      return this;
   }

   @Override
   public String toString() {
      return "Scheme{primary="
         + this.primary
         + ", onPrimary="
         + this.onPrimary
         + ", primaryContainer="
         + this.primaryContainer
         + ", onPrimaryContainer="
         + this.onPrimaryContainer
         + ", secondary="
         + this.secondary
         + ", onSecondary="
         + this.onSecondary
         + ", secondaryContainer="
         + this.secondaryContainer
         + ", onSecondaryContainer="
         + this.onSecondaryContainer
         + ", tertiary="
         + this.tertiary
         + ", onTertiary="
         + this.onTertiary
         + ", tertiaryContainer="
         + this.tertiaryContainer
         + ", onTertiaryContainer="
         + this.onTertiaryContainer
         + ", error="
         + this.error
         + ", onError="
         + this.onError
         + ", errorContainer="
         + this.errorContainer
         + ", onErrorContainer="
         + this.onErrorContainer
         + ", background="
         + this.background
         + ", onBackground="
         + this.onBackground
         + ", surface="
         + this.surface
         + ", onSurface="
         + this.onSurface
         + ", surfaceVariant="
         + this.surfaceVariant
         + ", onSurfaceVariant="
         + this.onSurfaceVariant
         + ", outline="
         + this.outline
         + ", shadow="
         + this.shadow
         + ", inverseSurface="
         + this.inverseSurface
         + ", inverseOnSurface="
         + this.inverseOnSurface
         + ", inversePrimary="
         + this.inversePrimary
         + "}";
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ColorScheme)) {
         return false;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         ColorScheme other = (ColorScheme)obj;
         if (this.primary != other.primary) {
            return false;
         } else if (this.onPrimary != other.onPrimary) {
            return false;
         } else if (this.primaryContainer != other.primaryContainer) {
            return false;
         } else if (this.onPrimaryContainer != other.onPrimaryContainer) {
            return false;
         } else if (this.secondary != other.secondary) {
            return false;
         } else if (this.onSecondary != other.onSecondary) {
            return false;
         } else if (this.secondaryContainer != other.secondaryContainer) {
            return false;
         } else if (this.onSecondaryContainer != other.onSecondaryContainer) {
            return false;
         } else if (this.tertiary != other.tertiary) {
            return false;
         } else if (this.onTertiary != other.onTertiary) {
            return false;
         } else if (this.tertiaryContainer != other.tertiaryContainer) {
            return false;
         } else if (this.onTertiaryContainer != other.onTertiaryContainer) {
            return false;
         } else if (this.error != other.error) {
            return false;
         } else if (this.onError != other.onError) {
            return false;
         } else if (this.errorContainer != other.errorContainer) {
            return false;
         } else if (this.onErrorContainer != other.onErrorContainer) {
            return false;
         } else if (this.background != other.background) {
            return false;
         } else if (this.onBackground != other.onBackground) {
            return false;
         } else if (this.surface != other.surface) {
            return false;
         } else if (this.onSurface != other.onSurface) {
            return false;
         } else if (this.surfaceVariant != other.surfaceVariant) {
            return false;
         } else if (this.onSurfaceVariant != other.onSurfaceVariant) {
            return false;
         } else if (this.outline != other.outline) {
            return false;
         } else if (this.shadow != other.shadow) {
            return false;
         } else if (this.inverseSurface != other.inverseSurface) {
            return false;
         } else {
            return this.inverseOnSurface != other.inverseOnSurface ? false : this.inversePrimary == other.inversePrimary;
         }
      }
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.primary;
      result = 31 * result + this.onPrimary;
      result = 31 * result + this.primaryContainer;
      result = 31 * result + this.onPrimaryContainer;
      result = 31 * result + this.secondary;
      result = 31 * result + this.onSecondary;
      result = 31 * result + this.secondaryContainer;
      result = 31 * result + this.onSecondaryContainer;
      result = 31 * result + this.tertiary;
      result = 31 * result + this.onTertiary;
      result = 31 * result + this.tertiaryContainer;
      result = 31 * result + this.onTertiaryContainer;
      result = 31 * result + this.error;
      result = 31 * result + this.onError;
      result = 31 * result + this.errorContainer;
      result = 31 * result + this.onErrorContainer;
      result = 31 * result + this.background;
      result = 31 * result + this.onBackground;
      result = 31 * result + this.surface;
      result = 31 * result + this.onSurface;
      result = 31 * result + this.surfaceVariant;
      result = 31 * result + this.onSurfaceVariant;
      result = 31 * result + this.outline;
      result = 31 * result + this.shadow;
      result = 31 * result + this.inverseSurface;
      result = 31 * result + this.inverseOnSurface;
      return 31 * result + this.inversePrimary;
   }
}
