package com.moon.scripting.api.functions.render;

import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.FunctionContainer;
import com.moon.scripting.documentation.annotation.Parameter;

@FunctionContainer(value = "render/immediate", path = "functions/render/immediate")
@Description(
   "Contains non-recommended rendering functions for immediate rendering<br><br>## USAGE NOT RECOMMENDED ##<br><bold>Please refer to `render.batched` for proper rendering unless you want to use minecraft font!<bold>"
)
public interface ImmediateRenderFunctions {
   @Description("Renders a rectangle at the given position with the given boundaries and color")
   void rect(
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("width") @Description("The width") float width,
      @Parameter("height") @Description("The height") float height,
      @Parameter("color") @Description("The color") int color
   );

   @Description("Renders a gradient rectangle at the given position with the given boundaries and colors")
   void gradient_rect(
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("width") @Description("The width") float width,
      @Parameter("height") @Description("The height") float height,
      @Parameter("from") @Description("The from color") int from,
      @Parameter("to") @Description("The to color") int to,
      @Parameter("vertical") @Description("Whether the gradient should be vertical or horizontal") boolean vertical
   );

   @Description("Renders a rounded rectangle at the given position with the given boundaries, radius and color")
   void rounded_rect(
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("width") @Description("The width") float width,
      @Parameter("height") @Description("The height") float height,
      @Parameter("radius") @Description("The corner radius") float radius,
      @Parameter("color") @Description("The color") int color
   );

   @Description("Renders an outlined rounded rectangle at the given position with the given boundaries, radius, thickness and color")
   void outline_rounded_rect(
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("width") @Description("The width") float width,
      @Parameter("height") @Description("The height") float height,
      @Parameter("radius") @Description("The corner radius") float radius,
      @Parameter("thickness") @Description("The thickness of the outline") float thickness,
      @Parameter("color") @Description("The color") int color
   );

   @Description("Renders a triangle at the given position with the given color")
   void triangle(
      @Parameter("top_x") @Description("The top x of the triangle") float topX,
      @Parameter("top_y") @Description("The top y of the triangle") float topY,
      @Parameter("left_x") @Description("The left x of the triangle") float leftX,
      @Parameter("left_y") @Description("The left y of the triangle") float leftY,
      @Parameter("right_x") @Description("The right x of the triangle") float rightX,
      @Parameter("right_y") @Description("The right y of the triangle") float rightY,
      @Parameter("color") @Description("The color") int color
   );

   @Description("Renders a text with the minecraft font at the given position")
   void minecraft_text(
      @Parameter("text") @Description("The text to be rendered") String text,
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("color") @Description("The color") int color,
      @Parameter("shadow") @Description("Whether to apply a drop-shadow or not") boolean shadow
   );

   void text(
      @Parameter("font_type") @Description("The type of font to render the text with") CustomFontType customFontType,
      @Parameter("text") @Description("The text to be rendered") String text,
      @Parameter("size") @Description("The font size to use") float size,
      @Parameter("x") @Description("The x position") float x,
      @Parameter("y") @Description("The y position") float y,
      @Parameter("color") @Description("The color") int color,
      @Parameter("shadow") @Description("Whether to apply a drop-shadow or not") boolean shadow
   );
}
