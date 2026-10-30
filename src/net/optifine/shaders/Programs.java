package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;

public class Programs {
   private final List<Program> programs = new ArrayList<>();
   private final Program programNone = this.make("", ProgramStage.NONE, true);

   public Program make(String string, ProgramStage programStage, Program program) {
      int var4 = this.programs.size();
      Program var5 = new Program(var4, string, programStage, program);
      this.programs.add(var5);
      return var5;
   }

   private Program make(String string, ProgramStage programStage, boolean boolean3) {
      int var4 = this.programs.size();
      Program var5 = new Program(var4, string, programStage, boolean3);
      this.programs.add(var5);
      return var5;
   }

   public Program makeGbuffers(String string, Program program) {
      return this.make(string, ProgramStage.GBUFFERS, program);
   }

   public Program makeComposite(String string) {
      return this.make(string, ProgramStage.COMPOSITE, this.programNone);
   }

   public Program makeDeferred(String string) {
      return this.make(string, ProgramStage.DEFERRED, this.programNone);
   }

   public Program makeShadow(String string, Program program) {
      return this.make(string, ProgramStage.SHADOW, program);
   }

   public Program makeVirtual(String string) {
      return this.make(string, ProgramStage.NONE, true);
   }

   public Program[] makeComposites(String string, int integer) {
      Program[] var5 = new Program[integer];

      for (int var6 = 0; var6 < integer; var6++) {
         String var7 = var6 == 0 ? string : string + var6;
         var5[var6] = this.makeComposite(var7);
      }

      return var5;
   }

   public Program[] makeDeferreds(String string, int integer) {
      Program[] var5 = new Program[integer];

      for (int var6 = 0; var6 < integer; var6++) {
         String var7 = var6 == 0 ? string : string + var6;
         var5[var6] = this.makeDeferred(var7);
      }

      return var5;
   }

   public Program getProgramNone() {
      return this.programNone;
   }

   public int getCount() {
      return this.programs.size();
   }

   public Program getProgram(String string) {
      if (string != null) {
         for (Program var6 : this.programs) {
            String var7 = var6.getName();
            if (var7.equals(string)) {
               return var6;
            }
         }
      }

      return null;
   }

   public String[] getProgramNames() {
      String[] var4 = new String[this.programs.size()];

      for (int var5 = 0; var5 < var4.length; var5++) {
         var4[var5] = this.programs.get(var5).getName();
      }

      return var4;
   }

   public Program[] getPrograms() {
      return this.programs.toArray(new Program[0]);
   }

   public Program[] getPrograms(Program program1, Program program2) {
      int var6 = program1.getIndex();
      int var7 = program2.getIndex();
      if (var6 > var7) {
         int var8 = var6;
         var6 = var7;
         var7 = var8;
      }

      Program[] var10 = new Program[var7 - var6 + 1];

      for (int var9 = 0; var9 < var10.length; var9++) {
         var10[var9] = this.programs.get(var6 + var9);
      }

      return var10;
   }

   @Override
   public String toString() {
      return this.programs.toString();
   }
}
