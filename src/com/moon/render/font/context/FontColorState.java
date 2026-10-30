package com.moon.render.font.context;

public class FontColorState {
   private boolean bold;
   private boolean italic;
   private boolean obfuscated;
   private boolean strikethrough;
   private boolean underlined;

   public void reset() {
      this.bold = false;
      this.italic = false;
      this.obfuscated = false;
      this.strikethrough = false;
      this.underlined = false;
   }

   public boolean isBold() {
      return this.bold;
   }

   public boolean isItalic() {
      return this.italic;
   }

   public boolean isObfuscated() {
      return this.obfuscated;
   }

   public boolean isStrikethrough() {
      return this.strikethrough;
   }

   public boolean isUnderlined() {
      return this.underlined;
   }

   public FontColorState setBold(boolean bold) {
      this.bold = bold;
      return this;
   }

   public FontColorState setItalic(boolean italic) {
      this.italic = italic;
      return this;
   }

   public FontColorState setObfuscated(boolean obfuscated) {
      this.obfuscated = obfuscated;
      return this;
   }

   public FontColorState setStrikethrough(boolean strikethrough) {
      this.strikethrough = strikethrough;
      return this;
   }

   public FontColorState setUnderlined(boolean underlined) {
      this.underlined = underlined;
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof FontColorState other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isBold() != other.isBold()) {
         return false;
      } else if (this.isItalic() != other.isItalic()) {
         return false;
      } else if (this.isObfuscated() != other.isObfuscated()) {
         return false;
      } else {
         return this.isStrikethrough() != other.isStrikethrough() ? false : this.isUnderlined() == other.isUnderlined();
      }
   }

   protected boolean canEqual(Object obj) {
      return obj instanceof FontColorState;
   }

   @Override
   public int hashCode() {
      int result = 59 + (this.isBold() ? 79 : 97);
      result = result * 59 + (this.isItalic() ? 79 : 97);
      result = result * 59 + (this.isObfuscated() ? 79 : 97);
      result = result * 59 + (this.isStrikethrough() ? 79 : 97);
      return result * 59 + (this.isUnderlined() ? 79 : 97);
   }

   @Override
   public String toString() {
      boolean bold = this.isBold();
      boolean italic = this.isItalic();
      boolean obfuscated = this.isObfuscated();
      boolean underlined = this.isUnderlined();
      boolean strikethrough = this.isStrikethrough();
      return "FontColorState(bold=" + bold + ", italic=" + italic + ", obfuscated=" + obfuscated + ", strikethrough=" + strikethrough + ", underlined=" + underlined + ")";
   }
}
