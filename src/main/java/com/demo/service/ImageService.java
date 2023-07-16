package com.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dao.ImageRepository;
import com.demo.entity.Image;

@Service
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;

	public void saveImage(MultipartFile file, String imageName) throws IOException {
		Image imageEntity = new Image(imageName, file.getBytes());
		System.out.println(
				"imageName : " + imageName + ", file: [name:  " + file.getName() + ",size:  " + file.getSize());

		imageRepository.save(imageEntity);
	}

	public Optional<Image> getImageById(Long id) {
		return imageRepository.findById(id);
	}
}
