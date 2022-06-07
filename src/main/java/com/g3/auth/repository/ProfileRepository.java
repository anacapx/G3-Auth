package com.g3.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g3.auth.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
