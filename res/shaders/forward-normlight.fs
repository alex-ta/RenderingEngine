#version 120

varying vec2 texture;

uniform vec3 R_normlight;
uniform sampler2D R_diffuse;

void main(){
	gl_FragColor = texture2D(R_diffuse,texture.xy) * vec4(R_normlight,1);
	
}