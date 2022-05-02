# Game configuration

Top level configuration directory should include:

1. `root.json` file
2. `locations` subdirectory

e.g.

```
.
├── locations/
└── root.json
```

## root.json file

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
  "tag": "test-config-1-full",
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