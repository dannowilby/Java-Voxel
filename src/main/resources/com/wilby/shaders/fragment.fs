#version 450

in vec3 exColour;

out vec4 fragColour;

void main()
{

	fragColour = vec4(exColour, 1.0);

}