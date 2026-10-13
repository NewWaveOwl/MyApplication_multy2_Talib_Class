This is a Kotlin Multiplatform project targeting Android, iOS and Wasm.

### ViewModel exercises: coverage of the supplied presentation

All coding exercises in the supplied `AppDev2_Day_20and21_ViewModels_rev.pptx`
are implemented. This copy contains 45 slides, so there is no slide 46 to inspect.
Sprint planning/groupwork and optional external codelabs are not app features.

| Slides | Implementation |
| --- | --- |
| 13-18 | Lifecycle dependencies, `MySimpleViewModel`, private mutable counter exposed as `State<Int>`, multiplatform `viewModel { ... }` initializer, and Increment button. |
| 19-26 | Observable string lists, add/remove actions, Kotlin explicit backing fields, and reusable `DisplayChangingList` receiving only data/callbacks. `MySimpleViewModel2` changes its counter with list edits. Missing-item removals do not decrement it. Lists are bounded to 300 dp and can scroll. |
| 27 | `MySimpleViewModel2.init` copies optional initial items and initializes the count once per instance. |
| 29-31 | Home contains two `MySimpleScreen2a` instances with different stable ViewModel keys, preserving the independent counter/list demonstration. |
| 32-39 | Navigation store ownership, saved-state decorator first, standard ViewModel decorator, and same-route sharing until the last entry is removed. |
| 35, 40 | Serializable `MySimpleScreenKey`/`MySimpleScreen2Key`, serializer registrations, entry providers, bottom navigation, and both cleanup/retention modes. Screens use no manual ViewModel keys. |
| 42-43 | `MySimpleScreen3`, `MySimpleViewModel3(seed)`, serializable `MySimpleScreen3Key(seed)`, serializer/provider registration, Seed 10/20 navigation items and matching selection highlights. |
| 44-45 | Existing common lifecycle/ViewModel dependencies and shared platform-safe initializers. These slides contain background reading rather than additional app exercises. |

### Compare cleanup and retention (slides 40 and 42)

`App(clearViewModelsOnPop: Boolean = false)` is left in the retained-state mode
requested at the end of the exercise. Change its default to `true`, or call
`App(clearViewModelsOnPop = true)`, to run the standard cleanup comparison.
Restart the app after changing modes; these demonstrations retain memory state,
not state after process death or browser reload.

Both modes pass the saved-state decorator first and the ViewModel decorator
second to `NavDisplay`, as shown in the slides and the video's navigation example.

- **Cleanup (`true`):** uses `rememberViewModelStoreNavEntryDecorator()`. Entries
  with equal route keys share their model. Removing the last entry for a route
  clears its store. Reopening starts at 0 for My Simple/My Simple 2, or at the
  route's seed (10 or 20) for My Simple 3.
- **Retention (`false`):** uses `rememberRetainedViewModelStoreNavEntryDecorator()`.
  It retains a store per route content key after popping, so reopening restores
  that route's counter/list. Seed 10 and Seed 20 remain independent. Retained
  stores belong to a lifecycle-managed provider and clear with that provider.

**Lifecycle 2.11 compatibility:** the slide's literal
`rememberViewModelStoreNavEntryDecorator { false }` overload is deprecated and
its callback is ignored by the installed library: it still clears on pop.
The small retained decorator implements the exercise using supported
`rememberViewModelStoreProvider` and `rememberViewModelStoreOwner` APIs and
intentionally omits per-pop store cleanup. It is not a second root-wide model
shared across every seed; isolation follows route content keys, matching the
seed exercise. No dependency downgrade is needed.

### Screens and navigation

The bottom bar has Home, About Us, Contact, My Simple, My Simple 2, Seed 10,
and Seed 20. Tapping a simple-screen item can push another occurrence of the
same route. The top Back button pops one entry. The Home action pushes Home;
to remove entries for the cleanup experiment, use Back until only Home remains.

My Simple's Increment counter is independent of its list (the first exercise).
My Simple 2's counter tracks its list size. My Simple 3 starts with an empty list
and the selected seed as its count; adding an item increments that count and
removing it decrements it. The screen takes `seed` from its route and provides it
to `viewModel { MySimpleViewModel3(seed) }`, without a manual ViewModel key.

Manual comparison:

1. Open My Simple, increment, visit My Simple 2, then revisit My Simple. The
   original counter remains while an occurrence of its route is on the stack.
2. Pop all entries back to Home and reopen each. Cleanup mode resets them;
   retention mode keeps their values and lists.
3. Open Seed 10 and add two items (count 12). Open Seed 20 and add one (count 21).
   Revisit Seed 10: it remains 12 with its own list and the correct highlighted tab.
4. Pop all entries and revisit both seeds. Cleanup mode returns to 10 and 20;
   retention mode restores 12 and 21. Rotate and verify both route and state persist.

Reference material checked:

- [Class video's published navigation source](https://github.com/philipplackner/Nav3Guide/blob/1-nav3-basics/composeApp/src/commonMain/kotlin/com/plcoding/nav3_guide/navigation/NavigationRoot.kt).
- [Compose Multiplatform ViewModel scoping](https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html#viewmodel-scoping-with-navigation-3).
- [Compose Multiplatform Navigation 3](https://kotlinlang.org/docs/multiplatform/compose-navigation-3.html).
- Installed Lifecycle 2.11 source confirms the ignored legacy callback. YouTube
  returned an empty caption response, so the video review used its published code.

### MVVM and shared layout

- **Model:** immutable `Person` data and centralized `SamplePeople` defaults in
  `model/Person.kt`, independent of UI and navigation.
- **ViewModel:** `AboutViewModel`, `ContactViewModel`, `MySimpleViewModel`, `MySimpleViewModel2`, and `MySimpleViewModel3`
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
reopen it from Home to verify the behavior selected by clearViewModelsOnPop. iOS builds require macOS/Xcode.

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
