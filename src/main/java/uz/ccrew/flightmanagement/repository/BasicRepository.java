package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.exp.NotFoundException;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.JpaRepository;

@NoRepositoryBean
public interface BasicRepository<T, D> extends JpaRepository<T, D> {
    default T loadById(D id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }
}