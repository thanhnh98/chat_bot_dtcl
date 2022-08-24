package tlife.bot.dtcl

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import dev.inmo.tgbotapi.utils.RiskFeature

@OptIn(RiskFeature::class)
fun  CommonMessage<TextContent>.fromUser(): String? {
    return from?.firstName
}