package com.pint.BusinessLogic.Services;

import com.pint.BusinessLogic.Security.User;
import com.pint.Data.DataFacade;
import com.pint.Data.Models.BloodDrive;
import com.pint.Data.Models.Employee;
import com.pint.Data.Models.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dionny on 11/24/2015.
 */
@Service
public class BloodDriveService {

    @Autowired
    private DataFacade dataFacade;

    public List<BloodDrive> getBloodDrivesByLocation(String city, String state) {
        return dataFacade.getBloodDrivesByLocation(city, state);
    }

    public List<BloodDrive> getBloodDrivesForCoordinator(Hospital hospital, User user) {
        List<BloodDrive> output = new ArrayList<>();
        Iterable<BloodDrive> bloodDrives = dataFacade.getBloodDrives();
        for (BloodDrive bloodDrive :
                bloodDrives) {	 
            if (bloodDrive.getHospitalId().getId() == hospital.getId() &&
                    bloodDrive.getCoordinator().getUserId() == user.getId()) {
                output.add(bloodDrive);
            }
        } 

        return output;
    }

    public BloodDrive createBloodDrive(Hospital hospital,
                                       String title,
                                       String address,
                                       String description,
                                       String city,
                                       String state,
                                       Date from,
                                       Date to,
                                       Employee coordinator) {

        BloodDrive bloodDrive = new BloodDrive();
        bloodDrive.setTitle(title);
        bloodDrive.setAddress(address);
        bloodDrive.setDescription(description);
        bloodDrive.setStartTime(from);
        bloodDrive.setEndTime(to);
        bloodDrive.setCoordinator(coordinator);
        bloodDrive.setCity(city);
        bloodDrive.setState(state);
        bloodDrive.setHospitalId(hospital);

        dataFacade.createBloodDrive(bloodDrive);

        return bloodDrive;
    }

	public BloodDrive getBloodDrive(long bdId, User user) {
		// TODO Auto-generated method stub
	
		BloodDrive bd=dataFacade.getBloodDrivesById(bdId);
		if(bd.getCoordinator().getUserId()==user.getId())
		{
			return bd;
			
		}
		
		return null;
		
	}
	
	
	public List<Employee> getNursesForBloodDrive(BloodDrive bd, User user) {
		// TODO Auto-generated method stub
	
		 List<Employee> output = new ArrayList<>();
		 long hospitalid=bd.getHospitalId().getId() ;
		 if(bd.getCoordinator().getUserId() == user.getId())
		 {
			 Iterable<Employee> nurses=dataFacade.getNurses(hospitalid);
			 for (Employee nurse : nurses)
	                 {	 
	            if (nurse.getbloodDriveId()==bd.bloodDriveId())
	            		{
	                output.add(nurse);
	            }
	        }
		 }
		 
		 return output;
		 
		 
		
			
	}
	
	public List<Employee> getUnassignedNurses(BloodDrive bd, User user) {
		// TODO Auto-generated method stub
	
		List<Employee> output = new ArrayList<>();
		 long hospitalid=bd.getHospitalId().getId() ;
		 if(bd.getCoordinator().getUserId() == user.getId())
		 {
			 Iterable<Employee> nurses=dataFacade.getNurses(hospitalid);
			 for (Employee nurse : nurses)
	        {	 
	            if (nurse.getBloodDriveId()==null)
	            {
	                output.add(nurse);
	            }
	        }
		 }
		 
		 return output;
	
}
