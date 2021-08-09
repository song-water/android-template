pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "android-template"
include(":androidModules:common")
include(":androidApp")
include(":shared")
include(":androidModules:unitTests")
include(":androidModules:androidTests")
