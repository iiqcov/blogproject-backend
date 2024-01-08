package iiqcov.blog.springbootdeveloper.api.image;

import iiqcov.blog.springbootdeveloper.dto.image.AddImageResponse;
import iiqcov.blog.springbootdeveloper.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/api/image")
    public ResponseEntity<AddImageResponse> addImage(@RequestParam MultipartFile image){
        String imageUrl= imageService.addImage(image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new AddImageResponse(imageUrl));
    }
}
