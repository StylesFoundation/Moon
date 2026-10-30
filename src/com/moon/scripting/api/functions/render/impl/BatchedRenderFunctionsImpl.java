package com.moon.scripting.api.functions.render.impl;

import com.moon.scripting.api.enums.TemplateType;
import com.moon.scripting.api.enums.TemplateTypeMapper;
import com.moon.scripting.api.functions.render.BatchedRenderFunctions;
import com.moon.scripting.api.types.render.RenderBatch;
import com.moon.scripting.api.types.render.TemplateRenderBatch;
import com.moon.scripting.api.types.templates.RenderTemplate;

public class BatchedRenderFunctionsImpl implements BatchedRenderFunctions {
   @Override
   public RenderBatch prepare_batch(TemplateType templateType) {
      return new TemplateRenderBatch(TemplateTypeMapper.toRenderType(templateType));
   }

   @Override
   public void render_batch(TemplateType templateType, RenderTemplate[] templates) {
      TemplateRenderBatch batch = new TemplateRenderBatch(TemplateTypeMapper.toRenderType(templateType));

      for (RenderTemplate template : templates) {
         batch.add(template);
      }

      batch.render();
   }
}
