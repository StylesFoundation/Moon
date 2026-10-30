package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;

public abstract class RenderTemplateConverter<T extends RenderTemplate> {
   public abstract RenderCommand convert(T renderTemplate);
}
