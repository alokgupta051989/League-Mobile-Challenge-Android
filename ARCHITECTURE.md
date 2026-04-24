# Architecture Guide: MVVM + Clean Architecture

This document explains the architectural decisions made in this project and how they work together to create a maintainable and testable Android application.

## 🏗 Why This Combination?

We chose to combine **MVVM (Model-View-ViewModel)** with **Clean Architecture** principles because it provides the "Sweet Spot" for modern Android development.

- **Clean Architecture** provides the high-level **boundaries** (Data, Domain, Presentation).
- **MVVM** provides the **reactive behavior** within the Presentation layer.

---

## 📂 Layer Breakdown

### 1. Data Layer (Infrastructure)
*   **Purpose:** Fetches raw data from external sources (Network).
*   **Key Files:** `Api.kt`, `Service.kt`, `Repository.kt`, `PostDto`.
*   **Logic:** It handles Retrofit calls and knows nothing about the UI. It speaks in "DTOs" (Data Transfer Objects).

### 2. Domain Layer (The Brain)
*   **Purpose:** Contains business logic and converts raw data into clean models.
*   **Key Files:** `Post.kt` (Domain Model), `mapToDomain` (Mapper).
*   **Logic:** This layer is the most stable. If the API changes a field name, we only update the mapper here. The UI never sees the "raw" network models.

### 3. Presentation Layer (MVVM)
*   **Purpose:** Manages UI state and handles user interaction.
*   **Key Files:** `FeedViewModel.kt`, `FeedScreen.kt`, `PostItem.kt`.
*   **MVVM Pattern:**
    *   **View (Compose):** Observes `StateFlow` from the ViewModel and renders the UI.
    *   **ViewModel:** Requests data from the Repository and processes it into a `FeedUiState`. It survives configuration changes and manages the lifecycle of the data request.

---

## ❓ Why not just one?

### Why not "Just MVVM"?
If we used just MVVM, we would likely put the data mapping and multiple API calls directly inside the `ViewModel`. This leads to **"Massive ViewModels"** that are hard to read and test. Clean Architecture keeps the ViewModel focused solely on UI state management.

### Why not "Just Clean"?
Clean Architecture is a set of principles, not a UI pattern. Without MVVM (or a similar pattern like MVI), you lack a standardized way to sync your domain data with a reactive UI like Jetpack Compose.

---

## 🔄 Alternatives Considered

| Architecture | Why it wasn't chosen for this project |
| :--- | :--- |
| **MVI (Model-View-Intent)** | While great for complex state, it adds significant boilerplate (Reducers, Intents) which is overkill for a feed-based application. |
| **MVP (Model-View-Presenter)** | Outdated for modern Android. It relies on interfaces that create tight coupling between the Presenter and View, making it clumsy to use with declarative UI like Compose. |

## ✅ Summary of Benefits
1.  **Testability:** We can unit test the ViewModel and Mapper in isolation without any Android dependencies.
2.  **Scalability:** Adding a local database (Room) would only require changes in the Data Layer.
3.  **Readability:** The separation of concerns makes it easy for any developer to locate logic (e.g., "Where is the data mapped?" -> Domain Layer).
