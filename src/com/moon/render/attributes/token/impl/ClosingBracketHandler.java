package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseException;
import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.token.AttributeTokenHandler;

public class ClosingBracketHandler extends AttributeTokenHandler {
   public ClosingBracketHandler() {
      super(AttributeTokenType.CLOSING_BRACKET);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (state.inGroupHeader && state.currentGroup != null) {
         if (this.canEmitValue(state)) {
            state.currentList.flow().add(new AttributeValue(state.buffer));
         }

         state.currentGroup.flow().add(state.currentList);
         state.inValue = false;
         state.currentList = null;
      } else {
         throw new AttributeParseException("Cannot assign array to nothing");
      }
   }
}
