# Unit Testing Strategy - Version 3.0 (Multi-Module)

This document outlines the unit testing approach for the multi-module architecture. Testing is now distributed across modules to ensure strict boundary validation and isolation.

## 🛠 Testing Stack
- **JUnit 4**: The core testing framework.
- **Mockito & Mockito-Kotlin**: For mocking dependencies and verifying interactions.
- **Kotlinx-Coroutines-Test**: For testing suspending functions and handling flows.
- **MockedStatic**: Used to mock static Android platform methods like `android.util.Log` and `android.util.Base64`.

---

## 📊 Coverage Areas by Module

### 1. `:core:domain` (Business Logic)
- **`GetPostsUseCaseTest`**: Verifies the orchestration logic (Checking login state before fetching posts).
- **`LoginUseCaseTest`**: Ensures the login request is correctly passed to the repository.

### 2. `:core:data` (Data Orchestration)
- **`RepositoryTest`**: 
    - Verifies the "Single Source of Truth" logic (Syncing network to DB).
    - Tests error mapping for `HttpException` and `IOException`.
    - Validates token management interactions with `TokenManager`.
- **`MappingTest`**: Ensures complex API-to-Domain mapping and interleaving logic is correct.

### 3. `:core:database` (Persistence)
- **`DatabaseMappingTest`**: Validates mapping between `PostEntity` and `Post` domain models, including the `sortOrder` preservation.

### 4. `:feature:feed` (Presentation)
- **`FeedViewModelTest`**:
    - **Reactive Observation**: Verifies that the ViewModel correctly collects the Paging stream.
    - **Refresh Logic**: Validates that `syncPosts()` is triggered on refresh.
    - **Error Handling**: Ensures exceptions from the data layer are mapped to user-friendly UI states.

---

## 🚀 How to Run Tests

Since the project is modular, you can run tests for the entire project or for specific modules:

### Run All Tests
```bash
./gradlew test
```

### Run Module-Specific Tests
```bash
./gradlew :core:data:test
./gradlew :core:domain:test
./gradlew :feature:feed:test
```

---

## 💡 Key Testing Decisions in V3.0

1. **Modular Isolation**: Each module's tests focus strictly on its responsibilities. For example, `:core:domain` tests only business logic, never touching database or network details.
2. **Paging Testing**: ViewModel tests use `collectAsLazyPagingItems` or similar flow collectors to verify that data streams are handled efficiently.
3. **Reactive Flows**: Tests extensively use `runTest` and `Turbine` (optional) style assertions to handle the `Flow`-based architecture.
