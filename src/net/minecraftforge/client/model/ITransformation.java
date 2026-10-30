package net.minecraftforge.client.model;

import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Matrix4f;

public interface ITransformation {
   Matrix4f Method363();

   EnumFacing rotate(EnumFacing enumFacing);

   int rotate(EnumFacing enumFacing, int integer);
}
