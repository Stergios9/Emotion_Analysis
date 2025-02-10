package com.example.emotion_analysis.service.contact;

import com.example.emotion_analysis.dao.ContactRepository;
import com.example.emotion_analysis.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
}
