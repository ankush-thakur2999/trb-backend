package ip.gr1.TechnologyRelatedBlogs.Service;

import ip.gr1.TechnologyRelatedBlogs.Exception.PostNotFoundExeption;
import ip.gr1.TechnologyRelatedBlogs.Model.Posts;
import ip.gr1.TechnologyRelatedBlogs.Repository.PostRepository;
import ip.gr1.TechnologyRelatedBlogs.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
    @Autowired
    private  AuthService authService;
    @Autowired
    PostRepository postRepository;
    private Posts mapFromDtoToPost (PostDto postDto){
        Posts posts= new Posts();
        posts.setTitle(postDto.getTitle());
        posts.setContent(postDto.getContent());
       User username=  authService.getCurrentUser().orElseThrow(()->new IllegalArgumentException("NO User Logged in"));

        posts.setUsername(username.getUsername());
        posts.setPostedOn(Instant.now());
       return posts;

    }

    public List<PostDto> showAllPosts() {
        List<Posts> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    private PostDto mapFromPostToDto(Posts posts) {
        PostDto postDto = new PostDto();
        postDto.setId(posts.getId());
        postDto.setTitle(posts.getTitle());
        postDto.setContent(posts.getContent());
        postDto.setUsername(posts.getUsername());
        return postDto;
    }
    public void  createPost(PostDto postDto){
        Posts posts = mapFromDtoToPost(postDto);
        postRepository.save(posts);
    }

    public  PostDto readSinglePost(Long id) {
        Posts posts =postRepository.findById(id).orElseThrow( ()-> new PostNotFoundExeption("For id"+id));
        return mapFromPostToDto(posts);
    }
}
