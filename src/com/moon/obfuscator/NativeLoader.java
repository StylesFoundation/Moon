package com.moon.obfuscator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;

public class NativeLoader {
   private static final int MIN_PREFIX_LENGTH = 3;
   public static final String NATIVE_FOLDER_PATH_PREFIX = "moon-temp";
   private static File temporaryDir;

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void loadLibraryFromJar(String path) throws IOException {
      String[] parts = path.split("/");
      String filename = parts.length > 1 ? parts[parts.length - 1] : null;
      if (temporaryDir == null) {
         temporaryDir = createTempDirectory();
         temporaryDir.deleteOnExit();
      }

      assert filename != null;
      File temp = new File(temporaryDir, filename);

      try {
         try (InputStream is = NativeLoader.class.getResourceAsStream(path)) {
            Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
         }
      } catch (IOException e) {
         FileUtils.deleteQuietly(temp);
         throw e;
      } catch (NullPointerException e) {
         FileUtils.deleteQuietly(temp);
         throw new FileNotFoundException("File " + path + " was not found inside JAR.");
      }

      try {
         System.load(temp.getAbsolutePath());
      } finally {
         if (isPosixCompliant()) {
            FileUtils.deleteQuietly(temp);
         } else {
            temp.deleteOnExit();
         }
      }
   }

   private static boolean isPosixCompliant() {
      try {
         return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
      } catch (FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) {
         return false;
      }
   }

   private static File createTempDirectory() {
      String tempDir = System.getProperty("java.io.tmpdir");
      File generatedDir = new File(tempDir, "moon-temp");
      generatedDir.mkdir();
      return generatedDir;
   }
}
