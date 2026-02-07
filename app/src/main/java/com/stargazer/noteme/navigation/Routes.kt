package com.stargazer.noteme.navigation

import kotlinx.serialization.Serializable

//gitmesini istediğimiz rotaların isimlerini tanımlıyoruz
@Serializable
object HomeRoute

@Serializable
object AddNoteRoute

@Serializable
data class NoteDetailRoute(val noteId: Int)













