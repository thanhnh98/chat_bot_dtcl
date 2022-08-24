package tlife.bot.dtcl.chat_bot

import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import tlife.bot.dtcl.model.command.CommandAction

interface BotAction {
    suspend fun onMusicAction(fullNameSender: String, bc: BehaviourContext, message: CommonMessage<TextContent>, chatId: ChatId, action: CommandAction.MusicAction)
    suspend fun onPlayAlertAction(fullNameSender: String, bc: BehaviourContext, message: CommonMessage<TextContent>, chatId: ChatId, action: CommandAction.PlayGameAction)
}