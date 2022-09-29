package com.nrgroup.bucket.pincode.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrgroup.bucket.pincode.connector.PincodeClient;
import com.nrgroup.bucket.pincode.model.AddressDetails;
import com.nrgroup.bucket.pincode.model.PincodeResponse;
import com.nrgroup.bucket.pincode.model.PostOffice;

@Service
public class PincodeService {

    @Autowired
    PincodeClient pincodeClient;

    public AddressDetails getAddressByPincode(Integer pincode) {
        PincodeResponse response = pincodeClient.getPincodeAddress("/pincode/" + pincode);
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setStatus(response.getStatus());
        addressDetails.setMessage(response.getMessage());
        if (response.getPostOffice() != null) {
            addressDetails.setState(response.getPostOffice().get(0).getState());
            addressDetails.setBlock(response.getPostOffice().get(0).getBlock());
            addressDetails.setCircle(response.getPostOffice().get(0).getCircle());
            addressDetails.setCountry(response.getPostOffice().get(0).getCountry());
            addressDetails.setDistrict(response.getPostOffice().get(0).getDistrict());
            addressDetails.setDivision(response.getPostOffice().get(0).getDivision());
            addressDetails.setRegion(response.getPostOffice().get(0).getRegion());
            List<String> postOfficeNames = new ArrayList<>();
            for (PostOffice postOffice : response.getPostOffice()) {
                postOfficeNames.add(postOffice.getName());
            }
            addressDetails.setPostOffice(postOfficeNames);
        }
        return addressDetails;
    }
}
