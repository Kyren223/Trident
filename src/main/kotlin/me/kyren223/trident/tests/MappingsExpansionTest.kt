package me.kyren223.trident.tests

import org.junit.Test

class MappingsExpansionTest {
    @Test
    fun testExpand() {
        val mappings = mapOf(
            "\$A" to "A",
            "\$BB" to "B",
            "\$CCC" to "C",
            "\$DDD" to "D",
            "\$EEE" to "E",
            "\$FFFFF" to "F",
            "\$GG" to "G",
            "\$UUU" to "U",
            "\$VVVV" to "V",
            "\$LLLLLL" to "L",
            "\$MM" to "M",
            "\$NN" to "N",
            "\$OOO" to "O",
            "\$PPP" to "P",
            "\$QQQQ" to "Q",
            "\$R" to "R",
            "\$WWW" to "W",
            "\$XX" to "X",
            "\$HI" to "H",
            "\$IJ" to "I",
            "\$JetBrains" to "J",
            "\$Krypton" to "K",
            "\$Soft" to "S",
            "\$Tnt" to "T",
            "\$Ycombinator" to "Y",
            "\$Zig" to "Z",
        ).toList().sortedByDescending { it.first.length }

        mappings.forEach { (key, value) ->
            println("$key -> $value")
        }

    }
}