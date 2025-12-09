package com.lti.flipfit.constant;



public final class JpqlGymCenter {
 private JpqlGymCenter() {}

public static final String UPDATE_STATUS_BY_ID =
        "UPDATE GymFlipFitCenter u SET u.status = :status WHERE u.id = :id";


}

