package com.moon.render.attributes.model;

import java.util.List;

public record AttributeStruct(String name, List<AttributeField> flow) implements AttributeNode {
}
