package com.moon.render.attributes;

import com.moon.render.attributes.model.AttributeGroup;
import com.moon.render.attributes.model.AttributeList;
import com.moon.render.attributes.model.AttributeStruct;
import java.util.ArrayList;
import java.util.List;

public class AttributeParseState {
   public final List<AttributeGroup> groups = new ArrayList<>();
   public boolean inGroupHeader;
   public boolean inValue;
   public boolean inStruct;
   public boolean inList;
   public boolean fieldNamePending;
   public boolean quoted;
   public AttributeGroup currentGroup;
   public AttributeList currentList;
   public AttributeStruct currentStruct;
   public String buffer = "";
   public String pendingFieldName;
}
