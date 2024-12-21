package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.NotificationDto;
import com.nckh.motelroom.mapper.NotificationMapper;
import com.nckh.motelroom.model.Notification;
import com.nckh.motelroom.repository.CriteriaRepository;
import com.nckh.motelroom.repository.NotificationRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.ImageService;
import com.nckh.motelroom.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    CriteriaRepository criteriaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    NotificationMapper notificationMapper;

    @Override
    public void createNotification(Notification notification) {

    }

    @Override
    public Page<NotificationDto> getNotificationsByEmail(String email, int page, boolean screen) {
        return null;
    }

    @Override
    public Page<NotificationDto> getNotificationsByEmailAndCriteria(String email, Long criteria, int page) {
        return null;
    }

    @Override
    public NotificationDto seenNotification(Long id) {
        return null;
    }
}