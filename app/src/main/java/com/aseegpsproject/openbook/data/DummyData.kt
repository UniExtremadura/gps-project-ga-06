package com.aseegpsproject.openbook.data

import com.aseegpsproject.openbook.data.apimodel.APIAuthor
import com.aseegpsproject.openbook.data.apimodel.APIWork
import com.aseegpsproject.openbook.data.apimodel.Authors
import com.aseegpsproject.openbook.data.apimodel.Counts
import com.aseegpsproject.openbook.data.apimodel.Doc
import com.aseegpsproject.openbook.data.apimodel.Rating
import com.aseegpsproject.openbook.data.apimodel.SearchQuery
import com.aseegpsproject.openbook.data.apimodel.Summary
import com.aseegpsproject.openbook.data.apimodel.TrendingQuery
import com.aseegpsproject.openbook.data.apimodel.TrendingWork
import com.aseegpsproject.openbook.data.apimodel.WorkAuthor

val dummyNetworkTrendingResponse = TrendingQuery(
    trendingWorks = arrayListOf(
        TrendingWork(
            key = "/works/OL1W",
            title = "To Kill a Mockingbird",
            firstPublishYear = 1960,
            coverI = 1,
            authorName = arrayListOf("Harper Lee"),
            authorKey = arrayListOf("/authors/OL1A")
        ),
        TrendingWork(
            key = "/works/OL2W",
            title = "The Adventures of Huckleberry Finn",
            firstPublishYear = 1884,
            coverI = 2,
            authorName = arrayListOf("Mark Twain"),
            authorKey = arrayListOf("/authors/OL2A")
        ),
        TrendingWork(
            key = "/works/OL3W",
            title = "The Great Gatsby",
            firstPublishYear = 1925,
            coverI = 3,
            authorName = arrayListOf("F. Scott Fitzgerald"),
            authorKey = arrayListOf("/authors/OL3A")
        ),
        TrendingWork(
            key = "/works/OL4W",
            title = "The Catcher in the Rye",
            firstPublishYear = 1951,
            coverI = 4,
            authorName = arrayListOf("J. D. Salinger"),
            authorKey = arrayListOf("/authors/OL4A")
        ),
        TrendingWork(
            key = "/works/OL5W",
            title = "1984",
            firstPublishYear = 1949,
            coverI = 5,
            authorName = arrayListOf("George Orwell"),
            authorKey = arrayListOf("/authors/OL5A")
        ),
        TrendingWork(
            key = "/works/OL6W",
            title = "Romeo and Juliet",
            firstPublishYear = 1597,
            coverI = 6,
            authorName = arrayListOf("William Shakespeare"),
            authorKey = arrayListOf("/authors/OL6A")
        ),
        TrendingWork(
            key = "/works/OL7W",
            title = "Harry Potter and the Sorcerer's Stone",
            firstPublishYear = 1997,
            coverI = 7,
            authorName = arrayListOf("J. K. Rowling"),
            authorKey = arrayListOf("/authors/OL7A")
        ),
        TrendingWork(
            key = "/works/OL8W",
            title = "Pride and Prejudice",
            firstPublishYear = 1813,
            coverI = 8,
            authorName = arrayListOf("Jane Austen"),
            authorKey = arrayListOf("/authors/OL8A")
        ),
        TrendingWork(
            key = "/works/OL9W",
            title = "The Shining",
            firstPublishYear = 1977,
            coverI = 9,
            authorName = arrayListOf("Stephen King"),
            authorKey = arrayListOf("/authors/OL9A")
        )
    )
)

val dummyNetworkWorkDetailResponse = APIWork(
    title = "To Kill a Mockingbird",
    authors = arrayListOf(Authors(WorkAuthor("/authors/OL1A"))),
    key = "/works/OL1W",
    description = "A novel about the injustices of the prejudiced social hierarchy in the 20th century society.",
    subjects = arrayListOf("Classic Literature", "Historical Fiction"),
    covers = arrayListOf(1, 2, 3)
)

val dummyNetworkSearchWorksResponse = SearchQuery(
    numFound = 9,
    start = 0,
    docs = arrayListOf(
        Doc(
            key = "/works/OL1W",
            title = "To Kill a Mockingbird",
            firstPublishYear = 1960,
            coverI = 1,
            authorName = arrayListOf("Harper Lee"),
            authorKey = arrayListOf("/authors/OL1A")
        ),
        Doc(
            key = "/works/OL2W",
            title = "The Adventures of Huckleberry Finn",
            firstPublishYear = 1884,
            coverI = 2,
            authorName = arrayListOf("Mark Twain"),
            authorKey = arrayListOf("/authors/OL2A")
        ),
        Doc(
            key = "/works/OL3W",
            title = "The Great Gatsby",
            firstPublishYear = 1925,
            coverI = 3,
            authorName = arrayListOf("F. Scott Fitzgerald"),
            authorKey = arrayListOf("/authors/OL3A")
        ),
        Doc(
            key = "/works/OL4W",
            title = "The Catcher in the Rye",
            firstPublishYear = 1951,
            coverI = 4,
            authorName = arrayListOf("J. D. Salinger"),
            authorKey = arrayListOf("/authors/OL4A")
        ),
        Doc(
            key = "/works/OL5W",
            title = "1984",
            firstPublishYear = 1949,
            coverI = 5,
            authorName = arrayListOf("George Orwell"),
            authorKey = arrayListOf("/authors/OL5A")
        ),
        Doc(
            key = "/works/OL6W",
            title = "Romeo and Juliet",
            firstPublishYear = 1597,
            coverI = 6,
            authorName = arrayListOf("William Shakespeare"),
            authorKey = arrayListOf("/authors/OL6A")
        ),
        Doc(
            key = "/works/OL7W",
            title = "Harry Potter and the Sorcerer's Stone",
            firstPublishYear = 1997,
            coverI = 7,
            authorName = arrayListOf("J. K. Rowling"),
            authorKey = arrayListOf("/authors/OL7A")
        ),
        Doc(
            key = "/works/OL8W",
            title = "Pride and Prejudice",
            firstPublishYear = 1813,
            coverI = 8,
            authorName = arrayListOf("Jane Austen"),
            authorKey = arrayListOf("/authors/OL8A")
        ),
        Doc(
            key = "/works/OL9W",
            title = "The Shining",
            firstPublishYear = 1977,
            coverI = 9,
            authorName = arrayListOf("Stephen King"),
            authorKey = arrayListOf("/authors/OL9A")
        )
    )
)

val dummyNetworkSearchAuthorsResponse = SearchQuery(
    numFound = 10,
    start = 0,
    docs = arrayListOf(
        Doc(
            key = "OL66452A",
            name = "Harper Lee",
            alternateNames = arrayListOf("Nelle Harper Lee"),
            birthDate = "1926",
            deathDate = "2016",
            topWork = "/works/OL1W",
            workCount = 1
        ),
        Doc(
            key = "OL66451A",
            name = "Mark Twain",
            alternateNames = arrayListOf("Samuel Clemens"),
            birthDate = "1835",
            deathDate = "1910",
            topWork = "/works/OL2W",
            workCount = 2
        ),
        Doc(
            key = "OL66450A",
            name = "F. Scott Fitzgerald",
            alternateNames = arrayListOf("Francis Scott Key Fitzgerald"),
            birthDate = "1896",
            deathDate = "1940",
            topWork = "/works/OL3W",
            workCount = 3
        ),
        Doc(
            key = "OL66459A",
            name = "J. D. Salinger",
            alternateNames = arrayListOf("Jerome David Salinger"),
            birthDate = "1919",
            deathDate = "2010",
            topWork = "/works/OL4W",
            workCount = 4
        ),
        Doc(
            key = "OL66458A",
            name = "George Orwell",
            alternateNames = arrayListOf("Eric Arthur Blair"),
            birthDate = "1903",
            deathDate = "1950",
            topWork = "/works/OL5W",
            workCount = 5
        ),
        Doc(
            key = "OL66457A",
            name = "William Shakespeare",
            alternateNames = arrayListOf(
                "William Shakspere",
                "William Shakspeare",
                "William Shakespear",
                "William Shaksper",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear",
                "William Shakespear",
                "William Shakspeare",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear",
                "William Shakespear",
                "William Shakspeare",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear",
                "William Shakespear",
                "William Shakspeare",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear",
                "William Shakespear",
                "William Shakspeare",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear",
                "William Shakespear",
                "William Shakspeare",
                "William Shakspe",
                "William Shakspea",
                "William Shakespe",
                "William Shakspear"
            ),
            birthDate = "1564",
            deathDate = "1616",
            topWork = "/works/OL6W",
            workCount = 6
        ),
        Doc(
            key = "OL66456A",
            name = "J. K. Rowling",
            alternateNames = arrayListOf(
                "Newt Scamander",
                "Kennilworthy Whisp",
                "Robert Galbraith"
            ),
            birthDate = "1965",
            deathDate = null,
            topWork = "/works/OL7W",
            workCount = 7
        ),
        Doc(
            key = "OL66455A",
            name = "Jane Austen",
            alternateNames = arrayListOf("Jane Austin"),
            birthDate = "1775",
            deathDate = "1817",
            topWork = "/works/OL8W",
            workCount = 8
        ),
        Doc(
            key = "OL66454A",
            name = "Stephen King",
            alternateNames = arrayListOf("Richard Bachman", "John Swithen"),
            birthDate = "1947",
            deathDate = null,
            topWork = "/works/OL9W",
            workCount = 9
        ),
        Doc(
            key = "OL66453A",
            name = "John Steinbeck",
            alternateNames = arrayListOf("John Ernst Steinbeck", "John Ernst Steinbeck, Jr."),
            birthDate = "1902",
            deathDate = "1968",
            topWork = "/works/OL10W",
            workCount = 10
        )
    )
)

val dummyNetworkAuthorDetailResponse = APIAuthor(
    personalName = "Harper Lee",
    name = "Harper Lee",
    birthDate = "1926",
    deathDate = "2016",
    bio = "Nelle Harper Lee was born on April 28, 1926 in Monroeville, Alabama. She is the youngest of four children of Amasa Coleman Lee and Frances Cunningham Finch Lee. Harper Lee attended Huntingdon College 1944-45, studied law at University of Alabama 1945-49, and studied one year at Oxford University. In the 1950s she worked as a reservation clerk with Eastern Air Lines in New York City.",
    photos = arrayListOf(1, 2, 3),
    alternateNames = arrayListOf("Nelle Harper Lee"),
    fullerName = "Nelle Harper Lee",
    key = "OL66452A"
)

val dummyNetworkWorkRatingsResponse = Rating(
    summary = Summary(average = 4.5, count = 2, sortable = 4.5),
    counts = Counts(one = 0, two = 0, three = 0, four = 1, five = 1)
)