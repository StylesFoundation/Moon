#version 330 core
#include <prelude/o_kawase.glsl>

out vec4 fragColor;

uniform Kawase kawase;

#define halfPixel (kawase.texelSize * 0.5)

void main(){
    vec2 offset = kawase.offset;
    vec4 tex0 = texture2D(kawase.texture, oTexCoord) * 4.0;
    vec4 sum = vec4(tex0);
    float sumAlpha = 0.0;
    sum.rgb *= sum.a;
    vec4 tex1 = texture2D(kawase.texture, oTexCoord - halfPixel.xy * offset);
    tex1.rgb *= tex1.a;
    sum += tex1;
    vec4 tex2 = texture2D(kawase.texture, oTexCoord + halfPixel.xy * offset);
    tex2.rgb *= tex2.a;
    sum += tex2;
    vec4 tex3 = texture2D(kawase.texture, oTexCoord + (vec2(halfPixel.x, -halfPixel.y) * offset));
    tex3.rgb *= tex3.a;
    sum += tex3;
    vec4 tex4 = texture2D(kawase.texture, oTexCoord - vec2(halfPixel.x, -halfPixel.y) * offset);
    tex4.rgb *= tex4.a;
    sum += tex4;
    fragColor = vec4((sum.rgb / sum.a) / 8, sum.a / 8);
}
