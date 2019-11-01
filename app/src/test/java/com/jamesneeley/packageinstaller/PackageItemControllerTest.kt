package com.jamesneeley.packageinstaller

import com.jamesneeley.packageinstaller.misc.TestCycle
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

    @Test
    fun get_data_set() {
        assertEquals(PackageItemController.getDataSet(TestCycle.A), returnA)
        assertEquals(PackageItemController.getDataSet(TestCycle.B), returnB)
        assertEquals(PackageItemController.getDataSet(TestCycle.C), returnC)
        assertEquals(PackageItemController.getDataSet(TestCycle.D), returnD)
        assertEquals(PackageItemController.getDataSet(TestCycle.E), returnE)
    }
}
