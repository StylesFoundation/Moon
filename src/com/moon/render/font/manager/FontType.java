package com.moon.render.font.manager;

public enum FontType {
   ROBOTO_MEDIUM(FontRenderMode.MSDF, "Roboto-Medium", 0.2F, 0.04F, -0.99F),
   INTER_MEDIUM(FontRenderMode.MSDF, "Inter-Medium", 0.35F, 0.05F, -0.97F),
   INTER_SEMI_BOLD(FontRenderMode.MSDF, "Inter-SemiBold", 0.325F, 0.05F, -0.99F),
   TAHOMA(FontRenderMode.MSDF, "Tahoma", 0.25F, 0.05F, -0.99F),
   VERDANA(FontRenderMode.MSDF, "Verdana", 0.25F, 0.05F, -0.99F),
   SF(FontRenderMode.MSDF, "SanFrancisco", 0.5F, 0.05F, -0.99F),
   SF_UI(FontRenderMode.MSDF, "SFUI", 0.25F, 0.05F, -0.98F),
   SOURCE_SANS(FontRenderMode.MSDF, "SourceSansPro-Regular", 0.25F, 0.03F, -0.99F),
   OPENSANS_MEDIUM(FontRenderMode.MSDF, "OpenSans-Medium", 0.25F, 0.03F, -0.99F),
   OPENSANS_SEMIBOLD(FontRenderMode.MSDF, "OpenSans-SemiBold", 0.25F, 0.03F, -0.99F),
   MATERIAL_ICONS(FontRenderMode.MSDF, "MaterialIcons-Regular", 0.25F, 0.03F, -0.99F),
   PRODUCT_SANS(FontRenderMode.MSDF, "ProductSans", 0.1F, 0.04F, -0.97F),
   VOLTE_SEMI_BOLD(FontRenderMode.MSDF, "Volte-SemiBold", 0.15F, 0.04F, -0.99F),
   MATERIAL_SYMBOLS_OUTLINED_REGULAR(FontRenderMode.MSDF, "MaterialSymbolsOutlined-Regular", 0.3F, 0.03F, -0.99F);

   private final FontRenderMode renderMode;
   private final String resourceName;
   private final float weight;
   private final float outlineOffset;
   private final float sdfOffset;

   public FontRenderMode getRenderMode() {
      return this.renderMode;
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public float getWeight() {
      return this.weight;
   }

   public float getOutlineOffset() {
      return this.outlineOffset;
   }

   public float getSdfOffset() {
      return this.sdfOffset;
   }

   FontType(FontRenderMode renderMode, String resourceName, float weight, float outlineOffset, float sdfOffset) {
      this.renderMode = renderMode;
      this.resourceName = resourceName;
      this.weight = weight;
      this.outlineOffset = outlineOffset;
      this.sdfOffset = sdfOffset;
   }
}
