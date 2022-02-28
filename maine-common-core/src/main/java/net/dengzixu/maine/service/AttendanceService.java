package net.dengzixu.maine.service;

public interface AttendanceService {
    void createBasic(String title, String description, Long userID);

    void webTake(Long taskID, Long takeUserID);
}
