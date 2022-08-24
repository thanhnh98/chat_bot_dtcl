package tlife.bot.dtcl.model.command

import tlife.bot.dtcl.utils.containEx

data class CommandModel(
    val commandKeys: List<String>,
    val commandAction: CommandAction
){
    companion object {
        fun getCommandByString(key: String): CommandModel?{
            return commands.find { result ->
                result.commandKeys.any {
                    key.containEx(it)
                }
            }
        }

        val commands = listOf(
            CommandModel(
                commandKeys = listOf(
                    "music",
                    "lên nhạc",
                ),
                CommandAction.MusicAction(
                    "https://www.youtube.com/watch?v=Llw9Q6akRo4"
                )
            ),
            CommandModel(
                commandKeys = listOf(
                    "chơi game",
                    "zô game",
                    "vô game",
                    "làm trận",
                    "làm ván",
                    "zô nè",
                    "vô nè",
                    "zô ae",
                    "vô đi",
                    "zo de",
                    "zoo ae",
                    "cóc kêu ae",
                    "cóc gọi ae",
                    "cóc hú ae",
                    "cóc đâu",
                    "kêu ae",
                    "hú ae",
                    "ae đâu"
                ),
                CommandAction.PlayGameAction(
                    listOf(
                        "@bonnmh",
                        "@thangtop1",
                        "@thanhnguyenh"
                    )
                )
            ),
        )
    }
}

sealed class CommandAction() {
    data class MusicAction(
        val url: String
    ): CommandAction()
    data class PlayGameAction(
        val listId: List<String>
    ): CommandAction()
}