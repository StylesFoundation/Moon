#version 330 core
#include <prelude/o_roundedrect.glsl>
#include <prelude/scissor.glsl>

out vec4 fragColor;

uniform Stencil stencil;

const int FLAG_TOP_LEFT = 1 << 0;
const int FLAG_TOP_RIGHT = 1 << 1;
const int FLAG_BOTTOM_LEFT = 1 << 2;
const int FLAG_BOTTOM_RIGHT = 1 << 3;

float circle(vec2 pixel)
{
    float tR = oRadius;
    bool topLeft = pixel.x <= tR && pixel.y <= tR;
    bool bottomLeft = pixel.x <= tR && pixel.y >= (oSize.y - tR);
    bool topRight = pixel.x >= (oSize.x - tR) && pixel.y <= tR;
    bool bottomRight = pixel.x >= (oSize.x - tR) && pixel.y >= (oSize.y - tR);

    float l = oRadius - smoothness;
    float r = oRadius + smoothness;
    float factor = 0.0;
    float alphaValue = 1.0;
    if (topLeft) {
        factor = smoothstep(l, r, length(pixel - vec2(oRadius, oRadius)));
    } else if (bottomLeft) {
        factor = smoothstep(l, r, length(pixel - vec2(oRadius, (oSize.y - tR))));
    } else if (topRight) {
        factor = smoothstep(l, r, length(pixel - vec2((oSize.x - oRadius), oRadius)));
    } else if (bottomRight) {
        factor = smoothstep(l, r, length(pixel - vec2((oSize.x - oRadius), (oSize.y - oRadius))));
    }
    alphaValue *= 1.0 - factor;
    return alphaValue;
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
    vec2 position = oPosition * oScreenSize;
    vec2 pixel = position - oOrigin;
    float mod = 0;
    float tR = oRadius;

    int flags = int(oFlags);
    bool tl = (flags & FLAG_TOP_LEFT) == FLAG_TOP_LEFT;
    bool tr = (flags & FLAG_TOP_RIGHT) == FLAG_TOP_RIGHT;
    bool bl = (flags & FLAG_BOTTOM_LEFT) == FLAG_BOTTOM_LEFT;
    bool br = (flags & FLAG_BOTTOM_RIGHT) == FLAG_BOTTOM_RIGHT;
    bool topLeft = pixel.x <= tR && pixel.y <= tR;
    bool bottomLeft = pixel.x <= tR && pixel.y >= (oSize.y - tR);
    bool topRight = pixel.x >= (oSize.x - tR) && pixel.y <= tR;
    bool bottomRight = pixel.x >= (oSize.x - tR) && pixel.y >= (oSize.y - tR);
    bool override = (pixel.x >= tR && pixel.x <= (oSize.x - tR))
    || (pixel.y >= tR && pixel.y <= (oSize.y - tR))
    || topLeft && tl
    || topRight && tr
    || bottomLeft && bl
    || bottomRight && br;

    if (override) {
        mod = 1;
    } else {
        mod = circle(pixel);
    }

    vec4 c = vec4(oColor.rgb, mod);
    fragColor = vec4(c.rgb, c.a * oColor.a);
}