package com.moon.scripting.api.functions.render;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;

@FunctionContainer(value = "render/state", path = "functions/render/state")
@Description("Allows you to control OpenGL states in a really simple way. More functionality will be added to this later on, for now it is really basic")
public interface StatesRenderFunctions {
   @Description("Disables depth")
   void enable_depth();

   @Description("Enables depth")
   void disable_depth();

   @Description("Enables blending")
   void enable_blend();

   @Description("Disables blending")
   void disable_blend();

   @Description("Applies the given blend function")
   void blend_func(@Parameter("src_factor") int srcFactor, @Parameter("dst_factor") int dstFactor);

   @Description("Applies the given blend function")
   void blend_func_separate(
      @Parameter("src_factor") int srcFactor,
      @Parameter("dst_factor") int dstFactor,
      @Parameter("src_factor_alpha") int srcFactorAlpha,
      @Parameter("dst_factor_alpha") int dstFactorAlpha
   );
}
