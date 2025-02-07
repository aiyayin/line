pluginManagement {
    repositories {
        maven() {
            setUrl("https://maven.douyu.tv/artifactory/douyu_dev_opt")
        }
        maven() {
            setUrl("https://maven.aliyun.com/repository/jcenter")
        }
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.google.com/") }
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven() {
            setUrl("https://maven.douyu.tv/artifactory/douyu_dev_opt")
        }
        maven() {
            setUrl("https://maven.aliyun.com/repository/jcenter")
        }
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.google.com/") }
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "line"
include(":base")
include(":demo")
include(":app")
include(":javalib")
