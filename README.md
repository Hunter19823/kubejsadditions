# KubeJS Additions

## About

This is a mod that aims to add some additional events, wrappers,
and functionality to KubeJS that aren't worth PRing into KubeJS itself
or just take too long as a PR to get merged.

## *Most Recent Addition*:

### Custom JEI Categories & Recipe Types.

![custom_jei_category.png](custom_jei_category.png)

## Custom JEI Category Example

### [JEIEvents.registerCategories](https://github.com/Hunter19823/kubejsadditions/blob/1.20.1/common/src/main/java/pie/ilikepiefoo/compat/jei/events/RegisterCategoriesEventJS.java)  (Client Only)

```js
JEIAddedEvents.registerCategories((event) => {
    // Register a new CustomCategory with the id "kubejsadditions:painful_blocks".
    event.custom("kubejsadditions:painful_blocks", (category) => {
        const guiHelper = category.jeiHelpers.getGuiHelper();
        // Set the title of the category to "Painful Blocks".
        category.title("Painful Blocks")
                // Set the background of the category to a blank 100x50 drawable canvas.
                .background(guiHelper.createBlankDrawable(100, 50))
                // Set the icon of the category to a cactus item.
                .icon(guiHelper.createDrawableItemStack(Item.of('minecraft:cactus')))
                // Set the callback function that will verify if a recipe is a valid recipe for this category.
                .isRecipeHandled((recipe) => {
                    return verifyPainfulBlocksRecipe(category.jeiHelpers, recipe);
                })
                // Set the callback function that will allow JEI to index this recipe and determine
                // what the inputs and outputs of each recipe are.
                .handleLookup((builder, recipe, focuses) => {
                    configurePainfulBlocksRecipeLookup(category.jeiHelpers, builder, recipe, focuses);
                })
                // Set the callback function for rendering additional detials to the screen.
                .setDrawHandler((recipe, recipeSlotsView, guiGraphics, mouseX, mouseY) => {
                    renderPainfulBlocksRecipeDescription(category.jeiHelpers, recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
                })
    });
});
```

### [RecipeCategoryBuilder.IsRecipeHandled](https://github.com/Hunter19823/kubejsadditions/blob/f541be151f96745a0a184c5b3b8c48b0a07a83b3/common/src/main/java/pie/ilikepiefoo/compat/jei/builder/RecipeCategoryBuilder.java#L268-L273)

```js
// This function will be used by JEI to verify if a custom recipe is apart of this category.
function verifyPainfulBlocksRecipe(jeiHelpers, recipe) {
    // The data we give later on in the recipes will be stored in the `data` field.
    // Whatever you pass in, is whatever you'll get out.
    // The possibilities are endless, as you are only restricted to what you can store on
    // the KubeJS client side.

    // IMPORTANT: Always return true or false. If you do not, it could crash the game or cause
    // JEI to not work properly.
    if (!recipe) return false;
    if (!recipe.data) return false;
    if (!recipe.data.type) return false;
    if (!recipe.data.name) return false;

    return !!recipe.data.description;
}
```

### [RecipeCategoryBuilder.handleLookup](https://github.com/Hunter19823/kubejsadditions/blob/f541be151f96745a0a184c5b3b8c48b0a07a83b3/common/src/main/java/pie/ilikepiefoo/compat/jei/builder/RecipeCategoryBuilder.java#L203-L211)

```js
// JEI needs to understand what sort of information is held within
// the recipe. This is where you can define different types of slots,
// where they should go, if they are input, output, catalysts, or neither.
// Depending on the slot type, it will effect if the recipe appears in
// in the recipe lookup.
// Refer to the JEI API for more information on how to use this.
function configurePainfulBlocksRecipeLookup(jeiHelpers, builder, recipe, focuses) {
    switch (recipe.data.type) {
        case "item":
        case "block":
            // Add an input slot to the recipe that is 35 pixels from the left and 20 pixels from the top.
            // Name the slot "input" so that if we want to reference it in the draw handler, we can.
            builder.addSlot("INPUT", 35, 20).addItemStack(Item.of(recipe.data.name)).setSlotName("input");
            // Add an invisible output slot so that if you look at how the item is made, it shows this recipe.
            builder.addInvisibleIngredients("OUTPUT").addItemStack(Item.of(recipe.data.name))
            break;
        case "fluid":
            // Add an input slot to the recipe that is 35 pixels from the left and 20 pixels from the top.
            // This one is slightly different as we are adding a fluid to the slot instead of an Item.
            // you can chain these as much as you'd like and add as many different ingredients
            // as you'd like.
            builder.addSlot("INPUT", 35, 20).addFluidStack(recipe.data.name, 1000).setSlotName("input")
            // Add an invisible output slot so that if you look at how the item is made, it shows this recipe.
            builder.addInvisibleIngredients("OUTPUT").addFluidStack(recipe.data.name, 1000)
            break;
    }
}
```

### [RecipeCategoryBuilder.draw](https://github.com/Hunter19823/kubejsadditions/blob/f541be151f96745a0a184c5b3b8c48b0a07a83b3/common/src/main/java/pie/ilikepiefoo/compat/jei/builder/RecipeCategoryBuilder.java#L214-L231)

```js
// We tap into the Minecraft GuiGraphics class to draw the text description above the input slot.
function renderPainfulBlocksRecipeDescription(jeiHelpers, recipe, recipeSlotsView, guiGraphics, mouseX, mouseY) {
    // By using the Client binding we can get the Minecraft font.
    // Next we can draw the description of the recipe above the input slot.
    // The first argument is the font, the second is the FormattedText, the third is the x position,
    // the fourth is the y position, the fifth is the width of the text, and I have no clue what the last argument does.
    // Probably z-index if I had to guess.
    guiGraphics.drawWordWrap(Client.font, Text.of(recipe.data.description), 0, 10, 100, 0);
}
```

### [JEIEvents.registerRecipes](https://github.com/Hunter19823/kubejsadditions/blob/f541be151f96745a0a184c5b3b8c48b0a07a83b3/common/src/main/java/pie/ilikepiefoo/compat/jei/events/RegisterRecipesEventJS.java#L23-L27)  (Client Only)

```js
// Here we can hook into the JEI recipe registration event to add some recipes to our
// newly created category.
JEIAddedEvents.registerRecipes((event) => {
    // This utilized the new recipe category you made above here.
    // All that's left is to add as many different recipes as your heart desires.
    // There are no limitations to what you can store as a "recipe". Nothing that you don't control
    // cares about the format of the data.
    // If you want it to be an object, an array, a string, a number, a boolean, or even a function,
    // It does not matter. It's all up to you. Go wild.
    // Just make sure to update how you render it in the category definition.
    event.custom("kubejsadditions:painful_blocks")
            .add({name: 'minecraft:cactus', type: 'block', description: 'It is kind of prickly.'})
            .add({name: 'minecraft:stick', type: 'item', description: 'It could be sharp!'})
            .add({name: 'minecraft:lava', type: 'fluid', description: 'It is very hot.'})
            .add({name: 'minecraft:sugar', type: 'item', description: 'It causes diabetes.'})
    // .add([])
    // .add("")
    // .add(true)
    // .add(50)
    // .add(12.4)
    // .add(()=> Item.of('steak'))
});
```

### Jade Integration

![custom_jade_integration.png](custom_jade_integration.png)

As of version 4.1.0 of KubeJS Additions, you can now fully use Jade's WAILA Plugin API
to completely customize the information displayed by Jade.

Jade is a fork of HWYLA, which is a fork of WAILA.
These mod are the ones that adds information about the block you are looking at in the world, to the top of your screen.

Below you may find an example on how to use this to

#### [WailaCommonRegistrationEventJS](https://github.com/Hunter19823/kubejsadditions/blob/1.20.1/common/src/main/java/pie/ilikepiefoo/compat/jade/WailaCommonRegistrationEventJS.java) (Startup Only)

```js
// The following would go inside the startup scripts folder.
// This is a simple example of how to register a block data provider for a block entity.

// Load the BlockEntity Class that you will be providing data for.
const $BRUSHABLE_BLOCK_ENTITY = Java.loadClass('net.minecraft.world.level.block.entity.BrushableBlockEntity');

function writeBrushableBuriedItemToServerData(tag, accessor) {
    // Refer to the Jade API for more information on what accessors are.
    // This specific accessor is a BlockAccessor.
    const blockEntity = accessor.getBlockEntity();
    // Return in case there is no block entity
    if (!blockEntity) return;
    // Return in case the Brushable Block entity does not have an item.
    if (!blockEntity.getItem()) return;
    // Return in case the Brushable Block entity has an empty item.
    if (blockEntity.getItem().isEmpty()) return;
    // Store the buried item in ServerData under "brushing_result" using KubeJS NBT helpers
    // so the client can reconstruct the stack (including display name) from the tag.
    tag.put("brushing_result", NBT.toTag(blockEntity.getItem()));
}

// This is the hook that will be called when the "IWailaPlugin" common registration event is fired.
// Refer to WailaCommonRegistrationEventJS for more information about the event.
JadeEvents.onCommonRegistration((event) => {
    // Register a new block data provider for the Brushable Block entity.
    event.blockDataProvider('kubejsadditions:brushable_block', $BRUSHABLE_BLOCK_ENTITY)
            // Our data provider is useless without a callback function.
            .setCallback((tag, accessor) => {
                writeBrushableBuriedItemToServerData(tag, accessor);
            });
});
```

#### [WailaClientRegistrationEventJS](https://github.com/Hunter19823/kubejsadditions/blob/1.20.1/common/src/main/java/pie/ilikepiefoo/compat/jade/WailaClientRegistrationEventJS.java) (Client Only)

```js
// The following would go in the client scripts folder.
// This is a simple example on how to retrieve the data we registered in the startup scripts.

// Load the Block Class that you will be providing data for.
const $BRUSHABLE_BLOCK = Java.loadClass('net.minecraft.world.level.block.BrushableBlock');

function appendBrushableBuriedItemHintToTooltip(tooltip, accessor, pluginConfig) {
    // Refer to the Jade API for more information on what accessors are.
    // Get the server data from the accessor.
    const {serverData} = accessor;
    // Return in case there is no server data.
    if (!serverData) return;
    // Return in case the server data does not contain the key "brushing_result".
    if (!serverData.contains("brushing_result")) return;
    // Read the item stack NBT written on the server and rebuild the ItemStack on the client.
    const displayNameTag = serverData.get("brushing_result");
    const displayName = Item.of(NBT.toJson(displayNameTag));
    // If the item could not be parsed, return.
    if (!displayName) return;
    // Add the display name to the tooltip.
    tooltip.add(Text.of(["I'm starting to suspect that there is an ", displayName.getDisplayName(), " hidden in this block..."]));
}

// This is the hook that will be called when the "IWailaPlugin" client registration event is fired.
// Refer to WailaClientRegistrationEventJS for more information about the event.
JadeEvents.onClientRegistration((event) => {
    // Register a new block component provider for the Brushable Block.
    event.block('kubejsadditions:brushable_block', $BRUSHABLE_BLOCK)
            .tooltip((tooltip, accessor, pluginConfig) => {
                appendBrushableBuriedItemHintToTooltip(tooltip, accessor, pluginConfig);
            });
});
```

## Features

# [Type Wrappers](https://github.com/Hunter19823/kubejsadditions/blob/1.19.2/common/src/main/java/pie/ilikepiefoo/AdditionsPlugin.java#L35)

- ### Chunk Pos
	- Supports:
		- ChunkPos
		- BlockPos
		- Long
		- BlockContainerJS
		- Entity

# Events

## [Custom Architectury Event Listeners](https://github.com/Hunter19823/kubejsadditions/blob/3d489ddef363573218f68a8a7e34f924729fe1c2/common/src/main/java/pie/ilikepiefoo/events/AdditionalEvents.java#L17)

### Using the power of Proxy classes, this mod adds the ability to listen to any Architectury event.

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
ArchEvents.handleServer('server starting', event => {
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

***Important Note for KubeJS Additions 4.0.0+ (Mc 1.20+)***:

Due to changes with KubeJS Event handlers, `ArchEvents.handle` has been split into `ArchEvents.handleClient`, `ArchEvents.handleServer`, and `ArchEvents.handleStartup` for each script type respectively.
The syntax is the same as the above example, however, you must use the correct method for the script type you are using.

For previous versions of KubeJS Additions, you can use `ArchEvents.handle` for all script types.

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
