package com.moon.render.attributes.token.impl;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeField;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.token.AttributeTokenHandler;
import java.util.List;

public class CommaHandler extends AttributeTokenHandler {
   public CommaHandler() {
      super(AttributeTokenType.COMMA);
   }

   @Override
   public void handle(AttributeParseState state, char character) {
      if (state.inList && state.fieldNamePending) {
         List structFlow = state.currentStruct.flow();
         String fieldValue = state.quoted ? state.buffer : state.buffer.replace(" ", "");
         structFlow.add(new AttributeField(state.pendingFieldName, fieldValue));
         state.pendingFieldName = "";
         state.buffer = "";
         state.fieldNamePending = false;
      } else if (this.canEmitValue(state)) {
         state.currentList.flow().add(new AttributeValue(state.buffer));
         state.buffer = "";
      } else {
         state.buffer = state.buffer + character;
      }
   }
}
