package com.library.web.controllers;

import com.library.web.models.Book;
import com.library.web.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public String book(Model model) {
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/books/add")
    public String bookAdd(Model model) {
        return "book-add";
    }

    @PostMapping("/books/add")
    public String bookPostAdd(@RequestParam String title, @RequestParam String author, @RequestParam int book_year, @RequestParam int quantity, Model model){
        Book book = new Book(title, author, book_year, quantity);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable(value = "id") long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/books";
        }
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("books", res);
        return "book-details";
    }

    @GetMapping("/books/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/books";
        }
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("books", res);
        return "book-edit";
    }

    @PostMapping("/books/{id}/edit")
    public String bookPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String author, @RequestParam int book_year, @RequestParam int quantity, Model model){
        Book book = bookRepository.findById(id).orElseThrow(IllegalStateException::new);
        book.setTitle(title);
        book.setAuthor(author);
        book.setBookYear(book_year);
        book.setQuantity(quantity);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/remove")
    public String bookPostDelete(@PathVariable(value = "id") long id, Model model){
        Book book = bookRepository.findById(id).orElseThrow(IllegalStateException::new);
        bookRepository.delete(book);
        return "redirect:/books";
    }

   /* @PostMapping("/books")
    public String bookFilter(@PathVariable String filter, Model model){
        List<Book> books = bookRepository.findByTitle(filter);
        model.addAttribute("books", books);
        return "/book";
    }*/

}
