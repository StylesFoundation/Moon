#version 330 core
#include <prelude/o_world_tex.glsl>

out vec4 fragColor;

uniform sampler2D tex;
uniform sampler2D particlesTex;
uniform sampler2D lightMap;

void main(void)
{
    vec4 particle;
    if (oIndex == 0) {
        particle = texture(tex, oTexCoord);
    } else {
        particle = texture(particlesTex, oTexCoord);
    }
    vec4 light = texture(lightMap, oLightMapCoord / 256.0);
    fragColor = particle * light * oColor;
}