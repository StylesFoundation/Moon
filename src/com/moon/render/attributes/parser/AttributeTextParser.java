package com.moon.render.attributes.parser;

import com.moon.render.attributes.AttributeParseState;
import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.model.AttributeField;
import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.model.AttributeSource;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.token.AttributeTokenHandler;
import com.moon.render.attributes.token.AttributeTokenHandlerRegistry;
import java.util.List;
import java.util.Map;

public class AttributeTextParser<T> {
   private static final Map<AttributeTokenType, AttributeTokenHandler> TOKEN_HANDLERS = new AttributeTokenHandlerRegistry();
   private final AttributeSource<T> source;
   private final AttributeParseState state = new AttributeParseState();

   public List<AttributeGroup> parse() {
      String[] lines = this.source.content().split("\n");
      int lineCount = lines.length;

      for (int i = 0; i < lineCount; i++) {
         for (char c : lines[i].toCharArray()) {
            AttributeTokenType tokenType = AttributeTokenType.fromChar(c);
            if (tokenType != null) {
               TOKEN_HANDLERS.get(tokenType).handle(this.state, c);
            } else {
               this.state.buffer = this.state.buffer + c;
            }
         }

         if (this.state.fieldNamePending) {
            String fieldValue = this.state.quoted ? this.state.buffer : this.state.buffer.replace(" ", "");
            this.state.currentStruct.flow().add(new AttributeField(this.state.pendingFieldName, fieldValue));
            this.state.fieldNamePending = false;
            this.state.pendingFieldName = "";
         }

         if (this.canEmitValue(this.state)) {
            this.state.currentList.flow().add(new AttributeValue(this.state.buffer));
         }

         this.state.buffer = "";
      }

      return this.state.groups;
   }

   private boolean canEmitValue(AttributeParseState state) {
      return state.inValue && !state.quoted && !state.buffer.isBlank() && !state.inStruct;
   }

   public AttributeTextParser(AttributeSource<T> source) {
      this.source = source;
   }
}
