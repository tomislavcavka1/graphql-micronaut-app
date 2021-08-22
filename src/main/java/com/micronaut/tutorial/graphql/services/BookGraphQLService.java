package com.micronaut.tutorial.graphql.services;

import com.micronaut.tutorial.domain.Book;
import com.micronaut.tutorial.graphql.GraphQLService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * {@link com.micronaut.tutorial.domain.Book} {@link com.micronaut.tutorial.graphql.GraphQLService}
 *
 * @author TCavka
 */
@Transactional
@GraphQLService
public class BookGraphQLService {

    @PersistenceContext
    private EntityManager entityManager;

    @GraphQLQuery
    public List<Book> bookList() {
        return entityManager.createQuery("from Book").getResultList();
    }

    @GraphQLQuery
    public Optional<Book> book(@GraphQLArgument(name = "id") long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }
}
