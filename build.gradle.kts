import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	jvm
	boot
	dependencyManagement
	spring
}

group = "com.watering"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
}

dependencies {
	implementation(springBootStarterThymeleaf)
	implementation(springBootStarterWeb)
	implementation(jacksonModuleKotlin)
	implementation(kotlinReflect)
	implementation(kotlinStdlibJdk8)
	developmentOnly(springBootDevtools)
	runtimeOnly(mysqlConnectorJava)
	testImplementation(springBootStarterTest)
	implementation(okHttp3)
	implementation(arrowCore)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.wrapper {
	gradleVersion = GRADLE_VERSION
}
