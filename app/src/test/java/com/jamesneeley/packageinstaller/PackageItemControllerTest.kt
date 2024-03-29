package com.jamesneeley.packageinstaller

import com.jamesneeley.packageinstaller.misc.TestCycle
import com.jamesneeley.packageinstaller.model.PackageItem
import com.jamesneeley.packageinstaller.model_controller.PackageItemController
import org.junit.Test

import org.junit.Assert.*


class PackageItemControllerTest {

    private val returnA =
        "\"KittenService: \"\n\"Leetmeme: Cyberportal\"\n\"Cyberportal: Ice\"\n\"CamelCaser: KittenService\"\n\"Fraudstream: Leetmeme\"\n\"Ice: \"\n"
    private val returnB =
        "\"KittenService: \"\n\"Le: etmeme: Cyberportal\"\n\"Cyberportal: Ice\"\n\"CamelCaser: KittenS: ervice\"\n\"Fraud: stream: Leetmeme\"\n\"Ice: \"\n"
    private val returnC =
        "\"KittenService: \"\n\"Leetmeme: Cyberportal\"\n\"Cyberportal: Ice\"\n\"CamelCaser: KittenService\"\n\"Fraudstream: \"\n\"Ice: Leetmeme\"\n"
    private val returnD =
        "\"KittenService: \"\n\"Leetmeme: Cyberportal\"\n\"Leetmeme: Cyberportal\"\n\"Cyberportal: Ice\"\n\"CamelCaser: KittenService\"\n\"Fraudstream: Leetmeme\"\n\"Ice: \"\n"
    private val returnE =
        "\"KittenService: \"\n\"Leetmeme: Cyberportal\"\n\"Cyberportal: Ice\"\n\"CamelCaser: KittenService\"\n\"Fraudstream: Leetmeme\"\n\"Fraudstream: Ice\"\n\"Ice: \"\n"
    private val returnF =
        "\"A: C\"\n\"B: C\"\n\"C: \"\n"
    private val returnG =
        "\"E: A\"\n\"F: B\"\n\"B: C\"\n\"C: D\"\n\"G: D\"\n\"A: G\"\n\"D: \"\n"

    private val returnOrderA = "KittenService, CamelCaser, Ice, Cyberportal, Leetmeme, Fraudstream"
    private val returnOrderB = null
    private val returnOrderC = null
    private val returnOrderD = "KittenService, CamelCaser, Ice, Cyberportal, Leetmeme, Fraudstream"
    private val returnOrderE = null
    private val returnOrderF = "C, A, B"
    private val returnOrderG = "D, G, A, E, C, B, F"

    private val packageChain =
        PackageItem(
            "nameSix",
            PackageItem(
                "nameFive",
                PackageItem(
                    "nameFour",
                    PackageItem(
                        "nameThree",
                        PackageItem(
                            "nameTwo",
                            PackageItem("nameOne", null)
                        )
                    )
                )
            )
        )

    private val packageChainString = mutableListOf("nameOne", "nameTwo", "nameThree", "nameFour", "nameFive", "nameSix")

    @Test
    fun get_data_set_order_test() {
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.A), returnOrderA)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.B), returnOrderB)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.C), returnOrderC)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.D), returnOrderD)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.E), returnOrderE)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.F), returnOrderF)
        assertEquals(PackageItemController.getDataSetDownloadOrder(TestCycle.G), returnOrderG)
    }

    @Test
    fun get_data_set_string() {
        assertEquals(PackageItemController.getDataSetString(TestCycle.A), returnA)
        assertEquals(PackageItemController.getDataSetString(TestCycle.B), returnB)
        assertEquals(PackageItemController.getDataSetString(TestCycle.C), returnC)
        assertEquals(PackageItemController.getDataSetString(TestCycle.D), returnD)
        assertEquals(PackageItemController.getDataSetString(TestCycle.E), returnE)
        assertEquals(PackageItemController.getDataSetString(TestCycle.F), returnF)
        assertEquals(PackageItemController.getDataSetString(TestCycle.G), returnG)
    }

    @Test
    fun end_of_dependency_chain() {
        assertEquals(
            PackageItemController.getEndOfDependencyChain(packageChain),
            PackageItem("nameOne", null)
        )
    }

    @Test
    fun extract_string_from_package() {
        assertEquals(
            PackageItemController.extractStringFromPackageItem(packageChain, arrayListOf()),
            packageChainString
        )
    }

    @Test
    fun test_for_cycle_in_set() {
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "nameOne"), true)
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "nameTwo"), true)
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "nameThree"), true)
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "nameFour"), true)
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "nameFive"), true)
        assertEquals(PackageItemController.doesPackageHaveCycle(packageChain, "Garbage"), false)
    }
}