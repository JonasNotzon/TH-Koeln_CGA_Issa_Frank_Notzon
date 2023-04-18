#version 330 core

//input from vertex shader
in struct VertexData
{
    vec3 color;
} vertexData;

//fragment shader output
out vec4 color;

void main(){
    // TODO This is currently only a color passthrough to the screen. For tasks 2.1.3 and 2.4.2 you have to implement a
    //      meaningful visualization of the surface normals. Think about how to map the coordinates of the normal vectors
    //      to RGB values and how you're going to handle negative coordinates.
    color = vec4(vertexData.color, 1.0f);
}