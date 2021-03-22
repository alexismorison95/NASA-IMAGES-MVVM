package com.morris.nasaimages.data.local.main

import com.morris.nasaimages.data.model.MainItem

class MainDataSource {

    fun getMainItems(): List<MainItem> {

        return listOf(
            MainItem(
                1,
                "APOD",
                "Astronomy Picture of the Day",
                "Astronomy Picture of the Day (APOD) is a website provided by NASA and Michigan Technological University (MTU). According to the website, \"Each day a different image or photograph of our universe is featured, along with a brief explanation written by a professional astronomer.\" \n" +
                        "The photograph does not necessarily correspond to a celestial event on the exact day that it is displayed, and images are sometimes repeated. However, the pictures and descriptions often relate to current events in astronomy and space exploration."),
            MainItem(
                2,
                "Mars Rover Photos",
                "Image data gathered by NASA\\'s Perseverance ,Curiosity, Opportunity, and Spirit rovers on Mars",
                "This API is designed to collect image data gathered by NASA's Curiosity, Opportunity, and Spirit rovers on Mars and make it more easily available to other developers, educators, and citizen scientists. This API is maintained by Chris Cerami.\n" +
                        "Each rover has its own set of photos stored in the database, which can be queried separately. There are several possible queries that can be made against the API. Photos are organized by the sol (Martian rotation or day) on which they were taken, counting up from the rover's landing date. A photo taken on Curiosity's 1000th Martian sol exploring Mars, for example, will have a sol attribute of 1000. If instead you prefer to search by the Earth date on which a photo was taken, you can do that too."),

            MainItem(
                3,
                "NASA Image Library",
                "Access to the NASA Image Library site at images.nasa.gov",
                "NASA's image library, images.nasa.gov, consolidates imagery and videos in one searchable locations. Users can download content in multiple sizes and resolutions and see the metadata associated with images, including EXIF/camera data on many images."),
        )
    }
}