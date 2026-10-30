package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "template_type", path = "enums/template_type")
@Description("The different batched rendering types")
public enum TemplateType {
   @Description("Applies the rectangle rendering type")
   RECT,
   @Description("Applies the gradient rendering type")
   GRADIENT,
   @Description("Applies the rounded rectangle rendering type")
   ROUNDED_RECT,
   @Description("Applies the outlined rounded rectangle rendering type")
   OUTLINED_ROUNDED_RECT,
   @Description("Applies the text rendering type")
   TEXT,
   @Description("Applies the triangle rendering type")
   TRIANGLE;
}
