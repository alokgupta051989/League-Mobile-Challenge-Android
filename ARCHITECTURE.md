# Architecture Guide: Evolution from Monolith to Multi-Module Clean Architecture

This document provides a deep dive into the architectural design of the League Android Challenge application across its three major versions.

---

## 🏗 Architectural Evolution

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

### 🟢 Version 1.0: Basic MVVM (Monolith)
The foundation of the app, using a standard **Model-View-ViewModel** pattern within a single module.
- **Data Flow**: `View <-> ViewModel <-> Repository <-> Network (Retrofit)`
- **Pros**: Quick implementation, low complexity.
- **Cons**: Difficult to test, high coupling between UI and Networking.

```text
[:app]
  ├── ui/          # View & ViewModel
  ├── repository/  # Data Logic
  └── api/         # Networking
```

---

### 🔵 Version 2.0: Single-Module Clean Architecture
Introduced **Clean Architecture** principles and **Dependency Injection** to separate business rules from implementation details.
- **Layers**: Presentation, Domain, and Data (all within one module).
- **Patterns**: Use Cases (Interactors) and Repository Pattern.
- **DI**: Dagger Hilt for automated dependency management.
- **Persistence**: Room Database for offline caching.

```text
[:app]
  ├── ui/          # Presentation
  ├── domain/      # Business Logic (Use Cases & Interfaces)
  └── data/        # Infrastructure (Repo, Network, DB)
```

---

### 🔴 Version 3.0: Multi-Module Clean Architecture (Current)
The ultimate evolution, enforcing architectural boundaries at the **compiler level** using a 7-module structure.

#### 📊 Dependency Graph (v3.0)
```text
       +-----------------------+
       |      :kotlin_app      | (Orchestrator / Hilt Entry)
       +-----------+-----------+
                   |
        +----------v----------+
        |    :feature:feed    | (UI / ViewModel)
        +----------+----------+
                   |
        +----------v----------+
        |    :core:domain     | (Use Cases / Interfaces)
        +----------+----------+
                   |
        +----------v----------+
        |     :core:data      | (Repositories / SSOT Logic)
        +-----+----------+----+
              |          |
      +-------v-------+  +-------v-------+
      | :core:network |  | :core:database| (Infrastructure)
      +-------+-------+  +-------+-------+
              |                  |
              +---------+--------+
                        |
              +---------v--------+
              |    :core:model   | (Domain Models / DTOs)
              +------------------+
```

#### 📂 Project Structure (v3.0)
```text
root/
├── kotlin_app/              # Main App Module (Glue)
├── feature/
│   └── feed/                # Feed Feature Module (UI & ViewModel)
├── core/
│   ├── domain/              # Business Logic (Pure Kotlin)
│   ├── data/                # Data Implementation & TokenManager
│   ├── network/             # Network Infrastructure & AuthInterceptor
│   ├── database/            # Persistence Layer (Room)
│   └── model/               # Shared Domain & DTO Models
```

---

## 📂 Version 3.0 Deep Dive

### 1. Presentation Layer (`:feature:feed`)
*   **Patterns**: MVVM + Jetpack Compose + M3.
*   **ViewModel**: Observes the `PagingData` stream from the Domain layer.

### 2. Domain Layer (`:core:domain`)
*   **Patterns**: Use Cases (Interactors) + Repository Interfaces.
*   **Pure Kotlin**: Zero Android dependencies, allowing for fast JVM testing.

### 3. Data Layer (`:core:data`, `:core:network`, `:core:database`)
*   **Single Source of Truth (SSOT)**: The database is the source of truth; the network only updates the cache.
*   **Paging 3**: Integrated at the database level for memory-efficient loading.

---

## 🔐 Security & Networking Infrastructure

-   **`AuthInterceptor`**: Centralized header management for automated token injection.
-   **`TokenManager`**: Uses **Jetpack DataStore** for secure, reactive preference storage.
-   **Error Mapping**: Converts `HttpException` and `IOException` into user-friendly domain exceptions.

---

## 🧪 Testing Strategy (Modular)
- **Unit Tests**: Domain logic and Mappers.
- **Integration Tests**: Repository-to-Database caching flows.
- **UI Tests**: ViewModel states and Paging data streams.

---

## ✅ Summary of Benefits (v3.0)
- **Strict Isolation**: Modules prevent accidental coupling.
- **Parallel Compilation**: Faster build times for large teams.
- **Reliability**: Offline-first design with Paging 3.
- **Security**: Professional-grade authentication management.
