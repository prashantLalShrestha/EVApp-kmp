import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.skie)
    alias(libs.plugins.buildKonfig)
}

val packageId = "np.prashant.dev.evlib"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "EVLib"
            binaryOption("bundleId", packageId)
            export(libs.kotlin.stdlib)
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += listOf("-Xbinary=gc=pmcs")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.stdlib)
            implementation(libs.koin.core)
            implementation(libs.koin.test)
            implementation(libs.bundles.ktor.common)
            implementation(libs.skie.annotations)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }
}

android {
    namespace = packageId
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    beforeEvaluate {
        libraryVariants.all {
            compileOptions {
                // Flag to enable support for the new language APIs
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
    dependencies {
        coreLibraryDesugaring(libs.android.desugarJdkLibs)

        implementation(libs.koin.android)
    }
}

afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

val buildIdAttribute = Attribute.of("buildIdAttribute", String::class.java)
configurations.forEach {
    it.attributes {
        attribute(buildIdAttribute, it.name)
    }
}

buildkonfig {
    packageName = packageId
     objectName = "EVLibConfig"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "API_HOST", "api.openchargemap.io")
        buildConfigField(FieldSpec.Type.STRING, "API_KEY", "123")
    }
}

task("testClasses")