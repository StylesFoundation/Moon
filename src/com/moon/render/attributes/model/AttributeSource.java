package com.moon.render.attributes.model;

public record AttributeSource<T>(String content, Class<T> resultType) {
}
