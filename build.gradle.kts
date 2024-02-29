plugins {
    id("io.papermc.paperweight.userdev") version "1.5.9"

    `java-library`
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.essentialsx.net/releases/")

    maven("https://repo.olziedev.com/")

    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven/")
        }
        filter { includeGroup("maven.modrinth") }
    }
}

rootProject.version = if (System.getenv("BUILD_NUMBER") != null) "${rootProject.version}-${System.getenv("BUILD_NUMBER")}" else rootProject.version
val mcVersion = providers.gradleProperty("minecraftVersion").get()

dependencies {
    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")

    compileOnly("maven.modrinth", "pl3xmap", providers.gradleProperty("pl3xmapVersion").get())

    compileOnly("net.essentialsx", "EssentialsX", "2.20.1")

    compileOnly("com.olziedev", "playerwarps-api", "6.24.0-pre30")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    val jarsDir = File("$rootDir/jars")

    assemble {
        if (jarsDir.exists()) jarsDir.delete() else jarsDir.mkdirs()

        dependsOn(reobfJar)
    }

    reobfJar {
        outputJar.set(file("$jarsDir/${rootProject.name}-${rootProject.version}.jar"))
    }

    processResources {
        val props = mapOf(
            "name" to rootProject.name,
            "group" to rootProject.group,
            "version" to rootProject.version,
            "description" to rootProject.description,
            "authors" to rootProject.properties["authors"],
            "website" to rootProject.properties["website"],
            "apiVersion" to rootProject.properties["apiVersion"],
        )

        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}