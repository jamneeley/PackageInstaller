package com.jamesneeley.packageinstaller.model

data class PackageItem(val name: String, var dependency: PackageItem?)