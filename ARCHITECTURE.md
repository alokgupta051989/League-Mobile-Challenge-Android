# Architecture Guide: Multi-Module Clean Architecture + MVVM

This document provides a deep dive into the architectural design of the League Android Challenge application (Version 3.0), explaining the "Why" and "How" behind each layer and component.

## 🏗 The Core Philosophy: Clean Architecture

The project is built on **Clean Architecture** principles, which aim to separate concerns and make the application independent of frameworks, UI, and external data sources.

### The Dependency Rule
Dependencies flow **inward** toward the **Domain Layer**. The Domain layer (the center of the circle) has no knowledge of the Data or Presentation layers.

```text
Presentation (UI) ──▶ Domain (Logic) ◀── Data (Infrastructure)
```

---

## 📊 Dependency Graph

The following diagram illustrates how modules depend on each other. Arrows indicate an "implementation" or "api" dependency.

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

---

## 📂 Project Structure

Below is the high-level file structure showing the responsibility of each module:

```text
root/
├── kotlin_app/              # Main App Module (Glue)
│   └── main/MainActivity    # Entry point & Navigation
├── feature/
│   └── feed/                # Feed Feature Module
│       ├── ui/              # Compose Screens & Components
│       └── FeedViewModel    # Manages UI state
├── core/
│   ├── domain/              # Business Logic (Pure Kotlin)
│   │   ├── model/           # Domain-specific models
│   │   ├── repository/      # Repository Interfaces
│   │   └── usecase/         # Interactors (GetPosts, SyncPosts)
│   ├── data/                # Data Implementation
│   │   ├── RepositoryImpl   # Implements Repo Interfaces
│   │   └── TokenManager     # DataStore integration
│   ├── network/             # Network Infrastructure
│   │   ├── api/             # Retrofit Interfaces
│   │   └── AuthInterceptor  # Automated Token injection
│   ├── database/            # Persistence Layer
│   │   ├── PostDao          # Room DAOs
│   │   └── PostEntity       # Room Entities
│   └── model/               # Shared Data Models
│       ├── Post             # Domain Model
│       └── Resource         # Generic State Wrapper
```

---

## 📂 Multi-Module Breakdown

In Version 3.0, these layers are enforced by **physical module boundaries**. This prevents accidental coupling and enables faster incremental builds.

### 1. Presentation Layer (`:feature:feed`)
*   **Patterns**: MVVM + Jetpack Compose + M3.
*   **Responsibility**: Rendering data to the screen and capturing user intent.
*   **ViewModel**: Observes the `PagingData` stream from the Domain layer and manages UI states (`Loading`, `Success`, `Error`).
*   **Compose**: Uses stateless components and hoisting to ensure UI code is easy to preview and test.

### 2. Domain Layer (`:core:domain`)
*   **Patterns**: Use Cases (Interactors) + Repository Interfaces.
*   **Responsibility**: Contains the **business logic** of the application.
*   **Pure Kotlin**: This is a non-Android module. It uses no Android-specific libraries, making it highly testable and reusable.
*   **Use Cases**: `GetPostsUseCase` (data observation) and `SyncPostsUseCase` (data refresh).

### 3. Data Layer (`:core:data`, `:core:network`, `:core:database`)
*   **Patterns**: Repository Pattern + Single Source of Truth (SSOT).
*   **Responsibility**: Managing data from multiple sources (API, Room, DataStore).
*   **SSOT**: The database (`:core:database`) is the single source of truth. The repository ensures that the UI always observes the database, while the network (`:core:network`) is used solely to update the cache.
*   **Paging 3**: Implemented at the database level (`PagingSource`) and exposed through the repository.

### 4. Shared Layers (`:core:model`)
*   Contains the common data structures used by all layers, including Domain models and Network DTOs.

---

## 🔐 Security & Networking Infrastructure

### Automated Authentication
- **`AuthInterceptor`**: A custom OkHttp interceptor that retrieves the token from `TokenManager` and injects it into every request. This keeps the network layer clean and avoids passing tokens manually.
- **`TokenManager`**: Uses **Jetpack DataStore** (Preferences) to store the API key. It provides a reactive `Flow` of the token, allowing the app to react instantly to session changes.

### Robust Error Handling
- **Mapping**: The Data layer catches `HttpException` and `IOException` and maps them to descriptive domain-level exceptions.
- **Auto-Logout**: A `401 Unauthorized` response automatically triggers a token cleanup, ensuring the user is prompted to re-authenticate if their session expires.

---

## 📦 Data Flow Example: Fetching the Feed

1.  **UI**: `FeedViewModel` calls `getPostsUseCase()`.
2.  **UseCase**: Returns a `Flow<PagingData<Post>>` from the `Repository`.
3.  **Repository**: Configures a `Pager` that uses `PostDao.getPagedPosts()` as the `PagingSource`.
4.  **Sync**: In parallel, `FeedViewModel` calls `syncPostsUseCase()`.
5.  **Repository**: Fetches users and posts from the API, merges them, and saves them to **Room**.
6.  **DB**: Room detects the update and automatically pushes the new data through the `Flow` to the UI.

---

## 🧪 Testing Strategy

The modular design allows for a tiered testing approach:
- **Unit Tests**: Domain logic (Use Cases) and Mappers.
- **Integration Tests**: Repository logic, verifying that network data is correctly cached in the database.
- **UI Tests**: ViewModel state transitions and Paging data collection.

---

## ✅ Summary of Benefits
- **Maintainability**: Clear separation of concerns makes it easy to locate and fix bugs.
- **Scalability**: New features can be added as isolated modules without impacting existing code.
- **Testability**: Most business and data logic can be tested on the JVM without an emulator.
- **Reliability**: Offline-first design ensures the app remains functional in poor network conditions.
