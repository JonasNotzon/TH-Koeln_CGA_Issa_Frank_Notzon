package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import cga.framework.OBJLoader.loadOBJ
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL30.*


class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram =
        ShaderProgram("assets/shaders/tron_vert.glsl", "assets/shaders/tron_frag.glsl")
    private val sphereMesh: Mesh
    private val groundMesh: Mesh

    private val sphereMatrix = Matrix4f()
    private val groundMatrix = Matrix4f()

    init {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); GLError.checkThrow()

        enableDepthTest(GL_LESS)
        enableFaceCulling(GL_CCW, GL_BACK)

        val pos = VertexAttribute(3, GL_FLOAT, 8 * 4, 0)
        val tex = VertexAttribute(2, GL_FLOAT, 8 * 4, 3 * 4)
        val col = VertexAttribute(3, GL_FLOAT, 8 * 4, 5 * 4)
        val vertexAttributes = arrayOf<VertexAttribute>(pos, tex, col)

//      Aufg. 2.1.1
        val sphereMeshList = loadOBJ("assets/models/sphere.obj").objects[0].meshes[0]
        val sphereVertices = sphereMeshList.vertexData
        val sphereIndices = sphereMeshList.indexData
        sphereMesh = Mesh(sphereVertices, sphereIndices, vertexAttributes)
        sphereMatrix
            .scale(0.5f)

        val groundMeshList = loadOBJ("assets/models/ground.obj").objects[0].meshes[0]
        val groundVertices = groundMeshList.vertexData
        val groundIndices = groundMeshList.indexData
        groundMesh = Mesh(groundVertices, groundIndices, vertexAttributes)
        groundMatrix
            .rotation(90f, Vector3f(1f, 0f, 0f))
            .scale(0.03f)
    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        staticShader.use()
        staticShader.setUniform("model_matrix", sphereMatrix)
        sphereMesh.render()
        staticShader.setUniform("model_matrix", groundMatrix)
        groundMesh.render()
    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}

    fun cleanup() {
        sphereMesh.cleanup()
        groundMesh.cleanup()
    }

    /**
     * enables culling of specified faces
     * orientation: ordering of the vertices to define the front face
     * faceToCull: specifies the face, that will be culled (back, front)
     * Please read the docs for accepted parameters
     */
    fun enableFaceCulling(orientation: Int, faceToCull: Int) {
        glEnable(GL_CULL_FACE); GLError.checkThrow()
        glFrontFace(orientation); GLError.checkThrow()
        glCullFace(faceToCull); GLError.checkThrow()
    }

    /**
     * enables depth test
     * comparisonSpecs: specifies the comparison that takes place during the depth buffer test
     * Please read the docs for accepted parameters
     */
    fun enableDepthTest(comparisonSpecs: Int) {
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(comparisonSpecs); GLError.checkThrow()
    }
}