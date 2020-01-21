package com.bookstore;

import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            bookstoreService.persistAuthorsAndBooks();
            
            // bookstoreService.updateAuthorsAndBooks();
            bookstoreService.updateAuthorsGtAgeAndBooks();
        };
    }
}



/*
How To Bulk Updates

Description: Bulk operations (updates and deletes) are faster than batching, can benefit from indexing, but they have two main drawbacks:

bulk updates/deletes may leave the Persistent Context in an outdated state (prevent this by flushing the Persistent Context before update/delete and close/clear it after the update/delete to avoid issues created by potentially unflushed or outdated entities)
bulk updates/deletes don't benefit of application-level optimistic locking mechanisms, therefore the lost updates are not prevented (it is advisable to signal these updates by explicitly incrementing version (if any is present)).
This application provides examples of bulk updates for Author and Book entities (between Author and Book there is a bidirectional lazy @OneToMany association). Both, Author and Book, has a version field.

Key points:

this application provide an example of bulk updates that don't involve managed entities (data is not loaded in the Persistent Context)
this application provide an example of bulk updates that involve managed entities (data is loaded in the Persistent Context before update it via bulk operations)
*/