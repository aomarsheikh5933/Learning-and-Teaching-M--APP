package com.student.diary.util

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import  com.student.diary.models.Courses
import com.student.diary.models.Event
import com.student.diary.models.News

/**
 * Factory class for parsing XML files related to courses, events, and news from assets.
 */
class XmlParserFactory {

    /**
     * Parses a courses XML file from assets into a Courses object.
     *
     * @param context The context used to access assets.
     * @param fileName The name of the XML file in assets.
     * @return A Courses object parsed from the XML file, or null if parsing fails.
     */
    fun parseCoursesXmlFromAssets(context: Context, fileName: String): Courses? {
        var inputStream: InputStream? = null
        val courses = Courses()

        try {
            // Open the file from assets
            inputStream = context.assets.open(fileName)

            // Create XML Pull Parser
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (tagName) {
                            "courses" -> {
                                courses.courseCode = parser.getAttributeValue(null, "Course_Code")
                                courses.courseTitle = parser.getAttributeValue(null, "courseTitle")
                                courses.contactHours =
                                    parser.getAttributeValue(null, "contactHours")
                                courses.days = parser.getAttributeValue(null, "Days")
                                courses.time = parser.getAttributeValue(null, "Time")
                            }
                        }
                    }
                }

                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }

        return courses
    }

    /**
     * Parses an events XML file from assets into a list of Event objects.
     *
     * @param context The context used to access assets.
     * @param fileName The name of the XML file in assets.
     * @return A list of Event objects parsed from the XML file.
     */
    fun parseEventsXmlFromAssets(context: Context, fileName: String): List<Event> {
        val events = mutableListOf<Event>()
        var inputStream: InputStream? = null

        try {
            // Open the file from assets
            inputStream = context.assets.open(fileName)

            // Create XML Pull Parser
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var currentEvent: Event? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == "event") {
                            currentEvent = Event()
                            currentEvent.day = parser.getAttributeValue(null, "day")
                            currentEvent.note = parser.getAttributeValue(null, "note")
                            currentEvent.time = parser.getAttributeValue(null, "time")
                            currentEvent.type = parser.getAttributeValue(null, "type")
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName == "event") {
                            currentEvent?.let { events.add(it) }
                        }
                    }
                }

                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }

        return events
    }

    /**
     * Parses a news XML file from assets into a News object.
     *
     * @param context The context used to access assets.
     * @param fileName The name of the XML file in assets.
     * @return A News object parsed from the XML file, or null if parsing fails.
     */
    fun parseNewsXmlFromAssets(context: Context, fileName: String): News? {
        var inputStream: InputStream? = null
        val news = News()

        try {
            // Open the file from assets
            inputStream = context.assets.open(fileName)

            // Create XML Pull Parser
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name

                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == "news") {
                            news.highlights = parser.getAttributeValue(null, "highlights")
                            news.keyword = parser.getAttributeValue(null, "keyword")
                            news.title = parser.getAttributeValue(null, "title")
                        }
                    }
                }

                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }

        return news
    }
}
