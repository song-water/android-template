// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        //val kotlin_version: String by extra
        classpath("com.android.tools.build:gradle:$android_gradle_plugin_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        // classpath("com.squareup.sqldelight:gradle-plugin:$1.5.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

task<Delete>(name = "clean") {
    group = "build"
    delete(rootProject.buildDir)
}