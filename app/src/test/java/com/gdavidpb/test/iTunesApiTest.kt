package com.gdavidpb.test

import com.gdavidpb.test.data.source.remote.iTunesSearchApi
import com.gdavidpb.test.utils.extensions.getOrThrow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class iTunesApiTest : KoinTest {

    private val artistSearchQuery = "Michael Jackson"
    private val artistIdQuery = 32940L
    private val albumIdQuery = 159292399L

    private val api: iTunesSearchApi by inject()

    @Before
    fun `start koin`() {
        startKoin {
            modules(listOf(testModule))
        }
    }

    @Test
    fun `should get artists from iTunes api`() {
        val result = runBlocking {
            api.searchArtists(terms = artistSearchQuery).getOrThrow()
        }

        assertNotNull(result)

        assertTrue(result.resultCount > 0)
    }

    @Test
    fun `should get the artist's albums from iTunes api`() {
        val result = runBlocking {
            api.lookupAlbums(artistId = artistIdQuery).getOrThrow()
        }

        assertNotNull(result)

        assertTrue(result.resultCount > 0)
    }

    @Test
    fun `should get the album's songs from iTunes api`() {
        val result = runBlocking {
            api.lookupTracks(albumId = albumIdQuery).getOrThrow()
        }

        assertNotNull(result)

        assertTrue(result.resultCount > 0)
    }

    @After
    fun `stop koin`() {
        stopKoin()
    }
}
