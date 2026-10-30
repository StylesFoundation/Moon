package com.moon.render.gl;

import org.lwjgl.opengl.GL30;

public class VertexArray {
   private final int id = GL30.glGenVertexArrays();

   public void bind() {
      GL30.glBindVertexArray(this.id);
   }

   public void delete() {
      GL30.glDeleteVertexArrays(this.id);
   }
}
