package com.example.codefellowship.infrastructure;

import com.example.codefellowship.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Posts , Long> {
}
