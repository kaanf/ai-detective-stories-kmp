import com.android.build.api.dsl.ApplicationExtension
import com.kaanf.detectiveaistories.convention.configureKotlinAndroid
import com.kaanf.detectiveaistories.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension>() {
                val releaseStoreFile =
                    providers.gradleProperty("DETECTIVE_STORE_FILE")
                        .orElse(providers.environmentVariable("DETECTIVE_STORE_FILE"))
                val releaseStorePassword =
                    providers.gradleProperty("DETECTIVE_STORE_PASSWORD")
                        .orElse(providers.environmentVariable("DETECTIVE_STORE_PASSWORD"))
                val releaseKeyAlias =
                    providers.gradleProperty("DETECTIVE_KEY_ALIAS")
                        .orElse(providers.environmentVariable("DETECTIVE_KEY_ALIAS"))
                val releaseKeyPassword =
                    providers.gradleProperty("DETECTIVE_KEY_PASSWORD")
                        .orElse(providers.environmentVariable("DETECTIVE_KEY_PASSWORD"))
                val hasReleaseSigning =
                    releaseStoreFile.isPresent &&
                        releaseStorePassword.isPresent &&
                        releaseKeyAlias.isPresent &&
                        releaseKeyPassword.isPresent

                namespace = "com.kaanf.detectiveaistories"

                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                if (hasReleaseSigning) {
                    signingConfigs {
                        create("release") {
                            storeFile = File(releaseStoreFile.get())
                            storePassword = releaseStorePassword.get()
                            keyAlias = releaseKeyAlias.get()
                            keyPassword = releaseKeyPassword.get()
                        }
                    }
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        if (hasReleaseSigning) {
                            signingConfig = signingConfigs.getByName("release")
                        }
                    }
                }

                configureKotlinAndroid(this)
            }
        }
    }
}
