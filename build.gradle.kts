import com.almostreliable.almostgradle.dependency.LoadingMode

plugins {
    id("net.neoforged.moddev") version "2.0.138"
    id("com.almostreliable.almostgradle") version "2.2.0"
    id("idea")
    id("me.shedaniel.unified-publishing") version "0.1.13"
}

val runningInCI = System.getenv("CI").toBoolean()
val env = System.getenv()

almostgradle.setup {
    javaVersion = 21
    modPackage = "pie.ilikepiefoo"

    launchArgs {
        loggingLevel = "INFO"
        autoWorldJoin = true
    }

    dataGen = false
    splitRunDirs = true
    withAccessTransformerValidation = !runningInCI

    recipeViewers {
        jei {
            runConfig = true
            mode = LoadingMode.API
            version = project.property("jeiVersion").toString()
            minecraftVersion = project.property("minecraftVersion").toString()
        }
    }

    tests {
        jUnit = true
    }
}

neoForge {
    interfaceInjectionData {
        from(file("interfaces.json"))
        publish(file("interfaces.json"))
    }
}

repositories {
    mavenCentral()

    maven {
        setUrl("https://maven.neoforged.net/releases")
    }

    maven {
        setUrl("https://maven.latvian.dev/releases")
        content {
            includeGroup("dev.latvian.mods")
            includeGroup("dev.latvian.apps")
        }
    }

    maven {
        setUrl("https://jitpack.io")
        content {
            includeGroup("com.github.rtyley")
        }
    }

    maven {
        setUrl("https://maven.architectury.dev/")
        content {
            includeGroup("dev.architectury")
        }
    }

    maven {
        setUrl("https://maven.blamejared.com")
        content {
            includeGroup("mezz.jei")
            includeGroup("snownee.jade")
        }
    }

    maven {
        setUrl("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    api("dev.latvian.mods:kubejs-neoforge:${property("kubejsVersion")}")
    interfaceInjectionData("dev.latvian.mods:kubejs-neoforge:${property("kubejsVersion")}")

    api("dev.architectury:architectury-neoforge:${property("architecturyVersion")}")
    api("curse.maven:jade-324717:${property("jadeFileId")}")
}

unifiedPublishing {
    project {
        releaseType = property("uploadType").toString()
        gameVersions = property("supportedVersions").toString().split(", ").toList()
        gameLoaders = listOf("neoforge")
        displayName = "${property("modName")} NeoForge ${project.version}"
        mainPublication(tasks.jar.get())

        relations {
            depends {
                curseforge = "kubejs"
                modrinth = "kubejs"
            }
            optional {
                curseforge = "architectury"
                curseforge = "jade"
                curseforge = "jei"
                modrinth = "architectury"
                modrinth = "jade"
                modrinth = "jei"
            }
        }

        if (env["CURSEFORGE_KEY"] != null) {
            curseforge {
                token = env["CURSEFORGE_KEY"]
                id = property("curseforgeId").toString()
            }
        }

        if (env["MODRINTH_TOKEN"] != null) {
            modrinth {
                token = env["MODRINTH_TOKEN"]
                id = property("modrinthId").toString()
                version = "${property("modId")}-neoforge-${property("modVersion")}"
            }
        }
    }
}