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

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.3")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}