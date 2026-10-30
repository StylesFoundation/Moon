package com.moon.util.render;

import com.moon.handlers.ScaleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.src.Config;
import net.minecraft.util.Vec3;
import net.optifine.shaders.Shaders;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector4f;

public class ProjectionUtil {
   public static Vector4f projectToScreenBounds(Vec3 position) {
      RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
      Vector4f bounds = null;
      position = project((float)(position.xCoord - renderManager.Method588()), (float)(position.yCoord - renderManager.Method589()), (float)(position.zCoord - renderManager.Method590()));
      if (position != null && position.zCoord >= 0.0 && position.zCoord <= 1.0) {
         bounds = new Vector4f((float)position.xCoord, (float)position.yCoord, (float)position.zCoord, (float)position.yCoord);
         bounds.x = (float)Math.min(position.xCoord, bounds.x);
         bounds.y = (float)Math.min(position.yCoord, bounds.y);
         bounds.z = (float)Math.max(position.xCoord, bounds.z);
         bounds.w = (float)Math.max(position.yCoord, bounds.w);
      }

      return bounds;
   }

   public static Vec3 project(float x, float y, float z) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float shaderScale = Config.isShaders() ? (float)Shaders.renderWidth / Minecraft.getMinecraft().displayWidth : 1.0F;
      return GLU.gluProject(x, y, z, ActiveRenderInfo.MODELVIEW, ActiveRenderInfo.PROJECTION, ActiveRenderInfo.VIEWPORT, ActiveRenderInfo.OBJECTCOORDS)
         ? new Vec3(
            ActiveRenderInfo.OBJECTCOORDS.get(0) / scaledResolution.getScaleFactor() / shaderScale,
            scaledResolution.Method238() - ActiveRenderInfo.OBJECTCOORDS.get(1) / scaledResolution.getScaleFactor() / shaderScale,
            ActiveRenderInfo.OBJECTCOORDS.get(2)
         )
         : null;
   }
}
