This is a Kotlin Multiplatform project targeting Android, iOS.

### ViewModel exercises through presentation slide 31

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

### List counter and keyed instances (slides 23-31)

Home now embeds two `MySimpleScreen2` instances using keys `1` and `2`. Add an item
in either demo: only that demo's list and counter change. Tap an item to remove it
and decrement that demo's counter. Both instances use the same Home navigation
entry's ViewModelStoreOwner, so their different factory keys provide isolation.
Passing the same key to both instances instead demonstrates shared state.

- Slides 23-26: `MySimpleViewModel2` updates the count on add/remove and exposes
  read-only observable state. `MySimpleScreen2` passes only list data and actions
  to `DisplayChangingList`, whose list height is bounded to 300 dp for scrolling.
  Removing a missing item leaves the count unchanged.
- Slide 27: the ViewModel's `init` block copies optional initial items and sets
  the starting count once per instance. Home starts with empty lists and zeroes.
- Slides 29-31: `MySimpleScreen2(key: Int = 0, ...)` supplies `viewModel(key = "$key")`
  with a multiplatform initializer. Home supplies two distinct, stable keys.

The original **Go to ViewModel Demo** counter exercise remains available. Its
manual increment counter stays independent from its list, as in slides 14-22.
The Home demos use the later list-coupled counter exercise. No steps beyond
slide 31 or optional external codelabs are required for these exercises.

Manual check: on Home, add two items in the first demo and one in the second.
Remove one from the first; both counters should read 1 with different lists.
Rotate the Android device, or navigate to About then use Back: each demo should
retain its own state. Scroll Home and add enough items to scroll each bounded list.

### MVVM and shared layout

- **Model:** immutable `Person` data and centralized `SamplePeople` defaults in
  `model/Person.kt`, independent of UI and navigation.
- **ViewModel:** `AboutViewModel`, `ContactViewModel`, `MySimpleViewModel`, and `MySimpleViewModel2`
  own screen data/state. Route data is immutable; the demo exposes observable
  counter/list state. Models never hold composables, navigation hosts or UI objects.
- **View:** screen composables bind ViewModels at the screen boundary, then pass
  values and callbacks to reusable content. Home receives navigation callbacks and hosts the two keyed screen demos.

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
