package com.moon.render.attributes.token;

import com.moon.render.attributes.AttributeTokenType;
import com.moon.render.attributes.token.impl.BracesHandler;
import com.moon.render.attributes.token.impl.BracketHandler;
import com.moon.render.attributes.token.impl.ClosingBracesHandler;
import com.moon.render.attributes.token.impl.ClosingBracketHandler;
import com.moon.render.attributes.token.impl.ClosingParenthesesHandler;
import com.moon.render.attributes.token.impl.CommaHandler;
import com.moon.render.attributes.token.impl.EqualsHandler;
import com.moon.render.attributes.token.impl.ParenthesesHandler;
import com.moon.render.attributes.token.impl.QuotationHandler;
import java.util.HashMap;

public class AttributeTokenHandlerRegistry extends HashMap<AttributeTokenType, AttributeTokenHandler> {
   public AttributeTokenHandlerRegistry() {
      this.put(AttributeTokenType.PARENTHESES, new ParenthesesHandler());
      this.put(AttributeTokenType.CLOSING_PARENTHESES, new ClosingParenthesesHandler());
      this.put(AttributeTokenType.QUOTATION, new QuotationHandler());
      this.put(AttributeTokenType.BRACKET, new BracketHandler());
      this.put(AttributeTokenType.CLOSING_BRACKET, new ClosingBracketHandler());
      this.put(AttributeTokenType.BRACES, new BracesHandler());
      this.put(AttributeTokenType.CLOSING_BRACES, new ClosingBracesHandler());
      this.put(AttributeTokenType.EQUALS, new EqualsHandler());
      this.put(AttributeTokenType.COMMA, new CommaHandler());
   }
}
