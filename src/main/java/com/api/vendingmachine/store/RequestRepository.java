package com.api.vendingmachine.store;

import com.api.vendingmachine.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import util.Status;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("from Request where id = ?1 and status in ('MONEY_ADDED', 'PROCESSING_ORDER')")
    Request fetchPendingRequestById(int requestId);

    @Query("from #{#entityName} where id = ?1 and status =?2")
    Request fetchRequestByIdAndStatus(int requestId, Status status);

}
