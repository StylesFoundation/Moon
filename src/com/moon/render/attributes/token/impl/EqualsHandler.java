package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.token.AttributeTokenHandler;

public class EqualsHandler extends AttributeTokenHandler {
   public EqualsHandler() {
      super(AttributeTokenType.EQUALS);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (state.inList && !state.fieldNamePending) {
         state.pendingFieldName = state.buffer.trim();
         state.buffer = "";
         state.fieldNamePending = true;
      } else {
         state.buffer = state.buffer + character;
      }
   }
}
