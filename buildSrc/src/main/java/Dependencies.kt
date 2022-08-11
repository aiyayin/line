object BuildConfigs {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "31.0.0"

    const val minSdkVersion = 23
    const val targetSdkVersion = 31
}

object Versions {
    const val support_lib = "27.0.2"
    const val retrofit = "2.3.0"
    const val rxjava = "2.1.9"
    const val kotlin_version = "1.5.21"
    const val nav_version = "1.0.0-alpha02"
}

object RootBuilds {
    const val tool_build = "com.android.tools.build:gradle:4.1.3"
}

object Libs {
    const val multidex = "com.android.support:multidex:1.0.3"

    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    const val appcompat = "androidx.appcompat:appcompat:1.4.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.2"
    const val kotlin_stdlib_jdk7 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"
    const val lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.kotlin_version}"
    const val lifecycle_viewmodel = "android.arch.lifecycle:viewmodel:${Versions.kotlin_version}"
    const val lifecycle_livedata = "android.arch.lifecycle:livedata:${Versions.kotlin_version}"
    const val dynamicanimation = "androidx.dynamicanimation:dynamicanimation:1.1.0-alpha03"
    const val navigation_fragment =
        "android.arch.navigation:navigation-fragment:${Versions.nav_version}"
    const val navigation_ui = "android.arch.navigation:navigation-ui:${Versions.nav_version}"
    const val activity = "androidx.activity:activity-ktx:1.4.0"
    const val gson = "com.google.code.gson:gson:2.8.5"

}


object Compose {
    const val version = "1.1.1"
    const val animation = "androidx.compose.animation:animation:$version"
    const val ui = "androidx.compose.ui:ui:$version"
    const val material = "androidx.compose.material:material:$version"
    const val compiler = "androidx.compose.compiler:compiler:$version"
    const val activity = "androidx.activity:activity-compose:1.4.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
    const val ui_tool = "androidx.compose.ui:ui-tooling:$version"
    const val pager = "com.google.accompanist:accompanist-pager:0.24.0-alpha"
//    const val viewmodel_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0"

}


object OpenLibs {
    const val CymChad_Adapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"
    const val html_textview = "org.sufficientlysecure:html-textview:4.0"

    const val glide = "com.github.bumptech.glide:glide:4.12.0"
    const val glide_okhttp3_integration = "com.github.bumptech.glide:okhttp3-integration:4.12.0"

    const val commons_text = "org.apache.commons:commons-text:1.9"
}
