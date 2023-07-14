![637830420779583100 (1)](https://github.com/KostromDan/NetJS/assets/90044015/5ff2e169-bc09-4d0d-8176-78671884a70e)
# NetJS

## About

Addon for KubeJS for very limited safe network interaction.

## Features

### PasteBin

#### Usage example:

```js
BlockEvents.rightClicked(event => {
    if (event.hand == 'main_hand') {
        let result = NetJS.getPasteBinString('3zCF8MM6')
        if (result.success) {
            event.player.tell(`id = "${result.id}"\n` +
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
                    `paste = "${result}"\n`)
        } else {
            event.player.tell(result.exception)
        }
        //You can use result.getAsJsonObject() to deserialize paste to Json object
    }
})
```

This will return content of <https://pastebin.com/raw/3zCF8MM6>
