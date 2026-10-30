package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.token.AttributeTokenHandler;

public class QuotationHandler extends AttributeTokenHandler {
   public QuotationHandler() {
      super(AttributeTokenType.QUOTATION);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (!state.quoted) {
         state.buffer = "";
         state.quoted = true;
      } else {
         state.quoted = false;
      }
   }
}
