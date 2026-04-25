# Architecture Guide: Multi-Module Clean Architecture + MVVM

This document explains the architectural decisions made in this project and how they work together to create a maintainable, testable, and scalable Android application.

## 🏗 Why This Architecture?

The project follows **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)** and **Multi-Module** isolation.

-   **Clean Architecture** provides the high-level **boundaries** (Data, Domain, Presentation).
-   **Multi-Module** enforces these boundaries at the **compilation level**, preventing accidental leaks of implementation details.
-   **MVVM** provides the **reactive behavior** within the Presentation layer.

---

## 📈 Evolution: Version 1.0 to Version 3.0

Version 3.0 represents a professional-grade shift to modularization, enhanced security, and production-ready data handling.

| Feature | Version 1.0 | Version 2.0 | Version 3.0 (Current) | Benefit |
| :--- | :--- | :--- | :--- | :--- |
| **Structure** | Monolithic | Single Module | **Multi-Module** | Faster build times, strict isolation, and independent scaling. |
| **Dependency Injection** | Manual Factory | Dagger Hilt | **Hilt (Multi-Module)** | Automated DI across all modules with clear scoping. |
| **Data Storage** | Network only | Room Database | **Room + DataStore** | Secure, reactive token management and offline-first data. |
| **Data Fetching** | Simple List | List + Cache | **Paging 3 + Flow** | Memory-efficient, reactive, and scalable data handling. |
| **Authentication** | Manual logic | Basic Storage | **AuthInterceptor** | Transparent and automated security for all network calls. |
| **UI Framework** | Basic Compose | Compose + M3 | **Modular Compose** | UI components isolated in feature modules for reusability. |

---

## 📂 Module Breakdown (v3.0)

### 1. Feature Modules (`:feature:*`)
*   **`:feature:feed`**: Isolated UI module for the Social Feed. It depends only on `:core:domain` and `:core:model`.

### 2. Core Modules (`:core:*`)
*   **`:core:domain`**: The core business logic. Contains **Use Cases** and Repository interfaces. Pure Kotlin module.
*   **`:core:data`**: Implements repositories and manages the orchestration between the network, database, and DataStore.
*   **`:core:network`**: Networking infrastructure using Retrofit and an **AuthInterceptor** for automatic header injection.
*   **`:core:database`**: Persistence layer using Room, providing a Single Source of Truth for the application.
*   **`:core:model`**: Shared domain models and DTOs.

### 3. App Module (`:kotlin_app`)
*   The orchestrator module that wires everything together using Hilt.

---

## 🔐 Security & Data Management

-   **Jetpack DataStore**: Secure, reactive storage for auth tokens.
-   **AuthInterceptor**: Centralized header management.
-   **Offline-First**: The app observes the local database. When a sync is triggered, the data is fetched from the network, cached in Room, and automatically reflected in the UI.
-   **Paging 3**: The database provides a `PagingSource` to the Repository, which exposes a `PagingData` stream to the UI for efficient rendering.

---

## ✅ Summary of Benefits
1.  **Strict Isolation:** Feature modules are decoupled from implementation details.
2.  **Scalability:** Ready for infinite scrolling and large datasets via Paging 3.
3.  **Reliability:** Offline-first design ensures a consistent user experience.
4.  **Security:** Centralized authentication and secure token storage.
