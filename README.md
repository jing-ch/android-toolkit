# Android Toolkit — A Multi-Feature App in Java

> A multi-feature Android app (Java) demonstrating background concurrency, lifecycle-aware location tracking, and dynamic list UIs — built as a hands-on tour of core Android development.

![Language](https://img.shields.io/badge/language-Java-or008b.svg)
![Platform](https://img.shields.io/badge/platform-Android-3ddc84.svg)
![minSDK](https://img.shields.io/badge/minSDK-27-blue.svg)
![targetSDK](https://img.shields.io/badge/targetSDK-36-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

---

## Screenshots

> _I will add when I find time :P_

---

## Overview

This Android application is a single launcher hub that opens five self-contained mini-features, each exploring a different area of the Android SDK — threading, location services, dynamic lists, and UI state management.

It began as individual coursework for Northeastern University's mobile development course and has since been cleaned up and restructured into a portfolio piece. The emphasis throughout is on doing things *correctly*: handling the activity lifecycle, surviving configuration changes, doing heavy work off the main thread, and respecting runtime permissions.

---

## Features

| Feature | What it demonstrates |
| --- | --- |
| **Prime Finder** | Two background-threading approaches for an unbounded compute task, with `Handler`/`Looper` posting results back to the UI thread, plus start/stop concurrency controls. |
| **Location Tracker** | Live location via `FusedLocationProviderClient`, runtime permission handling, cumulative distance tracking with a movement threshold to filter GPS noise, lifecycle-aware updates, and rotation survival. |
| **Link Collector** | A `RecyclerView`-backed list with a custom adapter/ViewHolder, add-via-dialog, swipe-to-delete with undo, and state persistence across configuration changes. |
| **Quic Calc** | A lightweight calculator with expression display and clear/evaluate logic. |
| **About Me** | A simple static profile screen. |

---

## Tech Stack

- **Language:** Java
- **UI:** Android Views, Material Components, ConstraintLayout, `RecyclerView`
- **Location:** Google Play Services — `play-services-location` (`FusedLocationProviderClient`)
- **Concurrency:** Java threads + `Handler`/`Looper`
- **Build:** Gradle (Kotlin DSL) with version catalog (`libs.versions.toml`)
- **Testing:** JUnit (unit) and Espresso (instrumented) scaffolding
- **SDK:** `minSdk 27`, `targetSdk 36`, Java 11 source/target compatibility

---

## Project Structure

The codebase uses a **package-per-feature** layout — each feature is fully isolated, making it easy to navigate and reason about.

```
app/src/main/java/edu/northeastern/numad26sp_jinghanchen/
├── main/            # MainActivity — launcher hub, routes to each feature
├── prime_finder/    # Background prime computation with start/stop controls
├── location/        # Live location + distance tracking
├── link_collector/  # RecyclerView list with add / swipe-to-delete / undo
│   ├── Person.java          # Model
│   ├── PersonAdapter.java   # RecyclerView adapter
│   └── PersonViewHolder.java
├── quic_calc/       # Calculator
└── about_me/        # Static profile screen
```

---

## Technical Highlights

A few implementation details worth calling out:

- **Off-main-thread computation.** Prime Finder runs its search on worker threads and marshals incremental results back to the UI via `Handler`/`Looper`, keeping the interface responsive while the work runs and stoppable on demand.
- **GPS-noise filtering.** The Location Tracker only accumulates distance once movement exceeds a tuned threshold, so reported distance doesn't drift upward from stationary GPS jitter.
- **Lifecycle correctness.** Location updates are registered and unregistered in step with the activity lifecycle so the app doesn't waste battery or leak callbacks when not visible.
- **Surviving configuration changes.** Feature state (collected links, tracked distance) is preserved across screen rotation via `onSaveInstanceState` / restore.
- **Intentional back navigation.** Features that hold unsaved state use `OnBackPressedCallback` to confirm before discarding, rather than silently losing the user's work.
- **Undo-friendly UX.** Swipe-to-delete in Link Collector uses `ItemTouchHelper` paired with a Snackbar undo, following Material interaction patterns.

---

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest stable)
- JDK 11
- Android SDK Platform 36

### Run

```bash
git clone https://github.com/jing-ch/individualassignments-jing-ch.git
cd individualassignments-jing-ch
```

1. Open the project in Android Studio (open the repository root).
2. Let Gradle sync and download dependencies.
3. Select an emulator or connected device (API 27+) and click **Run**.

> The Location Tracker requests location permission at runtime — grant it when prompted to see live updates. For emulators, set a mock location via **Extended Controls → Location**.

---

## Testing

```bash
# Unit tests (JVM)
./gradlew test

# Instrumented tests (requires a connected device/emulator)
./gradlew connectedAndroidTest
```

---

## Future Improvements

Ideas for evolving this into a more production-shaped app:

- Adopt **MVVM** with `ViewModel` + `LiveData`/`StateFlow` to separate UI from logic.
- Persist Link Collector data with **Room** instead of in-memory + instance state.
- Replace raw threads with **coroutines** / `WorkManager` for structured concurrency.
- Add **map visualization** (Google Maps SDK) for the location route.
- Expand **test coverage** with unit tests for the distance/threshold and prime logic.
- Migrate the UI toward **Jetpack Compose**.

---

## License

Released under the [MIT License](LICENSE).

## Author

**Jinghan Chen** · [GitHub @jing-ch](https://github.com/jing-ch)
