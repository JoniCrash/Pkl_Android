//import org.gradle.internal.impldep.bsh.commands.dir
//import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
//import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.layout"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.layout"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation ("pub.devrel:easypermissions:3.0.0")
    implementation ("com.vmadalin:easypermissions-ktx:1.0.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.compose.material:material-icons-extended:1.6.4")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.4")
    // location permission
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")

    implementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.compose.foundation:foundation-android:1.6.4")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}