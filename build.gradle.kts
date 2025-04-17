buildscript {
    repositories {
        google() // Google's Maven repository
        mavenCentral() // Central repository for Java libraries
    }
    dependencies {
        // Adds the Google Services plugin for integrating Google services like Firebase
        classpath("com.google.gms:google-services:4.3.15")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
