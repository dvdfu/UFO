uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord0) * v_color;
	color.a = 1.0 - v_texCoord0.y;
	gl_FragColor = color;
}