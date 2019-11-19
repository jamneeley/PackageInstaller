package com.jamesneeley.packageinstaller.view_controller

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.jamesneeley.packageinstaller.R
import com.jamesneeley.packageinstaller.misc.TestCycle
import com.jamesneeley.packageinstaller.misc.showDismissAlertWith
import com.jamesneeley.packageinstaller.model_controller.PackageItemController

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var downloadPackagesButton: Button
    private lateinit var packageResultsTextView: TextView
    private lateinit var packageDataStructureTextView: TextView
    private lateinit var rawDataTextView: TextView
    private lateinit var resultTextView: TextView


    private var lastTestCycle = TestCycle.A

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        packageDataStructureTextView = findViewById(R.id.mainActivityPackageDataStructureTextView)
        packageResultsTextView = findViewById(R.id.mainActivityPackageResultsTextView)
        downloadPackagesButton = findViewById(R.id.mainActivityDownloadPackagesButton)
        rawDataTextView = findViewById(R.id.mainActivityRawDataTextView)
        resultTextView = findViewById(R.id.mainActivityResultTextView)

        downloadPackagesButton.setOnClickListener(this)

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val hasSeenInstruction = sharedPref.getBoolean(getString(R.string.has_seen_instructions), false)

        if (!hasSeenInstruction) {

            with(sharedPref.edit()) {
                putBoolean(
                    getString(R.string.has_seen_instructions),
                    true
                )
                apply()
            }

            showDismissAlertWith("", getString(R.string.tap_button_instructions))
        }
    }

    override fun onClick(view: View) {
        when (view) {
            downloadPackagesButton -> {
                downloadPackage()
                updateTestCycle()
            }
            else -> {}
        }
    }

    private fun downloadPackage() {
        packageDataStructureTextView.text = PackageItemController.getDataSet(lastTestCycle)
        val cycleName = lastTestCycle.name
        val rawDataString = "${getString(R.string.raw_data)} - $cycleName"
        val resultString = "${getString(R.string.download_order)} - $cycleName"
        rawDataTextView.text = rawDataString
        resultTextView.text = resultString


        val order = PackageItemController.getDataSetDownloadOrder(lastTestCycle)

        if (order.isNullOrBlank()) {
            packageResultsTextView.setTextColor(Color.RED)
            val error = "${PackageItemController.getErrorLog().name} Error"
            packageResultsTextView.text = error

        } else {
            packageResultsTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.dark_green, null))
            packageResultsTextView.text = order
        }
    }

    private fun updateTestCycle() {
        lastTestCycle = when (lastTestCycle) {
            TestCycle.A -> TestCycle.B
            TestCycle.B -> TestCycle.C
            TestCycle.C -> TestCycle.D
            TestCycle.D -> TestCycle.E
            TestCycle.E -> TestCycle.F
            TestCycle.F -> TestCycle.G
            TestCycle.G -> TestCycle.A
        }
    }
}
