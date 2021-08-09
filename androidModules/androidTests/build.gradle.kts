plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
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

    api("junit:junit:$junit_version")

    //compileOnly "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlin_coroutines_version")
    api("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    api("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    api("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlin_version")

    api("androidx.arch.core:core-testing:2.1.0")

    api("androidx.test.ext:junit:1.1.2")
    api("androidx.test.espresso:espresso-core:$espresso_version")

}