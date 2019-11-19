package com.jamesneeley.packageinstaller.misc

enum class TestCycle {
    A,
    B,
    C,
    D,
    E,
    F,
    G
}

enum class PackageError {
    NONE,
    CYCLE,
    DUPLICATION,
    PACKAGE_NAME,
    MISSING_SEPARATOR,
    MULTIPLE_SEPARATORS
}