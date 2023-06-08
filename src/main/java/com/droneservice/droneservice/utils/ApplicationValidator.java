/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.utils;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class ApplicationValidator implements Predicate<String> {

    @Override
    public boolean test(String t) {
        return false;
    }

    public boolean validateMedicationName(String medication) {
        Boolean result = false;
        Pattern pattern = Pattern.compile("^[0-9A-Za-z\\_\\-]+$");
        Matcher matcher = pattern.matcher(medication);
        boolean matchFound = matcher.matches();
        if (matchFound) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
     public boolean validateMedicationCode(String medication) {
        Boolean result = false;
        Pattern pattern = Pattern.compile("^[0-9A-Z\\_]+$");
        Matcher matcher = pattern.matcher(medication);
        boolean matchFound = matcher.matches();
        if (matchFound) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
