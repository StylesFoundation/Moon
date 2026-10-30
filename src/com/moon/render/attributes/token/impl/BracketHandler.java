package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseException;
import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeList;
import com.moon.render.attributes.token.AttributeTokenHandler;
import java.util.ArrayList;

public class BracketHandler extends AttributeTokenHandler {
   public BracketHandler() {
      super(AttributeTokenType.BRACKET);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (!state.inGroupHeader) {
         throw new AttributeParseException("Cannot assign array to nothing");
      }

      state.inValue = true;
      state.currentList = new AttributeList(new ArrayList<>());
   }
}
