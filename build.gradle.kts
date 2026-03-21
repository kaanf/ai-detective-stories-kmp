import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask
import org.jlleitschuh.gradle.ktlint.tasks.KtLintFormatTask

fun BaseKtLintCheckTask.excludeGeneratedSources() {
    exclude("**/build/**")
    exclude("**/generated/**")
    exclude { fileTreeElement ->
        val normalizedPath = fileTreeElement.file.invariantSeparatorsPath
        normalizedPath.contains("/build/") || normalizedPath.contains("/generated/")
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.hot.reload) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ktlint)
}

configure<KtlintExtension> {
    outputToConsole.set(true)
    ignoreFailures.set(false)
}

tasks.withType<BaseKtLintCheckTask>().configureEach {
    excludeGeneratedSources()
}

tasks.withType<KtLintFormatTask>().configureEach {
    excludeGeneratedSources()
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<KtlintExtension> {
        outputToConsole.set(true)
        ignoreFailures.set(false)
    }

    tasks.withType<BaseKtLintCheckTask>().configureEach {
        excludeGeneratedSources()
    }

    tasks.withType<KtLintFormatTask>().configureEach {
        excludeGeneratedSources()
    }
}

tasks.named("ktlintCheck") {
    dependsOn(subprojects.map { "${it.path}:ktlintCheck" })
}

tasks.named("ktlintFormat") {
    dependsOn(subprojects.map { "${it.path}:ktlintFormat" })
}
