plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
}

android {
    compileSdk = compile_version
    buildToolsVersion =build_tools_version

    defaultConfig {
        minSdk = min_sdk_version
        targetSdk = target_sdk_version
        // versionCode = 1
        // versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lint {
        isCheckReleaseBuilds = false
        // Or, if you prefer, you can continue to check for errors in release builds,
        // but continue the build even when errors are found:
        isAbortOnError = false
    }
}

dependencies {
    //api fileTree(dir: "libs", include: ["*.jar"])
    api(project(":shared"))

    api("androidx.core:core-ktx:1.6.0")
    api("androidx.appcompat:appcompat:1.3.1")
    api("androidx.activity:activity:1.3.0")
    api("androidx.activity:activity-ktx:1.3.0")
    api("androidx.lifecycle:lifecycle-process:2.3.1")

    // api("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    // api("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    // api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")
    api("androidx.constraintlayout:constraintlayout:$constraint_layout_version")

    api("io.ktor:ktor-client-android:$ktor_version")
    api("io.ktor:ktor-client-okhttp:$ktor_version")
    api("io.ktor:ktor-client-json-jvm:$ktor_version")
    api("io.ktor:ktor-client-logging-jvm:$ktor_version")
    api("io.ktor:ktor-client-serialization:$ktor_version")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    testImplementation(project(":androidModules:unitTests"))
    androidTestImplementation(project(":androidModules:androidTests"))
}