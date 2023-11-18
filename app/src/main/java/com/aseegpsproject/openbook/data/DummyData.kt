package com.aseegpsproject.openbook.data

import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.model.Book

val dummyBooks = listOf(
    Book(
        id = 1,
        title = "The Hunger Games",
        author = "Suzanne Collins",
        year = "2008",
        description = "Could you survive on your own, in the wild, with everyone out to make sure you don't live to see the morning?",
        isbn = "978-0-439-02348-1",
        coverImage = R.drawable.the_hunger_games
    ),
    Book(
        id = 2,
        title = "Harry Potter and the Philosopher's Stone",
        author = "J.K. Rowling",
        year = "1997",
        description = "Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive.",
        isbn = "978-0-7475-3269-6",
        coverImage = R.drawable.harry_potter_and_the_philosophers_stone
    ),
    Book(
        id = 3,
        title = "Twilight",
        author = "Stephenie Meyer",
        year = "2005",
        description = "About three things I was absolutely positive.",
        isbn = "978-0-316-16017-9",
        coverImage = R.drawable.twilight
    ),
    Book(
        id = 4,
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        year = "1960",
        description = "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.",
        isbn = "978-0-446-31078-9",
        coverImage = R.drawable.to_kill_a_mockingbird
    ),
    Book(
        id = 7,
        title = "The Hobbit",
        author = "J.R.R. Tolkien",
        year = "1937",
        description = "Bilbo Baggins is a hobbit who enjoys a comfortable, unambitious life, rarely traveling any farther than his pantry or cellar.",
        isbn = "978-0-345-33968-3",
        coverImage = R.drawable.the_hobbit
    ),
    Book(
        id = 8,
        title = "The Catcher in the Rye",
        author = "J.D. Salinger",
        year = "1951",
        description = "The hero-narrator of The Catcher in the Rye is an ancient child of sixteen, a native New Yorker named Holden Caulfield.",
        isbn = "978-0-316-76953-1",
        coverImage = R.drawable.the_catcher_in_the_rye
    )
)