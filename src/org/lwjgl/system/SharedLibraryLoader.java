package org.lwjgl.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.lwjgl.Version;

final class SharedLibraryLoader {
   private static final Lock EXTRACT_PATH_LOCK = new ReentrantLock();
   @Nullable
   @GuardedBy("EXTRACT_PATH_LOCK")
   private static Path extractPath;
   private static HashSet<Path> extractPaths = new HashSet<>(4);
   private static boolean checkedJDK8195129;

   private SharedLibraryLoader() {
   }

   static FileChannel load(String name, String filename, URL resource, @Nullable Consumer<String> load) {
      try {
         EXTRACT_PATH_LOCK.lock();

         Path extractedFile;
         try {
            if (extractPath != null) {
               extractedFile = extractPath.resolve(filename);
            } else {
               extractedFile = getExtractPath(filename, resource, load);
               Path parent = extractedFile.getParent();
               if (Platform.get() != Platform.WINDOWS || checkedJDK8195129) {
                  extractPath = parent;
               }

               initExtractPath(parent);
            }
         } finally {
            EXTRACT_PATH_LOCK.unlock();
         }

         return extract(extractedFile, resource);
      } catch (Exception e) {
         throw new RuntimeException("\tFailed to extract " + name + " library", e);
      }
   }

   private static void initExtractPath(Path extractPath) {
      if (!extractPaths.contains(extractPath)) {
         extractPaths.add(extractPath);
         String newLibPath = extractPath.toAbsolutePath().toString();
         String libPath = Configuration.LIBRARY_PATH.get();
         if (libPath != null && !libPath.isEmpty()) {
            newLibPath = newLibPath + File.pathSeparator + libPath;
         }

         System.setProperty(Configuration.LIBRARY_PATH.getProperty(), newLibPath);
         Configuration.LIBRARY_PATH.set(newLibPath);
      }
   }

   private static Path getExtractPath(String filename, URL resource, @Nullable Consumer<String> load) {
      String override = Configuration.SHARED_LIBRARY_EXTRACT_PATH.get();
      if (override != null) {
         Path root;
         Path file = (root = Paths.get(override)).resolve(filename);
         if (canWrite(root, file, resource, load)) {
            return file;
         }

         APIUtil.apiLog(String.format("\tThe path %s is not accessible. Trying other paths.", override));
      }

      String version = Version.getVersion().replace(' ', '-');
      Path root;
      Path file = (root = Paths.get(System.getProperty("java.io.tmpdir")))
         .resolve(Paths.get(Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl" + System.getProperty("user.name")), version, filename));
      if (canWrite(root, file, resource, load)) {
         return file;
      }

      Path lwjgl_version_filename = Paths.get("." + Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl"), version, filename);
      file = (root = Paths.get("").toAbsolutePath()).resolve(lwjgl_version_filename);
      if (canWrite(root, file, resource, load)) {
         return file;
      }

      file = (root = Paths.get(System.getProperty("user.home"))).resolve(lwjgl_version_filename);
      if (canWrite(root, file, resource, load)) {
         return file;
      }

      if (Platform.get() == Platform.WINDOWS) {
         String env = System.getenv("SystemRoot");
         if (env != null) {
            file = (root = Paths.get(env, "Temp")).resolve(lwjgl_version_filename);
            if (canWrite(root, file, resource, load)) {
               return file;
            }
         }

         env = System.getenv("SystemDrive");
         if (env != null) {
            file = (root = Paths.get(env + "/")).resolve(Paths.get("Temp").resolve(lwjgl_version_filename));
            if (canWrite(root, file, resource, load)) {
               return file;
            }
         }
      }

      try {
         file = Files.createTempDirectory("lwjgl");
         root = file.getParent();
         file = file.resolve(filename);
         if (canWrite(root, file, resource, load)) {
            return file;
         }
      } catch (IOException var9) {
      }

      throw new RuntimeException("Failed to find an appropriate directory to extract the native library");
   }

   private static FileChannel extract(Path file, URL resource) throws IOException {
      if (Files.exists(file)) {
         try (
            InputStream source = resource.openStream();
            InputStream target = Files.newInputStream(file);
         ) {
            if (crc(source) == crc(target)) {
               if (Configuration.DEBUG_LOADER.get(false)) {
                  APIUtil.apiLog(String.format("\tFound at: %s", file));
               }

               return lock(file);
            }
         }
      }

      APIUtil.apiLog(String.format("\tExtracting: %s", resource.getPath()));
      if (extractPath == null) {
         APIUtil.apiLog(String.format("\t        to: %s", file));
      }

      Files.createDirectories(file.getParent());

      try (InputStream source = resource.openStream()) {
         Files.copy(source, file, StandardCopyOption.REPLACE_EXISTING);
      }

      return lock(file);
   }

   private static FileChannel lock(Path file) {
      try {
         FileChannel fc = FileChannel.open(file);
         if (fc.tryLock(0L, Long.MAX_VALUE, true) == null) {
            if (Configuration.DEBUG_LOADER.get(false)) {
               APIUtil.apiLog("\tFile is locked by another process, waiting...");
            }

            fc.lock(0L, Long.MAX_VALUE, true);
         }

         return fc;
      } catch (Exception e) {
         throw new RuntimeException("Failed to lock file.", e);
      }
   }

   private static long crc(InputStream input) throws IOException {
      CRC32 crc = new CRC32();
      byte[] buffer = new byte[8192];

      int n;
      while ((n = input.read(buffer)) != -1) {
         crc.update(buffer, 0, n);
      }

      return crc.getValue();
   }

   private static boolean canWrite(Path root, Path file, URL resource, @Nullable Consumer<String> load) {
      Path testFile;
      if (Files.exists(file)) {
         if (!Files.isWritable(file)) {
            return false;
         }

         testFile = file.getParent().resolve(".lwjgl.test");
      } else {
         try {
            Files.createDirectories(file.getParent());
         } catch (IOException ignored) {
            return false;
         }

         testFile = file;
      }

      try {
         Files.write(testFile, new byte[0]);
         Files.delete(testFile);
         if (load != null && Platform.get() == Platform.WINDOWS) {
            workaroundJDK8195129(file, resource, load);
         }

         return true;
      } catch (Throwable ignored) {
         if (file == testFile) {
            canWriteCleanup(root, file);
         }

         return false;
      }
   }

   private static void canWriteCleanup(Path root, Path file) {
      try {
         Files.deleteIfExists(file);

         for (Path parent = file.getParent(); !Files.isSameFile(parent, root); parent = parent.getParent()) {
            try (Stream<Path> dir = Files.list(parent)) {
               if (dir.findAny().isPresent()) {
                  break;
               }
            }

            Files.delete(parent);
         }
      } catch (IOException var17) {
      }
   }

   private static void workaroundJDK8195129(Path file, URL resource, @Nonnull Consumer<String> load) throws Throwable {
      String filepath = file.toAbsolutePath().toString();
      if (filepath.endsWith(".dll")) {
         boolean mustCheck = false;

         for (int i = 0; i < filepath.length(); i++) {
            if (128 <= filepath.charAt(i)) {
               mustCheck = true;
            }
         }

         if (mustCheck) {
            try (FileChannel ignored = extract(file, resource)) {
               load.accept(file.toAbsolutePath().toString());
            }
         }

         checkedJDK8195129 = true;
      }
   }
}
