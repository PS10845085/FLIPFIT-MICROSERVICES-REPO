package com.lti.flipfit.constants;

//src/main/java/com/example/persistence/jpql/JpqlGymUser.java


public final class JpqlGymUser {
 private JpqlGymUser() {}

 public static final String FIND_CUSTOMER_DETAIL_BY_USERNAME_AND_STATUS =
		 """ 
	 		SELECT c
	        FROM GymCustomer c
	        JOIN FETCH c.user u
	        LEFT JOIN FETCH c.address a
	        WHERE u.username = :username
	          AND u.status = :status
	      """;

 public static final String FIND_CUSTOMER_BY_USER_ID_AND_STATUS_WITH_ADDRESS = 
		 """
		    SELECT c
		    FROM GymCustomer c
		    JOIN FETCH c.user u
		    LEFT JOIN FETCH c.address a
		    WHERE u.id = :userId
		      AND u.status = :status
		""";
 
 public static final String FIND_BY_ROLE_ID_WITH_ADDRESS = 
	 """
	    SELECT c
	    FROM GymCustomer c
	    JOIN FETCH c.user u
	    LEFT JOIN FETCH c.address a
	    LEFT JOIN FETCH c.center cn
	    WHERE u.roleid = :roleid
	""";
 
 public static final String FIND_BY_USERNAME =
         "SELECT g FROM GymUser g " +
         "WHERE g.username = :username";

 public static final String FIND_ACTIVE =
         "SELECT g FROM GymUser g WHERE g.status = 'ACTIVE'";

 public static final String FIND_WITH_ROLES_FETCH =
         "SELECT g FROM GymUser g LEFT JOIN FETCH g.roles " +
         "WHERE g.username = :username AND g.status = :status";

public static final String FIND_BY_USERID = 
		"SELECT g FROM GymCustomer g " +
        "WHERE g.userid = :userid";


public static final String UPDATE_STATUS_BY_ID =
        "UPDATE GymUser u SET u.status = :status WHERE u.id = :id";


}

