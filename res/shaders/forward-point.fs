#version 120
#include "lighting.glh"

varying vec2 texture;
varying vec3 normal0;
varying vec3 worldPos0;

uniform PointLight pointLight;



void main()
{
    gl_FragColor = texture2D(diffuse, texture.xy) * calcPointLight(pointLight, normalize(normal0),worldPos0);
}
