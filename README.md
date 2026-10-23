This is a Kotlin Multiplatform project targeting Android, iOS and Wasm.

### Navigation guide

The bottom bar contains **Home**, **About Us**, and **Contact**.
Tap the **hamburger menu in the top-right corner** from any screen to open:

- **Screen previews:** compare Screen 1, Screen 2, Seed 10, and Seed 20 interactively.
- **Flow demo:** run the two independent collectors and see their latest values.
- **My Simple:** open Screen 1's counter and list exercise.
- **My Simple 2:** open Screen 2's list-linked counter exercise.
- **Seed 10** or **Seed 20:** open Screen 3 with the selected starting count.

The current menu destination appears in bold. Choosing an item closes the menu
and opens that screen. Tap outside the menu or press Back to dismiss it. You can
choose the same destination again to push another occurrence for the navigation
exercises. The top-left Back arrow removes one entry from the back stack.

Home also keeps its **Flow demo**, **Screen previews**, and ViewModel demo buttons.
The **Home** floating button opens Home; use Back repeatedly to pop entries when
checking whether ViewModels reset or retain their state.

### Day 22/23: Flow exercises through slide 22

Choose **Flow demo** from the top-right menu or Home. **Collector 1** and **Collector 2** independently
collect the same cold `Flow<Int>`. Each run emits 1, 2 and 3, with `delay(500)`
between values, without blocking the UI. Each panel shows **No value yet** until
it receives a value, then displays its own **Latest value**. Starting one collector
does not start or update the other. A finished collector can be started again.

- Slides 14-18: `ColdFlows.kt` defines the cold `flow { emit(...) }` producer and
  the `flowOf`/collection `asFlow` alternatives. Each collection starts afresh.
- Slides 20-21: `ColdFlowDemo` uses `rememberCoroutineScope()` and independent
  `launch { collect { ... } }` calls from the two collector buttons. Leaving the
  screen cancels its active collections.
- Slide 22: both latest values use `rememberSaveable { mutableStateOf<Int?>(null) }`
  and appear as Text in the UI. Rotation preserves the latest displayed values;
  collecting again is an explicit button action.
- Coroutines Core is an explicit common dependency. Host tests exercise concurrent
  collection, early completion/cancellation, recollection and the fixed builders.

This was the original stopping point. Later exercises now include StateFlow,
object/list state, and the Part 9 SharedFlow comparison described below.
The earlier features/benefits team activity is classroom groupwork rather than
an app implementation or an authorization to submit anything to Teams.

### Part 9: SharedFlow (Days 22–23, slides 66–72)

Open **Screen previews → Exercise 9: SharedFlow**. Use **Open full screen** for
the regular navigation destination. Press **Emit event** rapidly and compare
the click count with received event labels. The collector uses `collect` with
a 500 ms delay before appending each event to its local observable list.

| Pattern | replay | extraBufferCapacity | onBufferOverflow |
| --- | ---: | ---: | --- |
| Default | 0 | 0 | SUSPEND |
| Buffered | 0 | 1 | SUSPEND |
| Drop oldest | 0 | 1 | DROP_OLDEST |
| Replay latest | 1 | 0 | DROP_OLDEST |

The two suspend patterns eventually deliver every emitted event while the
collector stays active. Drop-oldest patterns can skip queued events under rapid
clicking. Replay retains the last event for a returning collector. Changing
patterns resets the displayed click count/list; each pattern's ViewModel counter
remains while its owner is retained. Leaving composition cancels collection.
Without subscribers, only replay values are retained: an extra buffer does not
save events for an absent screen. This qualifies the slides' lossless-delivery
statement. See the [official SharedFlow contract](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/).

`SharedFlowViewModelTest` verifies ordered rapid emissions for both suspend
patterns, dropped queued events for a slow collector, absent-subscriber loss,
and replay to a new collector using virtual time. Test tooling is confined to
`commonTest`; no production dependency version was changed.

### Day 25: Coroutines, slides 17–21

- Slide 17: Android calls `firstCoroutine()` just before `setContent()`. Logcat
  prints `Hello`, then `World!` one second later. This intentionally blocks
  startup for the lecture's `runBlocking` experiment; it is outside Compose.
- Slides 18–21: open **Screen previews → Day 25: Coroutines**. **Click me**
  launches the Hello/World example in `rememberCoroutineScope()`.
  `UserProfile(userId)` calls the mock suspend function `fetchUserData` inside
  `LaunchedEffect(userId)`. **Change user** switches IDs; a pending fetch is
  cancelled and restarted. Leaving composition cancels its effects/scope.

The mock fetch only delays and returns a label. These examples add no network,
repository, or ViewModel layer.

### Screen previews: app feature

Choose **Screen previews** from the top-right menu or Home. Select **Screen 1**, **Screen 2**,
**Seed 10**, or **Seed 20** to interact with the corresponding real screen view.
The seed previews start at 10 and 20; the other views start at 0. Add/remove items
or increment the first screen's counter, then switch views to compare their states.
**Open full screen** navigates to the selected screen's regular destination.

Each preview selection has its own ViewModel owner inside a shared preview
provider. This keeps the two seed previews independent without changing the
screen implementations, and preserves each preview while switching selections
or rotating. Preview stores are released when the preview session leaves the
composition. Regular destinations keep their existing navigation state rules.
The nested owner provider also demonstrates the subtree override from slide 9.

User benefit: users can compare screen behavior and seed starting values in one
place, keeping their place as they switch between examples.

Manual checks: start each collector separately and together, let both reach 3,
then restart one and verify the other stays at 3. Rotate with values displayed.
In Screen previews, increment Screen 1, edit Screen 2, add an item to Seed 10 and
Seed 20, and switch back to confirm their states remain separate. Open a selected
screen full-size and use Back to return to the preview feature.

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
| 35, 40 | Serializable `MySimpleScreenKey`/`MySimpleScreen2Key`, serializer registrations, entry providers, menu navigation, and both cleanup/retention modes. Screens use no manual ViewModel keys. |
| 42-43 | `MySimpleScreen3`, `MySimpleViewModel3(seed)`, serializable `MySimpleScreen3Key(seed)`, serializer/provider registration, Seed 10/20 menu items and matching selection highlights. |
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

The bottom bar has Home, About Us, and Contact. The top-right hamburger menu
contains the simple screens, both seeds, Flow demo, and Screen previews. Choosing
a simple screen or seed from the menu can push another occurrence of the same
route. The top Back button pops one entry. The Home action pushes Home; to remove
entries for the cleanup experiment, use Back until only Home remains.

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
   Revisit Seed 10: it remains 12 with its own list and the correct highlighted menu item.
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
