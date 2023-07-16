package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
