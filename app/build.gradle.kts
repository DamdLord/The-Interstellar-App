plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.theinterstellaatlas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.theinterstellaatlas"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation for Jetpack Compose
    implementation(libs.androidx.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)

    //coil for async Image
    implementation(libs.coil.compose)

    // json
    //implementation(libs.converter.gson)

    // Lifecycle ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    // OkHttp (for toMediaType)
    implementation(libs.okhttp)

    // OkHttp logging interceptor
    implementation(libs.logging.interceptor)

    // Kotlinx Serialization JSON
    implementation(libs.kotlinx.serialization.json.v163)

    // Retrofit converter for kotlinx.serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter.v080)


    // Lifecycle ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    //Icons
    implementation(libs.compose.material.icons)
    implementation(libs.androidx.ui.text.google.fonts)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}