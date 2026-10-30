package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShaderUniforms {
   private final List<ShaderUniformBase> listUniforms = new ArrayList<>();

   public void setProgram(int integer) {
      Iterator var5 = this.listUniforms.iterator();

      while (var5.hasNext()) {
         ((ShaderUniformBase)var5.next()).setProgram(integer);
      }
   }

   public void reset() {
      Iterator var4 = this.listUniforms.iterator();

      while (var4.hasNext()) {
         ((ShaderUniformBase)var4.next()).reset();
      }
   }

   public ShaderUniform1i make1i(String string) {
      ShaderUniform1i var2 = new ShaderUniform1i(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniform2i make2i(String string) {
      ShaderUniform2i var2 = new ShaderUniform2i(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniform4i make4i(String string) {
      ShaderUniform4i var2 = new ShaderUniform4i(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniform1f make1f(String string) {
      ShaderUniform1f var2 = new ShaderUniform1f(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniform3f make3f(String string) {
      ShaderUniform3f var2 = new ShaderUniform3f(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniform4f make4f(String string) {
      ShaderUniform4f var2 = new ShaderUniform4f(string);
      this.listUniforms.add(var2);
      return var2;
   }

   public ShaderUniformM4 makeM4(String string) {
      ShaderUniformM4 var2 = new ShaderUniformM4(string);
      this.listUniforms.add(var2);
      return var2;
   }
}
