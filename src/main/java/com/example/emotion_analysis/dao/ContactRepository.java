package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {


    Contact save(Contact contact);
}
