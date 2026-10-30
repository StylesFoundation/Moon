package com.moon.scripting.api.functions.render;

import com.moon.scripting.api.enums.TemplateType;
import com.moon.scripting.api.types.render.RenderBatch;
import com.moon.scripting.api.types.templates.RenderTemplate;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;

@FunctionContainer(value = "render/batched", path = "functions/render/batched")
@Description("Contains recommended rendering functions for batched rendering, please refer to the rendering examples for a proper tutorial")
public interface BatchedRenderFunctions {
   @Description("Prepares a batch for future insertion and rendering")
   RenderBatch prepare_batch(@Parameter("type") @Description("The template type used") TemplateType templateType);

   @Description("Renders a given batch of templates")
   void render_batch(
      @Parameter("type") @Description("The template type used") TemplateType templateType,
      @Parameter("templates") @Description("The templates to render") RenderTemplate[] arr
   );
}
