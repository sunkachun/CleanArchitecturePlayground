// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.12.0") // Example: Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10") // Example: Kotlin Gradle Plugin
    }
}

plugins {
    id("com.android.application") version "8.12.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
    id("com.android.library") version "8.12.0" apply false
    id("com.google.dagger.hilt.android") version "2.57" apply false
    kotlin("kapt") version "2.2.10" apply false
}