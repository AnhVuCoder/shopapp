//package com.ngleanhvu.shopapp.controller;
//
//import com.ngleanhvu.shopapp.dto.CategoryDTO;
//import com.ngleanhvu.shopapp.repo.ICategoryRepo;
//import com.ngleanhvu.shopapp.service.ICategoryService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("api/v1/categories")
//public class CategoryController {
//    @Autowired
//    private ICategoryService iCategoryService;
//    @GetMapping
//    public ResponseEntity<String> getAllCategories(@RequestParam("page") int page,
//                                                   @RequestParam("limit") int limit){
//        return ResponseEntity.ok(String.format("Page=%d ,Limit=%d", page,limit));
//    }
//    @PostMapping
//    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO,
//                                                 BindingResult result){
//        if(result.hasErrors()){
//            List<String> messageError=result.getFieldErrors()
//                    .stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .toList();
//            return ResponseEntity.badRequest().body(messageError);
//        }
//        return ResponseEntity.ok().body(iCategoryService.create(categoryDTO));
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateCategory(@PathVariable Integer id){
//        return ResponseEntity.ok("UPDATE "+id);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
//        return ResponseEntity.ok("DELETE");
//    }
//}
