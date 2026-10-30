package com.moon.render.attributes.model;

import java.util.List;

public record AttributeGroup(String name, List<AttributeNode> flow) implements AttributeNode {
}
