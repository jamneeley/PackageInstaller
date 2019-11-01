package com.jamesneeley.packageinstaller.model_controller

import com.jamesneeley.packageinstaller.misc.PackageError
import com.jamesneeley.packageinstaller.misc.TestCycle
import java.lang.StringBuilder

object PackageItemController {

    /**

    Flow

    1. display raw Data in textViewA
    2. display formatted Data in green if PASS in textViewB
    3. display error in red if FAIL in textViewB

     */

    /**
    Errors to check for

    Make sure there is only one example of “: “
    Make sure there are characters before “: “
    If no character after “: “ means there is no dependency

    Check for duplicates if there is an exact duplicate delete it and move forward
    Make sure if there is a duplicate it has the same dependencies otherwise fail
    Make sure there are no cycles

     */





    /**
     * PASS
     *
     * Correct Order:
     *
     * KittenService, CamelCaser, Ice, Cyberportal, Leetmeme, Fraudstream
     *          or
     * Ice, Cyberportal, Leetmeme, Fraudstream, KittenService, CamelCaser,
     */
    private val dataSetA = arrayListOf(
        "KittenService: ",
        "Leetmeme: Cyberportal",
        "Cyberportal: Ice",
        "CamelCaser: KittenService",
        "Fraudstream: Leetmeme",
        "Ice: "
    )


    /**
     * FAIL - Multiple Separators Error
     */

    private val dataSetB = arrayListOf(
        "KittenService: ",
        "Le: etmeme: Cyberportal",
        "Cyberportal: Ice",
        "CamelCaser: KittenS: ervice",
        "Fraud: stream: Leetmeme",
        "Ice: "
    )

    /**
     * FAIL - Cycle Error
     */
    private val dataSetC = arrayListOf(
        "KittenService: ",
        "Leetmeme: Cyberportal",
        "Cyberportal: Ice",
        "CamelCaser: KittenService",
        "Fraudstream: ",
        "Ice: Leetmeme"
    )

    /**
     * PASS - Ok Duplicate
     *
     * Correct Order:
     *
     * KittenService, CamelCaser, Ice, Cyberportal, Leetmeme, Fraudstream
     *          or
     * Ice, Cyberportal, Leetmeme, Fraudstream, KittenService, CamelCaser,
     */

    private val dataSetD = arrayListOf(
        "KittenService: ",
        "Leetmeme: Cyberportal",
        "Leetmeme: Cyberportal",
        "Cyberportal: Ice",
        "CamelCaser: KittenService",
        "Fraudstream: Leetmeme",
        "Ice: "
    )

    /**
     * FAIL - Malicious Duplicate Error (at least in the scope of this example)
     */

    private val dataSetE = arrayListOf(
        "KittenService: ",
        "Leetmeme: Cyberportal",
        "Cyberportal: Ice",
        "CamelCaser: KittenService",
        "Fraudstream: Leetmeme",
        "Fraudstream: Ice",
        "Ice: "
    )

    private const val SEPARATOR = ": "
    private var errorLog = PackageError.NONE

    fun getErrorLog(): PackageError {
        return errorLog
    }

    fun getDataSet(cycle: TestCycle): String {

        val packageArray = when (cycle) {
            TestCycle.A -> dataSetA
            TestCycle.B -> dataSetB
            TestCycle.C -> dataSetC
            TestCycle.D -> dataSetD
            TestCycle.E -> dataSetE
        }


        val stringBuilder = StringBuilder()

        packageArray.forEach {
            stringBuilder.append("\"$it\"\n")
        }

        return stringBuilder.toString()
    }
}