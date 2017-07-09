#version 450

in vec2 outTextCoord;
out vec4 fragColour;

uniform sampler2D texture_sampler;

void main()
{

	fragColour = texture(texture_sampler, outTextCoord);

}