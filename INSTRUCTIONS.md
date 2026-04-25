# Instructions for Build and Run - Version 3.0

This document provides clear, step-by-step instructions on how to set up, build, and run the League Android Challenge application.

## 🛠 Prerequisites

Before you begin, ensure you have the following installed on your development machine:

1.  **Android Studio (Ladybug 2024.2.1 or newer)**: Required for full support of the multi-module structure and Compose Previews.
2.  **JDK 17**: The project is configured to use JDK 17 for the Gradle build system (though modules target Java 11).
3.  **Android SDK 34**: Ensure you have the SDK for Android 14 (API 34) installed via the SDK Manager.
4.  **Internet Connection**: Required for the initial Gradle sync and downloading dependencies.

---

## 🚀 Setup & Build Steps

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/android-code-challenge.git
cd android-code-challenge
```

### 2. Open in Android Studio
- Launch Android Studio.
- Select **Open** and navigate to the project root folder.
- Wait for the IDE to finish the "Gradle Sync" process.

### 3. Configure JDK
- Go to **Settings (or Preferences) > Build, Execution, Deployment > Build Tools > Gradle**.
- Set the **Gradle JDK** to **JDK 17**.

### 4. Build the Project
- To build via the IDE: Go to **Build > Make Project**.
- To build via Terminal:
  ```bash
  ./gradlew assembleDebug
  ```

---

## 📱 Running the App

### 1. Set Up an Emulator or Device
- Open the **Device Manager** in Android Studio.
- Create or select an emulator running **API 30 (Android 11) or higher**.
- Alternatively, connect a physical device with USB Debugging enabled.

### 2. Launch the Application
- In the top toolbar, select the **`:kotlin_app`** run configuration.
- Click the **Run** button (Green Play icon).

---

## 🧪 Running Tests

The project includes unit tests for all architectural layers.

### Run All Tests
```bash
./gradlew test
```

### View Test Results
- Tests results will be printed in the terminal.
- For a visual report, navigate to: `kotlin_app/build/reports/tests/testDebugUnitTest/index.html`

---

## 📖 Troubleshooting

-   **Memory Issues**: If Gradle runs out of memory, check `gradle.properties` and ensure `org.gradle.jvmargs=-Xmx2048m`.
-   **Schema Mismatch**: If you see a Room error, the app will automatically handle it via destructive migration on the next run.
-   **Sync Failures**: Ensure you are on the latest version of the Ladybug IDE, as earlier versions may struggle with some newer Gradle features.
