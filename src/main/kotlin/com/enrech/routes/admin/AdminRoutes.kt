package com.enrech.routes.admin

import com.enrech.db.DatabaseFactory
import com.enrech.db.model.content.Chapter
import com.enrech.db.model.content.Lesson
import com.enrech.db.model.content.LessonGroup
import com.enrech.db.model.content.Subject
import com.enrech.db.model.user.*
import com.enrech.domain.repository.ChapterRepository
import com.enrech.domain.repository.LessonGroupRepository
import com.enrech.domain.repository.LessonRepository
import com.enrech.domain.repository.SubjectRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.adminRoutes() {
    val lessonRepo by inject<LessonRepository>()
    val groupRepo by inject<LessonGroupRepository>()
    val chapterRepo by inject<ChapterRepository>()
    val subjectRepo by inject<SubjectRepository>()

    route("/admin") {
        get("/populate") {
            DatabaseFactory.dbQueryWithCatch {
                val subject1 = Subject.new { name = "Maths" }
                val subject2 = Subject.new { name = "Art" }
                val subject3 = Subject.new { name = "Science" }
                val subject4 = Subject.new { name = "Biology" }

                val chapter1 = Chapter.new { title = "Rational numbers"; order = 1; subjectId = subject1.id.value }
                val chapter2 = Chapter.new { title = "Imaginary numbers"; order = 2; subjectId = subject1.id.value }
                val chapter3 = Chapter.new { title = "How to paint a portrait"; order = 1; subjectId = subject2.id.value }
                val chapter4 = Chapter.new { title = "The galaxy"; order = 1; subjectId = subject3.id.value }
                val chapter5 = Chapter.new { title = "Wild life in Africa"; order = 1; subjectId = subject4.id.value }

                val group1 = LessonGroup.new { title = "First numbers"; chapterId = chapter1.id.value }
                val group2 = LessonGroup.new { title = "Last numbers"; chapterId = chapter1.id.value }
                val group3 = LessonGroup.new { title = "Mammals"; chapterId = chapter5.id.value }
                val group4 = LessonGroup.new { title = "Birds"; chapterId = chapter5.id.value }
                val group5 = LessonGroup.new { title = "Snakes"; chapterId = chapter1.id.value }

                val lesson1 = Lesson.new { name = "from 1 to 10"; description = "Going from 1 to 10"; duration = 1000; streamUrl = "https://google.com"; groupId = group1.id.value }
                val lesson2 = Lesson.new { name = "from 11 to 20"; description = "Going from 11 to 20"; duration = 1000; streamUrl = "https://google.com"; groupId = group1.id.value }
                val lesson3 = Lesson.new { name = "from 21 to 30"; description = "Going from 21 to 30"; duration = 1000; streamUrl = "https://google.com"; groupId = group1.id.value }
                val lesson4 = Lesson.new { name = "Elephants"; description = "Some Elephants"; duration = 1000; streamUrl = "https://google.com"; groupId = group3.id.value }
                val lesson5 = Lesson.new { name = "Ostrich"; description = "Some Ostrich"; duration = 1000; streamUrl = "https://google.com"; groupId = group4.id.value }
                val lesson6 = Lesson.new { name = "Lions"; description = "Some Lions"; duration = 1000; streamUrl = "https://google.com"; groupId = group3.id.value }

                val user = User.new {
                    email = "pau@test.com"
                    password = "123456"
                }

                val reward = UserReward.new { this.user = user }


                val bookmark1 = UserBookmark.new { userId = user.id.value; lessonId = lesson1.id.value; bookmarkPosition = 200; description = "Some bookmark description" }
                val bookmark2 = UserBookmark.new { userId = user.id.value; lessonId = lesson1.id.value; bookmarkPosition = 400; description = "Some bookmark description" }
                val bookmark3 = UserBookmark.new { userId = user.id.value; lessonId = lesson5.id.value; bookmarkPosition = 200; description = "Some bookmark description" }
                val bookmark4 = UserBookmark.new { userId = user.id.value; lessonId = lesson4.id.value; bookmarkPosition = 200; description = "Some bookmark description" }
                val bookmark5 = UserBookmark.new { userId = user.id.value; lessonId = lesson6.id.value; bookmarkPosition = 200; description = "Some bookmark description" }

                val badge1 = UserBadge.new { obtainedTime = 12312323; this.reward = reward }
                val badge2 = UserBadge.new { obtainedTime = 21432414; this.reward = reward }
                val badge3 = UserBadge.new { obtainedTime = 12442124; this.reward = reward }

                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}