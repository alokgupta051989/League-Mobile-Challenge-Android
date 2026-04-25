# Architecture Guide: Multi-Module Clean Architecture + MVVM

This document explains the architectural decisions made in this project and how they work together to create a maintainable, testable, and scalable Android application.

## 🏗 Why This Architecture?

The project follows **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)** and **Multi-Module** isolation.

-   **Clean Architecture** provides the high-level **boundaries** (Data, Domain, Presentation).
-   **Multi-Module** enforces these boundaries at the **compilation level**, preventing accidental leaks of implementation details.
-   **MVVM** provides the **reactive behavior** within the Presentation layer.

---

## 📈 Evolution: Version 1.0 to Version 3.0

Version 3.0 represents a professional-grade shift to modularization and enhanced security.

| Feature | Version 1.0 | Version 2.0 | Version 3.0 (Current) | Benefit |
| :--- | :--- | :--- | :--- | :--- |
| **Structure** | Monolithic | Single Module | **Multi-Module** | Faster build times, strict isolation, and independent scaling. |
| **Dependency Injection** | Manual Factory | Dagger Hilt | **Hilt (Multi-Module)** | Automated DI across all modules with clear scoping. |
| **Data Storage** | Network only | Room Database | **Room + DataStore** | Secure, reactive token management and offline-first data. |
| **Authentication** | Manual logic | Basic Storage | **AuthInterceptor** | Transparent and automated security for all network calls. |
| **UI Framework** | Basic Compose | Compose + M3 | **Modular Compose** | UI components isolated in feature modules for reusability. |

---

## 📂 Module Breakdown (v3.0)

### 1. Feature Modules (`:feature:*`)
*   **`:feature:feed`**: Contains the Social Feed UI, ViewModels, and UI-specific state management using Jetpack Compose and Material 3.

### 2. Core Modules (`:core:*`)
*   **`:core:domain`**: The "Brain" of the app. Pure Kotlin module containing **Use Cases** and Repository interfaces. It has zero dependencies on Android or network libraries.
*   **`:core:data`**: The orchestrator. Implements the Repository interfaces and coordinates data flow between Network, Database, and DataStore.
*   **`:core:network`**: Infrastructure for Retrofit, OkHttp, and the **AuthInterceptor** for automatic header injection.
*   **`:core:database`**: Persistence layer using Room for local caching of posts.
*   **`:core:model`**: Shared domain models and DTOs used across all modules.

### 3. App Module (`:kotlin_app`)
*   The thin entry point of the application. It handles dependency wiring (Hilt) and navigation.

---

## 🔐 Security & Data Management

Version 3.0 introduces a robust security layer:
-   **Jetpack DataStore**: Used for persistent, reactive, and thread-safe storage of authentication tokens.
-   **AuthInterceptor**: Intercepts every outgoing network request to inject the `x-access-token` header if a token is available, removing manual token handling from individual API calls.

---

## 🧪 Testing Strategy

The modular structure enhances our ability to run targeted tests:
-   **Unit Tests (Pure Kotlin)**: Fast tests for `:core:domain` (Use Cases) and `:core:model`.
-   **Integration Tests**: Testing Repositories in `:core:data` and Network logic in `:core:network`.
-   **UI Tests**: Testing ViewModels and Compose screens in `:feature:feed`.

For a detailed breakdown of what is covered and how to run the tests, please refer to the [Testing Guide](TESTING.md).

---

## ✅ Summary of Benefits
1.  **Strict Isolation:** Feature modules cannot accidentally depend on internal implementation details of core modules.
2.  **Scalability:** New features can be added as new modules without increasing the complexity of existing code.
3.  **Security:** Authentication is centralized and automated, reducing the risk of security leaks.
4.  **Build Efficiency:** Gradle can compile modules in parallel, significantly reducing build times for large projects.
