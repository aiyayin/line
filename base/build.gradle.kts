

plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.yin.line.base"

    compileSdk = 34

    defaultConfig {
        minSdk = 30
        consumerProguardFiles("consumer-rules.pro")

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    buildFeatures {
        compose = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    api(libs.multidex)

    api(libs.appcompat)
    api(libs.material)
    api(libs.constraintlayout)
    api(libs.recyclerview)
    api(libs.kotlin.stdlib)
    api(libs.lifecycle.viewmodel)
    api(libs.lifecycle.livedata)
    api(libs.activity.ktx)

    api(libs.cymchad.adapter)
    api(libs.codelocator.core)

    api(libs.androidx.activity.compose)

    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)


    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.tooling)
    api(libs.compose.foundation) // 版本由 BOM 自动管理
    api(libs.compose.material) // 使用 Material 3 的 Text
    api(libs.compose.constraintlayout)
    api(libs.androidx.compose.runtime) // 不需要手动指定版本
    api(libs.androidx.compose.compiler)
}