plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.termux"
    compileSdk = 36

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "TERMUX_PACKAGE_VARIANT", "\"debug\"")
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.9.0")
    implementation("androidx.core:core:1.13.1")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.guava:guava:24.1-jre")

    implementation(project(":termux-app:terminal-view"))
    implementation(project(":termux-app:termux-shared"))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
