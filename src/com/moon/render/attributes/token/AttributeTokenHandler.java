package com.moon.render.attributes.token;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;

public abstract class AttributeTokenHandler {
   private final AttributeTokenType tokenType;

   public abstract void handle(AttributeParseState state, char character);

   protected boolean canEmitValue(AttributeParseState state) {
      return state.inValue && !state.quoted && !state.buffer.isBlank() && !state.inStruct;
   }

   public AttributeTokenType getTokenType() {
      return this.tokenType;
   }

   public AttributeTokenHandler(AttributeTokenType tokenType) {
      this.tokenType = tokenType;
   }
}
