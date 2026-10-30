package com.moon.module.impl.player.sessioninformation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class KillDeathDetector {
   private final List<String> killPatterns = new ArrayList<>();
   private final List<String> deathPatterns = new ArrayList<>();
   private File killsFile;
   private File deathsFile;

   public void loadKillPatterns(File file) {
      this.killPatterns.clear();
      this.killPatterns.addAll(this.readLines(file));
      this.killsFile = file;
   }

   public void loadDeathPatterns(File file) {
      this.deathPatterns.clear();
      this.deathPatterns.addAll(this.readLines(file));
      this.deathsFile = file;
   }

   public void reload() {
      if (this.killsFile != null && this.deathsFile != null && this.killsFile.exists() && this.deathsFile.exists()) {
         this.loadKillPatterns(this.killsFile);
         this.loadDeathPatterns(this.deathsFile);
      }
   }

   private List<String> readLines(File file) {
      try {
         return Files.lines(file.toPath()).toList();
      } catch (IOException e) {
         return List.of();
      }
   }

   public List<String> getKillPatterns() {
      return this.killPatterns;
   }

   public List<String> getDeathPatterns() {
      return this.deathPatterns;
   }

   public File getKillsFile() {
      return this.killsFile;
   }

   public File getDeathsFile() {
      return this.deathsFile;
   }
}
