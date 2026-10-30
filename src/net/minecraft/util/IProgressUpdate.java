package net.minecraft.util;

public interface IProgressUpdate {
   void displaySavingString(String string);

   void resetProgressAndMessage(String string);

   void displayLoadingString(String string);

   void setLoadingProgress(int integer);

   void setDoneWorking();
}
