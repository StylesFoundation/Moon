package com.moon.module.impl.visual.esp.renderer;

import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.StorageESP;
import com.moon.render.command.impl.shape.BracketCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.util.vector.Vector4f;

public class EspBoxRenderer extends EspEntityRenderer {
   private final StorageESP storageEsp;

   public EspBoxRenderer(ESP esp, StorageESP storageEsp) {
      super(esp);
      this.storageEsp = storageEsp;
   }

   @Override
   public void render(Entity entity) {
      Vector4f screenCoords = entity.Method940();
      float minX = screenCoords.x;
      float minY = screenCoords.y;
      float maxX = screenCoords.z;
      float maxY = screenCoords.w;
      float width = maxX - minX;
      float height = maxY - minY;
      int outlineColor = Color.BLACK.getRGB();
      int boxColor = this.getEsp().getBoxColor().getValue();
      if (this.getEsp().getBoxInfill().getValue()) {
         this.rectTask.addCommand(RectangleCommand.of(minX + 1.0F, minY + 1.0F, width - 2.0F, height - 2.0F, Integer.MIN_VALUE));
      }

      if (this.getEsp().getBoxCornered().getValue()) {
         float cornerWidth = (maxX - minX) / this.getEsp().getCornerFactor().getValue();
         float cornerHeight = this.getEsp().getBoxCorneredRelative().getValue() ? (maxY - minY) / this.getEsp().getCornerFactor().getValue() : cornerWidth;
         this.rectTask.addCommand(RectangleCommand.of(minX, minY, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, minY, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth, minY, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.5F, minY, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, maxY - 1.5F, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, maxY - cornerHeight, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth, maxY - 1.5F, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.5F, maxY - cornerHeight, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, minY + 0.5F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, minY + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth + 0.5F, minY + 0.5F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.0F, minY + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, maxY - 1.0F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, maxY - cornerHeight + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth + 0.5F, maxY - 1.0F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.0F, maxY - cornerHeight + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
      } else {
         this.rectTask.addCommand(BracketCommand.of(minX, minY, maxX, maxY, outlineColor, boxColor));
      }
   }

   public void renderTileEntity(TileEntity tileEntity) {
      Vector4f screenCoords = tileEntity.Method982();
      float minX = screenCoords.x;
      float minY = screenCoords.y;
      float maxX = screenCoords.z;
      float maxY = screenCoords.w;
      float width = maxX - minX;
      float height = maxY - minY;
      int outlineColor = Color.BLACK.getRGB();
      int boxColor = this.getTileEntityColor(tileEntity);
      if (this.storageEsp.getBoxInfill().getValue()) {
         this.rectTask.addCommand(RectangleCommand.of(minX + 1.0F, minY + 1.0F, width - 2.0F, height - 2.0F, Integer.MIN_VALUE));
      }

      if (this.storageEsp.getBoxCornered().getValue()) {
         float cornerWidth = (maxX - minX) / this.storageEsp.getCornerFactor().getValue();
         float cornerHeight = this.storageEsp.getBoxCorneredRelative().getValue() ? (maxY - minY) / this.storageEsp.getCornerFactor().getValue() : cornerWidth;
         this.rectTask.addCommand(RectangleCommand.of(minX, minY, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, minY, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth, minY, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.5F, minY, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, maxY - 1.5F, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX, maxY - cornerHeight, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth, maxY - 1.5F, cornerWidth, 1.5F, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.5F, maxY - cornerHeight, 1.5F, cornerHeight, outlineColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, minY + 0.5F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, minY + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth + 0.5F, minY + 0.5F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.0F, minY + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, maxY - 1.0F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(minX + 0.5F, maxY - cornerHeight + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - cornerWidth + 0.5F, maxY - 1.0F, cornerWidth - 1.0F, 0.5F, boxColor));
         this.rectTask.addCommand(RectangleCommand.of(maxX - 1.0F, maxY - cornerHeight + 0.5F, 0.5F, cornerHeight - 1.0F, boxColor));
      } else {
         this.rectTask.addCommand(BracketCommand.of(minX, minY, maxX, maxY, outlineColor, boxColor));
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getBoxEnabled().getValue();
   }

   public int getTileEntityColor(TileEntity tileEntity) {
      if (tileEntity instanceof TileEntityChest chest) {
         return chest.getChestType() == 1 ? this.storageEsp.getRedstoneColor().getValue() : this.storageEsp.getNormalColor().getValue();
      } else {
         return tileEntity instanceof TileEntityEnderChest ? this.storageEsp.getEnderColor().getValue() : -1;
      }
   }
}
