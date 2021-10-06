package boot.config;

import org.springframework.data.jpa.repository.JpaRepository;

import boot.model.Book;

//Spring Data magic :)
public interface BookRepository extends JpaRepository<Book, Long> {
}
