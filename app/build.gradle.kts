plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.example.finalproject_chilicare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalproject_chilicare"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }




}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.databinding:databinding-runtime:8.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //epoxy
    implementation ("com.airbnb.android:epoxy:5.1.3")
//    kapt("com.airbnb.android:epoxy-processor:5.1.3")

    //gmsplay service
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")


    //lottie animation
    implementation ("com.airbnb.android:lottie-compose:6.1.0")
    implementation ("com.airbnb.android:lottie:6.1.0")

    //sliderindicator
    implementation ("com.github.zhpanvip:viewpagerindicator:1.2.3")

    // picasso
    implementation ("com.squareup.picasso:picasso:2.8")

    // lifecycle scope coroutine
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    implementation ("com.google.android.gms:play-services-location:21.0.0")
    implementation ("com.google.dagger:dagger:2.46.1")


    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-compiler:2.49")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.49")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.49")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.49")
    kaptTest ("com.google.dagger:hilt-compiler:2.49")

    //youtube sdk
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    implementation("androidx.exifinterface:exifinterface:1.3.6")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

}
