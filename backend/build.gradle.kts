val mockitoAgent: Configuration by configurations.creating

plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.31.33")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.14.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    mockitoAgent("net.bytebuddy:byte-buddy-agent:1.17.5") { isTransitive = false }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-javaagent:${mockitoAgent.singleFile.absolutePath}")
}

ktlint {
    version.set("1.6.0")
    verbose.set(true)

    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

// DependencyUpdatesタスクの設定。依存関係の更新チェック時に、特定の条件に合致するバージョンを無視するように設定します。
tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf {
        // 管理対象の依存関係リストを定義します。これらの依存関係は自動更新の対象外とします。
        val managedDependencies =
            setOf(
                "org.springframework.boot:spring-boot-starter-web",
                "com.fasterxml.jackson.module:jackson-module-kotlin",
                "org.jetbrains.kotlin:kotlin-reflect",
                "org.springframework.boot:spring-boot-devtools",
                "org.springframework.boot:spring-boot-starter-test",
                "org.jetbrains.kotlin:kotlin-test-junit5",
                "org.junit.platform:junit-platform-launcher",
            )

        // 更新候補の依存関係が管理対象リストに含まれているかをチェックします。
        val isManaged = "${candidate.group}:${candidate.module}" in managedDependencies
        // 更新候補のバージョンが、alpha、beta、rc、cr、mといった非安定版の識別子を含んでいるかをチェックします（大文字・小文字を区別しません）。
        val isNonStable = candidate.version.contains(Regex("(?i).*[.-](alpha|beta|rc|cr|m)[.\\d-]*"))

        // 管理対象の依存関係であるか、または非安定版のバージョンである場合は、更新を拒否します。
        isManaged || isNonStable
    }
}
