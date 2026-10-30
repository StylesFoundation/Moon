package net.optifine.config;

public class GlVersion {
   private final int major;
   private final int minor;
   private final int release;
   private final String suffix;

   public GlVersion(int integer1, int integer2) {
      this(integer1, integer2, 0);
   }

   public GlVersion(int integer1, int integer2, int integer3) {
      this(integer1, integer2, integer3, null);
   }

   public GlVersion(int integer1, int integer2, int integer3, String string) {
      this.major = integer1;
      this.minor = integer2;
      this.release = integer3;
      this.suffix = string;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getRelease() {
      return this.release;
   }

   public int toInt() {
      return this.minor > 9
         ? this.major * 100 + this.minor
         : (this.release > 9 ? this.major * 100 + this.minor * 10 + 9 : this.major * 100 + this.minor * 10 + this.release);
   }

   @Override
   public String toString() {
      return this.suffix == null ? this.major + "." + this.minor + "." + this.release : this.major + "." + this.minor + "." + this.release + this.suffix;
   }
}
