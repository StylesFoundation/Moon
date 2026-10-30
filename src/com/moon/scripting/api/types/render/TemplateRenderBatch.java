package com.moon.scripting.api.types.render;

import com.moon.Moon;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.RenderCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.scripting.api.enums.CustomFontTypeMapper;
import com.moon.scripting.api.types.templates.RenderTemplate;
import com.moon.scripting.api.types.templates.RenderTemplateConverterRegistry;
import com.moon.scripting.api.types.templates.TextTemplate;
import java.util.ArrayList;
import java.util.List;

public class TemplateRenderBatch extends RenderBatch {
   private final RenderType renderType;
   private final List<RenderTemplate> templates = new ArrayList<>();

   public TemplateRenderBatch(RenderType renderType) {
      this.renderType = renderType;
   }

   @Override
   public void add(RenderTemplate renderTemplate) {
      this.templates.add(renderTemplate);
   }

   @Override
   public void render() {
      if (!this.templates.isEmpty()) {
         RenderTemplate firstTemplate = this.templates.get(0);
         RenderTask renderTask = this.createRenderTask(firstTemplate);
         FontRenderer fontRenderer = null;

         for (RenderTemplate template : this.templates) {
            if (template instanceof TextTemplate textTemplate) {
               if (fontRenderer == null) {
                  FontType fontType = CustomFontTypeMapper.toFontType(textTemplate.font_type);
                  fontRenderer = Moon.getInstance().getFontManager().getFont(fontType);
               }

               if (textTemplate.shadow) {
                  fontRenderer.drawWithShadow(renderTask, textTemplate.text, textTemplate.size, textTemplate.x, textTemplate.y, textTemplate.color);
               } else {
                  fontRenderer.draw(renderTask, textTemplate.text, textTemplate.size, textTemplate.x, textTemplate.y, textTemplate.color);
               }
            } else {
               RenderCommand renderCommand = RenderTemplateConverterRegistry.convert(template);
               if (renderCommand != null) {
                  renderTask.addCommand(renderCommand);
               }
            }
         }

         renderTask.render();
      }
   }

   private RenderTask createRenderTask(RenderTemplate renderTemplate) {
      if (renderTemplate instanceof TextTemplate) {
         FontType fontType = CustomFontTypeMapper.toFontType(((TextTemplate)renderTemplate).font_type);
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(fontType);
         return RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      } else {
         return RenderTask.builder().withRenderType(this.renderType).build();
      }
   }

   public static int Method993() {
      return 61;
   }

   static {
      if (Method993() == 0) {
      }
   }
}
