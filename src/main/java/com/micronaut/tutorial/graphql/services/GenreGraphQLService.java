package com.micronaut.tutorial.graphql.services;

import com.micronaut.tutorial.auth.AuthenticationProvider;
import com.micronaut.tutorial.domain.Book;
import com.micronaut.tutorial.domain.Genre;
import com.micronaut.tutorial.graphql.GraphQLService;
import com.micronaut.tutorial.security.Secured;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.micronaut.validation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link com.micronaut.tutorial.domain.Genre} {@link GraphQLService}
 *
 * @author TCavka
 */
@Transactional
@GraphQLService
@Validated
public class GenreGraphQLService {

    @PersistenceContext
    private EntityManager entityManager;

    @GraphQLQuery
    public List<Genre> genreList() {
        return entityManager.createQuery("from Genre").getResultList();
    }

    @GraphQLQuery
    public Optional<Genre> genre(@GraphQLArgument(name = "id") long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @GraphQLQuery
    public List<Book> books(@GraphQLContext Genre genre, @GraphQLArgument(name = "limit") long limit) {
        return genre.getBooks().stream().limit(limit).collect(Collectors.toList());
    }

    @GraphQLMutation
    @Secured(AuthenticationProvider.ROLE_ADMIN)
    public Genre genreCreate(@NotNull @GraphQLArgument(name = "name") String name) {
        Genre genre = new Genre();
        genre.setName(name);
        entityManager.persist(genre);
        return genre;
    }
}
