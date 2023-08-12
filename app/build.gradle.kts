plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.xpenses"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.xpenses"
        minSdk = 21
        targetSdk = 33
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

    buildFeatures{
        viewBinding = true
    }
}
val room_version = "2.5.2"
val hilt_version = "2.44"
val lifecycle_version = "2.6.1"
val coroutines_version = "1.6.4"

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    // coroutines for Room
    implementation("androidx.room:room-ktx:$room_version")
    //Lifecyle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

//Hilt: Allow references to generated code
kapt {
    correctErrorTypes = true
}
