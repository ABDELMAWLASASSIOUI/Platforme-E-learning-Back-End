package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ChapterDTO;
import com.twd.SpringSecurityJWT.entity.Chapter;
import com.twd.SpringSecurityJWT.entity.Course;
import com.twd.SpringSecurityJWT.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    // Convert Chapter entity to DTO
    private ChapterDTO convertToDTO(Chapter chapter) {
        ChapterDTO dto = new ChapterDTO();
        dto.setId(chapter.getId());
        dto.setTitle(chapter.getTitle());
        dto.setContent(chapter.getContent());
        dto.setCourseId(chapter.getCourse().getId());
        return dto;
    }

    // Convert DTO to Chapter entity
    private Chapter convertToEntity(ChapterDTO dto) {
        Chapter chapter = new Chapter();
        // chapter.setId(dto.getId());
        chapter.setTitle(dto.getTitle());
        chapter.setContent(dto.getContent());
        Course course = new Course(); // ou utilisez CourseRepository pour trouver l'entité Course
        course.setId(dto.getCourseId());
        chapter.setCourse(course);
        return chapter;
    }

    // Create a new chapter
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {

        ChapterDTO chaptersave= new ChapterDTO();

        Chapter chapter = convertToEntity(chapterDTO);
        Chapter savedChapter = chapterRepository.save(chapter);
        return convertToDTO(savedChapter);


    }

    /*
    // Get chapters by course ID
    public List<ChapterDTO> getChaptersByCourseId(Long courseId) {
        List<Chapter> chapters = chapterRepository.findByCourseId(courseId);
        return chapters.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

     */
    public List<ChapterDTO> getChaptersByCourseId(Long courseId) {
        List<Chapter> chapters = chapterRepository.findByCourseId(courseId);
        List<ChapterDTO> chapterDTOs = chapters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Affichage dans la console
        chapterDTOs.forEach(chapterDTO -> System.out.println(chapterDTO));

        return chapterDTOs;
    }
    // Delete a chapter by ID
    // Delete a chapter by ID
    public void deleteChapter(Long id) {
        if (chapterRepository.existsById(id)) {
            chapterRepository.deleteById(id);
        } else {
            throw new RuntimeException("Chapter not found with id: " + id);
        }
    }



    // Get a chapter by ID
    public ChapterDTO getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Chapter not found"));
        return convertToDTO(chapter);
    }

    // Update a chapter
    public ChapterDTO updateChapter(String title, ChapterDTO chapterDetails) {
        Chapter existingChapter = chapterRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Chapter not found"));
        existingChapter.setTitle(chapterDetails.getTitle());
        existingChapter.setContent(chapterDetails.getContent());

        Course course = new Course();
        course.setId(chapterDetails.getCourseId());
        existingChapter.setCourse(course);



        Chapter updatedChapter = chapterRepository.save(existingChapter);
        return convertToDTO(updatedChapter);
    }

    // Delete a chapter

    // Get all chapters
    public List<ChapterDTO> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        return chapters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}