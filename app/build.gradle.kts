plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.detekt)
}

android {
    namespace = "net.pedromalta.mirrorclock"
    compileSdk = 37

    defaultConfig {
        applicationId = "net.pedromalta.mirrorclock"
        minSdk = 19
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = false

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    buildFeatures {
        compose = false
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("androidx.test:runner:1.6.2")
    testImplementation("androidx.test.ext:junit-ktx:1.2.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

detekt {
    config.setFrom("$rootDir/detekt-config.yml")
    baseline = file("$rootDir/detekt-baseline.xml")
}
