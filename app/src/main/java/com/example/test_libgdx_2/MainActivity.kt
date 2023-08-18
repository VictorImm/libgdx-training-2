package com.example.test_libgdx_2

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.graphics.g3d.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {

    private lateinit var myGDX: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        myGDX = MyGLSurfaceView(this)
        setContentView(myGDX)

    }

    override fun onResume() {
        super.onResume()
        myGDX.onResume()
    }

    override fun onPause() {
        super.onPause()
        myGDX.onPause()
    }
}