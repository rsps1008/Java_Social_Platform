package socailPlatform.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import socailPlatform.JavaSocialPlatformApplication.RegisterInfo;
import socailPlatform.JavaSocialPlatformApplication.addPostInfo;
import socailPlatform.Model.Post;

@RestController
public class PostController {
	@Autowired
    private Post postService;
	
	@GetMapping("/PostController/getAllPosts")
	public List<Map<String, Object>> getAllPosts() {
    	return postService.getAllPosts();
    }
	
	@PostMapping("/PostController/addNewPost")
	public boolean addNewPost(@RequestBody addPostInfo post) {
    	return postService.addNewPost(post.user, post.content, post.imageUrl);
    }
}
