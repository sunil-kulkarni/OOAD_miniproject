package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Image;
import com.example.blog.entity.User;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService {

    private final String UPLOAD_DIR = "./uploads/";

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    public BlogService(BlogRepository blogRepository, UserService userService, ImageRepository imageRepository) {
        this.blogRepository = blogRepository;
        this.userService = userService;
        this.imageRepository = imageRepository;
    }

    public Blog create(Integer userId, String title, String content, MultipartFile[] images) {
        System.out.println("Creating blog for userId: " + userId + ", title: " + title);
        User user = userService.findById(userId);
        System.out.println("Found user: " + user.getName());
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUser(user);
        
        Blog savedBlog = blogRepository.save(blog);
        System.out.println("Saved blog with id: " + savedBlog.getId());
        
        saveImages(savedBlog, images);
        
        return savedBlog;
    }

    public Blog edit(Integer blogId, String title, String content, MultipartFile[] images, boolean removeImage) {
        Blog blog = view(blogId);
        if (title != null) blog.setTitle(title);
        if (content != null) blog.setContent(content);
        
        // Handle image removal
        if (removeImage && blog.getImagePath() != null) {
            // Delete the current image file
            try {
                Path oldImagePath = Paths.get(UPLOAD_DIR, blog.getImagePath());
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            } catch (IOException e) {
                // Log but don't fail if old file deletion fails
                System.err.println("Could not delete old image: " + e.getMessage());
            }
            // Clear the image path
            blog.setImagePath(null);
        }
        // If new images are provided during edit, replace the cover image
        else if (images != null && images.length > 0 && !images[0].isEmpty()) {
            // Delete the old cover image file
            if (blog.getImagePath() != null) {
                try {
                    Path oldImagePath = Paths.get(UPLOAD_DIR, blog.getImagePath());
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                } catch (IOException e) {
                    // Log but don't fail if old file deletion fails
                    System.err.println("Could not delete old image: " + e.getMessage());
                }
            }
            // Clear the old cover image path so the new one becomes the cover
            blog.setImagePath(null);
        }
        
        saveImages(blog, images);
        
        return blogRepository.save(blog);
    }
    
    private void saveImages(Blog blog, MultipartFile[] imageFiles) {
        if (imageFiles != null && imageFiles.length > 0) {
            for (MultipartFile file : imageFiles) {
                if (file.isEmpty()) continue;
                try {
                    Path uploadPath = Paths.get(UPLOAD_DIR);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(filename);
                    Files.copy(file.getInputStream(), filePath);

                    Image image = new Image();
                    image.setUrl(filename); // Store filename as URL
                    image.setBlog(blog);
                    imageRepository.save(image);
                    
                    // Set the first image as the cover image if none exists
                    if (blog.getImagePath() == null) {
                        blog.setImagePath(filename);
                        blogRepository.save(blog);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
            }
        }
    }

    public void delete(Integer blogId) {
        blogRepository.deleteById(blogId);
    }

    public Blog view(Integer blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }
    
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }
    
    public List<Blog> findByUserId(Integer userId) {
        return blogRepository.findByUserId(userId);
    }
}
