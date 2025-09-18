package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;
import java.util.List;
import java.util.Map;

/**
 * Service interface for Alert operations.
 * Defines the contract for business logic operations related to emergency alerts and information queries.
 */
public interface AlertServiceInterface {
    
    /**
     * Get child alert information for a specific address.
     * @param address the address to search for children
     * @return ChildAlertDTO containing children and other residents
     */
    ChildAlertDTO getChildAlert(String address);
    
    /**
     * Get phone numbers for residents served by a specific firestation.
     * @param firestation the firestation number
     * @return List of phone alert DTOs
     */
    List<PhoneAlertDTO> getPhoneAlert(String firestation);
    
    /**
     * Get fire information for a specific address.
     * @param address the address to get fire information for
     * @return FireDTO containing residents and firestation information
     */
    FireDTO getFireInfo(String address);
    
    /**
     * Get flood information for specific stations.
     * @param stations list of station numbers
     * @return Map of station numbers to flood information
     */
    Map<String, List<FloodDTO>> getFloodStations(List<String> stations);
    
    /**
     * Get person information by last name.
     * @param lastName the last name to search for
     * @return List of person information DTOs
     */
    List<PersonInfoDTO> getPersonInfo(String lastName);
    
    /**
     * Get community email addresses for a specific city.
     * @param city the city name
     * @return List of email addresses
     */
    List<String> getCommunityEmail(String city);
    
    /**
     * Get firestation coverage information.
     * @param firestation the firestation number
     * @return FirestationCoverageDTO containing coverage details
     */
    FirestationCoverageDTO getFirestationCoverage(String firestation);
}