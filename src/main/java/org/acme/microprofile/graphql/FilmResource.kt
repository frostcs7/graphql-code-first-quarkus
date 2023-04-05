package org.acme.microprofile.graphql

import io.smallrye.graphql.api.Subscription
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor
import org.eclipse.microprofile.graphql.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@GraphQLApi
@ApplicationScoped
class FilmResource {
    @Inject
    lateinit var service: GalaxyService
    private val processor = BroadcastProcessor.create<Hero>()

    @get:Description("Get all Films from a galaxy far far away")
    @get:Query("allFilms")
    val allFilms: List<Film>
        get() = service.allFilms

    @Query
    @Description("Get a Films from a galaxy far far away")
    fun getFilm(@Name("filmId") id: Int): Film {
        return service.getFilm(id)
    }

    fun heroes(@Source film: Film): List<Hero> {
        return service.getHeroesByFilm(film)
    }

    @Mutation
    fun createHero(hero: Hero): Hero {
        service.addHero(hero)
        processor.onNext(hero)
        return hero
    }

    @Mutation
    fun deleteHero(id: Int): Hero {
        return service.deleteHero(id)
    }

    @Query
    fun getHeroesWithSurname(@DefaultValue("Skywalker") surname: String): List<Hero> {
        return service.getHeroesBySurname(surname)
    }

    @Subscription
    fun heroCreated(): Multi<Hero> {
        return processor
    }
}