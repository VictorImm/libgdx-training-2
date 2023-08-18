package com.example.test_libgdx_2

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)

        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    private var prevX: Float = 0f
    private var prevY: Float = 0f

    override fun onTouchEvent(e: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        val x: Float = e.x
        val y: Float = e.y

        when (e.action) {
            MotionEvent.ACTION_MOVE -> {

                var dx: Float = x - prevX
                var dy: Float = y - prevY

                // Calculate rotation angles based on touch movement
                val rotationX: Float = dy * TOUCH_SCALE_FACTOR
                val rotationY: Float = dx * TOUCH_SCALE_FACTOR

                // Apply rotations to the renderer's transformation matrix
                renderer.rotateObject(rotationX, rotationY)
                requestRender()
            }
        }

        prevX = x
        prevY = y
        return true
    }
}