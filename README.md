# Rent me Android challenge

## Changes done in the project

- Fixed the bugs found in the project.
- Kept the state of the ads recycler view when device is rotated.
- Used Kodein for dependency injection instead of the given Assemblers to unify the way everything is instanced.
- Added a new activity to navigate and check the clicked ad.
- Added the possibility to mark an ad as favorite.
- Added error management and pull-to-refresh.
- Added an example UI Test.
- Added an example Unit Test.

## Known errors

- Kodein instance is sometimes not injected properly on UI Tests. This should be fixed adding an override
to the binding of `AdsPresenter` on a custom `TestRunner` through the `ChallengeApplication` instance (it should be accessible from List package).

## Next Steps

- Proguard
- Add a disk persistence/cache to show data when phone is offline.
- Use Moshi instead of Gson because it's  more friendly with Kotlin nullables.
- Firebase Performance, Crashlytics and Analytics
- With More time I would re-do the project with an architecture I feel more comfy with(https://github.com/bq/mini-kotlin)
- Tablet UI
- I would move to reactive pattern to communicate between layers. It would also help to sync different views when backend changes appear.