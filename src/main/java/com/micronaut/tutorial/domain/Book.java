package com.micronaut.tutorial.domain;

import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne
    private Genre genre;

    public Book() {
    }

    public Book(@NotNull final String name, @NotNull final String author, final Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    @GraphQLQuery
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @GraphQLQuery
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @GraphQLQuery
    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book{");
        sb.append("id=");
        sb.append(id);
        sb.append(", name='");
        sb.append(name);
        sb.append("', isbn='");
        sb.append(author);
        sb.append("', genre='");
        sb.append(genre);
        sb.append("'}");
        return sb.toString();
    }
}
