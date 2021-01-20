package ru.eremenko.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.eremenko.demo.model.Request;

public interface RequestRepo extends JpaRepository<Request,Long> {
}
