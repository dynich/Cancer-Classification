plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dicoding.asclepius"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.asclepius"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation("org.tensorflow:tensorflow-lite:2.8.0") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("org.tensorflow:tensorflow-lite-support:0.3.1") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }

    implementation("androidx.core:core-ktx:1.12.0") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("androidx.appcompat:appcompat:1.6.1") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("com.google.android.material:material:1.11.0") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("androidx.room:room-compiler:2.6.1") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }

    testImplementation("junit:junit:4.13.2") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    androidTestImplementation("androidx.test.ext:junit:1.1.5") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") {
        exclude(group = "com.intellij", module = "annotations")
        exclude(group = "org.jetbrains", module = "annotations")
    }

    // Tambahkan versi annotations yang diinginkan secara eksplisit
    implementation("org.jetbrains:annotations:13.0")
}

