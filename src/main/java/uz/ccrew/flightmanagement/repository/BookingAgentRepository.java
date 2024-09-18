package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.BookingAgent;

public interface BookingAgentRepository extends BasicRepository<BookingAgent, Integer> {
    boolean existsByAgentName(String agentName);
}
