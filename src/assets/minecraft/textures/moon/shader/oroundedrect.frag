#version 330 core
#include <prelude/o_oroundedrect.glsl>
#include <prelude/scissor.glsl>

out vec4 fragColor;

uniform Stencil stencil;

float sdf(vec2 c, vec2 s, float r)
{
    return length(max(abs(c) - s + r, 0.0)) - r;
}

float sl(float t)
{
    if (t >= 6) {
        return 0.4;
    }
    float base = 0;
    base += t / 10;
    return base;
}

float sr(float t)
{
    if (t >= 6) {
        return 0.6;
    }
    float base = 1;
    base -= t / 10;
    return base;
}

void main(void)
{
    // Process stencil
    vec2 stenPos = oPosition;
    vec4 stenArea = stencil.area;
    bool stenReq = stencil.required == 1;
    bool exceeds = stenPos.x < stenArea.x || stenPos.y < stenArea.y || stenPos.x > stenArea.z || stenPos.y > stenArea.w;
    if (stenReq && exceeds)
    {
        discard;
    }
    float thickness = oThickness;
    vec4 color = oColor;
    vec2 pixel = oPosition * oScreenSize - oOrigin;
    vec2 center = oSize / 2.0;
    float factor = abs((sdf(pixel - center, center - 1, oRadius)) / thickness);
    float sa = smoothstep(min(sl(thickness), 0.4), min(sr(thickness), 0.6), factor);
    vec4 c = vec4(color.rgb, 1 - sa);
    fragColor = vec4(c.rgb, c.a * color.a);
}