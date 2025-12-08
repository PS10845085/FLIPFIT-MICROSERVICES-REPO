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
 
 public static final String FIND_BY_ROLE_ID_WITH_ADDRESS = 
		 """
		    SELECT o
		    FROM GymOwner o
		    JOIN FETCH o.user u
		    LEFT JOIN FETCH o.address a
		    LEFT JOIN FETCH o.center cn
		    WHERE u.roleid = :roleid
		"""; 
 
}
