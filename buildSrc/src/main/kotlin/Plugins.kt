import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec

val PluginDependenciesSpec.jvm get() = kotlin("jvm").version(Versions.Plugin.KOTLIN)
val PluginDependenciesSpec.boot get() = id("org.springframework.boot").version(Versions.Plugin.SPRING_BOOT)
val PluginDependenciesSpec.dependencyManagement get() = id("io.spring.dependency-management").version(Versions.Plugin.DEPENDENCY_MANAGEMENT)
val PluginDependenciesSpec.spring get() = kotlin("plugin.spring").version(Versions.Plugin.KOTLIN)
val PluginDependenciesSpec.flyway get() = id("org.flywaydb.flyway").version(Versions.Plugin.FLYWAY)
