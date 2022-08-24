package tlife.bot.dtcl.chat_bot

import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import tlife.bot.dtcl.model.command.CommandAction
import tlife.bot.dtcl.model.say_hi.playGameAlert

class BotActionImpl: BotAction {
    override suspend fun onMusicAction(fullNameSender: String, bc: BehaviourContext, message: CommonMessage<TextContent>, chatId: ChatId, action: CommandAction.MusicAction) {
        bc.sendTextMessage(
            chatId,
            action.url,
            replyToMessageId = message.messageId
        )
    }

    override suspend fun onPlayAlertAction(fullNameSender: String, bc: BehaviourContext, message: CommonMessage<TextContent>, chatId: ChatId, action: CommandAction.PlayGameAction) {
        var listTag = ""
        action.listId.onEach {
            listTag += "$it "
        }
        bc.sendMessage(
            chatId,
            "${playGameAlert.random().replace("@name", fullNameSender)}\n$listTag",
            replyToMessageId = message.messageId
        )
    }
}