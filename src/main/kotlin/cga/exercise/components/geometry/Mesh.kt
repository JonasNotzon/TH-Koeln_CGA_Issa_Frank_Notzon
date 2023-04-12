package cga.exercise.components.geometry

import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*

/**
 * Creates a Mesh object from vertexdata, intexdata and a given set of vertex attributes
 *
 * @param vertexData plain float array of vertex data
 * @param indexData  index data
 * @param attributes vertex attributes contained in vertex data
 * @throws Exception If the creation of the required OpenGL objects fails, an exception is thrown
 *
 * Created 29.03.2023.
 */
class Mesh(vertexData: FloatArray, indexData: IntArray, attributes: Array<VertexAttribute>) {
    private var vaoId = 0
    private var vboId = 0
    private var iboId = 0
    private var indexCount = indexData.count()

    init {
        vaoId = glGenVertexArrays()
        vboId = glGenBuffers()
        iboId = glGenBuffers()

        glBindVertexArray(vaoId)

        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        GL15.glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW)

        for ((index, attribute) in attributes.withIndex()) {
            glEnableVertexAttribArray(index)
            GL20.glVertexAttribPointer(
                index, attribute.n, attribute.type, false, attribute.stride, attribute.offset
            )
        }

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId)
        GL15.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW)

        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    /**
     * Renders the mesh
     */
    fun render() {
        glBindVertexArray(vaoId)
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }

    /**
     * Deletes the previously allocated OpenGL objects for this mesh
     */
    fun cleanup() {
        if (iboId != 0) glDeleteBuffers(iboId)
        if (vboId != 0) glDeleteBuffers(vboId)
        if (vaoId != 0) glDeleteVertexArrays(vaoId)
    }
}