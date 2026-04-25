# League Android Code Challenge - Version 3.0 (Multi-Module Architecture)

This version represents the ultimate evolution of the project, transforming it into a **professional-grade, multi-module Android application**. It adheres to the highest standards of modern Android development, focusing on **build performance, strict isolation, and secure authentication**.

## 🚀 Version 3.0 Enhancements

### 1. Multi-Module Clean Architecture
To support large-scale development and faster build times, the application has been decomposed into feature and core modules:
- **`:kotlin_app`**: The thin entry point (Android App).
- **`:feature:feed`**: Isolated UI module for the social feed using Jetpack Compose.
- **`:core:domain`**: Pure Kotlin module containing business logic (Use Cases) and repository interfaces.
- **`:core:data`**: Implementation of repositories, managing data flow between network, database, and preferences.
- **`:core:network`**: Centralized networking logic, Retrofit configuration, and API definitions.
- **`:core:database`**: Local persistence layer using Room.
- **`:core:model`**: Shared domain and DTO models.

### 2. Enhanced Security & Authentication
- **Secure Token Management**: Replaced standard Preferences with **Jetpack DataStore** for reactive and more secure storage of the `api_key`.
- **Automated Auth Interceptor**: Integrated an `AuthInterceptor` in the OkHttp pipeline that automatically injects the `x-access-token` header, ensuring all network calls are authenticated seamlessly.
- **Token Provider Pattern**: Decoupled token retrieval from the network layer for better testability and cleaner architecture.

### 3. Build & Tooling Optimization
- **Consistent JVM Toolchain**: Unified the build environment using `jvmToolchain(11)` across all modules to resolve complex `jlink` and compatibility issues.
- **Optimized Dependencies**: Each module declares only the dependencies it needs, reducing the final APK size and improving incremental compilation.

---

## 🏗 Modular Architecture Diagram

```text
       [:kotlin_app]
            |
    [:feature:feed]
      /     |     \
[:core:domain] [:core:model]
      |
[:core:data]
    /    \
[:core:network] [:core:database]
```

---

## 🛠 Key Technologies
- **Jetpack Compose**: Modern declarative UI.
- **Dagger Hilt**: Multi-module dependency injection.
- **Coroutines & Flow**: For reactive, non-blocking programming.
- **Retrofit & OkHttp**: For robust networking.
- **Room**: For local SQLite persistence.
- **Jetpack DataStore**: For modern, secure preference storage.

---

## 📋 Build & Run
1. Open the project in **Android Studio (Ladybug or newer)**.
2. Ensure you have **JDK 11** installed and configured in your Gradle settings.
3. Sync Project with Gradle Files.
4. Run the `:kotlin_app` module.

---

## 📜 Documentation History
- [View Version 2.0 Documentation (Single Module Clean Architecture)](README_v2.md)
- [View Version 1.0 Documentation (Basic MVVM)](README_v1.md)
