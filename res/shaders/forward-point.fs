#version 120

varying vec2 texture;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight
{
    vec3 color;
    float intensity;
};
struct Attenuation{
	float constant;
	float linear;
	float exponent;
};
struct PointLight{
	BaseLight base;
	Attenuation atten;
	vec3 position;
	float range;
};

uniform vec3 camera;
uniform sampler2D diffuse;

uniform float specularIntensity;
uniform float specularPower;

uniform PointLight pointLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
    float diffuseFactor = dot(normal, -direction);
    
    vec4 diffuseColor = vec4(0,0,0,0);
    vec4 specularColor = vec4(0,0,0,0);
    
    if(diffuseFactor > 0)
    {
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
        
        vec3 cameraDirection = normalize(camera - worldPos0);
        vec3 reflectDirection = normalize(reflect(direction, normal));
        
        float specularFactor = dot(cameraDirection, reflectDirection);
        specularFactor = pow(specularFactor, specularPower);
        
        if(specularFactor > 0)
        {
            specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
        }
    }
    
    return diffuseColor + specularColor;
}

vec4 calcPointLight(PointLight light,vec3 normal){
vec3 lightdirection = worldPos0 - light.position;
float distanceToPoint = length(lightdirection);

if(distanceToPoint > light.range)
	return vec4(0,0,0,0);

lightdirection = normalize(lightdirection);
vec4 color = calcLight(light.base,lightdirection,normal);
float attenuation = light.atten.constant+
					light.atten.linear*distanceToPoint+
					light.atten.exponent * distanceToPoint *distanceToPoint +
					0.0001;
return color/attenuation;
}

void main()
{
    gl_FragColor = texture2D(diffuse, texture.xy) * calcPointLight(pointLight, normalize(normal0));
}
