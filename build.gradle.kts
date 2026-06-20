import com.almostreliable.almostgradle.dependency.LoadingMode

plugins {
    id("net.neoforged.moddev") version "2.0.138"
    id("com.almostreliable.almostgradle") version "2.1.1"
    id("idea")
    id("me.shedaniel.unified-publishing") version "0.1.13"
}

val runningInCI = System.getenv("CI").toBoolean()
val env = System.getenv()

almostgradle.setup {
    javaVersion = 25
    modPackage = "pie.ilikepiefoo"

    launchArgs {
        loggingLevel = "INFO"
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
}

neoForge {
    interfaceInjectionData {
        from(file("interfaces.json"))
        publish(file("interfaces.json"))
    }
}

repositories {
    mavenLocal()
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

    compileOnly("mezz.jei:jei-${property("minecraftVersion")}-neoforge:${property("jeiVersion")}")
    compileOnly("dev.architectury:architectury-neoforge:${property("architecturyVersion")}")
    compileOnly("snownee.jade:Jade-neoforge:${property("jadeVersion")}")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("-Xmaxerrs", "1000"))
}

/*unifiedPublishing {
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
		}

		if (env["CURSEFORGE_KEY"] != null) {
			curseforge {
				token = env["CURSEFORGE_KEY"]
				id = property("curseforge_id").toString()
			}
		}

		if (env["MODRINTH_TOKEN"] != null) {
			modrinth {
				token = env["MODRINTH_TOKEN"]
				id = property("modrinth_id").toString()
				version = "${property("modId")}-neoforge-${property("modVersion")}"
			}
		}
	}
}*/