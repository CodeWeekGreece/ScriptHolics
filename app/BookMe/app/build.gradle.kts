import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    signingConfigs {
        create("release") {
            val propsFile = rootProject.file("keystore.properties")
            val props = Properties()
            props.load(FileInputStream(propsFile))
            storeFile = file(props["storeFile"].toString())
            storePassword = props["storePassword"].toString()
            keyAlias = props["keyAlias"].toString()
            keyPassword = props["keyPassword"].toString()
        }
    }
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.midyh.bookme"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            isShrinkResources = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
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

    lint {
        isCheckReleaseBuilds = false
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    // Material
    implementation("com.google.android.material:material:1.4.0")

    // AndroidX
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("androidx.preference:preference-ktx:1.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.0-alpha04")
    kapt("androidx.room:room-compiler:2.4.0-alpha04")
    implementation("androidx.room:room-ktx:2.4.0-alpha04")

    // Image loading
    implementation("io.coil-kt:coil:1.3.2")

    // Firebase
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
