#version 330 core

#include <prelude/o_kawase_stencil.glsl>

out vec4 fragColor;

uniform KawaseStencil kawaseStencil;

void main()
{
    // Apply stencil
    vec4 sten = texture2D(kawaseStencil.stencil, oTexCoord);
    float alpha = sten.a;
    if (alpha == 0.0) {
        discard;
    }
    vec4 color = texture2D(kawaseStencil.texture, oTexCoord);
    fragColor = vec4(color.rgb, alpha);
}
