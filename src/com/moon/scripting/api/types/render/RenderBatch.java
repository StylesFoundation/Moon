package com.moon.scripting.api.types.render;

import com.moon.scripting.api.types.templates.RenderTemplate;
import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;

@DataClassContainer(value = "render/render_batch", path = "types/render/render_batch")
@Description("Contains information about a current render batch which is soon to be drawn")
public abstract class RenderBatch {
   @Description("Puts the given render_template into the batch, make sure it matches the type you defined on creation")
   public abstract void add(
      @Parameter("template") @Description("The render template to add, you can find a list of available render templates in `types.template`") RenderTemplate renderTemplate
   );

   @Description("Renders this current render_batch, this can only be done **once**")
   public abstract void render();
}
