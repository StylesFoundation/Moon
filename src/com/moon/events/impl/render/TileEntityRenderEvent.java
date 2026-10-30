package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRenderEvent extends EventCancellable {
   private final TileEntity tileEntity;

   public TileEntity getTileEntity() {
      return this.tileEntity;
   }

   public TileEntityRenderEvent(TileEntity tileEntity) {
      this.tileEntity = tileEntity;
   }
}
