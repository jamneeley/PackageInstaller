package com.jamesneeley.packageinstaller.model_controller

import com.jamesneeley.packageinstaller.misc.PackageError
import com.jamesneeley.packageinstaller.misc.TestCycle
import com.jamesneeley.packageinstaller.model.PackageItem
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


    fun getDataSetDownloadOrder(cycle: TestCycle): String? {

        val downloadedData =  when (cycle) {
            TestCycle.A -> dataSetA
            TestCycle.B -> dataSetB
            TestCycle.C -> dataSetC
            TestCycle.D -> dataSetD
            TestCycle.E -> dataSetE
        }

        val errorCheck = checkForDataErrors(downloadedData)
        if (errorCheck.isNullOrEmpty()) {
            return null
        }

        val cycleCheck =
            combinePackages(errorCheck.toSet()) //casting to set gets rid of exact duplicates which are ok
        if (cycleCheck.isNullOrEmpty()) {
            return null
        }

        val stringBuilder = StringBuilder()

        for (i in 0 until cycleCheck.count()) {

            if (i == cycleCheck.count() - 1) {
                stringBuilder.append(extractStringFromPackageChain(cycleCheck.elementAt(i), arrayListOf()))
            } else {
                stringBuilder.append("${extractStringFromPackageChain(cycleCheck.elementAt(i), arrayListOf())}, ")
            }
        }

        return stringBuilder.toString()
    }


    private fun checkForDataErrors(dataArray: ArrayList<String>): ArrayList<PackageItem>? {

        val packageArray = arrayListOf<PackageItem>()
        dataArray.forEach { item ->

            val packageName: String
            var dependencyName: String? = null


            if (!item.contains(SEPARATOR)) {
                errorLog = PackageError.MISSING_SEPARATOR
                return null
            } else {


                if (!doesItemContainOneSeparator(item)) {
                    errorLog = PackageError.MULTIPLE_SEPARATORS
                    return null
                }

                val index = item.indexOf(SEPARATOR)

                if (index == 0) {
                    errorLog = PackageError.PACKAGE_NAME
                    return null
                }

                packageName = item.substring(0, index)

                val separatorCount = SEPARATOR.count()

                if (index != item.count() - separatorCount) {
                    //there is a dependency
                    dependencyName = item.substring(index + separatorCount)
                }
            }


            val dependency = if (dependencyName != null) PackageItem(dependencyName, null) else null
            packageArray.add(PackageItem(packageName, dependency))

        }


        return if (packageArray.isNotEmpty()) packageArray else null
    }


    private fun doesItemContainOneSeparator(item: String): Boolean {

        val regex = Regex(SEPARATOR)

        val matches = regex.findAll(item)

        val count = matches.count()
        if (count >  1) {
            return false
        }

        return true
    }


    private fun combinePackages(dataSet: Set<PackageItem>): Set<PackageItem>? {
        val mutableSet = dataSet

        val count = mutableSet.count()

        for (i in 0 until count) {
            for (j in 0 until count) {

                if (i == j) {
                    continue
                }

                val packageItem = mutableSet.elementAt(i)
                val comparingItem = mutableSet.elementAt(j)

                if (packageItem.name == comparingItem.name) {
                    /**
                     * we already checked for exact duplicates, this means that there are two packages with different dependencies
                     * packages aren't supposed to have multiple dependencies, at least in the scope of this project.
                     */

                    errorLog = PackageError.DUPLICATION
                    return null
                }


                if (packageItem.dependency != null) {

                    val endPackage = getEndOfDependencyChain(packageItem)

                    if (endPackage.name == comparingItem.name) {

                        if (comparingItem.dependency != null) {
                            if (doesPackageHaveCycle(packageItem, comparingItem.dependency!!.name)) {
                                errorLog = PackageError.CYCLE
                                return null
                            }
                        }

                        endPackage.dependency = comparingItem.dependency

                        return combinePackages(mutableSet.minus(comparingItem))
                    }
                } else {
                    break
                }
            }
        }
        return mutableSet
    }


    fun getEndOfDependencyChain(item: PackageItem): PackageItem {
        return if (item.dependency == null) {
            item
        } else {
            getEndOfDependencyChain(item.dependency!!)
        }
    }


    fun doesPackageHaveCycle(packageItem: PackageItem, checkItemName: String): Boolean {
        val dependency = packageItem.dependency

        return when {
            (packageItem.name == checkItemName) -> true
            (dependency == null) -> false
            else -> doesPackageHaveCycle(dependency, checkItemName)

        }
    }

    fun extractStringFromPackageChain(item: PackageItem, chain: ArrayList<String>): String {
        val dependency = item.dependency
        chain.add(0, item.name)

        return if (dependency == null) {
            chain.joinToString()
        } else {
            extractStringFromPackageChain(dependency, chain)
        }
    }
}