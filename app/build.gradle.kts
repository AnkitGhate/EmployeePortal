/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.ankitgh.employeeportal"
        minSdkVersion(28)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation(Kotlin.KOTLIN_STDLIB)

    // Coroutine
    implementation(Coroutines.COROUTINES_ANDROID)
    implementation(Coroutines.COROUTINES_CORE)

    //Material Design
    implementation(Google.MATERIAL)

    // Androidx
    implementation(Androidx.CORE_KTX)
    implementation(Androidx.APPCOMPAT)
    implementation(Androidx.CONSTRAINTLAYOUT)
    implementation(Androidx.LEGACY_SUPPORT_V4)
    implementation(Androidx.NAVIGATION_FRAGMENT_KTX)
    implementation(Androidx.NAVIGATION_UI_KTX)
    implementation(Androidx.BIOMETRIC)

    // LifeCycle
    implementation(Lifecycle.LIFECYCLE_EXTENSIONS)
    implementation(Lifecycle.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Lifecycle.LIFECYCLE_LIVE_DATA_KTX)

    // Firebase
    implementation(Firebase.FIREBASE_STORAGE)
    implementation(Firebase.FIREBASE_FIRESTORE)
    implementation(Firebase.FIREBASE_AUTH)

    // Dagger-Hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_COMPILER)
    implementation(Hilt.HILT_LIFECYCLE_VIEWMODEL)
    kapt(Hilt.HILT_ANDROID_COMPILER)

    // Testing---begin---
    testImplementation(Testing.JUNIT)
    testImplementation(Coroutines.TEST_KOTLINX_COROUTINES_TEST)
    androidTestImplementation(Testing.TEST_CORE)
    androidTestImplementation(Testing.TEST_RUNNER)
    androidTestImplementation(Testing.TEST_RULE)
    androidTestImplementation(Testing.TEST_EXT_JUNIT)
    androidTestImplementation(Testing.TEST_EXT_TRUTH)
    androidTestImplementation(Testing.TEST_GOOGLE_TRUTH)
    androidTestImplementation(Coroutines.TEST_KOTLINX_COROUTINES_TEST)
    testImplementation(Androidx.ARCH_TESTING)

    // Espresso dependencies
    androidTestImplementation(Testing.Espresso.ESPRESSO_CORE)
    androidTestImplementation(Testing.Espresso.ESPRESSO_CONTRIB)
    androidTestImplementation(Testing.Espresso.ESPRESSO_INTENTS)
    androidTestImplementation(Testing.Espresso.ESPRESSO_ACCESSIBILITY)
    androidTestImplementation(Testing.Espresso.IDLING_CONCURRENT)
    androidTestImplementation(Testing.Espresso.ESPRESSO_IDLING_RESOURCE)

    // Mockito
    testImplementation(Testing.Mockito.MOCKITO)
    // Testing---end---

    // Bottom Navigation
    implementation(Libs.CHIP_NAVIGATION)

    // Retrofit
    implementation(Retrofit.RETROFIT2)
    implementation(Retrofit.CONVERTER_GSON)

    // OkHttp
    implementation(OkHttp.OKHTTP)
    implementation(OkHttp.LOGGING_INTERCEPTOR)

    // Glide
    implementation(Libs.BUMPTECH_GLIDE)
    annotationProcessor(Libs.BUMPTECH_GLIDE_COMPILER)

    // Facebook Shimmer Effect
    implementation(Libs.FACEBOOK_SHIMMER)

    // Animation Lib
    implementation(Libs.AIRBNB_LOTTIE)

    // Circular ImageView
    implementation(Libs.HDODENHOF_CIRCLEIMAGEVIEW)

    // Runtime Permission
    implementation(Libs.DEVREL_EASYPERMISSIONS)

    // Charts
    implementation(Libs.DIOGOBERNARDINO_WILLIAMCHART)

    // CalenderView
    implementation(Libs.APPLANDEO_MATERIAL_CALENDAR_VIEW)

    // Logging
    implementation(Libs.TIMBER)
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}
