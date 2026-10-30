in vec2 oPosition;
in vec2 oTexCoord;

struct KawaseFog
{
    sampler2D depth;
    sampler2D blur;
    float distance;
};