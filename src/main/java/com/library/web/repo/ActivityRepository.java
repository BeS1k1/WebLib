package com.library.web.repo;

import com.library.web.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByTitleIgnoreCaseContaining(String title);

}
