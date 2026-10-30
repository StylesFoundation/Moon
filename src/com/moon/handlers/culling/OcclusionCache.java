package com.moon.handlers.culling;

public interface OcclusionCache {
   void clear();

   void markVisible(int x, int y, int z);

   void markOpaque(int x, int y, int z);

   int getState(int x, int y, int z);

   void repeatOpaque();

   void repeatVisible();
}
