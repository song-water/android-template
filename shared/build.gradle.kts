import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")
    id("com.android.library")
    // id("com.squareup.sqldelight")
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.8")
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.13.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.ktor:ktor-client-mock:$ktor_version")
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                // implementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlin_coroutines_version")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")
                implementation("io.ktor:ktor-client-android:$ktor_version")
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
                implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
                implementation("com.tencent:mmkv-static:1.2.10")
                // implementation("com.squareup.sqldelight:android-driver:$sql_delight_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:$junit_version")
                implementation("io.ktor:ktor-client-mock-jvm:$ktor_version")
                // implementation("com.squareup.sqldelight:sqlite-driver:$sql_delight_version")
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }
}