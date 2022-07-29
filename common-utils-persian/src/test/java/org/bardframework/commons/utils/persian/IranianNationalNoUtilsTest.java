package org.bardframework.commons.utils.persian;

import org.apache.commons.lang3.RandomStringUtils;

class IranianNationalNoUtilsTest {

    public static void main(String[] args) {
        String prefix = "00";
        String nationalNo = RandomStringUtils.randomNumeric(8);
        while (!IranianNationalNoUtils.checkNationalNo(Long.parseLong(prefix + nationalNo))) {
            nationalNo = RandomStringUtils.randomNumeric(8);
        }
        System.out.println(prefix + nationalNo);
    }
}