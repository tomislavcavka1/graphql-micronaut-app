package com.micronaut.tutorial.beans;

import com.micronaut.tutorial.domain.Book;
import com.micronaut.tutorial.domain.Genre;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Loads the initial data to the database
 *
 * @author TCavka
 */
@Singleton
public class DataLoader {

    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @PersistenceContext
    private EntityManager entityManager;

    @EventListener
    @Transactional
    protected void init(StartupEvent event) {
        // if no data yet in the database
        if (((Number) entityManager.createQuery("select count(*) from Genre").getSingleResult()).longValue() == 0) {
            logger.debug("Loading initial data to the database");

            Genre genre = new Genre("Crime");
            entityManager.persist(genre);

            entityManager.persist(new Book("Truman Capote", "In Cold Blood", genre));
            entityManager.persist(new Book("James Ellroy", "My Dark Places", genre));
            entityManager.persist(new Book("Ann Rule", "The Stranger Beside Me", genre));
        }

        // log what we have in the db
        for (Book book : (List<Book>) entityManager.createQuery("from Book").getResultList())
            logger.debug("Book found: {}, {}, {}", book.getAuthor(), book.getName(), book.getGenre().getName());
    }
}
