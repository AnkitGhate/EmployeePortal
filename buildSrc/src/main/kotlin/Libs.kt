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

object Testing {
    const val JUNIT = "junit:junit:4.13"
    const val TEST_CORE = "androidx.test:core:1.2.0"

    const val TEST_RUNNER = "androidx.test:runner:1.2.0"
    const val TEST_RULE = "androidx.test:rules:1.2.0"
    const val TEST_EXT_JUNIT = "androidx.test.ext:junit:1.1.1"
    const val TEST_EXT_JUNIT_KTX = "androidx.test.ext:junit-ktx:1.1.0"
    const val TEST_EXT_TRUTH = "androidx.test.ext:truth:1.2.0"
    const val TEST_GOOGLE_TRUTH = "com.google.truth:truth:1.0.1"
    const val TEST_FRAGMENT = "androidx.fragment:fragment-testing:1.2.5"

    object Espresso {
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.2.0"
        const val ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:3.2.0"
        const val ESPRESSO_INTENTS = "androidx.test.espresso:espresso-intents:3.2.0"
        const val ESPRESSO_ACCESSIBILITY = "androidx.test.espresso:espresso-accessibility:3.2.0"
        const val IDLING_CONCURRENT = "androidx.test.espresso.idling:idling-concurrent:3.2.0"
        const val ESPRESSO_IDLING_RESOURCE = "androidx.test.espresso:espresso-idling-resource:3.2.0"
    }

    object Mockito {
        const val MOCKITO = "org.mockito:mockito-core:3.3.3"
    }
}

object Libs {
    const val APPLANDEO_MATERIAL_CALENDAR_VIEW = "com.applandeo:material-calendar-view:1.8.0-rc01"
    const val DIOGOBERNARDINO_WILLIAMCHART = "com.diogobernardino:williamchart:3.7.1"
    const val DEVREL_EASYPERMISSIONS = "pub.devrel:easypermissions:3.0.0"
    const val HDODENHOF_CIRCLEIMAGEVIEW = "de.hdodenhof:circleimageview:3.1.0"
    const val AIRBNB_LOTTIE = "com.airbnb.android:lottie:3.4.0"
    const val FACEBOOK_SHIMMER = "com.facebook.shimmer:shimmer:0.5.0"
    const val BUMPTECH_GLIDE = "com.github.bumptech.glide:glide:4.11.0"
    const val BUMPTECH_GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.11.0"

    //Chip Navigation
    //Docs : https://github.com/ismaeldivita/chip-navigation-bar
    const val CHIP_NAVIGATION = "com.ismaeldivita.chipnavigation:chip-navigation-bar:1.0.0"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
    const val JSOUP = "org.jsoup:jsoup:1.12.1"
}

object Lifecycle {
    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val LIFECYCLE_LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
}

object Hilt {
    const val HILT_ANDROID = "com.google.dagger:hilt-android:2.28-alpha"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:2.28-alpha"
    const val HILT_LIFECYCLE_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:1.0.0-alpha01"
}

object Retrofit {
    const val RETROFIT2 = "com.squareup.retrofit2:retrofit:2.9.0"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:2.6.0"
}

object OkHttp {
    const val OKHTTP = "com.squareup.okhttp3:okhttp:4.7.2"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:4.7.2"
}

object Coroutines {
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"

    const val TEST_KOTLINX_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.1"
}

object Androidx {
    const val CORE_KTX = "androidx.core:core-ktx:1.3.0"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val LEGACY_SUPPORT_V4 = "androidx.legacy:legacy-support-v4:1.0.0"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:2.3.0-rc01"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:2.3.0-rc01"
    const val ARCH_TESTING = "androidx.arch.core:core-testing:2.1.0"
    const val BIOMETRIC = "androidx.biometric:biometric:1.0.1"
}

object Google {
    const val MATERIAL = "com.google.android.material:material:1.3.0-alpha02"
}

object Firebase {
    const val FIREBASE_STORAGE = "com.google.firebase:firebase-storage:19.1.1"
    const val FIREBASE_FIRESTORE = "com.google.firebase:firebase-firestore:21.4.3"
    const val FIREBASE_AUTH = "com.google.firebase:firebase-auth-ktx:19.3.1"

}

object Kotlin {
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:1.3.72"
}