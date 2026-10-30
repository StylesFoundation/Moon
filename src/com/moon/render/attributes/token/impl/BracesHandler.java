package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeStruct;
import com.moon.render.attributes.token.AttributeTokenHandler;
import java.util.ArrayList;

public class BracesHandler extends AttributeTokenHandler {
   public BracesHandler() {
      super(AttributeTokenType.BRACES);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (state.inValue) {
         state.inStruct = true;
      }

      state.currentStruct = new AttributeStruct(state.buffer.trim(), new ArrayList<>());
      state.inList = true;
   }
}
