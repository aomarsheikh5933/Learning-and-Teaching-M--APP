package com.student.diary.models
/**
 * Represents contact information.
 */
data class Contact(
    var name: String = "",
    var email: String = "",
    var office: String = "",
    var offile_hour: String = "",
    var phone: String = "",
    var position: String = "",
    var type: String = "",
    var course: MutableList<Course> = mutableListOf() // List of associated courses
)

/**
 * Represents a single course.
 */
data class Course(
    var name: String = "",
    var course_code: String = ""
)

/**
 * Represents detailed information about courses.
 */
data class Courses(
    var courseCode: String = "",   // Course code
    var courseTitle: String = "",  // Course title
    var contactHours: String = "", // Contact hours
    var days: String = "",         // Days of the week
    var time: String = ""          // Time of the course
)

/**
 * Represents an event.
 */
data class Event(
    var day: String = "",   // Event day
    var note: String = "",  // Event note
    var time: String = "",  // Event time
    var type: String = ""   // Event type
)

/**
 * Represents news item.
 */
data class News(
    var highlights: String = "", // News highlights
    var keyword: String = "",    // News keyword
    var title: String = ""       // News title
)
