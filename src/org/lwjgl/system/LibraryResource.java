package org.lwjgl.system;

import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public final class LibraryResource {
   private LibraryResource() {
   }

   public static Path load(String module, String name) {
      return load(LibraryResource.class, module, name);
   }

   public static Path load(Class<?> context, String module, String name) {
      return load(context, module, name, false, true);
   }

   public static Path load(Class<?> context, String module, String name, boolean bundledWithLWJGL) {
      return load(context, module, name, bundledWithLWJGL, true);
   }

   private static Path load(Class<?> context, String module, String name, boolean bundledWithLWJGL, boolean printError) {
      APIUtil.apiLog("Loading library resource: " + name);
      APIUtil.apiLog("\tModule: " + module);
      Path path = Paths.get(name);
      if (path.isAbsolute()) {
         if (!Files.exists(path)) {
            if (printError) {
               printError();
            }

            throw new IllegalStateException("Failed to locate library resource: " + name);
         } else {
            APIUtil.apiLog("\tSuccess");
            return path;
         }
      } else {
         URL resourceURL = Library.findResource(context, module, name, bundledWithLWJGL);
         if (resourceURL == null) {
            path = loadFromLibraryPath(module, name, bundledWithLWJGL);
            if (path != null) {
               return path;
            }
         } else {
            boolean debugLoader = Configuration.DEBUG_LOADER.get(false);

            try {
               String regular = Library.getRegularFilePath(resourceURL);
               if (regular != null) {
                  APIUtil.apiLog("\tLoaded from classpath: " + regular);
                  return Paths.get(regular);
               }

               if (debugLoader) {
                  APIUtil.apiLog("\tUsing SharedLibraryLoader...");
               }

               try (FileChannel ignored = SharedLibraryLoader.load(name, name, resourceURL, null)) {
                  path = loadFromLibraryPath(module, name, bundledWithLWJGL);
                  if (path != null) {
                     return path;
                  }
               }
            } catch (Exception e) {
               if (debugLoader) {
                  e.printStackTrace(APIUtil.DEBUG_STREAM);
               }
            }
         }

         String paths = System.getProperty("java.library.path");
         if (paths != null) {
            path = load(module, name, bundledWithLWJGL, "java.library.path", paths);
            if (path != null) {
               return path;
            }
         }

         if (printError) {
            printError();
         }

         throw new IllegalStateException("Failed to locate library resource: " + name);
      }
   }

   @Nullable
   private static Path loadFromLibraryPath(String module, String libName, boolean bundledWithLWJGL) {
      String paths = Configuration.LIBRARY_PATH.get();
      return paths == null ? null : load(module, libName, bundledWithLWJGL, Configuration.LIBRARY_PATH.getProperty(), paths);
   }

   @Nullable
   private static Path load(String module, String name, boolean bundledWithLWJGL, String property, String paths) {
      Path resource = Library.findFile(paths, module, name, bundledWithLWJGL);
      if (resource == null) {
         APIUtil.apiLog(String.format("\t%s not found in %s=%s", name, property, paths));
         return null;
      } else {
         APIUtil.apiLog(String.format("\tLoaded from %s: %s", property, resource));
         return resource;
      }
   }

   public static Path load(Class<?> context, String module, Configuration<String> name, String... defaultNames) {
      return load(context, module, name, null, defaultNames);
   }

   public static Path load(Class<?> context, String module, Configuration<String> name, @Nullable Supplier<Path> fallback, String... defaultNames) {
      if (defaultNames.length == 0) {
         throw new IllegalArgumentException("No default names specified.");
      }

      String resourceName = name.get();
      if (resourceName != null) {
         return load(context, module, resourceName);
      }

      if (fallback == null && defaultNames.length <= 1) {
         return load(context, module, defaultNames[0]);
      }

      try {
         return load(context, module, defaultNames[0], false, false);
      } catch (Throwable t) {
         for (int i = 1; i < defaultNames.length; i++) {
            try {
               return load(context, module, defaultNames[i], false, fallback == null && i == defaultNames.length - 1);
            } catch (Throwable var9) {
            }
         }

         if (fallback != null) {
            return fallback.get();
         } else {
            throw t;
         }
      }
   }

   private static void printError() {
      Library.printError(
         "[LWJGL] Failed to load a library resource. Possible solutions:\n\ta) Add the directory that contains the resource to -Djava.library.path or -Dorg.lwjgl.librarypath.\n\tb) Add the JAR that contains the resource to the classpath."
      );
   }

   static {
      Library.initialize();
   }
}
