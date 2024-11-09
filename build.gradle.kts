// Root build.gradle.kts file

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    dependencies {
        // Add your project-level dependencies here
        classpath("com.android.tools.build:gradle:7.4.2") // Gradle Plugin for Android
        classpath("com.google.gms:google-services:4.3.15") // Google services plugin
    }
}

allprojects {
    // No need to add repositories here anymore
}
