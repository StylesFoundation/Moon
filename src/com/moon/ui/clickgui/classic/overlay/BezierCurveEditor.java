package com.moon.ui.clickgui.classic.overlay;

import com.moon.module.setting.impl.BezierSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.vertex.VertexDataBuffer;
import java.util.Arrays;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class BezierCurveEditor extends OverlayContent {
   private final BezierControlPoint[] controlPoints = new BezierControlPoint[4];
   private final BezierSetting setting;
   private BezierControlPoint draggedPoint;

   @Override
   public void init() {
      this.controlPoints[0] = new BezierControlPoint(vector -> this.setting.setBegin(vector.getY()), false);
      this.controlPoints[1] = new BezierControlPoint(
         vector -> this.setting.setControlPoints(new float[]{vector.getX(), vector.getY()}, this.setting.getControlPoints()[1]), true
      );
      this.controlPoints[2] = new BezierControlPoint(
         vector -> this.setting.setControlPoints(this.setting.getControlPoints()[0], new float[]{vector.getX(), vector.getY()}), true
      );
      this.controlPoints[3] = new BezierControlPoint(vector -> this.setting.setEnd(vector.getY()), false);
      super.init();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.height = 150.0F;
      this.width = 150.0F;
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      bgTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 6.0F, this.colorScheme.getSecondaryContainer()));
      bgTask.render();
      float innerX = this.x + 8.0F;
      float innerY = this.y + 8.0F;
      float innerWidth = this.width - 16.0F;
      float innerHeight = this.height - 16.0F;
      float innerBottom = innerY + innerHeight;
      this.controlPoints[0].setX(innerX);
      this.controlPoints[0].setY(innerBottom - this.setting.getBegin() * innerHeight);
      this.controlPoints[1].setX(innerX + this.setting.getControlPoints()[0][0] * innerWidth);
      this.controlPoints[1].setY(innerBottom - this.setting.getControlPoints()[0][1] * innerHeight);
      this.controlPoints[2].setX(innerX + this.setting.getControlPoints()[1][0] * innerWidth);
      this.controlPoints[2].setY(innerBottom - this.setting.getControlPoints()[1][1] * innerHeight);
      this.controlPoints[3].setX(innerX + innerWidth);
      this.controlPoints[3].setY(innerBottom - this.setting.getEnd() * innerHeight);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      RenderTask lineTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withDrawMode(3).build();
      VertexDataBuffer lineBuffer = lineTask.getState().getVertexData();
      float viewportWidth = lineTask.getState().getViewportWidth();
      float viewportHeight = lineTask.getState().getViewportHeight();

      for (int i = 0; i < 101; i++) {
         float[] point = this.setting.evaluate(i / 100.0F);
         float pointX = innerX + point[0] * innerWidth;
         float pointY = innerBottom - point[1] * innerHeight;
         lineBuffer.appendPosition(pointX / viewportWidth, pointY / viewportHeight).appendColor(this.colorScheme.getOnSecondaryContainer());
      }

      lineTask.getState().setVertexCount(lineTask.getState().getVertexCount() + 101);
      lineTask.render();
      GL11.glEnable(GL11.GL_POINT_SMOOTH);
      GL11.glPointSize(10.0F);
      RenderTask pointsTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withDrawMode(0).build();
      VertexDataBuffer pointsBuffer = pointsTask.getState().getVertexData();

      for (BezierControlPoint controlPoint : this.controlPoints) {
         pointsBuffer.appendPosition(controlPoint.getX() / viewportWidth, controlPoint.getY() / viewportHeight).appendColor(this.colorScheme.getPrimary());
      }

      pointsTask.getState().setVertexCount(pointsTask.getState().getVertexCount() + 4);
      pointsTask.render();
      if (this.draggedPoint != null) {
         float normalizedY = 1.0F - (mouseY - innerY) / innerHeight;
         float normalizedX = MathHelper.clamp_float((mouseX - innerX) / innerWidth, 0.0F, 1.0F);
         boolean smoothHandle = this.draggedPoint.isSmoothHandle();
         if (smoothHandle) {
            this.draggedPoint.setX(normalizedX);
         }

         this.draggedPoint.setY(!smoothHandle ? MathHelper.clamp_float(normalizedY, 0.0F, 1.0F) : normalizedY);
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

   public BezierCurveEditor(BezierSetting setting) {
      this.setting = setting;
   }
}
