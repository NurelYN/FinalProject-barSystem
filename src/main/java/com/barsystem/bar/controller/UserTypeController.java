package com.barsystem.bar.controller;

import com.barsystem.bar.converter.UserTypeConverter;
import com.barsystem.bar.dto.userType.UserTypeRequest;
import com.barsystem.bar.model.UserType;
import com.barsystem.bar.service.UserTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value="/usertypes")
public class UserTypeController {

    private final UserTypeService userTypeService;

    private final UserTypeConverter userTypeConverter;

    @PostMapping
    public ResponseEntity<UserType> save(@RequestBody @Valid UserTypeRequest userTypeRequest){
        UserType userType = userTypeConverter.convert(userTypeRequest);
        UserType savedUserType = userTypeService.save(userType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserType);
    }

    @GetMapping
    public ResponseEntity<Set<UserType>> findAll(){
        return ResponseEntity.ok().body(userTypeService.findAll()
                .stream()
                .collect(Collectors.toSet()));
    }

    @GetMapping(value="/id/{id}")
    public ResponseEntity<UserType> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userTypeService.findById(id));
    }

    @GetMapping(value="/name/{name}")
    public ResponseEntity<UserType> findByName(@PathVariable String name){
        return ResponseEntity.ok().body(userTypeService.findByName(name));
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){
        userTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/name/{name}")
    public ResponseEntity<HttpStatus> deleteByName(@PathVariable String name){
        userTypeService.delete(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserType> update(@RequestBody @Valid UserTypeRequest userTypeRequest,@PathVariable Long id){
        UserType userType = userTypeConverter.convert(userTypeRequest);
        UserType updatedUserType = userTypeService.update(userType,id);
        return ResponseEntity.ok().body(updatedUserType);
    }

}

