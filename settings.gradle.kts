// 1. Primero la gestión de plugins
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// 2. Luego la resolución de dependencias de los módulos
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// 3. Al final la configuración del proyecto
rootProject.name = "NetBuster"
include(":app")