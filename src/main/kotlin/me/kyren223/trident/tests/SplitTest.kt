package me.kyren223.trident.tests

import org.junit.Test

class SplitTest {
    
    @Test
    fun testSplit() {
        val filenameAndExtension = "SplitTest.kt"
        val filename = "SplitTest"
        val dotfile = ".ideavimrc"
        
        val split = filenameAndExtension.split(".")
        assert(split.size == 2)
        assert(split[0] == filename)
        assert(split[1] == "kt")
        
        val split2 = filename.split(".")
        assert(split2.size == 1)
        assert(split2[0] == filename)
        
        val split3 = dotfile.split(".")
        assert(split3.size == 2)
        assert(split3[0] == "")
        assert(split3[1] == "ideavimrc")
        
        
    }
    
    @Test
    fun testSplit2() {
        val path = "C:/Users/Owner/Projects/Trident/src/main/kotlin/me/kyren223/trident/tests/SplitTest.kt"
        testPath(path, "SplitTest", "kt")
        testPath("SplitTest.kt", "SplitTest", "kt")
        testPath("SplitTest", "SplitTest", null)
        testPath(".ideavimrc", null, "ideavimrc")
        testPath("build.gradle.kts", "build.gradle", "kts")
        
    }
    
    private fun testPath(path: String, filename: String?, extension: String?) {
        
        val fullFilename = path.split("/").last()
        println("fullFilename: $fullFilename")
        val lastDot = fullFilename.lastIndexOf(".")
        val noExtension = lastDot == -1
        
        val filename2 = if (noExtension) fullFilename else {
            fullFilename.substring(0, lastDot).ifEmpty { null }
        }
        val extension2 = if (noExtension) null else fullFilename.substring(lastDot + 1)
        
        println("filename: $filename")
        println("extension: $extension")
        println("filename2: $filename2")
        println("extension2: $extension2")
        
        assert(filename2 == filename)
        assert(extension2 == extension)
    }
}