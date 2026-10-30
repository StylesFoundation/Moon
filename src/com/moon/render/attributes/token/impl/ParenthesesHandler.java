package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.token.AttributeTokenHandler;
import java.util.ArrayList;

public class ParenthesesHandler extends AttributeTokenHandler {
   public ParenthesesHandler() {
      super(AttributeTokenType.PARENTHESES);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      state.inGroupHeader = true;
      state.currentGroup = new AttributeGroup(state.buffer, new ArrayList<>());
      state.buffer = "";
   }
}
