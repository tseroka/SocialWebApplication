package com.app.web.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
	
	@Query("SELECT P.nickname FROM Profile P where ( (:sex=''OR P.sex=:sex) AND (:city='' OR P.city=:city ) AND ((:interests is null) OR (:interests in P.interests) )    ) ") 
	List<String> findProfileBySexAndCityAndInterests(@Param("sex") String sex, @Param("city") String city, @Param("interests") List<String> interests);

}
