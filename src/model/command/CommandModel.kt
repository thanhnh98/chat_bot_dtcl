package tlife.bot.dtcl.model.command

data class CommandModel(
    val commandKeys: List<String>,
    val commandAction: CommandAction
){
    companion object {
        fun getCommandByString(key: String): CommandModel?{
            return commands.find { result ->
                result.commandKeys.any {
                    it.lowercase() == key.lowercase()
                }
            }
        }

        val commands = listOf(
            CommandModel(
                commandKeys = listOf(
                    "music",
                    "nhạc",
                ),
                CommandAction.MusicAction(
                    "https://www.youtube.com/watch?v=Llw9Q6akRo4"
                )
            )
        )
    }
}

sealed class CommandAction() {
    data class MusicAction(
        val url: String
    ): CommandAction()
}