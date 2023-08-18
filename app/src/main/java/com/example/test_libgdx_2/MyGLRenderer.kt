package com.example.test_libgdx_2

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log

class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var mSquare: SquareGDX
    private var mCube: ArrayList<SquareGDX> = ArrayList(4)

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    // rotation
    private val rotationMatrix = FloatArray(16)
    private var rotationX: Float = 0f
    private var rotationY: Float = 0f

    fun rotateObject(angleX: Float, angleY: Float) {
        rotationX += angleX
        rotationY += angleY
    }

    // initiator
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        // TODO: Change if needed
        // initialize object
        mCube.add( // front
            SquareGDX(floatArrayOf(
                -0.5f,  0.5f, 0.5f,      // top left
                -0.5f, -0.5f, 0.5f,      // bottom left
                0.5f, -0.5f, 0.5f,       // bottom right
                0.5f,  0.5f, 0.5f        // top right
            ))
        )

        mCube.add( // left
            SquareGDX(floatArrayOf(
                -0.5f,  0.5f, -0.5f,      // top left
                -0.5f, -0.5f, -0.5f,      // bottom left
                -0.5f, -0.5f, 0.5f,       // bottom right
                -0.5f, 0.5f, 0.5f         // top right
            ))
        )

        mCube.add( // back
            SquareGDX(floatArrayOf(
                -0.5f,  0.5f, -0.5f,      // top left
                -0.5f, -0.5f, -0.5f,      // bottom left
                0.5f, -0.5f, -0.5f,       // bottom right
                0.5f,  0.5f, -0.5f        // top right
            ))
        )

        mCube.add( // right
            SquareGDX(floatArrayOf(
                0.5f,  0.5f, 0.5f,      // top left
                0.5f, -0.5f, 0.5f,      // bottom left
                0.5f, -0.5f, -0.5f,     // bottom right
                0.5f, 0.5f, -0.5f       // top right
            ))
        )
    }

    // every redraw
    override fun onDrawFrame(unused: GL10) {
        val scratch = FloatArray(16)
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // set camera pos
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -5f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        // Calculate the total rotation transformation
        Matrix.setIdentityM(rotationMatrix, 0)
        Matrix.rotateM(rotationMatrix, 0, rotationX, 1f, 0f, 0f) // Rotate around X axis
        Matrix.rotateM(rotationMatrix, 0, rotationY, 0f, 1f, 0f) // Rotate around Y axis

        // calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

        Log.d("Triangle", "draw")
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        // TODO: Change if needed
        for (square in mCube) {
            square.draw(scratch)
        }
    }

    // every changed orientation
    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }
}