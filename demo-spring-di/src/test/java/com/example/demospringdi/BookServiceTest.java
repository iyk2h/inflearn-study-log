package com.example.demospringdi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    BookService bookService;

    @Test
    public void di() {
        Assertions.assertNotNull(bookService);
        Assertions.assertNotNull(bookService.bookRepository);
    }
}