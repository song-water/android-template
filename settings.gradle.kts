pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "android-template"

include(":shared")

// android modules configuration
include(":androidApp")
composeSubProject("androidModules", "android_")

fun composeSubProject(subProjectDirName: String, modulePrefix: String) {
    println("composeSubProject $subProjectDirName -->> $$modulePrefix")
    File("./$subProjectDirName")
        .walk()
        .maxDepth(1)
        .filterNot {
            it.name == subProjectDirName
        }
        .filter {
            it.isDirectory
        }
        .forEach { dir ->
            include("$modulePrefix${dir.name}")
            project(":$modulePrefix${dir.name}").projectDir = dir
            println("dir: $modulePrefix${dir.name}")
        }
}