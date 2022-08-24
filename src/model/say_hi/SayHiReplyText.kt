package tlife.bot.dtcl.model.say_hi

data class SayHiReplyText (
    val fullName: String
){
    fun getContentReply(): String{
        return templateReply.random().replace("@user", fullName)
    }

    private val templateReply = listOf(
        "Cái đel gì thế @user",
        "Ồ là thằng @user, nói đi",
        "Vl lại là thằng @user, cần gì",
        "@user, m bị rãnh à, gáy lẹ đi t đang ko rãnh",
        "Haha, hú cái quần, nói lẹ đi th khứa @user",
        "Hôm nay t hơi mệt, gáy nhanh t đi ngủ nè",
        "Top 2 never die, @user gáy lẹ",
        "@user, lại là m, GÁY",
        "Mày kêu t à @user"
    )
}