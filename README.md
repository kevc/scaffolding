# scaffolding

![Clean Architecture](https://8thlight.com/blog/assets/posts/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

## Structure

`core`

Entities that are passed around between layers.

`domain`

Datasource apis (interfaces). Interactors also live here. Interactors are the single-use classes that operate at the boundaries of different layers.

`presentation`

Redux, basically. Redux is our chosen architecture for managing the data flow from users to the network, and from the network back to the user. https://medium.com/groupon-eng/grox-the-art-of-the-state-b5223f48d696

Our Redux implementation breaks apart the `Epic` (asynchronous behaviors) and `Reducer` (pure functions for updating state). This module also contains `Selectors`, which are the pure functions used to take `State` and transform it into what various frontends (e.g. Android) use.

`data`

API model classes, implementations of domain datasources, Retrofit `Service` interfaces, 

`android`

The Android specific UI bindings. Think `Activity`, `View`, etc.

## API

For now, this app uses the Getty Images API
