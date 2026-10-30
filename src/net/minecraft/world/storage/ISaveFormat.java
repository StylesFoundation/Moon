package net.minecraft.world.storage;

import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   String getName();

   ISaveHandler getSaveLoader(String string, boolean boolean2);

   List<SaveFormatComparator> getSaveList() throws AnvilConverterException;

   void flushCache();

   WorldInfo getWorldInfo(String string);

   boolean isNewLevelIdAcceptable(String string);

   boolean deleteWorldDirectory(String string);

   void renameWorld(String string1, String string2);

   boolean isConvertible(String string);

   boolean isOldMapFormat(String string);

   boolean convertMapFormat(String string, IProgressUpdate iProgressUpdate);

   boolean canLoadWorld(String string);
}
