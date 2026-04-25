# Instructions for Build and Run - Multi-Version Guide

This document provides instructions on how to set up, build, and run the different versions of the League Android Challenge application.

## 🛠 Prerequisites

Regardless of the version you are running, ensure you have the following:

1.  **Android Studio (Ladybug 2024.2.1 or newer)**: Required for full support of Compose and multi-module tooling.
2.  **JDK 17**: The project uses JDK 17 for the Gradle build system.
3.  **Android SDK 34**: Required for compiling all versions.

---

## 🚀 Switching Between Versions

The project is organized into sequential versions representing architectural growth. Use Git to switch between them:

-   **Version 1.0 (Basic MVVM)**: `git checkout version_1.0`
-   **Version 2.0 (Clean Arch + Room)**: `git checkout version_2.0`
-   **Version 3.0 (Multi-Module)**: `git checkout version_3.0` (Recommended)

---

## 📦 Version-Specific Build Instructions

### Version 3.0 (Latest)
- **Structure**: Multi-module (7 modules).
- **Run Configuration**: Select **`:kotlin_app`** in the toolbar.
- **Key Note**: Uses `jvmToolchain(11)` and `DataStore`. If migrating from v2 on the same device, a destructive Room migration will occur automatically.

### Version 2.0
- **Structure**: Single-module Clean Architecture.
- **Run Configuration**: Select **`:app`** (or the primary app module).
- **Key Note**: Uses `SharedPreferences` and a single-module Room database.

### Version 1.0
- **Structure**: Basic MVVM.
- **Run Configuration**: Select **`:app`**.
- **Key Note**: Requires an active internet connection as there is no local caching.

---

## 🛠 Setup & Build Steps

1.  **Open in Android Studio**: Select **Open** and navigate to the project root.
2.  **Configure JDK**: Go to **Settings > Build, Execution, Deployment > Build Tools > Gradle**. Set **Gradle JDK** to **JDK 17**.
3.  **Sync Gradle**: Click the "Sync Project with Gradle Files" icon.
4.  **Build**: 
    - IDE: **Build > Make Project**.
    - Terminal: `./gradlew assembleDebug`

---

## 📱 Running the App

1.  **Device**: Use an emulator or physical device running **API 30 or higher**.
2.  **Launch**: Click the Green Play icon with the correct module selected.

---

## 🧪 Running Tests

Tests are localized within each module (v3) or the main app module (v1/v2).

- **v3.0**: `./gradlew test` (Runs all module tests).
- **v1.0 / v2.0**: `./gradlew :app:testDebugUnitTest`.

---

## 📖 Troubleshooting

-   **Clean Build**: When switching branches, always run `./gradlew clean` to avoid build cache conflicts.
-   **Invalid Cache**: If classes are missing after a branch switch, go to **File > Invalidate Caches...** and restart.
