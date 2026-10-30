package org.lwjgl;

import java.util.Optional;

public final class Version {
   public static final int VERSION_MAJOR = 3;
   public static final int VERSION_MINOR = 3;
   public static final int VERSION_REVISION = 1;
   public static final Version.BuildType BUILD_TYPE = Version.BuildType.STABLE;
   private static final String version = String.valueOf(3)
      + '.'
      + 3
      + '.'
      + 1
      + BUILD_TYPE.postfix
      + ' '
      + Optional.ofNullable(Version.class.getPackage().getImplementationVersion()).orElse("SNAPSHOT");

   private Version() {
   }

   public static void main(String[] args) {
      System.out.println(version);
      System.err.println(version.substring(0, version.indexOf(32)));
   }

   public static String getVersion() {
      return version;
   }

   public enum BuildType {
      ALPHA("a"),
      BETA("b"),
      STABLE("");

      public final String postfix;

      BuildType(String postfix) {
         this.postfix = postfix;
      }
   }
}
