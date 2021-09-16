import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	jvm
	boot
	dependencyManagement
	spring
	flyway
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
	implementation(springBootStarterSecurity)
	implementation(springBootStarterJdbc)
	annotationProcessor(springBootConfigurationProcessor)
	implementation(jacksonModuleKotlin)
	implementation(kotlinReflect)
	implementation(kotlinStdlibJdk8)
	developmentOnly(springBootDevtools)
	runtimeOnly(mysqlConnectorJava)
	testImplementation(springBootStarterTest)
	implementation(springBootStarterValidation)
	implementation(okHttp3)
	implementation(arrowCore)
	implementation(exposedCore)
	implementation(exposedDao)
	implementation(exposedJdbc)
	implementation(exposedJavaTime)
	implementation(exposedSpringTransaction)
	implementation(javaJwt)
	implementation(awsJavaSdkSsm)
	implementation(aspectjweaver)
	implementation(awsJavaSdkSqs)
	implementation(bootstrap)
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

flyway {
	url = System.getenv("WATERING_FLYWAY_URL")
	user = System.getenv("WATERING_FLYWAY_USER")
	password = System.getenv("WATERING_FLYWAY_PASSWORD")
}
