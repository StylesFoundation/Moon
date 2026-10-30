package com.moon.commons.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class IOUtils {
   public static String readFile(File file) throws IOException {
      FileReader reader = new FileReader(file);
      return readFile(reader);
   }

   public static String readFile(FileReader reader) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(reader);

      try {
         return readFile(bufferedReader);
      } finally {
         reader.close();
      }
   }

   public static String readFile(BufferedReader reader) throws IOException {
      try {
         return reader.lines().collect(Collectors.joining("\n"));
      } finally {
         reader.close();
      }
   }
}
