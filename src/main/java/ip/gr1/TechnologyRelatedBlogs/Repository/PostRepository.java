package ip.gr1.TechnologyRelatedBlogs.Repository;

import ip.gr1.TechnologyRelatedBlogs.Model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts,Long> {
}
