This is a Kotlin Multiplatform project targeting Android, iOS.

### ViewModel exercises through presentation slide 22

From Home, choose **Go to ViewModel Demo**. **Increment** updates the retained
counter. **Add item** generates a string, and tapping an item removes it. The
counter and list are separate exercises: list edits do not change the counter.

- Slides 13–18: lifecycle dependencies, `viewmodels/MySimpleViewModel`, private
  mutable counter exposed as `State<Int>`, and the `viewModel { ... }` factory.
- Slides 19–22: observable string list, `add`/`remove` actions, and reusable
  `DisplayChangingList` receiving only a list and callbacks. The counter uses a
  traditional backing property; the list demonstrates Kotlin 2.4 explicit backing
  fields from slide 21.

Navigation 3 entry decorators retain each demo's model across recomposition,
Android rotation, and navigation away while its entry remains on the back stack.
Use the top Back button to return to a retained demo after opening another screen.
Popping the demo clears its model; opening a new demo starts at zero with an empty
list. ViewModels do not persist through process death or a browser reload.

### MVVM and shared layout

- **Model:** immutable `Person` data and centralized `SamplePeople` defaults in
  `model/Person.kt`, independent of UI and navigation.
- **ViewModel:** `AboutViewModel`, `ContactViewModel`, and `MySimpleViewModel`
  own screen data/state. Route data is immutable; the demo exposes observable
  counter/list state. Models never hold composables, navigation hosts or UI objects.
- **View:** screen composables bind ViewModels at the screen boundary, then pass
  values and callbacks to reusable content. Home is a stateless navigation menu
  and does not need an empty ViewModel.

`App` owns navigation and hoists `MainLayout` above `NavDisplay` as shown in Day 18.
The shared bars accept state/callbacks, retain the existing Home/About/Contact and
Home FAB actions, and highlight the current destination. `ScreenKey` is a sealed
route contract (Day 19) with localized title metadata. Route parameters and the
non-Android serialization configuration are preserved.

The original SVG icon shapes have Android-compatible vector XML equivalents;
the bars use those resources to avoid Android's unsupported-SVG launch crash.

The shared module targets Android, iOS and Wasm. There is no desktop `jvmMain`
target, so the desktop-only `kotlinx-coroutines-swing` dependency from slide 13
is not needed. Existing screens, resources, and platform entry points are kept.

Build and automated checks:

```powershell
.\gradlew.bat :androidApp:assembleDebug :shared:testAndroidHostTest :shared:compileKotlinWasmJs :shared:compileTestKotlinWasmJs
```

Manual Android check: increment several times, add items, remove one, rotate the
device, and verify both states remain. Navigate to About or Contact with the
bottom bar, then use Back and verify the demo's state remains. Pop the demo and
reopen it from Home to verify it starts fresh. iOS builds require macOS/Xcode.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
