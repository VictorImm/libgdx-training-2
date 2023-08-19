package com.example.test_libgdx_2

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.graphics.g3d.*
import com.example.test_libgdx_2.databinding.ActivityMainBinding
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

private const val TOUCH_SCALE_FACTOR: Float = 90.0f / 320f

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var myGDX: GLSurfaceView
    private lateinit var renderer: MyGLRenderer

    private lateinit var btnTriangle: Button
    private lateinit var btnCube: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        myGDX = binding.myGlViewer

        myGDX.setEGLContextClientVersion(2)

        btnTriangle = binding.btn1
        btnTriangle.setOnClickListener {
            // change property
            renderer.shape = 0
            renderer.rotationX = 0f
            renderer.rotationY = 0f

            myGDX.requestRender()
            Log.d("MainActivity", "Request 1 sent")
        }
        btnCube = binding.btn2
        btnCube.setOnClickListener {
            // change property
            renderer.shape = 1
            renderer.rotationX = 0f
            renderer.rotationY = 0f

            myGDX.requestRender()
            Log.d("MainActivity", "Request 2 sent")
        }

        renderer = MyGLRenderer()
        myGDX.setRenderer(renderer)

        myGDX.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onResume() {
        super.onResume()
        myGDX.onResume()
    }

    override fun onPause() {
        super.onPause()
        myGDX.onPause()
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
                myGDX.requestRender()
            }
        }

        prevX = x
        prevY = y
        return true
    }
}