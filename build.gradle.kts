plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "de.funboyy"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "de.funboyy.addon.hypixel"

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    //devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "hypixel"
        displayName = "Hypixel"
        author = "Funboyy"
        description = "A nice helpful addon for Hypixel"
        minecraftVersion = "*"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}