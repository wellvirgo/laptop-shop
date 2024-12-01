package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findById(long id);

    public boolean existsByEmail(String email);

    public User findByEmail(String email);

    @NonNull
    public Page<User> findAll(@NonNull Pageable pageable);
}
