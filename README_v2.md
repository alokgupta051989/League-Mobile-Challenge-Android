# League Android Code Challenge - Version 2.0 (Scalable Architecture)

This version of the application is a major architectural evolution from Version 1.0, focusing on **scalability, performance, and offline-first capabilities**. It implements industry-standard patterns that allow large teams of developers to collaborate effectively.

## 🚀 Version 2.0 Enhancements

### 1. Advanced Architecture: Full Clean Architecture + Hilt
We've moved beyond basic MVVM by strictly enforcing Clean Architecture boundaries and automating dependency management.
- **Dependency Injection (Hilt)**: Replaced manual factory logic with Hilt. This automates the management of scopes (Singleton, Activity, ViewModel) and makes the project significantly easier to test and scale.
- **Domain Layer Interactors (Use Cases)**: Introduced a Use Case layer (`GetPostsUseCase`). Business logic is now isolated from the ViewModel, promoting reusability across different UI components or background services.

### 2. Offline-First Capability (Room Database)
The app now supports **offline caching** to provide a seamless user experience even without an active internet connection.
- **Single Source of Truth**: The UI always observes the local database. The network is used only to update the cache.
- **Room Persistence**: Implemented `PostEntity`, `PostDao`, and `AppDatabase` to persist social feed data locally.

### 3. Scalable Data Fetching (Paging 3 - Infrastructure Ready)
We've integrated the **Paging 3 library** infrastructure to support large datasets efficiently.
- **Pagination Support**: The architecture is now configured to handle data in chunks, reducing memory overhead and improving UI responsiveness for infinite scrolling.

### 4. Technical Stack Evolution
- **Dagger Hilt**: For robust Dependency Injection.
- **Room Persistence**: For local SQLite caching.
- **Paging 3**: For efficient list management and pagination.
- **Jetpack Compose + Material 3**: Continued use of modern UI toolkit with improved state management.

---

## 📂 Project Structure

```text
life.league.challenge.kotlin
├── api/             # Retrofit interfaces & Data DTOs
├── db/              # Room Database, DAOs, and Entities
├── di/              # Hilt Modules (Network, Database)
├── domain/          # Use Cases (Business Logic)
├── model/           # Clean Domain Models & Mappers
├── ui/              # Compose Screens & Hilt ViewModels
└── main/            # MainActivity (Hilt Android Entry Point)
```

---

## 🛠 Build & Run Instructions
1. Open the project in **Android Studio**.
2. Sync Gradle (this will trigger Hilt's annotation processing).
3. Run the `:kotlin_app` module.

---

## 🧪 Documentation History
- [View Version 1.0 Documentation (Basic MVVM)](README_v1.md)
- [View Technical Architecture Deep Dive](ARCHITECTURE.md)
