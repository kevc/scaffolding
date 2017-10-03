# scaffolding

## Structure

`android`

The Android specific UI bindings, along with the api datasource implementations are in the `android` module.

`core`

`core` holds all of the core entities that are passed around between layers.

`domain`

The `domain` module defines datasource apis, and holds the interactor class, which interacts at the boundary between the client and the web.

`presentation`

The `presentation` module holds the api for what the view (Android) derives its data from. It's a simple redux implementation
