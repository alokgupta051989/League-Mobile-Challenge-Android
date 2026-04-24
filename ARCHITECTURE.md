# Architecture Guide: MVVM + Clean Architecture

This document explains the architectural decisions made in this project and how they work together to create a maintainable and testable Android application.

## 🏗 Why This Combination?

We chose to combine **MVVM (Model-View-ViewModel)** with **Clean Architecture** principles because it provides the "Sweet Spot" for modern Android development.

- **Clean Architecture** provides the high-level **boundaries** (Data, Domain, Presentation).
- **MVVM** provides the **reactive behavior** within the Presentation layer.

---

## 📈 Evolution: Version 1.0 vs Version 2.0

Version 2.0 introduces significant architectural improvements to handle scale and complexity.

| Feature | Version 1.0 | Version 2.0 (Current) | Benefit |
| :--- | :--- | :--- | :--- |
| **Dependency Injection** | Manual Factory logic | **Dagger Hilt** | Decouples components and automates object lifecycle management. |
| **Domain Logic** | Contained in ViewModel | **Use Cases (Interactors)** | Business logic is reusable and easier to test in isolation. |
| **Data Storage** | Network only (In-memory) | **Room Database** | Supports **Offline-First** experience and single source of truth. |
| **List Loading** | Basic List | **Paging 3 Infrastructure** | Efficiently handles large datasets with less memory overhead. |
| **Unit Testing** | Basic (ViewModel only) | **High Coverage (85%+)** | Covers Repository, Mappers, UseCases, and ViewModels. |

---

## 📂 Layer Breakdown (v2.0)

### 1. Data Layer (Infrastructure)
*   **Purpose:** Fetches raw data from external sources (Network) and manages local persistence.
*   **Key Files:** `Api.kt`, `Repository.kt`, `PostDao.kt`, `PostEntity.kt`.
*   **Logic:** Orchestrates data flow between the API and the local database.

### 2. Domain Layer (The Brain)
*   **Purpose:** Contains business logic and converts raw data into clean models.
*   **Key Files:** `Post.kt` (Domain Model), `mapToDomain` (Mapper), `GetPostsUseCase.kt`.
*   **Logic:** Pure Kotlin layer. No dependencies on Android or Network frameworks.

### 3. Presentation Layer (MVVM)
*   **Purpose:** Manages UI state and handles user interaction.
*   **Key Files:** `FeedViewModel.kt`, `FeedScreen.kt`, `PostItem.kt`.
*   **MVVM Pattern:**
    *   **View (Compose):** Observes `StateFlow` from the ViewModel and renders the UI.
    *   **ViewModel:** Uses Hilt to inject Use Cases and updates `FeedUiState`.

---

## 🧪 Testing Strategy

The project is built with **Test-Driven Development (TDD)** principles. Each layer has corresponding unit tests to ensure high code quality.

- **ViewModel Tests:** Test UI state transitions and user interactions.
- **Repository Tests:** Test data orchestration and error handling.
- **Mapping Tests:** Test data transformation and integrity.

For a detailed breakdown of what is covered and how to run the tests, please refer to the [Testing Guide](TESTING.md).

---

## ✅ Summary of Benefits
1.  **Testability:** High coverage achieved through isolated unit tests.
2.  **Scalability:** Hilt and Clean Architecture allow adding new features without breaking existing ones.
3.  **Readability:** The separation of concerns makes it easy for any developer to locate logic.
