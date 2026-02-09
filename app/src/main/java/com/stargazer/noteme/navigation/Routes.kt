package com.stargazer.noteme.navigation

import kotlinx.serialization.Serializable

// We define the names of the routes we want it to take
@Serializable
object HomeRoute

@Serializable
object AddNoteRoute

@Serializable
data class NoteDetailRoute(val noteId: Int)













