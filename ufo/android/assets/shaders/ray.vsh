attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
attribute vec2 a_point;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
	v_color = a_color;
    v_texCoords = a_texCoord0;
    vec4 p = a_position;
    gl_Position = u_projTrans * p;
}