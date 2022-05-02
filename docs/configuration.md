# Game configuration

## Table of contents

1. [Root directory](#root-directory)
2. [`root.json` file](#rootjson-file)
3. [`locations` directory](#locations-directory)
4. [Location configuration](#location-configuration)
5. [`GameObject` configuration](#gameobject-configuration)
6. [Example of full configuration structure](#example-of-full-configuration-structure)
7. [Example configurations](#example-configurations)

## Root directory

Top level configuration directory should contain:

1. `root.json` file
2. `locations` subdirectory

e.g.

```
.
├── locations/
└── root.json
```

## `root.json` file

File in `json` format. Specifies a single object that must specify
following props:

* `tag` - It represents the game's name.
* `locations` - List of location tags. Only locations mentioned here are
    loaded by the engine. However, this is a subject to change. In the future
    we want to load all the locations specified in `locations` directory w/o the need
    to specify their tags here.
* `player` - Player object specification. It follows the `GameObject` specification
    rules. (TODO: add hyperlink to that section)
* `rootLocation` - `tag` of first-to-be-displayed location.

e.g.
```json
{
  "tag": "docs-game-configuration",
  "locationTags": [
    "location-1",
    "location-2"
  ],
  "rootLocation": "location-1",
  "player": {
    "tag": "player",
    "position": { "row": 4, "col": 5 },
    "type": "player",
    "assetPath": "assets/stone.png",
    "location": "location-1"
  }
}
```

## `locations` directory

For each location tag specified in the `locationTags` prop of object configured in `root.json` file
`locations` directory should contain a subdirectory. The name of the subdirectory
must exactly match location's tag.

Each subdirectory contains detailed location configuration (explained below).

In the future, there will be no need to define locations tags in `root.json`. 
Instead, names of the subdirectories will be treated as location tags & 
location object will be created, by the engine for every subdirectory that contains
correct location configuration (right now, the engine creates it only for the 
locations mentioned in `root.json`).


e.g., there are two location configured in the game: `location-1`, `location-2`:

```
locations
├── location-1/
└── location-2/
```

## location configuration

Configuration for each location must be contained inside `locations/<location-tag>`
subdirectory.

It consists of:

* json file named according to the schema: `<location-tag>.json` \
    This is the main configuration point for location.
* *(optional)* `objects` subdirectory \
    You can place `GameObjects` configurations inside if they get long & you want
    your configuration files to be more readable. \
    It is possible to specify part of the object in location config & part inside `object` directory.
    For any prop that is specified in both places, the value from `objects` directory is taken.

`<location-tag>.json` file contains:

* `tag` - Location tag. Must be unique across all locations in the game. \
    It is possible that in future versions it will be removed and location 
    tag will be deduced from directory name.
* `objects` - List of `GameObject` configurations. 
* `backgroundPath` - Path to the background asset. As any path, it must be eiter absolute or relative to the engine's source root.

In the future, we plan to drop necessity of specifying objects in location config. Instead,
all the objects configured in `objects` directory will be loaded.

location config example:

```json
{
  "tag": "location-1",
  "objects": [
    { "tag": "object-1", "position": { "row": 0, "col": 5 }, "type": "collectible", "assetPath": "assets/someDude.png" },
    { "tag": "object-2", "position": { "row": 1, "col": 3 }, "type": "dialog", "assetPath": "assets/someDude.png" }
  ],
  "backgroundPath": "assets/map.png"
}
```

location directory example:

```
location-1
├── location-1.json
└── objects/
```

## `GameObject` configuration

`GameObject` configuration consists of:

* `tag` - Unique id for the object across all game objects.
* `position` - Position of the object in the location. See examples.
* `assetPath` - Path to the asset representing this game object. As any path, it must be eiter absolute or relative to the engine's source root.
* *(optional)* `onPress` - Configuration for the action to be triggered when user clicks on the object. See [action configuration](TODO!!).
* *(optional)* `onApproach` - Configuration for the action to be triggered when user comes close enough to the object. See [action configuration](TODO!!!).


## Example of full configuration structure

```json
docs-game-configuration
├── locations/
│   ├── location-1/
│   │   ├── location-1.json
│   │   └── objects/
│   │       ├── object-1.json
│   │       └── object-2.json
│   └── location-2/
│       ├── location-2.json
│       └── objects/
│           ├── object-1.json
│           └── object-3.json
└── root.json
```

## Example configurations

You can also see complete configurations in [configurations directory](../configurations).


