plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.stargazer.noteme"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.stargazer.noteme"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    //FireBase
    implementation(platform("google.firebase:firebase-bom:34.0.0")) // bom kullanarak versiyon çakışmalarını engelleriz
    implementation("google.firebase:firebase-storage-ktx") // Fotoğraflar için depolama
    implementation("google.firebase:firebase-auth-ktx") // ileride giriş sistemi gerekebilir

    // Retrofit - İnternet üzerinden veri alışverişi
    val retrofit_version = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // Coil (Compose sürümü) - İnternetteki linki görsele çevirmek için
    implementation("io.coil-kt:coil-compose:2.7.0")

// Room - Yerel veritabanı
    val room_version = "2.7.0-alpha01"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    // kapt yerine ksp kullanıyoruz:
    ksp("androidx.room:room-compiler:$room_version")


    // ViewModel ve Lifecycle (Uygulamanın beyni)
    // Neden? Kullanıcı telefonu yan çevirdiğinde notların kaybolmaması
    // ve UI ile veri arasındaki köprüyü kurmak için.
    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")

    // GSON (Tercümanımız)
    // Neden? İnternetten (Retrofit ile) veri çekerken veriler "JSON" formatında gelir.
    // GSON bu karmaşık metinleri bizim anlayacağımız Kotlin sınıflarına otomatik çevirir.
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Navigation Compose (Sayfa değiştirici)
    // Neden? Ana sayfadan not ekleme sayfasına gitmek için bir navigasyon sistemine ihtiyacımız var.
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Kotlin Coroutines (Arka plan işçileri)
    // Neden? Veritabanına kayıt yapmak veya internetten fotoğraf çekmek ağır işlerdir.
    // Bu işleri yaparken uygulamanın donmaması için arka planda çalışmalarını sağlarız.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}