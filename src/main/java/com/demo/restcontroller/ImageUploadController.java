package com.demo.restcontroller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dao.ImageRepository;
import com.demo.entity.Image;

@Controller
public class ImageUploadController {

	@Autowired
	private ImageRepository imageService;

	@GetMapping("/upload")
	public String showUploadForm(Model model) {
		model.addAttribute("image", new Image());
		return "upload"; // The name of the JSP file for the image upload form without extension
	}

	@PostMapping("/upload")
	public String handleImageUpload(@ModelAttribute("name") String name, @RequestParam("file") MultipartFile file,
			Model model) throws IOException {
		System.out.println("name file : " + file.getName() + " \nfile " + file.getBytes().toString());

		Image image = new Image();
		try {
			image = new Image(name, file.getBytes());
			imageService.save(image);
			System.out.println("Saved : " + image);
			model.addAttribute("message", "Image uploaded successfully!");
		} catch (IOException e) {
			model.addAttribute("message", "Error uploading image.");
			e.printStackTrace();
			return "redirect:/upload";
		}
		String redirect = ("redirect:/images/" + image.getId());
		System.out.println("redirect : " + redirect);
		return redirect;
	}

	@GetMapping("/images/{id}")
	public String showImages(@PathVariable("id") Long id, Model model) {
		System.out.println("ID : " + id);
		Image image = imageService.findById(Long.valueOf(id)).orElse(null);
		System.out.println("Image byte array length: " + image.getImage().length); // Add this line to check the byte
																					// array length
		// Add this line to check the contents of imageMap

		if (image != null) {
			Map<String, String> imageMap = new HashMap<>();
			imageMap.put("name", image.getName());
			imageMap.put("imageUrl", "data:image/png;base64," + Base64.getEncoder().encodeToString(image.getImage()));
			model.addAttribute("imageMap", imageMap);
		}

		return "images";
	}
}
