package com.moon.ui.clickgui.modern.overlay;

import com.moon.module.setting.impl.BezierSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.vertex.VertexDataBuffer;
import com.moon.ui.clickgui.classic.overlay.BezierControlPoint;
import java.util.Arrays;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ClassicBezierCurveEditor extends ClassicOverlayContent {
   private final BezierControlPoint[] controlPoints = new BezierControlPoint[4];
   private final BezierSetting setting;
   private BezierControlPoint draggedPoint;

   @Override
   public void init() {
      this.controlPoints[0] = new BezierControlPoint(controlPoint -> this.setting.setBegin(controlPoint.getY()), false);
      this.controlPoints[1] = new BezierControlPoint(
         controlPoint -> this.setting.setControlPoints(new float[]{controlPoint.getX(), controlPoint.getY()}, this.setting.getControlPoints()[1]), true
      );
      this.controlPoints[2] = new BezierControlPoint(
         controlPoint -> this.setting.setControlPoints(this.setting.getControlPoints()[0], new float[]{controlPoint.getX(), controlPoint.getY()}), true
      );
      this.controlPoints[3] = new BezierControlPoint(controlPoint -> this.setting.setEnd(controlPoint.getY()), false);
      super.init();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.height = 150.0F;
      this.width = 150.0F;
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      bgTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 6.0F, -14606047));
      bgTask.render();
      float innerX = this.x + 8.0F;
      float innerY = this.y + 8.0F;
      float innerWidth = this.width - 16.0F;
      float innerHeight = this.height - 16.0F;
      float bottomY = innerY + innerHeight;
      this.controlPoints[0].setX(innerX);
      this.controlPoints[0].setY(bottomY - this.setting.getBegin() * innerHeight);
      this.controlPoints[1].setX(innerX + this.setting.getControlPoints()[0][0] * innerWidth);
      this.controlPoints[1].setY(bottomY - this.setting.getControlPoints()[0][1] * innerHeight);
      this.controlPoints[2].setX(innerX + this.setting.getControlPoints()[1][0] * innerWidth);
      this.controlPoints[2].setY(bottomY - this.setting.getControlPoints()[1][1] * innerHeight);
      this.controlPoints[3].setX(innerX + innerWidth);
      this.controlPoints[3].setY(bottomY - this.setting.getEnd() * innerHeight);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      RenderTask curveTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withDrawMode(3).build();
      VertexDataBuffer buffer = curveTask.getState().getVertexData();
      float viewportWidth = curveTask.getState().getViewportWidth();
      float viewportHeight = curveTask.getState().getViewportHeight();

      for (int i = 0; i < 101; i++) {
         float[] point = this.setting.evaluate(i / 100.0F);
         float pointX = innerX + point[0] * innerWidth;
         float pointY = bottomY - point[1] * innerHeight;
         buffer.appendPosition(pointX / viewportWidth, pointY / viewportHeight).appendColor(-5592406);
      }

      curveTask.getState().setVertexCount(curveTask.getState().getVertexCount() + 101);
      curveTask.render();
      GL11.glEnable(GL11.GL_POINT_SMOOTH);
      GL11.glPointSize(10.0F);
      RenderTask pointsTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withDrawMode(0).build();
      VertexDataBuffer pointsBuffer = pointsTask.getState().getVertexData();

      for (BezierControlPoint controlPoint : this.controlPoints) {
         pointsBuffer.appendPosition(controlPoint.getX() / viewportWidth, controlPoint.getY() / viewportHeight).appendColor(-1);
      }

      pointsTask.getState().setVertexCount(pointsTask.getState().getVertexCount() + 4);
      pointsTask.render();
      if (this.draggedPoint != null) {
         float pointY = 1.0F - (mouseY - innerY) / innerHeight;
         float pointX = MathHelper.clamp_float((mouseX - innerX) / innerWidth, 0.0F, 1.0F);
         boolean smoothHandle = this.draggedPoint.isSmoothHandle();
         if (smoothHandle) {
            this.draggedPoint.setX(pointX);
         }

         this.draggedPoint.setY(!smoothHandle ? MathHelper.clamp_float(pointY, 0.0F, 1.0F) : pointY);
         this.draggedPoint.apply();
      }
   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (button == 0) {
         BezierControlPoint hoveredPoint = Arrays.stream(this.controlPoints).filter(controlPoint -> controlPoint.isHovered(mouseX, mouseY)).findFirst().orElse(null);
         if (hoveredPoint != null) {
            this.draggedPoint = hoveredPoint;
         }
      }

      super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY, int button) {
      this.draggedPoint = null;
      super.mouseReleased(mouseX, mouseY, button);
   }

   public ClassicBezierCurveEditor(BezierSetting setting) {
      this.setting = setting;
   }
}
