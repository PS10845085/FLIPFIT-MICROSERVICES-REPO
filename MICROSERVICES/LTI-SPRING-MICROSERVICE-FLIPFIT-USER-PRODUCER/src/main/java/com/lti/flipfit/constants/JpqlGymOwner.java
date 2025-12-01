package com.lti.flipfit.constants;

//src/main/java/com/example/persistence/jpql/JpqlGymUser.java


public final class JpqlGymOwner {
 private JpqlGymOwner() {}

 public static final String FIND_OWNER_DETAIL_BY_USERNAME_AND_STATUS =
		 """ 
	 		SELECT c
	        FROM GymOwner c
	        JOIN FETCH c.user u
	        LEFT JOIN FETCH c.address a
	        WHERE u.username = :username
	          AND u.status = :status
	      """;
 
}
