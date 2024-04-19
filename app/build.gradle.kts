plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}

android {
    namespace = "com.fadlurahmanf.monorepo.starterappmvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fadlurahmanf.monorepo.starterappmvvm"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
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
            buildConfigField(
                "String",
                "BASE_MERCHANT_URL",
                "\"https://fake_merchant.bankmas.my.id\""
            )
            applicationIdSuffix = ".fake"
            resValue("string", "app_name", "Monorepo Fake")
        }

        create("dev") {
            dimension = "environment"
            buildConfigField("String", "BASE_MERCHANT_URL", "\"https://merchant.bankmas.my.id\"")
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Monorepo Dev")
        }

        create("staging") {
            dimension = "environment"
            buildConfigField("String", "BASE_MERCHANT_URL", "\"https://merchant.bankmas.link\"")
            applicationIdSuffix = ".staging"
            resValue("string", "app_name", "Monorepo Staging")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_MERCHANT_URL", "\"https://merchant.bankmas.net\"")
            resValue("string", "app_name", "Monorepo")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core_crypto"))
    implementation(project(":core_notification"))

    implementation(project(":app_config"))
    implementation(project(":app_example"))
    implementation(project(":app_notification"))
    implementation(project(":app_shared"))
}