package com.moon.render.shader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class ShaderProgram {
   private static final Pattern INCLUDE_PATTERN = Pattern.compile("#include <(?<path>.*)>");
   private static final TaggedLogger LOGGER = Logger.tag("engine/shaders");
   private int programId;

   public ShaderProgram(String vertexShaderPath, String fragmentShaderPath, boolean useOptionalColor) {
      int vertexShader = this.compileShader(35633, this.loadShaderSource(vertexShaderPath));
      int fragmentShader = this.compileShader(35632, this.loadShaderSource(fragmentShaderPath));
      if (vertexShader != -1 && fragmentShader != -1) {
         this.programId = GL20.glCreateProgram();
         GL20.glAttachShader(this.programId, vertexShader);
         GL20.glAttachShader(this.programId, fragmentShader);
         GL30.glBindFragDataLocation(this.programId, 0, "fragColor");
         if (useOptionalColor) {
            LOGGER.info("Enabling optional / secondary color for " + vertexShaderPath + " and " + fragmentShaderPath);
            GL30.glBindFragDataLocation(this.programId, 1, "optionalColor");
         }

         GL20.glLinkProgram(this.programId);
         LOGGER.info("Successfully linked shaders with vertex path: {} and fragment path: {}", new Object[]{vertexShaderPath, fragmentShaderPath});
      }
   }

   public void bind() {
      GL20.glUseProgram(this.programId);
   }

   private int compileShader(int type, String source) {
      int shaderId = GL20.glCreateShader(type);
      GL20.glShaderSource(shaderId, source);
      GL20.glCompileShader(shaderId);
      if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) != 1) {
         String infoLog = GL20.glGetShaderInfoLog(shaderId, 2048);
         LOGGER.error("Failed to compile shader: {}", new Object[]{infoLog});
         return -1;
      } else {
         return shaderId;
      }
   }

   private String loadShaderSource(String path) {
      try {
         InputStream stream = this.getClass().getResourceAsStream(String.format("/engine/shaders/%s", path));
         if (stream == null) {
            LOGGER.error("Could not find input shader file {}", new Object[]{path});
            return null;
         } else {
            byte[] bytes = IOUtils.toByteArray(stream);
            return this.resolveIncludes(new String(bytes));
         }
      } catch (Exception e) {
         LOGGER.error("Failed to load shader source", e);
         return null;
      }
   }

   private String resolveIncludes(String source) {
      StringBuilder builder = new StringBuilder();

      String line;
      try (BufferedReader reader = new BufferedReader(new StringReader(source))) {
         while ((line = reader.readLine()) != null) {
            builder.append(line.startsWith("#include") ? this.resolveInclude(line) : line).append("\n");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return builder.toString();
   }

   private String resolveInclude(String line) {
      Matcher matcher = INCLUDE_PATTERN.matcher(line);
      if (!matcher.matches()) {
         LOGGER.error("Invalid import statement: {}", new Object[]{line});
         return line;
      } else {
         String includePath = matcher.group("path");
         return this.loadShaderSource(includePath);
      }
   }

   public int getProgramId() {
      return this.programId;
   }
}
