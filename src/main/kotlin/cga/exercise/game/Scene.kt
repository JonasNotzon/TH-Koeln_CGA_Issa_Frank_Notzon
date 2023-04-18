package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import org.lwjgl.opengl.GL30.*

/**
 * Created 29.03.2023.
 */
class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram =
        ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")
    private val mesh: Mesh

    init {
        glClearColor(0.0f, 0.533f, 1.0f, 1.0f); GLError.checkThrow()

        enableDepthTest(GL_LESS)
        enableFaceCulling(GL_CCW, GL_BACK)

/*      //Aufg 1.2.3: Gerendertes Haus
        val vertices = floatArrayOf(
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f
        )

        val indices = intArrayOf(
            0, 1, 2,
            0, 2, 4,
            4, 2, 3
        )*/


        //Aufg 1.2.5: Initialen
        val vertices = floatArrayOf(
            //I
            0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.5f, 1.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.25f, 1.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.25f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            //L
            0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.0f, 0.25f, 0.0f, 0.0f, 0.5f, 0.0f,
            -0.5f, 0.25f, 0.0f, 0.0f, 0.5f, 0.0f,
            -0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            -0.5f, 1.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            -0.75f, 1.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            -0.75f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )
        val indices = intArrayOf(
            //I
            0,1,2,
            0,2,3,
            //L
            3,4,2,
            4,5,3,
            5,7,4,
            5,6,7,
            8,10,7,
            10,8,9
        )

        val pos = VertexAttribute(3, GL_FLOAT, 24, 0)
        val col = VertexAttribute(3, GL_FLOAT, 24, 12)
        val vertexAttributes = arrayOf<VertexAttribute>(pos, col)

        mesh = Mesh(vertices, indices, vertexAttributes)
    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        staticShader.use()
        mesh.render()
    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}

    fun cleanup() {
        mesh.cleanup()
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