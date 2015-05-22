#version 120

varying vec2 texture;

uniform vec3 normlight;
uniform sampler2D sampler;

void main(){
	gl_FragColor = texture2D(sampler,texture.xy) * vec4(normlight,1);
	
}