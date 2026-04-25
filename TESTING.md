# Unit Testing Strategy - Version 3.1 (Multi-Module)

This document outlines the unit testing approach for the multi-module architecture. Testing is distributed across modules to ensure strict boundary validation and isolation.

## 🛠 Testing Stack
- **JUnit 4**: The core testing framework.
- **Mockito & Mockito-Kotlin**: For mocking dependencies and verifying interactions.
- **Kotlinx-Coroutines-Test**: For testing suspending functions and handling flows.
- **MockedStatic**: Used to mock static Android platform methods like `android.util.Log` and `android.util.Base64`.

---

## 📊 Coverage Areas by Module

### 1. `:core:domain` (Business Logic)
- **`GetPostsUseCaseTest`**: Verifies that `GetPostsUseCase` correctly retrieves the flow from the repository.
- **`LoginUseCaseTest`**: Ensures the login request is correctly passed to the repository.
- **`SyncPostsUseCaseTest`**: Verifies that the synchronization process is triggered in the repository.

### 2. `:core:network` (Network Infrastructure)
- **`AuthInterceptorTest`**: Validates that the `AuthInterceptor` correctly injects the `x-access-token` header when a token is available and remains silent when it is not.

### 3. `:core:data` (Data Orchestration)
- **`RepositoryTest`**: 
    - Verifies the "Single Source of Truth" logic (Syncing network to DB).
    - Tests error mapping for `HttpException` and `IOException`.
    - Validates token management interactions with `TokenManager`.
- **`MappingTest`**: Ensures complex API-to-Domain mapping and interleaving logic is correct.
- **`TokenManagerTest`**: Infrastructure for testing persistent storage (DataStore).

### 4. `:core:database` (Persistence)
- **`DatabaseMappingTest`**: Validates mapping between `PostEntity` and `Post` domain models, including the `sortOrder` preservation.

### 5. `:feature:feed` (Presentation)
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
./gradlew :core:network:test
./gradlew :core:data:test
./gradlew :core:domain:test
./gradlew :feature:feed:test
```

---

## 💡 Key Testing Decisions in V3.1

1. **Modular Isolation**: Each module's tests focus strictly on its responsibilities. For example, `:core:domain` tests only business logic, never touching database or network details.
2. **Interceptor Testing**: Unit tests for the network layer ensure that infrastructure concerns (like authentication) are handled correctly before hitting the wire.
3. **Reactive Flows**: Tests extensively use `runTest` and `first()` (or `Turbine`) to handle the `Flow`-based architecture in a deterministic way.

---

## 🚫 Excluded from Unit Testing

The following areas are intentionally excluded from unit testing for the reasons specified:

### 1. Simple Data Models (`:core:model`, `:core:network:model`)
- **Classes**: `Account`, `Post`, `PostDto`, `UserDto`.
- **Reason**: These are standard Kotlin `data class`es or POJOs used for data transport. They contain no business logic, transformations, or validation. Testing these would only verify the Kotlin compiler's `equals`, `hashCode`, and `toString` implementations, providing no value to the project's stability.

### 2. Dependency Injection Modules (`di` packages)
- **Reason**: Hilt modules are configuration files. Testing them would involve verifying that a specific class is provided by a specific function, which is better handled by compile-time checks (Hilt) or Integration/E2E tests that verify the app actually runs.

### 3. Repository Interfaces (`:core:domain:repository`)
- **Reason**: Interfaces contain no implementation. They are used as contracts for mocking in Use Case tests. The actual implementation is tested in the `:core:data` module (`RepositoryTest.kt`).

### 4. Boilerplate Retrofit Interfaces (`:core:network:api`)
- **Reason**: Standard Retrofit interfaces define endpoints via annotations. Testing them requires `MockWebServer` and adds significant overhead for verifying that Retrofit works as documented. In this project, we rely on `RepositoryTest` (Integration level) to verify that network calls are correctly mapped to domain models.
