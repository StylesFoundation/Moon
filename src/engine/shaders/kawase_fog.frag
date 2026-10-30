#version 330 core

#include <prelude/o_kawase_fog.glsl>

out vec4 fragColor;

uniform KawaseFog kawaseFog;

float linearize_depth(float d, float zNear, float zFar)
{
    float z_n = 2.0 * d - 1.0;
    return 2.0 * zNear * zFar / (zFar + zNear - z_n * (zFar - zNear));
}

void main()
{
    vec4 depth = texture(kawaseFog.depth, oTexCoord);
    float depth_value = depth.r;
    float linearized_depth = linearize_depth(depth_value, 0.05, 500);
    float alpha = linearized_depth / (150 * kawaseFog.distance);
    vec4 color = texture2D(kawaseFog.blur, oTexCoord);
    fragColor = vec4(color.rgb, alpha);
}
