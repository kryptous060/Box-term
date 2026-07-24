plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.termux"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}
