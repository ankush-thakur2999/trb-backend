package ip.gr1.TechnologyRelatedBlogs.Repository;

import ip.gr1.TechnologyRelatedBlogs.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,Long> {
    Optional<User> findByUserName(String username);
}
