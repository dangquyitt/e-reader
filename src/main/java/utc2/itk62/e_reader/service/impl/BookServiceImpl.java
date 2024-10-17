package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.dto.CreateBookRequest;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final FileService fileService;
    @Override
    public Book createBook(MultipartFile file, CreateBookRequest request) {

        Book book = Book.builder()
                .title(request.getTitle())
                .year(request.getYear())
                .genre(request.getGenre())
                .author(request.getAuthor())
                .numberOfPages(request.getNumberOfPages())
                .language(request.getLanguage())
                .file(fileService.uploadFile(file))
                .build();

        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_find")));
        fileService.deleteFile(book.getFile());
        bookRepository.delete(book);
        return !bookRepository.existsById(id);
    }

    @Override
    public Book updateBook(MultipartFile file, CreateBookRequest request, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_find")));
        if(!file.isEmpty()){
            fileService.deleteFile(book.getFile());
            book.setFile(fileService.uploadFile(file));
        }
        if(request.getTitle()!=null){
            book.setTitle(request.getTitle());
        }
        if(request.getGenre()!=null){
            book.setGenre(request.getGenre());
        }
        if(request.getLanguage()!=null){
            book.setLanguage(request.getLanguage());
        }
        if(request.getAuthor()!=null){
            book.setAuthor(request.getAuthor());
        }
        if(request.getYear() != book.getYear()){
            book.setYear(request.getYear());
        }
        if(request.getNumberOfPages() != book.getNumberOfPages()){
            book.setNumberOfPages(request.getNumberOfPages());
        }
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_find")));
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}
