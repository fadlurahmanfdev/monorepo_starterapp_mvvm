plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}

android {
    namespace = "com.fadlurahmanf.monorepo.app_notification"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("fake") {
            dimension = "environment"
        }

        create("dev") {
            dimension = "environment"
        }

        create("staging") {
            dimension = "environment"
        }

        create("prod") {
            dimension = "environment"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core_notification"))

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //noinspection UseTomlInstead
    implementation("com.github.fadlurahmanfdev:library_core_notification:0.0.1") {
        exclude(group = "com.github.fadlurahmanfdev.core_notification.data")
        exclude(group = "com.github.fadlurahmanfdev.core_notification.others")
    }
}