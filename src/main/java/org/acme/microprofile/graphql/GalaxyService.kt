package org.acme.microprofile.graphql

import java.time.LocalDate
import java.time.Month
import java.util.*
import java.util.stream.Collectors
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GalaxyService {
    private val heroes: MutableList<Hero> = ArrayList()
    private val films: MutableList<Film> = ArrayList()

    init {
        val aNewHope = Film()
        aNewHope.title = "A New Hope"
        aNewHope.releaseDate = LocalDate.of(1977, Month.MAY, 25)
        aNewHope.episodeID = 4
        aNewHope.director = "George Lucas"
        val theEmpireStrikesBack = Film()
        theEmpireStrikesBack.title = "The Empire Strikes Back"
        theEmpireStrikesBack.releaseDate = LocalDate.of(1980, Month.MAY, 21)
        theEmpireStrikesBack.episodeID = 5
        theEmpireStrikesBack.director = "George Lucas"
        val returnOfTheJedi = Film()
        returnOfTheJedi.title = "Return Of The Jedi"
        returnOfTheJedi.releaseDate = LocalDate.of(1983, Month.MAY, 25)
        returnOfTheJedi.episodeID = 6
        returnOfTheJedi.director = "George Lucas"
        films.add(aNewHope)
        films.add(theEmpireStrikesBack)
        films.add(returnOfTheJedi)
        val luke = Hero()
        luke.name = "Luke"
        luke.surname = "Skywalker"
        luke.height = 1.7
        luke.mass = 73
        luke.lightSaber = LightSaber.GREEN
        luke.darkSide = false
        luke.episodeIds.addAll(Arrays.asList(4, 5, 6))
        val leia = Hero()
        leia.name = "Leia"
        leia.surname = "Organa"
        leia.height = 1.5
        leia.mass = 51
        leia.darkSide = false
        leia.episodeIds.addAll(Arrays.asList(4, 5, 6))
        val vader = Hero()
        vader.name = "Darth"
        vader.surname = "Vader"
        vader.height = 1.9
        vader.mass = 89
        vader.darkSide = true
        vader.lightSaber = LightSaber.RED
        vader.episodeIds.addAll(Arrays.asList(4, 5, 6))
        heroes.add(luke)
        heroes.add(leia)
        heroes.add(vader)
    }

    val allFilms: List<Film>
        get() = films

    fun getFilm(id: Int): Film {
        return films[id]
    }

    fun getHeroesByFilm(film: Film): List<Hero> {
        return heroes.stream()
            .filter { hero: Hero -> hero.episodeIds.contains(film.episodeID) }
            .collect(Collectors.toList())
    }

    fun addHero(hero: Hero) {
        heroes.add(hero)
    }

    fun deleteHero(id: Int): Hero {
        return heroes.removeAt(id)
    }

    fun getHeroesBySurname(surname: String): List<Hero> {
        return heroes.stream()
            .filter { hero: Hero -> hero.surname == surname }
            .collect(Collectors.toList())
    }
}