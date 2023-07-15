# [![NetJS](https://github.com/KostromDan/NetJS/assets/90044015/5ff2e169-bc09-4d0d-8176-78671884a70e)](https://legacy.curseforge.com/minecraft/mc-mods/netjs-kubejs-addon)
# NetJS

## About

Addon for KubeJS for very limited safe network interaction.

## Features

### PasteBin

```js
NetJS.getPasteBin('3zCF8MM6', result => {}) // linear
NetJS.getPasteBinAsync('3zCF8MM6', result => {}) // async
```

#### Usage example:

```js
function print_all_params(player, result) {
    player.tell(`id = "${result.id}"\n` +
        `post_name = "${result.post_name}"\n` +
        `author_username = "${result.author_username}"\n` +
        `date = "${result.date}"\n` +
        `expire = "${result.expire}"\n` +
        `lang = "${result.lang}"\n` +
        `category = "${result.category}"\n` +
        `edited = "${result.edited}"\n` +
        `visits = "${result.visits}"\n` +
        `stars = "${result.stars}"\n` +
        `likes = "${result.likes}"\n` +
        `dislikes = "${result.dislikes}"\n` +
        `size = "${result.size}"\n` +
        `paste = "${result}"\n`) //This will return content of <https://pastebin.com/raw/3zCF8MM6>
        // result is equivalent of result.raw
}

BlockEvents.rightClicked(event => {
    if (event.hand == 'main_hand') {
        event.player.tell('started not async test') //first print
        NetJS.getPasteBin('3zCF8MM6', result => {
            if (result.success) {
                print_all_params(event.player, result) //second print
            } else {
                event.player.tell(result.exception)
            }
        })
        event.player.tell('finished not async test') //third print
    }
})

BlockEvents.leftClicked(event => {
    event.cancel()
    event.player.tell('started async test') //first print
    NetJS.getPasteBinAsync('3zCF8MM6', result => {
        if (result.success) {
            print_all_params(event.player, result)
        } else {
            event.player.tell(result.exception) //third print
        }
    })
    event.player.tell('finished async test') //second print
})

//You can use result.getAsJsonObject() to deserialize paste to Json object
```
