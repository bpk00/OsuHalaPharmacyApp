plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.s22010075.osuhalapharmacyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.s22010075.osuhalapharmacyapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.gridlayout)
    // implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // adding additional dependencies related to google maps
    // implementation (libs.play.services.maps)
    implementation(libs.play.services.maps)

    // Firebase BOM for version consistency
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Firebase Core Initialization
    implementation("com.google.firebase:firebase-common")

    // Firebase Storage, Realtime Database, App Check, and other Firebase libraries
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-appcheck")
    implementation("com.google.firebase:firebase-appcheck-playintegrity")

    // FirebaseUI libraries (for Authentication, Firestore, Storage)
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.firebaseui:firebase-ui-storage:8.0.2")

    // Add Firebase Authentication
    implementation ("com.google.firebase:firebase-auth:21.0.5")

    // Glide (image loading)
    implementation("com.github.bumptech.glide:glide:4.14.2")

    // RecyclerView and CardView for UI
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
}
