import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    kotlin("plugin.serialization") version "1.9.10"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Navigation
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")

            //ktor
            implementation("io.ktor:ktor-client-core:2.3.5")
            implementation("io.ktor:ktor-client-cio:2.3.5")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.hsdroid.bulkrepouploadforgithub.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe)
            packageName = "Bulk Repo Uploader"
            packageVersion = "1.0.0"
        }
    }
}
