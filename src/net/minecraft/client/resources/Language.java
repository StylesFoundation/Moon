package net.minecraft.client.resources;

public class Language implements Comparable<Language> {
   private final String languageCode;
   private final String region;
   private final String name;
   private final boolean bidirectional;

   public Language(String string1, String string2, String string3, boolean boolean4) {
      this.languageCode = string1;
      this.region = string2;
      this.name = string3;
      this.bidirectional = boolean4;
   }

   public String getLanguageCode() {
      return this.languageCode;
   }

   public boolean isBidirectional() {
      return this.bidirectional;
   }

   @Override
   public String toString() {
      return String.format("%s (%s)", this.name, this.region);
   }

   @Override
   public boolean equals(Object object) {
      return this == object || object instanceof Language && this.languageCode.equals(((Language)object).languageCode);
   }

   @Override
   public int hashCode() {
      return this.languageCode.hashCode();
   }

   public int compareTo(Language language) {
      return this.languageCode.compareTo(language.languageCode);
   }
}
