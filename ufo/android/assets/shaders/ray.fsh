uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
	vec4 color = texture2D(u_texture, v_texCoords) * v_color;
	if (color.a > 0.5) {
		color.a = 1.0 - sin(v_texCoords.y / 0.64);
	}
	// color.rg = vec2(1.0 - v_texCoords.y * 1.25);
	gl_FragColor = color;
}