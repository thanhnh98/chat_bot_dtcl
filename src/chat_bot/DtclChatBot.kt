package tlife.bot.dtcl.chat_bot

import com.google.gson.Gson
import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.send.media.sendPhoto
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onText
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.text
import dev.inmo.tgbotapi.extensions.utils.whenUsernameChat
import dev.inmo.tgbotapi.requests.abstracts.InputFile
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.chat.ExtendedSupergroupChatImpl
import dev.inmo.tgbotapi.types.chat.UsernameChat
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import dev.inmo.tgbotapi.utils.RiskFeature
import tlife.bot.dtcl.constant.ImageUrl
import tlife.bot.dtcl.model.command.CommandAction
import tlife.bot.dtcl.model.command.CommandModel
import tlife.bot.dtcl.model.command.CommandModel.Companion.getCommandByString
import tlife.bot.dtcl.model.say_hi.SayHiReplyText
import tlife.bot.dtcl.model.say_hi.sayHiTemplate
import tlife.bot.dtcl.utils.containEx

class DtclChatBot(
    private val botToken: String
): BotAction by BotActionImpl() {
    private val token = botToken
    suspend fun setupBot(){
        val bot = telegramBot(token)
        bot.buildBehaviourWithLongPolling {
            val botInfo = getMe()
            println("Ready: $botInfo")
            onText {
                val chatChannel = this.getChat(it.chat)
                val message = it
                val bc = this
                it.chat.whenUsernameChat { user ->
                    val messageWhen = message.date.unixMillisLong
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - messageWhen < 10000){
                        doOnText(bc, message, user, chatChannel is ExtendedSupergroupChatImpl)
                    }
                }
            }
        }
    }

    @OptIn(RiskFeature::class)
    suspend fun doOnText(
        bc: BehaviourContext,
        message: CommonMessage<TextContent>,
        user: UsernameChat,
        isSupperChannel: Boolean
    ) {
        val keyString = message.text?:""
        val fullName = "${message.from?.firstName} ${message.from?.lastName}"
        val chatId = message.chat.id
//        println("text coming from ${Gson().toJson(message.from)}")
        when {
            keyString == "spoon" -> {
                bc.sendPhoto(
                    chatId,
                    InputFile.fromUrl(ImageUrl.spoon)
                )
            }
            sayHiTemplate.any { sayHi ->
                keyString.containEx(sayHi)
            } -> {
                bc.sendTextMessage(
                    chatId,
                    SayHiReplyText(fullName).getContentReply(),
                    replyToMessageId = message.messageId
                )
            }
            getCommandByString(keyString) != null -> {
                val command = getCommandByString(keyString)!!
                doActionByCommand(fullName, bc, chatId, command, message)
            }
            else -> {

            }
        }
    }

    private suspend fun doActionByCommand(fullNameSender: String, bc: BehaviourContext, chatId: ChatId, command: CommandModel, message: CommonMessage<TextContent>) {
        val action = command.commandAction
        when(action){
            is CommandAction.MusicAction -> {
                onMusicAction(fullNameSender, bc, message, chatId, action)
            }

            is CommandAction.PlayGameAction -> {
                onPlayAlertAction(fullNameSender, bc, message, chatId, action)
            }
        }
    }
}