package com.demo.restcontroller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.Image;
import com.demo.service.ImageService;

@Controller
@RequestMapping("/api/images")
public class ImageRestController {

	@Autowired
	private ImageService imageService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String imageName) {
		try {
			imageService.saveImage(file, imageName);
			return ResponseEntity.ok().body("Image uploaded successfully!");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
		Optional<Image> Image = imageService.getImageById(id);
		if (Image.isPresent()) {
			Image image = Image.get();
			return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + image.getName() + "\"")
					.body(image.getImage());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
