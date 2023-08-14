# KubeJS Additions

## About

This is a mod that aims to add some additional events, wrappers,
and functionality to KubeJS that aren't worth PRing into KubeJS itself
or just take too long as a PR to get merged.

## Features

For the most part, a lot of these additions are events, however, there is some type wrappers
and bindings as well.

# [Bindings](https://github.com/Hunter19823/kubejsadditions/blob/1.19.2/common/src/main/java/pie/ilikepiefoo/AdditionsPlugin.javaL29)

- ### Damage Source
- ### Structures
- ### Feature

# [Type Wrappers](https://github.com/Hunter19823/kubejsadditions/blob/1.19.2/common/src/main/java/pie/ilikepiefoo/AdditionsPlugin.javaL35)

- ### Chunk Pos
	- Supports:
		- ChunkPos
		- BlockPos
		- Long
		- BlockContainerJS
		- Entity

# Events

## [Custom Architectury Event Listeners](https://github.com/Hunter19823/kubejsadditions/blob/3d489ddef363573218f68a8a7e34f924729fe1c2/common/src/main/java/pie/ilikepiefoo/events/AdditionalEvents.java#L17)

### Using the power of Proxy classes, this mod adds the ability to listen to any Architectury or Fabric event.

#### Architectury Event Register (Startup) (Cannot be reloaded without a restart)

The Architectury Registry Event is a special event that allows you to register events to the Architectury Event Bus.
This is useful for registering events that are not supported by KubeJS or addon mods.

```js
const LIFE_CYCLE_EVENTS = Java.loadClass('dev.architectury.event.events.common.LifecycleEvent');
ArchEvents.registry(event => {
	event.register('server starting', LIFE_CYCLE_EVENTS, 'SERVER_BEFORE_START');
});
```

The first part is to find and load a Java class that contains a public static field containing the Architectury Event you want to listen to.
In this example, that is the `LifecycleEvent` interface.
The second part is to find the field name of the event you want to listen to.
In this example, that is the `SERVER_BEFORE_START` field.
The third part is to register the event under an arbitrary name.
In this example, that is `server starting`.
See the below example for how to listen to the event.

#### Architectury Event Listen (Startup/Client/Server) (Reloadable)

```js
// See above for the first part of this example.
ArchEvents.handle('server starting', event => {
	console.log('Custom Arch Event handler has fired!');
	console.log("Event Class: " + event); // Returns the class of the event. (It's always a ProxyClass)
	console.log("Method Name: " + event.getMethodName()); // Returns the name of the method that fired the event.
	console.log("Parameter List: " + event.getArgs()); // Returns an array of parameters. Most likley will only show up as a list of objects.
	console.log("Parameter 0: " + event.getArg(0)); // Returns a specific parameter at the given index.
	console.log("Parameter Map: " + event.getParameters()); // Returns a map of parameters. Most parameter names will be "arg0", "arg1", etc.
	console.log("Requires Return: " + event.requiresReturn()); // Returns true if the event requires a return value.
	console.log("Return Type: " + event.getReturnType()); // Returns the type of the return value.
	console.log("Generic Return Type: " + event.getGenericReturnType()); // Returns the generic type of the return value.
	// Let's say the event requires a return value.
	// We can return a value like so:
	// event.setResult("Hello World!");
});
```

This example showcases how to listen to the event we registered above.
You use the name of the event you registered to listen to it.
The event parameter is an instance of the `ProxyClass` class.
You can listen to any event from any script context (Startup, Client, Server) with the same syntax.

## [Custom Fabric Event Listeners](https://github.com/Hunter19823/kubejsadditions/blob/3d489ddef363573218f68a8a7e34f924729fe1c2/fabric/src/main/java/pie/ilikepiefoo/fabric/FabricEventsJS.java#L60)

#### Fabric Event Register (Startup) (Cannot be reloaded without a restart)

The FabricEvents Registry Event is a special event that allows you to register events to the Fabric Event Bus.
This is useful for registering events that are not supported by KubeJS or addon mods.

```js
const SERVER_MESSAGES = Java.loadClass('net.fabricmc.fabric.api.message.v1.ServerMessageEvents');
FabricEvents.registry(event => {
	event.register('chat messages', SERVER_MESSAGES, 'CHAT_MESSAGE');
});
```

The first part is to find and load a Java class that contains a public static field containing the Fabric Event you want to listen to.
In this example, that is the `ServerMessageEvents` interface.
The second part is to find the field name of the event you want to listen to.
In this example, that is the `CHAT_MESSAGE` field.
The third part is to register the event under an arbitrary name.
In this example, that is `chat messages`.
See the below example for how to listen to the event.

#### Fabric Events Event Listen (Startup/Client/Server) (Reloadable)

```js
// See above for the first part of this example.
FabricEvents.handle('chat messages', event => {
	console.log('Custom Arch Event handler has fired!');
	console.log("Event Class: " + event); // Returns the class of the event. (It's always a ProxyClass)
	console.log("Method Name: " + event.getMethodName()); // Returns the name of the method that fired the event.
	console.log("Parameter List: " + event.getArgs()); // Returns an array of parameters. Most likley will only show up as a list of objects.
	console.log("Parameter 0: " + event.getArg(0)); // Returns a specific parameter at the given index.
	console.log("Parameter Map: " + event.getParameters()); // Returns a map of parameters. Most parameter names will be "arg0", "arg1", etc.
	console.log("Requires Return: " + event.requiresReturn()); // Returns true if the event requires a return value.
	console.log("Return Type: " + event.getReturnType()); // Returns the type of the return value.
	console.log("Generic Return Type: " + event.getGenericReturnType()); // Returns the generic type of the return value.
	// Let's say the event requires a return value.
	// We can return a value like so:
	// event.setResult("Hello World!");
});
```

This example showcases how to listen to the event we registered above.
You use the name of the event you registered to listen to it.
The event parameter is an instance of the `ProxyClass` class.
You can listen to any event from any script context (Startup, Client, Server) with the same syntax.

### [Common Events](https://github.com/Hunter19823/kubejsadditions/blob/1.19.2/common/src/main/java/pie/ilikepiefoo/events/AdditionalEvents.java)

#### Entity Enter Chunk (Server Only)

```js
CommonAddedEvents.entityEnterChunk(event => {
	console.log('Entity Enter Chunk event fired!');
	console.log(event);
});
```

#### Entity Tamed (Server Only)

```js
CommonAddedEvents.entityTame(event => {
	console.log('Entity Tame event fired!');
	console.log(event);
});
```

#### Player Changed Dimension (Server Only)

```js
CommonAddedEvents.playerChangeDimension(event => {
	console.log('Player Change Dimension event fired!');
	console.log(event);
});
```

#### Entity Cloned (Server Only)

```js
CommonAddedEvents.playerClone(event => {
	console.log('Player Clone event fired!');
	console.log(event);
});
```

#### Player Respawned. (Server Only)

```js
CommonAddedEvents.playerRespawn(event => {
	console.log('Player Respawn event fired!');
	console.log(event);
});
```

### [JEI Events](https://github.com/Hunter19823/kubejsadditions/blob/1.19.2/common/src/main/java/pie/ilikepiefoo/compat/jei/JEIEvents.java)

#### JEI Runtime Available (Client Only)

```js
JEIAddedEvents.onRuntimeAvailable(event => {
	console.log('Runtime Available event fired!');
	console.log(event);
});
```

#### JEI Register Advanced Recipe (Client Only)

```js
JEIAddedEvents.registerAdvanced(event => {
	console.log('Register Advanced event fired!');
	console.log(event);
});
```

#### JEI Register Recipe Category (Client Only)

```js
JEIAddedEvents.registerCategories(event => {
	console.log('Register Categories event fired!');
	console.log(event);
});
```

#### JEI Register Fluid Subtypes (Client Only)

```js
JEIAddedEvents.registerFluidSubtypes(event => {
	console.log('Register Fluid Subtypes event fired!');
	console.log(event);
});
```

#### JEI Register GUI Handlers (Client Only)

```js
JEIAddedEvents.registerGUIHandlers(event => {
	console.log('Register GUI Handlers event fired!');
	console.log(event);
});
```

#### JEI Register Ingredients (Client Only)

```js
JEIAddedEvents.registerIngredients(event => {
	console.log('Register Ingredients event fired!');
	console.log(event);
});
```

#### JEI Register Item Subtypes (Client Only)

```js
JEIAddedEvents.registerItemSubtypes(event => {
	console.log('Register Item Subtypes event fired!');
	console.log(event);
});
```

#### JEI Register Recipe Catalysts (Client Only)

```js
JEIAddedEvents.registerRecipeCatalysts(event => {
	console.log('Register Recipe Catalysts event fired!');
	console.log(event);
});
```

#### JEI Register Recipes (Client Only)

```js
JEIAddedEvents.registerRecipes(event => {
	console.log('Register Recipes event fired!');
	console.log(event);
});
```

#### JEI Register Recipe Transfer Handlers (Client Only)

```js
JEIAddedEvents.registerRecipeTransferHandlers(event => {
	console.log('Register Recipe Transfer Handlers event fired!');
	console.log(event);
});
```

#### JEI Register Vanilla Category Extensions (Client Only)

```js
JEIAddedEvents.registerVanillaCategoryExtensions(event => {
	console.log('Register Vanilla Category Extensions event fired!');
	console.log(event);
});
```

### [Fabric Events](https://github.com/Hunter19823/kubejsadditions/fabric/src/main/java/pie/ilikepiefoo/fabric/FabricEvents.java)

#### Fabric (Client Only)

```js
FabricAddedEvents.renderHUD(event => {
	console.log('Render HUD event fired!');
	console.log(event);
});
```

#### Fabric (Client Only)

```js
FabricAddedEvents.beforeEntities(event => {
	console.log('Before Entities event fired!');
	console.log(event);
});
```

#### Fabric (Client Only)

```js
FabricAddedEvents.afterEntities(event => {
	console.log('After Entities event fired!');
	console.log(event);
});
```

#### Fabric (Client Only)

```js
FabricAddedEvents.afterTranslucent(event => {
	console.log('After Translucent event fired!');
	console.log(event);
});
```

#### Fabric (Client Only)

```js
FabricAddedEvents.afterSetup(event => {
	console.log('After Setup event fired!');
	console.log(event);
});
```

#### Fabric Start Render Event (Client Only)

```js
FabricAddedEvents.startRender(event => {
	console.log('Start Render event fired!');
	console.log(event);
});
```

#### Fabric Last Render Event (Client Only)

```js
FabricAddedEvents.lastRender(event => {
	console.log('Last Render event fired!');
	console.log(event);
});
```

#### Fabric End Render Event (Client Only)

```js
FabricAddedEvents.endRender(event => {
	console.log('End Render event fired!');
	console.log(event);
});
```

#### Fabric Before BlockOutline Event (Client Only)

```js
FabricAddedEvents.beforeBlockOutline(event => {
	console.log('Before Block Outline event fired!');
	console.log(event);
});
```

#### Fabric BlockOutline Event (Client Only)

```js
FabricAddedEvents.blockOutline(event => {
	console.log('Block Outline event fired!');
	console.log(event);
});
```

#### Fabric Allow Elytra Flight Event (Server Only)

```js
FabricAddedEvents.allowElytraFlight(event => {
	console.log('Allow Elytra Flight event fired!');
	console.log(event);
});
```

#### Fabric Custom Elytra Flight Event (Server Only)

```js
FabricAddedEvents.customElytraFlight(event => {
	console.log('Custom Elytra Flight event fired!');
	console.log(event);
});
```

#### Fabric Allow Sleeping Event (Server Only)

```js
FabricAddedEvents.allowSleeping(event => {
	console.log('Allow Sleeping event fired!');
	console.log(event);
});
```

#### Fabric Start Sleeping Event (Server Only)

```js
FabricAddedEvents.startSleeping(event => {
	console.log('Start Sleeping event fired!');
	console.log(event);
});
```

#### Fabric Stop Sleeping Event (Server Only)

```js
FabricAddedEvents.stopSleeping(event => {
	console.log('Stop Sleeping event fired!');
	console.log(event);
});
```

#### Fabric Allow Bed Event (Server Only)

```js
FabricAddedEvents.allowBed(event => {
	console.log('Allow Bed event fired!');
	console.log(event);
});
```

#### Fabric Allow Sleep Time Event (Server Only)

```js
FabricAddedEvents.allowSleepTime(event => {
	console.log('Allow Sleep Time event fired!');
	console.log(event);
});
```

#### Fabric Allow Nearby Monsters Event (Server Only)

```js
FabricAddedEvents.allowNearbyMonsters(event => {
	console.log('Allow Nearby Monsters event fired!');
	console.log(event);
});
```

#### Fabric Allow Reseting Time Event (Server Only)

```js
FabricAddedEvents.allowResettingTime(event => {
	console.log('Allow Resetting Time event fired!');
	console.log(event);
});
```

#### Fabric Allow Modifying Sleeping Direction Event (Server Only)

```js
FabricAddedEvents.ModifySleepingDirection(event => {
	console.log('Modify Sleeping Direction event fired!');
	console.log(event);
});
```

#### Fabric Allow Setting Spawn Event (Server Only)

```js
FabricAddedEvents.allowSettingSpawn(event => {
	console.log('Allow Setting Spawn event fired!');
	console.log(event);
});
```

#### Fabric Allow Setting Bed Occupation State Event (Server Only)

```js
FabricAddedEvents.setBedOccupationState(event => {
	console.log('Set Bed Occupation State event fired!');
	console.log(event);
});
```

#### Fabric Allow Modifying Wake Up Position Event (Server Only)

```js
FabricAddedEvents.modifyWakeUpPosition(event => {
	console.log('Modify Wake Up Position event fired!');
	console.log(event);
});
```
