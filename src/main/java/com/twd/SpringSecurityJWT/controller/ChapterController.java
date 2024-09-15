package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ChapterDTO;
import com.twd.SpringSecurityJWT.entity.Chapter;
import com.twd.SpringSecurityJWT.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    // Create a chapter
    @PostMapping("/admin/add/chapters")
    public ResponseEntity<ChapterDTO> createChapter(@RequestBody ChapterDTO chapterDTO) {
        ChapterDTO savedChapter = chapterService.createChapter(chapterDTO);
        return new ResponseEntity<>(savedChapter, HttpStatus.CREATED);
    }

    // Get all chapters for a course
    @GetMapping("/admin/get/chaptersByIdCourse/{courseId}") //id de course
    public List<ChapterDTO> getChaptersByCourseId(@PathVariable Long courseId) {
        return chapterService.getChaptersByCourseId(courseId);
    }

    // Get chapter by ID
    @GetMapping("/admin/get/chapterById/{id}") //id de chapter
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Long id) {
        ChapterDTO chapterDTO = chapterService.getChapterById(id);
        return ResponseEntity.ok(chapterDTO);
    }

    // Update a chapter
    @PutMapping("/admin/update/chapterByTitle/{title}")
    public ResponseEntity<ChapterDTO> updateChapter(@PathVariable String title, @RequestBody ChapterDTO chapterDetails) {
        ChapterDTO updatedChapter = chapterService.updateChapter(title, chapterDetails);
        return ResponseEntity.ok(updatedChapter);
    }

    // Delete a chapter
    @DeleteMapping("/admin/deleteById/{id}")
    public ResponseEntity<String> deleteChapter(@PathVariable Long id) {
        try {
            chapterService.deleteChapter(id);
            return ResponseEntity.ok("Chapter deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/admin/getAllChapters")
public List<ChapterDTO> getAllChapters() {
    return chapterService.getAllChapters();
}
}