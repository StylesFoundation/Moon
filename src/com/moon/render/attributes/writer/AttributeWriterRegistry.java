package com.moon.render.attributes.writer;

import com.moon.render.attributes.model.AttributeList;
import com.moon.render.attributes.model.AttributeNode;
import com.moon.render.attributes.model.AttributeStruct;
import com.moon.render.attributes.model.AttributeValue;
import com.moon.render.attributes.writer.impl.ListAttributeWriter;
import com.moon.render.attributes.writer.impl.StructAttributeWriter;
import com.moon.render.attributes.writer.impl.ValueAttributeWriter;
import java.util.HashMap;

public class AttributeWriterRegistry extends HashMap<Class<? extends AttributeNode>, AttributeWriter> {
   public AttributeWriterRegistry() {
      this.put(AttributeValue.class, new ValueAttributeWriter());
      this.put(AttributeList.class, new ListAttributeWriter());
      this.put(AttributeStruct.class, new StructAttributeWriter());
   }
}
