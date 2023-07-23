# [![NetJS](https://github.com/KostromDan/NetJS/assets/90044015/5ff2e169-bc09-4d0d-8176-78671884a70e)](https://legacy.curseforge.com/minecraft/mc-mods/netjs-kubejs-addon)

# NetJS

## About

Addon for KubeJS for very limited safe network interaction.

## Features

At the current stage of development, it makes it possible to interact with Pastebin and github Gists.

```js
// linear, not recomended, because will stop main client/server thread utill result is received. 
NetJS.getPasteBin('3zCF8MM6', result => {})
NetJS.getGists('3f1cd831af032e52238ef161bdd715b3', result => {})
// async, accessing the network will be a separate thread, the game will continue to run, and the callback will be called when the result is received.
NetJS.getPasteBinAsync('3zCF8MM6', result => {})
NetJS.getGistsAsync('3f1cd831af032e52238ef161bdd715b3', result => {})
```

#### Usage example:

```js
let pastebin_id = '5fZP6aeu'
let gists_id = 'f16d2ee987a35d8930b35971c2d47d72'

BlockEvents.rightClicked(event => { // Gists example
    if (event.hand == 'main_hand') {
        NetJS.getGistsAsync(gists_id, result => { // gists_id must be only id, not url!
            if (result.success) {
                // Be sure to check that the request is successful.
                // Since the user may not have the Internet, the post has been deleted, a failure on the GitHub server.
                // And an infinite number of other cases. If this is not done, it will lead to errors.

                // Use result.raw to get the untouched text uploaded via Gists.
                event.player.tell(`text = "${result.raw}"`)

                // result has many additional parameters.
                // You can see all of them by visiting https://api.github.com/gists/<gists_id> Or by:
                // print_all_params(event.player, result) // uncomment this line to print them

                // Gists transmits all data in json format.
                // To get a specific parameter, work with result as a json object.
                // For example:
                event.player.tell(`Owner login = "${result["owner"]["login"]}"`)
                // result["files"]["<filename of uploaded file>"]["content"] is equivalent of result.raw

                // If uploaded data was in Json format, you can easily deserialize it to Json object.
                let json_result = result.parseRawToJson()
                event.player.tell(`test : "${json_result["test_key"]}"`)
                event.player.tell(`recursive_test = "${json_result["recursive_test"]["test_key"]}"`)

            } else {
                //If the request was not successful, the result.exception will store information about the exception.
                event.player.tell(result.exception)
            }
        })
    }
})

BlockEvents.leftClicked(event => { // PasteBin example
    NetJS.getPasteBinAsync(pastebin_id, result => { // pastebin_id must be only id, not url!
        if (result.success) {
            // Be sure to check that the request is successful.
            // Since the user may not have the Internet, the post has been deleted, a failure on the PasteBin server.
            // And an infinite number of other cases. If this is not done, it will lead to errors.

            // Use result.raw to get the untouched text uploaded via PasteBin.
            event.player.tell(`text = "${result.raw}"`)

            // result has many additional parameters.
            // You can see all of them by:
            // print_all_params(event.player, result) // uncomment this line to print them

            // To get a specific parameter, work with result as a json object.
            // For example:
            event.player.tell(`Author username = "${result["author_username"]}"`)
            // result["raw_text"] is equivalent of result.raw

            // If uploaded data was in Json format, you can easily deserialize it to Json object.
            let json_result = result.parseRawToJson()
            event.player.tell(`test : "${json_result["test_key"]}"`)
            event.player.tell(`recursive_test = "${json_result["recursive_test"]["test_key"]}"`)

        } else {
            //If the request was not successful, the result.exception will store information about the exception.
            event.player.tell(result.exception)
        }
    })
    event.cancel()
})

function print_all_params(player, result) {
    result.forEach((key, value) => {
        player.tell(`${key} = "${value}"`)
    })
}
```
