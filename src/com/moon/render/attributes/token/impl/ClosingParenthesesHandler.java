package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.token.AttributeTokenHandler;

public class ClosingParenthesesHandler extends AttributeTokenHandler {
   public ClosingParenthesesHandler() {
      super(AttributeTokenType.CLOSING_PARENTHESES);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      AttributeGroup group = state.currentGroup;
      if (group != null && state.currentGroup.flow().isEmpty()) {
         group.flow().add(new AttributeValue(state.buffer));
      }

      state.groups.add(group);
      state.currentGroup = null;
      state.inGroupHeader = false;
   }
}
