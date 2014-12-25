uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord0) * v_color;
	color.a = gl_FragCoord.y / 300.0;
	gl_FragColor = color;
}