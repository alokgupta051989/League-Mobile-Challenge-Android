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
- **Automated Auth Interceptor**: Integrated an `AuthInterceptor` in the OkHttp pipeline that automatically injects the `x-access-token` header.
- **Separated Login Logic**: Decoupled the login process from data fetching using a dedicated `LoginUseCase`.

### 3. Offline-First & Scalable Data Fetching
- **Room Single Source of Truth**: Re-integrated the local database. The UI now observes a reactive stream from Room, ensuring the app works perfectly offline.
- **Paging 3 Integration**: Implemented Paging 3 for the feed, allowing the app to handle large datasets efficiently with minimal memory overhead.
- **Automatic Sync**: The app automatically synchronizes data from the network and updates the local cache, providing a seamless user experience.

### 4. Robust Error Handling & Stability
- **Structured Error Mapping**: Implemented comprehensive error catching in the Data layer, converting `HttpException` and `IOException` into user-friendly messages.
- **Descriptive UI States**: The UI now reacts to specific failure scenarios (e.g., "Invalid Credentials", "Network Error") with clear feedback.
- **Comprehensive Unit Testing**: Updated the entire test suite to cover the new multi-module logic, including Repository and UseCase orchestration.

### 5. Build & Tooling Optimization
- **Consistent JVM Toolchain**: Unified the build environment using `jvmToolchain(11)` across all modules to resolve `jlink` and compatibility issues.
- **Optimized Dependencies**: Each module declares only its required dependencies, reducing APK size and improving compilation times.

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
- **Paging 3**: Efficient list management and pagination.
- **Coroutines & Flow**: For reactive, non-blocking programming.
- **Retrofit & OkHttp**: For robust networking.
- **Room**: For local SQLite persistence.
- **Jetpack DataStore**: For modern, secure preference storage.

---

## 📜 Documentation History
- [View Version 2.0 Documentation](README_v2.md)
- [View Version 1.0 Documentation](README_v1.md)
