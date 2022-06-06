# Game configuration

## Table of contents

1. [Root directory](#root-directory)
2. [`root.json` file](#rootjson-file)
3. [`locations` directory](#locations-directory)
4. [Location configuration](#location-configuration)
5. [`GameObject` configuration](#gameobject-configuration)
6. [Action configuration](#action-configuration)
7. [Condition configuration](#condition-configuration)
8. [Example of full configuration structure](#example-of-full-configuration-structure)
9. [Example configurations](#example-configurations)

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
* `locations` - List of location tags. All the locations specified in `locations` are loaded, however this field is left
    for backward-compatibility.
* `player` - Player object specification. It follows the `GameObject` specification
    rules. See [game object configuration][#game-object-configuration]
* `rootLocation` - `tag` of first-to-be-displayed location.
* `textPopupBackground` - Url of a picture to be used as a background for all TextPopups
* `textPopupButton` - Url of a picture to be used as a button image for all TextPopups
* `textImagePopupBackground` -  Url of a picture to be used as a background for all TextImagePopups
* `textImagePopupButton` - Url of a picture to be used as a button image for all TextImagePopups
* `quizPopupBackground` - Url of a picture to be used as a background for all QuizPopups
* `inventoryPopupBackground` - Url of a picture to be used as a background for the Inventory
* `dialoguePopupBackground` - Url of a picture to be used as a background for all DialoguePopups
* `npcFrame` - Url of a picture to be used as a frame around a NPC image in the DialoguePopups

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
  },
  "textPopupBackground": "assets/popup-background.png",
  "textPopupButton": "assets/button-image.png",
  "textImagePopupBackground": "assets/popup-background-2.png",
  "textImagePopupButton": "assets/button-image-2.png",
  "quizPopupBackground": "assets/popup-background-3.png",
  "inventoryPopupBackground": "assets/popup-background.png",
  "dialoguePopupBackground": "assets/popup-background.png",
  "npcFrame": "assets/npc-frame.png"
}
```

## `locations` directory

For each location tag specified in the `locationTags` prop of object configured in `root.json` file
`locations` directory should contain a subdirectory. The name of the subdirectory
must exactly match location's tag.

Each subdirectory contains detailed location configuration (explained below).

Names of the subdirectories are treated as location tags & 
location object will be created, by the engine for every subdirectory that contains
correct location configuration .


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
* `objects` - List of `GameObject` configurations. This field was left for backward compat, but it also allows you to 
    configure small objects in-line w/o need to create separate config file for them.
* `backgroundPath` - Path to the background asset. As any path, it must be eiter absolute or relative to the engine's source root.

All the objects configured in `objects` directory are loaded. You don't have to specify them inside `<location-tag>.json` file.
Note however, that object tags must be unique (as for now, in the future we plan to introduce special prefix for tag, that will
allow for multiple objects with the same tag).

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
    Aliases: `asset-path`, `asset`.
* *(optional)* `onClick` - Configuration for the action to be triggered when user clicks on the object. See [action configuration](#action-configuration).
    Aliases: `onPress`, `on-press`, `on-click`.
* *(optional)* `onLeftClick` - Configuration for the action to be triggered when user left-clicks on the object. See [action configuration](#action-configuration).
    Aliases: `on-left-click`, `onLeftPress`, `on-left-press`.
* *(optional)* `onRightClick` - Configuration for the action to be triggered when user right-clicks on the object. see [action configuration](#action-configuration).
    Aliases: `on-left-click`, `onRightPress`, `on-right-press`.
* *(optional)* `onApproach` - Configuration for the action to be triggered when user comes close enough to the object. See [action configuration](#action-configuration).
    Aliases: `on-approach`.

## Action configuration

As for now we support 5 kinds of actions:

[//]: # (TODO: Add descriptions for these action types)

* `quiz`
* `game-end`
* `location-change`
* `show-description`
* `dialogue`

For each kind configuration differs a bit, because different information is required. Let's take a closer look at the
common part first. Each action consists of following properties

* `tag` - Action tag; this is required however it is not used for now. Action tag does not have to be unique for now.
* `type` - One of action types mentioned above.
* *(optional)* `condition` - Condition configuration. The action won't be executed until the condition is satisfied.
    Aliases: `requires`. See [condition configuration](#condition-configuration)
* *(optional)* `before` - Configuration of action to be triggered before the proper action is executed.
  e.g. you may want to change location before starting the dialogue.
* *(optional)* `after` - Configuration of action to be triggered after the proper action is executed, e.g. you may want
    give player some output or item. 

Let's look at specific action types:

* `quiz`:
  * `questions` - **List** of `Question` objects. Right now, **only one question is supported**, however you must provide this
    parameter as a list.
    `Question` object should be configured as follows:
      * `question` - The text to be displayed as a question.
      * `a` - Answer A.
      * `b` - Answer B.
      * `c` - Answer C.
      * `d` - Answer D.
      * `correct` - Single character denoting the correct answer.
  * `reward` - Number of points as a reward for choosing the right answer.
* `game-end`:
  * `description` - Additional text to be displayed.
* `location-change`:
  * `target-location` - Tag of the target location.
  * `target-position` - Initial position of the player in target location.
  
    Aliases: `targetLocation`, `target`
* `show-description`:
  * `assetPath` - Path to the image with background for the dialogue window. As any path, it must be eiter absolute or relative to the engine's source root.
    Aliases: `asset-path`, `asset`.
  * `description` - Additional information about the object.
* `dialogue`:
  * `statements` - **List** of statements to be said. Right now, **only one statement is supported**, however you must provide it 
    as a list. Aliases: `text`, `dialogue-statements`, `dialogueStatements`. \\
  * `assetPath` - Path to the image with background for the dialogue window. As any path, it must be eiter absolute or relative to the engine's source root.
    Aliases: `asset-path`, `asset`.
* `battle` -- No props required.

## Condition configuration

Describes condition that must be satisfied before the action can be executed.

Condition consists of following properties:

* `type` - One of types:
  * `item-required` - Requires following props:
    * `item-tag` - Tag of the item that must be in the player inventory for the action to be executed.
        Aliases: `tag`, `itemTag`.
  * `defeat-opponent` - Requires following props:
    * `opponent-tag` - Tag of the opponent that must be defeated to unlock the action. Note that as for now, the engine
        does not check whether specified object exists or has battle action set. For now you must guarantee
        the correctness. Aliases: `tag`, `opponentTag`.
  * `level-required` - Requires following props:
    * `level` - Required player level. Player's level must be >= given value for the condition to be satisfied.

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


