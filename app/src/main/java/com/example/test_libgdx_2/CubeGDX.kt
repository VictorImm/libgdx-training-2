package com.example.test_libgdx_2

class CubeGDX(private val size: Float) {

    private val squares: Array<SquareGDX>

    private val squareCoords = generateCubeVertices(1.0f)

    init {
        squares = Array(6) { i ->
            val sideCoords = generateSideCoords(i)
            SquareGDX(sideCoords)
        }
    }

    private fun generateSideCoords(sideIndex: Int): FloatArray {
        val xOffset = size / 2
        val yOffset = size / 2

        val sideCoords = FloatArray(12) // 4 vertices * 3 coordinates
        for (i in 0..3) {
            val triangleIndex = sideIndex * 2
            val vertexIndex = triangleIndex * 3 + i
            sideCoords[i * 3] = squareCoords[vertexIndex * 3] + xOffset
            sideCoords[i * 3 + 1] = squareCoords[vertexIndex * 3 + 1] + yOffset
            sideCoords[i * 3 + 2] = squareCoords[vertexIndex * 3 + 2]
        }
        return sideCoords
    }

    fun draw(mvpMatrix: FloatArray) {
        for (square in squares) {
            square.draw(mvpMatrix)
        }
    }

    fun generateCubeVertices(size: Float): FloatArray {
        val halfSize = size / 2
        return floatArrayOf(
            -halfSize,  halfSize, -halfSize,  // top left front
            -halfSize, -halfSize, -halfSize,  // bottom left front
            halfSize, -halfSize, -halfSize,  // bottom right front
            halfSize,  halfSize, -halfSize,  // top right front
            -halfSize,  halfSize,  halfSize,  // top left back
            -halfSize, -halfSize,  halfSize,  // bottom left back
            halfSize, -halfSize,  halfSize,  // bottom right back
            halfSize,  halfSize,  halfSize   // top right back
        )
    }
}