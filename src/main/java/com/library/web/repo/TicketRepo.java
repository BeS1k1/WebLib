package com.library.web.repo;

import com.library.web.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUser_id(Long user_id);

}
