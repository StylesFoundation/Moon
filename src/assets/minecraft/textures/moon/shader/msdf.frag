#version 330 core
#include <prelude/o_texture.glsl>

out vec4 fragColor;

uniform sampler2D tex;

float median(float r, float g, float b)
{
    return max(min(r, g), min(max(r, g), b));
}

void main()
{
    float distanceFactor = 2.0 * 0.5;
    vec4 msdf = texture(tex, oTexCoord);
    float distance = distanceFactor * (median(msdf.r, msdf.g, msdf.b) - 0.5);
    float glyphAlpha = clamp(distance + 0.5, 0.0, 1.0);
    fragColor = vec4(oColor.rgb, glyphAlpha * oColor.a);
}