plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //binding
    id ("kotlin-parcelize")

    //Hilt
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.codingub.belarusianhistory"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codingub.belarusianhistory"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }

    }

    buildTypes {
        getByName("debug"){
            isDebuggable = true
            isMinifyEnabled = false

            buildConfigField("String",
                "history_endpoint",
                "\"http://127.0.0.1:8080/\"")
        }
        getByName("release"){
            isDebuggable = false
            isMinifyEnabled = true
            buildConfigField("String",
                "history_endpoint",
                "\"http://127.0.0.1:8080/\"")
        }
//        release {
//            isMinifyEnabled = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
    }

    flavorDimensions.add("type")
    flavorDimensions.add("accessLevel")
    productFlavors {
        create("free"){
            dimension = "type"
        }
        create("paid"){
            dimension = "type"
        }

        create("user"){
            dimension = "accessLevel"
        }
        create("admin"){
            dimension = "accessLevel"
        }
        create("teacher"){
            dimension = "accessLevel"
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
        buildConfig = true
    }

//    kapt {
//        generateStubs = true
//    }
}

dependencies {

    //metadatada
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-animation:1.0.0-rc01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ktx
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    //splash screen
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    // kapt("androidx.hilt:hilt-compiler:1.0.0")
   // implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    //Room
    val room_version = "2.6.0-rc01"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") //для работы корутин
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //gson
    implementation("com.google.code.gson:gson:2.10.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")
}

kapt {
    correctErrorTypes = true
}