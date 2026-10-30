package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeField;
import com.moon.render.attributes.token.AttributeTokenHandler;

public class ClosingBracesHandler extends AttributeTokenHandler {
   public ClosingBracesHandler() {
      super(AttributeTokenType.CLOSING_BRACES);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (state.inValue && state.currentList != null) {
         state.currentList.flow().add(state.currentStruct);
      } else if (state.currentGroup != null) {
         state.currentGroup.flow().add(state.currentStruct);
      }

      if (state.fieldNamePending) {
         String fieldValue = state.quoted ? state.buffer : state.buffer.replace(" ", "");
         state.currentStruct.flow().add(new AttributeField(state.pendingFieldName, fieldValue));
         state.fieldNamePending = false;
         state.buffer = "";
         state.pendingFieldName = "";
      }

      state.inList = false;
   }
}
