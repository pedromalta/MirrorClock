# MirrorClock Project Rules

## Overview

MirrorClock is a high-contrast Android clock app designed to run behind a one-way mirror on legacy tablets (Android 4.4.4+). It uses pure XML Views (no Compose), MVVM architecture, and Kotlin Coroutines for real-time updates.

## Architecture

- **Pattern**: MVVM — `MainActivity` (View) + `ClockViewModel` (ViewModel)
- **No Model layer** currently exists; business logic lives in the ViewModel
- **State**: `LiveData<Long>` exposes epoch milliseconds; UI derives formatted strings
- **UI Updates**: `viewModels()` delegate for ViewModel injection; `observe()` drives UI

## Code Conventions

### Language & Style
- **Language**: Kotlin with `official` code style (`gradle.properties`)
- **Max line length**: 120 characters (detekt rule)
- **No comments** in code unless explicitly requested

### File Structure
```
app/src/main/java/net/pedromalta/mirrorclock/
├── MainActivity.kt       # Entry point, ViewBinding, immersive mode setup
└── ClockViewModel.kt     # LiveData<Long> backed by coroutine ticker
```

### UI Rules
- **XML Views only** — Jetpack Compose is disabled (`compose = false`)
- **ViewBinding** required — no `findViewById`
- **Layouts**: `res/layout/activity_main.xml` (phone), tablet overrides in `res/layout-*/`
- **Dimensions**: Phone (`values/dimens.xml`), Tablet (`values-sw600dp/dimens.xml`)
- **Colors**: Pure black background (`#000000`), cyan (`#00E5FF`), amber (`#FFAB00`), pink (`#FF4081`)

### API Compatibility
- **Min SDK**: 19 (Android 4.4 KitKat)
- **Target SDK**: 37 (Android 15)
- **Dual API path** for immersive mode:
  - API 28+: `WindowInsetsControllerCompat`
  - API 19-27: Legacy `systemUiVisibility` flags
- Avoid API 21+ features without fallback checks

### Coroutines & Threading
- Use `viewModelScope.launch` for background work
- Smart sync: align tick to next second boundary (`1000 - (now % 1000)`)
- Never block the main thread

## Build & Configuration

### Gradle Setup
- **Kotlin DSL** (`build.gradle.kts`) with version catalog (`gradle/libs.versions.toml`)
- **Plugins**: Android Application, KSP, Kotlin Serialization, Detekt
- **Configuration cache**: enabled (`true`)
- **Release**: R8/ProGuard minification + resource shrinking enabled

### Version Catalog
- Only active libraries: `core-ktx`, `lifecycle`, `appcompat`, `constraintlayout`, `material`, `Coroutines`, `JUnit`, `Espresso`
- Before adding new libraries, check if they're truly needed — the catalog was previously cleaned of unused entries (Compose, Retrofit, CameraX, Room, etc.)

## Detekt Rules (Zero Tolerance)

- **`maxIssues: 0`** — all detekt warnings are errors
- **Baseline**: Empty (`detekt-baseline.xml` contains only `<baseline/>`)
- **Complexity limits**:
  - `LongMethod`: > 60 lines
  - `LongParameterList`: > 6 params
  - `TooManyFunctions`: > 11
  - `ReturnCount`: max 2 (excluding `equals`)
- **Naming**: `FunctionName` is disabled (test functions use backtick BDD notation: `` `given X when Y then Z` ``)

## Android Manifest

- Single activity, exported, landscape-locked
- Launcher intent filter with main action + category
- Full-screen: no action bar, immersive mode, always-on-screen

## Hardware Context

- Designed for tablets behind one-way mirrors
- Black masking around screen edges to prevent light bleed
- 24/7 power via thin USB cable
- Legacy KitKat tablets are the target hardware

## Adding New Features

1. Check if the feature requires APIs below 19 — if so, provide fallbacks
2. Follow MVVM: keep UI logic in `MainActivity`, state/logic in `ClockViewModel`
3. Use existing ViewBinding pattern — do not introduce Compose
4. Respect detekt zero-tolerance — run `./gradlew detekt` before committing
5. Keep layouts high-contrast with pure black backgrounds
6. Test on API 19 if possible, or verify API compatibility manually

## Testing

- JUnit, AndroidX Test, Espresso, Coroutines Test, Arch Core Testing declared
- No existing tests found — new tests should follow standard Android instrumentation + unit test patterns
