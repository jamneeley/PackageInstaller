package com.jamesneeley.packageinstaller.model

data class PackageItem(val name: String, val dependency: PackageItem?)