#version 330 core
#include <prelude/world_tex.glsl>
#include <prelude/matrix.glsl>

uniform Matrix matrix;

void main(void)
{
    mat4 mvp = matrix.projection * matrix.modelview;
    gl_Position = mvp * vec4(position, 1.0);
    // Define vertex data
    oPosition = position;
    oTexCoord = texCoord;
    oLightMapCoord = lightMapCoord;
    oColor = color;
    oIndex = int(index);
}