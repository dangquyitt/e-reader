package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
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
    public Book createBook(CreateBookParam createBookParam) {

        Book book = Book.builder()
                .title(createBookParam.getRequest().getTitle())
                .releaseYear(createBookParam.getRequest().getYear())
                .genre(createBookParam.getRequest().getGenre())
                .author(createBookParam.getRequest().getAuthor())
                .totalPage(createBookParam.getRequest().getNumberOfPages())
                .language(createBookParam.getRequest().getLanguage())
                .fileUrl(fileService.uploadFile(createBookParam.getFile()))
                .build();

        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_found")));
        fileService.deleteFile(book.getFileUrl());
        bookRepository.delete(book);
        return !bookRepository.existsById(id);
    }

    @Override
    public Book updateBook(UpdateBookParam updateBookParam) {
        Book book = bookRepository.findById(updateBookParam.getId()).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_found")));
        if(!updateBookParam.getFile().isEmpty()){
            fileService.deleteFile(book.getFileUrl());
            book.setFileUrl(fileService.uploadFile(updateBookParam.getFile()));
        }
        if(updateBookParam.getRequest().getTitle()!=null){
            book.setTitle(updateBookParam.getRequest().getTitle());
        }
        if(updateBookParam.getRequest().getGenre()!=null){
            book.setGenre(updateBookParam.getRequest().getGenre());
        }
        if(updateBookParam.getRequest().getLanguage()!=null){
            book.setLanguage(updateBookParam.getRequest().getLanguage());
        }
        if(updateBookParam.getRequest().getAuthor()!=null){
            book.setAuthor(updateBookParam.getRequest().getAuthor());
        }
        if(updateBookParam.getRequest().getYear() != book.getReleaseYear()){
            book.setReleaseYear(updateBookParam.getRequest().getYear());
        }
        if(updateBookParam.getRequest().getNumberOfPages() != book.getTotalPage()){
            book.setTotalPage(updateBookParam.getRequest().getNumberOfPages());
        }
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("book","book.id.not_found")));
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}
