package com.example.iawaketestapp.data.repository

import com.example.iawaketestapp.data.models.MediaResponse
import com.example.iawaketestapp.data.models.toModels
import com.example.iawaketestapp.data.service.MediaService
import com.example.iawaketestapp.domain.models.ProgramModel
import com.example.iawaketestapp.domain.models.TrackModel
import com.example.iawaketestapp.domain.repository.MediaRepository
import com.google.gson.Gson
import java.lang.Exception
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val service: MediaService
) : MediaRepository {

    private var cache: List<ProgramModel> = emptyList()

    override suspend fun loadPrograms(): List<ProgramModel> {
        if (cache.isEmpty()) {
            cache = service.loadMedia().programs?.toModels() ?: emptyList()
//            cache = readResponse().programs?.toModels() ?: emptyList()
        }
        return cache
    }

    override suspend fun getTracksForProgram(id: String): List<TrackModel> =
        cache.firstOrNull { it.id == id }?.tracks ?: emptyList()

    private fun readResponse(): MediaResponse {
        val gson = Gson()
        val response = "{\n" +
                "  \"_type\": \"MediaLibrary\",\n" +
                "  \"programs\": [\n" +
                "    {\n" +
                "      \"_type\": \"Program\",\n" +
                "      \"id\": \"iAwake Explore\",\n" +
                "      \"folders\": [\n" +
                "        {\n" +
                "          \"_type\": \"ProgramFolder\",\n" +
                "          \"title\": \"iAwake Explore\",\n" +
                "          \"cover\": {\n" +
                "            \"_type\": \"Cover\",\n" +
                "            \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/Schumann%20Holophonic/cover.jpg?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1553784479&Signature=Cp4fyxbJKKOO22ULTzvPcMPRFUQ%3D\",\n" +
                "            \"thumbnail\": \"/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAQABADASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAABQb/xAAVAQEBAAAAAAAAAAAAAAAAAAAGB//aAAwDAQACEAMQAAABdLlync4//8QAGRAAAgMBAAAAAAAAAAAAAAAAAQIDBAUA/9oACAEBAAEFAk1wZt7QV1qWAetzlj//xAAYEQEAAwEAAAAAAAAAAAAAAAADAAIjsf/aAAgBAwEBPwElR8b8n//EABgRAAIDAAAAAAAAAAAAAAAAAAACARIT/9oACAECAQE/AcEVrwf/xAAeEAABBAEFAAAAAAAAAAAAAAABAAIREiEDUWFiof/aAAgBAQAGPwKLnPiDRq36otMZ3RaYI4C//8QAGhABAAIDAQAAAAAAAAAAAAAAAQAhETGBsf/aAAgBAQABPyHECCW1Y3XirzUKRyV2P4R2Hs//2gAMAwEAAgADAAAAEM//xAAbEQABBAMAAAAAAAAAAAAAAAARAAEhMUGhwf/aAAgBAwEBPxA5ktw1PF//xAAYEQACAwAAAAAAAAAAAAAAAAAAEUFh4f/aAAgBAgEBPxBFBeH/xAAaEAEBAAMBAQAAAAAAAAAAAAABEQAhMUFR/9oACAEBAAE/EB4Q0wb8POseZCi5IX1IdImItVuak1sU5h1JpRsnBsz/2Q==\",\n" +
                "            \"resolutions\": [\n" +
                "              {\n" +
                "                \"_type\": \"CoverResolution\",\n" +
                "                \"url\": \"https://s3-us-west-2.amazonaws.com/iawake-mobile-thumbnails/Gamma-Burst%2Fa750848c0b84896b2db7f4fff49e31b1%2F500.jpg\",\n" +
                "                \"size\": 500\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          \"descriptionHTML\": \"<html>\\n<body>\\n<h2>THE PULSE OF NATURE</h2>\\n\\n<p><em><strong>For A Centering, Grounding, Refreshing Meditation</strong></em></p>\\n\\n<p><strong>Schumann Holophonic</strong>, part of&nbsp;the&nbsp;NeuroFlow Series,&nbsp;features the holophonic (3D) sounds of a thunderstorm in Track 1 and the 3D sounds of the sea, with a background of ambient music, in Track 2.&nbsp;Combined with our latest breakthrough brainwave and biofield entrainment technology, this deeply calming meditation can transport you into deep states of meditation very quickly.</p>\\n<p><strong>This program features a unique biofield and brainwave entrainment&nbsp;formula&nbsp;designed to help you naturally experience:</strong></p>\\n\\n<ul>\\n<li>Relaxed</li>\\n<li>Grounded</li>\\n<li>Balanced</li>\\n<li>Harmonious</li>\\n<li>Clear</li>\\n<li>Intuitive</li>\\n</ul>\\n\\n<p><strong>May also benefit:</strong></p>\\n\\n<ul>\\n<li>Reading and learning abilities</li>\\n<li>Reduced sleep needs</li>\\n<li>Intuition</li>\\n<li>Psychic abilities</li>\\n</ul>\\n</body>\\n</html>\",\n" +
                "          \"folders\": [\n" +
                "            \"string\"\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"title\": \"iAwake Explore\",\n" +
                "      \"isFree\": true,\n" +
                "      \"isAvailable\": true,\n" +
                "      \"isFeatured\": true,\n" +
                "      \"cover\": {\n" +
                "        \"_type\": \"Cover\",\n" +
                "        \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/Schumann%20Holophonic/cover.jpg?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1553784479&Signature=Cp4fyxbJKKOO22ULTzvPcMPRFUQ%3D\",\n" +
                "        \"thumbnail\": \"/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAQABADASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAABQb/xAAVAQEBAAAAAAAAAAAAAAAAAAAGB//aAAwDAQACEAMQAAABdLlync4//8QAGRAAAgMBAAAAAAAAAAAAAAAAAQIDBAUA/9oACAEBAAEFAk1wZt7QV1qWAetzlj//xAAYEQEAAwEAAAAAAAAAAAAAAAADAAIjsf/aAAgBAwEBPwElR8b8n//EABgRAAIDAAAAAAAAAAAAAAAAAAACARIT/9oACAECAQE/AcEVrwf/xAAeEAABBAEFAAAAAAAAAAAAAAABAAIREiEDUWFiof/aAAgBAQAGPwKLnPiDRq36otMZ3RaYI4C//8QAGhABAAIDAQAAAAAAAAAAAAAAAQAhETGBsf/aAAgBAQABPyHECCW1Y3XirzUKRyV2P4R2Hs//2gAMAwEAAgADAAAAEM//xAAbEQABBAMAAAAAAAAAAAAAAAARAAEhMUGhwf/aAAgBAwEBPxA5ktw1PF//xAAYEQACAwAAAAAAAAAAAAAAAAAAEUFh4f/aAAgBAgEBPxBFBeH/xAAaEAEBAAMBAQAAAAAAAAAAAAABEQAhMUFR/9oACAEBAAE/EB4Q0wb8POseZCi5IX1IdImItVuak1sU5h1JpRsnBsz/2Q==\",\n" +
                "        \"resolutions\": [\n" +
                "          {\n" +
                "            \"_type\": \"CoverResolution\",\n" +
                "            \"url\": \"https://s3-us-west-2.amazonaws.com/iawake-mobile-thumbnails/Gamma-Burst%2Fa750848c0b84896b2db7f4fff49e31b1%2F500.jpg\",\n" +
                "            \"size\": 500\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"headphones\": false,\n" +
                "      \"descriptionHTML\": \"<html>\\n<body>\\n<h2>THE PULSE OF NATURE</h2>\\n\\n<p><em><strong>For A Centering, Grounding, Refreshing Meditation</strong></em></p>\\n\\n<p><strong>Schumann Holophonic</strong>, part of&nbsp;the&nbsp;NeuroFlow Series,&nbsp;features the holophonic (3D) sounds of a thunderstorm in Track 1 and the 3D sounds of the sea, with a background of ambient music, in Track 2.&nbsp;Combined with our latest breakthrough brainwave and biofield entrainment technology, this deeply calming meditation can transport you into deep states of meditation very quickly.</p>\\n<p><strong>This program features a unique biofield and brainwave entrainment&nbsp;formula&nbsp;designed to help you naturally experience:</strong></p>\\n\\n<ul>\\n<li>Relaxed</li>\\n<li>Grounded</li>\\n<li>Balanced</li>\\n<li>Harmonious</li>\\n<li>Clear</li>\\n<li>Intuitive</li>\\n</ul>\\n\\n<p><strong>May also benefit:</strong></p>\\n\\n<ul>\\n<li>Reading and learning abilities</li>\\n<li>Reduced sleep needs</li>\\n<li>Intuition</li>\\n<li>Psychic abilities</li>\\n</ul>\\n</body>\\n</html>\",\n" +
                "      \"tracks\": [\n" +
                "        {\n" +
                "          \"_type\": \"Track\",\n" +
                "          \"key\": \"iAwake Explore/01 Heartwave Music.mp3\",\n" +
                "          \"title\": \"Heartwave Music\",\n" +
                "          \"folderPath\": [\n" +
                "            [\n" +
                "              \"iAwake Explore\",\n" +
                "              \"Heartwave Music\"\n" +
                "            ]\n" +
                "          ],\n" +
                "          \"order\": 1,\n" +
                "          \"duration\": 329,\n" +
                "          \"isAvailable\": true,\n" +
                "          \"media\": {\n" +
                "            \"_type\": \"TrackMedia\",\n" +
                "            \"mp3\": {\n" +
                "              \"_type\": \"TrackMediaFile\",\n" +
                "              \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1554308147&Signature=hcinkIQSwrKXTOBCJkjDjvkcr4M%3D\",\n" +
                "              \"headUrl\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1556011644&Signature=2w%2FJL%2BuOHt3iK%2BxjHMl%2FSRy%2F5i4%3D\"\n" +
                "            },\n" +
                "            \"flac\": {\n" +
                "              \"_type\": \"TrackMediaFile\",\n" +
                "              \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1554308147&Signature=hcinkIQSwrKXTOBCJkjDjvkcr4M%3D\",\n" +
                "              \"headUrl\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1556011644&Signature=2w%2FJL%2BuOHt3iK%2BxjHMl%2FSRy%2F5i4%3D\"\n" +
                "            },\n" +
                "            \"m4a\": {\n" +
                "              \"_type\": \"TrackMediaFile\",\n" +
                "              \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1554308147&Signature=hcinkIQSwrKXTOBCJkjDjvkcr4M%3D\",\n" +
                "              \"headUrl\": \"https://iawake-mobile-content.s3.amazonaws.com/iAwake%20Explore/02%20Profound%20Meditation.mp3?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1556011644&Signature=2w%2FJL%2BuOHt3iK%2BxjHMl%2FSRy%2F5i4%3D\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"categories\": [\n" +
                "    {\n" +
                "      \"_type\": \"Category\",\n" +
                "      \"id\": \"Meditation [Free]\",\n" +
                "      \"title\": \"Meditation\",\n" +
                "      \"cover\": {\n" +
                "        \"_type\": \"Cover\",\n" +
                "        \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/Schumann%20Holophonic/cover.jpg?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1553784479&Signature=Cp4fyxbJKKOO22ULTzvPcMPRFUQ%3D\",\n" +
                "        \"thumbnail\": \"/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAQABADASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAABQb/xAAVAQEBAAAAAAAAAAAAAAAAAAAGB//aAAwDAQACEAMQAAABdLlync4//8QAGRAAAgMBAAAAAAAAAAAAAAAAAQIDBAUA/9oACAEBAAEFAk1wZt7QV1qWAetzlj//xAAYEQEAAwEAAAAAAAAAAAAAAAADAAIjsf/aAAgBAwEBPwElR8b8n//EABgRAAIDAAAAAAAAAAAAAAAAAAACARIT/9oACAECAQE/AcEVrwf/xAAeEAABBAEFAAAAAAAAAAAAAAABAAIREiEDUWFiof/aAAgBAQAGPwKLnPiDRq36otMZ3RaYI4C//8QAGhABAAIDAQAAAAAAAAAAAAAAAQAhETGBsf/aAAgBAQABPyHECCW1Y3XirzUKRyV2P4R2Hs//2gAMAwEAAgADAAAAEM//xAAbEQABBAMAAAAAAAAAAAAAAAARAAEhMUGhwf/aAAgBAwEBPxA5ktw1PF//xAAYEQACAwAAAAAAAAAAAAAAAAAAEUFh4f/aAAgBAgEBPxBFBeH/xAAaEAEBAAMBAQAAAAAAAAAAAAABEQAhMUFR/9oACAEBAAE/EB4Q0wb8POseZCi5IX1IdImItVuak1sU5h1JpRsnBsz/2Q==\",\n" +
                "        \"resolutions\": [\n" +
                "          {\n" +
                "            \"_type\": \"CoverResolution\",\n" +
                "            \"url\": \"https://s3-us-west-2.amazonaws.com/iawake-mobile-thumbnails/Gamma-Burst%2Fa750848c0b84896b2db7f4fff49e31b1%2F500.jpg\",\n" +
                "            \"size\": 500\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"subtitle\": \"Clean your head\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"playlists\": [\n" +
                "    {\n" +
                "      \"_type\": \"Playlist\",\n" +
                "      \"id\": \"Meditation [Free]\",\n" +
                "      \"title\": \"Meditation\",\n" +
                "      \"cover\": {\n" +
                "        \"_type\": \"Cover\",\n" +
                "        \"url\": \"https://iawake-mobile-content.s3.amazonaws.com/Schumann%20Holophonic/cover.jpg?AWSAccessKeyId=AKIAIHM5JVUSRBUK643A&Expires=1553784479&Signature=Cp4fyxbJKKOO22ULTzvPcMPRFUQ%3D\",\n" +
                "        \"thumbnail\": \"/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAQABADASIAAhEBAxEB/8QAFQABAQAAAAAAAAAAAAAAAAAABQb/xAAVAQEBAAAAAAAAAAAAAAAAAAAGB//aAAwDAQACEAMQAAABdLlync4//8QAGRAAAgMBAAAAAAAAAAAAAAAAAQIDBAUA/9oACAEBAAEFAk1wZt7QV1qWAetzlj//xAAYEQEAAwEAAAAAAAAAAAAAAAADAAIjsf/aAAgBAwEBPwElR8b8n//EABgRAAIDAAAAAAAAAAAAAAAAAAACARIT/9oACAECAQE/AcEVrwf/xAAeEAABBAEFAAAAAAAAAAAAAAABAAIREiEDUWFiof/aAAgBAQAGPwKLnPiDRq36otMZ3RaYI4C//8QAGhABAAIDAQAAAAAAAAAAAAAAAQAhETGBsf/aAAgBAQABPyHECCW1Y3XirzUKRyV2P4R2Hs//2gAMAwEAAgADAAAAEM//xAAbEQABBAMAAAAAAAAAAAAAAAARAAEhMUGhwf/aAAgBAwEBPxA5ktw1PF//xAAYEQACAwAAAAAAAAAAAAAAAAAAEUFh4f/aAAgBAgEBPxBFBeH/xAAaEAEBAAMBAQAAAAAAAAAAAAABEQAhMUFR/9oACAEBAAE/EB4Q0wb8POseZCi5IX1IdImItVuak1sU5h1JpRsnBsz/2Q==\",\n" +
                "        \"resolutions\": [\n" +
                "          {\n" +
                "            \"_type\": \"CoverResolution\",\n" +
                "            \"url\": \"https://s3-us-west-2.amazonaws.com/iawake-mobile-thumbnails/Gamma-Burst%2Fa750848c0b84896b2db7f4fff49e31b1%2F500.jpg\",\n" +
                "            \"size\": 500\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"descriptionHTML\": \"<html>\\n<body>\\n<h2>THE PULSE OF NATURE</h2>\\n\\n<p><em><strong>For A Centering, Grounding, Refreshing Meditation</strong></em></p>\\n\\n<p><strong>Schumann Holophonic</strong>, part of&nbsp;the&nbsp;NeuroFlow Series,&nbsp;features the holophonic (3D) sounds of a thunderstorm in Track 1 and the 3D sounds of the sea, with a background of ambient music, in Track 2.&nbsp;Combined with our latest breakthrough brainwave and biofield entrainment technology, this deeply calming meditation can transport you into deep states of meditation very quickly.</p>\\n<p><strong>This program features a unique biofield and brainwave entrainment&nbsp;formula&nbsp;designed to help you naturally experience:</strong></p>\\n\\n<ul>\\n<li>Relaxed</li>\\n<li>Grounded</li>\\n<li>Balanced</li>\\n<li>Harmonious</li>\\n<li>Clear</li>\\n<li>Intuitive</li>\\n</ul>\\n\\n<p><strong>May also benefit:</strong></p>\\n\\n<ul>\\n<li>Reading and learning abilities</li>\\n<li>Reduced sleep needs</li>\\n<li>Intuition</li>\\n<li>Psychic abilities</li>\\n</ul>\\n</body>\\n</html>\",\n" +
                "      \"order\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"plan\": {\n" +
                "    \"name\": \"Full Access\",\n" +
                "    \"startDate\": \"2019-11-13T16:24:22+02:00\",\n" +
                "    \"endDate\": \"2019-12-13T16:24:22+02:00\"\n" +
                "  }\n" +
                "}"

        try {
            val result = gson.fromJson(response, MediaResponse::class.java)
            return result
        } catch (e: Exception) {
            return MediaResponse()
        }
    }
}